package servlets;

import app.entity.User;
import app.entity.UserRoleEnum;
import app.entity.binding.UserRegisterBindingModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.Set;

@WebServlet(name = "RegisterServlet", urlPatterns = "/user/register")
public class RegisterServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getSession().getAttribute("id") != null){
            response.sendRedirect("/user/profile");
            return;
        }
        request.setAttribute("error","");
        getServletContext().getRequestDispatcher("/register.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        ModelMapper modelMapper = new ModelMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        UserRegisterBindingModel registerBindingModel = new UserRegisterBindingModel();
        registerBindingModel.setUsername(request.getParameter("username"));
        registerBindingModel.setFirstName(request.getParameter("first-name"));
        registerBindingModel.setLastName(request.getParameter("last-name"));
        registerBindingModel.setEmail(request.getParameter("email"));
        registerBindingModel.setPassword(request.getParameter("password"));
        registerBindingModel.setRepeatPassword(request.getParameter("rep-password"));
        Set<ConstraintViolation<UserRegisterBindingModel>> violations = validator.validate(registerBindingModel);
        if(violations.isEmpty()){
            if(registerBindingModel.getPassword().equals(registerBindingModel.getRepeatPassword())) {

                EntityManagerFactory emf = Persistence.createEntityManagerFactory("alpha");
                EntityManager manager = emf.createEntityManager();
                manager.getTransaction().begin();
                Query query = manager.createQuery("from User u where u.username = :username");
                query.setParameter("username",registerBindingModel.getUsername());
                if (query.getResultList().isEmpty()) {
                    query = manager.createQuery("from User u where u.email = :email");
                    query.setParameter("email",registerBindingModel.getEmail());
                    if (query.getResultList().isEmpty()) {
                        User user = modelMapper.map(registerBindingModel, User.class);
                        user.setRole(UserRoleEnum.USER);
                        manager.persist(user);
                        manager.getTransaction().commit();
                        manager.close();
                        response.sendRedirect("/user/login");
                    }else {
                        request.setAttribute("error", "Email already in use.");
                        getServletContext().getRequestDispatcher("/register.jsp").forward(request,response);
                    }
                }else {
                    request.setAttribute("error", "Username already exists.");
                    getServletContext().getRequestDispatcher("/register.jsp").forward(request,response);
                }
            }else {
                request.setAttribute("error", "Passwords Don't Match");
                getServletContext().getRequestDispatcher("/register.jsp").forward(request,response);
            }
        }else{
            request.setAttribute("error", "Invalid Form Data");
            getServletContext().getRequestDispatcher("/register.jsp").forward(request,response);
        }

    }

}
