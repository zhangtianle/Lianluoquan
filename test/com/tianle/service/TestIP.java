package com.tianle.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

public class TestIP {
	@Test
	public void changeURL() {
		String changed = "";
		String ip = "";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		changed = "http://" + ip + "/Lianluoquan/upload/img/" + "dfs.jpg";
		System.out.println(changed);
	}
}
