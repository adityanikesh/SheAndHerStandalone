package com.cvs.avocado.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cvs.avocado.models.Client;
import com.cvs.avocado.security.AuthenticationFacade;
import com.cvs.avocado.services.ClientService;

@RestController
@RequestMapping("/api/client")
public class ClientController {
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	AuthenticationFacade authFacade;

	@GetMapping("/getAllClients")
	@PreAuthorize(value="hasAuthority('READ')")
	public List<Client> getAllClients() {
		System.out.println("Current Username: "+ authFacade.getCurrentUsername());
		return this.clientService.getAllClients();
	}
}
