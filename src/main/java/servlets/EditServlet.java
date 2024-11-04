package servlets;

import app.entity.User;
import app.entity.UserRoleEnum;
import app.entity.binding.UserEditBindingModel;
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

@WebServlet(name = "EditServlet", urlPatterns = "/user/edit")
public class EditServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getSession().getAttribute("id") == null){
            response.sendRedirect("/user/login");
            return;
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
                Query query = manager.createQuery("from User u where u.email = :email");
                query.setParameter("email",editBindingModel.getEmail());
                if (query.getResultList().isEmpty()) {
                    User userer = manager.find(User.class, request.getSession().getAttribute("id"));
                    User user = modelMapper.map(editBindingModel, User.class);
                    user.setId(userer.getId());
                    user.setUsername(userer.getUsername());
                    user.setRole(userer.getRole());
                    request.getSession().setAttribute("user", user);
                    manager.merge(user);
                    manager.getTransaction().commit();
                    manager.close();
                    response.sendRedirect("/user/profile");
                }else{
                    request.setAttribute("error", "Email is already used");
                    getServletContext().getRequestDispatcher("/edit.jsp").forward(request,response);
                }
            }else {
                request.setAttribute("error", "Passwords Don't Match");
                getServletContext().getRequestDispatcher("/edit.jsp").forward(request,response);
            }
        }else{
            System.out.println("Invalid");
            request.setAttribute("error", "Invalid Form Data");
            getServletContext().getRequestDispatcher("/edit.jsp").forward(request,response);
        }
    }

}
