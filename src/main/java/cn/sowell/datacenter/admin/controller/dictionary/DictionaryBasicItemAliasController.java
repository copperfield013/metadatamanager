package cn.sowell.datacenter.admin.controller.dictionary;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.model.demo.pojo.PlainDemo;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryBasicItemAliasCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryBasicItemAlias;
import cn.sowell.datacenter.model.dictionary.service.DictionaryBasicItemAliasService;

@Controller
@RequestMapping(AdminConstants.URI_DICTIONARY + "/dictBasicItemAlias")
public class DictionaryBasicItemAliasController {
	
	@Resource
	DictionaryBasicItemAliasService dictBasicItemAliasService;
	
	Logger logger = Logger.getLogger(DictionaryBasicItemAliasController.class);
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(ServletRequestDataBinder binder) {
		System.out.println("执行了InitBinder方法");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("/list")
	public String list(DictionaryBasicItemAliasCriteria criteria, Model model, PageInfo pageInfo){
		List<DictionaryBasicItemAlias> list = dictBasicItemAliasService.queryList(criteria, pageInfo);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("criteria", criteria);
		return AdminConstants.JSP_DICTIONARY + "/dictBasicItemAlias/list.jsp";
	}
	
	@RequestMapping("/add")
	public String add(String basicItemId, Model model){
		model.addAttribute("basicItemId", basicItemId);
		return AdminConstants.JSP_DICTIONARY + "/dictBasicItemAlias/add.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/do_add")
	public AjaxPageResponse doAdd(DictionaryBasicItemAlias criteria){
		try {
			dictBasicItemAliasService.create(criteria);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("添加成功", "demo_list");
		} catch (Exception e) {
			logger.error("添加失败", e);
			return AjaxPageResponse.FAILD("添加失败");
		}
	}

	@RequestMapping("/update/{id}")
	public String update(@PathVariable Long id, Model model){
		DictionaryBasicItemAlias dictBasicItemAlias = dictBasicItemAliasService.getDictionaryBasicItemAlias(id);
		model.addAttribute("dictBasicItemAlias", dictBasicItemAlias);
		return AdminConstants.JSP_DICTIONARY + "/dictBasicItemAlias/update.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/do_update")
	public AjaxPageResponse doUpdate(DictionaryBasicItemAlias criteria){
		try {
			
			System.out.println();
			DictionaryBasicItemAlias dictBasicItemAlias = dictBasicItemAliasService.getDictionaryBasicItemAlias(criteria.getId());
			dictBasicItemAlias.setAliasName(criteria.getAliasName());
			dictBasicItemAlias.setPriorityLevel(criteria.getPriorityLevel());
			dictBasicItemAliasService.update(dictBasicItemAlias);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "demo_list");
		} catch (Exception e) {
			logger.error("修改失败", e);
			return AjaxPageResponse.FAILD("修改失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("/do_delete/{id}")
	public AjaxPageResponse doDelte(@PathVariable Long id){
		try {
			dictBasicItemAliasService.delete(id);
			return AjaxPageResponse.REFRESH_LOCAL("删除成功");
		} catch (Exception e) {
			logger.error("删除失败", e);
			return AjaxPageResponse.FAILD("删除失败");
		}
	}

}
