package com.tianle.service.article;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.tianle.model.base.Article;
import com.tianle.util.SqlHelper;
import com.tianle.util.UUIDGenerator;
/**
 * 发布文章
 * Comments:
 * @author Kyle
 * @date 2015年6月6日 下午10:25:43
 */
public class PubArticle {
	Connection conn = null;
	Statement st = null;
	
	/**
	 * 发布文章 根据文章类
	 * @param article 文章
	 * @return 发布文章的uuid
	 */
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
	
	/**
	 * 发布文章 根据文章的各个参数
	 * @param userUUID 文章作者uuid
	 * @param circleUUID 文章所发布圈子uuid
	 * @param title 文章标题
	 * @param tags 文章标签
	 * @param content 文章内容
	 * @param photoURL 文章图片
	 * @return String 文章uuid
	 */
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

	/**
	 * 关闭数据库连接
	 */
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
