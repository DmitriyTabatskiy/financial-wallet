package com.tabatskiy.web.repository;

import com.tabatskiy.web.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findByEmailAndPassword(String email, String password);

    Optional<Client> findByEmail(String email);
}