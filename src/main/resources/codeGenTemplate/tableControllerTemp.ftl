<#setting classic_compatible=true>
package com.kaen.controller.pc;

import com.jfinal.core.Path;
import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.activerecord.Page;
import com.kaen.constants.DsConstans;
import com.kaen.dao.${className}Dao;
import com.kaen.entity.${className};
import com.loserstar.utils.db.jfinal.base.imp.WhereHelper;
import com.loserstar.utils.db.jfinal.vo.VResult;
import com.loserstar.utils.idgen.LoserStarIdGenUtil;
import com.loserstar.utils.json.LoserStarJsonUtil;

import java.util.ArrayList;
import java.util.List;
/**
 * ${tableRemarks}
 * @author loserStar
 *
 */
@Path(value = "/${fristLowerClassName}")
public class ${className}Controller extends PcBaseController {
	
	private ${className}Dao ${fristLowerClassName}Dao = new ${className}Dao(DsConstans.dataSourceName.${dataSouceName});

	/**
	 * 列表页面
	 */
	public void listPage() {
		try {
			String userid = getUserId();
			renderFreeMarker("/view/${fristLowerClassName}/${fristLowerClassName}List.html");
		} catch (Exception e) {
			e.printStackTrace();
			renderError(e.getMessage());
		}
	}
	
	/**
	 * 获取分页数据
	 */
	public void getPageData() {
		VResult result = new VResult();
		try {
            <#list fieldList as field>
            String ${field.name} = getPara("${field.name}");
            </#list>
			//排序字段
			//String sort_filed = getPara("sort_filed");
			//String sort_type = getPara("sort_type");
			
			WhereHelper whereHelper = new WhereHelper();
			List<Object> queryParamList = new ArrayList<Object>();
            <#list fieldList as field>
            if (checkNull(${field.name})) {
				whereHelper.addStrWhere("and ${field.name} like ?");
				queryParamList.add("%"+${field.name}+"%");
			}
            </#list>
			//排序
			//if (checkNull(sort_filed)) {
				//whereHelper.addStrOrder("order by "+getSortField(sort_filed)+" "+sort_type);
				//whereHelper.addStrOrder("order by sort asc");
			//}
			Page<${className}> dataPage =  ${fristLowerClassName}Dao.getListPage(getPageNumber(), getPageSize(), whereHelper, ${className}.class, queryParamList.toArray());
			result.ok("获取数据成功");
			result.setData(dataPage);
		} catch (Exception e) {
			e.printStackTrace();
			result.error(e.getMessage());
		}
		renderJson(result);
	}

	/**
	 * 表单页面
	 */
	public void formPage() {
		try {
			String userid = getUserId();
			String ${primaryKey} = getPara("${primaryKey}");
			if (checkNull(${primaryKey})) {
				${className} ${fristLowerClassName} = ${fristLowerClassName}Dao.getById(${primaryKey}, ${className}.class);
			}
			String op_type = getPara("op_type");
			setAttr("${primaryKey}", ${primaryKey});
			setAttr("op_type", op_type);
			setAttr("currentTimeMillis", System.currentTimeMillis());
			renderFreeMarker("/view/${fristLowerClassName}/${fristLowerClassName}Form.html");
		} catch (Exception e) {
			e.printStackTrace();
			renderError(e.getMessage());
		}

	}

	/**
	 * 表单页面查看
	 */
	public void formPageView() {
		try {
			String userid = getUserId();
			String ${primaryKey} = getPara("${primaryKey}");
			if (checkNull(${primaryKey})) {
				${className} ${fristLowerClassName} = ${fristLowerClassName}Dao.getById(${primaryKey}, ${className}.class);
			}
			String op_type = getPara("op_type");
			setAttr("${primaryKey}", ${primaryKey});
			setAttr("op_type", "view");
			setAttr("currentTimeMillis", System.currentTimeMillis());
			renderFreeMarker("/view/${fristLowerClassName}/${fristLowerClassName}Form.html");
		} catch (Exception e) {
			e.printStackTrace();
			renderError(e.getMessage());
		}
	}
	
	
	/**
	 * 保存（新增和修改一体的）
	 */
	public void save() {
		VResult result = new VResult();
		try {
			String userid = getUserId();
			String json = HttpKit.readData(getRequest());
			System.out.println(json);
			${className} ${fristLowerClassName} = LoserStarJsonUtil.toModel(json, ${className}.class);
			boolean flag = false;
			if (${fristLowerClassName}Dao.getById(${fristLowerClassName}.get${primaryKeyHumName}())==null) {
				${fristLowerClassName}.set${primaryKeyHumName}(LoserStarIdGenUtil.uuidHex());
				setCreateUser(${fristLowerClassName});
				${fristLowerClassName}.removeNullValueAttrs();
				flag = ${fristLowerClassName}Dao.insert(${fristLowerClassName});
				result.ok("新增成功");
			}else {
				setUpdateUser(${fristLowerClassName});
				${fristLowerClassName}.removeNullValueAttrs();
				flag = ${fristLowerClassName}Dao.update(${fristLowerClassName});
				result.ok("更新成功");
			}
			if (!flag) {
				throw new Exception("操作失败，请联系管理员");
			}
			
			result.setData(${fristLowerClassName});
		} catch (Exception e) {
			e.printStackTrace();
			result.error(e.getMessage());
		}
		renderJson(result);
	}
	
	/**
	 * 根据主键id获取数据
	 */
	public void getById() {
		VResult result = new VResult();
		try {
			String userid = getUserId();
			String ${primaryKey} = getPara("${primaryKey}");
			if (!checkNull(${primaryKey})) {
				throw new Exception("没有传入数据${primaryKey}");
			}
			${className} ${fristLowerClassName} = ${fristLowerClassName}Dao.getById(${primaryKey},${className}.class);
			if (${fristLowerClassName}==null) {
				throw new Exception("没有找到该${primaryKey}的数据");
			}
		 result.ok("获取数据成功");
		 result.setData(${fristLowerClassName});
		} catch (Exception e) {
			e.printStackTrace();
			result.error(e.getMessage());
		}
		renderJson(result);
	}

	/**
	 * 根据id，删除数据
	 */
	public void delById() {
		VResult result = new VResult();
		try {
			String ${primaryKey} = getPara("${primaryKey}");
			if (!checkNull(${primaryKey})) {
				throw new Exception("没有传入${primaryKey}");
			}
			boolean flag = true;
			${className} ${fristLowerClassName} = ${fristLowerClassName}Dao.getById(${primaryKey},${className}.class);
			if (${fristLowerClassName}==null) {
				throw new Exception("没有找到该数据，不能删除"+${primaryKey});
			}
			flag = ${fristLowerClassName}Dao.deleteSoftById(${primaryKey});
			if (!flag) {
				throw new Exception("删除失败");
			}
			result.ok("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.error(e.getMessage());
		}
		renderJson(result);
	}
}
