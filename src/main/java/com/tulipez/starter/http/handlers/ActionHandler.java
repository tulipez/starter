package com.tulipez.starter.http.handlers;

import com.tulipez.starter.model.Workspace;
import com.tulipez.starter.services.ActionService;
import com.tulipez.starter.util.RoutingContextUtils;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class ActionHandler {

	private ActionService actionService;
	
	public ActionHandler(ActionService actionService) {
		this.actionService = actionService;
	}
	
	public void handlePost(RoutingContext context) {
		Workspace selectedWorkspace = (Workspace) context.user().attributes().getValue("selected-workspace");
		if(selectedWorkspace==null) context.fail(403);
		else {
			JsonObject createSpecif = context.body().asJsonObject();
			actionService.createAction(selectedWorkspace, createSpecif)
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
		actionService.updateAction(updateSpecif)
		.onSuccess(a -> RoutingContextUtils.endJson(context, JsonObject.mapFrom(a)))
		.onFailure(err -> RoutingContextUtils.endError(context, err));
	}
	
}
