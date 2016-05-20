package com.moon.android.launcher.thailand.model;

import java.io.Serializable;

public class Regionlimit implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2249321613061300155L;
	private String code;
	private String msg;
	private String end_time;
	
	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
