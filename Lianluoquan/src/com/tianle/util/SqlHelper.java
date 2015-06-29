/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tianle.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.junit.Test;

/**
 * 数据连接获取
 * @author Administrator
 */
public class SqlHelper {
	
	/**
	 * 获得数据库连接，从文件中获取配置信息
	 * @return 数据库连接Connection
	 */
    public static Connection getConnection() {
        Connection conn = null;
        Properties prop = new Properties();
        InputStream in = null;;
        String path=Thread.currentThread().getContextClassLoader().getResource("").toString();  
        path=path.replace("%20", " "); // 将/换成\
        path=path.replace('/', '\\'); // 将/换成\  
        path=path.replace("file:", ""); //去掉file:  
        path=path.replace("classes\\", ""); //去掉class\  
        path=path.substring(1); //去掉第一个\,如 \D:\JavaWeb...  
        path+="DBConfig.properties";  
        
//        System.out.println(path);
       
        try {
            in = new FileInputStream(new File(path));
            prop.load(in);
//            prop.load(new FileInputStream("WebRoot/WEB-INF/DBConfig.properties"));  
            String strUserId = prop.getProperty("conn.strUserId");
            String strPassword = prop.getProperty("conn.strPassword");
            String strUrl = prop.getProperty("conn.strUrl");
            String className = prop.getProperty("conn.className");
              System.out.println(prop);
            //初始化驱动包  
            Class.forName(className);
            //根据数据库连接字符，名称，密码给conn赋值  
            conn = DriverManager.getConnection(strUrl,strUserId,strPassword);
            return conn;
        } catch (Exception e) {
            // TODO: handle exception  
            e.printStackTrace();
        }
        return null;
    }
   
}
