package com.msyd.business.util.kudu;

import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
public class MysqlUtil {
	private static DataSource dataSource; //Druid 连接池
    private static Connection conn; //数据库连接对象
    private static Logger logger = Logger.getLogger(MysqlUtil.class);
    private static InputStream in = null;
    private final static MysqlUtil mysqlUtil = new MysqlUtil();
    static
    {
        //使用druid.properties属性文件的配置方式 设置参数，文件名称没有规定但是属性文件中的key要一定的
        // 从druid.properties属性文件中获取key参数对应的value配置信息
        Properties properties = new Properties();
        try
        {
            //resources目录下的配置文件实际都会被编译进到 \target\classes 目录下
            in = mysqlUtil.getClass().getResourceAsStream("/Impaladruid.properties");
            properties.load(in);
            in.close();
        }
        catch (IOException e)
        {
            logger.error("ERROR:",e);//错误异常完整写入日志文件
//            e.printStackTrace();//窗口也打印错误信息
        }
        // 创建 Druid 连接池
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            logger.error("ERROR:",e);//错误异常完整写入日志文件
//            e.printStackTrace();//窗口也打印错误信息
        }
 
        //从 连接池中获取一个 数据库连接对象
//        Connection  数据库连接对象conn  =  dataSource.getConnection();
        //调用数据库连接对象的close()方法，把连接对象归还给连接池，并不是关闭连接
//        conn.close();
    }
 
    //获得连接池
    public static DataSource getDataSource()
    {
        return dataSource;
    }
 
    //从 连接池中获取一个 数据库连接对象
    public static Connection getConnection()
    {
        //从 连接池中获取一个 数据库连接对象
        try {
//            System.out.println("从 连接池中获取一个 数据库连接对象");
            conn  =  dataSource.getConnection();
        } catch (Exception e) {
//            e.printStackTrace();//窗口也打印错误信息
            logger.error("ERROR:",e);//错误异常完整写入日志文件
        }
        return conn;
    }
 
    /*
        new QueryRunner(MysqlUtil.getDataSource())
                QueryRunner中传入连接池，交由QueryRunner自动操作连接池中的连接。提供了自定事务处理、自动释放资源等操作，无需再手动。
                所以无需手动调用conn.close()，交由QueryRunner自动管理。
    */
    //调用数据库连接对象的close()方法，把指定的连接对象归还给连接池，并不是关闭连接
    public static void connectionClose(Connection conn)
    {
        //调用数据库连接对象的close()方法，把连接对象归还给连接池，并不是关闭连接
        try {
            conn.close();
        } catch (SQLException e) {
//            e.printStackTrace();//窗口也打印错误信息
            logger.error("ERROR:",e);//错误异常完整写入日志文件
        }
    }
}
