package com.omartech.mmaker.local.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omartech.mmaker.model.DNSLog;
import com.omartech.mmaker.utils.DNSLogParser;
import com.omartech.mmaker.utils.SystemVar;
import com.omartech.mmaker.utils.Utils;

public class TimelyParseLog {

	static Logger logger = LoggerFactory.getLogger(TimelyParseLog.class);

	public static void main(String[] args) {
		// String filePath = "/Users/omar/data/log20140817";
		String filePath = "/tmp/log";
		TimelyParseLog tp = new TimelyParseLog();
		List<DNSLog> loglist = DNSLogParser.parse(filePath);
		Map<String, Integer> statMostFavorSite = tp.statMostFavorSite(loglist, true);
		List<Entry<String, Integer>> array1 = new ArrayList<>(
				statMostFavorSite.entrySet());
		Map<String, Integer> statMostRequestIP = tp.statMostRequestIP(loglist);
		List<Entry<String, Integer>> array2 = new ArrayList<>(
				statMostRequestIP.entrySet());

		Utils.sortMapStringAndInteger(array1);
		Utils.sortMapStringAndInteger(array2);
		Utils.debugEntryArray(array1);
		Utils.debugEntryArray(array2);
		for (Entry<String, Integer> entry : array1) {
			System.out.println("['" + entry.getKey() + "'," + entry.getValue()
					+ "],");
		}
	}

	
	/**
	 * 按照站点分布的pie图
	 */
	public Map<String, Integer> mostFavorSiteJson(List<DNSLog> loglist){
		logger.debug("loglist.size : {}", loglist.size());
		Map<String, Integer> map = statMostFavorSite(loglist, true);
		return map;
	}
	/**
	 * 按照请求IP分布的PIE图
	 * @param loglist
	 * @return
	 */
	public Map<String, Integer> mostFavorIpJson(List<DNSLog> loglist){
		logger.debug("loglist.size : {}", loglist.size());
		Map<String, Integer> map = statMostRequestIP(loglist);
		return map;
	}

	public Map<String, Integer> statMostFavorSite(List<DNSLog> loglist, boolean filter) {
		Map<String, Integer> map = new HashMap<>();
		for (DNSLog log : loglist) {
			if(filter && log.isUseful()){
				String host = log.getHost();
				Integer integer = map.get(host);
				if (integer == null) {
					integer = 0;
				}
				integer++;
				map.put(host, integer);
			}
		}
		return map;
	}

	private Map<String, Integer> statMostRequestIP(List<DNSLog> loglist) {
		Map<String, Integer> map = new HashMap<>();
		for (DNSLog log : loglist) {
			String requestIp = log.getRequestIp();
			Integer integer = map.get(requestIp);
			if (integer == null) {
				integer = 0;
			}
			integer++;
			map.put(requestIp, integer);
		}
		return map;
	}

	// public static void main(String[] args) {
	// String str =
	// "14-08-13 10:00:34,553 INFO  us.codecraft.blackhole.connector.UDPConnectionWorker(UDPConnectionWorker.java:41) ## 123.116.51.53 want to visit videolectures.net.";
	// DNSLog log = parseLog(str);
	// System.out.println(log);
	// }

}
