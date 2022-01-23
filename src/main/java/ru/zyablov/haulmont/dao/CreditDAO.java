package ru.zyablov.haulmont.dao;

import org.hibernate.Session;
import ru.zyablov.haulmont.entity.Credit;
import ru.zyablov.haulmont.entity.Customer;
import ru.zyablov.haulmont.hibernate.HibernateSessionFactory;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class CreditDAO extends AbstractDAO <Credit> implements DaoServices<Credit> {

    public Credit getById(Long id) {
        Session session;
        Credit credit = null;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            credit = session.load(Credit.class, id);
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return credit;
    }

    @Override
    public List<Credit> getByFIO(String fio) {
        return null;
    }

    public List<Credit> findAll() {
        Session session;
        List<Credit> credits = new ArrayList<>();
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            CriteriaQuery<Credit> criteriaQuery = session.getCriteriaBuilder().createQuery(Credit.class);
            criteriaQuery.from(Credit.class);
            credits = session.createQuery(criteriaQuery).getResultList();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return credits;
    }
}
