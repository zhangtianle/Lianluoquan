package com.tianle.service.organization;

import java.util.ArrayList;
import java.util.List;

import com.tianle.model.base.Org;

/**
 * 用户加入圈子类
 * Comments:
 * @author Kyle
 * @date 2015年6月7日 下午4:01:22
 */
public class UseraddtoCircle {


	/**
	 * 将用户加入组织，以及对应的圈子
	 * @param userUUID 用户uuid
	 * @param orgUUID
	 */
	public void addCircle(String userUUID, String orgUUID) {
		
		ArrayList<Org> sons = new ArrayList<Org>();
		Org o = new Org();
		o.setOrgUUID(orgUUID);
		// 查找该组织的子组织
		OrgService os = new OrgService();
		OrgtoCircle otc = new OrgtoCircle();
		List<Org> all = os.selectOrg();
		ArrayList<Org> orgs = otc.getChild(o, all, sons);
		for(Org oo : orgs) {
			String allUUID = oo.getOrgUUID();
			os.addStudenttoOrg(userUUID, allUUID);
			os.addStudenttoCircle(userUUID, allUUID);
		}
	}
}
