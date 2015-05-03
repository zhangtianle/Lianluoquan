package com.tianle.service.article;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.tianle.model.base.Article;
import com.tianle.util.SqlHelper;
import com.tianle.util.UUIDGenerator;

public class PubArticle {
	Connection conn = null;
	Statement st = null;
	
	public String pubMyArticle(Article article) {
		String id = "";
		
		String userUUID = article.getUserUUID();
		String circleUUID = article.getCircleUUID();
		String title = article.getTitle();
		String tags = article.getTags();
		String content = article.getContent();
		String photoURL = article.getPhotoURL();

		id = pubMyArticle(userUUID, circleUUID, title, tags, content, photoURL);
		
		return id;
	}
	
	public String pubMyArticle(String userUUID, String circleUUID, 
			 String title, String tags, String content, String photoURL) {
		
		String id = "";
		
		conn = SqlHelper.getConnection();

		String articleUUID = UUIDGenerator.getUUID();
		String sql = "insert into article (articleuuid,useruuid,circleuuid,title,tags,content,photourl) "
				+ "values ('" + articleUUID + "','" + userUUID + "','" + circleUUID + "','" + title + "','" + tags + "','" + content + "','" +photoURL+"')";
		try {
			st = conn.createStatement();
			if(st.executeUpdate(sql) == 1) {
				id = articleUUID;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return id;
	}

	private void close() {
		try {
			if (st != null) {
				st.close();
				st = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
