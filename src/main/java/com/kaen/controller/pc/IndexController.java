package com.kaen.controller.pc;

import com.jfinal.core.Path;

/**
 * 
 * author: loserStar
 * date: 2023年12月19日上午9:57:22
 * remarks:公司简介
 */
@Path(value="/index")
public class IndexController extends PcBaseController {

	/**
	 * 路径不全时候跳转到首页
	 */
	public void index() {
		redirect("/index/indexPage.do");
	}
	
	/**
	 * 渲染首页
	 */
	public void indexPage(){
		renderFreeMarker("/index.html");
	}
}
