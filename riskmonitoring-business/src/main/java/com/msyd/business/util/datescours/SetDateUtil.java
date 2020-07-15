package com.msyd.business.util.datescours;

import java.util.ArrayList;
import java.util.List;

public class SetDateUtil {
	public static List<DataEnty> setData(){
		List<DataEnty> entyList=new ArrayList<>();
		DataEnty dataEnty1=new DataEnty();
		DataEnty dataEnty2=new DataEnty();
		DataEnty dataEnty3=new DataEnty();
		DataEnty dataEnty4=new DataEnty();
		DataEnty dataEnty5=new DataEnty();
		DataEnty dataEnty6=new DataEnty();
		DataEnty dataEnty7=new DataEnty();
		DataEnty dataEnty8=new DataEnty();
		DataEnty dataEnty9=new DataEnty();
		DataEnty dataEnty10=new DataEnty();
		DataEnty dataEnty11=new DataEnty();
		dataEnty1.setAppl_dt("2020-06-01");
		dataEnty1.setPid("-");
		dataEnty1.setCid("1");
		dataEnty1.setName("进件：22803");
		dataEnty1.setValue("22803");
		dataEnty1.setRejectnum("0");
		dataEnty1.setLsNum("0");
		dataEnty2.setAppl_dt("2020-06-01");
		dataEnty2.setPid("1");
		dataEnty2.setCid("11");
		dataEnty2.setName("反欺诈-贷款：22803");
		dataEnty2.setValue("22803");
		dataEnty2.setRejectnum("14968");
		dataEnty2.setLsNum("7835");
		dataEnty3.setAppl_dt("2020-06-01");
		dataEnty3.setPid("11");
		dataEnty3.setCid("111");
		dataEnty3.setName("小贷授信规则初审：7835");
		dataEnty3.setValue("7835");
		dataEnty3.setRejectnum("218");
		dataEnty3.setLsNum("1");
		dataEnty4.setAppl_dt("2020-06-01");
		dataEnty4.setPid("111");
		dataEnty4.setCid("1111");
		dataEnty4.setName("小贷授信路由匹配：1");
		dataEnty4.setValue("1");
		dataEnty4.setRejectnum("1");
		dataEnty4.setLsNum("0");
		dataEnty5.setAppl_dt("2020-06-01");
		dataEnty5.setPid("111");
		dataEnty5.setCid("1112");
		dataEnty5.setName("资方绑卡/合同签订：550");
		dataEnty5.setValue("550");
		dataEnty5.setRejectnum("0");
		dataEnty5.setLsNum("0");
		dataEnty6.setAppl_dt("2020-06-01");
		dataEnty6.setPid("111");
		dataEnty6.setCid("1113");
		dataEnty6.setName("稠州审核模型初审：7065");
		dataEnty6.setValue("7065");
		dataEnty6.setRejectnum("1461");
		dataEnty6.setLsNum("0");
		
		dataEnty7.setAppl_dt("2020-06-01");
		dataEnty7.setPid("1113");
		dataEnty7.setCid("11131");
		dataEnty7.setName("征信前置机：5604");
		dataEnty7.setValue("5604");
		dataEnty7.setRejectnum("4899");
		dataEnty7.setLsNum("0");
		
		dataEnty8.setAppl_dt("2020-06-01");
		dataEnty8.setPid("11131");
		dataEnty8.setCid("111311");
		dataEnty8.setName("反欺诈-资格预审：705");
		dataEnty8.setValue("705");
		dataEnty8.setRejectnum("22");
		dataEnty8.setLsNum("0");
		
		dataEnty9.setAppl_dt("2020-06-01");
		dataEnty9.setPid("111311");
		dataEnty9.setCid("1113111");
		dataEnty9.setName("小贷额度授信规则：683");
		dataEnty9.setValue("683");
		dataEnty9.setRejectnum("0");
		dataEnty9.setLsNum("0");
		
		
		dataEnty10.setAppl_dt("2020-06-01");
		dataEnty10.setPid("1113111");
		dataEnty10.setCid("11131111");
		dataEnty10.setName("稠州审核模型终审：683");
		dataEnty10.setValue("683");
		dataEnty10.setRejectnum("0");
		dataEnty10.setLsNum("0");
		
		dataEnty11.setAppl_dt("2020-06-01");
		dataEnty11.setPid("11131111");
		dataEnty11.setCid("111311111");
		dataEnty11.setName("通过");
		dataEnty11.setValue("683");
		dataEnty11.setRejectnum("0");
		dataEnty11.setLsNum("0");
		
		
		entyList.add(dataEnty1);
		entyList.add(dataEnty2);
		entyList.add(dataEnty3);
		entyList.add(dataEnty4);
		entyList.add(dataEnty5);
		entyList.add(dataEnty6);
		entyList.add(dataEnty7);
		entyList.add(dataEnty8);
		entyList.add(dataEnty9);
		entyList.add(dataEnty10);
		entyList.add(dataEnty11);
		return entyList;
	}
}
