package com.tianle.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpURL {
	// 更改图片路径
		public static String changeURL(String url) {
			String changed = "";
			String ip = "";
			if (url != null && !url.equals("null")) {
				try {
					ip = InetAddress.getLocalHost().getHostAddress();
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ip = "cqingkyle.java.jspee.net";
				changed = "http://" + ip + "/Lianluoquan/" + url;
			}
			System.out.println("send URL: " + changed);

			return changed;
		}
}
