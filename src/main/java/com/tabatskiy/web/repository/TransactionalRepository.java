package com.tabatskiy.web.repository;

import com.tabatskiy.web.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionalRepository extends JpaRepository<Transaction, Integer> {
}