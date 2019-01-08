package com.cvs.avocado.schedulers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	@Scheduled(fixedDelay=6000)
	public void removeTokenOfDeadAppmanager() {
		System.out.println("Inside the scheduler");
	}
	
	@Scheduled(fixedDelay=60000)
	public void removeStatsOfStoppedApplications() {
		
	}
}
