package servlets;

import app.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ProfileServlet", urlPatterns = "/user/profile")
public class ProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getSession().getAttribute("id") == null){
            response.sendRedirect("/user/login");
            return;
        }
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("alpha");
        EntityManager manager = factory.createEntityManager();
        User user = manager.find(User.class,request.getSession().getAttribute("id"));
        request.setAttribute("user",user);
        request.setAttribute("username",user.getUsername());
        manager.close();
        getServletContext().getRequestDispatcher("/profile.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    }

}
