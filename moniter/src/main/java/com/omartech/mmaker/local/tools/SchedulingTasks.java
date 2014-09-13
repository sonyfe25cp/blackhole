package com.omartech.mmaker.local.tools;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.omartech.mmaker.model.DNSLog;
import com.omartech.mmaker.utils.DNSLogParser;
import com.omartech.mmaker.utils.SystemVar;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@EnableScheduling
public class SchedulingTasks {

	static Logger logger = LoggerFactory.getLogger(SchedulingTasks.class);
	
	// @Autowired
	private SystemVar sv = new SystemVar();

	static String logNameFormat = "yyyy-MM-dd-HH-mm";
	static String logFolder = "/tmp/logs/";
	static String htmlFolder = "/tmp/html/";
	static String templateFolder = "/Users/omar/workspace/blackhole/moniter/template/";

	public static void main(String[] args) {
		SchedulingTasks st = new SchedulingTasks();
		st.parseLogs();
	}
	
	
	/**
	 * 每小时自动解析，并生成对应的html
	 */
	@Scheduled(fixedRate = 5000)
	public void parseLogs() {
		System.out.println("haha");

		Set<String> htmls = getTotalFile(htmlFolder);
		Set<String> logs = getTotalFile(logFolder);
		List<String> needDo = new ArrayList<>();
		for (String log : logs) {
			if (!htmls.contains(log)) {
				needDo.add(log);
			}
		}

		for (String date : needDo) {
			String fullpath = logFolder+ "log." + date;
			logger.debug("begin to parse {}", fullpath);
			List<DNSLog> dnsLogs = DNSLogParser.parse(fullpath);
			TimelyParseLog tpl = new TimelyParseLog();
			String mostFavorSiteJson = tpl.mostFavorSiteJson(dnsLogs);
			if(mostFavorSiteJson.length() > 0){
				String output = htmlFolder + date + ".html";
				write2File(mostFavorSiteJson, "pie.ftl", new File(output), date, "网址记录");
			}
		}

	}

	Set<String> getTotalFile(String folderPath) {
		File folder = new File(folderPath);
		Set<String> result = new HashSet<>();
		for (File file : folder.listFiles()) {
			String name = file.getName();
			if(name.contains(".")){
				String date = name.substring(name.lastIndexOf(".")+1);
				result.add(date);
			}
		}
		return result;
	}

	void write2File(String json, String templateName, File output, String date, String title) {
		try {
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(templateFolder));
			Template template = cfg.getTemplate(templateName);
			StringWriter stringWriter = new StringWriter();
			Map<String, Object> args = new HashMap<>();
			args.put("json", json);
			args.put("date", date);
			args.put("title", title);
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
