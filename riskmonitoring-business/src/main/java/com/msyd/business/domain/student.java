package com.msyd.business.domain;

public class student {
	private String id;
private String name;
private String hight;
private String low;

public student(String name,String hight,String low) {
	// TODO Auto-generated constructor stub
	this.hight=hight;
	this.name=name;
	this.low=low;
}
public student(String id,String name,String hight,String low) {
	// TODO Auto-generated constructor stub
	this.id=id;
	this.hight=hight;
	this.name=name;
	this.low=low;
}
public student(){
	
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getHight() {
	return hight;
}
public void setHight(String hight) {
	this.hight = hight;
}
public String getLow() {
	return low;
}
public void setLow(String low) {
	this.low = low;
}

public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
@Override
public String toString() {
	return "student [id=" + id + ", name=" + name + ", hight=" + hight + ", low=" + low + "]";
}


}
