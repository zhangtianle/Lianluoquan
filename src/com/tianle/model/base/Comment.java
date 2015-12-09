package com.tianle.model.base;

public class Comment {

	private String id;
	private String articleUUID;
	private String pubuserUUID;
	private String reuserUUID;
	private String content;
	private String time;
	private int layer;

	public String getId() {
		return id;
	}

	public void setId(String commentUUID) {
		this.id = commentUUID;
	}

	public String getArticleUUID() {
		return articleUUID;
	}

	public void setArticleUUID(String articleUUID) {
		this.articleUUID = articleUUID;
	}

	public String getPubuserUUID() {
		return pubuserUUID;
	}

	public void setPubuserUUID(String pubuserUUID) {
		this.pubuserUUID = pubuserUUID;
	}

	public String getReuserUUID() {
		return reuserUUID;
	}

	public void setReuserUUID(String reuserUUID) {
		this.reuserUUID = reuserUUID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
