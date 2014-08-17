package com.omartech.mmaker.utils;

import static java.lang.Math.min;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;

public class Utils {

	public static final String getResouce(String name) {
		try (InputStream is = Utils.class.getClassLoader().getResourceAsStream(
				name)) {
			return IOUtils.toString(is);
		} catch (IOException e) {
			throw new RuntimeException("not found " + name, e);
		}
	}

	public static void debugEntryArray(List<Entry<String, Integer>> array) {
		for (Entry<String, Integer> entry : array) {
			System.out.println(entry.getKey()+" -- "+ entry.getValue());
		}
	}

	public static final List<String> getResourceList(String name) {
		InputStream is = Utils.class.getClassLoader().getResourceAsStream(name);
		List<String> lines = null;
		try {
			lines = IOUtils.readLines(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	public static final String CHARSET = "charset=";

	public static final Charset ASCII = Charset.forName("US-ASCII");

	public static final Charset UTF_8 = Charset.forName("utf8");

	public static Charset detectCharset(String text) {
		byte[] body = text.getBytes();
		String s = new String(body, 0, min(512, body.length), ASCII);
		Charset c = guess(s, CHARSET);
		return c == null ? UTF_8 : c;
	}

	private static Charset guess(String html, String patten) {
		int idx = html.indexOf(patten);
		if (idx != -1) {
			int start = idx + patten.length();
			int end = html.indexOf('"', start);
			if (end != -1) {
				try {
					return Charset.forName(html.substring(start, end));
				} catch (Exception ignore) {
				}
			}
		}
		return null;
	}

	public static void sortMapStringAndInteger(
			List<Entry<String, Integer>> relationList) {
		sortMapStringAndInteger(relationList, true);
	}

	/**
	 * map按value大头朝下排序
	 * 
	 * @param relationList
	 */
	public static void sortMapStringAndInteger(
			List<Entry<String, Integer>> relationList, final boolean flag) {
		Collections.sort(relationList,
				new Comparator<Entry<String, Integer>>() {

					@Override
					public int compare(Entry<String, Integer> o1,
							Entry<String, Integer> o2) {
						Integer c1 = o1.getValue();
						Integer c2 = o2.getValue();
						int res = 0;
						if (c1 > c2) {
							res = 1;
						} else if (c1 == c2) {
							return 0;
						} else {
							res = -1;
						}
						if (flag) {
							return res;
						} else {
							return 0 - res;
						}
					}
				});
	}

	/**
	 * @param relationList
	 * @param output
	 * @throws IOException
	 */
	public static void writeDownMapStringInteger(
			List<Entry<String, Integer>> relationList, String output, String sep)
			throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(output)));
		for (Entry<String, Integer> entry : relationList) {
			bw.write(entry.getKey() + sep + entry.getValue());
			bw.write("\n");
		}
		bw.flush();
		bw.close();
	}

	public static void writeDownMapStringInteger(
			List<Entry<String, Integer>> relationList, String output)
			throws IOException {
		writeDownMapStringInteger(relationList, output, " ");
	}

	/**
	 * @param text
	 * @param n
	 * @return
	 */
	public static String[] getNgramSet(String text, int n) {
		StringBuilder sb = new StringBuilder();
		String spl = "，。，";
		for (int i = text.length(); i > 0; i--) {
			if (i - n >= 0) {
				sb.append(text.substring(i - n, i));
				sb.append(spl);
			}
		}
		return sb.toString().split(spl);
	}

	public static String arrayToString(String[] attr) {
		StringBuilder sb = new StringBuilder();
		int i = 1;
		for (String at : attr) {
			sb.append(at);
			if (i != attr.length) {
				sb.append(",");
			}
			i++;
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String[] ngramSet = getNgramSet("南京市长江大桥在长江大桥上发表了关于江大桥同志的事迹讲话", 3);
		for (String tm : ngramSet) {
			System.out.println(tm);
		}
	}

}
