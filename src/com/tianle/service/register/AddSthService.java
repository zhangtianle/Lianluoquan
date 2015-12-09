package com.tianle.service.register;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.tianle.util.SqlHelper;
import com.tianle.util.UUIDGenerator;

/**
 * 添加班级
 * Comments:
 * @author Kyle
 * @date 2015年6月7日 下午4:02:31
 */
public class AddSthService {

	Connection conn = null;
	Statement st = null;

	// ResultSet rs = null;
	/**
	 * 添加班级信息
	 * @param grade 年级
	 * @param college 学院
	 * @param className 班级代号如：0141201
	 * @return boolean 成功返回true，失败返回false
	 */
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
