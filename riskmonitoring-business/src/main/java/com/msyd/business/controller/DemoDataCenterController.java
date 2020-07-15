package com.msyd.business.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.msyd.business.util.kudu.ImpalaUtil;

/**
 * 报表
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/echarts/dataecharts")
public class DemoDataCenterController{
	private String prefix = "echarts/dataecharts";

	/**
	 * 大数据ECharts
	 */
	//@RequiresPermissions("echarts:dataecharts:view")
	@GetMapping()
	public String dateCenter_echarts(HttpServletResponse response,
			HttpServletRequest request)
	{
		//前端时间选择
		String selectValue=(String)request.getParameter("selectValue");
		request.setAttribute("selectValue",selectValue);
		return prefix + "/dataecharts";
	}
	@RequestMapping(value = "wd_echarts")
	@ResponseBody
	public String wdToAjax(HttpServletResponse response,HttpServletRequest request){
		ImpalaUtil IU=new ImpalaUtil();
		//前端时间选择
		String selectValue=(String) request.getParameter("selectValue");
		//根据时间判断同比函数
		String tb_function="minutes_sub";
		//根据时间填充sql查询字段
		String selectTime="10";
		if(Objects.equals(selectValue, null)||Objects.equals(selectValue, "")){
			tb_function="minutes_sub";
			selectValue="10m";
			selectTime="10";
		}else if(selectValue.equals("30")){
			tb_function="minutes_sub";
			selectValue="30m";
			selectTime="30";
		}else if(selectValue.equals("60")){
			tb_function="minutes_sub";
			selectValue="1h";
			selectTime="60";
		}else if(selectValue.equals("一天")){
			tb_function="days_sub";
			selectValue="day";
			selectTime="1";
		}else{
			tb_function="minutes_sub";
			selectValue="10m";
			selectTime="10";
			}
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
//		String numsql1="select a.latitude_type,a.monitor_node_event,a.latitude_name, "
//				+ "a.latitude_value_person_"+selectValue
//				+" from dm.dm_msyd_monitor_common_rtnodelatitude a where a.isnew=1";
//		String numsql2="select ar.monitor_node_event as xy_monitor_node_event,ar.latitude_type as xy_latitude_type, "
//				+ "ar.monitor_node as xy_monitor_node, "
//				+ "ar.latitude_name as xy_latitude_name, "
//				+ "ar.latitude_value_person_"+selectValue+" as xy_latitude_value_person_"+selectValue+", "
//				+ "bt.monitor_node_event as xz_monitor_node_event, " 
//				+ "bt.latitude_type as xz_latitude_type, "
//				+ "bt.monitor_node as xz_monitor_node, " 
//				+ "bt.latitude_name as xz_latitude_name, "
//				+ "bt.latitude_value_person_"+selectValue+" as xz_latitude_value_person_"+selectValue
//				+ " from "
//				+ "(select a.* from dm.dm_msyd_monitor_common_rtnodelatitude a where a.isnew=1) ar  "
//				+ "full join  "
//				+ "(select bq.* from dm.dm_msyd_monitor_common_rtnodelatitude bq where bq.data_dt in( "
//				+ "select "
//				+tb_function+"(bz.data_dt,"+selectTime+") as times "
//				+ "from ( "
//				+ "select * from (select rank() over(partition by a.latitude_name order by a.data_dt desc ) ra,  "
//				+ "a.* from dm.dm_msyd_monitor_common_rtnodelatitude a where a.isnew=1) s where s.ra =1) bz "
//				+ "group by "
//				+tb_function+"(bz.data_dt,"+selectTime+") )) bt "
//				+ "on ar.latitude_name=bt.latitude_name";
		String numsql3="SELECT "
				+ "sr.monitor_node,"
				+ "sr.monitor_node_event,"
				+ "sr.latitude_type,"
				+ "sr.latitude_name,"
				+ "sr.latitude_value_person_"+selectValue+", "
				+ "hb.monitor_node,"
				+ "hb.monitor_node_event,"
				+ "hb.latitude_type,"
				+ "hb.latitude_name,"
				+ "hb.latitude_value_person_"+selectValue
				+ " from (SELECT monitor_node,monitor_node_event,latitude_type,max(data_dt) data_dt FROM dm.dm_msyd_monitor_common_rtnodelatitude where isnew=1 GROUP BY 1,2,3) dt "
				+ "JOIN dm.dm_msyd_monitor_common_rtnodelatitude hb on dt.monitor_node = hb.monitor_node and dt.monitor_node_event=hb.monitor_node_event  "
				+ "and dt.latitude_type = hb.latitude_type and hb.data_dt="
				+ tb_function+"(dt.data_dt,"+selectTime+") "
				+ "FULL OUTER JOIN (SELECT * FROM dm.dm_msyd_monitor_common_rtnodelatitude where  isnew=1) sr  "
				+ "on hb.monitor_node=sr.monitor_node and hb.monitor_node_event=sr.monitor_node_event "
				+ "and hb.latitude_type=sr.latitude_type and hb.latitude_name=sr.latitude_name";
		List<Object[]> result=IU.query(numsql3);
		int credit_apply_result_present_date=0;
		int credit_apply_result_later_date=0;
		int release_result_present_date=0;
		int release_result_later_date=0;
		for (Object[] o : result){
			String present_monitor_node=o[0]==null?"":o[0].toString();
			String present_monitor_node_event=o[1]==null?"":o[1].toString();
			String present_latitude_type=o[2]==null?"":o[2].toString();
			String ago_monitor_node=o[5]==null?"":o[5].toString();
			String ago_monitor_node_event=o[6]==null?"":o[6].toString();
			String ago_latitude_type=o[7]==null?"":o[7].toString();
			if(present_monitor_node.equals("credit_apply")&&present_monitor_node_event.equals("credit_apply_result")&&present_latitude_type.equals("credit_apply_result_outreason")){
				credit_apply_result_present_date=credit_apply_result_present_date+Integer.parseInt(o[4]==null?"0":o[4].toString());
			}
			if(ago_monitor_node.equals("credit_apply")&&ago_monitor_node_event.equals("credit_apply_result")&&ago_latitude_type.equals("credit_apply_result_outreason")){
				credit_apply_result_later_date=credit_apply_result_later_date+Integer.parseInt(o[9]==null?"0":o[9].toString());
			}
			if(present_monitor_node.equals("loan_release")&&present_monitor_node_event.equals("release_result")&&present_latitude_type.equals("release_result_reason")){
				release_result_present_date=release_result_present_date+Integer.parseInt(o[4]==null?"0":o[4].toString());
			}
			if(ago_monitor_node.equals("loan_release")&&ago_monitor_node_event.equals("release_result")&&ago_latitude_type.equals("release_result_reason")){
				release_result_later_date=release_result_later_date+Integer.parseInt(o[9]==null?"0":o[9].toString());
			}
		}
//		System.out.println(credit_apply_result_present_date);
//		System.out.println(credit_apply_result_later_date);
//		System.out.println(release_result_present_date);
//		System.out.println(release_result_later_date);
//		System.out.println("---------------");
		for (Object[] o : result){
			Map<String,String> map = new HashMap<String,String>();
			String monitor_node=o[0]==null?"":o[0].toString();
			String monitor_node_event=o[1]==null?"":o[1].toString();
			String latitude_type=o[2]==null?"":o[2].toString();
			String latitude_name=o[3]==null?"":o[3].toString();
			String latitude_value_person= o[4]==null?"0":o[4].toString();
			//上一期
			String later_monitor_node=o[5]==null?"":o[5].toString();
			String later_monitor_node_event=o[6]==null?"":o[6].toString();
			String later_latitude_type=o[7]==null?"":o[7].toString();
			String later_latitude_name=o[8]==null?"":o[8].toString();
			String later_latitude_value_person= o[9]==null?"0":o[9].toString();
			//System.out.println(o[4].toString());
			if(monitor_node.equals("loan_release")&&monitor_node_event.equals("release_result")&&latitude_type.equals("release_result_reason")){
				map.put("name", "release_result_present");
				map.put("reason", latitude_name);
				map.put("value", latitude_value_person);
				map.put("later_reason", later_latitude_name);
				map.put("later_value", later_latitude_value_person);
				map.put("cal_value",calt_rate(latitude_value_person,later_latitude_value_person));
				map.put("present_rate",present_rate(latitude_value_person,release_result_present_date)+"%");
				map.put("later_rate",present_rate(later_latitude_value_person,release_result_later_date)+"%");
				//System.out.println(present_rate(latitude_value_person,release_result_present_date)+"------"+present_rate(later_latitude_value_person,release_result_later_date));
				map.put("rate",rate(present_rate(latitude_value_person,release_result_present_date),present_rate(later_latitude_value_person,release_result_later_date))+"%");
//				System.out.println(map.get("value"));
//				System.out.println(map.get("later_value"));
//				System.out.println(map.get("cal_value"));
//				System.out.println(release_result_present_date);
//				System.out.println(map.get("present_rate"));
//				System.out.println(map.get("later_rate"));
//				System.out.println(map.get("rate"));
			}else if(later_monitor_node.equals("loan_release")&&later_monitor_node_event.equals("release_result")&&later_latitude_type.equals("release_result_reason")){
				map.put("name", "release_result_later");
				map.put("reason", latitude_name);
				map.put("value", latitude_value_person);
				map.put("later_reason", later_latitude_name);
				map.put("later_value", later_latitude_value_person);
				map.put("cal_value",calt_rate(latitude_value_person,later_latitude_value_person));
				map.put("present_rate",present_rate(latitude_value_person,release_result_present_date)+"%");
				map.put("later_rate",present_rate(later_latitude_value_person,release_result_later_date)+"%");
				//System.out.println(present_rate(latitude_value_person,release_result_present_date)+"------"+present_rate(later_latitude_value_person,release_result_later_date));
				map.put("rate",rate(present_rate(latitude_value_person,release_result_present_date),present_rate(later_latitude_value_person,release_result_later_date))+"%");
			}
			if(monitor_node.equals("credit_apply")&&monitor_node_event.equals("credit_apply_result")&&latitude_type.equals("credit_apply_result_outreason")){
				map.put("name", "credit_apply_result_present");
				map.put("reason", latitude_name);
				map.put("value", latitude_value_person);
				map.put("later_reason", later_latitude_name);
				map.put("later_value", later_latitude_value_person);
				map.put("cal_value",calt_rate(latitude_value_person,later_latitude_value_person));
				map.put("present_rate",present_rate(latitude_value_person,credit_apply_result_present_date)+"%");
				map.put("later_rate",present_rate(later_latitude_value_person,credit_apply_result_later_date)+"%");
				map.put("rate",rate(present_rate(latitude_value_person,credit_apply_result_present_date),present_rate(later_latitude_value_person,credit_apply_result_later_date))+"%");
			}else if(later_monitor_node.equals("credit_apply")&&later_monitor_node_event.equals("credit_apply_result")&&later_latitude_type.equals("credit_apply_result_outreason")){
				map.put("name", "credit_apply_result_later");
				map.put("reason", latitude_name);
				map.put("value", latitude_value_person);
				map.put("later_reason", later_latitude_name);
				map.put("later_value", later_latitude_value_person);
				map.put("cal_value",calt_rate(latitude_value_person,later_latitude_value_person));
				map.put("present_rate",present_rate(latitude_value_person,credit_apply_result_present_date)+"%");
				map.put("later_rate",present_rate(later_latitude_value_person,credit_apply_result_later_date)+"%");
				map.put("rate",rate(present_rate(latitude_value_person,credit_apply_result_present_date),present_rate(later_latitude_value_person,credit_apply_result_later_date))+"%");
//				System.out.println(map.get("value"));
//				System.out.println(map.get("later_value"));
//				System.out.println(map.get("cal_value"));
//				System.out.println(map.get("present_rate"));
//				System.out.println(map.get("later_rate"));
//				System.out.println(map.get("rate"));
			}
			list.add(map);
			//System.out.println(list);
		}
		String str=JSON.toJSONString(list);
		return str;
	}
	public String getName(String name){
		String hyName="";
		switch(name){
	    case "regist" :
	    	hyName="注册";
	       break; 
	    case "auth_bind" :
	    	hyName="实名绑卡";
	       break;
	    case "credit_apply" :
	    	hyName="授信申请";
	       break;
	    case "loan_apply" :
	    	hyName="贷款";
	       break;
	    case "loan_withdraw" :
	    	hyName="提款";
	       break;
	    default :
	    	hyName="放款";
	}
		return hyName;
	}
	public String calt_rate(String num1,String num2){
		int a=Integer.parseInt(num1);
		int b=Integer.parseInt(num2);
		int c=a-b;
		return c+"";
	}
	public String rate(String num1,String num2){
		Double a=Double.parseDouble(num1);
		Double b=Double.parseDouble(num2);
		Double c=a-b;
		return String.format("%.2f",c);
	}
	public String present_rate(String num1,int num2){
		Double a=Double.parseDouble(num1);
		String num3=(num2==0?1:num2)+"";
		Double b=Double.parseDouble(num3);
		Double c=a*100/b;
		String result=String.format("%.2f",c);
		//System.out.println("a------"+a+"b-------"+b+"c------"+c);
		return result;
	}
}
