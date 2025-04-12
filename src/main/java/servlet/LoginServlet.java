package servlet;

import model.User;
import util.HibernateUtil;
import util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        System.out.println("Login attempt: " + email); 

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE email = :email", User.class);
            query.setParameter("email", email);
            User user = query.uniqueResult();

            System.out.println("User fetched from DB: " + (user != null ? user.getEmail() : "null")); 

            if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("user", user);

                System.out.println("Login success"); 
                resp.sendRedirect("event_list.html");
            } else {
                System.out.println("Login failed: invalid credentials"); 
                resp.sendRedirect("login.html?error=invalid");
            }
        } catch (Exception e) {
            e.printStackTrace();  
            resp.setContentType("text/plain");
            resp.getWriter().write("Login failed: " + e.getMessage());
        }

    }
}
