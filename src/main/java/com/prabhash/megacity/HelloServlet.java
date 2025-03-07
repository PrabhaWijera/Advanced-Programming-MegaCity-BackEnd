package com.prabhash.megacity;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        System.out.println("in war of EGO , the loser always wins !!!        ");
        System.out.println("IF somebody had to die I'D rather be me");

        message = "oohh you think darkness is your ally you merely adopted the dark. iwas born in it.molded by it" ;

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setContentType("text/plain");

        // This is a simple response to indicate the server is up and running
        PrintWriter out = response.getWriter();
        out.println("Server is Up and Running");
        // Hello

        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}