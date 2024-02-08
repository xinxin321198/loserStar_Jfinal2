package com.kaen.controller.pc;

import com.kaen.config.annotation.Controller;

/**
 * 用做访问根目录时候跳转到首页
 */
@Controller(controllerKey = "/")
public class RootController extends PcBaseController {

	public void index() {
		redirect("/index/indexPage.do");
	}
}
