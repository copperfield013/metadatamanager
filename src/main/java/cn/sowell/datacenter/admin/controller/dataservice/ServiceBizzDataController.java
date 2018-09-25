package cn.sowell.datacenter.admin.controller.dataservice;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.ajax.NoticeType;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.model.dataservice.criteria.ServiceBizzDataCriteria;
import cn.sowell.datacenter.model.dataservice.pojo.ServiceBizzData;
import cn.sowell.datacenter.model.dataservice.service.ServiceBizzDataService;
import cn.sowell.datacenter.utils.WebServiceUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags="serviceBizzData", description="数据服务")
@Controller
@RequestMapping(AdminConstants.URI_DATASERVICE + "/serviceBizzData")
public class ServiceBizzDataController {
    
	@Resource
	ServiceBizzDataService sBizzDataService;
	
	 @ApiOperation(value = "获取数据列表", nickname = "list", notes = "获取数据列表", response = ModelAndView.class, tags={ "serviceBizzData", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = ModelAndView.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/list",
        method = RequestMethod.POST)
	public ModelAndView list(ServiceBizzDataCriteria criteria, PageInfo pageInfo){
		 List<ServiceBizzData> list = sBizzDataService.queryList(criteria, pageInfo);
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.addObject("criteria", criteria);
		mv.addObject("pageInfo", pageInfo);
		mv.setViewName(AdminConstants.JSP_DATASERVICE + "/serviceBizzData/list.jsp");
		return mv;
	}
	
	 @ApiOperation(value = "跳转到添加页面", nickname = "add", notes = "跳转到添加页面", response = ModelAndView.class, tags={ "serviceBizzData", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = ModelAndView.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/add",
        method = RequestMethod.POST)
	public ModelAndView add(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(AdminConstants.JSP_DATASERVICE + "/serviceBizzData/add.jsp");
		return mv;
	}
	 
	@ResponseBody
	@ApiOperation(value = "创建数据服务", nickname = "doAdd", notes = "创建数据服务",response = AjaxPageResponse.class, tags={ "serviceBizzData", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "添加成功", response = AjaxPageResponse.class),
        @ApiResponse(code = 401, message = "添加失败") })
    @RequestMapping(value = "/do_add",
        method = RequestMethod.POST)
	public ResponseEntity<AjaxPageResponse> doAdd(ServiceBizzData sd){
		try {
			sBizzDataService.insert(sd);
			return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("添加成功", "serviceBizzData_list"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("添加失败"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ResponseBody
	@ApiOperation(value = "移除数据服务", nickname = "doDelte", notes = "移除数据服务",response = AjaxPageResponse.class, tags={ "serviceBizzData", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "删除成功", response = AjaxPageResponse.class),
        @ApiResponse(code = 401, message = "删除失败") })
    @RequestMapping(value = "/do_delete",
        method = RequestMethod.POST)
	public ResponseEntity<AjaxPageResponse> doDelte(Integer id){
		try {
			sBizzDataService.delete(id);
			return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.REFRESH_LOCAL("删除成功"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("删除失败"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//禁用启用
	@ResponseBody
	@ApiOperation(value = "测试数据服务", nickname = "testService", notes = "测试数据服务",response = AjaxPageResponse.class, tags={ "serviceBizzData", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = AjaxPageResponse.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/testService",
        method = RequestMethod.POST)
	public ResponseEntity<AjaxPageResponse> testService(Integer id){
		try {
			ServiceBizzData serviceBizzData = sBizzDataService.getOne(id);
			String params = null;
			String method = null;
			String url = buildWSURL(serviceBizzData);
			String wsdlResult = WebServiceUtil.getWsdlResult(url);
			
			if ("true".equals(wsdlResult)) {
				serviceBizzData.setState("1");
				sBizzDataService.update(serviceBizzData);
				return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.REFRESH_LOCAL("测试通过"), HttpStatus.OK);
			} else {
				serviceBizzData.setState("2");
				sBizzDataService.update(serviceBizzData);
				return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.REFRESH_LOCAL_BY_TYPE("测试不通过", NoticeType.INFO), HttpStatus.OK);
			}
		
		} catch (Exception e) {
			return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("操作失败"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	protected String buildWSURL(ServiceBizzData serviceBizzData) {
		return "http://"+serviceBizzData.getIp()+":"+serviceBizzData.getPort()+"/"+serviceBizzData.getName()+"/services/modelReLoadService?wsdl";
	}
	
	@ResponseBody
	@ApiOperation(value = "刷新应用配置", nickname = "refreshERXmlDom", notes = "刷新应用配置",response = AjaxPageResponse.class, tags={ "serviceBizzData", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = AjaxPageResponse.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/refreshERXmlDom",
        method = RequestMethod.POST)
	public ResponseEntity<AjaxPageResponse> refreshERXmlDom(Integer id){
		try {
			ServiceBizzData serviceBizzData = sBizzDataService.getOne(id);
			String params = null;
			String method = "loadERXmlDomFromDB";
			String url = buildWSURL(serviceBizzData);
			String wsdlResult = WebServiceUtil.getWsdlResult(url, method, params);
			String dataUrl = "http://"+serviceBizzData.getIp()+":"+serviceBizzData.getPort()+"/"+serviceBizzData.getName()+"/services/configReloadService?wsdl";
			String dataResult = WebServiceUtil.getWsdlResult(dataUrl, "syncModule", null);
			//String syncFieldResult = WebServiceUtil.getWsdlResult(dataUrl, "syncField", null);
			
			if ("true".equals(wsdlResult)) {
				return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.REFRESH_LOCAL("刷新成功"), HttpStatus.OK);
			} else {
				return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("刷新失败"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("刷新失败"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	 @ApiOperation(value = "跳转到编辑页面", nickname = "edit", notes = "跳转到编辑页面", response = ModelAndView.class, tags={ "serviceBizzData", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = ModelAndView.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/edit",
        method = RequestMethod.POST)
	public ModelAndView edit(Integer id){
		ServiceBizzData serviceBizzData = sBizzDataService.getOne(id);
		ModelAndView mv = new ModelAndView();
		mv.addObject("serviceBizzData", serviceBizzData);
		mv.setViewName(AdminConstants.JSP_DATASERVICE + "/serviceBizzData/edit.jsp");
		return mv;
	}
	
	@ResponseBody
	@ApiOperation(value = "编辑", nickname = "do_edit", notes = "编辑",response = AjaxPageResponse.class, tags={ "serviceBizzData", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "编辑成功", response = AjaxPageResponse.class),
        @ApiResponse(code = 401, message = "编辑失败") })
    @RequestMapping(value = "/do_edit",
        method = RequestMethod.POST)
	public ResponseEntity<AjaxPageResponse> do_edit(ServiceBizzData sd){
		try {
			sBizzDataService.update(sd);
		return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("编辑成功", "serviceBizzData_list"), HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("编辑失败"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
	}
	
}
	
