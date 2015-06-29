package com.tianle.service.comment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.sf.json.JSONObject;

import com.tianle.model.base.Comment;
import com.tianle.model.base.User;
import com.tianle.model.logic.LogicComment;
import com.tianle.service.user.UserInf;
import com.tianle.util.SqlHelper;
import com.tianle.util.UUIDGenerator;

/**
 * 添加comment相关类 Comments:
 * 
 * @author Kyle
 * @date 2015年5月2日 上午11:23:08
 */
public class CommentService {

	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	UserInf uInf = new UserInf();
	/**
	 * 将json转化为评论
	 * 
	 * @param commentJSON 评论的json String
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
	 * 
	 * @param comment 评论类
	 * @return
	 */
	public Comment addtoDataBase(Comment comment) {
		conn = SqlHelper.getConnection();
		String sql = "";
		String UUID = UUIDGenerator.getUUID();

		String commentUUID = "";
		String pubUserUUID = comment.getPubuserUUID();
		String reUserUUID = comment.getReuserUUID();
		String articleUUID = comment.getArticleUUID();
		String content = comment.getContent();
		int layer = comment.getLayer();

		if (reUserUUID == null || reUserUUID.equals("")) {
			reUserUUID = "";
			sql = "insert into comment (commentuuid, pubuseruuid,articleuuid,content,layer) values "
					+ "('"
					+ UUID
					+ "','"
					+ pubUserUUID
					+ "','"
					+ articleUUID
					+ "','" + content + "','" + layer + "')";
		} else {
			sql = "insert into comment (commentuuid,pubuseruuid,reuseruuid,articleuuid,content,layer) values " + "('" + UUID + "','"
					+ pubUserUUID + "','" + reUserUUID + "','" + articleUUID
					+ "','" + content + "','" + layer + "')";
		}
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
		comment.setId(commentUUID);
		return comment;
	}
	
	/**
	 * 根据文章的uuid和，时间进行查找文章列表
	 * @param articleUUID 文章uuid
	 * @param time 在此时间之后发表的文章
	 * @return 查找所得的文章ArrayList<LogicComment>
	 */
	public ArrayList<LogicComment> selcetComments(String articleUUID, String time) {
		ArrayList<LogicComment> logiComments = new ArrayList<LogicComment>();
		
		conn = SqlHelper.getConnection();
		try {
			st = conn.createStatement();
			String sqlcomm = "select commentuuid,pubuseruuid,reuseruuid,content,layer,time from comment where articleuuid = '"
					+ articleUUID + "' and time>'" + time + "' order by time desc";
			rs = st.executeQuery(sqlcomm);
			while (rs.next()) {
				String pubUserUUID = rs.getString("pubuseruuid");
				String reUserUUID = rs.getString("reuseruuid");
				
				
				LogicComment logicComment = new LogicComment();
				logicComment.setArticleUUID(articleUUID);
				logicComment.setId(rs.getString("commentuuid"));
				logicComment.setPubuserUUID(pubUserUUID);
				logicComment.setReuserUUID(reUserUUID);
				logicComment.setContent(rs.getString("content"));
				logicComment.setLayer(rs.getInt("layer"));
				String stime = rs.getString("time");
				stime = stime.substring(0, stime.length()-2);
				logicComment.setTime(stime);
				
				//根据用户uuid查找对应的用户信息
				User pubUser = uInf.getUserInf(pubUserUUID);
				User reUser = uInf.getUserInf(reUserUUID);
				
				logicComment.setPubUserName(pubUser.getName());
				logicComment.setPubUserHead(pubUser.getUserHead());
				
				logicComment.setReUserName(reUser.getName());
				logicComment.setReUserHead(reUser.getUserHead());
				
				logiComments.add(logicComment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		
		return logiComments;
	}

	/**
	 * 将comment添加用户信息转化为LogicComment
	 * @param commnet 需要转化的comment
	 * @return 转换好的LogicComment
	 */
	public LogicComment changeComment(Comment commnet) {
		LogicComment lc = new LogicComment();
		
		String pubUserUUID = commnet.getPubuserUUID();
		String reUserUUID = commnet.getReuserUUID();
		
		lc.setArticleUUID(commnet.getArticleUUID());
		lc.setPubuserUUID(commnet.getPubuserUUID());
		lc.setContent(commnet.getContent());
		lc.setId(commnet.getId());
		lc.setReuserUUID(commnet.getReuserUUID());
		lc.setTime(commnet.getTime());
		
		UserInf ui = new UserInf();
		User pubUser = ui.getUserInf(pubUserUUID);
		User reUser = ui.getUserInf(reUserUUID);

		lc.setPubUserName(pubUser.getName());
		lc.setPubUserHead(pubUser.getUserHead());
		lc.setReUserName(reUser.getName());
		lc.setReUserHead(reUser.getUserHead());
		
		return lc;
	}
	
	/**
	 * 关闭数据库连接
	 */
	private void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
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
