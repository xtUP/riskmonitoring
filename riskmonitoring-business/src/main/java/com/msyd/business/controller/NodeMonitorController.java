package com.msyd.business.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
@RequestMapping("/echarts/node")
public class NodeMonitorController{
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
		return prefix + "/NodeEcharts";
	}
	@RequestMapping(value = "node")
	@ResponseBody
	public String nodeToAjax(HttpServletResponse response,
			HttpServletRequest request){
		//前端时间选择
		String selectValue=(String) request.getParameter("selectValue");
		//根据时间判断同比函数
		String tb_function="days_sub";
		//根据时间判断环比函数
		String hb_function="minutes_sub";
		//根据时间填充sql查询字段
		String selectTime="10";
		if(Objects.equals(selectValue, null)||Objects.equals(selectValue, "")){
			selectValue="10m";
			selectTime="10";
		}else if(selectValue.equals("30")){
			selectValue="30m";
			selectTime="30";
		}else if(selectValue.equals("60")){
			selectValue="1h";
			selectTime="60";
		}else if(selectValue.equals("一天")){
			tb_function="weeks_sub";
			hb_function="days_sub";
			selectValue="day";
			selectTime="1";
		}else{
			selectValue="10m";
			selectTime="10";
		}
		//System.out.println(selectValue);
		ImpalaUtil IU=new ImpalaUtil();
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
//		String sql="select * from (select rank() over(partition by a.monitor_node_event order by a.data_dt desc ) ra, "
//				+"a.monitor_node_event"
//				+ ",a.reqeust_num_"+selectValue
//				+ ",a.request_person_"+selectValue
//				+ ",a.pass_num_"+selectValue
//				+ ",a.pass_person_"+selectValue
//				+ ",a.reject_num_"+selectValue
//				+ ",a.reject_person_"+selectValue
//				+ ",a.data_dt from dm.dm_msyd_monitor_common_rtnodevalue a where a.isnew=1) s where s.ra =1;";
		String sql_str="select xz.ra,xz.monitor_node_event"
						+ ",xz.reqeust_num_"+selectValue
						+ ",xz.request_person_"+selectValue
						+ ",xz.pass_num_"+selectValue
						+ ",xz.pass_person_"+selectValue
						+ ",xz.reject_num_"+selectValue
						+ ",xz.reject_person_"+selectValue
						+ ",xz.last_req_dt"
						+ ",xz.last_pass_dt"
						+ ",xy.monitor_node_event as yesterday_monitor_node_event"
						+ ",xy.reqeust_num_"+selectValue+" as yesterday_reqeust_num_"+selectValue
						+ ",xy.request_person_"+selectValue+" as yesterday_request_person_"+selectValue
						+ ",xy.pass_num_"+selectValue+" as yesterday_pass_num_"+selectValue
						+ ",xy.pass_person_"+selectValue+" as yesterday_pass_person_"+selectValue
						+ ",xy.reject_num_"+selectValue+" as yesterday_reject_num_"+selectValue
						+ ",xy.reject_person_"+selectValue+" as yesterday_reject_person_"+selectValue
						+ ",xy.last_req_dt as yesterday_last_req_dt "
						+ ",xy.last_pass_dt as yesterday_last_pass_dt "
						+ ",xyz.monitor_node_event as late_monitor_node_event"
						+ ",xyz.reqeust_num_"+selectValue+" as late_reqeust_num_"+selectValue
						+ ",xyz.request_person_"+selectValue+" as late_request_person_"+selectValue
						+ ",xyz.pass_num_"+selectValue+" as late_pass_num_"+selectValue
						+ ",xyz.pass_person_"+selectValue+" as late_pass_person_"+selectValue
						+ ",xyz.reject_num_"+selectValue+" as late_reject_num_"+selectValue
						+ ",xyz.reject_person_"+selectValue+" as late_reject_person_"+selectValue
						+ ",xyz.last_req_dt as late_last_req_dt "
						+ ",xyz.last_pass_dt as late_last_pass_dt "
						+ "from "
						+"(select * from "
								+"(select rank() over(partition by a.monitor_node_event order by "
								+"a.last_req_dt desc ) ra ,a.* from dm.dm_msyd_monitor_common_rtnodevalue a where a.isnew=1) x where x.ra =1)xz "
						+"left join "
						+"("
								+"select "
								+ "n.monitor_node_event"
								+ ",n.reqeust_num_"+selectValue
								+ ",n.request_person_"+selectValue
								+ ",n.pass_num_"+selectValue
								+ ",n.pass_person_"+selectValue
								+ ",n.reject_num_"+selectValue
								+ ",n.reject_person_"+selectValue
								+ ",n.last_req_dt,n.data_dt,n.last_pass_dt from dm.dm_msyd_monitor_common_rtnodevalue n where n.data_dt in "
								+ "("
										+"select "
										+tb_function+"(b.data_dt,1) as times "
										+"from ("
												+"select * from (select rank() over(partition by a.monitor_node_event order by a.last_req_dt desc ) ra, "
												+"a.* from dm.dm_msyd_monitor_common_rtnodevalue a where a.isnew=1) s where s.ra =1 ) b "
										+"	group by "
										+ tb_function+"(b.data_dt,1))"
										+")xy "
						+"on xz.monitor_node_event=xy.monitor_node_event "
						+ "left join "
						+ "("
						+"select "
						+ "n.monitor_node_event"
						+ ",n.reqeust_num_"+selectValue
						+ ",n.request_person_"+selectValue
						+ ",n.pass_num_"+selectValue
						+ ",n.pass_person_"+selectValue
						+ ",n.reject_num_"+selectValue
						+ ",n.reject_person_"+selectValue
							+ ",n.last_req_dt,n.data_dt,n.last_pass_dt from dm.dm_msyd_monitor_common_rtnodevalue n where n.data_dt in "
							+ "("
							+ "select "
							    + hb_function+"(b.data_dt,"+selectTime+") as times "
							+ "from ("
							+ "select * from (select rank() over(partition by a.monitor_node_event order by a.last_req_dt desc ) ra, "
							+ "a.last_req_dt,a.data_dt,a.last_pass_dt from dm.dm_msyd_monitor_common_rtnodevalue a where a.isnew=1) s where s.ra =1  ) b "
							+ "group by "
							+ hb_function+"(b.data_dt,"+selectTime+")) "
							+ ") xyz "
							+ "on xz.monitor_node_event=xyz.monitor_node_event";
		List<Object[]> result=IU.query(sql_str);
		for (Object[] o : result){
			Map<String,String> map = new HashMap<String,String>();
			map.put("monitor_node_event", o[1].toString());
			map.put("reqeust_num", o[2]==null?"暂无":o[2].toString());
			map.put("request_person", o[3]==null?"暂无":o[3].toString());
			map.put("pass_num", o[4]==null?"暂无":o[4].toString());
			map.put("pass_person", o[5]==null?"暂无":o[5].toString());
			map.put("reject_num", o[6]==null?"暂无":o[6].toString());
			map.put("reject_person", o[7]==null?"暂无":o[7].toString());
			
			//System.out.println(o[10].toString());
			//反欺诈贷款通过率
			Double anti_fraud_loan_pass_rate=balance_rate(map.get("pass_person"),map.get("reject_person"))*100;
			map.put("anti_fraud_loan_pass_rate",String.format("%.2f", anti_fraud_loan_pass_rate)+"%");
			//反欺诈贷款拒绝率
			Double anti_fraud_loan_reject_rate=balance_rate(map.get("reject_person"),map.get("pass_person"))*100;
			map.put("anti_fraud_loan_reject_rate",String.format("%.2f", anti_fraud_loan_reject_rate)+"%");
			//请求时间
			map.put("request_time", o[8]==null?"暂无":o[8].toString());
			Map<String,String> gsh_request=gsh_Time(map.get("request_time"));
			map.put("request_time1", gsh_request.get("time1"));
			map.put("request_time2", gsh_request.get("time2"));
			//请求通过时间
			map.put("request_pass_time", o[9]==null?"暂无":o[9].toString());
			//请求时间间隔
			Map<String,String> gsh_pass_time=gsh_Time(map.get("request_pass_time"));
			map.put("request_pass_time1", gsh_pass_time.get("time1"));
			map.put("request_pass_time2", gsh_pass_time.get("time2"));
			if(map.get("request_time").equals("暂无")){
				map.put("nearRequest_Time", "---");
			}else{
				map.put("nearRequest_Time", calculateTime(map.get("request_time")));
			}
			if(map.get("request_pass_time").equals("暂无")){
				map.put("nearPass_Time", "---");
			}else{
				map.put("nearPass_Time", calculateTime(map.get("request_pass_time")));
			}
			//昨天数据
			map.put("yesterday_reqeust_num", o[11]==null?"暂无":o[11].toString());
			map.put("yesterday_request_person", o[12]==null?"暂无":o[12].toString());
			map.put("yesterday_pass_num", o[13]==null?"暂无":o[13].toString());
			map.put("yesterday_pass_person", o[14]==null?"暂无":o[14].toString());
			map.put("yesterday_reject_num", o[15]==null?"暂无":o[15].toString());
			map.put("yesterday_reject_person", o[16]==null?"暂无":o[16].toString());
			//请求同比上一日此时Contrast
			if(map.get("yesterday_request_person").equals("暂无")){
				map.put("Contrast_people", "暂无");
				map.put("Contrast_people_flag","N");
			}else{
				Map<String,String> Contrast_people=Contrast_people(map.get("request_person"),map.get("yesterday_request_person"));
				map.put("Contrast_people", Contrast_people.get("rate")+"%");
				map.put("Contrast_people_flag", Contrast_people.get("flag"));
			}
			//System.out.println(map.get("Contrast_people"));
			//同比上一日反欺诈贷款通过率
			if(map.get("yesterday_pass_person").equals("暂无")){
				map.put("Contrast_anti_fraud_loan_pass_rate","暂无");
				map.put("Contrast_pass_rate_flag","N");
			}else{
				//Double Contrast_anti_fraud_loan_pass_rate=balance_rate(map.get("yesterday_pass_person"),map.get("yesterday_reject_person"))*100;
				Map<String,String> Contrast_pass_rate=Contrast_people(map.get("pass_person"),map.get("yesterday_pass_person"));
				map.put("Contrast_anti_fraud_loan_pass_rate",Contrast_pass_rate.get("rate")+"%");
				map.put("Contrast_pass_rate_flag",Contrast_pass_rate.get("flag"));
			}
			//同比上一日反欺诈贷款拒绝率
			//Double Contrast_anti_fraud_loan_reject_rate=balance_rate(map.get("yesterday_reject_person"),map.get("yesterday_pass_person"))*100;
			if(map.get("yesterday_reject_person").equals("暂无")){
				map.put("Contrast_anti_fraud_loan_reject_rate","暂无");
				map.put("Contrast_reject_rate_flag","N");
			}else{
			Map<String,String> Contrast_reject_rate=Contrast_people(map.get("reject_person"),map.get("yesterday_reject_person"));
			map.put("Contrast_anti_fraud_loan_reject_rate",Contrast_reject_rate.get("rate")+"%");
			map.put("Contrast_reject_rate_flag",Contrast_reject_rate.get("flag"));
			}
//			if(map.get("monitor_node_event").equals("anti_fraud_preApprv")){
//				System.out.println(map.get("yesterday_reject_person")+""+map.get("yesterday_pass_person"));
//				System.out.println(Contrast_reject_rate.get("flag")+"-----------"+Contrast_reject_rate.get("rate"));
//			}
			//环比数据
			map.put("late_reqeust_num", o[20]==null?"暂无":o[20].toString());
			map.put("late_request_person", o[21]==null?"暂无":o[21].toString());
			map.put("late_pass_num", o[22]==null?"暂无":o[22].toString());
			map.put("late_pass_person", o[23]==null?"暂无":o[23].toString());
			map.put("late_reject_num", o[24]==null?"暂无":o[24].toString());
			map.put("late_reject_person", o[25]==null?"暂无":o[25].toString());
			//环比请求人数
			if(map.get("late_request_person").equals("暂无")){
				map.put("QOQ_request_person", "暂无");
				map.put("QOQ_request_person_flag","N");
			}else{
			Map<String,String> QOQ_request_person=Contrast_people(map.get("request_person"),map.get("late_request_person"));
			map.put("QOQ_request_person",QOQ_request_person.get("rate")+"%");
			map.put("QOQ_request_person_flag",QOQ_request_person.get("flag"));
			}
			//环比通过人数
			//Double late_pass_person=balance_rate(map.get("pass_person"),map.get("late_reject_person"));
			if(map.get("late_pass_person").equals("暂无")){
				map.put("QOQ_pass_person_rate", "暂无");
				map.put("QOQ_pass_person_flag","N");
			}else{
			Map<String,String> late_rate=Contrast_people(map.get("pass_person"),map.get("late_pass_person"));
			map.put("QOQ_pass_person_rate", late_rate.get("rate")+"%");
			map.put("QOQ_pass_person_flag",late_rate.get("flag"));
			}
			//System.out.println(late_rate.get("flag")+"-----------"+late_rate.get("rate"));
			//环比拒绝人数
			//Double late_reject_person=balance_rate(map.get("late_reject_person"),map.get("late_pass_person"));
			if(map.get("late_reject_person").equals("暂无")){
				map.put("QOQ_reject_person_rate", "暂无");
				map.put("QOQ_reject_person_flag","N");
			}else{
			Map<String,String> reject_rate=Contrast_people(map.get("reject_person"),map.get("late_reject_person"));
			map.put("QOQ_reject_person_rate", reject_rate.get("rate")+"%");
			map.put("QOQ_reject_person_flag",reject_rate.get("flag"));
			}
			//System.out.println(reject_rate.get("flag")+"-----------"+reject_rate.get("rate"));
			//System.out.println(Contrast_people.get("flag")+"---------"+Contrast_rate.get("rate"));
			list.add(map);
			//System.out.println(list);
		}
		String str=JSON.toJSONString(list);
		return str;

	}
	//最近时间----天时分秒
	public String calculateTime(String time){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String nearTime="";
	    try{  
	      Date d1 = df.parse(time);  
	      String d =df.format(new Date());  
	      Date d2 = df.parse(d);
	      long diff = d2.getTime() - d1.getTime();//这样得到的差值是毫秒级别  
	      long days = diff / (1000 * 60 * 60 * 24);  
	      long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);  
	      long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
	      nearTime=""+days+"天"+hours+"小时"+minutes+"分";
	    }catch (Exception e){  
	    }  
		return nearTime;
	}
	//最近时间----分
		public String calculateTime_min(String time){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			String nearTime="";
		    try{  
		      Date d1 = df.parse(time);  
		      String d =df.format(new Date());  
		      Date d2 = df.parse(d);
		      long diff = d2.getTime() - d1.getTime();//这样得到的差值是毫秒级别  
		      long days = diff / (1000 * 60 * 60 * 24);  
		      long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);  
		      long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
		      nearTime=days*24*60+hours*60+minutes+"分";
		      //System.out.println(""+days+"天"+hours+"小时"+minutes+"分");  
		    }catch (Exception e){  
		    }  
			return nearTime;
		}
	//同比上一日此时通过人数
	public Map<String,String> Contrast_people(String today,String yesterday){
		Map<String,String> map = new HashMap<String,String>();
		Double todaynum=Double.parseDouble(today=="暂无"?"0.00":today);
		Double yesterdaynum=Double.parseDouble(yesterday=="暂无"?"0.00":yesterday);
		String flag="up";
		Double fz=0.00;
		Double result=0.00;
		String rate="0.00";
		if(todaynum-yesterdaynum>=0){
			fz=(todaynum-yesterdaynum)*100;
			if(yesterdaynum==0){
				yesterdaynum=1.00;
			}
			result=fz/yesterdaynum;
			rate=String.format("%.2f", result);
			flag="up";
		}else{
			fz=(yesterdaynum-todaynum)*100;
			if(yesterdaynum==0){
				yesterdaynum=1.00;
			}
			result=fz/yesterdaynum;
			rate=String.format("%.2f", result);
			flag="down";
		}
		map.put("flag", flag);
		map.put("rate", rate);
		return map;
	}
