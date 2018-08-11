package com.company.sailorsmarketplace;

import com.company.sailorsmarketplace.rest.AccountsResource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.PathResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Starts jetty-server on the specified port
 */
public class Launcher {
    private static final Logger log = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) throws IOException, URISyntaxException {
//        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        context.setContextPath("/rest/");

        final Server server = new Server(9998);
        //server.setHandler(context);///

        HandlerList handlers = new HandlerList();

        System.out.println((Launcher.class).getClassLoader().getResource("static-root/signup.html"));
        final URL jarUrl = Launcher.class.getClassLoader().getResource("static-root/signup.html");
        URI staticRootUri = Objects.requireNonNull(jarUrl).toURI().resolve("./").normalize();
        System.out.println(staticRootUri + "          !!!!!");

        // The filesystem paths we will map
//        Path homePath = new File(System.getProperty("user.home")).toPath().toRealPath();
        Path pwdPath = new File(System.getProperty("user.dir")).toPath().toRealPath();
//        System.out.println(homePath + "        pwd!!!!!");

        // Setup the basic application "context" for this application at "/"
        // This is also known as the handler tree (in jetty speak)
        ServletContextHandler basicContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        basicContext.setContextPath("/");
        basicContext.setBaseResource(new PathResource(pwdPath));
        server.setHandler(basicContext);


        // Fist, add special pathspec of "/home/" content mapped to the homePath
        ServletHolder holderHome = new ServletHolder("static-home", DefaultServlet.class);
        holderHome.setInitParameter("resourceBase", "file:/home/mackevaki/SailorsMarketplace/build/resources/main/");
        holderHome.setInitParameter("dirAllowed","true");
        // Use request pathInfo, don't calculate from contextPath
        holderHome.setInitParameter("pathInfoOnly","true");
        basicContext.addServlet(holderHome,"/res/*"); // must end in "/*" for pathInfo to work

        ServletContextHandler context = new ServletContextHandler(server, "/rest");

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                AccountsResource.class.getCanonicalName());


        // Lastly, the default servlet for root content
        // It is important that this is last.
        String defName = "default"; // the important "default" name
        ServletHolder holderDef = new ServletHolder(defName, DefaultServlet.class);
        holderDef.setInitParameter("dirAllowed","true");
        holderDef.setInitParameter("resourceBase", staticRootUri.toASCIIString());
        basicContext.addServlet(holderDef,"/"); // the servlet spec "default url-pattern"
//        // Add Spring Security Filter by the name
//        context.addFilter(
//                new FilterHolder( new DelegatingFilterProxy( "springSecurityFilterChain" ) ),
//                "/*", EnumSet.allOf( DispatcherType.class )
//        );



//        ResourceHandler resourceHandler = new ResourceHandler();
//        resourceHandler.setBaseResource(Resource.newResource("file:/home/mackevaki/SailorsMarketplace/build/resources/main/"));
//        handlers.addHandler(resourceHandler);

//        ResourceConfig config = new JerseyConfig();
//        ServletHolder servlet = new ServletHolder(new ServletContainer(config));//"SignationUpController", SignationUpController.class);
//        ServletContextHandler context = new ServletContextHandler(server, "/rest/");
//        servlet.setInitParameter("dirAllowed", "true");
//        context.addServlet(servlet, "/*");
        handlers.addHandler(context);
        handlers.addHandler(basicContext);
//
        server.setHandler(handlers);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            log.error("Error during server run");
        } finally {
            server.destroy();
        }
    }
}