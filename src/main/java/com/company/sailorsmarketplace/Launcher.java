package com.company.sailorsmarketplace;

import com.company.sailorsmarketplace.controllers.SignationUpController;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Starts jetty-server on the specified port
 */
public class Launcher {
    private static Server server;

    public static void main(String[] args){
        try {
            server = new Server(8080);
            ServletContextHandler context = new ServletContextHandler(server, "/com/company/sailorsmarketplace");
            context.setContextPath("/com/company/sailorsmarketplace");
            server.setHandler(context);

            ServletHolder holderPwd = new ServletHolder("SignationUpController", SignationUpController.class);//DefaultServlet.class);

            holderPwd.setInitParameter("dirAllowed","true");
            context.addServlet(holderPwd,"/*");

            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.destroy();
        }

    }
}