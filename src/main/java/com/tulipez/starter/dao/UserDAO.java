package com.tulipez.starter.dao;

import com.tulipez.starter.model.StarterUser;
import com.tulipez.starter.util.JacksonUtils;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

//TODO gestion d'erreur
//TODO commentaires
//TODO tests

public class UserDAO {
	
	private HibernateFacade hibernateFacade;
	
	public UserDAO(HibernateFacade hibernateFacade) {
		this.hibernateFacade = hibernateFacade;
	}
	
	public Future<StarterUser> getUser(JsonObject userSpecif) {
		return hibernateFacade.withTransaction(session -> 
			session
			.createQuery("select u from StarterUser u where u.sub = :sub", StarterUser.class)
			.setParameter("sub", userSpecif.getString("sub"))
			.getSingleResultOrNull()
			.thenCompose(user -> {
				if(user==null) {
					StarterUser newUser = userSpecif.mapTo(StarterUser.class);
					return session.persist(newUser).thenApply(v -> newUser);	
				}
				else {
					return session.merge(JacksonUtils.updatePojo(user, userSpecif));
				}
			})
		);
	}
	
}
