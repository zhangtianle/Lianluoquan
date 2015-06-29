package com.tianle.service.article;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tianle.util.SqlHelper;

/**
 * 文章点赞
 * Comments:
 * @author Kyle
 * @date 2015年6月7日 下午3:29:25
 */
public class ZanPlus {

	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;

	/**
	 * 根据文章的UUID进行点赞，被点赞的文章，点赞数加一
	 * @param articleUUID 文章的UUID
	 * @return 成功返回1
	 */
	public int addZan(String articleUUID) {
		int result = 0;
		conn = SqlHelper.getConnection();
		String sql = "UPDATE article set zancount=zancount+1 where articleuuid = '"
				+ articleUUID + "'";
		try {
			st = conn.createStatement();
			result = st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}

	/**
	 * 返回文章的点赞数目，根据文章的uuid来进行查找
	 * @param articleUUID
	 * @return 该文章的点赞数
	 */
	public int zanCount(String articleUUID) {
		int count = 0;
		conn = SqlHelper.getConnection();
		String sql = "select zancount from article where articleuuid = '"
				+ articleUUID + "'";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				count = rs.getInt("zancount");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return count;
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
			e.printStackTrace();
		}
	}
}
