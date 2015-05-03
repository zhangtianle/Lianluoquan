package com.tianle.service;

import org.junit.Test;

public class TestString {

	@Test
	public void testS() {
		String ss = ",D:\\Program Files\\apache-tomcat-7.0.54-windows-x64\\apache-tomcat-7.0.54\\webapps\\Lianluoquan\\upload\\img\\1430457124710.jpg";
		String[] sss = ss.split(",");
		String newString= "";
		for(String s : sss) {
			int i = s.indexOf("upload");
			int end = s.length();
			
			if (i != -1) {
				s = s.substring(i, end);
				newString = newString + s;
			}
		}
		newString = newString.replaceAll("\\\\", "/");
		System.out.println(newString);
	}
}
