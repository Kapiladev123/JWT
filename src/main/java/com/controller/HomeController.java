package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.AuthRequest;
import com.entity.UserInfo;
import com.service.JwtService;
import com.service.UserInfoService;

@RestController
@RequestMapping("/auth")
public class HomeController {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserInfoService Service;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	 @GetMapping("/welcome") 
	    public String welcome() { 
	        return "Welcome this endpoint is not secure"; 
	    } 
	 
	 @PostMapping("/addNewUser") 
	    public String addNewUser(@RequestBody UserInfo userInfo) { 
	        return Service.addUser(userInfo); 
	    } 
	 

	    @GetMapping("/user/userProfile") 
	    @PreAuthorize("hasAuthority('ROLE_USER')") 
	    public String userProfile() { 
	        return "Welcome to User Profile"; 
	    } 
	    
	    @GetMapping("/admin/adminProfile") 
	    @PreAuthorize("hasAuthority('ROLE_ADMIN')") 
	    public String adminProfile() { 
	        return "Welcome to Admin Profile"; 
	    } 
	    
	    @PostMapping("/generateToken") 
	    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) { 
	        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())); 
	        if (authentication.isAuthenticated()) { 
	            return jwtService.generateToken(authRequest.getUsername()); 
	        } else { 
	            throw new UsernameNotFoundException("invalid user request !"); 
	        } 
	    } 
	 
}