//	//同比上一日此时通过率
//		public Map<String,String> Contrast_rate(Double today,Double yesterday){
//			Map<String,String> map = new HashMap<String,String>();
//			String flag="up";
//			Double result=0.00;
//			String rate="0.00";
//			if(today-yesterday>=0){
//				result=today-yesterday;
//				rate=String.format("%.2f", result);
//				flag="up";
//			}else{
//				result=yesterday-today;
//				rate=String.format("%.2f", result);
//				flag="down";
//			}
//			map.put("flag", flag);
//			map.put("rate", rate);
//			return map;
//		}
		//计算比率
				public Double balance_rate(String now,String ago){
					Double nownum=Double.parseDouble(now=="暂无"?"0.00":now);
					Double agonum=Double.parseDouble(ago=="暂无"?"0.00":ago);
					Double fm=nownum+agonum;
					Double result=0.00;
					if(fm==0){
						fm=1.00;
					}
					result=nownum/fm;
					return result;
				}
				//最近时间----天时分秒
				public Map<String,String> gsh_Time(String time){
					Map<String,String> map = new HashMap<String,String>();
					if(time.equals("暂无")){
						map.put("time1","暂无");
						map.put("time2","暂无");
						return map;
					}
				    try{  
				    	
				    	map.put("time1",time.substring(0, 10));
						map.put("time2",time.substring(11));
				    }catch (Exception e){  
				    }  
				    return map;
				}				
		public static void main(String[] args) {
			Double a=5.00;
			Double b=-7.00;
			System.out.println(a-b);
		}
}

