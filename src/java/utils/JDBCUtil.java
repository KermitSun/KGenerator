package utils;

import entity.DB;

import java.sql.*;

/**
 * @Date: 2019/6/313:12
 * @Author: BoyuSun
 * @Description:
 */
public class JDBCUtil {
    private JDBCUtil(){}

    /**
     * 获取 Connetion
     * @return
     * @throws SQLException
     */
    public static Connection getConn(DB db) throws SQLException, ClassNotFoundException {
        try {
            Class.forName(db.getDriver());
        } catch(Exception e){
            e.printStackTrace();
        }
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
        } catch(Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 释放资源
     * @param conn
     * @param st
     * @param rs
     */
    public static void colseResource(Connection conn, Statement st, ResultSet rs) {
        closeResultSet(rs);
        closeStatement(st);
        closeConnection(conn);
    }

    /**
     * 释放连接 Connection
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if(conn !=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //等待垃圾回收
        conn = null;
    }

    /**
     * 释放语句执行者 Statement
     * @param st
     */
    public static void closeStatement(Statement st) {
        if(st !=null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //等待垃圾回收
        st = null;
    }

    /**
     * 释放结果集 ResultSet
     * @param rs
     */
    public static void closeResultSet(ResultSet rs) {
        if(rs !=null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //等待垃圾回收
        rs = null;
    }
}
