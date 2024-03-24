package com.tulipez.starter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import com.tulipez.starter.common.log.StdLogger;
import com.tulipez.starter.http.SslRedirectServer;
import com.tulipez.starter.http.handlers.ActionHandler;
import com.tulipez.starter.http.handlers.ApiHandler;
import com.tulipez.starter.http.handlers.GoogleAuthHandler;
import com.tulipez.starter.http.handlers.SubDomainHandler;
import com.tulipez.starter.http.handlers.WorkspaceHandler;
import com.tulipez.starter.services.ActionService;
import com.tulipez.starter.services.UserService;
import com.tulipez.starter.services.WorkspaceService;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.PemKeyCertOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.FaviconHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

public class MainVerticle extends AbstractVerticle {
	
	public @Override void start(Promise<Void> startPromise) throws Exception {

		ConfigRetriever
		.create(vertx, new ConfigRetrieverOptions().addStore(new ConfigStoreOptions()
			.setFormat("properties")
			.setType("file")
			.setConfig(new JsonObject().put("path", "config/config.properties"))))
		.getConfig()
		.onFailure(err -> err.printStackTrace())
		.onSuccess(config -> {
			
			//// init TimeZone
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			
			//// init log
			File consoleLogFile = new File(new File("log"), "console.log");
			StdLogger stdLogger = new StdLogger();
			stdLogger.setMode(config.getString("logger.mode"));
			stdLogger.setFile(consoleLogFile);
			stdLogger.start();
			
			//// create router
			Router router = Router.router(vertx);
			
			//// create dependencies
			WebClient webClient = WebClient.create(vertx);
			
			HibernateFacade hibernateFacade = new HibernateFacade(vertx, config);
			UserService userService = new UserService(hibernateFacade);
			WorkspaceService workspaceService = new WorkspaceService(hibernateFacade);
			ActionService actionService = new ActionService(hibernateFacade);

			ApiHandler apiHandler = new ApiHandler();
			GoogleAuthHandler googleAuthHandler = new GoogleAuthHandler(vertx, router, config);
			WorkspaceHandler workspaceHandler = new WorkspaceHandler(workspaceService, userService, webClient);
			ActionHandler actionHandler = new ActionHandler(actionService);
			
			//// create routes
			router.route().handler(SubDomainHandler.create(config.getString("server.host"))::handle);
			router.route().handler(BodyHandler.create());
			router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)).setCookieMaxAge(2147483647));
			router.route().handler(FaviconHandler.create(vertx, "webroot/favicon.png"));

			router.get("/login").handler(googleAuthHandler.createLoginHandler());
			router.get("/login").handler(context -> context.redirect("/"));
			router.get("/logout").handler(googleAuthHandler::handleLogout);
			
			router.route("/api/*").handler(apiHandler::handle).failureHandler(apiHandler::failureHandle);
			router.get("/api/workspace").handler(workspaceHandler::handleGet);
			router.put("/api/workspace").handler(workspaceHandler::handlePut);
			router.post("/api/action").handler(actionHandler::handlePost);
			router.put("/api/action").handler(actionHandler::handlePut);
			
			router.get("/*").handler(StaticHandler.create().setCachingEnabled(false));

			//// startList
			List<Future<?>> startList = new ArrayList<Future<?>>();
			
			// SslRedirectServer
			if("https".equals(config.getString("server.scheme"))) {
				startList.add(new SslRedirectServer(vertx).start());
			}
			
			// HttpServer
			HttpServerOptions httpServerOptions = new HttpServerOptions();
			if("https".equals(config.getString("server.scheme"))) {
				httpServerOptions.setSsl(true).setKeyCertOptions(new PemKeyCertOptions()
						.setKeyPath(config.getString("server.ssl.key-path"))
						.setCertPath(config.getString("server.ssl.cert-path")));
			}

			startList.add(vertx.createHttpServer(httpServerOptions)
					.requestHandler(router).listen(config.getInteger("server.port"))
					.onSuccess(v -> System.out.println("HttpServer started")));
			
			// Hibernate
			startList.add(hibernateFacade.init()
					.onSuccess(v -> System.out.println("Hibernate initialized")));
			
			// scheduler
			ScheduleDailyVerticle schedulerVerticle = new ScheduleDailyVerticle(() -> {
				actionService.createActionsFromAllSeries();
			});
			
			startList.add(vertx.deployVerticle(schedulerVerticle));
			
			//// return
			Future.all(startList).onComplete(ar -> {
				if (ar.succeeded()) {
					startPromise.complete();
				} else {
					startPromise.fail(ar.cause());
				}
			});
			
		});
	}

}
