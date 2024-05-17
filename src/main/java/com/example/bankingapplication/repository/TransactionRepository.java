package com.example.bankingapplication.repository;

import com.example.bankingapplication.entity.Transaction;
import org.hibernate.annotations.SQLSelect;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, String> {

    @SQLSelect(sql = "SELECT * FROM transactions where account_id = #{accountId}")
    List<Transaction> findByAccountId(@Param("accountId") long accountId);

    @Modifying
    @Query(value = "UPDATE transactions SET description = :sourceTransferDescription, related_transaction_id = :relatedTransactionId WHERE transaction_id = :transactionId", nativeQuery = true)
    void updateFundTransferTransactionByTransactionId(@Param("sourceTransferDescription") String sourceTransferDescription, @Param("relatedTransactionId") String relatedTransactionId, @Param("transactionId") String transactionId);
}
