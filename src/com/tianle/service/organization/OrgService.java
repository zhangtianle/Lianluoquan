package com.tianle.service.organization;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.tianle.model.base.Org;
import com.tianle.util.SqlHelper;
import com.tianle.util.UUIDGenerator;

public class OrgService {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;

	/**
	 * 查找所有的组织
	 * 
	 * @return
	 */
	public ArrayList<Org> selectOrg() {
		ArrayList<Org> orgs = new ArrayList<Org>();
		conn = SqlHelper.getConnection();
		String sql = "select orgname,orguuid,fatheruuid from org";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Org org = new Org();
				org.setOrgUUID(rs.getString("orguuid"));
				org.setOrgName(rs.getString("orgname"));
				org.setFatherUUID(rs.getString("fatheruuid"));
				orgs.add(org);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return orgs;
	}

	/**
	 * 添加一个新的组织，同时建立同个uuid的圈子
	 * 
	 * @param orgName
	 *            组织名称
	 * @param faterUUID
	 *            父组织的编号
	 * @return orgUUID 该新建组织的编号
	 */
	public String addOrg(String orgName, String faterUUID) {
		String UUID = UUIDGenerator.getUUID();
		String orgUUID = "";
		int result = 0;
		conn = SqlHelper.getConnection();
		try {
			// 建立新的组织
			String sql = "insert into org (orguuid,orgname,fatheruuid) values('"
					+ UUID + "','" + orgName + "','" + faterUUID + "')";
			st = conn.createStatement();
			result = st.executeUpdate(sql);

			// 建立成功
			if (result > 0) {
				// 建立对应的圈子
				if (creatCircleForOrg(UUID, orgName) > 0) {
					orgUUID = UUID;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return orgUUID;
	}

	/**
	 * 将学生添加至组织中，通过用户的uuid和组织的uuid
	 * 
	 * @param userUUID
	 *            用户的编号
	 * @param orgUUID
	 *            组织的编号
	 * @return org_userUUID 组织用户对应关系的编号
	 */
	public String addStudenttoOrg(String userUUID, String orgUUID) {
		String UUID = UUIDGenerator.getUUID();
		String org_userUUID = "";

		int result = 0;
		conn = SqlHelper.getConnection();
		String sql = "inser into org_user value('" + UUID + "','" + userUUID
				+ "','" + orgUUID + "')";
		try {
			st = conn.createStatement();
			result = st.executeUpdate(sql);
			if (result > 0) {
				org_userUUID = UUID;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return org_userUUID;
	}

	/**
	 * 根据组织建立圈子 组织的id和圈子的id相同
	 * 
	 * @param orgUUID
	 *            组织的编号
	 * @param orgName
	 *            组织的名称
	 * @return result 大于0建立成功
	 */
	public int creatCircleForOrg(String orgUUID, String orgName) {
		int result = 0;
		conn = SqlHelper.getConnection();
		String sql = "insert into circle (circleuuid,circlename)value('" + orgUUID + "','" + orgName	+ "')";
		try {
			st = conn.createStatement();
			System.out.println(sql);
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
	 * 将学生添加至圈子中（圈子id与组织id相同）
	 * 
	 * @param userUUID
	 *            用户的编号
	 * @param circleUUID
	 *            组织的编号
	 * @return
	 */
	public String addStudenttoCircle(String userUUID, String circleUUID) {
		String user_circleUUID = "";
		String UUID = UUIDGenerator.getUUID();
		int result = 0;

		conn = SqlHelper.getConnection();
		String sql = "inser into user_circle value('" + UUID + "','" + userUUID
				+ "','" + circleUUID + "')";
		try {
			st = conn.createStatement();
			result = st.executeUpdate(sql);
			if (result > 0) {
				user_circleUUID = UUID;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

		return user_circleUUID;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
