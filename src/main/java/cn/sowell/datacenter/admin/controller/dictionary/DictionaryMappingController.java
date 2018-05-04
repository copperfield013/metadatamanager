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
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryMappingCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryMapping;
import cn.sowell.datacenter.model.dictionary.service.DictionaryMappingService;

@Controller
@RequestMapping(AdminConstants.URI_DICTIONARY + "/dictMapping")
public class DictionaryMappingController {
	
	@Resource
	DictionaryMappingService dictMappingService;
	
	Logger logger = Logger.getLogger(DictionaryMappingController.class);
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(ServletRequestDataBinder binder) {
		System.out.println("执行了InitBinder方法");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("/list")
	public String list(DictionaryMappingCriteria criteria, Model model, PageInfo pageInfo){
		List<DictionaryMapping> list = dictMappingService.queryList(criteria, pageInfo);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("criteria", criteria);
		return AdminConstants.JSP_DICTIONARY + "/dictMapping/list.jsp";
	}
	
	@RequestMapping("/add")
	public String add(){
		return AdminConstants.JSP_DICTIONARY + "/dictMapping/add.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/do_add")
	public AjaxPageResponse doAdd(DictionaryMapping criteria){
		try {
			dictMappingService.create(criteria);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("添加成功", "dictMapping_list");
		} catch (Exception e) {
			logger.error("添加失败", e);
			return AjaxPageResponse.FAILD("添加失败");
		}
	}

	@RequestMapping("/update/{id}")
	public String update(@PathVariable Integer id, Model model){
		DictionaryMapping dictMapping = dictMappingService.getOne(id);
		model.addAttribute("dictMapping", dictMapping);
		return AdminConstants.JSP_DICTIONARY + "/dictMapping/update.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/do_update")
	public AjaxPageResponse doUpdate(DictionaryMapping criteria){
		try {
			dictMappingService.update(criteria);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "dictMapping_list");
		} catch (Exception e) {
			logger.error("修改失败", e);
			return AjaxPageResponse.FAILD("修改失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("/do_delete/{id}")
	public AjaxPageResponse doDelte(@PathVariable Integer id){
		try {
			dictMappingService.delete(id);
			return AjaxPageResponse.REFRESH_LOCAL("删除成功");
		} catch (Exception e) {
			logger.error("删除失败", e);
			return AjaxPageResponse.FAILD("删除失败");
		}
	}

}
