package com.kaen.controller.pc;

import com.jfinal.core.Path;
import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.activerecord.Page;
import com.kaen.constants.DsConstans;
import com.kaen.dao.SysDictDao;
import com.kaen.entity.SysDict;
import com.loserstar.utils.db.jfinal.base.imp.WhereHelper;
import com.loserstar.utils.db.jfinal.vo.VResult;
import com.loserstar.utils.idgen.LoserStarIdGenUtil;
import com.loserstar.utils.json.LoserStarJsonUtil;

import java.util.ArrayList;
import java.util.List;
/**
 * 字典表
 * @author loserStar
 *
 */
@Path(value = "/sysDict")
public class SysDictController extends PcBaseController {
	
	private SysDictDao sysDictDao = new SysDictDao(DsConstans.dataSourceName.myql);

	/**
	 * 列表页面
	 */
	public void listPage() {
		try {
			String userid = getUserId();
			renderFreeMarker("/view/sysDict/sysDictList.html");
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
            String dict_id = getPara("dict_id");
            String dict_value = getPara("dict_value");
            String dict_name = getPara("dict_name");
            String dict_type = getPara("dict_type");
            String dict_remarks = getPara("dict_remarks");
            String dict_c_name = getPara("dict_c_name");
            String dict_css_style = getPara("dict_css_style");
            String dict_sort = getPara("dict_sort");
            String del = getPara("del");
            String create_time = getPara("create_time");
            String create_user_code = getPara("create_user_code");
            String create_user_name = getPara("create_user_name");
            String update_time = getPara("update_time");
            String update_user_code = getPara("update_user_code");
            String update_user_name = getPara("update_user_name");
			//排序字段
			//String sort_filed = getPara("sort_filed");
			//String sort_type = getPara("sort_type");
			
			WhereHelper whereHelper = new WhereHelper();
			List<Object> queryParamList = new ArrayList<Object>();
            if (checkNull(dict_id)) {
				whereHelper.addStrWhere("and dict_id like ?");
				queryParamList.add("%"+dict_id+"%");
			}
            if (checkNull(dict_value)) {
				whereHelper.addStrWhere("and dict_value like ?");
				queryParamList.add("%"+dict_value+"%");
			}
            if (checkNull(dict_name)) {
				whereHelper.addStrWhere("and dict_name like ?");
				queryParamList.add("%"+dict_name+"%");
			}
            if (checkNull(dict_type)) {
				whereHelper.addStrWhere("and dict_type like ?");
				queryParamList.add("%"+dict_type+"%");
			}
            if (checkNull(dict_remarks)) {
				whereHelper.addStrWhere("and dict_remarks like ?");
				queryParamList.add("%"+dict_remarks+"%");
			}
            if (checkNull(dict_c_name)) {
				whereHelper.addStrWhere("and dict_c_name like ?");
				queryParamList.add("%"+dict_c_name+"%");
			}
            if (checkNull(dict_css_style)) {
				whereHelper.addStrWhere("and dict_css_style like ?");
				queryParamList.add("%"+dict_css_style+"%");
			}
            if (checkNull(dict_sort)) {
				whereHelper.addStrWhere("and dict_sort like ?");
				queryParamList.add("%"+dict_sort+"%");
			}
            if (checkNull(del)) {
				whereHelper.addStrWhere("and del like ?");
				queryParamList.add("%"+del+"%");
			}
            if (checkNull(create_time)) {
				whereHelper.addStrWhere("and create_time like ?");
				queryParamList.add("%"+create_time+"%");
			}
            if (checkNull(create_user_code)) {
				whereHelper.addStrWhere("and create_user_code like ?");
				queryParamList.add("%"+create_user_code+"%");
			}
            if (checkNull(create_user_name)) {
				whereHelper.addStrWhere("and create_user_name like ?");
				queryParamList.add("%"+create_user_name+"%");
			}
            if (checkNull(update_time)) {
				whereHelper.addStrWhere("and update_time like ?");
				queryParamList.add("%"+update_time+"%");
			}
            if (checkNull(update_user_code)) {
				whereHelper.addStrWhere("and update_user_code like ?");
				queryParamList.add("%"+update_user_code+"%");
			}
            if (checkNull(update_user_name)) {
				whereHelper.addStrWhere("and update_user_name like ?");
				queryParamList.add("%"+update_user_name+"%");
			}
			//排序
			//if (checkNull(sort_filed)) {
				//whereHelper.addStrOrder("order by "+getSortField(sort_filed)+" "+sort_type);
				//whereHelper.addStrOrder("order by sort asc");
			//}
			Page<SysDict> dataPage =  sysDictDao.getListPage(getPageNumber(), getPageSize(), whereHelper, SysDict.class, queryParamList.toArray());
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
			String dict_id = getPara("dict_id");
			if (checkNull(dict_id)) {
				SysDict sysDict = sysDictDao.getById(dict_id, SysDict.class);
			}
			String op_type = getPara("op_type");
			setAttr("dict_id", dict_id);
			setAttr("op_type", op_type);
			setAttr("currentTimeMillis", System.currentTimeMillis());
			renderFreeMarker("/view/sysDict/sysDictForm.html");
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
			String dict_id = getPara("dict_id");
			if (checkNull(dict_id)) {
				SysDict sysDict = sysDictDao.getById(dict_id, SysDict.class);
			}
			String op_type = getPara("op_type");
			setAttr("dict_id", dict_id);
			setAttr("op_type", "view");
			setAttr("currentTimeMillis", System.currentTimeMillis());
			renderFreeMarker("/view/sysDict/sysDictForm.html");
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
			SysDict sysDict = LoserStarJsonUtil.toModel(json, SysDict.class);
			boolean flag = false;
			if (sysDictDao.getById(sysDict.getDictId())==null) {
				sysDict.setDictId(LoserStarIdGenUtil.uuidHex());
				setCreateUser(sysDict);
				sysDict.removeNullValueAttrs();
				flag = sysDictDao.insert(sysDict);
				result.ok("新增成功");
			}else {
				setUpdateUser(sysDict);
				sysDict.removeNullValueAttrs();
				flag = sysDictDao.update(sysDict);
				result.ok("更新成功");
			}
			if (!flag) {
				throw new Exception("操作失败，请联系管理员");
			}
			
			result.setData(sysDict);
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
			String dict_id = getPara("dict_id");
			if (!checkNull(dict_id)) {
				throw new Exception("没有传入数据dict_id");
			}
			SysDict sysDict = sysDictDao.getById(dict_id,SysDict.class);
			if (sysDict==null) {
				throw new Exception("没有找到该dict_id的数据");
			}
		 result.ok("获取数据成功");
		 result.setData(sysDict);
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
			String dict_id = getPara("dict_id");
			if (!checkNull(dict_id)) {
				throw new Exception("没有传入dict_id");
			}
			boolean flag = true;
			SysDict sysDict = sysDictDao.getById(dict_id,SysDict.class);
			if (sysDict==null) {
				throw new Exception("没有找到该数据，不能删除"+dict_id);
			}
			flag = sysDictDao.deleteSoftById(dict_id);
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
