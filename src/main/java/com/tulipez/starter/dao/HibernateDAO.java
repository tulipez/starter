package com.tulipez.starter.dao;

import java.util.Properties;
import java.util.concurrent.CompletionStage;

import org.hibernate.cfg.Configuration;
import org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder;
import org.hibernate.reactive.stage.Stage;
import org.hibernate.service.ServiceRegistry;

import com.tulipez.starter.model.Quest;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class HibernateDAO {
	
	private Vertx vertx;
	private JsonObject config;
	private Stage.SessionFactory hibernateSessionFactory;

	public HibernateDAO(Vertx vertx, JsonObject config) {
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
			hibernateConfiguration.addAnnotatedClass(Quest.class);

			ServiceRegistry serviceRegistry = new ReactiveServiceRegistryBuilder().applySettings(hibernateConfiguration.getProperties()).build();
			hibernateSessionFactory = hibernateConfiguration.buildSessionFactory(serviceRegistry).unwrap(Stage.SessionFactory.class);
			
			return (Void)null;
		});
	}
	
	public Future<Void> persistQuest(Quest quest) {
		CompletionStage<Void> insertionResult = hibernateSessionFactory.withTransaction((session, transaction) -> {
			CompletionStage<Void> resultPersist = session.persist(quest);
			return resultPersist;
        });
		return Future.fromCompletionStage(insertionResult);
	}
}
