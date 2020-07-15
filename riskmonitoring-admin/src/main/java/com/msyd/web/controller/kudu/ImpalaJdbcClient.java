package com.msyd.web.controller.kudu;
//package com.msyd.web.controller.kudu;
//
//import java.sql.*;
//
//
//public class ImpalaJdbcClient {
//    private static String JDBC_DRIVER = "com.cloudera.impala.jdbc41.Driver";
//    //默认数据库default,默认用户名就是登录账号 密码 为空
//    private static String CONNECTION_URL = "jdbc:impala://172.30.2.72:21050/dws;auth=noSasl";
//   // private static final Logger log = Logger.getLogger(ImpalaJdbcClient.class);
//    public static void main(String[] args) throws Exception {
//        final Connection con = contants.getConn();
//        //查询
//        String sql = "select * from dws;";
//        final ResultSet rs = contants.QueryRows(sql);
//        System.out.println(rs);
//        contants.printRows(rs);
//        //插入
////        Contants.insertRows(new Person(11 , 11 , "zhansan" , "female" , "photo10"));
////        String sql = "select * from external_table2;";
////        final ResultSet rs = contants.QueryRows(sql);
////        Contants.printRows(rs);
//        //删除
////        Contants.deleteRows(10);
////        String sql = "select * from external_table2;";
////        final ResultSet rs = contants.QueryRows(sql);
////        Contants.printRows(rs);
//        //更新
////        Contants.updateRows(new Person(1,1,"aaaa" , "male" , "pppppp"));
////        String sql = "select * from external_table2;";
////        final ResultSet rs = contants.QueryRows(sql);
////        Contants.printRows(rs);
//    }
//}
