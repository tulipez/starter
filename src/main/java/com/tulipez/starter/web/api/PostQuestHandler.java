package com.tulipez.starter.web.api;

import com.tulipez.starter.dao.HibernateDAO;
import com.tulipez.starter.model.Quest;

import io.vertx.core.AsyncResult;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class PostQuestHandler {

	private HibernateDAO hibernateDAO;
	
	public PostQuestHandler(HibernateDAO hibernateDAO) {
		this.hibernateDAO = hibernateDAO;
	}
	
	public void handle(RoutingContext context) {
		JsonObject body = context.body().asJsonObject();
		
		String name = body.getString("name");
		if(name==null || name.equals("")) name = "new quest";
		
		Quest quest = new Quest();
		quest.setName(name);
		
		hibernateDAO.persistQuest(quest).onComplete((AsyncResult<Void> ar) -> {
			if (ar.succeeded()) {
				JsonObject jsonResult = new JsonObject()
						.put("id", quest.getId())
						.put("name", quest.getName());
				context.response()
		                .putHeader("Content-Type", "application/json; charset=UTF8")
		                .end(jsonResult.encodePrettily());
			} else {
				ar.cause().printStackTrace();
			}
		});
	}
	
}
