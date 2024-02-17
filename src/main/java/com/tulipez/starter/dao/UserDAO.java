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
	
	//TODO automatiser
	private StarterUser update(StarterUser starterUser, JsonObject userSpecif) {
		if(userSpecif.containsKey("name")) starterUser.setName(userSpecif.getString("name"));
		if(userSpecif.containsKey("given_name")) starterUser.setGiven_name(userSpecif.getString("given_name"));
		if(userSpecif.containsKey("family_name")) starterUser.setFamily_name(userSpecif.getString("family_name"));
		if(userSpecif.containsKey("picture")) starterUser.setPicture(userSpecif.getString("picture"));
		if(userSpecif.containsKey("email")) starterUser.setEmail(userSpecif.getString("email"));
		if(userSpecif.containsKey("email_verified")) starterUser.setEmail_verified(userSpecif.getBoolean("email_verified"));
		if(userSpecif.containsKey("locale")) starterUser.setLocale(userSpecif.getString("locale"));
		return starterUser;
	}
	
	public Future<StarterUser> get(JsonObject userSpecif) {
		return hibernateFacade
		.findByAttributeValue(StarterUser.class , "sub" , userSpecif.getString("sub"))
		.compose(user -> {
			return user==null ?
				hibernateFacade.persist(userSpecif.mapTo(StarterUser.class)):
				hibernateFacade.merge(update(user, userSpecif));
		});
	}
	
}
