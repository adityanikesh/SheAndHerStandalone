package com.cvs.avocado.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cvs.avocado.repositories.ApplicationStatsRepository;

@Component
public class ScheduledTasks {
	
	@Autowired
	ApplicationStatsRepository appStatsRepository;

//	@Scheduled(fixedDelay=6000)
//	public void removeTokenOfDeadAppmanager() {
//		System.out.println("Inside the scheduler");
//	}
	
	@Scheduled(fixedDelay=60000)
	public void updateStatsOfStoppedApplications() {
		this.appStatsRepository.updateStatsOfStoppedApplications();
	}
}
