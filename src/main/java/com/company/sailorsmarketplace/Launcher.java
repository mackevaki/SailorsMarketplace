package com.company.sailorsmarketplace;

import com.company.sailorsmarketplace.rest.JerseyConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.PathResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
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
        final Server server = new Server(9998);

        HandlerList handlers = new HandlerList();

        final URL jarUrl = Launcher.class.getClassLoader().getResource("static-root/signup.html");
        URI staticRootUri = Objects.requireNonNull(jarUrl).toURI().resolve("./").normalize();

        // The filesystem paths we will map
//        Path homePath = new File(System.getProperty("user.home")).toPath().toRealPath();
        Path pwdPath = new File(System.getProperty("user.dir")).toPath().toRealPath();

        // Setup the basic application "context" for this application at "/"
        // This is also known as the handler tree (in jetty speak)
        ServletContextHandler basicContext = new ServletContextHandler(server, "/");
        basicContext.setBaseResource(new PathResource(pwdPath));

        // Fist, add special pathspec of "/home/" content mapped to the homePath
        ServletHolder holderHome = new ServletHolder("static-home", DefaultServlet.class);
        holderHome.setInitParameter("resourceBase", "../resources/main/");
        holderHome.setInitParameter("dirAllowed","true");
        // Use request pathInfo, don't calculate from contextPath
        holderHome.setInitParameter("pathInfoOnly","true");
        basicContext.addServlet(holderHome,"/res/*"); // must end in "/*" for pathInfo to work

     //   ServletContextHandler context = new ServletContextHandler(server, "/");

        ResourceConfig config = new JerseyConfig();
        ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(config));

//        ServletHolder jerseyServlet = context.addServlet(
//                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        basicContext.addServlet(jerseyServlet, "/rest/*");

        // Lastly, the default servlet for root content
        // It is important that this is last.
        String defName = "default"; // the important "default" name
        ServletHolder holderDef = new ServletHolder(defName, DefaultServlet.class);
        holderDef.setInitParameter("dirAllowed","true");
        holderDef.setInitParameter("resourceBase", staticRootUri.toASCIIString());
        basicContext.addServlet(holderDef,"/"); // the servlet spec "default url-pattern"
//        // Add Security Filter by the name
//        context.addFilter(
//                new FilterHolder( new DelegatingFilterProxy( "securityFilterChain" ) ),
//                "/*", EnumSet.allOf( DispatcherType.class )
//        );

//        handlers.addHandler(context);
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