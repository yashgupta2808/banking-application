package com.example.bankingapplication.service;

import com.example.bankingapplication.dto.AccountDto;
import com.example.bankingapplication.dto.FundTransferRequestDto;
import com.example.bankingapplication.dto.TransactionDto;
import com.example.bankingapplication.util.TransactionEnum;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long accountId);

    TransactionDto deposit(Long accountId, double amount, TransactionEnum transactionEnum);

    TransactionDto withdraw(Long accountId, double amount, TransactionEnum transactionEnum);

    void deleteAccount(Long accountId);

    TransactionDto fundTransfer(FundTransferRequestDto fundTransferRequestDto);

    List<TransactionDto> getTransactionsByAccountId(long accountId);
}