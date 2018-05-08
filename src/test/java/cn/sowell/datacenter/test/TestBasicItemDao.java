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
		
		String entityCode = "TE10000";
		String codeStr = entityCode.substring(2);
		int code = Integer.parseInt(codeStr) + 1;
		String m = "TE";
        String format = String.format("%04d", code);  
        System.out.println(m+format);
		
	}
	
	
	
}
