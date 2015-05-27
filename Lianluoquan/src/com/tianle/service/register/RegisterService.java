package com.tianle.service.register;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.tianle.model.base.Circle;
import com.tianle.model.logic.LogicFriend;
import com.tianle.model.logic.LogicUser;
import com.tianle.service.friend.FriendService;
import com.tianle.util.SqlHelper;
import com.tianle.util.UUIDGenerator;

public class RegisterService {

	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;

	/**
	 * 根据用户的id查找学生信息logicUser，返回logicUser
	 * @param id
	 * @return
	 */
	public LogicUser selcetStuInf(String id) {
		LogicUser logicUser = new LogicUser();

		String classuuid = "";
		String studentNum = "";
		String name = "";
		String passWord = "";
		int grade = 0;
		String college = "";
		String className = "";
		
		ArrayList<Circle> circles = new ArrayList<Circle>();

		conn = SqlHelper.getConnection();

		
		try {
			String sql = "select name,password,classuuid,studentnum from user where useruuid='"
					+ id + "'";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				classuuid = rs.getString("classuuid");
				name = rs.getString("name");
				passWord = rs.getString("password");
				studentNum = rs.getString("studentnum");
			}

			// 查找学生对应的班级信息，包括学院
			String sqlClass = "select grade,college,classname from classinf where classuuid='"
					+ classuuid + "'";
			rs = st.executeQuery(sqlClass);

			while (rs.next()) {
				grade = rs.getInt("grade");
				college = rs.getString("college");
				className = rs.getString("classname");
			}
			
			//查找用户所在的圈子
			String sqlCircle = "select circleuuid,circlename FROM circle where circleuuid in "
					+ "(select circleuuid from user_circle where useruuid='"
					+ id + "')";
			rs = st.executeQuery(sqlCircle);
			
			while (rs.next()) {
				Circle c = new Circle();
				String circleuuid = rs.getString("circleuuid");
				String circlename = rs.getString("circlename");
				c.setId(circleuuid);
				c.setCircleName(circlename);
				circles.add(c);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rs = null;
			}
			close();
		}
		
		// 获得用户的关注用户的列表
		List<LogicFriend> friends = new FriendService().selectFriends(id);

		// 将学生信息重新打包
		logicUser.setName(name);
		logicUser.setUserNum(studentNum);
		logicUser.setGrade(grade);
		logicUser.setUserCollege(college);
		logicUser.setUserClass(className);
		logicUser.setId(id);
		logicUser.setPassWord(passWord);
		logicUser.setCircles(circles);
		logicUser.setFriends(friends);

