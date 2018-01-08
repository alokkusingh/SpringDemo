package com.alok.spring.rest.test.demo.jpa;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alok.spring.rest.test.demo.entity.Bookmark;

//https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Collection<Bookmark> findByAccountUsername(String username);
    Optional<Bookmark> findByAccountUsernameAndId(String username, Long id);
}