package cn.sowell.datacenter.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;

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
	BasicItemNodeService btn;
	
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
	@Transactional
	public void fun1() {
		
		
		
	}
	
	@Test
	@Transactional
	public void fun2() {
		
		System.out.println(Float.parseFloat("4.4") + Float.parseFloat("19.8"));
		
		float price=Float.parseFloat("4.4") + Float.parseFloat("19.8");
		DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
		String p=decimalFormat.format(price);//format 返回的是字符串
		
		float parseFloat = Float.parseFloat(p);
		
		System.out.println(p);
	}
}
