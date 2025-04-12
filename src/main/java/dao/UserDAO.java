package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;
import util.PasswordUtil;

public class UserDAO {

    // Save user to database
    public void saveUser(User user) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public User getUserByEmailAndPassword(String email, String plainPassword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(
                "FROM User WHERE email = :email", User.class);
            query.setParameter("email", email);
            User user = query.uniqueResult();

          
            if (user != null && PasswordUtil.checkPassword(plainPassword, user.getPassword())) {
                return user; 
            }
            return null; 
        }
    }
}
