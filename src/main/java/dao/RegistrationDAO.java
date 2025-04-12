package dao;

import model.Registration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class RegistrationDAO {

    public void registerUser(Registration registration) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(registration);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}