		return logicUser;
	}
	
	/**
	 * 将logicUser转化为json
	 * @param logicUser
	 * @return
	 */
	public String logicUserJSON(LogicUser logicUser) {
		String userJSON = "";
		JSONObject jsonObject = JSONObject.fromObject(logicUser);
		userJSON = jsonObject.toString();
		return userJSON;
	}
	
	/**
	 * 用户登陆
	 * @param loginInfor logicUser用户的json数据
	 * @return 该用户对应的uuid
	 */
	public String login(String loginInfor) {

		JSONObject jsonObject = JSONObject.fromObject(loginInfor);
		LogicUser user = (LogicUser) JSONObject.toBean(jsonObject, LogicUser.class);

		String userUUID = "";
		String passWord = user.getPassWord();
		String studentNum = user.getUserNum();
		String passWordSec = "";

		conn = SqlHelper.getConnection();

		String sql = "select userUUID,passWord from user where studentNum='"
				+ studentNum + "'";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				userUUID = rs.getString(1);
				passWordSec = rs.getString(2);
			}
		} catch (Exception e) {
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				rs = null;
			}
			close();
		}
		if (!passWordSec.equals(passWord)) {
			userUUID = "";
		}
		return userUUID;
	}
	
	/**
	 * 根据LogicUser来注册用户
	 * @param registerInfor
	 * @return
	 */
	public String registerUser(String registerInfor) {

		String userUUID = "";
		JSONObject jsonObject = JSONObject.fromObject(registerInfor);
		LogicUser logicUser = (LogicUser) JSONObject.toBean(jsonObject,
				LogicUser.class);

		String name = logicUser.getName();
		String passWord = logicUser.getPassWord();
		String studentNum = logicUser.getUserNum();
		String className = logicUser.getUserClass();
		String college = logicUser.getUserCollege();
		String grade = studentNum.substring(0, 4);

		// 建立对应的班级表，并取得班级uuid
		String classUUID = addClass(grade, college, className);
		
		

		// 将得到的用户数据写入数据库
		conn = SqlHelper.getConnection();

		String UUID = UUIDGenerator.getUUID();
		String sql = "insert into user (useruuid,name,password,studentnum,classuuid) value"
				+ " ('"
				+ UUID
				+ "','"
				+ name
				+ "','"
				+ passWord
				+ "','"
				+ studentNum + "','" + classUUID + "')";
		System.out.println("-----------添加学生uuid：" + sql);
		try {
			st = conn.createStatement();
			int result = st.executeUpdate(sql);
			System.out.println("----------result: " + result);
			if (result == 1) {
				userUUID = UUID;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		// 将班级以及学院信息添加至圈子
		adduc(userUUID, college, className);

		return userUUID;
	}

	public String addClass(String grade, String college, String className) {
		String UUID = UUIDGenerator.getUUID();
		String classUUID = "";
		conn = SqlHelper.getConnection();

		String sql = "insert into classinf (classuuid,grade,college,classname) value ('"
				+ UUID
				+ "','"
				+ grade
				+ "','"
				+ college
				+ "','"
				+ className
				+ "')";
		try {
			st = conn.createStatement();
			int result = st.executeUpdate(sql);
			if (result != 0) {
				classUUID = UUID;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return classUUID;
	}
	
	private void adduc(String useruuid, String college, String className) {
		String classUUID = UUIDGenerator.getUUID();
		conn = SqlHelper.getConnection();

		// 插入班级到圈子表
		String checkClassNameSQL = "select circleuuid,circlename from circle where circlename='" + className + "'";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(checkClassNameSQL);
			int flag = 0;
			while(rs.next()) {
				classUUID = rs.getString("circleuuid");
				flag = 1;
			}
			if(flag == 0) {
				String addClasstoCircleSQL = "insert into circle (circleuuid,circlename) value('" + classUUID + "','" + className + "')";
				if(st.executeUpdate(addClasstoCircleSQL) == 1) {
					System.out.println("添加班级至圈子成功： " + className);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeRs();
			close();
		}
		
		String collegeUUID = UUIDGenerator.getUUID();
		conn = SqlHelper.getConnection();
		// 插入学院到圈子表
		String checkCollegeSQL = "select circleuuid,circlename from circle where circlename='" + college + "'";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(checkCollegeSQL);
			int flag = 0;
			while(rs.next()) {
				collegeUUID = rs.getString("circleuuid");
				flag = 1;
			}
			if(flag == 0) {
				String addCollegetoCircleSQL = "insert into circle (circleuuid,circlename) value('" + collegeUUID + "','" + college + "')";
				if(st.executeUpdate(addCollegetoCircleSQL) == 1) {
					System.out.println("添加学院至圈子成功： " + college);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeRs();
			close();
		}
		
		// 将学生信息添加至圈子学生对应关系表
		addtouc(useruuid,classUUID);
		addtouc(useruuid,collegeUUID);
	}
	
	private void addtouc(String useruuid, String circleuuid) {
		String UUID = UUIDGenerator.getUUID();
		conn = SqlHelper.getConnection();
		// 插入学院到圈子表
		String adduc = "insert into user_circle value('" + UUID + "','" + useruuid + "','" + circleuuid + "')";
		try {
			st = conn.createStatement();
			st.executeUpdate(adduc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally  {
			close();
		}
	}
	
	private void closeRs() {
		try {
			if(rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			e.printStackTrace();
		}
	}
}
