package servlets;

import app.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/user/login")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext().getRequestDispatcher("/login.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("alpha");
        EntityManager manager = emf.createEntityManager();
        manager.getTransaction().begin();
        Query query = manager.createQuery("from User u where u.username = :username and u.password = :password");
        query.setParameter("username",request.getAttribute("username"));
        query.setParameter("password",request.getAttribute("password"));
        User user = (User) query.getResultList().get(0);
        if(user != null){
            request.getSession().setAttribute("logged",true);
            request.getSession().setAttribute("id",user.getId());
            response.sendRedirect("/user/profile");
        }else{
            request.setAttribute("error","Invalid username or password");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
    }

}
