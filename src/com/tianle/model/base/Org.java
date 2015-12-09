package com.tianle.model.base;

public class Org {
	private String orgUUID;
	private String orgName;
	private String fatherUUID;
	private int layer;
	public String getOrgUUID() {
		return orgUUID;
	}
	public void setOrgUUID(String orgUUID) {
		this.orgUUID = orgUUID;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getFatherUUID() {
		return fatherUUID;
	}
	public void setFatherUUID(String getFatherUUID) {
		this.fatherUUID = getFatherUUID;
	}
	public int getLayer() {
		return layer;
	}
	public void setLayer(int layer) {
		this.layer = layer;
	}
	
}
