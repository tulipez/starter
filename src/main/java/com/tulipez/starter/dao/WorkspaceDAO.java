package com.tulipez.starter.dao;

import com.tulipez.starter.model.Workspace;

import io.vertx.core.Future;

//TODO gestion d'erreur
//TODO commentaires
//TODO tests

public class WorkspaceDAO {
	
	private HibernateFacade hibernateFacade;
	
	public WorkspaceDAO(HibernateFacade hibernateFacade) {
		this.hibernateFacade = hibernateFacade;
	}
	
	public Future<Workspace> save(Workspace workspace) {
		return hibernateFacade.withTransaction(
	        session -> session.merge(workspace)
		);
	}
	
}
