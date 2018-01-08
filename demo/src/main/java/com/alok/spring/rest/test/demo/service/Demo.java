package com.alok.spring.rest.test.demo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class Demo {
	
	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<?> get() {
		
		return ResponseEntity.ok((String) "{\"msg\":\"Please try http://localhost:8080/demo/bookmarks\"}");
		//return ResponseEntity.badRequest().build();
		//return ResponseEntity.noContent().build();
	}
}
