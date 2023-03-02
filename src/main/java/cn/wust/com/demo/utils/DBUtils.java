package cn.wust.com.demo.utils;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBUtils {
    //在java语言中，方法只能调用方法
    public static Connection connection;
    static {
        //只执行一遍，在这个JVM中共享这一份数据
        Properties prop = new Properties();
        try {
            prop.load(ClassLoader.getSystemResourceAsStream("application.properties"));
            connection = DriverManager.getConnection(prop.getProperty("spring.datasource.url"),
                    prop.getProperty("spring.datasource.username"),
                    prop.getProperty("spring.datasource.password"));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }


    }

    public static int executeUpdate(String sql,Object... params){
        int num =0;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            for(int i=0;i<params.length;i++){
                ps.setObject((i+1),params[i]);
            }
            num = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    public static ResultSet executeQuery(String sql,Object... params){
        ResultSet resultSet=null;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            for(int i=0;i<params.length;i++){
                ps.setObject((i+1),params[i]);
            }
             resultSet = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;

    }
}
