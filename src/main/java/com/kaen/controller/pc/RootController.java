package com.kaen.controller.pc;

import com.jfinal.core.Path;

/**
 * 用做访问根目录时候跳转到首页
 */
@Path(value = "/")
public class RootController extends PcBaseController {

	public void index() {
		redirect("/index/indexPage.do");
	}
}
