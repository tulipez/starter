package com.tulipez.starter.http.handlers;

import com.tulipez.starter.dao.ActionDAO;
import com.tulipez.starter.model.Workspace;
import com.tulipez.starter.util.RoutingContextUtils;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class ActionHandler {

	private ActionDAO actionDAO;
	
	public ActionHandler(ActionDAO actionDAO) {
		this.actionDAO = actionDAO;
	}
	
	public void handlePost(RoutingContext context) {
		Workspace selectedWorkspace = (Workspace) context.user().attributes().getValue("selected-workspace");
		if(selectedWorkspace==null) context.fail(403);
		else {
			JsonObject createSpecif = context.body().asJsonObject();
			actionDAO.createAction(selectedWorkspace, createSpecif)
			.onSuccess(action -> {
				RoutingContextUtils.endJson(context, JsonObject.mapFrom(action));
			})
			.onFailure(err -> {
				RoutingContextUtils.endError(context, err);
			});
		}
	}
	
	public void handlePut(RoutingContext context) {
		JsonObject updateSpecif = context.body().asJsonObject();
		actionDAO.updateAction(updateSpecif)
		.onSuccess(a -> RoutingContextUtils.endJson(context, JsonObject.mapFrom(a)))
		.onFailure(err -> RoutingContextUtils.endError(context, err));
	}
	
}
