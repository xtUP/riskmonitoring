//package com.msyd.web.controller.kudu;
//
//
//import java.sql.*;
//
//import com.cloudera.impala.impala.jdbc41.ImpalaJDBC41Connection;
//
///**
// * Created by angel；
// */
//public class contants {
//    private static String JDBC_DRIVER = "com.cloudera.impala.jdbc41.Driver";
//    private static String CONNECTION_URL = "jdbc:impala://172.30.2.72:21050/dws;auth=noSasl";
//    static ImpalaJDBC41Connection con = null;
//    static ResultSet rs = null;
//    static PreparedStatement ps = null;
//    //连接
//    public static Connection getConn() {
//
//
//        try {
//            Class.forName(JDBC_DRIVER);
//            con = (ImpalaJDBC41Connection)DriverManager.getConnection(CONNECTION_URL);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return con;
//    }
//    //查询
//    public static ResultSet QueryRows(String sql){
//        try {
//            ps = con.prepareStatement(sql);
//            rs = ps.executeQuery();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            //关闭rs、ps和con
//            if(rs != null){
//                try {
//                    rs.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(ps != null){
//                try {
//                    ps.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(con != null){
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return rs;
//    }
//    public static void main(String[] args) {
//		contants.getConn();
//		ResultSet r=contants.QueryRows("select * from dws_msyd_antifraud_face_log limit 10");
//		try{
//            while (rs.next()) {
//            	String create_date = rs.getString("create_date");
//                String app_id = rs.getString("app_id");
//                System.out.println("create_date-----"+create_date+"app_id----"+app_id);
//            }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                //关闭rs、ps和con
//                if(rs != null){
//                    try {
//                        rs.close();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if(ps != null){
//                    try {
//                        ps.close();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if(con != null){
//                    try {
//                        con.close();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//    }
//    //打印
//    public static void printRows(ResultSet rs){
//        try{
//
//            while (rs.next()) {
//            final int companyId = rs.getInt("companyid");
//            final int workId = rs.getInt("workid");
//            final String name = rs.getString("Name");
//            final String gender = rs.getString("gender");
//            final String photo = rs.getString("photo");
//            System.out.println(companyId + "----" + workId + "----" + name+ "----" +gender+ "----" + photo);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            //关闭rs、ps和con
//            if(rs != null){
//                try {
//                    rs.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(ps != null){
//                try {
//                    ps.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(con != null){
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
////    //插入
////    public static void insertRows(Person person){
////        contants.getConn();
////        String sql = "insert into external_table2 (companyid,workid,name,gender,photo) values(?,?,?,?,?)";
////        PreparedStatement pstmt;
////        try {
////
////            pstmt = (PreparedStatement) con.prepareStatement(sql);
////            pstmt.setInt(1 , person.getCompanyId());
////            pstmt.setInt(2, person.getWorkId());
////            pstmt.setString(3, person.getName());
////            pstmt.setString(4, person.getGender());
////            pstmt.setString(5, person.getPhoto());
////            pstmt.executeUpdate();
////
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////    }
////
////    //更新
////    public static void updateRows(Person person){
////        Connection conn = getConn();
////        String sql = "update external_table2 set photo='" + person.getPhoto() + "' , name='"+person.getName()+"' where workid=" + person.getWorkId();
////
////        PreparedStatement pstmt;
////        try {
////            pstmt = (PreparedStatement) conn.prepareStatement(sql);
////            pstmt.executeUpdate();
////
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////    }
////
////    //删除
////    public static void deleteRows(int workID){
////        Connection conn = getConn();
////        String sql = "delete from external_table2 where workid="+workID;
////        PreparedStatement pstmt;
////        try {
////            pstmt = (PreparedStatement) conn.prepareStatement(sql);
////            pstmt.executeUpdate();
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////    }
//
//
//}
