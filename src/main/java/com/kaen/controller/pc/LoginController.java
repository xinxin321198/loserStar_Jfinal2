/**
 * author: loserStar
 * date: 2019年8月23日下午3:44:12
 * email:362527240@qq.com
 * github:https://github.com/xinxin321198
 * remarks:
 */
package com.kaen.controller.pc;

import com.jfinal.core.Path;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.PropKit;
import com.kaen.constants.DsConstans;
import com.kaen.constants.SessionKeyConstants;
import com.kaen.dao.SysUserDao;
import com.kaen.entity.SysUser;
import com.kaen.entity.vo.common.UserVo;
import com.kaen.sms.service.YnzyZpSmsService;
import com.loserstar.utils.checkCode.LoserStarCheckCodeUtils;
import com.loserstar.utils.date.LoserStarDateUtils;
import com.loserstar.utils.db.jfinal.vo.VResult;
import com.loserstar.utils.json.LoserStarJsonUtil;

import javax.imageio.ImageIO;
import java.util.Date;

/**
 * author: loserStar
 * date: 2019年8月23日下午3:44:12
 * remarks:登录（这个不需要登录，登录页面）
 */
@Path(value="/login")
public class LoginController extends PcBaseController {
	/**
	 * 密码输出次数过多后锁定时间
	 */
	public static final long login_banTime = PropKit.getLong("login.banTime");
	/**
	 * 最大允许密码输入错误次数
	 */
	public static final int login_pwdErrorMaxCount= PropKit.getInt("login.pwdErrorMaxCount");

