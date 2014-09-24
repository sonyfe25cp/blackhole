package com.omartech.mmaker;

import com.omartech.mmaker.local.tools.SchedulingTasks;
import com.omartech.mmaker.local.tools.SiteMaker;

/**
 * 用于crontab
 * @author Chen Jie
 * @date 2014年9月24日
 */
public class AutoRun {

	public static void main(String[] args) {
		SchedulingTasks st = new SchedulingTasks();
		st.parseLogsEveryHour();
		SiteMaker sm = new SiteMaker();
		sm.homePage();
	}
}
