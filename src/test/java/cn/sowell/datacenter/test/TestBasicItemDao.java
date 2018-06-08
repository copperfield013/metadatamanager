package cn.sowell.datacenter.test;


import java.net.MalformedURLException;

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
import cn.sowell.datacenter.model.node.pojo.BasicItemNode;
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
	public void fun() throws MalformedURLException {
		System.out.println();
		NodeOpsType nodeOpsType1 = NodeOpsType.getNodeOpsType(1);
		NodeOpsType nodeOpsType2 = NodeOpsType.getNodeOpsType(2);
		
		NodeOpsType nodeOpsType3 = NodeOpsType.getNodeOpsType(3);
		
		NodeOpsType nodeOpsType4 = NodeOpsType.getNodeOpsType(4);
		NodeOpsType nodeOpsType5 = NodeOpsType.getNodeOpsType(5);
		NodeOpsType nodeOpsType6 = NodeOpsType.getNodeOpsType(6);
		
		System.out.println();
		
		
		
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
	  
	
}   
