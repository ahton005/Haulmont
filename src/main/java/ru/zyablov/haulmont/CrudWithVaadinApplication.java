package ru.zyablov.haulmont;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.zyablov.haulmont.entity.Bank;
import ru.zyablov.haulmont.entity.Credit;
import ru.zyablov.haulmont.entity.Customer;
import ru.zyablov.haulmont.hibernate.HibernateSessionFactory;

@SpringBootApplication
public class CrudWithVaadinApplication {

    private static final Logger log = LoggerFactory.getLogger(CrudWithVaadinApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CrudWithVaadinApplication.class);
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
//        Customer c = new Customer("Ivan","89518672580","ivan@mail.ru","2015874563");
//        Customer c1 = new Customer("Petr","89518672581","petr@mail.ru","2015874147");
//        Credit credit = new Credit(20,2000000);
        Bank bank = new Bank();
        session.beginTransaction();
        session.save(bank);
//        session.save(c);
//        session.save(c1);
//        session.save(credit);
        session.getTransaction().commit();
        session.close();
    }

}