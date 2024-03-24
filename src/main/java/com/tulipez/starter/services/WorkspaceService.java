package com.tulipez.starter.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.tulipez.starter.HibernateFacade;
import com.tulipez.starter.model.StarterUser;
import com.tulipez.starter.model.Workspace;

import io.vertx.core.Future;

//TODO gestion d'erreur
//TODO commentaires
//TODO tests

public class WorkspaceService {
	
	private HibernateFacade hibernateFacade;
	
	public WorkspaceService(HibernateFacade hibernateFacade) {
		this.hibernateFacade = hibernateFacade;
	}
	
	public Future<List<Workspace>> getWorkspaces(StarterUser user) {
		return hibernateFacade.withTransaction(session ->
			session
			.createQuery("select w from Workspace w where w.user = :user", Workspace.class)
			.setParameter("user", user)
        	.getResultList()
        	.thenCompose(workspaceList -> {
				if(workspaceList.isEmpty()) {
					Workspace workspace = new Workspace(user);
					return session.persist(workspace).thenApply(v -> {
						workspaceList.add(workspace);
						return workspaceList;
					});
				}
				else {
					return CompletableFuture.completedStage(workspaceList);
				}
			})
		);
	}
	
	public Future<Workspace> updateWorkspace(Workspace workspace) {
		return hibernateFacade.withTransaction(
	        session -> session.merge(workspace)
		);
	}
	
}
