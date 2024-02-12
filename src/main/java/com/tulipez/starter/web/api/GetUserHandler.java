package com.tulipez.starter.web.api;

import java.util.concurrent.CompletionStage;

import com.tulipez.starter.dao.HibernateDAO;
import com.tulipez.starter.model.StarterUser;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;

public class GetUserHandler {

	private WebClient client;
	private HibernateDAO hibernateDAO;
	
	public GetUserHandler(WebClient client, HibernateDAO hibernateDAO) {
		this.client = client;
		this.hibernateDAO = hibernateDAO;
	}
	/*
	private void persisteUser(Handler<Void> requestHandler) {
		JsonObject body = context.body().asJsonObject();
		
		String name = body.getString("name");
		if(name==null || name.equals("")) name = "new quest";
		
		StarterUser user = new StarterUser();
		user.setName(name);
		
		hibernateDAO.persist(user).onComplete((AsyncResult<Void> ar) -> {
			if (ar.succeeded()) {
//				JsonObject jsonResult = new JsonObject()
//						.put("id", quest.getId())
//						.put("name", quest.getName());
//				context.response()
//		                .putHeader("Content-Type", "application/json; charset=UTF8")
//		                .end(jsonResult.encodePrettily());
			} else {
				ar.cause().printStackTrace();
			}
		});
	}
	
	public Future<Void> persist(Object object) {
		CompletionStage<Void> insertionResult = hibernateSessionFactory.withTransaction((session, transaction) -> {
			CompletionStage<Void> resultPersist = session.persist(object);
			return resultPersist;
        });
		return Future.fromCompletionStage(insertionResult);
	}*/
	
//	private Future<Void> debug(User user) {
//		
//		JsonObject principal = user.principal();
//		String accessToken = principal.getString("access_token");
//		String url = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + accessToken;
//		client.getAbs(url)
//		.as(BodyCodec.jsonObject())
//		.send()
//		.onSuccess(res -> {
//			
//			JsonObject body = res.body();
//			user.attributes().put("profile", body);
//			
//			context.response()
//				.putHeader("Content-Type", "application/json; charset=UTF8")
//				.end(body.encodePrettily());
//			
//		})
//		.onFailure(err -> {
//			context.response().end("ERROR googleapis userinfo : " + err.getMessage() + " url=" + url);
//		});
//		
//	}

	public void handle(RoutingContext context) {
		
		User user = context.user();
		JsonObject profile = user.attributes().getJsonObject("profile");
		if(profile==null) {
			
//			debug
			
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
