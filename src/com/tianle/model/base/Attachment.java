package com.tianle.model.base;

public class Attachment {
	private String id;
	private String articleUUID;
	private String attachName;
	private String attachURL;

	public String getId() {
		return id;
	}

	public void setId(String attachUUID) {
		this.id = attachUUID;
	}

	public String getArticleUUID() {
		return articleUUID;
	}

	public void setArticleUUID(String articleUUID) {
		this.articleUUID = articleUUID;
	}

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	public String getAttachURL() {
		return attachURL;
	}

	public void setAttachURL(String attachURL) {
		this.attachURL = attachURL;
	}

}
