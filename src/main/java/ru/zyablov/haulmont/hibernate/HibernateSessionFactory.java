package ru.zyablov.haulmont.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.zyablov.haulmont.entity.Bank;
import ru.zyablov.haulmont.entity.Credit;
import ru.zyablov.haulmont.entity.CreditOffer;
import ru.zyablov.haulmont.entity.Customer;

public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;

    static {
        Configuration cfg = new Configuration().configure();
        cfg.addAnnotatedClass(Customer.class).addAnnotatedClass(Credit.class).addAnnotatedClass(Bank.class).addAnnotatedClass(CreditOffer.class);


        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties());
        sessionFactory = cfg.buildSessionFactory(builder.build());
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}