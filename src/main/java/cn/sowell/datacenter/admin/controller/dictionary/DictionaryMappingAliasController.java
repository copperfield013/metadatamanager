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
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictBasicItem;
import cn.sowell.datacenter.model.cascadedict.service.CascadedictBasicItemService;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryMappingAliasCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryBasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryMapping;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryMappingAlias;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryParentItem;
import cn.sowell.datacenter.model.dictionary.service.DictionaryBasicItemService;
import cn.sowell.datacenter.model.dictionary.service.DictionaryMappingAliasService;
import cn.sowell.datacenter.model.dictionary.service.DictionaryMappingService;
import cn.sowell.datacenter.model.dictionary.service.DictionaryParentItemService;

@Controller
@RequestMapping(AdminConstants.URI_DICTIONARY + "/dictMappingAlias")
public class DictionaryMappingAliasController {
	
	@Resource
	DictionaryMappingAliasService dictMappingAliasService;
	
	@Resource
	DictionaryMappingService dictionaryMappingService;
	
	@Resource
	CascadedictBasicItemService cascadedictBasicItemService;
	
	@Resource
	DictionaryParentItemService dictParentItemService;
	
	Logger logger = Logger.getLogger(DictionaryMappingAliasController.class);
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(ServletRequestDataBinder binder) {
		System.out.println("执行了InitBinder方法");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("/list")
	public String list(DictionaryMappingAliasCriteria criteria,String btItemParentName, String basicItemName, Model model, PageInfo pageInfo){
		List list = dictMappingAliasService.queryList(criteria, pageInfo);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("criteria", criteria);
		return AdminConstants.JSP_DICTIONARY + "/dictMappingAlias/list.jsp";
	}
	
	@RequestMapping("/update")
	public String update(DictionaryMappingAlias criteria, Model model){
		try {
			CascadedictBasicItem one = cascadedictBasicItemService.getOne(criteria.getBasicItemId());
			DictionaryMappingAlias dictMappingAlias = null;
			if (criteria.getId() != null) {
				dictMappingAlias = dictMappingAliasService.getOne(criteria.getId());
			}
			model.addAttribute("criteria", criteria);
			model.addAttribute("dictMappingAlias", dictMappingAlias);
			model.addAttribute("dictBasicItem", one);
			return AdminConstants.JSP_DICTIONARY + "/dictMappingAlias/update.jsp";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/do_update")
	public AjaxPageResponse doUpdate(DictionaryMappingAlias criteria){
		try {
			if (criteria.getId() !=null) {
				dictMappingAliasService.update(criteria);
			} else {
				dictMappingAliasService.create(criteria);
			}
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "dictMappingAlias_list");
		} catch (Exception e) {
			logger.error("修改失败", e);
			return AjaxPageResponse.FAILD("修改失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("/do_delete/{id}")
	public AjaxPageResponse doDelte(@PathVariable Integer id){
		try {
			dictMappingAliasService.delete(id);
			return AjaxPageResponse.REFRESH_LOCAL("删除成功");
		} catch (Exception e) {
			logger.error("删除失败", e);
			return AjaxPageResponse.FAILD("删除失败");
		}
	}

}
