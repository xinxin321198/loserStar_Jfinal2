package com.kaen.config.handler;

import com.jfinal.handler.Handler;
import com.jfinal.kit.HandlerKit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 浏览器兼容拦截器
 */
public class UAHandler extends Handler {

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        boolean flag = false;
        if (!target.endsWith(".do") || target.equals("/login/index.do")) {
            flag = true;
        } else {
            String userAgent = request.getHeader("User-Agent");
            if (userAgent != null) {
                // 判断是否为Edge浏览器
                if (userAgent.contains("Edg")) {
                    flag = true;
                }
                // 判断是否为Chrome浏览器
                else if (userAgent.contains("Chrome")) {
                    flag = true;
                }
            }
        }
        if (!flag) {
            HandlerKit.redirect(request.getContextPath() + "/login/index.do", request, response, isHandled);
            return;
        }
        next.handle(target, request, response, isHandled);
    }
}
