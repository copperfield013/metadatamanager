package cn.sowell.datacenter.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.abc.mapping.node.NodeOps;
import com.abc.mapping.node.NodeOpsType;
import com.abc.mapping.node.NodeType;
import com.abc.util.ValueTypeConstant;
import com.abc.variable.Global;
import com.alibaba.fastjson.JSONObject;

import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
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
	@Resource
	RecordRelationTypeService recordRelationTypeService;
	
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
		
		for (NodeOpsType f : NodeOpsType.values()) {
			System.out.println(f.getName());
		}
		System.out.println("=============");
		System.out.println(NodeType.ATTRIBUTE.getName());
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
	@Transactional
	public void fun2() {
		
		String leftRecordType = "IBTE001";//人
		String rightRecordType = "IBTE002";//房屋
		List<RecordRelationType> list = recordRelationTypeService.getEntityRelaByBitemId(leftRecordType, rightRecordType);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("comm", list);
		JSONObject jobj = new JSONObject(map);
		System.out.println();
	}
}
