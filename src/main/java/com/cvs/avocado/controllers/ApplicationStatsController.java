package com.cvs.avocado.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cvs.avocado.models.AppInfo;
import com.cvs.avocado.models.ClientStats;
import com.cvs.avocado.models.ServerStats;
import com.cvs.avocado.repositories.ApplicationStatsRepository;
import com.cvs.avocado.security.HttpServletFacade;

@RestController
public class ApplicationStatsController {

	private static final Logger log = LogManager.getLogger();

	@Autowired
	ApplicationStatsRepository appStatsRepository;

	@Autowired
	HttpServletFacade httpServlet;

	@PostMapping("/adpl/appinfo/create.ws")
	@PreAuthorize("hasAuthority('WRITE')")
	public void insertAppInfo(@RequestBody List<AppInfo> appInfoList) {
		String managementIP = this.httpServlet.getRemoteAddress();
		for(AppInfo appInfo : appInfoList) {
			appInfo.setManagementIP(managementIP);
			boolean result = this.appStatsRepository.insertAppInfo(appInfo);
			if(result) {
				log.info("Following appinfo inserted successfully in db: " + appInfo.toString());
			} else {
				log.error("Failed to insert following appinfo in db: " + appInfo.toString());
			}
		}
	}

	@PostMapping("/adpl/serverstats/create.ws")
	@PreAuthorize("hasAuthority('WRITE')")
	public void insertServerStats(@RequestBody List<ServerStats> serverStatsList) {
		String managementIP = this.httpServlet.getRemoteAddress();
		for(ServerStats serverStats : serverStatsList) {
			serverStats.setManagementIP(managementIP);
			boolean result = this.appStatsRepository.insertServerStats(serverStats);
			if(result) {
				log.info("Following server stats inserted successfully in db: " + serverStats.toString());
			} else {
				log.error("Failed to insert following server stats in db: " + serverStats.toString());
			}
		}
	}

	@PostMapping("/adpl/clientstats/create.ws")
	@PreAuthorize("hasAuthority('WRITE')")
	public void insertClientStats(@RequestBody List<ClientStats> clientStatsList) {
		for(ClientStats clientStats : clientStatsList) {
			boolean result = this.appStatsRepository.insertClientStats(clientStats);
			if(result) {
				log.info("Following client stats inserted successfully in db: " + clientStats.toString());
			} else {
				log.error("Failed to insert following client stats in db: " + clientStats.toString());
			}
		}
	}
	
	public void deleteAppInfo(@RequestBody AppInfo appInfo) {
		boolean result = this.appStatsRepository.deleteAppInfo(appInfo);
	}

}
