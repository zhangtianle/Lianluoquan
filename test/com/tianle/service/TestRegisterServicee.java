package com.tianle.service;

import com.tianle.service.register.RegisterService;

public class TestRegisterServicee {
	public static void main(String[] args) {
		
		String s = "{name='kyle',passWord='123',studentNum='234234'}";
		
		RegisterService rs = new RegisterService();
		rs.registerUser(s);
	}
}
