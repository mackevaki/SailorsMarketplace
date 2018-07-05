package com.company.sailorsmarketplace.controllers;

import com.company.sailorsmarketplace.validators.EmailValidator;
import com.company.sailorsmarketplace.validators.StringValidator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SignationUpController", urlPatterns = "/processsignup", loadOnStartup = 1)
public class SignationUpController extends HttpServlet {
    private ServletConfig config;
    @Override
    public void init (ServletConfig config) {
        this.config = config;
        ServletContext sc = config.getServletContext();
        sc.log( "Started OK!" );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        RequestHandler handler = RequestHandler.fromRequestParameters(request);
        handler.setAsRequestAttributes(request);
        List<String> violations = handler.validate();

        if (!violations.isEmpty()) {
            request.setAttribute("violations", violations);
        }

        String url = determineUrl(violations);
        forwardResponse(url, request, response);
    }

    private String determineUrl(List<String> violations) {
        if (!violations.isEmpty()) {
            return "/META-INF/index.jsp";
        } else {
            return "/META-INF/accountinfo.jsp";
        }
    }

    private void forwardResponse(String url, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private static class RequestHandler {
        private final String firstName;
        private final String lastName;
        private final String email;

        private RequestHandler(String firstName, String lastName, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }

        public static RequestHandler fromRequestParameters(HttpServletRequest request) {
            return new RequestHandler(
                    request.getParameter("firstname"),
                    request.getParameter("lastname"),
                    request.getParameter("email"));
        }

        public void setAsRequestAttributes(HttpServletRequest request) {
            request.setAttribute("firstname", firstName);
            request.setAttribute("lastname", lastName);
            request.setAttribute("email", email);
        }

        public List<String> validate() {
            List<String> violations = new ArrayList<>();
            if (!StringValidator.validate(firstName)) {
                violations.add("First Name is mandatory");
            }
            if (!StringValidator.validate(lastName)) {
                violations.add("Last Name is mandatory");
            }
            if (!EmailValidator.validate(email)) {
                violations.add("Email must be a well-formed address");
            }
            return violations;
        }
    }

}
