package com.omartech.mmaker;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omartech.mmaker.local.tools.SchedulingTasks;
import com.omartech.mmaker.local.tools.SiteMaker;

/**
 * 用于crontab
 * @author Chen Jie
 * @date 2014年9月24日
 */
public class AutoRun {
	static Logger logger = LoggerFactory.getLogger(AutoRun.class);
	public static void main(String[] args) {
		SchedulingTasks st = new SchedulingTasks();
		st.parseLogsEveryHour();
		SiteMaker sm = new SiteMaker();
		sm.homePage();
		logger.info("自动生成完毕: {}", new DateFormatUtils().format(new Date(), "yyyy-MM-dd hh"));
	}
}
