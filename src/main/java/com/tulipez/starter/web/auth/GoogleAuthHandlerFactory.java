package com.tulipez.starter.web.auth;

import java.util.List;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.auth.oauth2.providers.GoogleAuth;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.ext.web.handler.OAuth2AuthHandler;

public class GoogleAuthHandlerFactory {
	
	private Vertx vertx;
	private Router router;
	private JsonObject config;
	private OAuth2Auth authProvider;
	
	public GoogleAuthHandlerFactory(Vertx vertx, Router router, JsonObject config) {
		this.vertx = vertx;
		this.router = router;
		this.config = config;
		this.authProvider = GoogleAuth.create(vertx, config.getString("auth.google.clientId")
												   , config.getString("auth.google.clientSecret"));
	}
	
	public void handleLogout(RoutingContext context) {
		authProvider.revoke(context.user(), "access_token")
		.onSuccess(v -> {
			context.clearUser();
			context.next();
		})
		.onFailure(err -> {
			context.response().end("ERROR revoke access_token : " + err.getMessage());
			err.printStackTrace();
			context.next();
		});
	}
	
	public OAuth2AuthHandler getHandler() {
		return OAuth2AuthHandler.create(vertx, authProvider, config.getString("server.scheme") + "://" + config.getString("server.host") + ":" + config.getString("server.port") + config.getString("auth.google.callback.path"))
		.setupCallback(router.route(config.getString("auth.google.callback.path")))
		.withScopes(List.of("email", "profile", "openid"));
	}
}
