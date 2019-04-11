package cn.sowell.datacenter.admin.controller.node;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abc.mapping.ValueTypeMapping;
import com.abc.mapping.node.NodeOpsType;
import com.abc.mapping.node.NodeType;
import com.abc.util.ValueType;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.admin.controller.cascadedict.CascadedictBasicItemController;
import cn.sowell.datacenter.admin.controller.node.api.BasicItemNodes;
import cn.sowell.datacenter.admin.controller.node.api.BasicItems;
import cn.sowell.datacenter.admin.controller.node.api.DictionaryBasicItems;
import cn.sowell.datacenter.admin.controller.node.api.InlineResponse200;
import cn.sowell.datacenter.admin.controller.node.api.InlineResponse2001;
import cn.sowell.datacenter.admin.controller.node.api.InlineResponse2002;
import cn.sowell.datacenter.admin.controller.node.api.RecordRelationTypes;
import cn.sowell.datacenter.model.buildproject.service.BuildProjectService;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictBasicItem;
import cn.sowell.datacenter.model.cascadedict.service.CascadedictBasicItemService;
import cn.sowell.datacenter.model.demo.pojo.PlainDemo;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.OneLevelItem;
import cn.sowell.datacenter.model.dictionary.pojo.RecordRelationType;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
import cn.sowell.datacenter.model.dictionary.service.RecordRelationTypeService;
import cn.sowell.datacenter.model.node.criteria.BasicItemNodeCriteria;
import cn.sowell.datacenter.model.node.pojo.BasicItemNode;
import cn.sowell.datacenter.model.node.service.BasicItemNodeService;
import cn.sowell.datacenter.utils.NodeTypeMappingOps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-06-11T07:53:08.778Z")

@Api(tags="configurationFiles", description="配置文件")
@Controller
@RequestMapping(AdminConstants.URI_NODE + "/basicItemNode")
public class BasicItemNodeController {
	
	
	Logger logger = Logger.getLogger(BasicItemNodeController.class);
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(ServletRequestDataBinder binder) {
		System.out.println("执行了InitBinder方法");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}
    
	@Resource
	BasicItemNodeService basicItemNodeService;

	@Resource
	BasicItemService basicItemService;
	
