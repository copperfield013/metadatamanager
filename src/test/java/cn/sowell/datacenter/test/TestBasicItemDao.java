package cn.sowell.datacenter.test;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.mapping.conf.MappingContainer;
import com.abc.mapping.node.ABCNode;
import com.abc.mapping.node.NodeOpsType;
import com.abc.mapping.node.NodeType;
import com.abc.util.ValueTypeConstant;
import com.alibaba.fastjson.JSONObject;

import cn.sowell.datacenter.entityResolver.config.DBModuleConfigMediator;
import cn.sowell.datacenter.entityResolver.config.ModuleConfigureMediator;
import cn.sowell.datacenter.entityResolver.config.abst.Module;
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
	
	/*@Resource
	ModuleConfigureMediator dBModuleConfigMediator;*/
	@Test
	public void fun()  {
			/*String currentId = "1114";
			String beforeId = "1116";
			String afterId = "";
			BasicItemNode current = basicItemNodeService.getOne(Integer.parseInt(currentId));
			basicItemNodeService.nodeSort(current, beforeId, afterId);
		*/
		System.out.println();
		ABCNode abcNode = MappingContainer.getABCNode("人口信息");
		
		System.out.println();
		//basicItemNodeService.excuExtend("1423");
		
		
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
	}
	  
	
}   
