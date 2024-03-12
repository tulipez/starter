package com.tulipez.starter.dao;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.hibernate.reactive.stage.Stage.Session;

import com.tulipez.starter.model.Action;
import com.tulipez.starter.model.ActionSeries;
import com.tulipez.starter.model.Workspace;
import com.tulipez.starter.util.JacksonUtils;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

//TODO gestion d'erreur
//TODO commentaires
//TODO tests

public class ActionDAO {
	
	private HibernateFacade hibernateFacade;
	
	public ActionDAO(HibernateFacade hibernateFacade) {
		this.hibernateFacade = hibernateFacade;
	}
	
	private CompletionStage<ActionSeries> createActionSeries(Session session, JsonObject createSpecif) {
		String seriesSpecif = createSpecif.getString("series");
		
		if(seriesSpecif==null) return CompletableFuture.completedStage(null);
		if("no-repeat".equals(seriesSpecif)) return CompletableFuture.completedStage(null);
		
		ActionSeries series = new ActionSeries();
		series.setDefaultActionName(createSpecif.getString("name"));
		return session.persist(series).thenApply(v -> series);
	}
	
	public Future<Action> createAction(Workspace workspace, JsonObject createSpecif) {
		return hibernateFacade.withTransaction(session ->
			createActionSeries(session, createSpecif).thenCompose(series -> {
				Action newAction = new Action();
				newAction.setWorkspace(workspace);
				newAction.setName(createSpecif.getString("name"));
				newAction.setDate(LocalDate.now());
				if(series!=null) {
					newAction.setSeries(series);
					series.getActions().add(newAction);
				}
				return session.persist(newAction).thenApply(v -> {
					workspace.getActions().add(newAction);
					return newAction;
				});
			})
		);
	}
	
	public Future<Action> updateAction(JsonObject updateSpecif) {
		return hibernateFacade.withTransaction(session -> 
			session
			.find(Action.class, updateSpecif.getInteger("id"))
			.thenCompose(action -> session.merge(JacksonUtils.updatePojo(action, updateSpecif)))
		);
	}
	
}
