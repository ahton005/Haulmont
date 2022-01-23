package ru.zyablov.haulmont.dao;

import org.hibernate.Session;
import ru.zyablov.haulmont.hibernate.HibernateSessionFactory;

public abstract class AbstractDAO<E> {
    public void save(E e) {
        Session session;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(e);
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete(E e) {
        Session session;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(e);
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void update(E e) {
        Session session;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(e);
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
