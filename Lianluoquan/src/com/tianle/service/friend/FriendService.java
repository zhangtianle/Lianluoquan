package com.tianle.service.friend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.tianle.model.base.ClassInf;
import com.tianle.model.logic.LogicFriend;
import com.tianle.service.circle.CircleService;
import com.tianle.service.classinf.ClassInfService;
import com.tianle.util.SqlHelper;
import com.tianle.util.UUIDGenerator;

/**
 * 用户好友处理服务类
 * Comments:
 * @author Kyle
 * @date 2015年6月7日 下午3:49:44
 */
public class FriendService {
	
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	
	/**
	 * 用户关注某个用户并返回被关注的用户信息
	 * @param userUUID
	 * @param friendUUID
	 * @return
	 */
	public LogicFriend followFriend(String userUUID, String friendUUID) {
		LogicFriend lf = new LogicFriend();
		
		String UUID = UUIDGenerator.getUUID();
		
		conn = SqlHelper.getConnection();
		String sql = "insert into user_friend (ufuuid,useruuid,frienduuid) values ('" + UUID + "','" + userUUID + "','" + friendUUID + "')";
		try {
			st = conn.createStatement();
			int result = st.executeUpdate(sql);
			// 用户关注用户成功
			if(result != 0) {
				String usersql = "select `user`.name,`user`.userhead,classinf.classname,classinf.college,(SELECT count(*) from user_circle where useruuid = '" + friendUUID + "') as circlecount from user,classinf where useruuid='" + friendUUID + "' and classinf.classuuid=(SELECT `user`.classuuid from user where `user`.useruuid = '" + friendUUID + "' )";
				rs = st.executeQuery(usersql);
				while(rs.next()) {
					lf.setId(friendUUID);
					lf.setHead(rs.getString("userhead"));
					lf.setName(rs.getString("name"));
					lf.setCollege(rs.getString("college"));
					lf.setCirclesCounts(rs.getInt("circlecount"));
					lf.setFriendClass(rs.getString("classname"));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return lf;
	}
	
	/**
	 * 判断是否已经关注该用户
	 * @param userUUID
	 * @param friendUUID
	 * @return true 已经关注，false 还没关注
	 */
	public boolean isFollowed(String userUUID, String friendUUID) {
		boolean result = false;
		List<String> followedUUIDs = new ArrayList<String>();
		conn = SqlHelper.getConnection();
		String sql = "SELECT frienduuid FROM user_friend WHERE useruuid = '" + userUUID + "'";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				followedUUIDs.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		if(followedUUIDs.contains(friendUUID)) {
			result = true;
		}
		return result;
	}
	
	/**
	 * 根据用户useruuid来查找该用户所关注的人
	 * @param userUUID 需要查找好友用户的uuid
	 * @return friends 所有关注的人
	 */
	public List<LogicFriend> selectFriends(String userUUID) {
		
		ClassInfService cis = new ClassInfService();
		CircleService cs = new CircleService();
		
		List<LogicFriend> friends = new ArrayList<LogicFriend>();
		conn = SqlHelper.getConnection();
		// 查找好友的信息 未包含 班级 学院信息
		String sql = "SELECT `user`.`name`,`user`.useruuid,classuuid,`user`.userhead FROM `user` WHERE useruuid in (SELECT frienduuid FROM user_friend WHERE useruuid='"+ userUUID +"')";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			System.out.println("selectFriends: " + sql);
			while(rs.next()) {
				LogicFriend lf = new LogicFriend();
				
				String friendUUID = rs.getString("useruuid");
				String classUUID = rs.getString("classuuid");
				int circleCount = cs.followedCount(friendUUID);
				ClassInf c = cis.getClassInfforClassUUID(classUUID);
				String college = c.getCollege();
				String friendClass = c.getClassName();
				
				lf.setHead(rs.getString("userhead"));
				lf.setId(friendUUID);
				lf.setName(rs.getString("name"));
				lf.setCirclesCounts(circleCount);
				lf.setCollege(college);
				lf.setFriendClass(friendClass);
				
				friends.add(lf);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return friends;
	}
	
	/**
	 * 将好友信息转化为json格式
	 * @param logicFriend
	 * @return
	 */
	public String friendtoJson(LogicFriend logicFriend) {
		String slf = "";
		JSONObject jsonObject = JSONObject.fromObject(logicFriend);
		slf = jsonObject.toString();
		return slf;
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









