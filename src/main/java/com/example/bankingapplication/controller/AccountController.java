package com.example.bankingapplication.controller;

import com.example.bankingapplication.dto.AccountDto;
import com.example.bankingapplication.dto.FundTransferRequestDto;
import com.example.bankingapplication.dto.TransactionDto;
import com.example.bankingapplication.service.AccountService;
import com.example.bankingapplication.util.TransactionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccount(@PathVariable Long accountId) {
        try {
            AccountDto accountDto = accountService.getAccountById(accountId);
            return ResponseEntity.ok(accountDto);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @PutMapping("/{accountId}/deposit")
    public ResponseEntity<?> deposit(@PathVariable Long accountId, @RequestBody Map<String, Double> request) {
        try {
            TransactionDto transactionDto = accountService.deposit(accountId, request.get("amount"), TransactionEnum.Deposit);
            return ResponseEntity.ok("Transaction Successful!" + "\n" + transactionDto.toString());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @PutMapping("/{accountId}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable Long accountId, @RequestBody Map<String, Double> request) {
        try {
            TransactionDto transactionDto = accountService.withdraw(accountId, request.get("amount"), TransactionEnum.Withdrawal);
            return ResponseEntity.ok("Transaction Successful!" + "\n" + transactionDto.toString());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{accountId}/delete")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountId) {
        try {
            accountService.deleteAccount(accountId);
            return ResponseEntity.ok("Account deleted successfully!");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @PostMapping("/fund-transfer")
    public ResponseEntity<?> fundTransfer(@RequestBody FundTransferRequestDto fundTransferRequestDto) {
        try {
            TransactionDto transactionDto = accountService.fundTransfer(fundTransferRequestDto);
            return ResponseEntity.ok("Transaction Successful!" + "\n" + transactionDto.toString());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @GetMapping("/{accountId}/transaction-history")
    public ResponseEntity<?> getTransactionHistory(@PathVariable Long accountId) {
        try {
            List<TransactionDto> transactionDtoList = accountService.getTransactionsByAccountId(accountId);
            return ResponseEntity.ok(transactionDtoList);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }
}
