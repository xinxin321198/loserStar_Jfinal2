package com.kaen.test;

import javax.sql.DataSource;

import com.jfinal.kit.PropKit;
import com.kaen.config.JfinalConfig;

/**
 * 
 * author: loserStar
 * date: 2018年3月28日下午2:45:40
 * remarks:
 */
public class GenCodeDBConfig_local {
	public static DataSource start() {
		//改为直接从配置文件读取配置
		PropKit.use(JfinalConfig.propertiesFileNameString_test);
		return null;
//		String connectionStr = PropKit.get("ynzy_zp.jdbcUrl");
//		DruidPlugin dp  = new DruidPlugin(connectionStr, PropKit.get("ynzy_zp.userName"),PropKit.get("ynzy_zp.passWord"));
//		dp.setDriverClass(PropKit.get("ynzy_zp.driverClass"));
//		
//		ActiveRecordPlugin arp = new ActiveRecordPlugin(DsConstans.dataSourceName.ynzy_zp,dp);
//		arp.setShowSql(false);//打印出执行的sql
//		arp.setDialect(new AnsiSqlDialect());
//		arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));// 不区分大小写
//		dp.start();
//		arp.start();
//		return dp.getDataSource();
	}
}
