package com.tianle.service.search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.tianle.model.base.Circle;
import com.tianle.model.logic.LogicUser;
import com.tianle.service.circle.CircleService;
import com.tianle.service.register.RegisterService;
import com.tianle.util.SqlHelper;
import com.tianle.util.UUIDGenerator;

/**
 * 根据关键词查找圈子以及用户 Comments:
 * 
 * @author Kyle
 * @date 2015年6月7日 下午4:10:05
 */
public class SearchService {

	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;

	/**
	 * 根据关键词查找圈子，返回圈子的列表
	 * 
	 * @param circleNameLike
	 * @return
	 */
	public List<Circle> searchCircles(String circleNameLike) {
		List<Circle> circles = new ArrayList<Circle>();

		conn = SqlHelper.getConnection();
		String sql = "select * from circle where circlename like '%"
				+ circleNameLike + "%'";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Circle c = new Circle();
				c.setCircleName(rs.getString("circlename"));
				c.setId(rs.getString("circleuuid"));
				c.setCircleHead(rs.getString("circleHead"));
				circles.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return circles;
	}

	/**
	 * 根据关键词查找用户，返回用户的列表
	 * 
	 * @param circleNameLike
	 * @return
	 */
	public List<LogicUser> searchUsers(String userNameLike) {

		List<LogicUser> logicUsers = new ArrayList<LogicUser>();
		RegisterService registerService = new RegisterService();

		conn = SqlHelper.getConnection();
		String sql = "select useruuid from user where name like '%"
				+ userNameLike + "%'";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String userUUID = rs.getString("useruuid");
				LogicUser lu = registerService.selcetStuInf(userUUID);
				logicUsers.add(lu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return logicUsers;
	}

	/**
	 * 根据搜索的关键词，返回logicUsers，circles 并封装成json格式
	 * 
	 * @param logicUsers
	 * @param circles
	 * @return json格式的logicUsers，circles
	 */
	public String searchMain(List<LogicUser> logicUsers, List<Circle> circles) {
		String searchResultJSON = "";

		JSONArray userJsonArray = JSONArray.fromObject(logicUsers);
		JSONArray circlesJsonArray = JSONArray.fromObject(circles);
		String sUser = "\"users\":" + userJsonArray.toString();
		String sCircles = "\"circles\":" + circlesJsonArray.toString();
		searchResultJSON = "{" + sUser + "," + sCircles + "}";

		return searchResultJSON;
	}

	/**
	 * 用户关注某个圈子，并返回该圈子的信息
	 * 
	 * @param userUUID
	 *            用户的uuid
	 * @param circleUUID
	 *            该用户要关注的circleuuid
	 * @return Circle类
	 */
	public Circle followCircle(String userUUID, String circleUUID) {
		Circle circle = null;

		String UUID = UUIDGenerator.getUUID();

		conn = SqlHelper.getConnection();
		try {

			String checkSql = "select circleuuid from user_circle where useruuid='"
					+ userUUID + "'";
			List<String> followed = new ArrayList<String>();
			st = conn.createStatement();
			rs = st.executeQuery(checkSql);
			while (rs.next()) {
				followed.add(rs.getString("circleuuid"));
			}
			// 如果已经关注返回null
			if (followed.contains(circleUUID)) {
				return null;
			} else {
				String sql = "insert into user_circle (ucuuid,useruuid,circleuuid) values ('"
						+ UUID + "','" + userUUID + "','" + circleUUID + "')";
				int result = st.executeUpdate(sql);
				// 如果关注成功
				if (result != 0) {
					CircleService cs = new CircleService();
					circle = cs.getCircleforUUID(circleUUID);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return circle;
	}

	/**
	 * 将circle转化为json
	 * 
	 * @param cricle
	 * @return
	 */
	public String circletoJSON(Circle cricle) {
		JSONObject jsonObject = JSONObject.fromObject(cricle);
		return jsonObject.toString();
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
