package com.omartech.mmaker.local.tools;

import java.util.Date;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
public class T {
	@Scheduled(fixedRate = 5000)
	public void parseLogs() {
		System.out.println(new Date()+" -- 5");
	}
	
	@Scheduled(fixedRate = 2000)
	public void parseLogs2() {
		System.out.println(new Date() +" -- 2");
	}
}
