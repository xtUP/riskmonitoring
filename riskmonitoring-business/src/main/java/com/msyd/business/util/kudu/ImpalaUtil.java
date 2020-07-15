package com.msyd.business.util.kudu;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.log4j.Logger;

public class ImpalaUtil {
    private static Logger logger = Logger.getLogger(ImpalaUtil.class);
	public   List<Object[]> query(String sql) {
		List<Object[]> arrayListResult = null;
		DataSource dataSource =null;
		QueryRunner queryRunner = null;
		try{
			dataSource = MysqlUtil.getDataSource();
			queryRunner = new QueryRunner(dataSource);
			logger.warn("******************开始查询kudu*******************");
			arrayListResult = queryRunner.query(sql, new ArrayListHandler());
			logger.warn("返回结果："+arrayListResult);
			return arrayListResult;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
}
