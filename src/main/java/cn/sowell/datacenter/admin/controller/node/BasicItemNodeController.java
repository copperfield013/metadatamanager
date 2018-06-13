package cn.sowell.datacenter.admin.controller.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abc.mapping.node.NodeOpsType;
import com.abc.util.ValueTypeConstant;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.ajax.NoticeType;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.admin.controller.node.api.BasicItemNodes;
import cn.sowell.datacenter.admin.controller.node.api.BasicItems;
import cn.sowell.datacenter.admin.controller.node.api.DictionaryBasicItems;
import cn.sowell.datacenter.admin.controller.node.api.RecordRelationTypes;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-06-11T07:53:08.778Z")

@Api(tags="configurationFiles", description="配置文件")
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
	
	@ApiIgnore
	@ApiOperation(value="获取实体列表信息", notes="获取实体列表")
	@RequestMapping("/list")
	public String list(BasicItemNodeCriteria criteria, Model model, PageInfo pageInfo) {
		List<BasicItemNode> list = basicItemNodeService.queryList(criteria, pageInfo);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("criteria", criteria);
		return AdminConstants.JSP_NODE + "/basicItemNode/list.jsp";
	}

	@ApiIgnore
	@ResponseBody
	@RequestMapping("/saveOrUpdate")
	public String saveOrUpdate(BasicItemNode basicItemNode) {
		boolean check = basicItemNodeService.check(basicItemNode.getName(), basicItemNode.getParentId());
		if (check) {//重复了
			return "{\"state\": \"fail\"}";
		} else {
			basicItemNodeService.saveOrUpdate(basicItemNode);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("node", basicItemNode);
			map.put("state", "success");
			JSONObject jobj = new JSONObject(map);
			return jobj.toString();
		}
	}

	@ApiIgnore
	@ResponseBody
	@RequestMapping("/do_delete")
	public AjaxPageResponse doDelte(Integer id, boolean isDelChil) {
		try {
			basicItemNodeService.delete(id, isDelChil);
			return AjaxPageResponse.REFRESH_LOCAL("删除成功");
		} catch (Exception e) {
			return AjaxPageResponse.FAILD("删除失败");
		}
	}

	/*public String entityList() {
		BasicItemCriteria criteria = new BasicItemCriteria();
		criteria.setDataType("记录类型");
		criteria.setUsingState(1);
		List<BasicItem> list = basicItemService.queryList(criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", list);
		JSONObject jobj = new JSONObject(map);
		return jobj.toString();
	}*/

	// ajax 获取NodeOpsType
	@ApiIgnore
	@ApiOperation(notes = "getNodeOpsType", httpMethod = "POST", value = "添加一个新的群组")
	@ResponseBody
	@RequestMapping("/getNodeOpsType")
	public String getNodeOpsType() {
		List list = new ArrayList();

		for (NodeOpsType f : NodeOpsType.values()) {
			list.add(f.getName());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nodeOpsType", list);
		JSONObject jobj = new JSONObject(map);
		return jobj.toString();
	}

	// ajax 获取dataType
	@ApiIgnore
	@ResponseBody
	@RequestMapping("/getDataType")
	public String getDataType() {
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

	// ajax 根据实体id, 获取本实体下所有的多值属性本身
	@ApiIgnore
	@ResponseBody
	@RequestMapping("/getRepeat")
	public String getRepeat(String entityId) {
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

	// ajax 根据多值属性本身的id,获取多值属性的孩子
	@ApiIgnore
	@ResponseBody
	@RequestMapping("/getRepeatChild")
	public String getRepeatChild(String repeatId) {
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

	// ajax 根据实体id， 获取当前实体下的所有普通属性，
	// 还有当前实体下的所有二级属性
	@ApiIgnore
	@ResponseBody
	@RequestMapping("/getComm")
	public String getComm(String entityId) {
		List list = basicItemService.getComm(entityId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("comm", list);
		JSONObject jobj = new JSONObject(map);
		return jobj.toString();
	}

	// 获取lab关系名称, 关系下边的lab
	@ResponseBody
	@ApiOperation(value = "获取lab关系名称, 关系下边的lab", nickname = "getLabRela", notes = "关系名称", response = RecordRelationTypes.class, tags={ "configurationFiles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = RecordRelationTypes.class),
        @ApiResponse(code = 401, message = "操作失败", response = String.class) })
    @RequestMapping(value = "/getLabRela",
        method = RequestMethod.POST)
	public ResponseEntity<RecordRelationTypes> getLabRela(String leftRecordType, String rightRecordType) {
		List<RecordRelationType> list = recordRelationTypeService.getEntityRelaByBitemId(leftRecordType,
				rightRecordType);
		RecordRelationTypes recordRelTypes = new RecordRelationTypes();
		recordRelTypes.labRela(list);
		return new ResponseEntity<RecordRelationTypes>(recordRelTypes, HttpStatus.OK);
	}

	// 获取标签名称， 实体下面和属性组下边的标签， 从DictionaryBasicItem
	@ResponseBody
	@ApiOperation(value = "获取标签名称,实体下面和属性组下边的标签", nickname = "getCommLab", notes = "标签名称", response = DictionaryBasicItems.class, tags={ "configurationFiles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = DictionaryBasicItems.class),
        @ApiResponse(code = 400, message = "操作失败", response = String.class) })
    @RequestMapping(value = "/getCommLab",
        method = RequestMethod.POST)
	public ResponseEntity<DictionaryBasicItems> getCommLab() {
		List<DictionaryBasicItem> list = dictBitemServices.getDictBasicItemByParent(125);
		DictionaryBasicItems dictBasicItems = new DictionaryBasicItems();
		dictBasicItems.commLab(list);
		return new ResponseEntity<DictionaryBasicItems>(dictBasicItems, HttpStatus.OK);
	}

	//TODO.....
	@ApiIgnore
	@RequestMapping("/operate")
	public String operate(Model model) {
		BasicItemCriteria criteria = new BasicItemCriteria();
		criteria.setDataType("记录类型");
		criteria.setUsingState(1);
		List<BasicItem> list = basicItemService.queryList(criteria);
		model.addAttribute("list", list);
		return AdminConstants.JSP_NODE + "/basicItemNode/operate.jsp";
	}
	
	//TODO.....
	@ApiIgnore
	@RequestMapping("/edit")
	public String edit(String nodeId, Model model) {
		BasicItemNode btNode = basicItemNodeService.getOne(Integer.parseInt(nodeId));
		BasicItem basicItem = basicItemService.getBasicItem(btNode.getAbcattrCode());
		
		model.addAttribute("btNode", btNode);
		model.addAttribute("basicItem", basicItem);
		return AdminConstants.JSP_NODE + "/basicItemNode/edit.jsp";
	}
	
	@ResponseBody
	@ApiOperation(value = "通过父节点id， 获取孩子", nickname = "getChildNode", notes = "通过父节点id， 获取孩子", response = BasicItemNodes.class, tags={ "configurationFiles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = BasicItemNodes.class),
        @ApiResponse(code = 400, message = "操作失败", response = String.class) })
    @RequestMapping(value = "/getChildNode",
        method = RequestMethod.POST)
	public ResponseEntity<BasicItemNodes> getChildNode(String nodeId) {
		List<BasicItemNode> list = basicItemNodeService.getChildNode(nodeId);
		BasicItemNodes btItemNodes = new BasicItemNodes();
		btItemNodes.childNode(list);
		return new ResponseEntity<BasicItemNodes>(btItemNodes, HttpStatus.OK);
	}

	@ResponseBody
	@ApiOperation(value = "节点排序", nickname = "nodeSort", notes = "节点排序", response = String.class, tags={ "configurationFiles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = String.class),
        @ApiResponse(code = 400, message = "操作失败", response = String.class) })
    @RequestMapping(value = "/nodeSort",
        method = RequestMethod.POST)
   public ResponseEntity<String> nodeSort(String currentId, String beforeId, String afterId){
		BasicItemNode current = basicItemNodeService.getOne(Integer.parseInt(currentId));
		 try {
			 basicItemNodeService.nodeSort(current, beforeId, afterId);
		} catch (Exception e) {
			basicItemNodeService.excuExtend(current.getParentId());
		}
		 
         return new ResponseEntity<String>("1", HttpStatus.OK);
	}

	@ResponseBody
	@ApiOperation(value = "获取实体列表", nickname = "entityList", notes = "获取实体列表", response = BasicItems.class, tags={ "configurationFiles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = BasicItems.class),
        @ApiResponse(code = 400, message = "操作失败", response = String.class) })
    @RequestMapping(value = "/entityList",
        method = {RequestMethod.POST})
	public ResponseEntity<BasicItems> entityList() {
		BasicItemCriteria criteria = new BasicItemCriteria();
		criteria.setDataType("记录类型");
		criteria.setUsingState(1);
		List<BasicItem> list = basicItemService.queryList(criteria);
		BasicItems btItems = new BasicItems();
		btItems.entity(list);
		return new ResponseEntity<BasicItems>(btItems, HttpStatus.OK);
	}
		
}
