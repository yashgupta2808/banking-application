package com.example.bankingapplication.mapper;

import com.example.bankingapplication.dto.AccountDto;
import com.example.bankingapplication.entity.Account;

public class AccountMapper {
    public static Account mapToAccount(AccountDto accountDto) {
        return new Account(
                accountDto.getAccountId(),
                accountDto.getAccountHolderName(),
                accountDto.getBalance()
        );
    }

    public static AccountDto mapToAccountDto(Account account) {
        return new AccountDto(
                account.getAccountId(),
                account.getAccountHolderName(),
                account.getBalance()
        );
    }
}
