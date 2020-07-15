package com.msyd.business.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.msyd.business.domain.student;
import com.msyd.business.service.studentService;
import com.msyd.business.util.kudu.ImpalaUtil;

/**
 * 报表
 * 
 * @author xt
 */
@Controller
@RequestMapping("/echarts/loan_release")
public class LoanReleaseController{
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
		return prefix + "/LoanRelease";
	}
}

