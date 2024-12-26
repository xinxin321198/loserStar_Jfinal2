package com.kaen.test;

import javax.sql.DataSource;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.kaen.config.JfinalConfig;
import com.kaen.constants.DsConstans;

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
		String connectionStr = PropKit.get("mysql.jdbcUrl");
		DruidPlugin dp  = new DruidPlugin(connectionStr, PropKit.get("mysql.userName"),PropKit.get("mysql.passWord"));
		dp.setDriverClass(PropKit.get("mysql.driverClass"));

		ActiveRecordPlugin arp = new ActiveRecordPlugin(DsConstans.dataSourceName.myql,dp);
		arp.setShowSql(false);//打印出执行的sql
		arp.setDialect(new AnsiSqlDialect());
		arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));// 不区分大小写
		dp.start();
		arp.start();
		return dp.getDataSource();
	}
}
