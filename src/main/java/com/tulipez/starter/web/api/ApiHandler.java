package com.tulipez.starter.web.api;

import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;

public class ApiHandler {
	
	public ApiHandler() {
	}

	public void handle(RoutingContext context) {
		User user = context.user();
		if(user==null) {
			context.fail(403);
		}
		else {
			context.next();
		}
	}
	
	public void failureHandle(RoutingContext context) {
		Throwable failure = context.failure();
		if(failure!=null) failure.printStackTrace();
		int statusCode = context.statusCode();
		context.response()
			.setStatusCode(statusCode)
			.end(failure!=null ? ("Internal Server Error : " + failure.getMessage()) : ("Error " + statusCode));
	}
	
}
