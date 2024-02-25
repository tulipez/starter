package com.tulipez.starter.dao;

import com.tulipez.starter.model.Action;
import com.tulipez.starter.model.Workspace;

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
	
	public Future<Action> getAction(JsonObject actionSpecif) {
		return hibernateFacade.withTransaction(session -> 
			session.find(Action.class, actionSpecif.getInteger("id"))
		);
	}
	
	public Future<Action> createAction(Workspace workspace, JsonObject createSpecif) {
		return hibernateFacade.withTransaction(session -> {
			Action newAction = createSpecif.mapTo(Action.class);
			newAction.setWorkspace(workspace);
			return session.persist(newAction).thenApply(v -> {
				workspace.addAction(newAction);
				return newAction;
			});
		});
	}
	
}
