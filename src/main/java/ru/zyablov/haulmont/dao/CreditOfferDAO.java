package ru.zyablov.haulmont.dao;

import org.hibernate.Session;
import ru.zyablov.haulmont.entity.Bank;
import ru.zyablov.haulmont.entity.CreditOffer;
import ru.zyablov.haulmont.hibernate.HibernateSessionFactory;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class CreditOfferDAO extends AbstractDAO <CreditOffer> implements DaoServices<CreditOffer> {

    public CreditOffer getById(Long id) {
        Session session;
        CreditOffer creditOffer = null;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            creditOffer = session.load(CreditOffer.class, id);
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return creditOffer;
    }

    @Override
    public List<CreditOffer> getByFIO(String fio) {
        return null;
    }

    public List<CreditOffer> findAll() {
        Session session;
        List<CreditOffer> creditOffers = new ArrayList<>();
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            CriteriaQuery<CreditOffer> criteriaQuery = session.getCriteriaBuilder().createQuery(CreditOffer.class);
            criteriaQuery.from(CreditOffer.class);
            creditOffers = session.createQuery(criteriaQuery).getResultList();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return creditOffers;
    }
}
