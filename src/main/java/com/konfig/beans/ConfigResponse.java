package com.konfig.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="config")
public class ConfigResponse implements Serializable{
	
	private static final long serialVersionUID = 3861752878105656735L;
	String app="";
	String env="";
	ArrayList<ConfigInfo> info=null;
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	public ArrayList<ConfigInfo> getInfo() {
		return info;
	}
	public void setInfo(ArrayList<ConfigInfo> info) {
		this.info = info;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		return true;
	}

}
