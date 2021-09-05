package com.codingfuture.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCUtils {
    private JDBCUtils() {

    }
    private static final ComboPooledDataSource comboPooledDataSource=new ComboPooledDataSource();
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = comboPooledDataSource.getConnection();
        }catch (SQLException e){
            System.err.println("获取数据库连接失败");
        }
        return connection;
    }
}
