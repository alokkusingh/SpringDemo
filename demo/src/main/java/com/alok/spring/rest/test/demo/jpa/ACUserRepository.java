package com.alok.spring.rest.test.demo.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alok.spring.rest.test.demo.entity.ACUser;

public interface ACUserRepository extends JpaRepository<ACUser, Long> {
    Optional<ACUser> findByUsername(String username);
}
