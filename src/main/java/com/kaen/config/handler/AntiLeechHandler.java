package com.kaen.config.handler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;

public class AntiLeechHandler extends Handler {
	private static boolean IS_ANTI_LEECH = PropKit.getBoolean("isAntiLeech");//是否开启防盗链
	private static List<String> DOMAINS = Arrays.asList(PropKit.get("domains").split(";"));//防盗链的主机名，不允许其它页面跳转过来，用逗号分隔
	
	@Override
	public void handle(String var1, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		String referer = request.getHeader("Referer");  
        if (IS_ANTI_LEECH) {
            // 判断 referer 是否为空或是否来自你的网站  
            if (StrKit.isBlank(referer) || !DOMAINS.contains(referer)) {  
//            	try {
//					response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Forbidden");
					// 标记该请求已被处理  
		            isHandled[0] = true;
		            return;
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
            }else {
            	next.handle(var1, request, response, isHandled);
			}
		}else {
			next.handle(var1, request, response, isHandled);
		}
        
	}

}
