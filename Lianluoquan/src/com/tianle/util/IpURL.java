package com.tianle.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 更改路径
 * Comments:
 * @author Kyle
 * @date 2015年6月7日 下午4:14:29
 */
public class IpURL {
		/**
		 * 将数据库中存储的路径更改为客户端可读取的路径
		 * @param url 将要更改的路径
		 * @return 更改后的路径
		 */
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
				ip = "113.251.163.35";
				changed = "http://" + ip + "/Lianluoquan/" + url;
			}
			System.out.println("send URL: " + changed);

			return changed;
		}
}
