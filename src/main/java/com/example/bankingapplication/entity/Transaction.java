package com.example.bankingapplication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "account_id")
    private long accountId;

    @Column(name = "transaction_type")
    private String transactionType;

    private double amount;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(length = 90)
    private String description;

    private double balance;

    @Column(name = "related_transaction_id")
    private String relatedTransactionId;
}
