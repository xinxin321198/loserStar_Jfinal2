package com.kaen.service.user;

import com.kaen.entity.vo.common.UserVo;
import com.loserstar.utils.encodes.LoserStarMd5Utils;

/**
 * 用户账号信息
 */
public class UserService {
	
	private static UserService userService;
	
	private UserService() {
	}

	public static UserService ins() {
		if (userService == null) {
			synchronized (UserService.class) {
				userService = new UserService();
			}
		}
		return userService;
	}
	
	/**
	 * 根据手机号和年度获取注册用户
	 * @param phone 手机号
	 * @param year 年度
	 * @return
	 * @throws Exception 
	 */
	public UserVo getUserVo(String phone,String year) throws Exception {
		UserVo userVo = new UserVo();
		userVo.setFullName("姓名");
		userVo.setNumber("123");
		userVo.setYear("2024");
		userVo.setPwd1("123");
		userVo.setPwdErrCount(0);
		userVo.setPhone("123");
		return userVo;
	}
	
	/**
	 * 通过3个值，加密出密码的密文
	 * @param year 年度
	 * @param phone 手机号
	 * @param pwd 密码明文
	 * @return
	 * @throws Exception 
	 */
	public String getMd5Pwd(String year,String phone,String pwd) throws Exception {
		String md5Pwd = LoserStarMd5Utils.md5(year+"_"+phone+"_"+pwd);
		return md5Pwd;
	}
	
	/**
	 * 更新账号数据
	 * @param userVo
	 * @return
	 */
	public boolean updateUserVo(UserVo userVo) {
		return true;
	}
}
