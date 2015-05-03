package com.tianle.service.attachment;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.tianle.model.base.Attachment;
import com.tianle.util.SqlHelper;
import com.tianle.util.UUIDGenerator;

public class CreatAttachment {

	Connection conn = null;
	Statement st = null;
	
	Attachment attachment = new Attachment();
	
	public void addAttachment(String articleUUID, String attachname, String attachurl) {
		String UUID = UUIDGenerator.getUUID();
		conn = SqlHelper.getConnection();

		String sql = "insert into attachment "
				+ "value('" + UUID + "','" + articleUUID + "','" + attachname + "','" + attachurl + "')";
		try {
			st = conn.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void addAttachment(Attachment attachment) {
		String articleUUID = attachment.getArticleUUID();
		String attachname = attachment.getAttachName();
		String attachurl = attachment.getAttachURL();
		addAttachment(articleUUID, attachname, attachurl);
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
