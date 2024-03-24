package com.tulipez.starter.http;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class SslRedirectServer {

	private Vertx vertx;
	
	public SslRedirectServer(Vertx vertx) {
		this.vertx = vertx;
	}
	
	public Future<HttpServer> start() {
		return vertx.createHttpServer()
		.requestHandler(r -> {
			String absoluteURI = r.absoluteURI();
			if(absoluteURI==null) {
				r.response().setStatusCode(500).end();
			}
			else {
				r.response()
				.setStatusCode(301)
				.putHeader("Location", absoluteURI.replace("http", "https"))
				.end();
			}
		})
		.listen(80)
		.onSuccess(v -> System.out.println("SslRedirectServer started"));
	}
}
