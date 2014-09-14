package com.omartech.mmaker.local.tools;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.google.gson.Gson;
import com.omartech.mmaker.template.HomePage;
import com.omartech.mmaker.utils.SystemVar;
import com.omartech.mmaker.utils.Utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 用于生成站点目录及相应页面
 * @author Chen Jie
 * @date 2014年9月14日
 */
public class SiteMaker {

	public static void main(String[] args) {
		SiteMaker sm =  new SiteMaker();
		sm.homePage();
	}
	
	void run(){
		
	}
	
	/**
	 * 
	 */
	void about(){
		Gson gson = new Gson();
		String resouce = Utils.getResouce("texts/homepage");
		HomePage homepage = gson.fromJson(resouce, HomePage.class);
		
	}
	
	/**
	 * 生成首页
	 */
	void homePage(){
		Gson gson = new Gson();
		String resouce = Utils.getResouce("texts/homepage");
		HomePage homepage = gson.fromJson(resouce, HomePage.class);
		write2HomePage("homepage.ftl", "index.html", DateFormatUtils.format(new Date(), "yyyy-MM-dd"), homepage);
		write2HomePage("aboutUs.ftl", "about.html", DateFormatUtils.format(new Date(), "yyyy-MM-dd"), homepage);
		write2HomePage("contactUs.ftl", "contact.html", DateFormatUtils.format(new Date(), "yyyy-MM-dd"), homepage);
	}
	void write2HomePage(String templateName, String output, String date, HomePage homepage) {
		try {
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(SystemVar.templateFolder));
			Template template = cfg.getTemplate(templateName);
			StringWriter stringWriter = new StringWriter();
			Map<String, Object> args = new HashMap<>();
			args.put("date", date);
			args.put("homepage", homepage);
			try {
				template.process(args, stringWriter);
			} catch (TemplateException e) {
				e.printStackTrace();
			}
			FileUtils.write(new File(SystemVar.htmlOutputFolder+output), stringWriter.toString());
			stringWriter.close();
		} catch (IOException ignore) {
			ignore.printStackTrace();
		}
	}
}
