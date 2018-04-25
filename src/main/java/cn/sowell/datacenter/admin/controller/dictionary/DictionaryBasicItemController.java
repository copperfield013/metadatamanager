package cn.sowell.datacenter.admin.controller.dictionary;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.model.demo.pojo.PlainDemo;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryBasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryBasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryParentItem;
import cn.sowell.datacenter.model.dictionary.service.DictionaryBasicItemService;
import cn.sowell.datacenter.model.dictionary.service.DictionaryParentItemService;

@Controller
@RequestMapping(AdminConstants.URI_DICTIONARY + "/dictBasicItem")
public class DictionaryBasicItemController {
	
	@Resource
	DictionaryBasicItemService dictBasicItemService;
	
	@Resource
	DictionaryParentItemService dictionaryParentItemService;
	
	
	Logger logger = Logger.getLogger(DictionaryBasicItemController.class);
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(ServletRequestDataBinder binder) {
		System.out.println("执行了InitBinder方法");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("/list")
	public String list(DictionaryBasicItemCriteria criteria, Model model, PageInfo pageInfo){
		List<DictionaryBasicItem> list = dictBasicItemService.queryList(criteria, pageInfo);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("criteria", criteria);
		return AdminConstants.JSP_DICTIONARY + "/dictParentItem/detail.jsp";
	}
	
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable Integer id, Model model){
		DictionaryBasicItem dictBasicItem = dictBasicItemService.getDictionaryBasicItem(id);
		model.addAttribute("dictBasicItem", dictBasicItem);
		return AdminConstants.JSP_DICTIONARY + "/dictBasicItem/detail.jsp";
	}
	
	@RequestMapping("/add")
	public String add(Integer parentId, Model model){
		model.addAttribute("parentId", parentId);
		return AdminConstants.JSP_DICTIONARY + "/dictBasicItem/add.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/do_add")
	public AjaxPageResponse doAdd(DictionaryBasicItem creteria){
		try {
			DictionaryParentItem dictParentItem = dictionaryParentItemService.getDictionaryParentItem(creteria.getParentId());
			creteria.setParentName(dictParentItem.getName());
			dictBasicItemService.create(creteria);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("添加成功", "dictBasicItem_add");
		} catch (Exception e) {
			logger.error("添加失败", e);
			return AjaxPageResponse.FAILD("添加失败");
		}
	}

	@RequestMapping("/update/{id}")
	public String update(@PathVariable Integer id, Model model){
		DictionaryBasicItem dictBasicItem = dictBasicItemService.getDictionaryBasicItem(id);
		model.addAttribute("dictBasicItem", dictBasicItem);
		return AdminConstants.JSP_DICTIONARY + "/dictBasicItem/update.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/do_update")
	public AjaxPageResponse doUpdate(DictionaryBasicItem criteria){
		try {
			DictionaryBasicItem dictBasicItem = dictBasicItemService.getDictionaryBasicItem(criteria.getId());
			dictBasicItem.setName(criteria.getName());
			dictBasicItem.setEnName(criteria.getEnName());
			dictBasicItem.setStatus(criteria.getStatus());
			
			dictBasicItemService.update(dictBasicItem);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "demo_list");
		} catch (Exception e) {
			logger.error("修改失败", e);
			return AjaxPageResponse.FAILD("修改失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("/do_delete/{id}")
	public AjaxPageResponse doDelte(@PathVariable Integer id){
		try {
			dictBasicItemService.delete(id);
			return AjaxPageResponse.REFRESH_LOCAL("删除成功");
		} catch (Exception e) {
			logger.error("删除失败", e);
			return AjaxPageResponse.FAILD("删除失败");
		}
	}
	
	@ResponseBody
    @RequestMapping(value="basicItemList",method = RequestMethod.POST, headers="Accept=application/json")
    public JSONArray  basicItemListAjax(DictionaryBasicItemCriteria criteria, Model model, PageInfo pageInfo) {
		List<DictionaryBasicItem> list = dictBasicItemService.queryList(criteria, pageInfo);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("criteria", criteria);
		
		return null;
	}

}
