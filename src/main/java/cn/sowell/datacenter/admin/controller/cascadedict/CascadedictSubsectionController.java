package cn.sowell.datacenter.admin.controller.cascadedict;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictSubsection;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictSubsectionChild;
import cn.sowell.datacenter.model.cascadedict.service.CascadedictBasicItemService;
import cn.sowell.datacenter.model.cascadedict.service.CascadedictSubsectionService;

@Controller
@RequestMapping(AdminConstants.URI_CASCADEDICT + "/cascadedictSubsection")
public class CascadedictSubsectionController {
	
	@Resource
	CascadedictBasicItemService cascadedictBasicItemService;
	
	@Resource
	CascadedictSubsectionService csService;
	
	Logger logger = Logger.getLogger(CascadedictSubsectionController.class);
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(ServletRequestDataBinder binder) {
		System.out.println("执行了InitBinder方法");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	
/**
	 * 根据父id, 获取当前父亲subselection
	 * @param id
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSubSelectByParentId")
	public String getSubSelectByParentId(String parentId, Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			List<CascadedictSubsection> childList = csService.getSubSelectByParentId(parentId);
			map.put("code", 200);
			map.put("msg", "success");
			map.put("childList", childList);
			model.addAttribute("childList", childList);
			return jobj.toString();
		} catch (Exception e) {
			logger.error("添加失败", e);
			map.put("code", 400);
			map.put("msg", "error");
			return jobj.toString();
		}
	}
	
	
	/**
	 * 根据父subselectionId, 获取当前父亲subChild
	 * @param id
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSubChildByPid")
	public String getSubChildByPid(String subsectionId, Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			List<CascadedictSubsectionChild> childList = csService.getSubChildByPid(subsectionId);
			map.put("code", 200);
			map.put("msg", "success");
			map.put("childList", childList);
			model.addAttribute("childList", childList);
			return jobj.toString();
		} catch (Exception e) {
			logger.error("添加失败", e);
			map.put("code", 400);
			map.put("msg", "error");
			return jobj.toString();
		}
	}
	
	@ResponseBody
	@RequestMapping("/saveOrUpdate")
	public String saveOrUpdate(CascadedictSubsection creteria){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			if (creteria.getOrder() == null) {
				creteria.setOrder(1);
			}
			csService.saveOrUpdate(creteria);
			map.put("code", 200);
			map.put("msg", "success");
			map.put("creteria", creteria);
			return jobj.toString();
		} catch (Exception e) {
			logger.error("添加失败", e);
			map.put("code", 400);
			map.put("msg", "error");
			return jobj.toString();
		}
	}
	
	@ResponseBody
	@RequestMapping("/saveOrUpSubChild")
	public String saveOrUpSubChild(CascadedictSubsectionChild subChild){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			if (subChild.getOrder() == null) {
				subChild.setOrder(1);
			}
			csService.saveOrUpSubChild(subChild);
			map.put("code", 200);
			map.put("msg", "success");
			map.put("creteria", subChild);
			return jobj.toString();
		} catch (Exception e) {
			logger.error("添加失败", e);
			map.put("code", 400);
			map.put("msg", "error");
			return jobj.toString();
		}
	}
	
	
	@ResponseBody
	@RequestMapping("/doDelte/{id}")
	public String do_delte(@PathVariable String id){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			
			List<CascadedictSubsectionChild> subChildList = csService.getSubChildByPid(id);
			if (!subChildList.isEmpty()) {
				
				map.put("code", 400);
				map.put("msg", "请先删除孩子");
				return jobj.toString();
			}
			csService.deleteById(id);
			map.put("code", 200);
			map.put("msg", "success");
			return jobj.toString();
		} catch (Exception e) {
			logger.error("删除失败", e);
			map.put("code", 400);
			map.put("msg", "删除失败");
			return jobj.toString();
		}
	}
	
	@ResponseBody
	@RequestMapping("/doDelSubChild/{id}")
	public String doDelSubChild(@PathVariable String id){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			csService.delSubChildById(id);
			map.put("code", 200);
			map.put("msg", "success");
			return jobj.toString();
		}catch(ConstraintViolationException e) {
			logger.error("删除失败", e);
			map.put("code", 400);
			map.put("msg", "请先删除孩子");
			return jobj.toString();
		} catch (Exception e) {
			logger.error("删除失败", e);
			map.put("code", 400);
			map.put("msg", "删除失败");
			return jobj.toString();
		}
	}

}
