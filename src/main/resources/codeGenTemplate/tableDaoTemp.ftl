<#setting classic_compatible=true>
package ${packgeName};

import com.loserstar.utils.db.jfinal.base.imp.BaseDao;

/**
 * 本文件代码生成器自动生成，代码生成器会覆盖该文件，如需扩展方法，请继承该类或者使用service进行扩展
 * author: autoGenerate
 * date: ${.now}
 * remarks:${tableRemarks}
 */
public class ${className} extends BaseDao {
	public static final String TABLE_NAME = "${schemas}.${tableName}";
	public static final String PRIMARY_KEY = "${primaryKey}";
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
	public ${className}(String dataSourceName) {
		super(dataSourceName);
		// TODO Auto-generated constructor stub
	}
}
