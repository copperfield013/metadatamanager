package cn.sowell.datacenter.admin.controller.dictionary;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.model.demo.criteria.DemoCriteria;
import cn.sowell.datacenter.model.demo.pojo.PlainDemo;
import cn.sowell.datacenter.model.demo.service.DemoService;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryBasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryParentItemCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryBasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryParentItem;
import cn.sowell.datacenter.model.dictionary.pojo.RecordRelationType;
import cn.sowell.datacenter.model.dictionary.service.DictionaryBasicItemService;
import cn.sowell.datacenter.model.dictionary.service.DictionaryParentItemService;
import cn.sowell.datacenter.model.dictionary.service.RecordRelationTypeService;

@Controller
@RequestMapping(AdminConstants.URI_DICTIONARY + "/recordRelationType")
public class RecordRelationTypeController {
	
	@Resource
	RecordRelationTypeService recordRelationTypeService;
	
	Logger logger = Logger.getLogger(RecordRelationTypeController.class);
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(ServletRequestDataBinder binder) {
		System.out.println("执行了InitBinder方法");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}
	
	@ResponseBody
	@RequestMapping("/doAdd")
	public void doAdd(String leftRecordType,String rightRecordType, String leftName, String rightName, String symmetry){
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
		
		recordRelationTypeService.saveRelation(leftObj, rightObj, symmetry);
	}

}
