package com.example.bankingapplication.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionDto {
    String transactionId;
    long accountId;
    String transactionType;
    double amount;
    Date transactionDate;
    String description;
    double balance;
    String relatedTransactionId;

    @Override
    public String toString() {
        return "Transaction Id = " + transactionId + "\n" +
                "Account Id = " + accountId + "\n" +
                "Transaction Type = " + transactionType + "\n" +
                "Amount = " + amount + "\n" +
                "Transaction Date = " + transactionDate + "\n" +
                "Balance = " + balance;
    }
}
