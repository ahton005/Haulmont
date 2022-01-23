package ru.zyablov.haulmont.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Long id;
    @Column(name = "fio")
    private String fio;
    @Column(name = "phonenumber")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "numberpassport")
    private String numberPassport;

    public Customer() {
    }

    public Customer(String fio, String phoneNumber, String email, String numberPassport) {
        this.fio = fio;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.numberPassport = numberPassport;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, firstName='%s', lastName='%s']", id,
                fio, phoneNumber);
    }


}
