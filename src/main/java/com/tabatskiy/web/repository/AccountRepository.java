package com.tabatskiy.web.repository;

import com.tabatskiy.web.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByIdAndClientId(Integer id, Integer clientId);

    List<Account> findAllByClientId(Integer clientId);

    void deleteAccountByIdAndClientId(Integer id, Integer clientId);
}