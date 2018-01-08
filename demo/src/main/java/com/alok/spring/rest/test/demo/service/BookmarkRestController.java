package com.alok.spring.rest.test.demo.service;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alok.spring.rest.test.demo.entity.Account;
import com.alok.spring.rest.test.demo.entity.Bookmark;
import com.alok.spring.rest.test.demo.jpa.AccountRepository;
import com.alok.spring.rest.test.demo.jpa.BookmarkRepository;
import com.alok.spring.rest.test.demo.validator.BookmarkValidator;
import com.alok.spring.rest.test.demo.exception.BookmarkNotFoundException;
import com.alok.spring.rest.test.demo.exception.UserNotFoundException;

@RestController
@RequestMapping("/{userId}/bookmarks")
public class BookmarkRestController {
	private final BookmarkRepository bookmarkRepository;
	private final AccountRepository accountRepository;

	//@Autowired - since the same is included in constructor so no need filed autowired
	private BookmarkValidator bookmarkValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(bookmarkValidator);
	}

	@Autowired
	BookmarkRestController(BookmarkRepository bookmarkRepository, AccountRepository accountRepository, BookmarkValidator bookmarkValidator) {
		this.bookmarkRepository = bookmarkRepository;
		this.accountRepository = accountRepository;
		this.bookmarkValidator = bookmarkValidator;
	}

	@RequestMapping(method = RequestMethod.GET)
	Collection<Bookmark> readBookmarks(@PathVariable String userId) {
		this.validateUser(userId);
		//try {Thread.sleep(20000);} catch (Exception e) {}
		return this.bookmarkRepository.findByAccountUsername(userId);
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@PathVariable String userId, 
			@RequestBody @Validated Bookmark input) {

		this.validateUser(userId);

		Optional<Account> account = this.accountRepository.findByUsername(userId);
		if (account.isPresent()) {
			Bookmark result = bookmarkRepository.save(new Bookmark(account.get(), input.uri, input.description));

			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId())
					.toUri();

			return ResponseEntity.created(location).build();
		} else {
			return ResponseEntity.noContent().build();
		}

		/*
		 * Java 1.8 return this.accountRepository .findByUsername(userId)
		 * .map(account -> { Bookmark result = bookmarkRepository.save(new
		 * Bookmark(account, input.uri, input.description));
		 * 
		 * URI location = ServletUriComponentsBuilder
		 * .fromCurrentRequest().path("/{id}")
		 * .buildAndExpand(result.getId()).toUri();
		 * 
		 * return ResponseEntity.created(location).build(); })
		 * .orElse(ResponseEntity.noContent().build());
		 */

	}

	@RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}", produces = "application/json")
	Bookmark readBookmark(@PathVariable String userId, @PathVariable Long bookmarkId) {
		this.validateUser(userId);
		// return this.bookmarkRepository.findOne(bookmarkId);

		Optional<Bookmark> bookmark = this.bookmarkRepository.findByAccountUsernameAndId(userId, bookmarkId);
		if (bookmark.isPresent()) {
			return bookmark.get();
		} else {
			throw new BookmarkNotFoundException(userId, bookmarkId);
		}

	}

	private void validateUser(String userId) {
		System.out.println("User count: " + accountRepository.count());
		// this.accountRepository.findByUsername(userId).orElseThrow((Supplier<?
		// extends UserNotFoundException>) new UserNotFoundException(userId));
		if (!this.accountRepository.findByUsername(userId).isPresent())
			throw new UserNotFoundException(userId);

		/*
		 * this.accountRepository.findByUsername(userId).orElseThrow( () -> new
		 * UserNotFoundException(userId));
		 */
	}
}
