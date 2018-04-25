package com.abc.application.eight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.application.FusionContext;
import com.abc.mapping.entity.Entity;
import com.abc.mapping.entity.SimpleEntity;
import com.abc.panel.Discoverer;
import com.abc.panel.Integration;
import com.abc.panel.PanelFactory;
import com.alibaba.fastjson.JSONObject;

import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;

@ContextConfiguration(locations = "classpath*:spring-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class RecordRelationTest {

	private static Logger logger = Logger.getLogger(RecordRelationTest.class);
	protected String mapperName = "studentparty";
	protected String writeMappingName = "studentparty";
	protected String dictionaryMappingName="default_dm";
	
	@Resource
	SessionFactory sessionFactory;
	
	@Resource
	BasicItemDao basicItemDao;
	
	/*@Test
	public void readData() {
//		Session session = sessionFactory.getCurrentSession();
		long startTime = System.currentTimeMillis();
		FusionContext context=new FusionContext();
		context.setMappingName(writeMappingName);
		context.setSource(FusionContext.SOURCE_COMMON);
		context.setDictionaryMappingName(dictionaryMappingName);
		Integration integration=PanelFactory.getIntegration();
		Entity entity=createEntity(mapperName);
		logger.debug(entity.toJson());
		String code=integration.integrate(entity, context);
		
		Discoverer discoverer=PanelFactory.getDiscoverer(context);
		Entity result=discoverer.discover(code);
		logger.debug(code + " : "+ result.toJson());
		
		long endTime = System.currentTimeMillis();// 记录结束时间
		logger.debug((float) (endTime - startTime) / 1000);
	}*/

	private Entity createEntity(String mappingName) {
		Entity entity = new Entity(mappingName);
		entity.putValue("姓名", "施连心8");
		entity.putValue("身份证号码", "3318419056509112");
		entity.putValue("性别", "女");
		entity.putValue("所在班级", "经济141");
		entity.putValue("出生年月", "1996-09-06");
		entity.putValue("寝室号", "5-607");
		entity.putValue("申请时间", "2015-04-01");
		entity.putValue("所在支部", "第二党支部");
		entity.putValue("志愿时数", 50);
		
		entity.putValue("类别", "入党申请人");
		
		//multi attribute
		//干部情况
		SimpleEntity sentity = new SimpleEntity("干部情况");
		sentity.putValue("任职起始时间", "2015-04-01");
		sentity.putValue("任职名称", "班长");
		sentity.putValue("任职级别", "班级");
		entity.putMultiAttrEntity(sentity);
		
		Entity relationentity = new Entity("培养联系人");
		relationentity.putValue("姓名", "刘志华");
		entity.putRelationEntity("培养联系人","入党联系人", relationentity);
		
		return entity;
	}

}
