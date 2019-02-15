package cn.sowell.datacenter.admin.controller.dictionary;

import com.abc.util.ValueType;

import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.OneLevelItem;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;

public class BasicItemContext {
	
	public BasicItem saveBasicItem(BasicItemService basicItemService,BasicItem basicItem, OneLevelItem oneLevelItem, Integer cascadedict) throws Exception {
		
		String dType = oneLevelItem.getDataType();
		String comm = null;
		
		if ("5".equals(dType)) {
			oneLevelItem.setDictParentId(0);
		} else if ("1".equals(dType)) {
			oneLevelItem.setDictParentId(0);
		}else if ("15".equals(dType)) {
			oneLevelItem.setDictParentId(0);
		}else if ("6".equals(dType)) {
			oneLevelItem.setDictParentId(0);
		}else if ("7".equals(dType)) {
			oneLevelItem.setDictParentId(0);
		}else if ("10".equals(dType)) {
			oneLevelItem.setNeedHistory(1);
		}else if ("9".equals(dType)) {
			oneLevelItem.setNeedHistory(1);
			oneLevelItem.setDictParentId(0);
		}else if ("16".equals(dType)) {
			oneLevelItem.setNeedHistory(1);
		} else if ("14".equals(dType)) {
		} else if ("8".equals(dType)) {
			oneLevelItem.setDictParentId(0);
		} else if ("11".equals(dType)) {
			oneLevelItem.setDictParentId(0);
		} else if ("21".equals(dType)) {
			oneLevelItem.setDictParentId(0);
		} 
		oneLevelItem.setDataType(String.valueOf(dType));
		//记录类型
		if (String.valueOf(ValueType.RECORD.getIndex()).equals(oneLevelItem.getDataType())) {
			oneLevelItem.setDictParentId(0);
			basicItem.setParent("");
		} else if (String.valueOf(ValueType.GROUP.getIndex()).equals(oneLevelItem.getDataType())) {
			oneLevelItem.setDictParentId(0);
		} else if (String.valueOf(ValueType.REPEAT.getIndex()).equals(oneLevelItem.getDataType())) {
			
		} else {
			// 到这儿来是普通属性  和多值属性下的普通属性
			//它们的区别是父亲不同， 所以先求父亲    默认前端传来的都是父亲的code， 
			BasicItem bItemPanrent = basicItemService.getBasicItem(basicItem.getParent());
			
			//多值属性下的普通属性
			if (String.valueOf(ValueType.REPEAT.getIndex()).equals(bItemPanrent.getOneLevelItem().getDataType())) {
				basicItem.setParent(bItemPanrent.getParent() + "_" +bItemPanrent.getCode());
				oneLevelItem.setTableName(bItemPanrent.getOneLevelItem().getTableName());
				oneLevelItem.setGroupName(bItemPanrent.getCode());
			} else {//普通属性
				oneLevelItem.setTableName("t_" + basicItem.getParent() + "_" + oneLevelItem.getGroupName());
				comm = "comm";
			}
		}
		basicItem.setUsingState(0);
		
		String flag = "";
		if (basicItem.getCode()== null ||basicItem.getCode() == "" || basicItem.getCode().length()<1) {
			flag = "add";
		}
		
		basicItem.setOneLevelItem(oneLevelItem);
		
        BasicItem saveOrUpdate = basicItemService.saveOrUpdate(basicItem, flag, comm, cascadedict);
        return saveOrUpdate;
	}
}
