package com.msyd.business.util.datescours;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.alibaba.fastjson.JSONObject;

import net.sf.json.JSONArray;
public class Test {
	public static void main(String[] args) {
//		try {
//			JSONArray joa=JSONArray.fromObject(dateToDo());
//			System.out.println(joa);
//		} catch (Exception e) {
//			e.printStackTrace();		}
		System.out.println(getDate());
		List<JSONObject> list=new ArrayList<>();
		JSONObject jo=new JSONObject();
		jo.put("name", "tom");
		list.add(jo);
		System.out.println(jo);
	}
	public static String getDate(){
		String dataStr="";
		try {
			JSONArray joa=JSONArray.fromObject(dateToDo());
			dataStr=joa.toString();
			dataStr=dataStr.substring(1,dataStr.length()-1);
		} catch (Exception e) {
			e.printStackTrace();		}
		
		
		return dataStr;
	}
	public static List<DataEnty> dateToDo() throws Exception{
		List<DataEnty> entyList=SetDateUtil.setData();
		List<DataEnty> entyListnew=new ArrayList<>();
		for(int i=0;i<entyList.size();i++){
			DataEnty DataEnty=entyList.get(i);
			DataEnty dataEnty1=new DataEnty();
			dataEnty1.setPid(DataEnty.getCid());
			dataEnty1.setCid("888");
			dataEnty1.setName("拒绝"+DataEnty.getRejectnum());
			dataEnty1.setRejectnum(DataEnty.getRejectnum());
			dataEnty1.setValue(DataEnty.getRejectnum());
			entyListnew.add(dataEnty1);
			DataEnty dataEnty2=new DataEnty();
			dataEnty2.setPid(DataEnty.getCid());
			dataEnty2.setCid("666");
			dataEnty2.setName("流失"+DataEnty.getLsNum());
			dataEnty2.setLsNum(DataEnty.getLsNum());
			dataEnty2.setValue(DataEnty.getLsNum());
			entyListnew.add(dataEnty2);
			entyListnew.add(DataEnty);
		}
		List<DataEnty> list=new ArrayList<>();
		for(int i=0;i<entyListnew.size();i++){
			DataEnty DataEnty=entyListnew.get(i);
			if("-".equals(DataEnty.getPid())){
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
}
