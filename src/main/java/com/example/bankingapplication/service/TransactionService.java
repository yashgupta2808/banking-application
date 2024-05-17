package com.example.bankingapplication.service;

import com.example.bankingapplication.dto.AccountDto;
import com.example.bankingapplication.dto.FundTransferRequestDto;
import com.example.bankingapplication.dto.TransactionDto;
import com.example.bankingapplication.util.TransactionEnum;

import java.util.List;

public interface TransactionService {
    TransactionDto createTransaction(AccountDto accountDto, double amount, TransactionEnum transactionType);

    TransactionDto storeTransaction(TransactionDto transactionDto);

    void deleteTransactionsByAccountId(long accountId);

    void updateFundTransferTransactions(FundTransferRequestDto fundTransferRequestDto, String transactionId, String relatedTransactionId);

    List<TransactionDto> getTransactionsByAccountId(long accountId);
}
