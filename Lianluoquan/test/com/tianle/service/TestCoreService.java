package com.tianle.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.tianle.model.base.Attachment;
import com.tianle.model.logic.LogicArticle;
import com.tianle.service.mainPage.MainPageService;



public class TestCoreService {
	public static void main(String[] args) {
		MainPageService mp = new MainPageService();
		ArrayList arts = mp.mainPageResponse("kyleuuid","0");
		ArrayList articles = mp.formatArticle(arts);
//		System.out.println(arts);
//		System.out.println(articles);
//		JSONArray ja1 = JSONArray.fromObject(arts);
//		System.out.println(ja1);
		JSONArray ja = JSONArray.fromObject(articles);
		System.out.println(ja);
		
		String sja = ja.toString();
		JSONArray sjsona = JSONArray.fromObject(sja);
		ArrayList<LogicArticle> sarticles = (ArrayList) JSONArray.toCollection(sjsona,LogicArticle.class);
		LogicArticle la = sarticles.get(0);
		
		List<Attachment> atts =  la.getAttachments();
		JSONArray satts = JSONArray.fromObject(atts);
		System.out.println(atts.get(0).getAttachName().toString());
	}
}
