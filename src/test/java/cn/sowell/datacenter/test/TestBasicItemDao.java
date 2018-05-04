package cn.sowell.datacenter.test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.persistence.Transient;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.abc.mapping.node.NodeType;

import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;

@ContextConfiguration(locations = "classpath*:spring-config/spring-junit.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestBasicItemDao {
	@Resource
	BasicItemService basicItemService;

	@Resource
	SessionFactory sFactory;
	
	@Test
	@Transactional
	public void fun() {
		System.out.println("sdf");
	//	basicItemService.createTabCol();
		
		
		NodeType nodeType = NodeType.getNodeType(1);
		
		
		System.out.println(nodeType.getName());
		
		System.out.println();
		
		
		
	}
	
	
	
}
