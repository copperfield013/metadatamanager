package cn.sowell.datacenter.admin.controller.node;

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
import cn.sowell.datacenter.model.node.criteria.BasicItemNodeCriteria;
import cn.sowell.datacenter.model.node.pojo.BasicItemNode;
import cn.sowell.datacenter.model.node.service.BasicItemNodeService;

@Controller
@RequestMapping(AdminConstants.URI_DICTIONARY + "/basicItemNode")
public class BasicItemNodeController {
	
	@Resource
	BasicItemNodeService basicItemNodeService;
	
	Logger logger = Logger.getLogger(BasicItemNodeController.class);
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(ServletRequestDataBinder binder) {
		System.out.println("执行了InitBinder方法");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("/list")
	public String list(BasicItemNodeCriteria criteria, Model model, PageInfo pageInfo){
		List<BasicItemNode> list = basicItemNodeService.queryList(criteria, pageInfo);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("criteria", criteria);
		return AdminConstants.JSP_DICTIONARY + "/basicItemNode/list.jsp";
	}
	
	@RequestMapping("/add")
	public String add(String basicItemId, Model model){
		
		
		model.addAttribute("basicItemId", basicItemId);
		return AdminConstants.JSP_DICTIONARY + "/basicItemNode/add.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/do_add")
	public AjaxPageResponse doAdd(BasicItemNode criteria){
		try {
			basicItemNodeService.create(criteria);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("添加成功", "basicItemNode_list");
		} catch (Exception e) {
			logger.error("添加失败", e);
			return AjaxPageResponse.FAILD("添加失败");
		}
	}

	@RequestMapping("/update/{id}")
	public String update(@PathVariable Integer id, Model model){
		return AdminConstants.JSP_DICTIONARY + "/basicItemNode/update.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/do_update")
	public AjaxPageResponse doUpdate(BasicItemNode criteria){
		try {
			BasicItemNode basicItemNode = basicItemNodeService.getOne(criteria.getId());
			basicItemNodeService.update(basicItemNode);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "basicItemNode_list");
		} catch (Exception e) {
			logger.error("修改失败", e);
			return AjaxPageResponse.FAILD("修改失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("/do_delete/{id}")
	public AjaxPageResponse doDelte(@PathVariable Integer id){
		try {
			basicItemNodeService.delete(id);
			return AjaxPageResponse.REFRESH_LOCAL("删除成功");
		} catch (Exception e) {
			logger.error("删除失败", e);
			return AjaxPageResponse.FAILD("删除失败");
		}
	}

}
