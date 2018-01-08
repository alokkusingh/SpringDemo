package com.alok.spring.rest.test.demo.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alok.spring.rest.test.demo.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
    Optional<Account> findById(Long id);
}
