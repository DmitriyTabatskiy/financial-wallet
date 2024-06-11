package com.tabatskiy.web.entity;

import javax.persistence.*;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "transaction")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account accountFrom;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account accountTo;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @ManyToMany
    @JoinTable(
            name = "transaction_to_category",
            joinColumns = @JoinColumn(name = "transaction_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private List<Category> categories;
}