package com.company.sailorsmarketplace;

import com.company.sailorsmarketplace.config.JerseyConfig;
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
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;


/**
 * Starts jetty-server on the specified port
 */
public class Launcher {
    private static final Logger log = LoggerFactory.getLogger(Launcher.class);
    public static final int SERVER_PORT = 9991;
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
        ServletContextHandler basicContext = new ServletContextHandler(server, "/");
        ResourceConfig config = new JerseyConfig();
        ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(config));

        jerseyServlet.setInitOrder(0);

        basicContext.addServlet(jerseyServlet, "/rest/*");

        handlers.addHandler(basicContext);
        server.setHandler(handlers);

        server.start();
    }

    public static void stopServer() throws Exception {
        server.stop();
    }
}