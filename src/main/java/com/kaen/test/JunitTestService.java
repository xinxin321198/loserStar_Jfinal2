package com.kaen.test;

import org.junit.Before;

public class JunitTestService {
	
	/**
	 * 前置方法，加载配置文件，连接数据库
	 */
	@Before
	public void startDb() {
		GenCodeDBConfig_local.start();
	}
	
}
