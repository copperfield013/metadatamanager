package cn.sowell.datacenter.test;


import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.mapping.node.NodeOpsType;
import com.abc.mapping.node.NodeType;
import com.abc.util.ValueTypeConstant;
import com.abc.variable.Global;

import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
import cn.sowell.datacenter.model.dictionary.service.DictionaryBasicItemService;
import cn.sowell.datacenter.model.dictionary.service.RecordRelationTypeService;
import cn.sowell.datacenter.model.node.service.BasicItemNodeService;

@ContextConfiguration(locations = "classpath*:spring-config/spring-junit.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestBasicItemDao {
	@Resource
	BasicItemService basicItemService;
	@Resource
	RecordRelationTypeService rrt;
	@Resource
	SessionFactory sFactory;
	@Resource
	DictionaryBasicItemService dictBitemServices;
	@Resource
	BasicItemNodeService basicItemNodeService;
	@Resource
	RecordRelationTypeService recordRelationTypeService;
	
	@Test
	public void fun() {
		String LeftRecordRelaCode = "TE10000R999";
		int indexOf = LeftRecordRelaCode.indexOf("R");
		String rightRecordRelaCode = LeftRecordRelaCode.substring(indexOf+1);
		
		int code = Integer.parseInt(rightRecordRelaCode) + 1;
        String format = String.format("%03d", code);  
        System.out.println(format);
	}
	
	@Test
	public void fun1() {
		
		for (NodeOpsType f : NodeOpsType.values()) {
			System.out.println(f.getName());
		}
		System.out.println("=============");
		System.out.println(NodeType.ATTRIBUTE);
		System.out.println(NodeType.ATTRIBUTE.getCode());
		System.out.println(NodeType.ATTRIBUTE);
		System.out.println(NodeType.getNodeType(1));
		System.out.println("=============");
		
		int abctRecord = ValueTypeConstant.ABCT_RECORD;
		 int abctDate = ValueTypeConstant.ABCT_DATE;
		 int abctString = ValueTypeConstant.ABCT_STRING;
		 String abctNameString = ValueTypeConstant.ABCT_NAME_STRING;
		 String abctNameRecord = ValueTypeConstant.ABCT_NAME_RECORD;
		 
		System.out.println(abctRecord);
		
		System.out.println("=============");
		Long pOSITION_CODE = Global.POSITION_CODE;
		System.out.println(pOSITION_CODE);
	}
	  
	@Test
	public void fun2() {
		
		
		
		/*BasicItemNode current = basicItemNodeService.getOne(919);
		
		basicItemNodeService.nodeSort(current, 920, 921);*/
		//basicItemNodeService.excuExtend("915");
	}
}   
