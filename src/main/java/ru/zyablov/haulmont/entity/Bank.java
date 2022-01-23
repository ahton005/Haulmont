package ru.zyablov.haulmont.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany()
    @JoinTable(name = "bank_customer",joinColumns = @JoinColumn(name = "bank_id"),inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Customer> customers = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "bank_id")
    private List<Credit> credits = new ArrayList<>();

    public Bank() {
    }

    public void addCredit(Credit credit){
        credits.add(credit);
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", customers=" + customers +
                ", credits=" + credits +
                '}';
    }
}
