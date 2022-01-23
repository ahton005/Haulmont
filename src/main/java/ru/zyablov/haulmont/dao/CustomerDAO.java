package ru.zyablov.haulmont.dao;

import org.hibernate.Session;
import ru.zyablov.haulmont.entity.Customer;
import ru.zyablov.haulmont.hibernate.HibernateSessionFactory;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends AbstractDAO <Customer> implements DaoServices<Customer> {

    public Customer getById(Long id) {
        Session session;
        Customer patient = null;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            patient = session.load(Customer.class, id);
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return patient;
    }

    public List<Customer> getByFIO(String name) {
        Session session;
        List customers = null;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            customers = session.createQuery("FROM Customer where fio = :param").setParameter("param",name).list();

            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return customers;
    }

    public List<Customer> findAll() {
        Session session;
        List<Customer> customers = new ArrayList<>();
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            CriteriaQuery<Customer> criteriaQuery = session.getCriteriaBuilder().createQuery(Customer.class);
            criteriaQuery.from(Customer.class);
            customers = session.createQuery(criteriaQuery).getResultList();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return customers;
    }
}
