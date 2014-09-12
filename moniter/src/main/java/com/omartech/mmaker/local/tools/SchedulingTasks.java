package com.omartech.mmaker.local.tools;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
public class SchedulingTasks{

//	@Autowired
//	private SystemVar sv;
	
	@Scheduled(fixedRate = 5000)
	public void parseLogs() {
		System.out.println("haha");
		
//		String now = DateFormatUtils.format(new Date(), "yyyy-MM-dd-HH-mm");
//		System.out.println(now);
//		String logFolder = sv.getLogFolder();
//		for(File file : new File(logFolder).listFiles()){
//			String name = file.getName();
//			System.out.println(name);
//		}
		
	}

}
