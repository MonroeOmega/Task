package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "BasicServlet",urlPatterns = "/**")
public class BasicServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        System.out.println("Empty");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    }

}
