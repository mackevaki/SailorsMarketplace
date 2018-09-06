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
    public static final int SERVER_PORT = 9998;
    private static Server server;

    public static void main(String[] args) throws Exception {
        runWebServer();
    }

    public static void runWebServer() throws Exception {
        log.info("Starting internal jetty server");

        try {
            startServer();
            server.join();
        } finally {
            server.destroy();
        }
    }

    public static void startServer() throws Exception {
        server = new Server(SERVER_PORT);
        HandlerList handlers = new HandlerList();

        final URL jarUrl = Launcher.class.getClassLoader().getResource("static-root");
        URI resourcesUri = Objects.requireNonNull(jarUrl).toURI().resolve("./").normalize();

        Path pwdPath = new File(System.getProperty("user.dir")).toPath().toRealPath();

        ServletContextHandler basicContext = new ServletContextHandler(server, "/");
        basicContext.setBaseResource(new PathResource(pwdPath));

        ServletHolder holderHome = new ServletHolder("static-home", DefaultServlet.class);
        holderHome.setInitParameter("resourceBase", resourcesUri.toASCIIString());
        holderHome.setInitParameter("dirAllowed","true");
        // Use request pathInfo, don't calculate from contextPath
        holderHome.setInitParameter("pathInfoOnly","true");
        basicContext.addServlet(holderHome,"/res/*"); // must end in "/*" for pathInfo to work

        ResourceConfig config = new JerseyConfig();
        ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(config));

        jerseyServlet.setInitOrder(0);

        basicContext.addServlet(jerseyServlet, "/rest/*");

        String defName = "default"; // the important "default" name
        ServletHolder holderDef = new ServletHolder(defName, DefaultServlet.class);
        holderDef.setInitParameter("dirAllowed","true");
        holderDef.setInitParameter("resourceBase", resourcesUri.toASCIIString());
        basicContext.addServlet(holderDef,"/"); // the servlet spec "default url-pattern"

        handlers.addHandler(basicContext);
        server.setHandler(handlers);

        server.start();
    }

    public static void stopServer() throws Exception {
        server.stop();
    }
}