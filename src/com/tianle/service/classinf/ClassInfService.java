package com.tianle.service.classinf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tianle.model.base.ClassInf;
import com.tianle.util.SqlHelper;
/**
 * 班级信息处理服务类
 * Comments:
 * @author Kyle
 * @date 2015年6月7日 下午3:39:13
 */
public class ClassInfService {
	
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	
	/**
	 * 根据班级uuid查找该班的信息
	 * @param classUUID 班级的uuid
	 * @return 根据uuid查找出来的班级详细信息，返回ClassInf
	 */
	public ClassInf getClassInfforClassUUID(String classUUID) {
		ClassInf c = new ClassInf();
		conn = SqlHelper.getConnection();
		String sql = "select * from classinf where classuuid='" + classUUID + "'";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				c.setClassName(rs.getString("classname"));
				c.setCollege(rs.getString("college"));
				c.setClssUUID(classUUID);
				c.setGrade(rs.getInt("grade"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return c;
	}
	
	/**
	 * 关闭数据库连接释放资源
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
