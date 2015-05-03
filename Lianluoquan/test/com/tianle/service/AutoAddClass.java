package com.tianle.service;

import com.tianle.service.register.AddSthService;

public class AutoAddClass {
	public static void main(String[] args) {
		AddSthService ass = new AddSthService();
		
		for(long i = 401401; i<401415 ; i++) {
//			System.out.println(i);
		ass.addClass(2014, "计算机科学与技术", "0"+i+"");
		}
	}
}
