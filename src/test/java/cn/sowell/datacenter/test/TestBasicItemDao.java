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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.abc.mapping.node.NodeType;

import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.RecordRelationType;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
import cn.sowell.datacenter.model.dictionary.service.RecordRelationTypeService;

@ContextConfiguration(locations = "classpath*:spring-config/spring-junit.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestBasicItemDao {
	@Resource
	BasicItemService basicItemService;
	@Resource
	RecordRelationTypeService rrt;
	@Resource
	SessionFactory sFactory;
	
	@Test
	@Transactional
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
		
		String symmetry = "symmetry";
		
		String leftRecordType = "IBTE001";
		String rightRecordType = "IBTE001";
		String leftName = "111";
		String rightName = "2222";
		
		RecordRelationType rightObj = new RecordRelationType();
		RecordRelationType leftObj = new RecordRelationType();
		
		leftObj.setName(leftName);
		leftObj.setLeftRecordType(leftRecordType);
		
		if ("symmetry".equals(symmetry)) {//添加对称关系
			leftObj.setRightRecordType(leftRecordType);
		} else {
			leftObj.setRightRecordType(rightRecordType);
			
			//生成右关系
			rightObj.setName(rightName);
			rightObj.setLeftRecordType(rightRecordType);
			rightObj.setRightRecordType(leftRecordType);
		}
		
		try {
			rrt.saveRelation(leftObj, rightObj, symmetry);
		} catch (DataIntegrityViolationException e) {
			rrt.saveRelation(leftObj, rightObj, symmetry);
		}
		
		
	}
	
}
