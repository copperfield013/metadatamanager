package cn.sowell.datacenter.admin.controller.module;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.entityResolver.config.ModuleConfigureMediator;
import cn.sowell.datacenter.entityResolver.config.abst.Module;
import cn.sowell.datacenter.entityResolver.config.param.CreateModuleParam;
import cn.sowell.datacenter.entityResolver.config.param.QueryModuleCriteria;
import cn.sowell.datacenter.model.node.pojo.BasicItemNode;
import cn.sowell.datacenter.model.node.service.BasicItemNodeService;

@Controller
@RequestMapping(AdminConstants.URI_MODULE + "/configModule")
public class ConfigModuleController {
    
	@Resource
	ModuleConfigureMediator dBModuleConfigMediator;
	
	@Resource
	BasicItemNodeService basicItemNodeService;
	
	@Resource
	SessionFactory sFactory;
	
	@RequestMapping("/list")
	public String list(QueryModuleCriteria criteria, Model model){
		
		List<Module> list = dBModuleConfigMediator.queryModules(criteria);
		model.addAttribute("list", list);
		model.addAttribute("criteria", criteria);
		return AdminConstants.JSP_MODULE + "/configModule/list.jsp";
	}
	
	@RequestMapping("/add")
	public String add(Model model){
		List<BasicItemNode> abcList = basicItemNodeService.getAllAbc();
		List<BasicItemNode> childNodeList = basicItemNodeService.getAttribute(String.valueOf(abcList.get(0).getId()));
		model.addAttribute("abcList", abcList);
		model.addAttribute("childNodeList", childNodeList);
		return AdminConstants.JSP_MODULE + "/configModule/add.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/childNodeList")
	public String childNodeList(String parentId){
		List<BasicItemNode> childNodeList = basicItemNodeService.getAttribute(parentId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("childNodeList", childNodeList);
		JSONObject json = new JSONObject(map);
		return json.toString();
	}
	
	@ResponseBody
	@RequestMapping("/do_add")
	public AjaxPageResponse doAdd(String moduleName, String moduleTitle, String mappingName, String codeName, String titleName){
		try {
			CreateModuleParam param = new CreateModuleParam(moduleTitle, mappingName);
			param.setModuleName(moduleName);
			param.setCodeName(codeName);
			param.setTitleName(titleName);
			dBModuleConfigMediator.createModule(param);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("添加成功", "configModule_list");
		} catch (Exception e) {
			return AjaxPageResponse.FAILD("添加失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("/do_delete")
	public AjaxPageResponse doDelte(String name){
		try {
			dBModuleConfigMediator.removeModule(name);
			return AjaxPageResponse.REFRESH_LOCAL("删除成功");
		} catch (Exception e) {
			return AjaxPageResponse.FAILD("删除失败");
		}
	}
	
	//禁用启用
	@ResponseBody
	@RequestMapping("/disabled")
	public AjaxPageResponse disabled(String name, String endisabled){
		try {
			if ("true".equals(endisabled)) {//disabled 为true， 说明为启用状态
				dBModuleConfigMediator.disableModule(name);
			} else {
				dBModuleConfigMediator.enableModule(name);
			}
			return AjaxPageResponse.REFRESH_LOCAL("操作成功");
		} catch (Exception e) {
			return AjaxPageResponse.FAILD("操作失败");
		}
	}
	
	
	@ResponseBody
	@RequestMapping("/refresh")
	public AjaxPageResponse refresh(String name){
		
		
		try {
			basicItemNodeService.refresh(name);
			return AjaxPageResponse.REFRESH_LOCAL("刷新成功");
		} catch (Exception e) {
			return AjaxPageResponse.FAILD("刷新失败");
		}
	}
	
}
	
