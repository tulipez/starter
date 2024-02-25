package com.tulipez.starter.web.api;

import com.tulipez.starter.dao.WorkspaceDAO;
import com.tulipez.starter.model.StarterUser;
import com.tulipez.starter.model.Workspace;
import com.tulipez.starter.util.JacksonUtils;
import com.tulipez.starter.util.RoutingContextUtils;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class PutWorkspaceHandler {

	private WorkspaceDAO workspaceDAO;
	
	public PutWorkspaceHandler(WorkspaceDAO workspaceDAO) {
		this.workspaceDAO = workspaceDAO;
	}
	
	public void handle(RoutingContext context) {
		StarterUser starterUser = (StarterUser) context.user().attributes().getValue("starter-user");
		if(starterUser==null) {
			context.fail(403);
		}
		else {
			JsonObject updateSpecif = context.body().asJsonObject();
			Workspace workspaceUpdated = JacksonUtils.updatePojo(starterUser.getWorkspace(), updateSpecif);
			
			workspaceDAO.save(workspaceUpdated)
			.onSuccess(w -> RoutingContextUtils.endJson(context, JsonObject.mapFrom(w)))
			.onFailure(err -> RoutingContextUtils.endError(context, err));
		}
	}
	
}
