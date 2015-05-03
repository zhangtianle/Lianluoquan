package com.tianle.service.comment;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.json.JSONObject;

import com.tianle.model.base.Comment;
import com.tianle.util.SqlHelper;
import com.tianle.util.UUIDGenerator;
/**
 * 添加comment相关类
 * Comments:
 * @author Kyle
 * @date 2015年5月2日 上午11:23:08
 */
public class AddComment {

	Connection conn = null;
	Statement st = null;

	/**
	 * 将json转化为评论
	 * @param commentJSON
	 * @return
	 */
	public Comment getComment(String commentJSON) {

		JSONObject jsonObject = JSONObject.fromObject(commentJSON);
		Comment comment = (Comment) JSONObject
				.toBean(jsonObject, Comment.class);
		return comment;
	}

	/**
	 * 将获得到的comment存入数据库中
	 * @param comment
	 * @return
	 */
	public String addtoDataBase(Comment comment) {

		String UUID = UUIDGenerator.getUUID();

		String commentUUID = "";
		String pubUserUUID = comment.getPubuserUUID();
		String reUserUUID = comment.getReuserUUID();
		String articleUUID = comment.getArticleUUID();
		String content = comment.getContent();
		int layer = comment.getLayer();

		conn = SqlHelper.getConnection();
		String sql = "insert into comment values " + "('" + UUID + "','"
				+ pubUserUUID + "','" + reUserUUID + "','" + articleUUID
				+ "','" + content + "','" + layer + "')";
		try {
			st = conn.createStatement();
			int result = st.executeUpdate(sql);
			if (result != 0) {
				commentUUID = UUID;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return commentUUID;
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









