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

/**
 * 文章附件处理服务类
 * Comments:
 * @author Kyle
 * @date 2015年6月7日 下午3:34:28
 */
public class AttachmentService {

	
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	
	Attachment attachment = new Attachment();
	
	/**
	 * 获得文章附件，根据文章的uuid进行查找
	 * @param articleUUID 文章uuid
	 * @return 对应文章的附件列表，ArrayList<Attachment>
	 */
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
	
	/**
	 * 添加文章附件
	 * @param articleUUID 需要添加附件的文章uuid
	 * @param attachname 附件名称
	 * @param attachurl 附件存放地址（服务器相对路径）
	 */
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
	/**
	 * 添加文章附件 
	 * @param attachment 需要添加的附件
	 */
	public void addAttachment(Attachment attachment) {
		String articleUUID = attachment.getArticleUUID();
		String attachname = attachment.getAttachName();
		String attachurl = attachment.getAttachURL();
		addAttachment(articleUUID, attachname, attachurl);
	}
	
	/**
	 * 关闭数据库连接
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
			e.printStackTrace();
		}
	}
}
