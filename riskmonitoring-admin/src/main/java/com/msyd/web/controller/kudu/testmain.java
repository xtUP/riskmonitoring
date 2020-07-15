//package com.msyd.web.controller.kudu;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
// 
//public class testmain {
//    static String JDBC_DRIVER = "org.apache.hive.jdbc.HiveDriver";
//    static String CONNECTION_URL = "jdbc:impala://172.30.2.72:21050/dws";//bjs_db为数据库名
// 
//    public static void main(String[] args)
//    {
//        Connection con = null;
//        ResultSet rs = null;
//        PreparedStatement ps = null;
//        try
//        {
//            Class.forName(JDBC_DRIVER);
//            con = DriverManager.getConnection(CONNECTION_URL);
//            ps = con.prepareStatement("SELECT * FROM dws_msyd_antifraud_req_log");
//            rs = ps.executeQuery();
//            while (rs.next())
//            {
////kudu数据库的字段           
//     System.out.println(rs.getString("i_e_flag")+","+rs.getString("arrival_no"));
//            }
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        } finally
//        {
//            //关闭rs、ps和con
//        }
//    }
//}
