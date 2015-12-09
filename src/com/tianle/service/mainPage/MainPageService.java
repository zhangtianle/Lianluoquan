package com.tianle.service.mainPage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.tianle.model.base.Article;
import com.tianle.model.base.Attachment;
import com.tianle.model.base.User;
import com.tianle.model.logic.LogicArticle;
import com.tianle.model.logic.LogicComment;
import com.tianle.model.logic.LogicMainPageArticle;
import com.tianle.service.user.UserInf;
import com.tianle.util.IpURL;
import com.tianle.util.SqlHelper;

/**
 * 客户端首页数据处理类
 * Comments:
 * @author Kyle
 * @date 2015年6月7日 下午3:52:59
 */
public class MainPageService {

	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;

	UserInf uInf = new UserInf();

	
	/**
	 * 根据用户的uuid查询该用户关注的联络圈的所有消息
	 * @param userUUID 用户uuid
	 * @param page 分页查询，当前页数，若为all，则返回全部信息
	 * @param circle 查询指定圈子的文章，若为all，则返回该用户关注所有圈子的文章
	 * @param loadTime 最后加载时间，返回改时间之后的所有文章
	 * @return 所查询的文章列表ArrayList<Article>
	 */
	public ArrayList<Article> mainPageResponse(String userUUID, String page,
			String circle, String loadTime) {
		// 设置分页，默认每页5条数据
		int pageSize = 5;
		int pageNow = Integer.parseInt(page);
		ArrayList<Article> articles = new ArrayList<Article>();
		conn = SqlHelper.getConnection();
		try {
			st = conn.createStatement();

			String sql = "";
			// 刷新请求，如果时间默认为0，则返回全部请求
			if (loadTime.equals("0")) {
				if (circle.equals("all")) {
					// 查找用户关注的所有圈子
					sql = "select * from article where circleuuid in "
							+ "(SELECT circleuuid from user_circle WHERE useruuid ='"
							+ userUUID + "')" + "order by time desc";
				} else {
					// 查找用户关注指定的圈子
					sql = "select * from article where circleuuid = '" + circle
							+ "' order by time desc";
				}
			} else {
				if (circle.equals("all")) {
					// 查找用户关注的所有圈子
					sql = "select * from article where  circleuuid in "
							+ "(SELECT circleuuid from user_circle WHERE useruuid ='"
							+ userUUID + "')" + " and time > '" + loadTime
							+ "' order by time desc limit "
							+ (pageNow * pageSize) + ","
							+ (pageSize * (pageNow + 1) - 1);
				} else {
					// 查找用户关注指定的圈子
					sql = "select * from article where circleuuid = '" + circle
							+ "' and time > '" + loadTime
							+ "' order by time desc " + (pageNow * pageSize)
							+ "," + (pageSize * (pageNow + 1) - 1);
				}
			}
			// 测试输出sql语句
			System.out.println("mainPageResponse: " + sql);
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Article art = new Article();

				String articleuuid = rs.getString("articleuuid");
				String useruuid = rs.getString("useruuid");
				String circleuuid = rs.getString("circleuuid");
				String title = rs.getString("title");
				String tags = rs.getString("tags");
				String content = rs.getString("content");
				String time = rs.getString("time");
				int zancount = rs.getInt("zancount");
				String photourl = rs.getString("photourl");

				// 转化图片以及时间格式
				photourl = IpURL.changeURL(photourl);
				time = changeTime(time);

				art.setId(articleuuid);
				art.setUserUUID(useruuid);
				art.setCircleUUID(circleuuid);
				art.setContent(content);
				art.setTitle(title);
				art.setTags(tags);
				art.setTime(time);
				art.setZanCount(zancount);
				art.setPhotoURL(photourl);

				articles.add(art);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

		for (Article art : articles) {
			System.out.println(art.getTitle());
		}

		return articles;
	}
	
	/**
	 * 查找该用户发表的文章
	 * @param userUUID 用户uuid
	 * @return 该用户发表的所有文章列表ArrayList<Article>
	 */
	public ArrayList<Article> getArticleforUserUUID(String userUUID) {
		ArrayList<Article> articles = new ArrayList<Article>();
		conn = SqlHelper.getConnection();
		try {
			st = conn.createStatement();
			String sql = "select * from article where useruuid='" + userUUID + "'order by time desc";
			System.out.println("getArticleforUserUUID: " + sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				Article art = new Article();

				String articleuuid = rs.getString("articleuuid");
				String useruuid = rs.getString("useruuid");
				String circleuuid = rs.getString("circleuuid");
				String title = rs.getString("title");
				String tags = rs.getString("tags");
				String content = rs.getString("content");
				String time = rs.getString("time");
				int zancount = rs.getInt("zancount");
				String photourl = rs.getString("photourl");

				// 转化图片以及时间格式
				photourl = IpURL.changeURL(photourl);
				time = changeTime(time);

				art.setId(articleuuid);
				art.setUserUUID(useruuid);
				art.setCircleUUID(circleuuid);
				art.setContent(content);
				art.setTitle(title);
				art.setTags(tags);
				art.setTime(time);
				art.setZanCount(zancount);
				art.setPhotoURL(photourl);

				articles.add(art);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return articles;
	}

	/**
	 * 根据articleuui来获得对应文章的所有信息
	 * 
	 * @param articleUUID 文章uuid
	 * @return 文章信息Article
	 */
	public Article getArtcleforUUID(String articleUUID) {
		Article art = new Article();
		conn = SqlHelper.getConnection();
		try {
			st = conn.createStatement();
			String sql = "select * from article where articleuuid='"
					+ articleUUID + "'";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String articleuuid = rs.getString("articleuuid");
				String useruuid = rs.getString("useruuid");
				String circleuuid = rs.getString("circleuuid");
				String title = rs.getString("title");
				String tags = rs.getString("tags");
				String content = rs.getString("content");
				String time = rs.getString("time");
				int zancount = rs.getInt("zancount");
				String photourl = rs.getString("photourl");

				// 转化图片以及时间格式
				photourl = IpURL.changeURL(photourl);
				time = changeTime(time);

				art.setId(articleuuid);
				art.setUserUUID(useruuid);
				art.setCircleUUID(circleuuid);
				art.setContent(content);
				art.setTitle(title);
				art.setTags(tags);
				art.setTime(time);
				art.setZanCount(zancount);
				art.setPhotoURL(photourl);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return art;
	}

	/**
	 * 将article转化成客户端所需求的article格式，加上附件和评论的数目
	 * @param articles 所要转化的原始文章Article
	 * @return 按照要求转化好的文章ArrayList<LogicMainPageArticle>
	 */
	public ArrayList<LogicMainPageArticle> formatMainPageArticle(
			ArrayList<Article> articles) {
		ArrayList<LogicMainPageArticle> mArticles = new ArrayList<LogicMainPageArticle>();
		conn = SqlHelper.getConnection();
		try {
			for (Article art : articles) {
				LogicMainPageArticle lma = new LogicMainPageArticle();
				String articleuuid = art.getId();
				// 获得文章的作者
				String useruuid = art.getUserUUID();
				st = conn.createStatement();
				String sql = "select name,userhead from user where useruuid = '"
						+ useruuid + "' ";
				rs = st.executeQuery(sql);
				while (rs.next()) {
					String name = rs.getString("name");
					String userhead = rs.getString("userhead");
					lma.setUserHead(userhead);
					lma.setName(name);
				}

				// 获得圈子的名称
				String circleuuid = art.getCircleUUID();
				String sqlcir = "select circlename from circle where circleuuid = '"
						+ circleuuid + "' ";
				rs = st.executeQuery(sqlcir);
				while (rs.next()) {
					String circlename = rs.getString("circlename");
					lma.setCircle(circlename);
				}

				lma.setTitle(art.getTitle());
				lma.setTime(art.getTime());
				lma.setId(art.getId());
				lma.setContent(art.getContent());
				lma.setZanCount(art.getZanCount());
				lma.setPhotoURL(art.getPhotoURL());
				lma.setType(art.getTags());

				// 获得文章附件的数目
				if (articleuuid != null) {
					// st = conn.createStatement();
					String sqlatt = "select count(*) from attachment where articleuuid = '"
							+ articleuuid + "' ";
					rs = st.executeQuery(sqlatt);
					while (rs.next()) {
						lma.setAttachments(rs.getInt(1));
					}
				}

				// 获得文章评论的数目
				if (articleuuid != null) {
					String sqlcom = "select count(*) from comment where articleuuid = '"
							+ articleuuid + "' ";
					rs = st.executeQuery(sqlcom);
					while (rs.next()) {
						lma.setComments(rs.getInt(1));
					}
				}
				mArticles.add(lma);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return mArticles;
	}

	/**
	 * 将article转化成客户端所需求的article格式，加上所有的附件和评论内容
	 * @param articles 所要转的文章
	 * @return 转化好的文章
	 */
	public ArrayList<LogicArticle> formatArticle(ArrayList<Article> articles) {
		ArrayList<LogicArticle> logicArticles = new ArrayList<LogicArticle>();

		conn = SqlHelper.getConnection();
		try {
			for (Article art : articles) {

				LogicArticle la = new LogicArticle();
				String articleuuid = art.getId();

				// 获得文章的作者
				String useruuid = art.getUserUUID();
				st = conn.createStatement();
				String sql = "select name from user where useruuid = '"
						+ useruuid + "' ";
				rs = st.executeQuery(sql);
				while (rs.next()) {
					String name = rs.getString("name");
					la.setName(name);
				}

				// 获得圈子的名称
				String circleuuid = art.getCircleUUID();
				// st = conn.createStatement();
				String sqlcir = "select circlename from circle where circleuuid = '"
						+ circleuuid + "' ";
				rs = st.executeQuery(sqlcir);
				while (rs.next()) {
					String circlename = rs.getString("circlename");
					la.setCircle(circlename);
				}

				la.setTitle(art.getTitle());
				la.setTime(art.getTime());
				la.setId(art.getId());
				la.setContent(art.getContent());
				la.setZanCount(art.getZanCount());
				la.setPhotoURL(art.getPhotoURL());
				la.setType(art.getTags());

				// 获得每个文章的附件列表
				if (articleuuid != null) {
					ArrayList<Attachment> attachments = new ArrayList<Attachment>();
					// st = conn.createStatement();
					String sqlatt = "select attachuuid,attachname,attachurl,articleuuid from attachment where articleuuid = '"
							+ articleuuid + "' ";
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
					la.setAttachments(attachments);
				}

				// 获得每个文章的评论列表
				if (articleuuid != null) {
					ArrayList<LogicComment> logiComments = new ArrayList<LogicComment>();
					// st = conn.createStatement();
					String sqlcomm = "select commentuuid,pubuseruuid,reuseruuid,content,layer,time from comment where articleuuid = '"
							+ articleuuid + "' ";
					rs = st.executeQuery(sqlcomm);
					while (rs.next()) {
						String pubUserUUID = rs.getString("pubuseruuid");
						String reUserUUID = rs.getString("reuseruuid");

						LogicComment logicComment = new LogicComment();
						logicComment.setArticleUUID(articleuuid);
						logicComment.setId(rs.getString("commentuuid"));
						logicComment.setPubuserUUID(pubUserUUID);
						logicComment.setReuserUUID(reUserUUID);
						logicComment.setContent(rs.getString("content"));
						logicComment.setLayer(rs.getInt("layer"));
						logicComment.setTime(rs.getString("time"));

						// 根据用户uuid查找对应的用户信息
						User pubUser = uInf.getUserInf(pubUserUUID);
						User reUser = uInf.getUserInf(reUserUUID);

						logicComment.setPubUserName(pubUser.getName());
						logicComment.setPubUserHead(pubUser.getUserHead());

						logicComment.setReUserName(reUser.getName());
						logicComment.setReUserHead(reUser.getUserHead());

						logiComments.add(logicComment);
					}
					la.setComments(logiComments);
				}
				logicArticles.add(la);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return logicArticles;
	}

	

	/**
	 * 将时间后面的点零去掉（由于在读取时间的时候，时间后面有个.0）
	 * 
	 * @param time 所要转化的时间
	 * @return 转化完成的时间
	 */
	private String changeTime(String time) {
		String changedTime = "";
		int length = time.length();
		changedTime = time.substring(0, length - 2);
		return changedTime;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
