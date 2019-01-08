package cn.sowell.datacenter.admin.controller.stat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abc.util.ValueType;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.admin.controller.dictionary.BasicItemContext;
import cn.sowell.datacenter.model.cascadedict.criteria.CascadedictBasicItemCriteria;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictBasicItem;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictSubsection;
import cn.sowell.datacenter.model.cascadedict.service.CascadedictBasicItemService;
import cn.sowell.datacenter.model.cascadedict.service.CascadedictSubsectionService;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.OneLevelItem;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
import cn.sowell.datacenter.model.stat.pojo.StatE;
import cn.sowell.datacenter.model.stat.service.StatEService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping(AdminConstants.URI_STAT + "/state")
public class StatEController {
	
	@Resource
	StatEService statEService;
	@Resource
	BasicItemService basicItemService;
	
	Logger logger = Logger.getLogger(StatEController.class);
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(ServletRequestDataBinder binder) {
		System.out.println("执行了InitBinder方法");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("/list")
	public ModelAndView list(){
		try {
			ModelAndView mv = new ModelAndView();
			StatE criteria = new StatE();
			List statEList = statEService.queryList(criteria);
			mv.addObject("statEList", statEList);
			mv.setViewName(AdminConstants.JSP_STAT + "/list.jsp");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@ResponseBody
	@RequestMapping("/getEntity")
	public String getEntity(){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			List allEntity = basicItemService.getAllEntity();
			map.put("allEntity", allEntity);
			map.put("code", 200);
			map.put("msg", "success");
			return jobj.toString();
		}catch(ConstraintViolationException e) {
			logger.error("操作失败", e);
			map.put("code", 400);
			map.put("msg", "操作失败");
			return jobj.toString();
		}
		
	}
	
	/**
	 * 根据实体Code获取实体下的普通属性
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getComm")
	public String getComm(String bieCode){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			List allEntity = basicItemService.getComm(bieCode);
			map.put("allEntity", allEntity);
			map.put("code", 200);
			map.put("msg", "success");
			return jobj.toString();
		}catch(ConstraintViolationException e) {
			logger.error("操作失败", e);
			map.put("code", 400);
			map.put("msg", "操作失败");
			return jobj.toString();
		}
		
	}
	

	@ResponseBody
	@RequestMapping("/do_add")
	public AjaxPageResponse doAdd(@ApiParam(name="BasicItem", value="传入json格式", required=true)BasicItem basicItem, OneLevelItem oneLevelItem, Integer cascadedict,StatE creteria){
		try {
			statEService.insert(basicItem, oneLevelItem, cascadedict,creteria);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("添加成功", "state_list");
		} catch (DataIntegrityViolationException e) {
			logger.error("该统计实体已存在", e);
			return AjaxPageResponse.FAILD("该统计实体已存在");
		} catch (Exception e) {
			logger.error("添加失败", e);
			return AjaxPageResponse.FAILD("添加失败");
		}
	}

	@ResponseBody
	@RequestMapping("/do_delete")
	public AjaxPageResponse doDelte(String bieCode, String entityId){
		try {
			statEService.delete(bieCode, entityId);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("删除成功", "state_list");
		}catch (Exception e) {
			logger.error("删除失败", e);
			return AjaxPageResponse.FAILD("删除失败");
		}
	}
	
	
	//过期实体or正常  普通属性和多值属性
	@ResponseBody
	@RequestMapping(value="/saveStatus")
	public String savePastDue(String id, String statusStr){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		
		try {
			BasicItem basicItem = basicItemService.getBasicItem(id);
			basicItemService.saveUsingStatus(basicItem, statusStr);
			map.put("dataType", "comm");
			if (String.valueOf(ValueType.RECORD.getIndex()).equals(basicItem.getOneLevelItem().getDataType())) {
				map.put("dataType", "RECORD");
			} 
			
			map.put("basicItem", basicItem);
			map.put("code", 200);
			map.put("msg", "success");
			return jobj.toString();
		} catch (Exception e) {
			logger.error("操作失败", e);
			map.put("code", 400);
			map.put("msg", "操作失败");
			return jobj.toString();
		}
	}

}
