package com.omartech.mmaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.omartech.mmaker.local.tools.SchedulingTasks;
import com.omartech.mmaker.utils.SystemVar;

public class BootMachine {
	
	@Autowired
	SystemVar systemVar;
	
	void testSV(){
		String logFolder = systemVar.logFolder;
		System.out.println(logFolder);
	}
	
	public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath*:/spring/applicationContext*.xml");
        BootMachine crawler = context.getBean("boot", BootMachine.class);
        crawler.testSV();
        
        System.out.println("gogo");
        
        SpringApplication.run(SchedulingTasks.class);
    }
}
