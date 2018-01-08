package com.alok.spring.rest.test.demo.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import org.apache.tomcat.util.buf.StringUtils;

@Entity
public class ACUser {

	@Id
    @GeneratedValue
    private Long id;

    //@Column(name = "USERNAME", unique = true)
	String username;
	String password;
	
	
    @ManyToMany(fetch = FetchType.EAGER, 	cascade = CascadeType.ALL)
    @JoinTable(name = "acuser_acrole", 
    	joinColumns = @JoinColumn(name = "acuser_id", referencedColumnName = "id"), 
    	inverseJoinColumns = @JoinColumn(name = "acrole_id", referencedColumnName = "id"))
    private Set<ACRole> roles = new HashSet<>();
	
    public ACUser() { // jpa only
    }
    
	public String getUserName() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getRoles() {
		Set<String> roleList = new HashSet<>();
		for (ACRole role: roles) {
			roleList.add(role.getRole());
		}
		
		return StringUtils.join(roleList, ',');
	}
}
