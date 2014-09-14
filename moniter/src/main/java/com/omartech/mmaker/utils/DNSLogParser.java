package com.omartech.mmaker.utils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omartech.mmaker.model.DNSLog;

public class DNSLogParser {
	static Logger logger = LoggerFactory.getLogger(DNSLogParser.class);

	
//	public static List<DNSLog> filterNoise(List<DNSLog> logs){
//		List<DNSLog> list = new ArrayList<>();
//		
//	}
	
	
	public static List<DNSLog> parse(String filePath) {
		List<DNSLog> list = new ArrayList<>();
		try {
			List<String> readLines = FileUtils.readLines(new File(filePath));
			for (String line : readLines) {
				if (line.contains("want to")) {
					DNSLog log = parseLog(line);
					if (log != null) {
						list.add(log);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	private static Pattern pattern = Pattern.compile("## ([\\d.])+ want to visit ");

	public static DNSLog parseLog(String line) {
		DNSLog log = null;
		String time = line.substring(0, line.indexOf(","));
		String sub = line.substring(line.lastIndexOf("#") + 1);
		String after = sub.replace("want to visit ", "");
		String[] split = after.trim().split(" ");
		String requestIp = split[0];
		String host = split[1];
		host = host.substring(0, host.length()-1);
		boolean userful = isUserful(host);
		log = new DNSLog(requestIp, host);
		log.setUseful(userful);
		try {
			Date requestTime = DateUtils.parseDate(time, "yy-MM-dd hh:mm:ss");
			log.setTime(requestTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return log;
	}

	/**
	 * 只要两截的域名
	 * 开头带+的域名请求是什么？
	 * +css-tricks.com
	 * +getbootstrap.com
	 * +ghbtns.com
	 * +github.com
	 * @param host
	 * @return
	 */
	public static boolean isUserful(String host) {
		String[] split = host.split("\\.");
//		logger.info("size : {}", split.length);
		if (split.length == 2) {
			if(host.startsWith("+")){
				System.out.println(host);
				return false;
			}else{
				return true;
			}
		} else {
			return false;
		}
	}
}
