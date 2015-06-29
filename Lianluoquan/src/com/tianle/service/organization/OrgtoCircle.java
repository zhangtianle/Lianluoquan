package com.tianle.service.organization;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tianle.model.base.Org;
import com.tianle.util.SqlHelper;

/**
 * 将组织对应至圈子
 * Comments:
 * @author Kyle
 * @date 2015年5月2日 下午6:44:10
 */
public class OrgtoCircle {
	
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;

	/**
	 * 根据组织的uuid来查找他的下级组织
	 * @param orgUUID
	 * @return
	 */
	public List<Org> getChildOrgsforOrgUUID(String orgUUID) {
		List<Org> orgs = new ArrayList<Org>();
		
		conn = SqlHelper.getConnection();
		String sql = "select * from org where fatheruuid = '" + orgUUID + "'";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				Org o = new Org();
				o.setFatherUUID(rs.getString("fatheruuid"));
				o.setLayer(rs.getInt("layer"));
				o.setOrgName(rs.getString("orgname"));
				o.setOrgUUID(rs.getString("orguuid"));
				
				orgs.add(o);
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
	 * 递归来查找他的下级组织
	 * @param o
	 * @param all
	 * @param sons
	 * @return
	 */
	public ArrayList<Org> getChild(Org o, List<Org> all, ArrayList<Org> sons) {
		for(Org oo : all) {
			if(o.getOrgUUID().equals(oo.getFatherUUID())) {
				sons.add(oo);
				sons = getChild(oo, all, sons);
			}
		}
		return sons;
	}
	
/*	public static void main(String[] args) {
		OrgService os = new OrgService();
		Org o = new Org();
		o.setOrgUUID("1");
		ArrayList<Org> all = os.selectOrg();
		sons = getChild(o, all ,sons);
		for(Org s : sons) {
			System.out.println(s.getOrgUUID());
		}
	}*/
	
	/**
	 * 关闭连接
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




