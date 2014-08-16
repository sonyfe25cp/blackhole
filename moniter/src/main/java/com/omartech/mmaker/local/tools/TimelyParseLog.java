package com.omartech.mmaker.local.tools;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.omartech.mmaker.model.DNSLog;


public class TimelyParseLog {

    
//    public static void main(String[] args) {
//        
//    }
    
    List<DNSLog> parse(String filePath){
        List<DNSLog> list = new ArrayList<>();
        try {
            List<String> readLines = FileUtils.readLines(new File(filePath));
            for(String line : readLines){
                DNSLog log = parseLog(line);
                list.add(log);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    static Pattern pattern = Pattern.compile("## ([\\d.])+ want to visit ");
    
    static DNSLog parseLog(String line){
        DNSLog log = null;
//        Matcher matcher = pattern.matcher(line);
//        if(matcher.find()){
//            String requestIp = matcher.group(0);
//            String host = matcher.group(1);
//            log = new DNSLog(requestIp, host);
//        }
        String time = line.substring(0, line.indexOf(","));
        String sub = line.substring(line.lastIndexOf("#")+1);
        System.out.println(sub);
        String after = sub.replace("want to visit ", "");
        String[] split = after.trim().split(" ");
        String requestIp = split[0];
        String host = split[1];
        log = new DNSLog(requestIp, host);
        try {
            Date requestTime = DateUtils.parseDate(time, "yy-MM-dd hh:mm:ss");
            log.setTime(requestTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return log;
    }
    
    
    public static void main(String[] args) {
        String str = "14-08-13 10:00:34,553 INFO  us.codecraft.blackhole.connector.UDPConnectionWorker(UDPConnectionWorker.java:41) ## 123.116.51.53 want to visit videolectures.net.";
        DNSLog log = parseLog(str);
        System.out.println(log);
    }
    
}



