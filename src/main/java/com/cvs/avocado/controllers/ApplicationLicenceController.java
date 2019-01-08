package com.cvs.avocado.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cvs.avocado.models.Application;
import com.cvs.avocado.repositories.ApplicationRepository;
import com.cvs.avocado.security.HttpServletFacade;
import com.cvs.avocado.services.ApplicationLicenceService;
import com.cvs.avocado.utils.OrchestratorConstants;

@RestController
public class ApplicationLicenceController {
	
	@Autowired
	ApplicationLicenceService applicationLicenceService;
	
	@Autowired
	ApplicationRepository applicationRepository;
	
	@Autowired
	HttpServletFacade httpServletFacade;

	@PostMapping("/adpl/app/lic/synch.ws")
	public Map<String, Object> getApplicationLicenceFile() {
		String managementIP = httpServletFacade.getRemoteAddress();
		List<Application> applicationList = this.applicationRepository.callApplicationLicenceFileGeneratorSP(managementIP);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("reqType", OrchestratorConstants.ZMQ_REQ_FETCH_LICENCE);
		response.put("data", applicationList);
		return response;
	}
}
