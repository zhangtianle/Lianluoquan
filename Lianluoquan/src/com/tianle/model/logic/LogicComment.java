package com.tianle.model.logic;

import com.tianle.model.base.Comment;

public class LogicComment extends Comment {
	private String pubUserName;
	private String pubUserHead;
	private String reUserName;
	private String reUserHead;
	
	public String getPubUserName() {
		return pubUserName;
	}

	public void setPubUserName(String pubUserName) {
		this.pubUserName = pubUserName;
	}

	public String getPubUserHead() {
		return pubUserHead;
	}

	public void setPubUserHead(String pubUserHead) {
		this.pubUserHead = pubUserHead;
	}

	public String getReUserName() {
		return reUserName;
	}

	public void setReUserName(String reUserName) {
		this.reUserName = reUserName;
	}

	public String getReUserHead() {
		return reUserHead;
	}

	public void setReUserHead(String reUserHead) {
		this.reUserHead = reUserHead;
	}

}
