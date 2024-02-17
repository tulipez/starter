package com.tulipez.starter.web.api;

import com.tulipez.starter.dao.UserDAO;
import com.tulipez.starter.model.StarterUser;
import com.tulipez.starter.util.RoutingContextUtils;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class PutUserHandler {

	private UserDAO userDAO;
	
	public PutUserHandler(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public void handle(RoutingContext context) {
		userDAO.saveUser(context.body().asJsonObject().mapTo(StarterUser.class))
			.onSuccess(starterUser -> RoutingContextUtils.endJson(context, JsonObject.mapFrom(starterUser)))
			.onFailure(err -> RoutingContextUtils.endError(context, err));
	}
	
}
