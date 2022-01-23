package ru.zyablov.haulmont.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "credit")
public class Credit {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "percent")
    private long percent;

    @Column(name = "credit_limit")
    private long creditLimit;
//    @OneToMany()
//    @JoinColumn(name = "credit_offer_id")
//    private List<CreditOffer> creditOffers;

    public Credit() {}

    public Credit(long percent, long creditLimit) {
        this.percent = percent;
        this.creditLimit = creditLimit;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", percent=" + percent +
                ", creditLimit=" + creditLimit +
                '}';
    }
}
