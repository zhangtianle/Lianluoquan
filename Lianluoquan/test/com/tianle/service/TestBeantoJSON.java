package com.tianle.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.tianle.model.base.Article;
import com.tianle.model.base.Circle;
import com.tianle.model.base.Comment;
import com.tianle.model.logic.LogicComment;
import com.tianle.model.logic.LogicMainPageArticle;
import com.tianle.model.logic.LogicUser;

public class TestBeantoJSON {
	@Test
	public void reg() {
		LogicUser lu = new LogicUser();
		JSONObject jsonObject = JSONObject.fromObject(lu);
		System.out.println(jsonObject);
	}
	
	@Test
	public void article() {
		LogicMainPageArticle lmp = new LogicMainPageArticle();
		JSONArray jsonObject = JSONArray.fromObject(lmp);
		System.out.println(jsonObject);
	}
	
	@Test
	public void uploadArticle() {
		Article a = new Article();
		JSONObject jsonObject = JSONObject.fromObject(a);
		System.out.println(jsonObject);
	}
	
	@Test
	public void comment() {
		Comment c = new Comment();
		JSONObject jsonObject = JSONObject.fromObject(c);
		System.out.println(jsonObject);
		
		LogicComment lc = new LogicComment();
		JSONObject lcc = JSONObject.fromObject(lc);
		System.out.println(lcc);
	}
	
	@Test
	public void search() {
		LogicUser lu = new LogicUser();
		Circle c = new Circle();
		
		JSONArray userJsonArray = JSONArray.fromObject(lu);
		JSONArray circlesJsonArray = JSONArray.fromObject(c);
		String sUser = "\"users\":" + userJsonArray.toString();
		String sCircles = "\"circles\":" + circlesJsonArray.toString();
		System.out.println("{" + sUser + "," + sCircles + "}");
	}
	
	
}
