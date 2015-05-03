package com.tianle.service.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tianle.model.base.User;
import com.tianle.util.SqlHelper;

public class UserInf {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	
	/**
	 * 根据用户id查找用户信息
	 * @param userUUID
	 * @return
	 */
	public User getUserInf(String userUUID) {
		User u = new User();
		conn = SqlHelper.getConnection();
		String sql = "select * from user where useruuid='" + userUUID + "'";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				u.setId(rs.getString("useruuid"));
				u.setName(rs.getString("name"));
				u.setUserHead(rs.getString("userhead"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return u;
	}
	
	// 关闭连接
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
