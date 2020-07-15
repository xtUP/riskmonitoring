package com.msyd.business.util.datescours;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class DataEnty {

	private String appl_dt;
	private String pid;
	private String cid;
	private String name;
	private String creditLY;
	private String value;
	private String rejectnum;
	private String passNum;
	private String lrNestNum;
	private String lsNum;
	private String nodePassRate;
	private String nodeRejectRate;
	private String qkjRejectRate;
	private String nodeLSRate;
	private List<DataEnty> children = new ArrayList<>();
	private JSONObject lineStyle;
	
	public String getQkjRejectRate() {
		return qkjRejectRate;
	}
	public void setQkjRejectRate(String qkjRejectRate) {
		this.qkjRejectRate = qkjRejectRate;
	}
	public String getAppl_dt() {
		return appl_dt;
	}
	public void setAppl_dt(String appl_dt) {
		this.appl_dt = appl_dt;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreditLY() {
		return creditLY;
	}
	public void setCreditLY(String creditLY) {
		this.creditLY = creditLY;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRejectnum() {
		return rejectnum;
	}
	public void setRejectnum(String rejectnum) {
		this.rejectnum = rejectnum;
	}
	public String getPassNum() {
		return passNum;
	}
	public void setPassNum(String passNum) {
		this.passNum = passNum;
	}
	public String getLrNestNum() {
		return lrNestNum;
	}
	public void setLrNestNum(String lrNestNum) {
		this.lrNestNum = lrNestNum;
	}
	public String getLsNum() {
		return lsNum;
	}
	public void setLsNum(String lsNum) {
		this.lsNum = lsNum;
	}
	public String getNodePassRate() {
		return nodePassRate;
	}
	public void setNodePassRate(String nodePassRate) {
		this.nodePassRate = nodePassRate;
	}
	public String getNodeRejectRate() {
		return nodeRejectRate;
	}
	public void setNodeRejectRate(String nodeRejectRate) {
		this.nodeRejectRate = nodeRejectRate;
	}
	public String getNodeLSRate() {
		return nodeLSRate;
	}
	public void setNodeLSRate(String nodeLSRate) {
		this.nodeLSRate = nodeLSRate;
	}
	public List<DataEnty> getChildren() {
		return children;
	}
	public void setChildren(List<DataEnty> children) {
		this.children = children;
	}
	
	
	
	public JSONObject getLineStyle() {
		return lineStyle;
	}
	public void setLineStyle(JSONObject lineStyle) {
		this.lineStyle = lineStyle;
	}
	@Override
	public String toString() {
		return "DataEnty [appl_dt=" + appl_dt + ", pid=" + pid + ", cid=" + cid + ", name=" + name + ", creditLY="
				+ creditLY + ", value=" + value + ", rejectnum=" + rejectnum + ", passNum=" + passNum + ", lrNestNum="
				+ lrNestNum + ", lsNum=" + lsNum + ", nodePassRate=" + nodePassRate + ", nodeRejectRate="
				+ nodeRejectRate + ", qkjRejectRate=" + qkjRejectRate + ", nodeLSRate=" + nodeLSRate + ", children="
				+ children + ", lineStyle=" + lineStyle + "]";
	}
}
