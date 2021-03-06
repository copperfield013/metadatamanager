package cn.sowell.datacenter.admin.controller.dictionary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abc.model.enun.RelationType;
import com.abc.model.enun.ValueType;
import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.ajax.NoticeType;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.model.demo.criteria.DemoCriteria;
import cn.sowell.datacenter.model.demo.pojo.PlainDemo;
import cn.sowell.datacenter.model.demo.service.DemoService;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.RecordRelationType;
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
	public String doAdd(String leftRecordType,String rightRecordType, String leftName, String rightName,String leftRelationType,String rightRelationType, Integer leftgiant, Integer rightgiant, String symmetry){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		
		RecordRelationType leftObj = new RecordRelationType();
		RecordRelationType rightObj = new RecordRelationType();
		
		leftObj.setName(leftName);
		leftObj.setLeftRecordType(leftRecordType);
		leftObj.setGiant(leftgiant);
		
		if ("symmetry".equals(symmetry)) {//添加对称关系
			leftObj.setRightRecordType(leftRecordType);
			leftObj.setRelationType(leftRelationType);
		} else {
			leftObj.setRightRecordType(rightRecordType);
			leftObj.setRelationType(leftRelationType);
			
			//生成右关系
			rightObj.setName(rightName);
			rightObj.setLeftRecordType(rightRecordType);
			rightObj.setRightRecordType(leftRecordType);
			rightObj.setRelationType(rightRelationType);
			rightObj.setGiant(rightgiant);
		}
		
			try {
				recordRelationTypeService.saveRelation(leftObj, rightObj, symmetry);
				map.put("code", 200);
				map.put("msg", "操作成功");
				return jobj.toString();
			} catch (DataIntegrityViolationException e) {
				map.put("code", 400);
				map.put("msg", "主键重复或者是关系名重复, 请重新添加");
				return jobj.toString();
			} catch (Exception e) {
				logger.error("操作失败", e);
				map.put("code", 400);
				if (e.getMessage().contains("t_sc_basic_item_fix")) {
					map.put("msg", "t_sc_basic_item_fix：没有可用数据");
					return jobj.toString();
				}
				map.put("msg", "操作失败");
				return jobj.toString();
			}
	}

	@ResponseBody
	@RequestMapping("/delete")
	public AjaxPageResponse delete(String id){
		try {
			AjaxPageResponse response = new AjaxPageResponse();
			recordRelationTypeService.delete(id);
			response.setNotice("删除成功");
			response.setNoticeType(NoticeType.SUC);
			return response;
		} catch (Exception e) {
			logger.error("删除失败", e);
			return AjaxPageResponse.FAILD("删除失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("/saveStatus")
	public String saveStatus(String typeCode, boolean isPastDue){
		try {
			recordRelationTypeService.saveStatus(typeCode, isPastDue);
			System.out.println();
			return "{\"code\":200, \"msg\":\"操作成功\"}";
		} catch (Exception e) {
			logger.error("删除失败", e);
			return "{\"code\":400, \"msg\":\"操作失败\"}";
		}
	}
	
	/**
	 * 编辑关系名称， 获取关系记录
	 * @param typeCode
	 * @param isPastDue
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/edit")
	public String edit(String typeCode){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			RecordRelationType recordRelationType = recordRelationTypeService.getRecordRelationType(typeCode);
			map.put("recordRelationType", recordRelationType);
			map.put("code", 200);
			map.put("msg", "成功！");
			return jobj.toJSONString();
		} catch (Exception e) {
			map.put("code", 400);
			map.put("msg", "失败！");
			return jobj.toJSONString();
		}
	}
	
	
	/**
	 * 保存编辑后的 关系名称
	 * @param typeCode
	 * @param isPastDue
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/do_edit")
	public String edit(String name, String typeCode, String relationType, Integer giant){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			RecordRelationType recordRelationType = recordRelationTypeService.getRecordRelationType(typeCode);
			recordRelationType.setName(name);
			recordRelationType.setRelationType(relationType);
			recordRelationType.setGiant(giant);
			recordRelationTypeService.update(recordRelationType);
			map.put("recordRelationType", recordRelationType);
			map.put("code", 200);
			map.put("msg", "成功！");
			return jobj.toJSONString();
		} catch (Exception e) {
			map.put("code", 400);
			map.put("msg", "失败！");
			return jobj.toJSONString();
		}
	}
	
	 //这里获取关系类型
    @ResponseBody
	@RequestMapping("/getRelationType")
	public String getRelationType(){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			Map<String, Object> reTypeMap = new HashMap<String, Object>();
			reTypeMap.put(String.valueOf(RelationType.MANY.getIndex()), RelationType.MANY.getCName());
			reTypeMap.put(String.valueOf(RelationType.ONE.getIndex()), RelationType.ONE.getCName());
			map.put("reTypeMap", reTypeMap);
			map.put("code", 200);
			map.put("msg", "操作成功");
			return jobj.toString();
		} catch (Exception e) {
			logger.error("操作失败", e);
			map.put("code", 400);
			map.put("msg", "操作失败");
			return jobj.toString();
		}
	}
    
    
    //这里获取本实体下特定类型的关系
    @ResponseBody
	@RequestMapping("/getRelation")
	public String getRelation(String leftRecordType, String relationType){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			List<RecordRelationType> relationList = recordRelationTypeService.getRelaByType(leftRecordType, relationType);
			map.put("relationList", relationList);
			map.put("code", 200);
			map.put("msg", "操作成功");
			return jobj.toString();
		} catch (Exception e) {
			logger.error("操作失败", e);
			map.put("code", 400);
			map.put("msg", "操作失败");
			return jobj.toString();
		}
	}
	
}
