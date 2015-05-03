package com.tianle.service.article;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tianle.util.SqlHelper;

public class ZanPlus {

	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;

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
