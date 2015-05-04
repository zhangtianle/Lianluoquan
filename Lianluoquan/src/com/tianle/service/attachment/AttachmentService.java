package com.tianle.service.attachment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.tianle.model.base.Attachment;
import com.tianle.util.IpURL;
import com.tianle.util.SqlHelper;
import com.tianle.util.UUIDGenerator;

public class AttachmentService {

	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	
	Attachment attachment = new Attachment();
	
	public ArrayList<Attachment> getAttachement(String articleUUID) {
		ArrayList<Attachment> attachments = new ArrayList<Attachment>();
		conn = SqlHelper.getConnection();
		if (articleUUID != null) {
			 try {
				st = conn.createStatement();
				String sqlatt = "select attachuuid,attachname,attachurl,articleuuid from attachment where articleuuid = '"
						+ articleUUID + "' ";
				rs = st.executeQuery(sqlatt);
				while (rs.next()) {
					Attachment attachment = new Attachment();
					attachment.setId(rs.getString("attachuuid"));
					attachment.setAttachName(rs.getString("attachname"));
					attachment.setAttachURL(IpURL.changeURL(rs
							.getString("attachurl")));
					attachment.setArticleUUID(rs.getString("articleuuid"));

					attachments.add(attachment);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				close();
			}
		}
		return attachments;
	}
	
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
			e.printStackTrace();
		}
	}
}
