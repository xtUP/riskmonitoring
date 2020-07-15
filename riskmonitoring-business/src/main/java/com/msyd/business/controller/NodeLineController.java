package com.msyd.business.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.msyd.business.util.kudu.ImpalaUtil;

/**
 * 报表
 * 时间：2020-03-12
 * @author xt
 */
@Controller
@RequestMapping("/echarts/node_line1_echarts")
public class NodeLineController{
	private String prefix = "echarts/dataecharts";
	/**
	 * 节点展示
	 */
	//@RequiresPermissions("echarts:registEcharts:view")
	@GetMapping()
	public String regist_echarts(HttpServletResponse response,
			HttpServletRequest request)
	{
		//前端时间选择
		String selectTime=(String)request.getParameter("selectTime");
		request.setAttribute("selectTime",selectTime);
		return prefix + "/NodeLine-1";
	}
	@RequestMapping(value = "node_line_charts")
	@ResponseBody
	public String registToAjax(HttpServletResponse response,HttpServletRequest request){
		ImpalaUtil IU=new ImpalaUtil();
		String sql="select * from ("+
				"SELECT nv.monitor_node,nv.monitor_node_event "+
				",SUM(if(nv.data_dt=t.m0,nv.request_person_10m,0)) request_person_10m,SUM(if(nv.data_dt=t.m0,nv.pass_person_10m,0)) pass_person_10m,SUM(if(nv.data_dt=t.m0,nv.reject_person_10m,0)) reject_person_10m "+
				",SUM(if(nv.data_dt=t.m1,nv.request_person_10m,0)) request_person_20m,SUM(if(nv.data_dt=t.m1,nv.pass_person_10m,0)) pass_person_20m,SUM(if(nv.data_dt=t.m1,nv.reject_person_10m,0)) reject_person_20m "+
				",SUM(if(nv.data_dt=t.m2,nv.request_person_10m,0)) request_person_30m,SUM(if(nv.data_dt=t.m2,nv.pass_person_10m,0)) pass_person_30m,SUM(if(nv.data_dt=t.m2,nv.reject_person_10m,0)) reject_person_30m "+
				",SUM(if(nv.data_dt=t.m3,nv.request_person_10m,0)) request_person_40m,SUM(if(nv.data_dt=t.m3,nv.pass_person_10m,0)) pass_person_40m,SUM(if(nv.data_dt=t.m3,nv.reject_person_10m,0)) reject_person_40m "+
				",SUM(if(nv.data_dt=t.m4,nv.request_person_10m,0)) request_person_50m,SUM(if(nv.data_dt=t.m4,nv.pass_person_10m,0)) pass_person_50m,SUM(if(nv.data_dt=t.m4,nv.reject_person_10m,0)) reject_person_50m "+
				",SUM(if(nv.data_dt=t.m5,nv.request_person_10m,0)) request_person_60m,SUM(if(nv.data_dt=t.m5,nv.pass_person_10m,0)) pass_person_60m,SUM(if(nv.data_dt=t.m5,nv.reject_person_10m,0)) reject_person_60m "+
				",SUM(if(nv.data_dt=t.y0,nv.request_person_10m,0)) zt_request_person_10m,SUM(if(nv.data_dt=t.y0,nv.pass_person_10m,0)) zt_pass_person_10m,SUM(if(nv.data_dt=t.y0,nv.reject_person_10m,0)) zt_reject_person_10m "+
				",SUM(if(nv.data_dt=t.y1,nv.request_person_10m,0)) zt_request_person_20m,SUM(if(nv.data_dt=t.y1,nv.pass_person_10m,0)) zt_pass_person_20m,SUM(if(nv.data_dt=t.y1,nv.reject_person_10m,0)) zt_reject_person_20m "+
				",SUM(if(nv.data_dt=t.y2,nv.request_person_10m,0)) zt_request_person_30m,SUM(if(nv.data_dt=t.y2,nv.pass_person_10m,0)) zt_pass_person_30m,SUM(if(nv.data_dt=t.y2,nv.reject_person_10m,0)) zt_reject_person_30m "+
				",SUM(if(nv.data_dt=t.y3,nv.request_person_10m,0)) zt_request_person_40m,SUM(if(nv.data_dt=t.y3,nv.pass_person_10m,0)) zt_pass_person_40m,SUM(if(nv.data_dt=t.y3,nv.reject_person_10m,0)) zt_reject_person_40m "+
				",SUM(if(nv.data_dt=t.y4,nv.request_person_10m,0)) zt_request_person_50m,SUM(if(nv.data_dt=t.y4,nv.pass_person_10m,0)) zt_pass_person_50m,SUM(if(nv.data_dt=t.y4,nv.reject_person_10m,0)) zt_reject_person_50m "+
				",SUM(if(nv.data_dt=t.y5,nv.request_person_10m,0)) zt_request_person_60m,SUM(if(nv.data_dt=t.y5,nv.pass_person_10m,0)) zt_pass_person_60m,SUM(if(nv.data_dt=t.y5,nv.reject_person_10m,0)) zt_reject_person_60m "+
				"from  "+
				"("+
				"SELECT * ,days_sub(y.m0, 1) y0,days_sub(y.m1, 1) y1,days_sub(y.m2, 1) y2,days_sub(y.m3, 1) y3,days_sub(y.m4, 1) y4,days_sub(y.m5, 1) y5 from"+
				"(SELECT data_dt m0,monitor_node ,monitor_node_event "+
				",minutes_sub(data_dt,10) m1 ,minutes_sub(data_dt,20) m2 ,minutes_sub(data_dt,30) m3 "+
				",minutes_sub(data_dt,40) m4,minutes_sub(data_dt,50) m5 "+
				"from dm.dm_msyd_monitor_common_rtnodevalue where isnew=1 "+
				")y) t join dm.dm_msyd_monitor_common_rtnodevalue nv on nv.monitor_node=t.monitor_node and nv.monitor_node_event=t.monitor_node_event "+
				"and nv.data_dt in (t.m0,t.m1,t.m2,t.m3,t.m4,t.m5,t.y0,t.y1,t.y2,t.y3,t.y4,t.y5)  "+
				"GROUP BY nv.monitor_node,nv.monitor_node_event "+
				" ) cc join (SELECT * ,days_sub(ys.m0, 1) y0,days_sub(ys.m1, 1) y1,days_sub(ys.m2, 1) y2,days_sub(ys.m3, 1) y3,days_sub(ys.m4, 1) y4,days_sub(ys.m5, 1) y5 from"+
				"(SELECT monitor_node,monitor_node_event,data_dt m0 "+
				",minutes_sub(data_dt,10) m1 ,minutes_sub(data_dt,20) m2 ,minutes_sub(data_dt,30) m3 "+
				",minutes_sub(data_dt,40) m4,minutes_sub(data_dt,50) m5 "+
				"from dm.dm_msyd_monitor_common_rtnodevalue where isnew=1 "+
				")ys) xx on cc.monitor_node=xx.monitor_node and cc.monitor_node_event=xx.monitor_node_event";
		List<Object[]> result=IU.query(sql);
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		for (Object[] o : result){
			Map<String,String> map = new LinkedHashMap<String,String>();
			//节点和数据
			map.put("monitor_node",o[0].toString());
			map.put("monitor_node_event",o[1].toString());
			map.put("request_person_10m",o[2].toString());
			map.put("pass_person_10m",o[3].toString());
			map.put("reject_person_10m",o[4].toString());
			map.put("request_person_20m",o[5].toString());
			map.put("pass_person_20m",o[6].toString());
			map.put("reject_person_20m",o[7].toString());
			map.put("request_person_30m",o[8].toString());
			map.put("pass_person_30m",o[9].toString());
			map.put("reject_person_30m",o[10].toString());
			map.put("request_person_40m",o[11].toString());
			map.put("pass_person_40m",o[12].toString());
			map.put("reject_person_40m",o[13].toString());
			map.put("request_person_50m",o[14].toString());
			map.put("pass_person_50m",o[15].toString());
			map.put("reject_person_50m",o[16].toString());
			map.put("request_person_60m",o[17].toString());
			map.put("pass_person_60m",o[18].toString());
			map.put("reject_person_60m",o[19].toString());
			
			map.put("zt_request_person_10m",o[20].toString());
			map.put("zt_pass_person_10m",o[21].toString());
			map.put("zt_reject_person_10m",o[22].toString());
			map.put("zt_request_person_20m",o[23].toString());
			map.put("zt_pass_person_20m",o[24].toString());
			map.put("zt_reject_person_20m",o[25].toString());
			map.put("zt_request_person_30m",o[26].toString());
			map.put("zt_pass_person_30m",o[27].toString());
			map.put("zt_reject_person_30m",o[28].toString());
			map.put("zt_request_person_40m",o[29].toString());
			map.put("zt_pass_person_40m",o[30].toString());
			map.put("zt_reject_person_40m",o[31].toString());
			map.put("zt_request_person_50m",o[32].toString());
			map.put("zt_pass_person_50m",o[33].toString());
			map.put("zt_reject_person_50m",o[34].toString());
			map.put("zt_request_person_60m",o[35].toString());
			map.put("zt_pass_person_60m",o[36].toString());
			map.put("zt_reject_person_60m",o[37].toString());
			
			//时间
			map.put("Time10m",simpleDate(o[40].toString()));
			map.put("Time20m",simpleDate(o[41].toString()));
			map.put("Time30m",simpleDate(o[42].toString()));
			map.put("Time40m",simpleDate(o[43].toString()));
			map.put("Time50m",simpleDate(o[44].toString()));
			map.put("Time60m",simpleDate(o[45].toString()));
			//拒绝率
			map.put("pass_rate_10m",createRate(map.get("pass_person_10m"),map.get("request_person_10m")));
			map.put("pass_rate_20m",createRate(map.get("pass_person_20m"),map.get("request_person_20m")));
			map.put("pass_rate_30m",createRate(map.get("pass_person_30m"),map.get("request_person_30m")));
			map.put("pass_rate_40m",createRate(map.get("pass_person_40m"),map.get("request_person_40m")));
			map.put("pass_rate_50m",createRate(map.get("pass_person_50m"),map.get("request_person_50m")));
			map.put("pass_rate_60m",createRate(map.get("pass_person_60m"),map.get("request_person_60m")));
			list.add(map);
		}
		//System.out.println(list);
		String str=JSON.toJSONString(list);
		return str;
	}
	public String simpleDate(String time){
		String dateString=time.substring(11,16);
		return dateString;
	}
	//拒绝率计算
	public String createRate(String son,String father){
		Double molecule=Double.parseDouble(son);
		Double denominator=Double.parseDouble(father);
		Double result=0.00;
		if(denominator<=0){
			denominator=1.00;
		}
		result=molecule*100/denominator;
		String resultStr=String.format("%.2f", result);
		return resultStr;
	}
}

