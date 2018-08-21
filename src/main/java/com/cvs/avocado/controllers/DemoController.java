package com.cvs.avocado.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

	@GetMapping("/secure")
	@PreAuthorize("hasAuthority('READ')")
	public void sayHelloFriend() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("Current User: "+authentication.getName());
		System.out.println("Hello Friend");
	}
	
	@GetMapping("/unsecure")
	public void sayHelloStranger() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("Current User: "+authentication.getName());
		System.out.println("Hello Stranger");
	}
}
