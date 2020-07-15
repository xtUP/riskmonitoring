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
@RequestMapping("/echarts/node_line2_echarts")
public class NodeLinesController{
	private String prefix = "echarts/dataecharts";
	//@RequiresPermissions("echarts:registEcharts:view")
	@GetMapping()
	public String regist_echarts(HttpServletResponse response,
			HttpServletRequest request)
	{
		//前端时间选择
		String selectTime=(String)request.getParameter("selectTime");
		request.setAttribute("selectTime",selectTime);
		return prefix + "/NodeLine-2";
	}
}

