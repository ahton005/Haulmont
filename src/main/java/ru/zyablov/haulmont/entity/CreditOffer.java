package ru.zyablov.haulmont.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "credit_offer")
public class CreditOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "sumOfCredit")
    private long sumOfCredit;


    @OneToOne
    private Customer customer;


    @ManyToOne
    private Credit credit;
}
