package com.omartech.mmaker.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class SystemVar {

	private String logFolder = "/tmp/blackhole/logs/";
	
	private String htmlFolder = "/tmp/www/html/";

	public String getLogFolder() {
		return logFolder;
	}

	public void setLogFolder(String logFolder) {
		this.logFolder = logFolder;
	}

	public String getHtmlFolder() {
		return htmlFolder;
	}

	public void setHtmlFolder(String htmlFolder) {
		this.htmlFolder = htmlFolder;
	}

}
