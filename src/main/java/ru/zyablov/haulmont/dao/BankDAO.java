package ru.zyablov.haulmont.dao;

import org.hibernate.Session;
import ru.zyablov.haulmont.entity.Bank;
import ru.zyablov.haulmont.entity.Credit;
import ru.zyablov.haulmont.hibernate.HibernateSessionFactory;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class BankDAO extends AbstractDAO <Bank> implements DaoServices<Bank> {

    public Bank getById(Long id) {
        Session session;
        Bank bank = null;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            bank = session.load(Bank.class, id);
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bank;
    }

    @Override
    public List<Bank> getByFIO(String fio) {
        return null;
    }

    public List<Bank> findAll() {
        Session session;
        List<Bank> banks = new ArrayList<>();
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            CriteriaQuery<Bank> criteriaQuery = session.getCriteriaBuilder().createQuery(Bank.class);
            criteriaQuery.from(Bank.class);
            banks = session.createQuery(criteriaQuery).getResultList();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return banks;
    }
}
