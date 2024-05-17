package com.example.bankingapplication.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FundTransferRequestDto {
    long sourceAccountId;
    long destinationAccountId;
    double amount;
    String description;
}