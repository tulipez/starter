package com.tulipez.starter.dao;

import com.tulipez.starter.model.StarterUser;

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
		return hibernateFacade
		.findByAttributeValue(StarterUser.class , "sub" , userSpecif.getString("sub"))
		.compose(user -> {
			return user==null ?
				hibernateFacade.persist(userSpecif.mapTo(StarterUser.class)):
				hibernateFacade.merge(user.updateFrom(userSpecif));
		});
	}
	
	public Future<StarterUser> saveUser(StarterUser starterUser) {
		return hibernateFacade.merge(starterUser);
	}
	
}
