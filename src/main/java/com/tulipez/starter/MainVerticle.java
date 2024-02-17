package com.tulipez.starter;

import java.io.File;

import com.tulipez.starter.common.log.StdLogger;
import com.tulipez.starter.dao.HibernateFacade;
import com.tulipez.starter.dao.UserDAO;
import com.tulipez.starter.web.api.ApiHandler;
import com.tulipez.starter.web.api.GetUserHandler;
import com.tulipez.starter.web.api.PostQuestHandler;
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
	
	//TODO restructurer
	
	public @Override void start(Promise<Void> startPromise) throws Exception {

		ConfigStoreOptions configStoreOptions = new ConfigStoreOptions()
				.setFormat("properties")
				.setType("file")
				.setConfig(new JsonObject().put("path", "config/config.properties"));
		
		ConfigRetriever configRetriever = ConfigRetriever
				.create(vertx, new ConfigRetrieverOptions().addStore(configStoreOptions));

		configRetriever.getConfig()
		.onFailure(err -> err.printStackTrace())
		.onSuccess(config -> {
			
			File consoleLogFile = new File(new File("log"), "console.log");
			StdLogger stdLogger = new StdLogger();
			stdLogger.setMode(config.getString("logger.mode"));
			stdLogger.setFile(consoleLogFile);
			stdLogger.start();
			
			Router router = Router.router(vertx);
			
			//redirect www. ftp. etc... to server.host
			//TODO amelioration : prendre en compte un server.host=www.domaine et un absoluteURI=domaine
			String serverHost = config.getString("server.host");
			String serverHostKey = "://" + serverHost;
			String regex = "://.*" + serverHost;
			router.route().handler(context -> {
				String absoluteURI = context.request().absoluteURI();
				if(!absoluteURI.contains(serverHostKey)) {
					context.redirect(absoluteURI.replaceAll(regex, serverHostKey));
				}
				else context.next();
			});
			
			router.route().handler(BodyHandler.create());
			router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)).setCookieMaxAge(2147483647));
			router.route().handler(FaviconHandler.create(vertx, "webroot/favicon.png"));

			WebClient webClient = WebClient.create(vertx);
			HibernateFacade hibernateFacade = new HibernateFacade(vertx, config);
			
			ApiHandler apiHandler = new ApiHandler();
			router.route("/api/*").handler(apiHandler::handle).failureHandler(apiHandler::failureHandle);
			router.post("/api/quest").handler(new PostQuestHandler(hibernateFacade)::handle);
			
			UserDAO userDAO = new UserDAO(hibernateFacade);
			router.get("/api/user").handler(new GetUserHandler(webClient, userDAO)::handle);

			GoogleAuthHandlerFactory googleAuthHandlerFactory = new GoogleAuthHandlerFactory(vertx, router, config);
			router.get("/api/logout").handler(googleAuthHandlerFactory::handleLogout);
			router.get("/auth").handler(googleAuthHandlerFactory.getHandler());
			router.get("/auth").handler(context -> context.redirect("/"));
			
//			router.get("/").handler(context -> {
//				if (context.user() == null)
//					context.redirect("/login.html");
//				else
//					context.next();
//			});

			router.get("/*").handler(StaticHandler.create().setCachingEnabled(false));

			// httpServer
			HttpServerOptions httpServerOptions = new HttpServerOptions();
			if("https".equals(config.getString("server.scheme"))) {
				
				httpServerOptions.setSsl(true).setKeyCertOptions(new PemKeyCertOptions()
						.setKeyPath(config.getString("server.ssl.key-path"))
						.setCertPath(config.getString("server.ssl.cert-path")));
				
				//create redirect SSL Server
				var redirectSSLServer = vertx.createHttpServer();
				redirectSSLServer
				.requestHandler(r -> {
					r.response()
					.setStatusCode(301)
					.putHeader("Location", r.absoluteURI().replace("http", "https"))
					.end();
				})
				.listen(80) //TODO mettre dans la config
				.onSuccess(v -> System.out.println("redirectSSLServer.complete"));
			}

			Future<HttpServer> startHttpServer = vertx.createHttpServer(httpServerOptions)
					.requestHandler(router).listen(config.getInteger("server.port"))
					.onSuccess(v -> System.out.println("startHttpServer.complete"));
			
			Future<Void> initHibernate = hibernateFacade.init()
					.onSuccess(v -> System.out.println("startHibernate.complete"));

			Future.all(initHibernate, startHttpServer).onComplete(ar2 -> {
				if (ar2.succeeded()) {
					System.out.println("startPromise.complete");
					startPromise.complete();
				} else {
					startPromise.fail(ar2.cause());
				}
			});
			
		});
	}

}
