package com.omartech.mmaker.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ChartUtils {
	/**
	 * 只取前100
	 * @param map
	 * @return
	 */
	public static String map2PieJson(Map<String, Integer> map) {
		List<Entry<String, Integer>> array = new ArrayList<>(map.entrySet());
		Utils.sortMapStringAndInteger(array, false);
//		Utils.debugEntryArray(array);
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Entry<String, Integer> entry : array) {
			if(i < 100){
				sb.append("['" + entry.getKey() + "'," + entry.getValue() + "],");
			}
//			else{
//				if (array.size() > SystemVar.minCount) {
//					if (entry.getValue() < SystemVar.minCount) {
//						continue;
//					}
//				}
//				sb.append("['" + entry.getKey() + "'," + entry.getValue() + "],");
//			}
			i ++;
		}
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}
}
