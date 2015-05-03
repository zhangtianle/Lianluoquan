package com.tianle.service.mainPage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

public class CoreService {
	public String processRequest(HttpServletRequest request) throws IOException {
		ServletInputStream inputStream = request.getInputStream();
		Reader reader = new InputStreamReader(inputStream, "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(reader);
		String str = "";
		StringBuffer sb = new StringBuffer();
		while ((str = bufferedReader.readLine()) != null) {
			sb.append(str);
		}
		reader.close();
//		System.out.println(request.getParameter("name") + "  neir");
		return sb.toString();
	}

}
