package servlet;

import dao.UserDAO;
import model.User;
import util.PasswordUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        
        String hashedPassword = PasswordUtil.hashPassword(password);

        User user = new User(name, email, hashedPassword);

        UserDAO userDAO = new UserDAO();
        userDAO.saveUser(user); 
        response.sendRedirect("login.html");
    }
}
