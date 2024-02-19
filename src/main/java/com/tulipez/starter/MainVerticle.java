package com.tulipez.starter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tulipez.starter.common.log.StdLogger;
import com.tulipez.starter.dao.HibernateFacade;
import com.tulipez.starter.dao.UserDAO;
import com.tulipez.starter.web.SslRedirectServer;
import com.tulipez.starter.web.api.ApiHandler;
import com.tulipez.starter.web.api.GetUserHandler;
import com.tulipez.starter.web.api.PostQuestHandler;
import com.tulipez.starter.web.api.PutUserHandler;
import com.tulipez.starter.web.api.SubDomainHandler;
import com.tulipez.starter.web.auth.GoogleAuthHandlerFactory;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
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
			ApiHandler apiHandler = new ApiHandler();
			UserDAO userDAO = new UserDAO(hibernateFacade);
			GoogleAuthHandlerFactory googleAuthHandlerFactory = new GoogleAuthHandlerFactory(vertx, router, config);
			
			//// create routes
			router.route().handler(SubDomainHandler.create(config.getString("server.host"))::handle);
			router.route().handler(BodyHandler.create());
			router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)).setCookieMaxAge(2147483647));
			router.route().handler(FaviconHandler.create(vertx, "webroot/favicon.png"));
			router.route("/api/*").handler(apiHandler::handle).failureHandler(apiHandler::failureHandle);
			router.post("/api/quest").handler(new PostQuestHandler(hibernateFacade)::handle);
			router.get("/api/user").handler(new GetUserHandler(webClient, userDAO)::handle);
			router.put("/api/user").handler(new PutUserHandler(userDAO)::handle);

			router.get("/login").handler(googleAuthHandlerFactory.getHandler());
			router.get("/login").handler(context -> context.redirect("/"));
			
			router.get("/logout").handler(googleAuthHandlerFactory::handleLogout);
			router.get("/logout").handler(context -> context.redirect("/"));
			
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
