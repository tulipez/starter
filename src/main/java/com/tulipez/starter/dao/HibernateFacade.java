package com.tulipez.starter.dao;

import java.util.Properties;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import org.hibernate.cfg.Configuration;
import org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder;
import org.hibernate.reactive.stage.Stage;
import org.hibernate.reactive.stage.Stage.Session;
import org.hibernate.service.ServiceRegistry;

import com.tulipez.starter.model.Action;
import com.tulipez.starter.model.StarterUser;
import com.tulipez.starter.model.Workspace;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import jakarta.persistence.criteria.CriteriaBuilder;

public class HibernateFacade {
	
	private Vertx vertx;
	private JsonObject config;
	private Stage.SessionFactory sessionFactory;

	public HibernateFacade(Vertx vertx, JsonObject config) {
		this.vertx = vertx;
		this.config = config;
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
			hibernateConfiguration.addAnnotatedClass(Workspace.class);
			hibernateConfiguration.addAnnotatedClass(Action.class);

			ServiceRegistry serviceRegistry = new ReactiveServiceRegistryBuilder().applySettings(hibernateConfiguration.getProperties()).build();
			sessionFactory = hibernateConfiguration.buildSessionFactory(serviceRegistry).unwrap(Stage.SessionFactory.class);
			
			return (Void)null;
		});
	}
	
	public CriteriaBuilder getCriteriaBuilder() {
		return sessionFactory.getCriteriaBuilder();
	}
	
	public <T> Future<T> withTransaction(Function<Session, CompletionStage<T>> work) {
		return Future.fromCompletionStage(sessionFactory.withTransaction(
				session -> work.apply(session)));
	}
	
}
