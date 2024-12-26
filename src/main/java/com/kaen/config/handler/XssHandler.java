package com.kaen.config.handler;

import com.jfinal.handler.Handler;
import com.kaen.config.wrapper.XssRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName XssHandler
 * @Description 防止XSS攻击
 * @Author 李兴
 * @Date 2024/3/5 15:27
 */
public class XssHandler extends Handler {

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        // 对于非静态文件，和非指定排除的url实现过滤
//        if (!target.contains(".") && !target.contains("manager")) {
        request = new XssRequestWrapper(request);
//        }
        next.handle(target, request, response, isHandled);
    }

}
