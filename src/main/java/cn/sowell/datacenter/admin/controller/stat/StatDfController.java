package cn.sowell.datacenter.admin.controller.stat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;
import cn.sowell.datacenter.model.dictionary.pojo.OneLevelItem;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
import cn.sowell.datacenter.model.stat.pojo.StatDf;
import cn.sowell.datacenter.model.stat.service.StatDfService;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(AdminConstants.URI_STAT + "/statDf")
public class StatDfController {
	
	@Resource
	StatDfService statDfService;
	@Resource
	BasicItemService basicItemService;
	
	Logger logger = Logger.getLogger(StatDfController.class);
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(ServletRequestDataBinder binder) {
		System.out.println("执行了InitBinder方法");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@ResponseBody
	@RequestMapping("/do_add")
	public String doAdd(@ApiParam(name="BasicItem", value="传入json格式", required=true)BasicItem basicItem, OneLevelItem oneLevelItem, Integer cascadedict,StatDf creteria, BiRefAttr biRefAttr){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			
			statDfService.saveOrUpdate(basicItem, oneLevelItem, cascadedict, creteria, biRefAttr);
			
			map.put("statDf", creteria);
			map.put("code", 200);
			map.put("msg", "success");
			return jobj.toString();
		}  catch (Exception e) {
			logger.error("操作失败", e);
			map.put("code", 400);
			map.put("msg", "操作失败");
			return jobj.toString();
		}
	}

	@ResponseBody
	@RequestMapping("/do_delete")
	public String doDelte(Integer statdfid){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			statDfService.delete(statdfid);
			map.put("code", 200);
			map.put("msg", "success");
			return jobj.toString();
		}catch (Exception e) {
			logger.error("操作失败", e);
			map.put("code", 400);
			map.put("msg", "操作失败");
			return jobj.toString();
		}
	}
	
	@ResponseBody
	@RequestMapping("/getStatDfList")
	public String getStatDfList(String bieCode){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			List statDfList = statDfService.getStatDfList(bieCode);
			
			map.put("statDfList", statDfList);
			map.put("code", 200);
			map.put("msg", "success");
			return jobj.toString();
		}  catch (Exception e) {
			logger.error("操作失败", e);
			map.put("code", 400);
			map.put("msg", "操作失败");
			return jobj.toString();
		}
	}
	
	@ResponseBody
	@RequestMapping("/getStatDf")
	public String getStatDf(Integer statdfid){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			 StatDf statDf = statDfService.get(StatDf.class, statdfid);
			map.put("statDf", statDf);
			map.put("code", 200);
			map.put("msg", "success");
			return jobj.toString();
		}  catch (Exception e) {
			logger.error("操作失败", e);
			map.put("code", 400);
			map.put("msg", "操作失败");
			return jobj.toString();
		}
	}
}
