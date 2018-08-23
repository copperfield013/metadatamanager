package cn.sowell.datacenter.admin.controller.cascadedict;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.model.cascadedict.criteria.CascadedictBasicItemCriteria;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictBasicItem;
import cn.sowell.datacenter.model.cascadedict.service.CascadedictBasicItemService;

@Controller
@RequestMapping(AdminConstants.URI_CASCADEDICT + "/cascadedictBasicItem")
public class CascadedictBasicItemController {
	
	@Resource
	CascadedictBasicItemService cascadedictBasicItemService;
	
	Logger logger = Logger.getLogger(CascadedictBasicItemController.class);
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(ServletRequestDataBinder binder) {
		System.out.println("执行了InitBinder方法");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("/list")
	public String list(CascadedictBasicItemCriteria criteria, Model model, PageInfo pageInfo){
		List<CascadedictBasicItem> list;
		try {
			list = cascadedictBasicItemService.queryList(criteria, pageInfo);
			model.addAttribute("list", list);
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("criteria", criteria);
			return AdminConstants.JSP_CASCADEDICT + "/cascadedictBasicItem/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 根据父id, 获取当前父亲的孩子
	 * @param id
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getChildByParentId")
	public String getChildByParentId(Integer parentId, Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			List<CascadedictBasicItem> childList = cascadedictBasicItemService.getChildByParentId(parentId);
			map.put("code", 200);
			map.put("msg", "success");
			map.put("childList", childList);
			model.addAttribute("childList", childList);
			return jobj.toString();
		} catch (Exception e) {
			logger.error("添加失败", e);
			map.put("code", 400);
			map.put("msg", "error");
			return jobj.toString();
		}
	}
	
	/**
	 * 根据id获取当前对象信息
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/getOne")
	public String getOne(Integer id, Model model){
		try {
			CascadedictBasicItem one = cascadedictBasicItemService.getOne(id);
			
			List<CascadedictBasicItem> list = new ArrayList<CascadedictBasicItem>();
			String casPid = one.getCasPid();
			String[] str = casPid.split("\\.");
			for (int i=0;i<str.length-1; i++) {
				CascadedictBasicItem one2 = cascadedictBasicItemService.getOne(Integer.parseInt(str[i+1]));
				list.add(one2);
			}
			model.addAttribute("basicItem", one);
			model.addAttribute("parentList", list);
			return AdminConstants.JSP_CASCADEDICT + "/cascadedictBasicItem/childmanage_tree.jsp";
		} catch (Exception e) {
			logger.error("失败", e);
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/add")
	public String add(Integer parentId, Model model){
		model.addAttribute("parentId", parentId);
		return AdminConstants.JSP_CASCADEDICT + "/cascadedictBasicItem/add.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/do_add")
	public AjaxPageResponse doAdd(CascadedictBasicItem creteria){
		try {
			cascadedictBasicItemService.create(creteria);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("添加成功", "cascadedictBasicItem_list");
		} catch (Exception e) {
			logger.error("添加失败", e);
			return AjaxPageResponse.FAILD("添加失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("/saveOrUpdate")
	public String saveOrUpdate(CascadedictBasicItem creteria){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			if (creteria.getOrder() == null) {
				creteria.setOrder(1);
			}
			cascadedictBasicItemService.saveOrUpdate(creteria);
			map.put("code", 200);
			map.put("msg", "success");
			map.put("creteria", creteria);
			return jobj.toString();
		} catch (Exception e) {
			logger.error("添加失败", e);
			map.put("code", 400);
			map.put("msg", "error");
			return jobj.toString();
		}
	}

	@RequestMapping("/update/{id}")
	public String update(@PathVariable Integer id, Model model){
		try {
			CascadedictBasicItem dictBasicItem = cascadedictBasicItemService.getOne(id);
			model.addAttribute("dictBasicItem", dictBasicItem);
			return AdminConstants.JSP_CASCADEDICT + "/cascadedictBasicItem/update.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/do_update")
	public AjaxPageResponse doUpdate(CascadedictBasicItem criteria){
		try {
			cascadedictBasicItemService.update(criteria);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "cascadedictBasicItem_list");
		} catch (Exception e) {
			logger.error("修改失败", e);
			return AjaxPageResponse.FAILD("修改失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("/do_delete/{id}")
	public AjaxPageResponse doDelte(@PathVariable Integer id){
		try {
			cascadedictBasicItemService.delete(id);
			return AjaxPageResponse.REFRESH_LOCAL("删除成功");
		}catch(ConstraintViolationException e) {
			logger.error("删除失败", e);
			return AjaxPageResponse.FAILD("请先删除孩子");
		} catch (Exception e) {
			logger.error("删除失败", e);
			return AjaxPageResponse.FAILD("删除失败");
		}
	}
	
	
	@ResponseBody
	@RequestMapping("/doDelte/{id}")
	public String do_delte(@PathVariable Integer id){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			cascadedictBasicItemService.delete(id);
			map.put("code", 200);
			map.put("msg", "success");
			return jobj.toString();
		}catch(ConstraintViolationException e) {
			logger.error("删除失败", e);
			map.put("code", 400);
			map.put("msg", "请先删除孩子");
			return jobj.toString();
		} catch (Exception e) {
			logger.error("删除失败", e);
			map.put("code", 400);
			map.put("msg", "删除失败");
			return jobj.toString();
		}
	}

}
