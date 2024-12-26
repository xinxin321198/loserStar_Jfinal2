package com.kaen.constants;

/**
 * session的key相关的常量
 */
public class SessionKeyConstants {

	/**
	 * 当前登录用户
	 */
	public static final String userVo = "userVo";
	/**
	 * 登录验证码
	 */
	public static final String loginCheckCode = "loginCheckCode";
	/**
	 * 注册短信验证码
	 */
	public static final String registerCheckCode = "registerCheckCode";
	/**
	 * 注册的短信验证码的发送时间
	 */
	public static final String registerCheckCodeSendTime = "registerCheckCodeSendTime";
	/**
	 * 注册时候验证码对应的手机号
	 */
	public static final String registerPhone = "registerPhone";
	
	/**
	 * 修改密码验证码
	 */
	public static final String modifyPwdCheckCode = "modifyPwdCheckCode";
	/**
	 * 修改密码验证码发送时间
	 */
	public static final String modifyPwdCheckCodeSendTime = "modifyPwdCheckCodeSendTime";
	
	/**
	 * 发送短信时候的图形验证码
	 */
	public static final String sendSmsCheckCode = "sendSmsCheckCode";
	
	/**
	 * 修改密码时候验证码对应的手机号
	 */
	public static final String modifyPhone = "modifyPhone";
	/**
	 * 修改密码时候的用户id
	 */
	public static final String modifyUserId = "modifyUserId";
}
