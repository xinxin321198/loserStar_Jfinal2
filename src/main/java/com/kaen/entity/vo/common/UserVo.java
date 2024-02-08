package com.kaen.entity.vo.common;

import java.io.Serializable;

/**
 * 注册用户的Vo
 */
public class UserVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 用户编码
	 */
	private String number;
	
	/**
	 * 密码
	 */
	private String pwd1;
	
	/**
	 * 密码确认
	 */
	private String pwd2;
	

	/**
	 * 姓名
	 */
	private String fullName;
	
	/**
	 * 身份证号
	 */
	private String idCard;
	
	/**
	 * 出生日期
	 */
	private String birthday;
	
	/**
	 * 性别
	 */
	private String sex;
	
	/**
	 * 学历
	 */
	private String education;
	
	/**
	 * 手机号（作为账号）
	 */
	private String phone;
	
	/**
	 * 验证码
	 */
	private String checkCode;
	
	/**
	 * 年度
	 */
	private String year;
	
	/**
	 * 密码错误次数
	 */
	private int pwdErrCount;
	

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getPwdErrCount() {
		return pwdErrCount;
	}

	public void setPwdErrCount(int pwdErrCount) {
		this.pwdErrCount = pwdErrCount;
	}


	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd1() {
		return pwd1;
	}

	public void setPwd1(String pwd1) {
		this.pwd1 = pwd1;
	}


	public String getPwd2() {
		return pwd2;
	}

	public void setPwd2(String pwd2) {
		this.pwd2 = pwd2;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
}
