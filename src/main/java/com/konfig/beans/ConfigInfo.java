package com.konfig.beans;

import java.util.HashMap;

public class ConfigInfo {
	String source="";
	HashMap<String,String> params=null;
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public HashMap<String, String> getParams() {
		return params;
	}
	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

}
