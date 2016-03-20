package com.konfig.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ping {
	String status="";

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
