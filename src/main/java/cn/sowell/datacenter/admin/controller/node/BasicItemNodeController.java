package cn.sowell.datacenter.admin.controller.node;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abc.mapping.node.NodeOpsType;
import com.abc.util.ValueTypeConstant;
import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.ajax.NoticeType;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryBasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.RecordRelationType;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
import cn.sowell.datacenter.model.dictionary.service.DictionaryBasicItemService;
import cn.sowell.datacenter.model.dictionary.service.RecordRelationTypeService;
import cn.sowell.datacenter.model.node.criteria.BasicItemNodeCriteria;
import cn.sowell.datacenter.model.node.pojo.BasicItemNode;
import cn.sowell.datacenter.model.node.service.BasicItemNodeService;

@Controller
@RequestMapping(AdminConstants.URI_NODE + "/basicItemNode")
public class BasicItemNodeController {
	
	@Resource
	BasicItemNodeService basicItemNodeService;
	
	@Resource
	BasicItemService basicItemService;
	
	@Resource
	RecordRelationTypeService recordRelationTypeService;
	
	@Resource
	DictionaryBasicItemService dictBitemServices;
	
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
		return AdminConstants.JSP_NODE + "/basicItemNode/list.jsp";
	}
	
		@ResponseBody
		@RequestMapping("/do_add")
		public AjaxPageResponse doAdd(BasicItemNode criteria){
			try {
				basicItemNodeService.create(criteria);
				
				AjaxPageResponse response = new AjaxPageResponse();
				response.setNotice("操作成功");
				response.setNoticeType(NoticeType.SUC);
				return response;
			} catch (Exception e) {
				logger.error("操作失败", e);
				return AjaxPageResponse.FAILD("操作失败");
			}
		}
	
	@ResponseBody
	@RequestMapping("/do_delete")
	public AjaxPageResponse doDelte(@PathVariable Integer id,String isDelChil){
		try {
			basicItemNodeService.delete(id, isDelChil);
			return AjaxPageResponse.REFRESH_LOCAL("删除成功");
		} catch (Exception e) {
			logger.error("删除失败", e);
			return AjaxPageResponse.FAILD("删除失败");
		}
	}
	
	//ajax 获取实体列表
	@ResponseBody
	@RequestMapping("/entityList")
	public String entityList(){
		BasicItemCriteria criteria = new BasicItemCriteria();
		criteria.setDataType("记录类型");
		criteria.setUsingState(1);
		
		List<BasicItem> list = basicItemService.queryList(criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", list);
		JSONObject jobj = new JSONObject(map);
		return jobj.toString();
	}

	//ajax 获取NodeOpsType
	@ResponseBody
	@RequestMapping("/getNodeOpsType")
	public String getNodeOpsType(){
		List list = new ArrayList();
		
		for (NodeOpsType f : NodeOpsType.values()) {
			list.add(f.getName());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nodeOpsType", list);
		JSONObject jobj = new JSONObject(map);
		return jobj.toString();
	}
		
	//ajax 获取dataType
	@ResponseBody
	@RequestMapping("/getDataType")
	public String getDataType(){
		List<String> list = new ArrayList<String>();
		list.add(ValueTypeConstant.ABCT_NAME_BYTES);
		list.add(ValueTypeConstant.ABCT_NAME_DATE);
		list.add(ValueTypeConstant.ABCT_NAME_DATETIME);
		list.add(ValueTypeConstant.ABCT_NAME_DOUBLE);
		list.add(ValueTypeConstant.ABCT_NAME_FLOAT);
		list.add(ValueTypeConstant.ABCT_NAME_INT);
		list.add(ValueTypeConstant.ABCT_NAME_LONG);
		list.add(ValueTypeConstant.ABCT_NAME_STRING);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dataType", list);
		JSONObject jobj = new JSONObject(map);
		return jobj.toString();
	}
	
	//ajax 根据实体id, 获取本实体下所有的多值属性本身
	@ResponseBody
	@RequestMapping("/getRepeat")
	public String getRepeat(String entityId){
		BasicItemCriteria criteria = new BasicItemCriteria();
		criteria.setParent(entityId);
		criteria.setDataType("重复类型");
		criteria.setUsingState(1);
		
		List<BasicItem> list = basicItemService.queryList(criteria);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("repeat", list);
		JSONObject jobj = new JSONObject(map);
		return jobj.toString();
	}	
	
	//ajax 根据多值属性本身的id,获取多值属性的孩子
	@ResponseBody
	@RequestMapping("/getRepeatChild")
	public String getRepeatChild(String repeatId){
		BasicItem repeat = basicItemService.getBasicItem(repeatId);
		BasicItemCriteria criteria = new BasicItemCriteria();
		criteria.setParent(repeat.getParent() + "_" + repeatId);
		criteria.setUsingState(1);
		
		List<BasicItem> list = basicItemService.queryList(criteria);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("repeatChild", list);
		JSONObject jobj = new JSONObject(map);
		return jobj.toString();
	}
	
	//ajax 根据实体id， 获取当前实体下的所有普通属性， 
	//还有当前实体下的所有二级属性
	@ResponseBody
	@RequestMapping("/getComm")
	public String getComm(String entityId){
		List list = basicItemService.getComm(entityId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("comm", list);
		JSONObject jobj = new JSONObject(map);
		return jobj.toString();
	}	
	
	//获取lab关系名称, 关系下边的lab
	@ResponseBody
	@RequestMapping("/getLabRela")
	public String getLabRela(String leftRecordType, String rightRecordType){
		List<RecordRelationType> list = recordRelationTypeService.getEntityRelaByBitemId(leftRecordType, rightRecordType);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("labRela", list);
		JSONObject jobj = new JSONObject(map);
		return jobj.toString();
	}	
	
	//获取标签名称， 实体下面和属性组下边的标签， 从DictionaryBasicItem
	@ResponseBody
	@RequestMapping("/getCommLab")
	public String getCommLab(){
		List<DictionaryBasicItem> list = dictBitemServices.getDictBasicItemByParent(125);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("commLab", list);
		JSONObject jobj = new JSONObject(map);
		return jobj.toString();
	}	
	
	
	
	
	
}
