package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A simple Java Servlet that responds with "Hello from mani kiran App!"
 * and displays some request information.
 */
@WebServlet("/hello") // This annotation maps the servlet to the /hello URL path
public class SimpleServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // Recommended for Serializable classes

    /**
     * Handles GET requests.
     * @param request The HttpServletRequest object that contains the client's request.
     * @param response The HttpServletResponse object that contains the servlet's response.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html"); // Set content type to HTML
        PrintWriter out = response.getWriter(); // Get a PrintWriter to write the response

        // Write HTML response
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>mani kiran app</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Hello from mani kiran App!</h1>");
        out.println("<p>This is a simple Java web application.</p>");
        out.println("<p>Requested URI: " + request.getRequestURI() + "</p>");
        out.println("<p>Context Path: " + request.getContextPath() + "</p>");
        out.println("<p>Servlet Path: " + request.getServletPath() + "</p>");
        out.println("</body>");
        out.println("</html>");

        out.close(); // Close the PrintWriter
    }

    /**
     * Handles POST requests. For this simple example, it delegates to doGet.
     * @param request The HttpServletRequest object that contains the client's request.
     * @param response The HttpServletResponse object that contains the servlet's response.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Reuse doGet logic for POST requests
    }
}
