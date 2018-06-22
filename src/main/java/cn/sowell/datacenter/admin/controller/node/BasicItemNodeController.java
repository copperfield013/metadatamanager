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
import org.springframework.web.servlet.ModelAndView;

import com.abc.mapping.node.NodeOpsType;
import com.abc.mapping.node.NodeType;
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
import cn.sowell.datacenter.admin.controller.node.api.InlineResponse200;
import cn.sowell.datacenter.admin.controller.node.api.InlineResponse2001;
import cn.sowell.datacenter.admin.controller.node.api.InlineResponse2002;
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
	
	 @ApiOperation(value = "获取实体列表信息", nickname = "list", notes = "获取实体列表", response = ModelAndView.class, tags={ "configurationFiles", })
	    @ApiResponses(value = { 
	        @ApiResponse(code = 200, message = "OK", response = ModelAndView.class),
	        @ApiResponse(code = 401, message = "操作失败") })
	    @RequestMapping(value = "/list",
	        method = RequestMethod.POST)
	public ModelAndView list(BasicItemNodeCriteria criteria, PageInfo pageInfo) {
		List<BasicItemNode> list = basicItemNodeService.queryList(criteria, pageInfo);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("list", list);
		modelAndView.addObject("pageInfo", pageInfo);
		modelAndView.addObject("criteria", criteria);
		modelAndView.setViewName(AdminConstants.JSP_NODE + "/basicItemNode/list.jsp");
		return modelAndView;
	}

	@ResponseBody
	 @ApiOperation(value = "保存节点", nickname = "saveOrUpdate", notes = "保存节点信息", response = InlineResponse200.class, tags={ "configurationFiles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = InlineResponse200.class),
        @ApiResponse(code = 401, message = "操作失败", response = String.class) })
    @RequestMapping(value = "/saveOrUpdate",
        method = RequestMethod.POST)
	public ResponseEntity<InlineResponse200> saveOrUpdate(BasicItemNode basicItemNode) {
		 try {
			 
			 InlineResponse200 inline = new InlineResponse200();
			 //判断当前关系下只能有一个标签和一个实体
			 String relaNodeChil = "";
			 if (basicItemNode.getParentId() != null) {
				 BasicItemNode one = basicItemNodeService.getOne(Integer.parseInt(basicItemNode.getParentId()));
				 if (NodeType.RELATION.equals(NodeType.getNodeType(one.getType()))) {//当前实体的父亲是关系节点
					 if (basicItemNode.getId() == null) {
						 relaNodeChil = basicItemNodeService.getRelaNodeChil(basicItemNode.getParentId(), "", basicItemNode.getType());
					 } else {
						 relaNodeChil = basicItemNodeService.getRelaNodeChil(basicItemNode.getParentId(), String.valueOf(basicItemNode.getId()), basicItemNode.getType());
					 }
				 }
			 } 
			 
			//判断当前父节点下有没有重复的名字
			 boolean check = basicItemNodeService.check(basicItemNode);
			 
			 if ("true".equals(relaNodeChil)) {
				 inline.setState("error");
			 } else if (check) {//重复了
					inline.setState("fail");
			} else {
				basicItemNodeService.saveOrUpdate(basicItemNode);
				inline.setNode(basicItemNode);
				inline.setState("success");
			}
			
             return new ResponseEntity<InlineResponse200>(inline, HttpStatus.OK);
         } catch (Exception e) {
             return new ResponseEntity<InlineResponse200>(HttpStatus.INTERNAL_SERVER_ERROR);
         }
	}

	@ResponseBody
	@ApiOperation(value = "删除节点", nickname = "doDelte", notes = "doDelte", tags={ "configurationFiles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/do_delete",
        method = RequestMethod.POST)
	public ResponseEntity<AjaxPageResponse> doDelte(Integer id, boolean isDelChil) {
		try {
			basicItemNodeService.delete(id, isDelChil);
			 return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.REFRESH_LOCAL("删除成功"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("删除失败"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ajax 获取NodeOpsType
	@ResponseBody
	@ApiOperation(value = "获取NodeOpsType", nickname = "getNodeOpsType", notes = "getNodeOpsType", response = Object.class, tags={ "configurationFiles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Object.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/getNodeOpsType",
        method = RequestMethod.POST)
	public ResponseEntity<Object> getNodeOpsType() {
		 try {
			 	List list = new ArrayList();
				for (NodeOpsType f : NodeOpsType.values()) {
					list.add(f.getName());
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("nodeOpsType", list);
				JSONObject jobj = new JSONObject(map);
			 return new ResponseEntity<Object>(jobj, HttpStatus.OK);
         } catch (Exception e) {
             return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
         }
	}

	// ajax 获取dataType
	@ResponseBody
	@ApiOperation(value = "获取dataType", nickname = "getDataType", notes = "获取dataType", response = Object.class, tags={ "configurationFiles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Object.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/getDataType",
        method = RequestMethod.POST)
	public ResponseEntity<Object> getDataType() {
		try {
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
			return new ResponseEntity<Object>(jobj, HttpStatus.OK);
        } catch (Exception e) {                
            return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

	// ajax 根据实体id, 获取本实体下所有的多值属性本身
	@ResponseBody
	@ApiOperation(value = "根据实体id, 获取本实体下所有的多值属性本身", nickname = "getRepeat", notes = "根据实体id, 获取本实体下所有的多值属性本身", response = InlineResponse2002.class, tags={ "configurationFiles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = InlineResponse2002.class),
        @ApiResponse(code = 401, message = "操作失败")})
    @RequestMapping(value = "/getRepeat",
        method = RequestMethod.POST)
	public ResponseEntity<InlineResponse2002> getRepeat(String entityId) {
		try {
			BasicItemCriteria criteria = new BasicItemCriteria();
			criteria.setParent(entityId);
			criteria.setDataType("重复类型");
			criteria.setUsingState(1);

			List<BasicItem> list = basicItemService.queryList(criteria);
			InlineResponse2002 inline = new InlineResponse2002();
			inline.repeat(list);
			return new ResponseEntity<InlineResponse2002>(inline, HttpStatus.OK);
        } catch (Exception e) {                
            return new ResponseEntity<InlineResponse2002>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

	@ResponseBody
	@ApiOperation(value = "根据多值属性本身的id,获取多值属性的孩子", nickname = "getRepeatChild", notes = "多值属性本身的id, 获取多值属性的孩子", response = InlineResponse2001.class, tags={ "configurationFiles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = InlineResponse2001.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/getRepeatChild",
        method = RequestMethod.POST)
	public ResponseEntity<InlineResponse2001> getRepeatChild(String repeatId) {
        try {
        	BasicItem repeat = basicItemService.getBasicItem(repeatId);
    		BasicItemCriteria criteria = new BasicItemCriteria();
    		criteria.setParent(repeat.getParent() + "_" + repeatId);
    		criteria.setUsingState(1);
    		List<BasicItem> list = basicItemService.queryList(criteria);
    		InlineResponse2001 inline = new InlineResponse2001();
    		inline.repeatChild(list);
            return new ResponseEntity<InlineResponse2001>(inline, HttpStatus.OK);
        } catch (Exception e) {                
            return new ResponseEntity<InlineResponse2001>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

	// ajax 根据实体id， 获取当前实体下的所有普通属性，
	// 还有当前实体下的所有二级属性
	@ResponseBody
	 @ApiOperation(value = "根据实体id， 获取当前实体下的所有普通属性和二级属性", nickname = "getComm", notes = "根据实体id， 获取当前实体下的所有普通属性和二级属性", response = Object.class, tags={ "configurationFiles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = Object.class),
        @ApiResponse(code = 400, message = "操作失败", response = String.class) })
    @RequestMapping(value = "/getComm", method = RequestMethod.POST)
	public ResponseEntity<Object> getComm(String entityId) {
		try {
			List list = basicItemService.getComm(entityId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("comm", list);
			JSONObject jobj = new JSONObject(map);
			return new ResponseEntity<Object>(jobj, HttpStatus.OK);
        } catch (Exception e) {                
            return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
		try {
			List<RecordRelationType> list = recordRelationTypeService.getEntityRelaByBitemId(leftRecordType,
					rightRecordType);
			RecordRelationTypes recordRelTypes = new RecordRelationTypes();
			recordRelTypes.labRela(list);
			return new ResponseEntity<RecordRelationTypes>(recordRelTypes, HttpStatus.OK);
        } catch (Exception e) {                
            return new ResponseEntity<RecordRelationTypes>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
		try {
			List<DictionaryBasicItem> list = dictBitemServices.getDictBasicItemByParent(125);
			DictionaryBasicItems dictBasicItems = new DictionaryBasicItems();
			dictBasicItems.commLab(list);
			return new ResponseEntity<DictionaryBasicItems>(dictBasicItems, HttpStatus.OK);
        } catch (Exception e) {                
            return new ResponseEntity<DictionaryBasicItems>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

	@ApiOperation(value = "创建一个配置文件入口", nickname = "operate", notes = "创建一个配置文件入口", response = ModelAndView.class, tags={ "configurationFiles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = ModelAndView.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/operate",
        method = RequestMethod.POST)
	public ModelAndView operate() {
		BasicItemCriteria criteria = new BasicItemCriteria();
		criteria.setDataType("记录类型");
		criteria.setUsingState(1);
		List<BasicItem> list = basicItemService.queryList(criteria);
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.setViewName(AdminConstants.JSP_NODE + "/basicItemNode/operate.jsp");
		return mv;
	}
	
	@ApiOperation(value = "编辑配置文件入口", nickname = "edit", notes = "编辑配置文件入口", response = ModelAndView.class, tags={ "configurationFiles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = ModelAndView.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/edit",
        method = RequestMethod.POST)
	public ModelAndView edit(String nodeId) {
		BasicItemNode btNode = basicItemNodeService.getOne(Integer.parseInt(nodeId));
		BasicItem basicItem = basicItemService.getBasicItem(btNode.getAbcattrCode());
		ModelAndView mv = new ModelAndView();
		mv.addObject("btNode", btNode);
		mv.addObject("basicItem", basicItem);
		mv.setViewName(AdminConstants.JSP_NODE + "/basicItemNode/edit.jsp");
		return mv;
	}
	
	@ResponseBody
	@ApiOperation(value = "通过父节点id， 获取孩子", nickname = "getChildNode", notes = "通过父节点id， 获取孩子", response = BasicItemNodes.class, tags={ "configurationFiles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = BasicItemNodes.class),
        @ApiResponse(code = 400, message = "操作失败", response = String.class) })
    @RequestMapping(value = "/getChildNode",
        method = RequestMethod.POST)
	public ResponseEntity<BasicItemNodes> getChildNode(String nodeId) {
		try {
			List<BasicItemNode> list = basicItemNodeService.getChildNode(nodeId);
			BasicItemNodes btItemNodes = new BasicItemNodes();
			btItemNodes.childNode(list);
			return new ResponseEntity<BasicItemNodes>(btItemNodes, HttpStatus.OK);
        } catch (Exception e) {                
            return new ResponseEntity<BasicItemNodes>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
	public ResponseEntity<BasicItems> entityList(String leftRecordType) {
		try {
			List<BasicItem> list = basicItemService.getEntityList(leftRecordType);
			BasicItems btItems = new BasicItems();
			btItems.entity(list);
			return new ResponseEntity<BasicItems>(btItems, HttpStatus.OK);
		} catch (Exception e) {
            return new ResponseEntity<BasicItems>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
		
}
