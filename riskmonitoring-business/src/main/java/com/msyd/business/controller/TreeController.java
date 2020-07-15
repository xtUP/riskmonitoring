package com.msyd.business.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.alibaba.fastjson.JSONObject;
import com.msyd.business.util.datescours.DataEnty;
import com.msyd.business.util.kudu.ImpalaUtil;

import net.sf.json.JSONArray;


@Controller
@RequestMapping("/echarts/Credit_tree")
public class TreeController{
	static String qkjNum="";
	static Map<Object, Object> creditMap=new HashMap<Object, Object>();
	private String prefix = "/echarts/dataecharts";
	/**
	 * jiemian
	 */
	@GetMapping()
	public String search(HttpServletResponse response,
			HttpServletRequest request)
	{
		return prefix + "/TreeHTML";
	}
	@RequestMapping(value = "treeData")
	@ResponseBody
	public String TreeInfo(HttpServletResponse response,
			HttpServletRequest request){
		String selectValue=(String) request.getParameter("selectValue");
		ImpalaUtil IU=new ImpalaUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(Objects.equals(null,selectValue)||Objects.equals("",selectValue)){
			Calendar calendar = Calendar.getInstance();
	        calendar.add(Calendar.DAY_OF_MONTH,-1);
	        Date time = calendar.getTime();
	        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(time);
	        selectValue=yesterday;
		}else{
			Date ztDate =null;
			try {
				ztDate = df.parse(selectValue);
				Calendar c = Calendar.getInstance();
			    c.setTime(ztDate);
			    c.add(c.DAY_OF_MONTH,-1);
			    Date qtDate=c.getTime();
			    String zttime = new SimpleDateFormat("yyyy-MM-dd").format(qtDate);
			    selectValue=zttime;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
				String sql="SELECT b.val_name,a.* FROM dm.dm_msyd_credit_node_applnum a left join tset.dwd_msyd_dimension_reject_sts b "
						+" on b.typ_cde='reject_msg1' and a.reject_msg1=b.val_cde and b.route_cde=a.route_cde"
						+" where a.appl_dt='2020-06-01' ORDER BY b.order_num";
//		String sql=" select res1.appl_dt,res1.reject_msg1,res1.route_cde,res1.parent_num ,res1.order_num  "
//				+" ,res1.num appl_num ,res1.rejectNum reject_num ,res1.passNum pass_num,res2.parentPassNum children_appl_num ,res1.passNum-IFNULL(res2.parentPassNum,res1.passNum) lose_num "
//				+" ,dround(IFNULL(res1.passNum,0)*100/IF(res1.num>0,res1.num,1) ,2) pass_rate,dround(IFNULL(res1.rejectNum,0)*100/IF(res1.num>0,res1.num,1) ,2) 'reject_rate',dround((res1.passNum-IFNULL(res2.parentPassNum,res1.passNum))*100/IF(res1.num>0,res1.num,1) ,2) 'lose_rate'  "
//				+" from "
//				+" (select t1.appl_dt,IFNULL(sts.parent_num,'999') parent_num,IFNULL(sts.order_num,'999') order_num,IFNULL(sts.val_name,t1.reject_msg1) reject_msg1,sts.route_cde,SUM(t1.num) num,SUM(t1.rejectNum) rejectNum,SUM(t1.passNum) passNum FROM  "
//				+" 		(select cre.credit_limit_applcde,to_date(cre.credit_appl_dt) 'appl_dt' "
//				+" 				,usr.reject_msg1,strleft(cre.credit_route_cde,4) credit_route_cde "
//				+" 				,count(distinct cre.credit_limit_applcde) 'num',max(IF(result='Reject',1,0)) 'rejectNum',MIN(IF(result='Reject',0,1)) 'passNum'  "
//				+" 				from (select * from dws.dws_msyd_borrow_subject_creditinfo where create_date='"+selectValue+"' and to_date(credit_appl_dt)='"+selectValue+"' and product_cde in ('YKD','YZD') ) cre  "
//				+" 				join dws.dws_msyd_borrow_flag_rejectmsg_user usr on cre.credit_limit_applcde=usr.credit_limit_applcde  and to_date(usr.req_dt)='"+selectValue+"' and usr.req_dt<=hours_add('"+selectValue+" 00:00:00',26) "
//				+" 						 				group by 1,2,3,4 "
//				+" 				UNION "
//				+" 				select credit_limit_applcde,to_date(credit_appl_dt) 'appl_dt' "
//				+" 				,CASE WHEN credit_out_sts = '00' THEN 'tobesubmit' WHEN credit_out_sts in ('02','N') THEN 'apprvpass'  "
//				+" 				WHEN credit_out_sts = '01' AND credit_in_sts in ('07','15') THEN 'bind' ELSE concat(credit_in_sts,':其他处理中') END AS reject_msg1,strleft(credit_route_cde,4) credit_route_cde "
//				+" 				,1 'num',0 'rejectNum',0 'passNum'  "
//				+" 				from dws.dws_msyd_borrow_subject_creditinfo  "
//				+" 				where create_date='"+selectValue+"' and to_date(credit_appl_dt)='"+selectValue+"' and product_cde in ('YKD','YZD') "
//				+" 				AND credit_out_sts in ('00','01','02','N')  "
//				+" 				UNION "
//				+" 				select credit_limit_applcde,to_date(credit_appl_dt) 'appl_dt' ,'appl' reject_msg1,strleft(credit_route_cde,4) credit_route_cde "
//				+" 				,1 'num',0 'rejectNum',1 'passNum'  "
//				+" 				from dws.dws_msyd_borrow_subject_creditinfo  "
//				+" 				where create_date='"+selectValue+"' and to_date(credit_appl_dt)='"+selectValue+"' and product_cde in ('YKD','YZD') "
//				+" 				) t1 LEFT JOIN tset.dwd_msyd_dimension_reject_sts sts ON sts.typ_cde = 'reject_msg1' AND sts.val_cde = t1.reject_msg1 AND (sts.route_cde='' or sts.route_cde=t1.credit_route_cde)  "
//				+" 				GROUP BY 1,2,3,4,5 order by 2,3 "
//				+" 		) res1 LEFT JOIN "
//				+" ( "
//				+" 		select t1.appl_dt,sts.parent_num,SUM(t1.num) parentPassNum FROM  "
//				+" 		(select cre.credit_limit_applcde,to_date(cre.credit_appl_dt) 'appl_dt' "
//				+" 				,usr.reject_msg1,strleft(cre.credit_route_cde,4) credit_route_cde "
//				+" 				,count(distinct cre.credit_limit_applcde) 'num',max(IF(result='Reject',1,0)) 'rejectNum',MIN(IF(result='Reject',0,1)) 'passNum'  "
//				+" 				from (select * from dws.dws_msyd_borrow_subject_creditinfo where create_date='"+selectValue+"' and to_date(credit_appl_dt)='"+selectValue+"' and product_cde in ('YKD','YZD')) cre  "
//				+" 				join dws.dws_msyd_borrow_flag_rejectmsg_user usr on cre.credit_limit_applcde=usr.credit_limit_applcde  and to_date(usr.req_dt)='"+selectValue+"' and usr.req_dt<=hours_add('"+selectValue+" 00:30:00',26)  "
//				+" 				group by 1,2,3,4 "
//				+" 				UNION "
//				+" 				select credit_limit_applcde,to_date(credit_appl_dt) 'appl_dt' "
//				+" 				,CASE WHEN credit_out_sts = '00' THEN 'tobesubmit' WHEN credit_out_sts IN ('02','N') THEN 'apprvpass'  "
//				+" 				WHEN credit_out_sts = '01' AND credit_in_sts in ('07','15') THEN 'bind' ELSE '' END AS reject_msg1,strleft(credit_route_cde,4) credit_route_cde "
//				+" 				,1 'num',0 'rejectNum',0 'passNum'  "
//				+" 				from dws.dws_msyd_borrow_subject_creditinfo  "
//				+" 				where create_date='"+selectValue+"' and to_date(credit_appl_dt)='"+selectValue+"' and product_cde in ('YKD','YZD') "
//				+" 				AND credit_out_sts in ('00','01','02','N')  "
//				+" 				UNION "
//				+" 				select credit_limit_applcde,to_date(credit_appl_dt) 'appl_dt' ,'appl' reject_msg1,strleft(credit_route_cde,4) credit_route_cde "
//				+" 				,1 'num',0 'rejectNum',1 'passNum'  "
//				+" 				from dws.dws_msyd_borrow_subject_creditinfo  "
//				+" 				where create_date='"+selectValue+"' and to_date(credit_appl_dt)='"+selectValue+"' and product_cde in ('YKD','YZD') "
//				+" 				) t1 JOIN tset.dwd_msyd_dimension_reject_sts sts ON sts.typ_cde = 'reject_msg1' AND sts.val_cde = t1.reject_msg1 AND (sts.route_cde='' or sts.route_cde=t1.credit_route_cde)  "
//				+" 				GROUP BY 1,2 "
//				+" 		) res2 ON res1.appl_dt=res2.appl_dt and res1.order_num=res2.parent_num "
//				+" 		ORDER BY res1.appl_dt ,res1.order_num,res1.parent_num";
						List<Object[]> result=IU.query(sql);
						List<DataEnty> list=new ArrayList<>();
						for (Object[] o : result){
							DataEnty dataEnty=new DataEnty();
//							//节点和数据
//							dataEnty.setName(o[1].toString()+":"+o[5].toString());
//							dataEnty.setPid(o[3].toString());
//							dataEnty.setCid(o[4].toString());
//							dataEnty.setValue(o[5].toString()==null?"0":o[5].toString());
//							dataEnty.setRejectnum(o[6].toString()==null?"0":o[6].toString());
//							dataEnty.setLsNum(o[9].toString()==null?"0":o[9].toString());
//							dataEnty.setNodePassRate(o[10].toString()==null?"0":o[10].toString());
//							dataEnty.setNodeLSRate(o[12].toString()==null?"0":o[12].toString());
//							dataEnty.setNodeRejectRate(o[11].toString()==null?"0":o[11].toString());
							//节点和数据
							dataEnty.setName(o[0].toString()+":"+o[6].toString());
							dataEnty.setPid(o[4].toString());
							dataEnty.setCid(o[5].toString());
							dataEnty.setValue(o[6].toString()==null?"0":o[6].toString());
							dataEnty.setRejectnum(o[7].toString()==null?"0":o[7].toString());
							dataEnty.setLsNum(o[10].toString()==null?"0":o[10].toString());
							dataEnty.setNodePassRate(deciStr(o[11]));
							dataEnty.setNodeLSRate(deciStr(o[13]));
							dataEnty.setNodeRejectRate(deciStr(o[12]));
							if("".equals(dataEnty.getPid())||Objects.equals(null,dataEnty.getPid())){
								qkjNum=dataEnty.getValue();
								creditMap.put("", o[6]);
							}
							creditMap.put(o[5], o[6]);
//							if("".equals(dataEnty.getPid())||Objects.equals(null,dataEnty.getPid())){
//								qkjNum=dataEnty.getValue();
//								creditMap.put("", o[5]);
//							}
//							creditMap.put(o[4], o[5]);
							list.add(dataEnty);
						}
						String dataStr="";
						try {
							JSONArray joa=JSONArray.fromObject(dateToDo(list));
							dataStr=joa.toString();
							dataStr=dataStr.substring(1,dataStr.length()-1);
						} catch (Exception e) {
							e.printStackTrace();		}
						String str=dataStr;
						return str;

	}
	public static List<DataEnty> dateToDo(List<DataEnty> dataList) throws Exception{
		List<DataEnty> entyList=dataList;
		List<DataEnty> entyListnew=new ArrayList<>();
		for(int i=0;i<entyList.size();i++){
			DataEnty DataEnty=entyList.get(i);
			DataEnty.setLineStyle(nodeStyle());
			String isPass=DataEnty.getName()==null?"":DataEnty.getName();
			//根据子节点找出父节点数值，求本节点通过率
			Object fmNum=creditMap.get(DataEnty.getPid());
			DataEnty.setName(DataEnty.getName()+"\n本节点通过率："+rejectRate(DataEnty.getValue(),fmNum)+"%"+"\n全口径通过率："+rejectRate(DataEnty.getValue(),qkjNum)+"%");
			entyListnew.add(DataEnty);
			if(isPass.contains("通过")){
				continue;
			}
			DataEnty dataEnty2=new DataEnty();
			dataEnty2.setPid(DataEnty.getCid());
			dataEnty2.setCid("666");
			dataEnty2.setName("流失笔数:"+DataEnty.getLsNum()+"\n本节点流失率："+DataEnty.getNodeLSRate()+"%"+"\n全口径流失率："+rejectRate(DataEnty.getLsNum(),qkjNum)+"%");
			dataEnty2.setLsNum(DataEnty.getLsNum());
			dataEnty2.setValue(DataEnty.getLsNum());
			dataEnty2.setLineStyle(lsStyle());
			entyListnew.add(dataEnty2);
			DataEnty dataEnty1=new DataEnty();
			dataEnty1.setPid(DataEnty.getCid());
			dataEnty1.setCid("888");
			dataEnty1.setName("拒绝笔数:"+DataEnty.getRejectnum()+"\n本节点拒绝率："+DataEnty.getNodeRejectRate()+"%"+"\n全口径拒绝率："+rejectRate(DataEnty.getRejectnum(),qkjNum)+"%");
			dataEnty1.setRejectnum(DataEnty.getRejectnum());
			dataEnty1.setValue(DataEnty.getRejectnum());
			dataEnty1.setLineStyle(rejectStyle());
			entyListnew.add(dataEnty1);
		}
		List<DataEnty> list=new ArrayList<>();
		for(int i=0;i<entyListnew.size();i++){
			DataEnty DataEnty=entyListnew.get(i);
			if("".equals(DataEnty.getPid())||Objects.equals(null,DataEnty.getPid())){
				list.add(DataEnty);
			}
		}
		for(DataEnty DataEnty:list){
			DataEnty.setChildren(getChildNodes(DataEnty.getCid(),entyListnew));
		}
		return list;
	}

	private static List<DataEnty> getChildNodes(String id,List<DataEnty> rootList) throws Exception {
		List<DataEnty> childList = new ArrayList<>();
		for (DataEnty DataEnty : rootList) {
			if (!Objects.equals(null, DataEnty.getPid())) {
				if (id.equals(DataEnty.getPid())) {
					childList.add(DataEnty);
				}
			}
		}
		if (childList.size() == 0) {
			return null;
		}
		for (DataEnty entity : childList) {
			entity.setChildren(getChildNodes(entity.getCid(), rootList));
		}
		return childList;
	}
	//颜色选取
	public static JSONObject rejectStyle(){
		JSONObject jo=new JSONObject();
		jo.put("color","#CD0000");
		jo.put("borderColor","#CD0000");
		return jo;
	}
	public static JSONObject lsStyle(){
		JSONObject jo=new JSONObject();
		jo.put("color","#FFFF00");
		jo.put("borderColor","#FFFF00");
		return jo;
	}
	public static JSONObject nodeStyle(){
		JSONObject jo=new JSONObject();
		jo.put("color","#00ff33");
		jo.put("borderColor","#00ff33");
		return jo;
	}
	//百分比计算
	public static String rejectRate(Object fz,Object fm){
		String rateStr="0.00";
		try {
			if(Objects.equals(null, fm)||Objects.equals("",fm)||Objects.equals(null, fz)||Objects.equals("",fz)){
				rateStr="0.00";
				return rateStr;
			}
			Double fzNum=Double.parseDouble(fz.toString());
			fzNum=fzNum*100;
			Double fmNum=Double.parseDouble(fm.toString());
			BigDecimal fzNumDeci = new BigDecimal(fzNum);
			BigDecimal fmNumDeci = new BigDecimal(fmNum);
			BigDecimal expreDate = new BigDecimal("0.00");
			//结果
			Double result=fzNum/fmNum;
			int resultcom=fmNumDeci.compareTo(expreDate); 
			if(resultcom==0){
				rateStr="0.00";
			}else{
				DecimalFormat de=new DecimalFormat("##0.00");
				rateStr=de.format(result);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return rateStr;
		}
		return rateStr;
	}
	//
	public static String deciStr(Object obj){
		String rateStr="0.00";
		Double rateNum=0.00;
		try {
			if(Objects.equals(null, obj)||Objects.equals("",obj)){
				rateStr="0.00";
				return rateStr;
			}
		rateStr=obj.toString();
		rateNum=Double.parseDouble(rateStr);	
		rateNum=rateNum*100;
		DecimalFormat de=new DecimalFormat("##0.00");
		rateStr=de.format(Double.parseDouble(rateNum+""));
		} catch (Exception e) {
			e.printStackTrace();
			return rateStr;
		}
		return rateStr;
	}
}
