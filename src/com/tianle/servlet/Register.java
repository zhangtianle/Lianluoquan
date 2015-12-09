package com.tianle.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.tianle.model.base.Article;
import com.tianle.model.base.Attachment;
import com.tianle.model.base.Circle;
import com.tianle.model.base.Comment;
import com.tianle.model.logic.LogicComment;
import com.tianle.model.logic.LogicFriend;
import com.tianle.model.logic.LogicMainPageArticle;
import com.tianle.model.logic.LogicUser;
import com.tianle.service.article.PubArticle;
import com.tianle.service.article.RecArticle;
import com.tianle.service.article.ZanPlus;
import com.tianle.service.attachment.AttachmentService;
import com.tianle.service.comment.CommentService;
import com.tianle.service.friend.FriendService;
import com.tianle.service.mainPage.MainPageService;
import com.tianle.service.organization.UseraddtoCircle;
import com.tianle.service.register.RegisterService;
import com.tianle.service.search.SearchService;

public class Register extends HttpServlet {

	/**
	 * localhost/Lianluoquan/Register?type=article&userUUID=kyleuuid&circle=all
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		String type = request.getParameter("type");
		PrintWriter out = response.getWriter();
		System.out.println(type);
		RegisterService rs = new RegisterService();
		if (type != null) {
			if (type.equals("register")) {
				String reregisertinfor = request.getParameter("registerInfor");
				System.out.println(reregisertinfor);
				// 调用方法注册用户，包含（姓名，密码，学号，学院，班级）
				String uuid = rs.registerUser(reregisertinfor);
				if (uuid.equals("")) {
					out.println("false");
					out.close();
				} else {
					// 根据用户uuid返回用户详细信息
					// String logicUser =
					// rs.selcetStuInf(uuid).replaceAll("userUUID","id");
					String logicUser = rs.logicUserJSON(rs.selcetStuInf(uuid));
					// logicUser = "{\"users\": ["+logicUser+"]}";
					System.out.println(logicUser);
					out.println(logicUser);
					out.close();
				}
			} else if (type.equals("login")) {
				// String loginInfor =
				// request.getParameter("registerInfor").replaceAll("id","userUUID");
				String loginInfor = request.getParameter("registerInfor");
				String uuid = rs.login(loginInfor);
				if (uuid.equals("")) {
					out.println("false");
					out.close();
				} else {
					// 根据用户uuid返回用户详细信息
					// String logicUser =
					// rs.selcetStuInf(uuid).replaceAll("userUUID","id");
					String logicUser = rs.logicUserJSON(rs.selcetStuInf(uuid));
					// logicUser = "{\"users\": ["+logicUser+"]}";
					System.out.println(logicUser);
					out.println(logicUser);
					out.close();
				}
			} else if (type.equals("article")) {
				String userUUID = request.getParameter("userUUID");
				String circle = request.getParameter("circle");
				String page = request.getParameter("page");
				String time = request.getParameter("time");
				if (!userUUID.equals("") && !circle.equals("")) {
					MainPageService mps = new MainPageService();
					ArrayList<Article> articles = mps.mainPageResponse(
							userUUID, page, circle, time);
					ArrayList<LogicMainPageArticle> lmpas = mps
							.formatMainPageArticle(articles);
					JSONArray jsonArray = JSONArray.fromObject(lmpas);
					String sArticles = "{\"logicArticles\":"
							+ jsonArray.toString() + "}";
					out.println(sArticles);
					System.out.println(sArticles);
					out.close();
				}
			} else if (type.equals("uploadArticle")) {
				String uploadArt = request.getParameter("article");
				Article recArticle = new RecArticle().getArt(uploadArt);
				String articleUUID = new PubArticle().pubMyArticle(recArticle);
				System.out.println(articleUUID);
			} else if (type.equals("zan")) {
				String zanId = request.getParameter("articleuuid");
				ZanPlus zp = new ZanPlus();
				int result = zp.addZan(zanId);
				if (result > 0) {
					int count = zp.zanCount(zanId);
					out.println(count);
					out.close();
				}
			} else if (type.equals("comment")) {
				String commentJSON = request.getParameter("comment");
				System.out.println("接收到的评论： " + commentJSON);
				CommentService ac = new CommentService();
				Comment comment = ac.getComment(commentJSON);
				comment = ac.addtoDataBase(comment);
				LogicComment logicCommnet = ac.changeComment(comment);
				JSONObject jsonObject = JSONObject.fromObject(logicCommnet);
				if (jsonObject != null && !jsonObject.equals("")) {
					System.out.println("评论成功，评论为：   " + jsonObject);
					out.println(jsonObject);
					out.close();
				} else {
					out.println("false");
					out.close();
				}
			} else if (type.equals("addorg")) {
				String userUUID = request.getParameter("useruuid");
				String orgUUID = request.getParameter("orguuid");
				UseraddtoCircle uatc = new UseraddtoCircle();
				uatc.addCircle(userUUID, orgUUID);
			} else if (type.equals("refresh")) {
				String articleUUID = request.getParameter("articleUUID");
				String time = request.getParameter("time");
				CommentService cs = new CommentService();
				AttachmentService as = new AttachmentService();
				ArrayList<LogicComment> logiComments = cs.selcetComments(
						articleUUID, time);
				ArrayList<Attachment> attachments = as
						.getAttachement(articleUUID);

				JSONArray jsonAttachArray = JSONArray.fromObject(attachments);
				JSONArray jsonCommentsArray = JSONArray
						.fromObject(logiComments);

				String slAttachments = "\"attachments\":"
						+ jsonAttachArray.toString();
				String slogiComments = "\"comments\":"
						+ jsonCommentsArray.toString();

				String sendOut = "{" + slogiComments + "," + slAttachments
						+ "}";
				out.println(sendOut);
				System.out.println("发出去的东东评论加附件:" + sendOut);
				out.close();

				/*
				 * MainPageService mps = new MainPageService(); Article article
				 * = mps.getArtcleforUUID(articleUUID); ArrayList<Article>
				 * articles = new ArrayList<Article>(); articles.add(article);
				 * ArrayList<LogicArticle> logicArticles =
				 * mps.formatArticle(articles); JSONArray jsonArray =
				 * JSONArray.fromObject(logicArticles); String s =
				 * jsonArray.toString(); String sArticles = s.substring(1,
				 * s.length()-1); out.println(sArticles);
				 * System.out.println("点击查看文章详细内容： " + sArticles); out.close();
				 */
			} else if (type.equals("myArticle")) {
				String userUUID = request.getParameter("userUUID");
				MainPageService mps = new MainPageService();
				ArrayList<Article> articles = mps
						.getArticleforUserUUID(userUUID);
				if (articles.size() != 0) {
					ArrayList<LogicMainPageArticle> lmpas = mps
							.formatMainPageArticle(articles);
					JSONArray jsonArray = JSONArray.fromObject(lmpas);
					String sArticles = "{\"logicArticles\":"
							+ jsonArray.toString() + "}";
					out.println(sArticles);
					System.out.println(sArticles);
					out.close();
				} else {
					out.println("false");
					out.close();
				}
			} else if(type.equals("search")) {
//				String userUUID = request.getParameter("userUUID");
				String infor = request.getParameter("infor");
				SearchService searchService = new SearchService();
				List<Circle> circles = searchService.searchCircles(infor);
				List<LogicUser> logicUsers = searchService.searchUsers(infor);
				String resutl = searchService.searchMain(logicUsers, circles);
				out.println(resutl);
				System.out.println("搜索圈子和用户： " + resutl);
				out.close();
			} else if(type.equals("follow")) {
				String userUUID = request.getParameter("userUUID");
				String followType = request.getParameter("followType");
				String followUUID = request.getParameter("followUUID");
				if(followType.equals("circle")) {
					SearchService searchService = new SearchService();
					Circle circle = searchService.followCircle(userUUID, followUUID);
					String scircle = "";
					if(circle != null) {
						scircle = searchService.circletoJSON(circle);
					} else {
						scircle = "false";
					}
					out.println(scircle);
					System.out.println("关注的圈子： " + scircle);
					out.close();
				} else if (followType.equals("user")) {
					FriendService fs = new FriendService();
					String sLogicFriend = "";
					if(!fs.isFollowed(userUUID, followUUID)) {
						LogicFriend logicFriend = fs.followFriend(userUUID, followUUID);
						sLogicFriend = fs.friendtoJson(logicFriend);
					} else {
						sLogicFriend = "false";
					}
					out.println(sLogicFriend);
					System.out.println("关注的好友： " + sLogicFriend);
					out.close();
				} else {
					out.println("发送的请求参数有误");
					System.out.println("发送的请求参数有误");
					out.close();
				}
			}
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
