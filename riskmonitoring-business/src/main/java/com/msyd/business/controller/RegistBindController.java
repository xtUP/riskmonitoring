package com.msyd.business.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 报表
 * 
 * @author xt
 */
@Controller
@RequestMapping("/echarts/regist_bind")
public class RegistBindController{
	private String prefix = "echarts/dataecharts";
	/**
	 * 节点展示
	 */
	//@RequiresPermissions("echarts:node:view")
	@GetMapping()
	public String dateCenter_echarts(HttpServletResponse response,
			HttpServletRequest request)
	{
		//前端时间选择
		String selectValue=(String)request.getParameter("selectValue");
		request.setAttribute("selectValue",selectValue);
		return prefix + "/RegistBind";
	}
}

