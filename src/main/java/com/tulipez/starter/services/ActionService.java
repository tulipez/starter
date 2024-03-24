package com.tulipez.starter.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.tulipez.starter.HibernateFacade;
import com.tulipez.starter.model.Action;
import com.tulipez.starter.model.ActionSeries;
import com.tulipez.starter.model.Workspace;
import com.tulipez.starter.util.DateUtils;
import com.tulipez.starter.util.JacksonUtils;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

//TODO gestion d'erreur
//TODO commentaires
//TODO tests
//TODO decoupler DAO(get, save, update, delete), factory, facade, service ?

public class ActionService {
	
	private HibernateFacade hibernateFacade;
	
	public ActionService(HibernateFacade hibernateFacade) {
		this.hibernateFacade = hibernateFacade;
	}
	
	public Future<Action> createAction(Workspace workspace, JsonObject createSpecif) {
		String seriesSpecif = createSpecif.getString("series");
		String name = createSpecif.getString("name");
		
		if(seriesSpecif!=null && !"no-repeat".equals(seriesSpecif)) {
			ActionSeries series = createSeries(workspace, name);
			Action action = createActionFromSeries(series);
			return hibernateFacade.withTransaction(session ->
				session.persist(series)
				.thenCompose(v -> session.persist(action))
				.thenApply(v -> action)
			);
		}
		else {
			Action action = createNamedAction(workspace, name);
			return hibernateFacade.withTransaction(session ->
				session.persist(action)
				.thenApply(v -> action)
			);
		}
	}
	
	public Future<Action> updateAction(JsonObject updateSpecif) {
		return hibernateFacade.withTransaction(session -> 
			session
			.find(Action.class, updateSpecif.getInteger("id"))
			.thenCompose(action -> session.merge(JacksonUtils.updatePojo(action, updateSpecif)))
		);
	}

	public Future<List<Action>> createActionsFromAllSeries() {
		return hibernateFacade.withTransaction(session ->
			session
			.createQuery("SELECT series FROM ActionSeries series"
					+ " LEFT JOIN FETCH series.workspace"
					+ " LEFT JOIN FETCH series.actions", ActionSeries.class)
	    	.getResultList()
	    	.thenCompose(seriesList -> {
	    		List<CompletableFuture<Void>> futures = new ArrayList<>();
	    		List<Action> actionList = new ArrayList<Action>();
	    		for (ActionSeries series : seriesList) {
	    			Action action = createActionFromSeries(series);
	    			actionList.add(action);
	    			futures.add(session.persist(action).toCompletableFuture());
				}
	    		CompletableFuture<?>[] futuresArray = futures.toArray(new CompletableFuture<?>[0]);
	    		return CompletableFuture.allOf(futuresArray).thenApply(v -> actionList);
			})
		);
	}
	
	private ActionSeries createSeries(Workspace workspace, String defaultActionName) {
		ActionSeries result = new ActionSeries();
		result.setWorkspace(workspace);
		result.setDefaultActionName(defaultActionName);
		return result;
	}
	
	private Action createDefaultAction(Workspace workspace) {
		Action result = new Action();
		result.setWorkspace(workspace);
		result.setDate(DateUtils.getCurrentDayEpochUTC());
		workspace.getActions().add(result);
		return result;
	}
	
	private Action createNamedAction(Workspace workspace, String name) {
		Action result = createDefaultAction(workspace);
		result.setName(name);		
		return result;
	}
	
	private Action createActionFromSeries(ActionSeries series) {
		Action result = createNamedAction(series.getWorkspace(), series.getDefaultActionName());
		result.setSeries(series);
		series.getActions().add(result);
		return result;
	}
	
	
	

//	public Future<Action> createAction(Workspace workspace, JsonObject createSpecif) {
//		return hibernateFacade.withTransaction(session ->
//			createActionSeries(session, createSpecif).thenCompose(series -> {
//				Action newAction = new Action();
//				newAction.setWorkspace(workspace);
//				newAction.setName(createSpecif.getString("name"));
//				newAction.setDate(DateUtils.getCurrentDayEpochUTC());
//				if(series!=null) {
//					newAction.setSeries(series);
//					series.getActions().add(newAction);
//				}
//				return session.persist(newAction).thenApply(v -> {
//					workspace.getActions().add(newAction);
//					return newAction;
//				});
//			})
//		);
//	}
	
	
	

}
