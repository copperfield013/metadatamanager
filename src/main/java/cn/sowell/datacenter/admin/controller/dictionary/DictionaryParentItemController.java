package cn.sowell.datacenter.admin.controller.dictionary;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.model.demo.criteria.DemoCriteria;
import cn.sowell.datacenter.model.demo.pojo.PlainDemo;
import cn.sowell.datacenter.model.demo.service.DemoService;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryBasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryParentItemCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryBasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryParentItem;
import cn.sowell.datacenter.model.dictionary.service.DictionaryBasicItemService;
import cn.sowell.datacenter.model.dictionary.service.DictionaryParentItemService;

@Controller
@RequestMapping(AdminConstants.URI_DICTIONARY + "/dictParentItem")
public class DictionaryParentItemController {
	
	@Resource
	DictionaryParentItemService dictionaryParentItemService;
	
	@Resource
	DictionaryBasicItemService dictionaryBasicItemService;
	
	
	Logger logger = Logger.getLogger(DictionaryParentItemController.class);
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(ServletRequestDataBinder binder) {
		System.out.println("执行了InitBinder方法");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("/list")
	public String list(DictionaryParentItemCriteria criteria, Model model, PageInfo pageInfo){
		List<DictionaryParentItem> list = dictionaryParentItemService.queryList(criteria, pageInfo);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("criteria", criteria);
		return AdminConstants.JSP_DICTIONARY + "/dictParentItem/list.jsp";
	}
	
	@RequestMapping("/add")
	public String add(PlainDemo demo){
		return AdminConstants.JSP_DICTIONARY + "/dictParentItem/add.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/do_add")
	public AjaxPageResponse doAdd(DictionaryParentItem	dictionaryParentItem){
		try {
			//手动分配主键， 分配规则待定
			dictionaryParentItem.setId(1234);
			dictionaryParentItemService.create(dictionaryParentItem);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("添加成功", "dictParentItem_list");
		} catch (Exception e) {
			logger.error("添加失败", e);
			return AjaxPageResponse.FAILD("添加失败");
		}
	}

	@RequestMapping("/update/{id}")
	public String update(@PathVariable Integer id, Model model){
		DictionaryParentItem dictParentItem = dictionaryParentItemService.getDictionaryParentItem(id);
		model.addAttribute("dictParentItem", dictParentItem);
		return AdminConstants.JSP_DICTIONARY + "/dictParentItem/update.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/do_update")
	public AjaxPageResponse doUpdate(DictionaryParentItem dictionaryParentItem){
		try {
			dictionaryParentItemService.update(dictionaryParentItem);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "dictParentItem_list");
		} catch (Exception e) {
			logger.error("修改失败", e);
			return AjaxPageResponse.FAILD("修改失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("/do_delete/{id}")
	public AjaxPageResponse doDelte(@PathVariable Integer id){
		try {
			dictionaryParentItemService.delete(id);
			return AjaxPageResponse.REFRESH_LOCAL("删除成功");
		} catch (Exception e) {
			logger.error("删除失败", e);
			return AjaxPageResponse.FAILD("删除失败");
		}
	}

	@ResponseBody
	@RequestMapping("/getDictPitem")
	public String getDictPitem(){
		List<DictionaryParentItem> allList = dictionaryParentItemService.allList();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dictPitem", allList);
		JSONObject jobj = new JSONObject(map);
		return jobj.toString();
	}
}
