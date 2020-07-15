//package com.msyd.web.controller.kudu;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
// 
//public class Impala_jdbc {
//    private static String DRIVER = "com.cloudera.impala.jdbc41.Driver";
//    private static String URL = "jdbc:impala://172.30.2.72:21050/dws";
// 
//    public static void main(String[] args)
//    {
//        Connection conn = null;
//        ResultSet rs = null;
//        PreparedStatement pst = null;
// 
// 
//        try {
//            Class.forName(DRIVER);
//            conn = DriverManager.getConnection(URL);
//            pst = conn.prepareStatement("select * from dws.dws_msyd_antifraud_req_log limit 3");
//            rs = pst.executeQuery();
//            while (rs.next()) {
//                //rs.get类型(字段列)：字段列从1开始算起
//                System.out.println(rs.getString(1) + "," + rs.getObject(2) + "," + rs.getObject(3));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                conn.close();
//                pst.close();
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }
// 
//}
