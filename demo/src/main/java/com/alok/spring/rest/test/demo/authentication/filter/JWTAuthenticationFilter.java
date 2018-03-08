package com.alok.spring.rest.test.demo.authentication.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.alok.spring.rest.test.demo.entity.ACUser;
import com.alok.spring.rest.test.demo.jpa.ACUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private static final long EXPIRATION_TIME = 30;

	private AuthenticationManager authenticationManager;
	
	@Autowired
	ACUserRepository acuserRepository;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
	
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            ACUser user = new ObjectMapper().readValue(req.getHeader("Authorization"), ACUser.class);
        	
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                    		user.getUserName(),
                    		user.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = Jwts.builder()
                .setSubject(((ACUser) auth.getPrincipal()).getUserName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, "alok".getBytes())
                .compact();
        
        //res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        res.addHeader("ALOK_JWT", "JWT" + token);
    }
}
