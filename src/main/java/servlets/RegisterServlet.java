package servlets;

import app.entity.User;
import app.entity.UserRoleEnum;
import app.entity.binding.UserRegisterBindingModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
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
                User user = modelMapper.map(registerBindingModel, User.class);
                user.setRole(UserRoleEnum.USER);
                manager.persist(user);
                manager.getTransaction().commit();
                manager.close();
                response.sendRedirect("/user/login");
            }else {
                System.out.println("password");
                request.setAttribute("pas-error", "Passwords Don't Match");
                getServletContext().getRequestDispatcher("/register.jsp").forward(request,response);
            }
        }else{
            System.out.println("Invalid");
            request.setAttribute("error", "Invalid Form");
            getServletContext().getRequestDispatcher("/register.jsp").forward(request,response);
        }

    }

}
