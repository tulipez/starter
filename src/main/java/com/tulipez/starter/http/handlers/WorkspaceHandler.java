package com.tulipez.starter.http.handlers;

import com.tulipez.starter.dao.UserDAO;
import com.tulipez.starter.dao.WorkspaceDAO;
import com.tulipez.starter.model.Workspace;
import com.tulipez.starter.util.JacksonUtils;
import com.tulipez.starter.util.RoutingContextUtils;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;

public class WorkspaceHandler {

	private WorkspaceDAO workspaceDAO;
	private WebClient webClient;
	private UserDAO userDAO;
	
	public WorkspaceHandler(WorkspaceDAO workspaceDAO, UserDAO userDAO, WebClient webClient) {
		this.workspaceDAO = workspaceDAO;
		this.userDAO = userDAO;
		this.webClient = webClient;
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
	
	public void handleGet(RoutingContext context) {
		User vertxUser = context.user();
		requestUserSpecif(vertxUser)
		.compose(userSpecif -> userDAO.getUser(userSpecif))
		.compose(starterUser -> {
			return workspaceDAO.getWorkspaces(starterUser);
		})
		.onSuccess(workspaceList -> {
			
			// possibilité d'avoir plusieurs workspaces par user
			Workspace currentWorkspace = workspaceList.get(0);
			vertxUser.attributes().put("selected-workspace", currentWorkspace);
			
			RoutingContextUtils.endJson(context, JsonObject.mapFrom(currentWorkspace));
		})
		.onFailure(err -> RoutingContextUtils.endError(context, err));
	}
	
	public void handlePut(RoutingContext context) {
		Workspace selectedWorkspace = (Workspace) context.user().attributes().getValue("selected-workspace");
		if(selectedWorkspace==null) {
			context.fail(403);
		}
		else {
			JsonObject updateSpecif = context.body().asJsonObject();
			updateSpecif.remove("actions");
			workspaceDAO.updateWorkspace(JacksonUtils.updatePojo(selectedWorkspace, updateSpecif))
			.onSuccess(w -> RoutingContextUtils.endJson(context, JsonObject.mapFrom(w)))
			.onFailure(err -> RoutingContextUtils.endError(context, err));
		}
	}
}
