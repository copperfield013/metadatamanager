package cn.sowell.datacenter.admin.controller.module;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.admin.controller.node.api.BasicItemNodes;
import cn.sowell.datacenter.entityResolver.config.ModuleConfigureMediator;
import cn.sowell.datacenter.entityResolver.config.abst.Module;
import cn.sowell.datacenter.entityResolver.config.param.CreateModuleParam;
import cn.sowell.datacenter.entityResolver.config.param.QueryModuleCriteria;
import cn.sowell.datacenter.model.node.pojo.BasicItemNode;
import cn.sowell.datacenter.model.node.service.BasicItemNodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags="configModule", description="模块管理")
@Controller
@RequestMapping(AdminConstants.URI_MODULE + "/configModule")
public class ConfigModuleController {
    
	@Resource
	ModuleConfigureMediator dBModuleConfigMediator;
	
	@Resource
	BasicItemNodeService basicItemNodeService;
	
	@Resource
	SessionFactory sFactory;
	
	 @ApiOperation(value = "获取模块列表信息", nickname = "list", notes = "获取模块列表", response = ModelAndView.class, tags={ "configModule", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = ModelAndView.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/list",
        method = RequestMethod.POST)
	public ModelAndView list(QueryModuleCriteria criteria, PageInfo pageInfo){
		List<Module> list = dBModuleConfigMediator.queryModules(criteria);
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.addObject("criteria", criteria);
		mv.addObject("pageInfo", pageInfo);
		mv.setViewName(AdminConstants.JSP_MODULE + "/configModule/list.jsp");
		return mv;
	}
	
	 @ApiOperation(value = "跳转到添加页面", nickname = "add", notes = "跳转到添加页面", response = ModelAndView.class, tags={ "configModule", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = ModelAndView.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/add",
        method = RequestMethod.POST)
	public ModelAndView add(){
		List<BasicItemNode> abcList = basicItemNodeService.getAllAbc();
		List<BasicItemNode> childNode = basicItemNodeService.getAttribute(String.valueOf(abcList.get(0).getId()));
		ModelAndView mv = new ModelAndView();
		mv.addObject("abcList", abcList);
		mv.addObject("childNode", childNode);
		mv.setViewName(AdminConstants.JSP_MODULE + "/configModule/add.jsp");
		return mv;
	}
	
	@ResponseBody
	@ApiOperation(value = "根据abc的id，获取abc下面所有的attribute以及属性组下面的attribute", nickname = "childNodeList", notes = "根据abc的id，获取abc下面所有的attribute以及属性组下面的attribute",response = BasicItemNodes.class, tags={ "configModule", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = BasicItemNodes.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/childNodeList",
        method = RequestMethod.POST)
	public ResponseEntity<BasicItemNodes> childNodeList(String parentId){
		 try {
			 List<BasicItemNode> childNodeList = basicItemNodeService.getAttribute(parentId);
				Map<String, Object> map = new HashMap<String, Object>();
			 BasicItemNodes btn = new BasicItemNodes();
			 btn.childNode(childNodeList);
             return new ResponseEntity<BasicItemNodes>(btn, HttpStatus.OK);
         } catch (Exception e) {
             return new ResponseEntity<BasicItemNodes>(HttpStatus.INTERNAL_SERVER_ERROR);
         }
	}
	
	@ResponseBody
	@ApiOperation(value = "创建模块", nickname = "doAdd", notes = "创建模块",response = AjaxPageResponse.class, tags={ "configModule", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "添加成功", response = AjaxPageResponse.class),
        @ApiResponse(code = 401, message = "添加失败") })
    @RequestMapping(value = "/do_add")
	public ResponseEntity<AjaxPageResponse> doAdd(String moduleName, String moduleTitle, Long mappingId, String codeName, String titleName){
		try {
				
			if (codeName == null) {
				codeName="";
			}
			
			if (titleName == null) {
				titleName="";
			}
			
			CreateModuleParam param = new CreateModuleParam(moduleTitle, mappingId);
			param.setModuleName(moduleName);
			param.setCodeName(codeName);
			param.setTitleName(titleName);
			dBModuleConfigMediator.createModule(param);
			return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("添加成功", "configModule_list"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("添加失败"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ResponseBody
	@ApiOperation(value = "移除模块", nickname = "doDelte", notes = "移除模块",response = AjaxPageResponse.class, tags={ "configModule", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "删除成功", response = AjaxPageResponse.class),
        @ApiResponse(code = 401, message = "删除失败") })
    @RequestMapping(value = "/do_delete",
        method = RequestMethod.POST)
	public ResponseEntity<AjaxPageResponse> doDelte(String name){
		try {
			dBModuleConfigMediator.removeModule(name);
			return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.REFRESH_LOCAL("删除成功"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("删除失败"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//禁用启用
	@ResponseBody
	@ApiOperation(value = "禁用启用模块", nickname = "disabled", notes = "禁用启用模块",response = AjaxPageResponse.class, tags={ "configModule", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = AjaxPageResponse.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/disabled",
        method = RequestMethod.POST)
	public ResponseEntity<AjaxPageResponse> disabled(String name, String endisabled){
		try {
			if ("true".equals(endisabled)) {//disabled 为true， 说明为启用状态
				dBModuleConfigMediator.disableModule(name);
			} else {
				dBModuleConfigMediator.enableModule(name);
			}
			return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.REFRESH_LOCAL("操作成功"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("操作失败"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ResponseBody
	 @ApiOperation(value = "跳转到编辑页面", nickname = "edit", notes = "跳转到编辑页面", response = ModelAndView.class, tags={ "configModule", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = ModelAndView.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/edit",
        method = RequestMethod.POST)
	public ModelAndView edit(String name){
		Module module = dBModuleConfigMediator.getModule(name);
		
		BasicItemNode abc = basicItemNodeService.getAbc(module.getMappingId());
		List<BasicItemNode> childNode = basicItemNodeService.getAttribute(String.valueOf(abc.getId()));
		ModelAndView mv = new ModelAndView();
		mv.addObject("module", module);
		mv.addObject("childNode", childNode);
		mv.addObject("abc", abc);
		mv.setViewName(AdminConstants.JSP_MODULE + "/configModule/edit.jsp");
		return mv;
	}
	
	@ResponseBody
	@ApiOperation(value = "编辑模块", nickname = "do_edit", notes = "编辑模块",response = AjaxPageResponse.class, tags={ "configModule", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "编辑成功", response = AjaxPageResponse.class),
        @ApiResponse(code = 401, message = "编辑失败") })
    @RequestMapping(value = "/do_edit",
        method = RequestMethod.POST)
	public ResponseEntity<AjaxPageResponse> do_edit(String moduleName, String moduleTitle, String mappingName, String codeName, String titleName){
		try {
			//dBModuleConfigMediator.updateModulePropertyName(moduleName, codeName, titleName);
			dBModuleConfigMediator.updateModule(moduleName, moduleTitle, codeName, titleName);
		return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("编辑成功", "configModule_list"), HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("编辑失败"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
	}
	
}
	
