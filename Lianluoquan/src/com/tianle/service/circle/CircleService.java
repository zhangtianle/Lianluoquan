package com.tianle.service.circle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tianle.model.base.Circle;
import com.tianle.util.SqlHelper;

/**
 * circle处理类
 * Comments:
 * @author Kyle
 * @date 2015年5月9日 下午4:27:06
 */
public class CircleService {
	
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;

	/**
	 * 根据circleuuid来查找circle信息
	 * @param circleUUID
	 * @return circle类
	 */
	public Circle getCircleforUUID(String circleUUID) {
		Circle c = new Circle();
		conn = SqlHelper.getConnection();
		String sql = "select * from circle where circleuuid='" + circleUUID + "'";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				c.setId(rs.getString("circleuuid"));
				c.setCircleName(rs.getString("circlename"));
				c.setCircleHead(rs.getString("circlehead"));
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
	 * 查找用户关注的圈子数目
	 * @param userUUID
	 * @return
	 */
	public int followedCount(String userUUID) {
		int count = 0;
		conn = SqlHelper.getConnection();
		String sql = "select count(*) from user_circle where useruuid='" + userUUID + "'";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				count = rs.getInt(1);
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






