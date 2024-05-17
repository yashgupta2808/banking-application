package com.example.bankingapplication.service.impl;

import com.example.bankingapplication.dto.AccountDto;
import com.example.bankingapplication.dto.FundTransferRequestDto;
import com.example.bankingapplication.dto.TransactionDto;
import com.example.bankingapplication.entity.Transaction;
import com.example.bankingapplication.mapper.TransactionMapper;
import com.example.bankingapplication.repository.TransactionRepository;
import com.example.bankingapplication.service.TransactionService;
import com.example.bankingapplication.util.TransactionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public TransactionDto createTransaction(AccountDto accountDto, double amount, TransactionEnum transactionType) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAccountId(accountDto.getAccountId());
        transactionDto.setTransactionType(String.valueOf(transactionType));
        transactionDto.setAmount(amount);
        transactionDto.setTransactionDate(new Date());
        transactionDto.setBalance(accountDto.getBalance());
        return transactionDto;
    }

    @Override
    public TransactionDto storeTransaction(TransactionDto transactionDto) {
        try {
            Transaction transaction = TransactionMapper.mapToTransaction(transactionDto);
            Transaction savedTransaction = transactionRepository.save(transaction);
            return TransactionMapper.mapToTransactionDto(savedTransaction);
        } catch (Exception exception) {
            throw new RuntimeException("Transaction Failed. Try Again!");
        }

    }

    @Override
    public void deleteTransactionsByAccountId(long accountId) {
        try {
            List<Transaction> transactionList = transactionRepository.findByAccountId(accountId);
            transactionRepository.deleteAll(transactionList);
        } catch (Exception exception) {
            throw new RuntimeException("Error occurred. Try Again!");
        }
    }

    @Override
    public void updateFundTransferTransactions(FundTransferRequestDto fundTransferRequestDto, String withdrawalTransactionId, String depositTransactionId) {
        String sourceTransferDescription = "Fund Transfer to accountId: " + fundTransferRequestDto.getDestinationAccountId()
                + " - " + fundTransferRequestDto.getDescription();
        String destinationTransferDescription = "Fund Transfer from accountId: " + fundTransferRequestDto.getSourceAccountId()
                + " - " + fundTransferRequestDto.getDescription();
        try {
            //Update source account transaction
            transactionRepository.updateFundTransferTransactionByTransactionId(sourceTransferDescription, depositTransactionId, withdrawalTransactionId);
            //Update destination account transaction
            transactionRepository.updateFundTransferTransactionByTransactionId(destinationTransferDescription, withdrawalTransactionId, depositTransactionId);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to update fund transfer transactions");
        }

    }

    @Override
    public List<TransactionDto> getTransactionsByAccountId(long accountId) {
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        try {
            List<Transaction> transactionList = transactionRepository.findByAccountId(accountId);
            for (Transaction transaction : transactionList) {
                transactionDtoList.add(TransactionMapper.mapToTransactionDto(transaction));
            }
            return transactionDtoList;
        } catch (Exception exception) {
            throw new RuntimeException("Error retrieving transaction history!");
        }
    }
}
