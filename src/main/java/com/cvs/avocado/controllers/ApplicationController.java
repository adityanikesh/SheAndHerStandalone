package com.cvs.avocado.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cvs.avocado.models.Application;
import com.cvs.avocado.models.SLAResponse;
import com.cvs.avocado.repositories.ApplicationLicenceRepository;
import com.cvs.avocado.repositories.ApplicationRepository;
import com.cvs.avocado.repositories.GlobalSettingsRepository;
import com.cvs.avocado.repositories.ServerInformationRepository;
import com.cvs.avocado.security.HttpServletFacade;
import com.cvs.avocado.utils.OrchestratorConstants;
import com.cvs.avocado.utils.OrchestratorMessageUtil;
import com.cvs.avocado.workers.SendAppLicenceWorker;

@RestController
//@RequestMapping("/api/application")
public class ApplicationController {

	private static final Logger log = LogManager.getLogger();

	@Autowired
	ApplicationRepository applicationRepository;

	@Autowired
	HttpServletFacade httpServlet;

	@Autowired
	GlobalSettingsRepository globalSettingsRepository;

	@Autowired
	ApplicationLicenceRepository applLicRepository;

	@Autowired
	ServerInformationRepository serverInfoRepository;

	@PostMapping("/adpl/detected/application/create.ws")
//	@PostMapping("/detect.ws")
	@PreAuthorize("hasAuthority('WRITE')")
	public SLAResponse detectApplication(@RequestBody Application application) {
		String managementIP = this.httpServlet.getRemoteAddress();
		application.setManagementIP(managementIP);

		if(application.getCompliance() == null || application.getCompliance() == "") {
			application.setCompliance("Default");
		}

		if (application.getArgsSha2() != null && !application.getArgsSha2().equals("")) {
			String sha2Checksum = calculateSha2Checksum(application.getSha2Checksum(), application.getArgsSha2());
			application.setSha2Checksum(sha2Checksum);
		}

		Map<String, Object> out = this.applicationRepository.callSlaCheckSP(application);
		int slaResult = (Integer)out.get("slaResult");

		if(slaResult == OrchestratorConstants.SLA_MD5_MISMATCH_CODE
				|| slaResult == OrchestratorConstants.SLA_SHA2_MISMATCH_CODE
				|| slaResult == OrchestratorConstants.SLA_NO_RECORD_IN_DB_CODE) {
			//code for custom path check
		} else if(slaResult == OrchestratorConstants.SLA_APP_NOT_SYNC_CODE){
			this.sendAppLicence(managementIP);
		}

		SLAResponse slaResponse = new SLAResponse(slaResult, OrchestratorMessageUtil.getSLAMessage(slaResult));		
		slaResponse.setCid(application.getCid());
		slaResponse.setDid(application.getDid());
		slaResponse.setAdplAppId(application.getAdplAppId());
		slaResponse.setPid(application.getPid());
		slaResponse.setDiscoverFlag(this.globalSettingsRepository.getDiscoverOnlyMode());
		return slaResponse;
	}

	@PostMapping("/api/application/register.ws")
	@PreAuthorize("hasAuthority('WRITE')")
	public Map<Integer, String> registerApplication(@RequestBody List<Application> applicationList) {
		Map<Integer, String> appListRegResult = new LinkedHashMap<Integer, String>();

		try {
			for(Application application: applicationList) {
				Map<String, Object> appRegResult = this.applicationRepository.registerApplication(application);
				int regResultCode = (Integer)appRegResult.get("regResultCode");
				appListRegResult.put(application.getAppId(), OrchestratorMessageUtil.getAppRegMessage(regResultCode));
				if(regResultCode != OrchestratorConstants.APP_REG_SUCCESS_REGISTRATION_CODE) {
					log.error("Failed to register following application: " + application.toLog() + "Reason: " + OrchestratorMessageUtil.getAppRegMessage(regResultCode));
				} else {
					log.info("Registration result of the following application: " + application.toLog() + "Result: " + OrchestratorMessageUtil.getAppRegMessage(regResultCode));
					for(Object managementIP : (List<?>)appRegResult.get("managementIPList")) {
						this.sendAppLicence((String)managementIP);
					}
				}
			}
		} catch (Exception ex) {
			log.error("Exception ocured while registering applications: Reason: " + ex.getMessage());
		}
		return appListRegResult;
	}

	@PostMapping("/update.ws")
	@PreAuthorize("hasAuthority('WRITE')")
	public void updateApplication(@RequestBody Application application) {
		int result = this.applicationRepository.updateApplication(application);
		if(result > 0) {
			System.out.println("Application updated successfully");
		} else {
			System.out.println("Failed to update application");
		}
	}

	@DeleteMapping("/delete.ws")
	@PreAuthorize("hasAuthority('WRITE')")
	public void deleteApplication(String appId) {
		int result = this.applicationRepository.deleteApplication(appId);
		if(result > 0) {
			log.info("Application deleted successfully");
		}
	}

	private void sendAppLicence(String managementIP) {
		SendAppLicenceWorker sendAppLicWorker = new SendAppLicenceWorker(managementIP, applLicRepository, serverInfoRepository);
		sendAppLicWorker.start();
	}

	public String calculateSha2Checksum(String exeChecksum, String argsChecksum) {
		char[] chars = new char[exeChecksum.length()];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = toHex(fromHex(exeChecksum.charAt(i)) ^ fromHex(argsChecksum.charAt(i)));
		}
		return new String(chars).toLowerCase();
	}

	private static int fromHex(char c) {
		if (c >= '0' && c <= '9') {
			return c - '0';
		}
		if (c >= 'A' && c <= 'F') {
			return c - 'A' + 10;
		}
		if (c >= 'a' && c <= 'f') {
			return c - 'a' + 10;
		}
		throw new IllegalArgumentException();
	}

	private static char toHex(int nybble) {
		if (nybble < 0 || nybble > 15) {
			throw new IllegalArgumentException();
		}
		return "0123456789ABCDEF".charAt(nybble);
	}

}
