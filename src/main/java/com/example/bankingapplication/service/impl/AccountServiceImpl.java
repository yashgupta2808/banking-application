package com.example.bankingapplication.service.impl;

import com.example.bankingapplication.dto.AccountDto;
import com.example.bankingapplication.dto.FundTransferRequestDto;
import com.example.bankingapplication.dto.TransactionDto;
import com.example.bankingapplication.entity.Account;
import com.example.bankingapplication.mapper.AccountMapper;
import com.example.bankingapplication.repository.AccountRepository;
import com.example.bankingapplication.service.AccountService;
import com.example.bankingapplication.service.TransactionService;
import com.example.bankingapplication.util.TransactionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;


    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        try {
            Account account = AccountMapper.mapToAccount(accountDto);
            Account savedAccount = accountRepository.save(account);
            return AccountMapper.mapToAccountDto(savedAccount);
        } catch (Exception exception) {
            throw new RuntimeException("Something went wrong. Try Again!");
        }

    }

    @Override
    public AccountDto getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new RuntimeException("Account does not exist!"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    @Transactional
    public TransactionDto deposit(Long accountId, double amount, TransactionEnum transactionType) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new RuntimeException("Account does not exist!"));
        try {
            account.setBalance(account.getBalance() + amount);
            Account savedAccount = accountRepository.save(account);
            TransactionDto transactionDto = transactionService.createTransaction(AccountMapper.mapToAccountDto(savedAccount), amount, transactionType);
            return transactionService.storeTransaction(transactionDto);
        } catch (Exception exception) {
            throw new RuntimeException("Something went wrong. Try Again!");
        }

    }

    @Override
    @Transactional
    public TransactionDto withdraw(Long accountId, double amount, TransactionEnum transactionType) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new RuntimeException("Account does not exist!"));
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance!");
        }
        try {
            account.setBalance(account.getBalance() - amount);
            Account savedAccount = accountRepository.save(account);
            TransactionDto transactionDto = transactionService.createTransaction(AccountMapper.mapToAccountDto(savedAccount),
                    amount, transactionType);
            return transactionService.storeTransaction(transactionDto);
        } catch (Exception exception) {
            throw new RuntimeException("Something went wrong. Try Again!");
        }

    }

    @Override
    @Transactional
    public void deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new RuntimeException("Account does not exist!"));
        try {
            accountRepository.delete(account);
            transactionService.deleteTransactionsByAccountId(accountId);
        } catch (Exception exception) {
            throw new RuntimeException("Error occurred while deleting your account. Try Again!");
        }
    }

    @Override
    @Transactional
    public TransactionDto fundTransfer(FundTransferRequestDto fundTransferRequestDto) {
        TransactionDto withdrawalTransactionDto;
        TransactionDto depositTransactionDto;
        try {
            withdrawalTransactionDto = withdraw(fundTransferRequestDto.getSourceAccountId(), fundTransferRequestDto.getAmount(), TransactionEnum.Transfer_Debit);
            depositTransactionDto = deposit(fundTransferRequestDto.getDestinationAccountId(), fundTransferRequestDto.getAmount(), TransactionEnum.Transfer_Credit);
            transactionService.updateFundTransferTransactions(fundTransferRequestDto, withdrawalTransactionDto.getTransactionId(), depositTransactionDto.getTransactionId());
        } catch (Exception exception) {
            throw new RuntimeException("Fund transfer failed: " + exception.getMessage());
        }
        return withdrawalTransactionDto;
    }

    @Override
    public List<TransactionDto> getTransactionsByAccountId(long accountId) {
        try {
            return transactionService.getTransactionsByAccountId(accountId);
        } catch (Exception exception) {
            throw new RuntimeException("Error occurred: " + exception.getMessage());
        }
    }
}