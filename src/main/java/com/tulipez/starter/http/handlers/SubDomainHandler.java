package com.tulipez.starter.http.handlers;

import io.vertx.ext.web.RoutingContext;

public class SubDomainHandler {
	
	private String hostKey;
	private String regex;
	
	public static SubDomainHandler create(String host) {
		return new SubDomainHandler(host);
	}
	
	public SubDomainHandler(String host) {
		this.hostKey = "://" + host;
		this.regex = "://.*" + host;
	}
	
	public void handle(RoutingContext context) {
		String absoluteURI = context.request().absoluteURI();
		if(absoluteURI==null) {
			context.fail(403);
		}
		else {
			if(!absoluteURI.contains(hostKey)) {
				context.redirect(absoluteURI.replaceAll(regex, hostKey));
			}
			else context.next();
		}
	}
		
}
