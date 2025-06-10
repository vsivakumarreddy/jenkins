package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A simple Java Servlet that responds with a styled HTML page
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

        // Write the new styled HTML response
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("    <meta charset=\"UTF-8\">");
        out.println("    <title>Updated Customer App Welcome</title>");
        out.println("    <style>");
        out.println("        body {");
        out.println("            font-family: Arial, sans-serif;");
        out.println("            margin: 40px;");
        out.println("            background-color: #f4f4f4;");
        out.println("            color: #333;");
        out.println("            text-align: center;");
        out.println("        }");
        out.println("        h1 {");
        out.println("            color: #0056b3;");
        out.println("            margin-bottom: 20px;");
        out.println("        }");
        out.println("        p {");
        out.println("            font-size: 1.1em;");
        out.println("            line-height: 1.6;");
        out.println("        }");
        out.println("        .button-container {");
        out.println("            margin-top: 30px;");
        out.println("        }");
        out.println("        .button {");
        out.println("            display: inline-block;");
        out.println("            padding: 12px 25px;");
        out.println("            font-size: 1.1em;");
        out.println("            color: #fff;");
        out.println("            background-color: #28a745;");
        out.println("            border: none;");
        out.println("            border-radius: 5px;");
        out.println("            text-decoration: none;");
        out.println("            cursor: pointer;");
        out.println("            transition: background-color 0.3s ease;");
        out.println("        }");
        out.println("        .button:hover {");
        out.println("            background-color: #218838;");
        out.println("        }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <h1>Welcome to the Refreshed Manikiran App!</h1>");
        out.println("    <p>We've made some exciting updates to enhance your experience.</p>");
        out.println("    <p>This is the main entry point for your web application.</p>");
        out.println("");
        out.println("    <div class=\"button-container\">");
        out.println("        <p>Click the button below to interact with our simple servlet:</p>");
        out.println("        <a href=\"hello\" class=\"button\">Go to Hello Servlet</a>");
        out.println("    </div>");
        out.println("");
        out.println("    <p style=\"margin-top: 50px; font-size: 0.9em; color: #666;\">");
        out.println("        Built with Java, Maven, and deployed on Tomcat.");
        out.println("    </p>");
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
