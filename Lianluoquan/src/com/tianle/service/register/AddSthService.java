package com.tianle.service.register;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.tianle.util.SqlHelper;
import com.tianle.util.UUIDGenerator;

public class AddSthService {

	Connection conn = null;
	Statement st = null;

	// ResultSet rs = null;

	public boolean addClass(int grade, String college, String className) {

		boolean result = false;

		conn = SqlHelper.getConnection();

		String classUUID = UUIDGenerator.getUUID();
		String sql = "insert into classinf (classuuid,grade,college,classname) "
				+ "values('"
				+ classUUID
				+ "','"
				+ grade
				+ "','"
				+ college
				+ "','" + className + "')";
		try {
			st = conn.createStatement();
			if (st.executeUpdate(sql) == 1) {
				result = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
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
