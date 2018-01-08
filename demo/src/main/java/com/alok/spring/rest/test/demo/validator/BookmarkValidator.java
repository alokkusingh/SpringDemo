package com.alok.spring.rest.test.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.alok.spring.rest.test.demo.entity.Bookmark;

@Component
public class BookmarkValidator implements Validator {

	@Override
	public boolean supports(Class<?> obj) {
		return Bookmark.class.equals(obj);
	}

	@Override
	public void validate(Object obj, Errors err) {
		ValidationUtils.rejectIfEmpty(err, "description", "bookmark.description.empty");	
		ValidationUtils.rejectIfEmpty(err, "uri", "bookmark.uri.empty");	
		//ValidationUtils.rejectIfEmpty(err, "account", "bookmark.account.empty");
	}

}
