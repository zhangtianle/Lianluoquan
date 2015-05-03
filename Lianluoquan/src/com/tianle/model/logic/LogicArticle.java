package com.tianle.model.logic;

import java.util.List;

import com.tianle.model.base.Attachment;

public class LogicArticle {
	private String id;
	private String name;
	private String title;
	private String time;
	private String content;
	private int zanCount;
	private List<Attachment> attachments;
	private List<LogicComment> logicComments;
	private String circle;
	private String type;
	private String photoURL;

	public String getId() {
		return id;
	}

	public void setId(String articleUUID) {
		this.id = articleUUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getZanCount() {
		return zanCount;
	}

	public void setZanCount(int zanCount) {
		this.zanCount = zanCount;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public List<LogicComment> getComments() {
		return logicComments;
	}

	public void setComments(List<LogicComment> comments) {
		this.logicComments = comments;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}
	
	

}
