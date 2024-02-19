package com.tulipez.starter.web.api;

import com.tulipez.starter.dao.UserDAO;
import com.tulipez.starter.util.RoutingContextUtils;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;

public class GetUserHandler {

	private WebClient webClient;
	private UserDAO userDAO;
	
	public GetUserHandler(WebClient webClient, UserDAO userDAO) {
		this.webClient = webClient;
		this.userDAO = userDAO;
	}
	
	private Future<JsonObject> requestUserSpecif(User user) {
		String accessToken = user.principal().getString("access_token");
		String url = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + accessToken;
		return webClient
				.getAbs(url)
				.as(BodyCodec.jsonObject())
				.send()
				.compose(response -> Future.succeededFuture(response.body()));
	}
	
	public void handle(RoutingContext context) {
		requestUserSpecif(context.user())
			.compose(userSpecif -> userDAO.getUser(userSpecif))
			.onSuccess(starterUser -> {
				context.put("starter-user", starterUser);
				RoutingContextUtils.endJson(context, JsonObject.mapFrom(starterUser));
			})
			.onFailure(err -> RoutingContextUtils.endError(context, err));
	}
	
}
