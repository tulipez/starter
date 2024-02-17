package com.tulipez.starter.dao;

import java.util.Properties;

import org.hibernate.cfg.Configuration;
import org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder;
import org.hibernate.reactive.stage.Stage;
import org.hibernate.service.ServiceRegistry;

import com.tulipez.starter.model.StarterUser;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class HibernateFacade {
	
	private Vertx vertx;
	private JsonObject config;
	private Stage.SessionFactory sessionFactory;

	public HibernateFacade(Vertx vertx, JsonObject config) {
		this.vertx = vertx;
		this.config = config;
	}
	
	public Stage.SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public Future<Void> init() {
		return vertx.executeBlocking(() -> {
			Properties hibernateProperties = new Properties();

			hibernateProperties.put("hibernate.connection.url", config.getString("hibernate.connection.url"));
			hibernateProperties.put("hibernate.connection.username", config.getString("hibernate.connection.username"));
			hibernateProperties.put("hibernate.connection.password", config.getString("hibernate.connection.password"));
			hibernateProperties.put("jakarta.persistence.schema-generation.database.action", "update");
			hibernateProperties.put("hibernate.connection.pool_size", "10");
			hibernateProperties.put("hibernate.show_sql", "false");
			hibernateProperties.put("hibernate.format_sql", "false");
			hibernateProperties.put("hibernate.highlight_sql", "false");

			Configuration hibernateConfiguration = new Configuration();
			hibernateConfiguration.setProperties(hibernateProperties);
			hibernateConfiguration.addAnnotatedClass(StarterUser.class);

			ServiceRegistry serviceRegistry = new ReactiveServiceRegistryBuilder().applySettings(hibernateConfiguration.getProperties()).build();
			sessionFactory = hibernateConfiguration.buildSessionFactory(serviceRegistry).unwrap(Stage.SessionFactory.class);
			
			return (Void)null;
		});
	}
	
	public <T> Future<T> findByAttributeValue(Class<T> resultClass, String attributeName, Object attributeValue) {
		return Future.fromCompletionStage(sessionFactory.withTransaction(session -> {
			CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
			CriteriaQuery<T> query = criteriaBuilder.createQuery(resultClass);
			Root<T> fromRoot = query.from(resultClass);
			query.where(criteriaBuilder.equal(fromRoot.get(attributeName), attributeValue));
			return session.createQuery(query).getSingleResultOrNull();
        }));
	}
	
	public <T> Future<T> persist(T object) {
		return Future.fromCompletionStage(sessionFactory.withTransaction(session -> session.persist(object)))
				.compose(v -> Future.succeededFuture(object));
	}
	
	public <T> Future<T> merge(T object) {
		return Future.fromCompletionStage(sessionFactory.withTransaction(session -> session.merge(object)));
	}
	
}
