package com.kaen.sms.service;

import java.rmi.RemoteException;

import com.kaen.service.user.UserService;

/**
 * 云南中烟发送短信
 */
public class YnzyZpSmsService {
	
	private static YnzyZpSmsService ynzyZpSmsService;
	
	private YnzyZpSmsService() {
	}

	public static YnzyZpSmsService ins() {
		if (ynzyZpSmsService == null) {
			synchronized (UserService.class) {
				ynzyZpSmsService = new YnzyZpSmsService();
			}
		}
		return ynzyZpSmsService;
	}
	/**
	 * 发送短信
	 * @param mobile 手机号
	 * @param content 内容
	 * @return
	 * @throws RemoteException
	 */
	public boolean sendSms( String mobile, String content)  {
		System.out.println("正在发送"+mobile+"的短信------------------------------内容："+content);
		System.out.println("需实现接口com.kaen.sms.service.YnzyZpSmsService.sendSms(String, String)：");
		return true;
	}
}
