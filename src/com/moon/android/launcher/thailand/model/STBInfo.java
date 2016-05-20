package com.moon.android.launcher.thailand.model;

import java.io.Serializable;

public class STBInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1501549607657964590L;
	/**
	 * 
	 */
	private String result;
	private String model;
	private String firmware;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getFirmware() {
		return firmware;
	}
	public void setFirmware(String firmware) {
		this.firmware = firmware;
	}
	
	
}
