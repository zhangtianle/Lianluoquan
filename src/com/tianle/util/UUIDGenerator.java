package com.tianle.util;

import java.util.UUID;

import org.junit.Test;

/**
 * UUID生成器
 * Comments:
 * @author Kyle
 * @date 2015年6月7日 下午4:13:43
 */
public class UUIDGenerator {
	
	/**
	 * 生成UUID
	 * @return 返回UUID
	 */
	public static String getUUID() {  
        UUID uuid = UUID.randomUUID();  
        String str = uuid.toString();  
        // 去掉"-"符号  
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);  
        return temp;  
    }
	@Test
	public void print() {
		System.out.println(getUUID());
	}
}
