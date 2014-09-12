package com.omartech.mmaker.local.tools;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omartech.mmaker.model.DNSLog;
import com.omartech.mmaker.utils.Utils;

public class TimelyParseLog {

	static Logger logger = LoggerFactory.getLogger(TimelyParseLog.class);
	
	public static void main(String[] args) {
//		String filePath = "/Users/omar/data/log20140817";
		String filePath = "/tmp/log";
		TimelyParseLog tp = new TimelyParseLog();
		List<DNSLog> loglist = tp.parse(filePath);
		Map<String, Integer> statMostFavorSite = tp.statMostFavorSite(loglist);
		List<Entry<String, Integer>> array1 = new ArrayList<>(statMostFavorSite.entrySet());
		Map<String, Integer> statMostRequestIP = tp.statMostRequestIP(loglist);
		List<Entry<String, Integer>> array2 = new ArrayList<>(statMostRequestIP.entrySet());

		Utils.sortMapStringAndInteger(array1);
		Utils.sortMapStringAndInteger(array2);
		Utils.debugEntryArray(array1);
		Utils.debugEntryArray(array2);
		for(Entry<String, Integer> entry : array2){
			System.out.println("['"+entry.getKey()+"',"+entry.getValue()+"],");
		}
	}

	Map<String, Integer> statMostFavorSite(List<DNSLog> loglist) {
		Map<String, Integer> map = new HashMap<>();
		for (DNSLog log : loglist) {
			String host = log.getHost();
			Integer integer = map.get(host);
			if (integer == null) {
				integer = 0;
			}
			integer++;
			map.put(host, integer);
		}
		return map;
	}

	Map<String, Integer> statMostRequestIP(List<DNSLog> loglist) {
		Map<String, Integer> map = new HashMap<>();
		for (DNSLog log : loglist) {
			String requestIp = log.getRequestIp();
			Integer integer = map.get(requestIp);
			if (integer ==null) {
				integer = 0;
			}
			integer++;
			map.put(requestIp, integer);
		}
		return map;
	}

	List<DNSLog> parse(String filePath) {
		List<DNSLog> list = new ArrayList<>();
		try {
			List<String> readLines = FileUtils.readLines(new File(filePath));
			for (String line : readLines) {
				if (line.contains("want to")) {
					DNSLog log = parseLog(line);
					if(log != null){
						list.add(log);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	static Pattern pattern = Pattern.compile("## ([\\d.])+ want to visit ");

	static DNSLog parseLog(String line) {
		DNSLog log = null;
		String time = line.substring(0, line.indexOf(","));
		String sub = line.substring(line.lastIndexOf("#") + 1);
		String after = sub.replace("want to visit ", "");
		String[] split = after.trim().split(" ");
		String requestIp = split[0];
		String host = split[1];
		boolean userful = isUserful(host);
		log = new DNSLog(requestIp, host);
		log.setUserful(userful);
		try {
			Date requestTime = DateUtils.parseDate(time, "yy-MM-dd hh:mm:ss");
			log.setTime(requestTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return log;
	}

	static boolean isUserful(String host){
		String[] split = host.split(".");
		logger.info("size : {}", split.length);
		if(split.length < 3){
			return true;
		}else{
			return false;
		}
	}
	
	
	// public static void main(String[] args) {
	// String str =
	// "14-08-13 10:00:34,553 INFO  us.codecraft.blackhole.connector.UDPConnectionWorker(UDPConnectionWorker.java:41) ## 123.116.51.53 want to visit videolectures.net.";
	// DNSLog log = parseLog(str);
	// System.out.println(log);
	// }

}
