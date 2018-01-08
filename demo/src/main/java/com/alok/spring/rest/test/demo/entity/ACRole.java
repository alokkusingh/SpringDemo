package com.alok.spring.rest.test.demo.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class ACRole {
	
	@Id
    @GeneratedValue
    private Long id;

	private String role;
	private String description;
	
	//the below "roles" refers the varibale name deined in AUUserRole class
	//@ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
	@ManyToMany(mappedBy = "roles")
	private Set<ACUser> users = new HashSet<>();
	
    public ACRole() { // jpa only
    }
    
    public Long getId() {
    	return id;
    }

    public String getRole() {
    	return role;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public Set<ACUser> getUsers() {
    	return users;
    }
}
