package com.zhsq.test.biz;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.application.BizFusionContext;
import com.abc.application.FusionContext;
import com.abc.mapping.entity.Entity;
import com.abc.panel.Discoverer;
import com.abc.panel.Integration;
import com.abc.panel.IntegrationMsg;
import com.abc.panel.PanelFactory;
import com.zhsq.biz.common.SessionFactory;

@ContextConfiguration(locations = "classpath*:spring-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTest {
	
	private static Logger logger = Logger.getLogger(UserTest.class);
	protected String mapperName = "系统用户";

   @Before
    public void setUp() throws Exception {
        System.out.println("------------Before------------");
    }
    @After
    public void tearDown() throws Exception {
        System.out.println("------------After ------------");
    }
	
    @Test
	public void readData() {

    	long startTime = System.currentTimeMillis();
		BizFusionContext context=new BizFusionContext();
		context.setSource(FusionContext.SOURCE_COMMON);
//		context.setToEntityRange(BizFusionContext.ENTITY_CONTENT_RANGE_ABCNODE_CONTAIN);
		context.setUserCode("e10adc3949ba59abbe56e057f28888d5");
		Integration integration=PanelFactory.getIntegration();
		Entity entity=createEntity(mapperName);
		logger.debug(entity.toJson());
		IntegrationMsg imsg=integration.integrate(context,entity);
		String code=imsg.getCode();
		Discoverer discoverer=PanelFactory.getDiscoverer(context);
		Entity result=discoverer.discover(code);
		logger.debug(code + " : "+ result.toJson());
		
		long endTime = System.currentTimeMillis();// 记录结束时间
		logger.debug((float) (endTime - startTime) / 1000);
	}

	private Entity createEntity(String mappingName) {
		Entity entity = new Entity(mappingName);
		//entity.putValue("唯一编码", "e2a7abaebab643b7831de05469e4895c");
		entity.putValue("用户名", "ww咿i"); 
		
		//entity.removeAllRelationEntity("任务执行人");
		
		Entity relationentity = new Entity("属于组织");
		//relationentity.putValue("唯一编码", "e10adc3949ba59abbe56e057f28888d5");
		relationentity.putValue("名称", "1111");
		entity.putRelationEntity("属于组织","属于组织", relationentity);
		
		
		
		
		/*Entity relationentity2 = new Entity("属于组织");
		//relationentity.putValue("唯一编码", "e10adc3949ba59abbe56e057f28888d5");
		relationentity2.putValue("名称", "4444");
		entity.putRelationEntity("属于组织","属于组织", relationentity2);*/
		
		/*Entity relationentity1 = new Entity("用户");
		relationentity1.putValue("唯一编码", "e10adc3949ba59abbe56e057f28888u5");
		relationentity1.putValue("用户名", "admin");
		entity.putRelationEntity("任务创建人","创建人", relationentity1);*/
		return entity;
	}

}