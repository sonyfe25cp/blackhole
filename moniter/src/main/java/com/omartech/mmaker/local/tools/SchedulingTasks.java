package com.omartech.mmaker.local.tools;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.omartech.mmaker.model.DNSLog;
import com.omartech.mmaker.utils.ChartUtils;
import com.omartech.mmaker.utils.DNSLogParser;
import com.omartech.mmaker.utils.SystemVar;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@EnableScheduling
public class SchedulingTasks {

	static Logger logger = LoggerFactory.getLogger(SchedulingTasks.class);

	static String dayFormat = "yyyy-MM-dd";
	static String hourFormat = "yyyy-MM-dd-HH";
	static String logNameFormat = "yyyy-MM-dd-HH-mm";
	

	public static void main(String[] args) {
		SchedulingTasks st = new SchedulingTasks();
		st.parseLogsEveryHour();
	}
	
	/**
	 * 每小时自动解析，并生成对应的html
	 * 每天的报告
	 */
	@Scheduled(fixedRate = 60000)
	public void parseLogsEveryHour() {
		logger.debug("begin to parse logs for every hour");

		Set<String> logs = getTotalFile(SystemVar.logFolder);
		List<DNSLog> dnsLogsTotal = new ArrayList<>();//for whole day
		String today = DateFormatUtils.format(new Date(), dayFormat);
		for (String date : logs) {
			String day = parseDayFromLogFormat(date);
			String hour = parseHourFromLogFormat(date);
			String siteOutput = pathForPerHour(day) + File.separator + hour + "-sites.html";
			logger.debug(siteOutput);
			File siteOutputHtml = new File(siteOutput);
			if (siteOutputHtml.exists()) {
				logger.debug("{} 已存在，不需要重新算", siteOutput);
//				continue;
			}
			
			String ipOutput = pathForPerHour(day) + File.separator + hour + "-ips.html";
			logger.debug(ipOutput);
			File ipOutputHtml = new File(ipOutput);
			if (ipOutputHtml.exists()) {
				logger.debug("{} 已存在，不需要重新算", ipOutput);
//				continue;
			}
			
			String fullpath = SystemVar.logFolder + "log." + date;
			logger.debug("解析日志文件：{}", fullpath);
			List<DNSLog> dnsLogs = DNSLogParser.parse(fullpath);
			if(today.equals(day)){
				dnsLogsTotal.addAll(dnsLogs);
			}
			createPieAboutSites(day, siteOutputHtml, dnsLogs);
			createPieAboutIps(day, ipOutputHtml, dnsLogs);
		}
		String siteOutput = pathForPerHour(today) + File.separator + today+ ".sites.html";
		createPieAboutSites(today, new File(siteOutput), dnsLogsTotal);
		
		String ipOutput = pathForPerHour(today) + File.separator + today+ ".ips.html";
		createPieAboutIps(today, new File(ipOutput), dnsLogsTotal);
		
		
	}

	private void createPieAboutSites(String day, File outputHtml,
			List<DNSLog> dnsLogs) {
		sortByDate(dnsLogs);
		TimelyParseLog tpl = new TimelyParseLog();
		Map<String, Integer> map = tpl.mostFavorSiteJson(dnsLogs);
		String mostFavorSiteJson = ChartUtils.map2PieJson(map);

		File dayFolder = new File(pathForPerHour(day));
		if (!dayFolder.exists()) {
			dayFolder.mkdirs();
		}
		logger.debug("站点json: {}", mostFavorSiteJson);
		if (mostFavorSiteJson.length() > 0) {
			write2File(mostFavorSiteJson, "perHour.ftl", outputHtml, 	"网址记录", dnsLogs);
		}
	}
	private void createPieAboutIps(String day, File outputHtml,
			List<DNSLog> dnsLogs) {
		sortByDate(dnsLogs);
		TimelyParseLog tpl = new TimelyParseLog();
		Map<String, Integer> map = tpl.mostFavorIpJson(dnsLogs);
		String mostFavorSiteJson = ChartUtils.map2PieJson(map);

		File dayFolder = new File(pathForPerHour(day));
		if (!dayFolder.exists()) {
			dayFolder.mkdirs();
		}
		logger.debug("IP json: {}", mostFavorSiteJson);
		if (mostFavorSiteJson.length() > 0) {
			write2File(mostFavorSiteJson, "perHour.ftl", outputHtml, 	"IP记录", dnsLogs);
		}
	}
	
	String pathForPerHour(String day) {
		return SystemVar.htmlOutputFolder + "pages" + File.separator + "day" +File.separator
				+ day;
	}
	
	void sortByDate(List<DNSLog> dnsLogs){
		Collections.sort(dnsLogs, new Comparator<DNSLog>() {

			@Override
			public int compare(DNSLog o1, DNSLog o2) {
				Date time = o1.getTime();
				Date time2 = o2.getTime();
				return time2.compareTo(time);
			}
		});
	}

	/**
	 * 2014-09-14-20-57
	 * 
	 * @param date
	 * @return
	 */
	private static String parseDayFromLogFormat(String date) {
		String res = null;
		try {
			Date parseDate = DateUtils.parseDate(date, logNameFormat);
			res = DateFormatUtils.format(parseDate, dayFormat);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return res;
	}

	private static String parseHourFromLogFormat(String date) {
		String res = null;
		try {
			Date parseDate = DateUtils.parseDate(date, logNameFormat);
			res = DateFormatUtils.format(parseDate, hourFormat);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return res;
	}

	Set<String> getTotalFile(String folderPath) {
		File folder = new File(folderPath);
		Set<String> result = new HashSet<>();
		for (File file : folder.listFiles()) {
			String name = file.getName();
			if (name.contains(".")) {
				String date = name.substring(name.lastIndexOf(".") + 1);
				result.add(date);
			}
		}
		return result;
	}

	void write2File(String json, String templateName, File output,
			String title, List<DNSLog> dnsLogs) {
		try {
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(SystemVar.templateFolder));
			Template template = cfg.getTemplate(templateName);
			StringWriter stringWriter = new StringWriter();
			Map<String, Object> args = new HashMap<>();
			args.put("json", json);
			args.put("today", DateFormatUtils.format(new Date(), dayFormat));
			args.put("title", title);
			int size = dnsLogs.size();
			if(size > 1000){
				dnsLogs = dnsLogs.subList(0, 1000);
			}
			args.put("dnsLogs", dnsLogs);
			try {
				template.process(args, stringWriter);
			} catch (TemplateException e) {
				e.printStackTrace();
			}
			FileUtils.write(output, stringWriter.toString());
			stringWriter.close();
		} catch (IOException ignore) {
			ignore.printStackTrace();
		}
	}

}