	@Resource
	RecordRelationTypeService recordRelationTypeService;
	@Resource
	CascadedictBasicItemService cascadedictBasicItemService;
	
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
	public ResponseEntity<InlineResponse200> saveOrUpdate(BasicItemNode basicItemNode, String abcattrCode) {
		 try { 
			 BasicItem basicItem = new BasicItem();
			 basicItem.setCode(abcattrCode);
			 if (abcattrCode != null) {
				 basicItemNode.setBasicItem(basicItem);
			 }
			 
			 String dataType = basicItemNode.getDataType();
			 InlineResponse200 inline = new InlineResponse200();
			 //判断当前关系下只能有一个标签和一个实体
			 String relaNodeChil = "";
			 if (basicItemNode.getParentId() != null) {
				 BasicItemNode one = basicItemNodeService.getOne(basicItemNode.getParentId());
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
			 
			 String msg = "";
			 //判断当前节点ops, 修改后的权限小于等于父亲
			 if(basicItemNode.getParentId() == null && NodeType.ABC.equals(NodeType.getNodeType(basicItemNode.getType()))) {
				 inline.setState("200");
			 } else { //就是有父亲的
				 BasicItemNode parentNode = basicItemNodeService.getOne(basicItemNode.getParentId());
				 
				 //多值属性下的孩子过滤掉
				 if (!NodeType.MULTIATTRIBUTE.equals(NodeType.getNodeType(parentNode.getType()))) {
					 if(basicItemNode.getOpt() != null) {
						 NodeOpsType curOps = NodeOpsType.getNodeOpsType(Integer.parseInt(basicItemNode.getOpt()));
						 NodeOpsType parentOps = NodeOpsType.getNodeOpsType(parentNode.getOpt());
						 boolean include = parentOps.include(curOps);
						 if (include) {//修改后的权限最大是父亲
							 inline.setState("200");
						 } else {
							 inline.setState("400");
							 inline.setMsg("节点ops权限应该小于等于父节点权限");	
						 } 
					 }
					
				 }
				 
				 
			 }
			 //判断当前节点ops, 修改后的权限大于等于孩子，要想权限修改为小于孩子， 应该先把孩子的权限修改小
			/* List<BasicItemNodeCriteria> childNode =null;
			 if (basicItemNode.getId() != null) {
				 childNode = basicItemNodeService.getChildNode(basicItemNode.getId());
			 }
			 if (childNode!=null && childNode.size()>0) {//判断节点是否有孩子如果有孩子， 修改后的权限要大于等于孩子的最高权限
				 NodeOpsType curOps = NodeOpsType.getNodeOpsType(Integer.parseInt(basicItemNode.getOpt()));
				 //根据父id， 求出孩子的opt集合
				 List childOptList = basicItemNodeService.getChildOptList(basicItemNode.getId());
				 boolean compareOpt = NodeTypeMappingOps.compareOpt(childOptList, curOps);
				 if (compareOpt) {
					 inline.setState("200");
				 } else {
					 if (NodeType.MULTIATTRIBUTE.equals(NodeType.getNodeType(basicItemNode.getType()))) {
						 if (NodeOpsType.READ.equals(curOps)) {
							 inline.setState("400");
							 inline.setMsg("父节点opt不能比孩子opt小");
						 }
						 
					 } else {
					 
						 inline.setState("400");
						 inline.setMsg("父节点opt不能比孩子opt小");
					 }
				 }
			 } else {//--没有孩子
				 if (inline.getState() !="400") {
					 inline.setState("200");
				 }
			 }*/
			 
			 
			 
			 if ("true".equals(relaNodeChil)) {
				 inline.setState("400");
				 inline.setMsg("关系下只能有一个标签和一个实体");
			 } else if (check) {//重复了
					inline.setState("400");
					inline.setMsg("属性名不能相同或该多值属性已存在");
			} else if (inline.getState() == "400") {
				
			} else {
				basicItemNodeService.saveOrUpdate(basicItemNode);
				inline.setNode(basicItemNode);
				inline.setState("200");
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
        method = {RequestMethod.POST, RequestMethod.GET})
	public ResponseEntity<Object> getNodeOpsType(int opsCode) {
		 try {
			 	List list =	new ArrayList();
				NodeType nodeType = NodeType.getNodeType(opsCode); 
				Collection<NodeOpsType> opsSetByNodeType = NodeTypeMappingOps.getOpsSetByNodeType(nodeType);
				for (Iterator iterator = opsSetByNodeType.iterator(); iterator.hasNext();) {
					NodeOpsType nodeOpsType = (NodeOpsType) iterator.next();
					list.add(nodeOpsType.getName());
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
	public ResponseEntity<Object> getDataType(Integer dataType) {
		try {
			ValueType valueTypeByCName = ValueType.getValueType(dataType);
			
			if("ERRORTYPE".equals(valueTypeByCName.getName())) {
				return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			Collection<ValueType> canTransType = ValueTypeMapping.getCanTransType(valueTypeByCName);
			List list = new ArrayList();
			for (Iterator iterator = canTransType.iterator(); iterator.hasNext();) {
				ValueType valueType = (ValueType) iterator.next();
				String str[] = {valueType.getName(),valueType.getCName()};
				list.add(str);
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dataType", list);
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
	public ResponseEntity<DictionaryBasicItems> getCommLab(String entityCode) {
		try {
			BasicItem lableObj = basicItemService.getLableObj(entityCode);
			 List<CascadedictBasicItem> list = cascadedictBasicItemService.getChildByParentId(lableObj.getOneLevelItem().getDictParentId());
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
		OneLevelItem oneLevelItem = new OneLevelItem();
		criteria.setOneLevelItem(oneLevelItem);
		criteria.getOneLevelItem().setDataType(String.valueOf(ValueType.RECORD.getIndex()));
		criteria.setUsingState(1);
		List<BasicItem> list = basicItemService.queryList(criteria, "");
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
		BasicItem basicItem = btNode.getBasicItem();
		ModelAndView mv = new ModelAndView();
		List opsList = new ArrayList();
		Collection<NodeOpsType> opsSetByNodeType = NodeTypeMappingOps.getOpsSetByNodeType(NodeType.ABC);
		for (Iterator iterator = opsSetByNodeType.iterator(); iterator.hasNext();) {
			NodeOpsType nodeOpsType = (NodeOpsType) iterator.next();
			opsList.add(nodeOpsType.getName());
		}
		mv.addObject("btNode", btNode);
		mv.addObject("basicItem", basicItem);
		mv.addObject("opsList", opsList);
		mv.setViewName(AdminConstants.JSP_NODE + "/basicItemNode/edit.jsp");
		return mv;
	}
	
	//@Ignore
	@ResponseBody
    @RequestMapping(value = "/getChildNode")
	public String getChildNode(Integer nodeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject();
		try {
			List<BasicItemNodeCriteria> list = basicItemNodeService.getChildNode(nodeId);
			map.put("childNode", list);
			map.put("code", 200);
			map.put("msg", "加载成功！");
			return jobj.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception e) {  
        	e.printStackTrace();
        	map.put("code", 200);
			map.put("msg", "加载成功！");
			return jobj.toString();
        }
	}

	@ResponseBody
	@ApiOperation(value = "节点排序", nickname = "nodeSort", notes = "节点排序", response = String.class, tags={ "configurationFiles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = String.class),
        @ApiResponse(code = 400, message = "操作失败", response = String.class) })
    @RequestMapping(value = "/nodeSort",
        method = RequestMethod.POST)
   public ResponseEntity<String> nodeSort(Integer currentId, String beforeId, String afterId){
		BasicItemNode current = basicItemNodeService.getOne(currentId);
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
	
	@RequestMapping(value="/download")
    public ResponseEntity<byte[]> download(Integer nodeId)throws Exception {
		//创建ABC配置文件
		File file = basicItemNodeService.getConfigFile(nodeId);
       HttpHeaders headers = new HttpHeaders();  
       String downloadFileName = new String(file.getName().getBytes("UTF-8"),"iso-8859-1");//设置编码
       headers.setContentDispositionFormData("attachment", downloadFileName);
       headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
       byte[] readFileToByteArray = FileUtils.readFileToByteArray(file);
      // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
      if (file.exists() && file.isFile()) {
    	//在程序退出时删除临时文件
    	 file.delete();
      } 
       return new ResponseEntity<byte[]>(readFileToByteArray,    
               headers, HttpStatus.OK);  
    }
    
    @ApiOperation(value = "文件预览", nickname = "preview", notes = "文件预览", response = ModelAndView.class, tags={ "configurationFiles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = ModelAndView.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/preview",
        method = RequestMethod.POST)
	public ModelAndView preview(Integer nodeId) {
    	
		try {
	    	//创建ABC配置文件
			//bn.getConfigFile(file);
			File file = basicItemNodeService.getConfigFile(nodeId);
			ModelAndView mv = new ModelAndView();
			mv.addObject("file", FileUtils.readFileToString(file, "UTF-8"));
			mv.setViewName(AdminConstants.JSP_NODE + "/basicItemNode/preview.jsp");
			if (file.exists() && file.isFile()) {
		    	//在程序退出时删除临时文件
		    	 	file.delete();
		      } 
			return mv;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
    
    /**
     * 	获取所有配置文件
     * @param entityId  实体id
     * @return
     */
    @ResponseBody
	@RequestMapping("/getAllAbcNode")
	public String getAllAbcNode(){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			List<BasicItemNode> allAbc = basicItemNodeService.getAllAbc();
			map.put("code", 200);
			map.put("msg", "操作成功！");
			map.put("allAbc", allAbc);
			return jobj.toString();
		} catch (Exception e) {
			logger.error("添加失败", e);
			map.put("code", 400);
			map.put("msg", "操作失败！");
			return jobj.toString();
		}
	}
    
    /**
     * 	复制配置文件
     * @param entityId  实体id
     * @return
     */
    @ResponseBody
	@RequestMapping("/copyNode")
	public AjaxPageResponse copyNode(Integer nodeId){
		try {
			basicItemNodeService.copyNode(nodeId);
			return AjaxPageResponse.REFRESH_LOCAL("复制成功");
		}catch (GenericJDBCException e) {
			logger.error("已存在重名文件", e);
			return AjaxPageResponse.FAILD("已存在重名文件！");
		} catch (Exception e) {
			logger.error("复制失败", e);
			return AjaxPageResponse.FAILD("复制失败！");
		}
	}
    
    /**
     * 	根据实体id直接生成配置文件
     * @param entityId  实体id
     * @return
     */
    @ResponseBody
	@RequestMapping("/createConfigFile")
	public AjaxPageResponse createConfigFile(String entityId){
		try {
			boolean isTrue = basicItemNodeService.createConfigFile(entityId);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("操作成功！", "basicItemNode_list");
		}catch (Exception e) {
			logger.error("操作失败！", e);
			return AjaxPageResponse.FAILD("操作失败！");
		}
	}
    
}
