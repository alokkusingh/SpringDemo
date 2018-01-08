package com.alok.spring.rest.test.demo.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

//@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookmarkNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BookmarkNotFoundException(String userId, Long bookmarkId) {
		super("could not find bookmark for user '" + userId + "' and bookamrk id '" + bookmarkId + "'.");
	}
}
