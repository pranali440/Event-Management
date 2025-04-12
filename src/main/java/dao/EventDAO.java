package dao;

import model.Event;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class EventDAO {

    public void createEvent(Event event) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(event);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<Event> getAllEvents() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Event", Event.class).list();
        }
    }

    public Event getEventById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Event.class, id);
        }
    }

    public boolean deleteEventById(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Event event = session.get(Event.class, id);
            if (event != null) {
                session.delete(event);
                tx.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }
}
