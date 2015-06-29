package com.tianle.model.base;
/**
 * 文章类
 * Comments:
 * @author Kyle
 * @date 2015年6月7日 下午3:25:13
 */
public class Article {
	private String id;
	private String userUUID;
	private String circleUUID;
	private String time;
	private int zanCount;
	private String title;
	private String tags;
	private String content;
	private String photoURL;

	public String getId() {
		return id;
	}

	public void setId(String articleUUID) {
		this.id = articleUUID;
	}

	public String getUserUUID() {
		return userUUID;
	}

	public void setUserUUID(String userUUID) {
		this.userUUID = userUUID;
	}

	public String getCircleUUID() {
		return circleUUID;
	}

	public void setCircleUUID(String circleUUID) {
		this.circleUUID = circleUUID;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getZanCount() {
		return zanCount;
	}

	public void setZanCount(int zanCount) {
		this.zanCount = zanCount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

}
