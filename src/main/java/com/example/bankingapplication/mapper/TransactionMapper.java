package com.example.bankingapplication.mapper;

import com.example.bankingapplication.dto.TransactionDto;
import com.example.bankingapplication.entity.Transaction;

public class TransactionMapper {
    public static Transaction mapToTransaction(TransactionDto transactionDto) {
        return new Transaction(
                transactionDto.getTransactionId(),
                transactionDto.getAccountId(),
                transactionDto.getTransactionType(),
                transactionDto.getAmount(),
                transactionDto.getTransactionDate(),
                transactionDto.getDescription(),
                transactionDto.getBalance(),
                transactionDto.getRelatedTransactionId()
        );
    }

    public static TransactionDto mapToTransactionDto(Transaction transaction) {
        return new TransactionDto(
                transaction.getTransactionId(),
                transaction.getAccountId(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getTransactionDate(),
                transaction.getDescription(),
                transaction.getBalance(),
                transaction.getRelatedTransactionId()
        );
    }
}
