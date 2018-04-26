package cn.sowell.datacenter.admin.controller.dictionary;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.ajax.JsonArrayResponse;
import cn.sowell.copframe.dto.ajax.NoticeType;
import cn.sowell.copframe.dto.ajax.ResponseJSON;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.model.demo.criteria.DemoCriteria;
import cn.sowell.datacenter.model.demo.pojo.PlainDemo;
import cn.sowell.datacenter.model.demo.service.DemoService;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;

@Controller
@RequestMapping(AdminConstants.URI_DICTIONARY + "/basicItem")
public class BasicItemController {
	
	@Resource
	BasicItemService basicItemService;
	
	Logger logger = Logger.getLogger(BasicItemController.class);
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(ServletRequestDataBinder binder) {
		System.out.println("执行了InitBinder方法");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("/list")
	public String list(BasicItemCriteria criteria, Model model){
		criteria.setDataType("记录类型");
		List<BasicItem> list = basicItemService.queryList(criteria);
		model.addAttribute("list", list);
		model.addAttribute("criteria", criteria);
		return AdminConstants.JSP_DICTIONARY + "/basicItem/list.jsp";
	}
	
	//ajax 获取实体列表
	@ResponseBody
	@RequestMapping("/entityList")
	public String entityList(){
		BasicItemCriteria criteria = new BasicItemCriteria();
		criteria.setDataType("记录类型");
		List<BasicItem> list = basicItemService.queryList(criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", list);
		JSONObject jobj = new JSONObject(map);
		return jobj.toString();
	}
	
	@ResponseBody
	@RequestMapping("/getDataType")
	public String getDataType(BasicItem	basicItem){
		
		JSONObject jobj = new JSONObject(Constants.DATA_TYPE_MAP);
		
		System.out.println(jobj);
		return jobj.toString();
	}
	
	
	@ResponseBody
	@RequestMapping("/do_add")
	public AjaxPageResponse doAdd(BasicItem	basicItem){
		try {
			String dataType = (String) Constants.DATA_TYPE_MAP.get(basicItem.getDataType());
			basicItem.setDataType(dataType);
			//记录类型
			if ("记录类型".equals(basicItem.getDataType())) {
				basicItem.setDictParentId(0);
			} else if ("分组类型".equals(basicItem.getDataType())) {
				basicItem.setDictParentId(0);
				BasicItem bItem2 = basicItemService.getBasicItem(basicItem.getCode());
				if (bItem2 != null) {
					List<BasicItem> groupChild = basicItemService.getAttrByPidGroupName(bItem2.getParent(), bItem2.getCnName());
					if (!groupChild.isEmpty()) {
						for (BasicItem bt : groupChild) {
							bt.setGroupName(basicItem.getCnName());
							basicItemService.update(bt);
						}
					}
				}
			} else if ("重复类型".equals(basicItem.getDataType())) {
				basicItem.setDictParentId(0);
				basicItem.setTableName("t_" + basicItem.getParent() +"_"+ basicItem.getCode() +"_"+ basicItem.getCode());
			} else {
				// 到这儿来是普通属性  和多值属性下的普通属性
				//它们的区别是父亲不同， 所以先求父亲    默认前端传来的都是父亲的code， 
				BasicItem bItemPanrent = basicItemService.getBasicItem(basicItem.getParent());
				if ("重复类型".equals(bItemPanrent.getDataType())) {//多值属性下的普通属性
					basicItem.setParent(bItemPanrent.getParent() + "_" +bItemPanrent.getCode());
					basicItem.setTableName(bItemPanrent.getTableName());
				} else {//普通属性
					basicItem.setTableName("t_" + basicItem.getParent() + "_" + basicItem.getGroupName());
				}
			}
			
			basicItem.setUsingState(1);
			basicItemService.saveOrUpdate(basicItem);
			
			if ("记录类型".equals(basicItem.getDataType())) {
				return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "basicItem_list");
			} else {
				AjaxPageResponse response = new AjaxPageResponse();
				response.setNotice("操作成功");
				response.setNoticeType(NoticeType.SUC);
				return response;
			}
			
		} catch (Exception e) {
			logger.error("操作失败", e);
			return AjaxPageResponse.FAILD("操作失败");
		}
	}

	@ResponseBody
	@RequestMapping("/getOne")
	public String update(String id, Model model){
		BasicItem basicItem = basicItemService.getBasicItem(id);
		String jsonString = JSONObject.toJSONString(basicItem);
		return jsonString;
	}
	
	@ResponseBody
	@RequestMapping("/do_update")
	public AjaxPageResponse doUpdate(BasicItem basicItem){
		try {
			basicItemService.update(basicItem);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "basicItem_list");
		} catch (Exception e) {
			logger.error("修改失败", e);
			return AjaxPageResponse.FAILD("修改失败");
		}
	}
	
	//根据实体id， 获取实体下面的普通属性， 多值属性 和实体关系
	@ResponseBody
	@RequestMapping("/attrByPid")
	public String getAttrByPid(String parentId) {
		JSONObject attr = basicItemService.getAttrByPid(parentId);
		return attr.toJSONString();
	}
	
	//过期实体or正常  普通属性和多值属性
	@ResponseBody
	@RequestMapping("/saveStatus")
	public AjaxPageResponse savePastDue(String id, String statusStr){
		try {
			
			Integer status = Constants.USING_STATE_MAP.get(statusStr);
			
			BasicItem basicItem = basicItemService.getBasicItem(id);
			basicItemService.savePastDue(basicItem, status);
			
			if ("记录类型".equals(basicItem.getDataType())) {
				return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "basicItem_list");
			} else {
				AjaxPageResponse response = new AjaxPageResponse();
				response.setNotice("操作成功");
				response.setNoticeType(NoticeType.SUC);
				return response;
			}
		} catch (Exception e) {
			logger.error("操作失败", e);
			return AjaxPageResponse.FAILD("操作失败");
		}
	}
	
	
	//创建表
	@ResponseBody
	@RequestMapping("/createTab")
	public AjaxPageResponse createTab(){
		try {
			basicItemService.createTabCol();
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "basicItem_list");
		} catch (Exception e) {
			logger.error("操作失败", e);
			return AjaxPageResponse.FAILD("操作失败");
		}
	}
	

}
