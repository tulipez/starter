package com.tulipez.starter.web.api;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;

public class GetUserHandler {

	private WebClient client;
	
	public GetUserHandler(WebClient client) {
		this.client = client;
	}

	public void handle(RoutingContext context) {
		
		User user = context.user();
		JsonObject profile = user.attributes().getJsonObject("profile");
		if(profile==null) {
			JsonObject principal = user.principal();
			String accessToken = principal.getString("access_token");
			String url = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + accessToken;
			client.getAbs(url)
			.as(BodyCodec.jsonObject())
			.send()
			.onSuccess(res -> {
				
				JsonObject body = res.body();
				user.attributes().put("profile", body);
				
				context.response()
					.putHeader("Content-Type", "application/json; charset=UTF8")
					.end(body.encodePrettily());
				
			})
			.onFailure(err -> {
				context.response().end("ERROR googleapis userinfo : " + err.getMessage() + " url=" + url);
			});
		}
		else {
			context.response()
				.putHeader("Content-Type", "application/json; charset=UTF8")
				.end(profile.encodePrettily());
		}
		
	}
	
}
