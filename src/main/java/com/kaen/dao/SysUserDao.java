package com.kaen.dao;

import com.loserstar.utils.db.jfinal.base.imp.BaseDao;

/**
 * 本文件代码生成器自动生成，本文件若已存在，则不会被覆盖，需要覆盖请删除本文件后重新生成
 * author: autoGenerate
 * date: 2023-12-14 16:27:16
 * remarks:用户表
 */
public class SysUserDao extends BaseDao {
	public static final String TABLE_NAME = "ynzy_zp.sys_user";
	public static final String PRIMARY_KEY = "id";
	public static final String SOFT_DEL_FIELD= "DEL";
	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}

	@Override
	protected String getPrimaryKey() {
		// TODO Auto-generated method stub
		return PRIMARY_KEY;
	}

	@Override
	protected String getSoftDelField() {
		// TODO Auto-generated method stub
		return SOFT_DEL_FIELD;
	}

	/**
	 * 指定数据源
	 * @param dataSourceName 数据源名称（com.kaen.constants.DsConstans.dataSourceName）
	 */
	public SysUserDao(String dataSourceName) {
		super(dataSourceName);
		// TODO Auto-generated constructor stub
	}
}
