package com.tulipez.starter.web.api;

import com.tulipez.starter.dao.HibernateFacade;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class PostQuestHandler {

	private HibernateFacade hibernateDAO;
	
	public PostQuestHandler(HibernateFacade hibernateDAO) {
		this.hibernateDAO = hibernateDAO;
	}
	
	public void handle(RoutingContext context) {
		JsonObject body = context.body().asJsonObject();
		
		String name = body.getString("name");
		if(name==null || name.equals("")) name = "new quest";
		
//		hibernateDAO.persist(quest).onComplete((AsyncResult<Void> ar) -> {
//			if (ar.succeeded()) {
//				JsonObject jsonResult = new JsonObject()
//						.put("id", quest.getId())
//						.put("name", quest.getName());
//				context.response()
//		                .putHeader("Content-Type", "application/json; charset=UTF8")
//		                .end(jsonResult.encodePrettily());
//			} else {
//				ar.cause().printStackTrace();
//			}
//		});
	}
	
}
