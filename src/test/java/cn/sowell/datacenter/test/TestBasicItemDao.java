package cn.sowell.datacenter.test;


import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.model.enun.NodeOpsType;
import com.abc.model.enun.NodeType;

import cn.sowell.datacenter.entityResolver.config.ModuleConfigureMediator;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
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
	BasicItemNodeService basicItemNodeService;
	@Resource
	RecordRelationTypeService recordRelationTypeService;
	
	@Resource
	ModuleConfigureMediator moduleConfigMediator;
	@Test
	public void fun()  {
			/*String currentId = "1114";
			String beforeId = "1116";
			String afterId = "";
			BasicItemNode current = basicItemNodeService.getOne(Integer.parseInt(currentId));
			basicItemNodeService.nodeSort(current, beforeId, afterId);
		*/
		System.out.println();
		/*ABCNode abcNode = MappingContainer.getABCNode("人口信息");*/
		
		basicItemService.createTabCol();
		
		System.out.println();
		/*basicItemNodeService.excuExtend("1423");*/
		
		
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
			 
//		System.out.println(abctRecord);
		
		System.out.println("=============");
	}
	  
	
}   
