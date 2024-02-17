package com.tulipez.starter.util;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class RoutingContextUtils {

	public static void endJson(RoutingContext context, JsonObject jsonObject) {
		context.response()
		.putHeader("Content-Type", "application/json; charset=UTF8")
		.end(jsonObject.encode());
	}
	
	public static void endError(RoutingContext context, Throwable error) {
		error.printStackTrace();
		context.response().end("Une erreur est survenue : " + error.getMessage());
	}
	
}
