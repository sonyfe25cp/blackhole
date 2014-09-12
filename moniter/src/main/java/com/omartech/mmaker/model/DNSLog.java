package com.omartech.mmaker.model;

import java.util.Date;


public class DNSLog {
    
    private int id;
    private String requestIp;
    private String host;
    private String answerIp;
    private Date time;
    private boolean userful;//网站类型还是各种api
    
    public DNSLog(String requestIp, String host) {
        super();
        this.requestIp = requestIp;
        this.host = host;
    }

    public DNSLog() {
        super();
    }

    @Override
    public String toString() {
        return "DNSLog [id=" + id + ", requestIp=" + requestIp + ", host=" + host + ", answerIp="
                + answerIp + ", time=" + time + "]";
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getRequestIp() {
        return requestIp;
    }
    
    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }
    
    public String getHost() {
        return host;
    }
    
    public void setHost(String host) {
        this.host = host;
    }
    
    public String getAnswerIp() {
        return answerIp;
    }
    
    public void setAnswerIp(String answerIp) {
        this.answerIp = answerIp;
    }
    
    public Date getTime() {
        return time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }

	public boolean isUserful() {
		return userful;
	}

	public void setUserful(boolean userful) {
		this.userful = userful;
	}
}