	private YnzyZpSmsService ynzyZpSmsService = YnzyZpSmsService.ins();
	private SysUserDao sysUserDao = new SysUserDao(DsConstans.dataSourceName.myql);
	/**
	 * 登录页面
	 */
	public void loginPage() {
		try {
			//每次进入登录页面都刷新验证码
			refreshCheckCode();
			renderFreeMarker("/login.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 刷新验证码
	 * @throws Exception 
	 */
	private void refreshCheckCode() throws Exception {
		String code = LoserStarCheckCodeUtils.genCheckCode(4);
		getLoserStarSession().setAttr("loginCheckCode", code);
		System.out.println("sessionId："+getLoserStarSession().getSessionId());
		System.out.println("seesion中的验证码："+getLoserStarSession().getAttr("loginCheckCode"));
	}
	
	/**
	 * 得到验证码图片
	 */
	public void getLoginCheckCodeImg() {
		try {
			ImageIO.write(LoserStarCheckCodeUtils.getImage(getLoserStarSession().getAttr("loginCheckCode").toString()), "png", getResponse().getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderNull();
	}

	/**
	 * 登录
	 */
	public void login() {
		VResult result = new VResult();
		String pwdErrStr = "用户名或密码错误，如忘记密码请点击“找回密码”进行密码重置！";
		try {
			String phone = getPara("phone");
			if (!checkNull(phone)) {
				throw new Exception("请填写手机号");
			}
			String pwd = getPara("pwd");
			if (!checkNull(pwd)) {
				throw new Exception("请填写密码");
			}
			String checkCode = getPara("checkCode");
			if (!checkNull(checkCode)) {
				throw new Exception("请填写验证码");
			}
			String sessionCheckCode = getLoserStarSession().getAttr(SessionKeyConstants.loginCheckCode);
			if (!checkNull(sessionCheckCode)) {
				throw new Exception("尚未生成验证码");
			}
			if (!checkCode.toUpperCase().equals(sessionCheckCode.toUpperCase())) {
				throw new Exception("验证码错误,请重新输入");
			}

			SysUser sysUser = sysUserDao.getById(phone, SysUser.class);
			if (sysUser==null) {
				throw new Exception("用户名或密码错误");
			}
			if(sysUser.getPwdErrCount()>=login_pwdErrorMaxCount) {
				throw new Exception("对不起，密码输入次数过多，该账号已被锁定"+(login_banTime/1000/60)+"分钟，可请通过找回密码立即解锁！");
			}
			String dbPwd = sysUser.getPassword();// DB查出来的用户密码
			int pwdErrCount = sysUser.getPwdErrCount();//DB查出来的用户密码错误次数

//			if (!userService.getMd5Pwd("loserStar", pwd).equals(dbPwd)) {
			if (!(pwd).equals(dbPwd)) {
				pwdErrCount++;//密码错误，错误次数+1，并保存在数据库中
				sysUser.setPwdErrCount(pwdErrCount);
				sysUser.setLockBeginDate(new Date());
				sysUserDao.update(sysUser);
				if (pwdErrCount>=login_pwdErrorMaxCount) {
					if ((System.currentTimeMillis()-sysUser.getLockBeginDate().getTime())>0){
						throw new Exception("对不起，密码输入次数过多，该账号已被锁定"+(login_banTime/1000/60)+"分钟，账号将在"+LoserStarDateUtils.format(new Date(sysUser.getLockBeginDate().getTime()+login_banTime))+"后解锁，可请通过找回密码立即解锁！");
					}
				}else {
					throw new Exception("密码错误！还剩"+(login_pwdErrorMaxCount-pwdErrCount)+"次机会");
				}
			}
			pwdErrCount=0;//如果登录成功，错误次数清零，并更新数据库
			sysUser.setPwdErrCount(pwdErrCount);
			sysUserDao.update(sysUser);
			//-----------redis seesion-begin
			setLogin(sysUser);
			//-----------redis seesion-end
			result.ok("登录成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.error(e.getMessage());
			// 登录失败时，重新刷新验证码
			String code = LoserStarCheckCodeUtils.genCheckCode(4);
			try {
				getLoserStarSession().setAttr(SessionKeyConstants.loginCheckCode, code);
			} catch (Exception e1) {
				e1.printStackTrace();
				result.error(e1.getMessage());
			}
		}
		renderJson(result);
	}
	
	/**
	 * 注销
	 */
	public void loginOut() {
		try {
//			removeSessionAttr("userid");
//			getSession().invalidate();
//			String userid = getSessionAttr("userid");
//			if (userid==null||userid.equals("")) {
//				redirect("/login/loginPage.do");//重定向到登录页
//			}else {
//				renderText("seesion清除失败");
//			}
			delLogin();
			String userid = getUserId();
			if (userid!=null&&!userid.equals("")) {
				throw new Exception("seesion清除失败");
			}
			redirect("/index/index.do");//重定向到登录页
		} catch (Exception e) {
			renderError(e.getMessage());
		}
	}
	
	/**
	 * 注册页面
	 */
	public void registerPage() {
		try {
			renderFreeMarker("/register.html");
		} catch (Exception e) {
			e.printStackTrace();
			renderError(e.getMessage());
		}
	}
	
	/**
	 * 发送注册验证码
	 */
	public void sendRegisterPwdCheckCode() {
		VResult result = new VResult();
		try {
			String phone = getPara("phone");
			if (phone==null||phone.equals("")) {
				throw new Exception("请输入手机号");
			}
			SysUser sysUser = sysUserDao.getById(phone, SysUser.class);
			if (sysUser!=null) {
				throw new Exception("对不起，该手机号已注册过用户，请更换手机号或者通过“忘记密码”找回您的密码！");
			}
			String code = LoserStarCheckCodeUtils.genCheckCode(4);
			boolean flag =  ynzyZpSmsService.sendSms(phone,"您好，您正在注册云南中烟招聘系统账号，短信验证码为："+code);
			if (!flag) {
				throw new Exception("短信发送失败，请联系管理员检查！");
			}
			getLoserStarSession().setAttr("registerCheckCode", code);
			result.ok("发送成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.error(e.getMessage());
		}
		renderJson(result);
	}

	/**
	 * 注册
	 */
	public void register() {
		VResult result = new VResult();
		try {
			String json = HttpKit.readData(getRequest());
			UserVo userVo = LoserStarJsonUtil.toModel(json, UserVo.class);
			System.out.println(LoserStarJsonUtil.toJson(userVo));
			if (!checkNull(userVo.getPwd1())) {
				throw new Exception("未填写密码");
			}
			if (!checkNull(userVo.getPwd2())) {
				throw new Exception("未填写确认密码");
			}
			if (!checkNull(userVo.getFullName())) {
				throw new Exception("未填写姓名");
			}
			if (!checkNull(userVo.getIdCard())) {
				throw new Exception("未填写身份证号");
			}
			if (!checkNull(userVo.getBirthday())) {
				throw new Exception("未填写出生日期");
			}
			if (!checkNull(userVo.getSex())) {
				throw new Exception("未填写性别");
			}
			if (!checkNull(userVo.getPwd1())) {
				throw new Exception("未填写密码");
			}
			if (!checkNull(userVo.getEducation())) {
				throw new Exception("未填写学历");
			}
			if (!checkNull(userVo.getPhone())) {
				throw new Exception("未填写手机号");
			}
			if (!checkNull(userVo.getCheckCode())) {
				throw new Exception("未填写验证码");
			}
			if (!userVo.getPwd1().equals(userVo.getPwd2())) {
				throw new Exception("两次密码填写不一致");
			}
			if (getLoserStarSession().getAttr("registerCheckCode")==null) {
				throw new Exception("未找到验证码");
			}
			if (!userVo.getCheckCode().equals(getLoserStarSession().getAttr("registerCheckCode"))) {
				throw new Exception("验证码错误");
			}
			
			SysUser userVoOld = sysUserDao.getById(userVo.getPhone(), SysUser.class);
			if (userVoOld!=null) {
				throw new Exception("对不起，该手机号已注册过用户，请更换手机号或者通过“忘记密码”找回您的密码！");
			}
			String year = String.valueOf(LoserStarDateUtils.getYear(new Date()));
			SysUser saveUserVo = new SysUser();
			saveUserVo.setId(userVo.getPhone());
			saveUserVo.setUserName(userVo.getFullName());
			saveUserVo.setPassword(userVo.getPwd1());
			saveUserVo.setPwdErrCount(0);
			System.out.println(LoserStarJsonUtil.toJson(saveUserVo));
			boolean flag =  true;
			if (!flag) {
				throw new Exception("注册失败");
			}
			result.ok("注册成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.error(e.getMessage());
		}
		renderJson(result);
	}
}
