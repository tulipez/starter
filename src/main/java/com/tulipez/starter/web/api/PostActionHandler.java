package com.tulipez.starter.web.api;

import com.tulipez.starter.dao.ActionDAO;
import com.tulipez.starter.model.StarterUser;
import com.tulipez.starter.util.RoutingContextUtils;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class PostActionHandler {

	private ActionDAO actionDAO;
	
	public PostActionHandler(ActionDAO actionDAO) {
		this.actionDAO = actionDAO;
	}
	
	public void handle(RoutingContext context) {
		StarterUser starterUser = (StarterUser) context.user().attributes().getValue("starter-user");
		if(starterUser==null) context.fail(403);
		else {
			JsonObject createSpecif = context.body().asJsonObject();
			actionDAO.createAction(starterUser.getWorkspace(), createSpecif)
			.onSuccess(action -> RoutingContextUtils.endJson(context, JsonObject.mapFrom(action)))
			.onFailure(err -> RoutingContextUtils.endError(context, err));
		}
	}
	
}
