package com.abc.application.eight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.application.FusionContext;
import com.abc.dto.ErrorInfomation;
import com.abc.panel.Discoverer;
import com.abc.panel.PanelFactory;
import com.abc.record.Tracker;
import com.alibaba.fastjson.JSONObject;

import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;

@ContextConfiguration(locations = "classpath*:spring-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class YjsqExportTest {
	private static Logger logger = Logger.getLogger(YjsqExportTest.class);
	//private ExecutorService pool = Executors.newFixedThreadPool(20);
	protected String mapperName = "yjsq_people";
	protected String writeMappingName = "yjsq_people";
	protected String dictionaryMappingName="yjsq_people";
	protected String familyDoctorMapper = "familydoctor";
//	protected String filename = "E:\\数据\\下城\\下城区\\朝晖街道\\大家苑社区2017-10-26人口数据.xlsx";
//	protected String sheetName = "民政局人口信息记录";
	protected String excelExtName = "xlsx";

	/*@Test
	public void discoverTrack() {
		
		FusionContext context=new FusionContext();
		context.setMappingName(writeMappingName);
		context.setDictionaryMappingName(dictionaryMappingName);
		Discoverer discoverer = PanelFactory.getDiscoverer(context);
		Tracker tracker=
				discoverer.discoverTrack("9c84dc9ba21241dfb862b241b7564fa3");
		
		logger.debug(tracker.getEntity().toJson());
		
		List<ErrorInfomation> errorInfomatios = tracker.getErrorInfomations();
		if(errorInfomatios!=null){
			for(ErrorInfomation errorInfomation:errorInfomatios){
				logger.debug(errorInfomation.toString());
			}
		}
		
	}*/
}
