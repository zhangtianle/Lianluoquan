package com.tianle.service;

import java.util.ArrayList;

import com.tianle.service.mainPage.MainPageService;

public class TestMainPageService {
	public static void main(String[] args) {
		MainPageService mps = new MainPageService();
		ArrayList a = mps.mainPageResponse("kyleuuid");
		mps.formatArticle(a);
	}

}
