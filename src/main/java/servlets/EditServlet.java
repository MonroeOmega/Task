package servlets;

import app.entity.User;
import app.entity.UserRoleEnum;
import app.entity.binding.UserEditBindingModel;
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

@WebServlet(name = "EditServlet", urlPatterns = "/user/edit")
public class EditServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getSession().getAttribute("logged") == null){
            response.sendRedirect("/user/login");
        }
        getServletContext().getRequestDispatcher("/edit.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        ModelMapper modelMapper = new ModelMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        UserEditBindingModel editBindingModel = new UserEditBindingModel();
        editBindingModel.setFirstName(request.getParameter("first-name"));
        editBindingModel.setLastName(request.getParameter("last-name"));
        editBindingModel.setEmail(request.getParameter("email"));
        editBindingModel.setPassword(request.getParameter("password"));
        editBindingModel.setRepeatPassword(request.getParameter("rep-password"));
        Set<ConstraintViolation<UserEditBindingModel>> violations = validator.validate(editBindingModel);
        if(violations.isEmpty()){
            if(editBindingModel.getPassword().equals(editBindingModel.getRepeatPassword())) {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("alpha");
                EntityManager manager = emf.createEntityManager();
                manager.getTransaction().begin();
                User userer = manager.find(User.class,request.getSession().getAttribute("id"));
                User user = modelMapper.map(editBindingModel, User.class);
                user.setId(userer.getId());
                user.setUsername(userer.getUsername());
                user.setRole(userer.getRole());
                manager.persist(user);
                manager.getTransaction().commit();
                manager.close();
                response.sendRedirect("/user/profile");
            }else {
                System.out.println("password");
                request.setAttribute("pas-error", "Passwords Don't Match");
                getServletContext().getRequestDispatcher("/edit.jsp").forward(request,response);
            }
        }else{
            System.out.println("Invalid");
            request.setAttribute("error", "Invalid Form");
            getServletContext().getRequestDispatcher("/edit.jsp").forward(request,response);
        }
    }

}
