package com.kaen.config.handler;

import com.jfinal.handler.Handler;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * 防盗链
 */
public class AntiLeechHandler extends Handler {
	private static boolean IS_ANTI_LEECH = PropKit.getBoolean("isAntiLeech");//是否开启防盗链
	private static List<String> DOMAINS = Arrays.asList(PropKit.get("domains").split(";"));//防盗链的主机名，不允许其它页面跳转过来，用逗号分隔
	
	@Override
	public void handle(String var1, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        if (IS_ANTI_LEECH) {
        	String referer = request.getHeader("Referer");  
            // 判断 referer 是否为空或是否来自你的网站  
            try {
				if (StrKit.isBlank(referer) || !DOMAINS.contains(new URL(referer).getHost())) {
					System.out.println("检测到盗链："+referer);
						// 标记该请求已被处理  
				        isHandled[0] = true;
				        response.setStatus(403);
				        return;
				}else {
					next.handle(var1, request, response, isHandled);
				}
			} catch (MalformedURLException e) {
				System.out.println("防盗链检测referer异常："+referer);
				e.printStackTrace();
				isHandled[0] = true;
		        return;
			}
		}else {
			next.handle(var1, request, response, isHandled);
		}
	}
}
