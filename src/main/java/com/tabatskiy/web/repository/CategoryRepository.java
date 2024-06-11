package com.tabatskiy.web.repository;

import com.tabatskiy.web.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByIdAndClientId(Integer id, Integer clientId);

    List<Category> findAllByClientId(Integer clientId);

    void deleteByIdAndClientId(Integer id, Integer clientId);
}