package cn.sowell.datacenter.admin.controller.dictionary;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abc.model.enun.AggregateAttrType;
import com.abc.model.enun.ValueType;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.ajax.JsonArrayResponse;
import cn.sowell.copframe.dto.ajax.NoticeType;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.admin.controller.node.api.BasicItems;
import cn.sowell.datacenter.admin.controller.node.api.InlineResponse2001;
import cn.sowell.datacenter.admin.controller.node.api.InlineResponse2002;
import cn.sowell.datacenter.admin.controller.node.api.InlineResponse2003;
import cn.sowell.datacenter.admin.controller.node.api.InlineResponse2005;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictBasicItem;
import cn.sowell.datacenter.model.cascadedict.service.CascadedictBasicItemService;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.AggregateAttr;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;
import cn.sowell.datacenter.model.dictionary.pojo.CascadeAttr;
import cn.sowell.datacenter.model.dictionary.pojo.OneLevelItem;
import cn.sowell.datacenter.model.dictionary.pojo.Towlevelattr;
import cn.sowell.datacenter.model.dictionary.pojo.TowlevelattrMultiattrMapping;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
import cn.sowell.datacenter.model.dictionary.service.BiRefAttrService;
import cn.sowell.datacenter.model.dictionary.service.TowlevelattrMultiattrMappingService;
import cn.sowell.datacenter.model.dictionary.service.TowlevelattrService;
import cn.sowell.datacenter.model.stat.pojo.StatExpression;
import cn.sowell.datacenter.model.stat.service.StatExpressionService;
import cn.sowell.datacenter.utils.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags="entityManager", description="实体管理接口")
@Controller
@RequestMapping(AdminConstants.URI_DICTIONARY + "/basicItem")
public class BasicItemController {
	
	Logger logger = Logger.getLogger(BasicItemController.class);
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(ServletRequestDataBinder binder) {
		logger.debug("执行了InitBinder方法");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}
	
	@Resource
	BasicItemService basicItemService;
	
	@Resource
	TowlevelattrMultiattrMappingService tmms;
	
	@Resource
	TowlevelattrService towlevelattrService;
	
	@Resource
	CascadedictBasicItemService cascadedictBasicItemService;
	
	@Resource
	BiRefAttrService biRefAttrService;
	
	@Resource
	StatExpressionService statExpressionService;
	
	@ApiOperation(value = "跳转到list页面获取实体信息", nickname = "list", notes = "跳转到list页面获取实体信息", response = ModelAndView.class, tags={ "entityManager", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = ModelAndView.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/list",
        method = RequestMethod.POST)
	public ModelAndView list(){
		try {
			BasicItemCriteria criteria = new BasicItemCriteria();
			OneLevelItem oneLevelItem = new OneLevelItem();
			oneLevelItem.setDataType(String.valueOf(ValueType.RECORD.getIndex()));
			criteria.setOneLevelItem(oneLevelItem);
			
			List<BasicItem> list = basicItemService.queryList(criteria, "");
			ModelAndView mv = new ModelAndView();
			mv.addObject("list", list);
			mv.setViewName(AdminConstants.JSP_DICTIONARY + "/basicItem/list.jsp");
			return mv;
		} catch (Exception e) {
			logger.error("操作失败", e);
			e.printStackTrace();
		}
		return null;
	}
	
	//ajax 获取实体列表
	@ResponseBody
	@ApiOperation(value = "获取实体列表信息", nickname = "entityList", notes = "获取实体列表信息", response = BasicItems.class, tags={ "entityManager", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = BasicItems.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/entityList",
        method = RequestMethod.POST)
	public ResponseEntity<BasicItems> entityList(){
		try {
			BasicItemCriteria criteria = new BasicItemCriteria();
			OneLevelItem oneLevelItem = new OneLevelItem();
			oneLevelItem.setDataType(String.valueOf(ValueType.RECORD.getIndex()));
			criteria.setOneLevelItem(oneLevelItem);
			
			List<BasicItem> list = basicItemService.queryList(criteria, "");
			BasicItems bts = new BasicItems();
			bts.entity(list);
			return new ResponseEntity<BasicItems>(bts, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<BasicItems>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//ajax 获取实体列表
		@ResponseBody
		@ApiOperation(value = "引用类型获取对应实体列表", nickname = "referenceTypeEntityList", notes = "引用类型获取对应实体列表", response = BasicItems.class, tags={ "entityManager", })
	    @ApiResponses(value = { 
	        @ApiResponse(code = 200, message = "操作成功", response = BasicItems.class),
	        @ApiResponse(code = 401, message = "操作失败") })
	    @RequestMapping(value = "/referenceTypeEntityList",
	        method = RequestMethod.POST)
		public ResponseEntity<BasicItems> referenceTypeEntityList(){
			try {
				BasicItemCriteria criteria = new BasicItemCriteria();
				OneLevelItem oneLevelItem = new OneLevelItem();
				oneLevelItem.setDataType(String.valueOf(ValueType.RECORD.getIndex()));
				criteria.setOneLevelItem(oneLevelItem);
				criteria.setUsingState(1);
				List<BasicItem> list = basicItemService.queryList(criteria, "");
				BasicItems bts = new BasicItems();
				bts.entity(list);
				return new ResponseEntity<BasicItems>(bts, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<BasicItems>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	@ResponseBody
	@ApiOperation(value = "获取数据类型信息", nickname = "getDataType",response = InlineResponse2003.class, notes = "获取数据类型信息", tags={ "entityManager", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = InlineResponse2003.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/getDataType",
        method = RequestMethod.POST)
	public ResponseEntity<String> getDataType(){
		try {
			Map<String, Object> dataTypeMap = Constants.DATA_TYPE_MAP;
			dataTypeMap.remove(String.valueOf(ValueType.GROUP.getIndex()));
			dataTypeMap.remove(String.valueOf(ValueType.RECORD.getIndex()));
			dataTypeMap.remove(String.valueOf(ValueType.REPEAT.getIndex()));
			JSONObject jobj = new JSONObject(dataTypeMap);
			return new ResponseEntity<String>(jobj.toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ResponseBody
	@ApiOperation(value = "添加", nickname = "doAdd",response = AjaxPageResponse.class, notes = "新增实体,新增普通属性， 多值属性， 分组", tags={ "entityManager", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = AjaxPageResponse.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/do_add",
        method = RequestMethod.POST)
	public ResponseEntity doAdd(@ApiParam(name="BasicItem", value="传入json格式", required=true)BasicItem basicItem, OneLevelItem oneLevelItem,
			Integer cascadedict, BiRefAttr biRefAttr,
			String aggregateAttrCode, 
			Integer aggregateAttrType,   
			String aggregateAttrRelCode,
        	Integer aggregateAttrExpressionId,
        	Integer aggregateAttrFiltersId){
		
            try {
            	
            	AggregateAttr aggregateAttr = new AggregateAttr(aggregateAttrCode, aggregateAttrType, aggregateAttrRelCode, aggregateAttrExpressionId, aggregateAttrFiltersId, null, null);
            	
            	//刚刚做到这里
            	basicItem.setCnName(basicItem.getCnName().trim());
            	
            	basicItem.setOneLevelItem(oneLevelItem);
                BasicItem saveOrUpdate = basicItemService.saveOrUpdate(basicItem, cascadedict, biRefAttr, aggregateAttr);
    			if (String.valueOf(ValueType.RECORD.getIndex()).equals(basicItem.getOneLevelItem().getDataType())) {
    				return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("操作成功", "basicItem_list"), HttpStatus.OK);
    			} else {
    				AjaxPageResponse response = new AjaxPageResponse();
    				response.setNotice("操作成功");
    				response.setNoticeType(NoticeType.SUC);
    				return new ResponseEntity(basicItem, HttpStatus.OK);
    			}
            } catch (DataIntegrityViolationException e) {
                     return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("主键重复或者名称重复, 请重新添加"), HttpStatus.OK);
            } catch (Exception e) {
            	if (e.getMessage().contains("ids")) {
            		return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("t_sc_basic_item_fix：没有可用数据"), HttpStatus.OK);
               	}
                return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("操作失败"), HttpStatus.OK);
            }           
	}
	
	
	
	@ResponseBody
	@ApiOperation(value = "get节点信息", nickname = "getOne",response = BasicItem.class, notes = "获取单个节点信息", tags={ "entityManager", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = BasicItem.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/getOne",
        method = RequestMethod.POST)
	public ResponseEntity<BasicItem> getOne(String id){
		try {
			BasicItem basicItem = basicItemService.getBasicItem(id);
			return new ResponseEntity<BasicItem>(basicItem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<BasicItem>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ResponseBody
	@ApiOperation(value = "更新信息", nickname = "doUpdate",response = AjaxPageResponse.class, notes = "更新信息", tags={ "entityManager", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = AjaxPageResponse.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/do_update",
        method = RequestMethod.POST)
	public ResponseEntity<AjaxPageResponse> doUpdate(BasicItem basicItem){
		try {
			basicItemService.update(basicItem);
			return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "basicItem_list"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<AjaxPageResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//根据实体id， 获取实体下面的普通属性， 多值属性 和实体关系
	@ResponseBody
	@RequestMapping(value="/attrByPid", method=RequestMethod.POST)
	public String attrByPid(String parentId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			Map<String, List> attrByPid = basicItemService.getAttrByPid(parentId);
			map.put("code", 200);
			map.put("msg", "操作成功");
			map.put("attrByPid", attrByPid);
			return jobj.toString();
		} catch (Exception e) {
			logger.error("操作失败", e);
			map.put("code", 400);
			map.put("msg", "操作失败");
			return jobj.toString();
		}
		
	}
	
	//过期实体or正常  普通属性和多值属性
	@ResponseBody
	@ApiOperation(value = "过期实体", nickname = "savePastDue", notes = "过期实体or正常  普通属性和多值属性", response = AjaxPageResponse.class, tags={ "entityManager", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = AjaxPageResponse.class),
        @ApiResponse(code = 404, message = "操作失败") })
	@RequestMapping(value="/saveStatus", method=RequestMethod.POST)
	public ResponseEntity savePastDue(String id, String statusStr){
		try {
			BasicItem basicItem = basicItemService.getBasicItem(id);
			basicItemService.saveUsingStatus(basicItem, statusStr);
			if (String.valueOf(ValueType.RECORD.getIndex()).equals(basicItem.getOneLevelItem().getDataType())) {
				 return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "basicItem_list"), HttpStatus.OK);
			} else {
				return new ResponseEntity<BasicItem>(basicItem, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("操作失败"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//创建表
	@ResponseBody
	@RequestMapping(value="/createTab", method=RequestMethod.POST)
	public AjaxPageResponse createTab(){
		try {
			basicItemService.createTabCol();
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("操作成功", "basicItem_list");
		} catch (Exception e) {
		 return AjaxPageResponse.FAILD("操作失败");
		}
	}
	
	//删除实体， 属性
	@ResponseBody
	@RequestMapping(value="/delete", method=RequestMethod.POST)
		public AjaxPageResponse delete(String id){
			try {
				AjaxPageResponse response = new AjaxPageResponse();
					
					BasicItem basicItem = basicItemService.getBasicItem(id);
					
					//检查数据
					Message message = basicItemService.check(basicItem.getCode());
					 
					if (!message.getNoticeType().equals(NoticeType.SUC)) {
						return AjaxPageResponse.FAILD(message.getMessage());
					}
					basicItemService.delete(basicItem);
					if (String.valueOf(ValueType.RECORD.getIndex()).equals(basicItem.getOneLevelItem().getDataType())) {
						return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("删除成功", "basicItem_list");
					} else {
						response.setNotice("删除成功");
						response.setNoticeType(NoticeType.SUC);
						return response;
					}
			} catch (Exception e) {
				 return AjaxPageResponse.FAILD("删除失败");
			}
		}
	
	
	@ResponseBody
	@ApiOperation(value = "getDataByPid", nickname = "getDataByPid", notes = "点击添加二级属性的时候， 获取对应的多值属性中的普通属性和枚举属性", response = InlineResponse2005.class, tags={ "entityManager", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = InlineResponse2005.class),
        @ApiResponse(code = 404, message = "操作失败") })
    @RequestMapping(value = "getDataByPid",
        method = RequestMethod.POST)
	public ResponseEntity<InlineResponse2005> getDataByPid(String id){
		try {
			BasicItem basicItem = basicItemService.getBasicItem(id);
			List<BasicItem> btList = basicItemService.getDataByPId(basicItem.getParent()+ "_" + basicItem.getCode(), "");
			Map<String, Object> map = new HashMap<String, Object>();
			InlineResponse2005 inline = new InlineResponse2005();
			inline.child(btList);
			return new ResponseEntity<InlineResponse2005>(inline, HttpStatus.OK);
		} catch (Exception e) {
		 return new ResponseEntity<InlineResponse2005>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//添加一个二级属性
	@ResponseBody
	@ApiOperation(value = "添加二级属性", nickname = "saveTwoLevelAttr", notes = "添加二级属性", response = AjaxPageResponse.class, tags={ "entityManager", })
	@ApiResponses(value = { 
    @ApiResponse(code = 200, message = "操作成功", response = AjaxPageResponse.class),
    @ApiResponse(code = 404, message = "操作失败") })
	@RequestMapping(value="/saveTwoLevelAttr", method=RequestMethod.POST)
	public ResponseEntity<AjaxPageResponse> saveTwoLevelAttr(TowlevelattrMultiattrMapping tmm){
		try {
			tmm.setUsingState(1);
			tmms.saveOrUpdate(tmm);
			AjaxPageResponse response = new AjaxPageResponse();
			response.setNotice("添加成功");
			response.setNoticeType(NoticeType.SUC);
			return new ResponseEntity<AjaxPageResponse>(response, HttpStatus.OK);
		} catch (Exception e) {
		 return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("操作失败"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//添加一个二级属性的孩子
	@ResponseBody
	@ApiOperation(value = "添加二级属性的孩子", nickname = "saveTwoLevelAttrChild", notes = "添加二级属性的孩子", response = AjaxPageResponse.class, tags={ "entityManager", })
	@ApiResponses(value = { 
    @ApiResponse(code = 200, message = "操作成功", response = AjaxPageResponse.class),
    @ApiResponse(code = 404, message = "操作失败") })
	@RequestMapping(value="/saveTwoLevelAttrChild", method=RequestMethod.POST)
	public ResponseEntity saveTwoLevelAttrChild(String mappingId, String dictionaryCode, String name){
		try {
			Towlevelattr criteria = new Towlevelattr();
			criteria.setMappingId(mappingId);
			criteria.setDictionaryCode(dictionaryCode);
			basicItemService.createTowLevel(criteria, name);
			BasicItem basicItem = basicItemService.getBasicItem(criteria.getCode());
			criteria.setBasicItem(basicItem);
			return new ResponseEntity(criteria, HttpStatus.OK);
		} catch (Exception e) {
		 return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("操作失败"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//根据id查询一个二级属性
	@ResponseBody
	@RequestMapping(value="/getTwoLevelAttr", method=RequestMethod.POST)
	public String getTwoLevelAttr(Long id){
		TowlevelattrMultiattrMapping tmmObj = tmms.getOne(id);
		String mappingId = Long.toString(tmmObj.getId());
		List listByMappingId = towlevelattrService.getListByMappingId(mappingId);
		tmmObj.setChildList(listByMappingId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tmm", tmmObj);
		JSONObject json = new JSONObject(map);
		return json.toString();
	}
	
		//根据twolevelId   查询出字典里面的字段
		@ApiIgnore
		@ResponseBody
		@RequestMapping("/getDictCode")
		public String getDictCode(Long id){
			 try {
				List<CascadedictBasicItem> dictCode = basicItemService.getDictCode(id);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("dictList", dictCode);
				JSONObject json = new JSONObject(map);
				return json.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
			
		}
		
		//获取多值属性的父亲， 传入参数为多值属性的code
		@ApiIgnore
		@ResponseBody
		@RequestMapping("/getMoreParent")
		public String getMoreParent(String code){
			BasicItem btMore = basicItemService.getBasicItem(code);
			BasicItem btParent = basicItemService.getBasicItem(btMore.getParent());
			String str  = String.valueOf(btParent.getUsingState());
			return str;
		}	
		
		//添加二级属性的孩子， 二级属性孩子的名称和当前实体普通属性的名称不能相同
		@ApiIgnore
		@ResponseBody
		@RequestMapping("/getTwoSameCount")
		public String getTwoSameCount(String name, String entityId){
			BigInteger geSameCount = basicItemService.getTwoSameCount(name, entityId);
			if (geSameCount.equals(BigInteger.ZERO)) {
				return "true";
			} else {
				return "false";
			}
		}
		
		//删除二级属性的孩子
		@ApiIgnore
		@ResponseBody
		@RequestMapping(value="/twoattr_chil_delete")
		public AjaxPageResponse twoattr_chil_delete(String code){
			try {
				towlevelattrService.delete(code);
				AjaxPageResponse response = new AjaxPageResponse();
				response.setNotice("删除成功");
				response.setNoticeType(NoticeType.SUC);
				return response;
			} catch (Exception e) {
				return AjaxPageResponse.FAILD("删除失败");
			}
		}
			
		//删除二级属性本身
		@ApiIgnore
		@ResponseBody
		@RequestMapping(value="/twoattr_delete")
		public AjaxPageResponse twoattr_delete(Long id){
			try {
				AjaxPageResponse response = new AjaxPageResponse();
				List<Towlevelattr> listByMappingId = tmms.getListByMappingId(String.valueOf(id));
				
				if (!listByMappingId.isEmpty()) {
					response.setNotice("请先删除孩子");
					response.setNoticeType(NoticeType.INFO);
					return response;
				}
				tmms.delete(id);
				response.setNotice("删除成功");
				response.setNoticeType(NoticeType.SUC);
				return response;
			} catch (Exception e) {
				return AjaxPageResponse.FAILD("删除失败");
			}
		}
		
		//判断多值属性的孩子是否被二级属性占用， 占用则不可编辑
		@ApiIgnore
		@ResponseBody//entityId为二级属性孩子的id
		@RequestMapping("/isTwoattr")
		public String isTwoattr(String id){
			BasicItem bt = basicItemService.getBasicItem(id);
			//判断重复类型有没有二级属性
			TowlevelattrMultiattrMapping oneByRelaMulAttr = tmms.getOneByRelaMulAttr(bt.getOneLevelItem().getGroupName());
			if (oneByRelaMulAttr != null) {
				if (id.equals(oneByRelaMulAttr.getDictionaryAttr()) || id.equals(oneByRelaMulAttr.getValueAttr())) {
					return "true";//有二级属性占用
				}
			}
			return "false";
		}
		
		 
	    //这里写获取枚举值的方法    添加编辑实体的标签枚举也是这个方法     
	    @ResponseBody
		@RequestMapping("/getDictPitem")
		public String getDictPitem(){
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jobj = new JSONObject(map);
			try {
				List<CascadedictBasicItem> childList = cascadedictBasicItemService.getParentAll();
				map.put("code", 200);
				map.put("msg", "success");
				map.put("dictPitem", childList);
				return jobj.toString();
			} catch (Exception e) {
				logger.error("添加失败", e);
				map.put("code", 400);
				map.put("msg", "error");
				return jobj.toString();
			}
		}
	    
	  //这里写获取级联属性枚举值的方法         
	    @ResponseBody
		@RequestMapping("/getCascaseDictPitem")
		public String getCascaseDictPitem(){
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jobj = new JSONObject(map);
			try {
				List<CascadedictBasicItem> childList = cascadedictBasicItemService.getCascaseDictPitem();
				map.put("code", 200);
				map.put("msg", "success");
				map.put("dictPitem", childList);
				return jobj.toString();
			} catch (Exception e) {
				logger.error("添加失败", e);
				map.put("code", 400);
				map.put("msg", "error");
				return jobj.toString();
			}
		}
	    
	    
	  //这里获取普通分组下的所有引用属性    , 级联属性专用，其他勿用
	    @ResponseBody
		@RequestMapping("/getRefAttrByPidGroupName")
		public String getRefAttrByPidGroupName(String cascadeCode){
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jobj = new JSONObject(map);
			try {
				BasicItem basicItem = basicItemService.getBasicItem(cascadeCode);
				List attrByPidGroupName = basicItemService.getAttrByPidGroupName(basicItem.getParent(), basicItem.getOneLevelItem().getGroupName(), String.valueOf(ValueType.REFERENCE.getIndex()));
				map.put("code", 200);
				map.put("msg", "success");
				map.put("commRefAttr", attrByPidGroupName);
				return jobj.toString();
			} catch (Exception e) {
				logger.error("添加失败", e);
				map.put("code", 400);
				map.put("msg", "error");
				return jobj.toString();
			}
		}
	    
	    
	  //这里获取普通分组下的可选属性    , 级联属性专用，其他勿用
	    @ResponseBody
		@RequestMapping("/getAttrByPidGroupName")
		public String getAttrByPidGroupName(String cascadeCode){
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jobj = new JSONObject(map);
			try {
				BasicItem basicItem = basicItemService.getBasicItem(cascadeCode);
				List attrByPidGroupName = basicItemService.getAttrByPidGroupName(basicItem.getParent(), basicItem.getOneLevelItem().getGroupName(), String.valueOf(ValueType.STRING.getIndex()));
				map.put("code", 200);
				map.put("msg", "success");
				map.put("commAttr", attrByPidGroupName);
				return jobj.toString();
			} catch (Exception e) {
				logger.error("添加失败", e);
				map.put("code", 400);
				map.put("msg", "error");
				return jobj.toString();
			}
		}
	    
	    //这里获取多值属性下的可选属性    , 级联属性专用，其他勿用
	    @ResponseBody
		@RequestMapping("/getMoreAttrByPid")
		public String getMoreAttrByPid(String cascadeCode){
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jobj = new JSONObject(map);
			try {
				BasicItem basicItem = basicItemService.getBasicItem(cascadeCode);
				List attrByPidGroupName = basicItemService.getDataByPId(basicItem.getParent(), String.valueOf(ValueType.STRING.getIndex()));
				map.put("code", 200);
				map.put("msg", "success");
				map.put("commAttr", attrByPidGroupName);
				return jobj.toString();
			} catch (Exception e) {
				logger.error("添加失败", e);
				map.put("code", 400);
				map.put("msg", "error");
				return jobj.toString();
			}
		}
	    
	  //这里获取多值属性下的可选属性    , 级联属性专用，其他勿用
	    @ResponseBody
		@RequestMapping("/getMoreRefAttrByPid")
		public String getMoreRefAttrByPid(String cascadeCode){
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jobj = new JSONObject(map);
			try {
				BasicItem basicItem = basicItemService.getBasicItem(cascadeCode);
				List attrByPidGroupName = basicItemService.getDataByPId(basicItem.getParent(), String.valueOf(ValueType.REFERENCE.getIndex()));
				map.put("code", 200);
				map.put("msg", "success");
				map.put("commRefAttr", attrByPidGroupName);
				return jobj.toString();
			} catch (Exception e) {
				logger.error("添加失败", e);
				map.put("code", 400);
				map.put("msg", "error");
				return jobj.toString();
			}
		}
	    
	    //这里获取级联属性的孩子， 级联属性专用
	    @ResponseBody
		@RequestMapping("/getCascadeAttrChild")
		public String getCascadeAttrChild(String code){
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jobj = new JSONObject(map);
			try {
				List cascadeAttrChild = basicItemService.getCascadeAttrChild(code);
				map.put("code", 200);
				map.put("msg", "success");
				map.put("cascadeAttrChild", cascadeAttrChild);
				return jobj.toString();
			} catch (Exception e) {
				logger.error("添加失败", e);
				map.put("code", 400);
				map.put("msg", "error");
				return jobj.toString();
			}
		}
	    
	  //保存级联属性的孩子
	    @ResponseBody
		@RequestMapping("/saveCascaseRefAttrChild")
		public String saveCascaseRefAttrChild(String code, String casCode){
	    	
	    	Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jobj = new JSONObject(map);
			try {
				
				CascadeAttr cascadeAttr = basicItemService.saveCascaseAttrChild(code, casCode);
				map.put("code", 200);
				map.put("msg", "success");
				map.put("cascadeAttr", cascadeAttr);
				return jobj.toString();
			}catch (DataIntegrityViolationException e) {
				logger.error("添加失败", e);
				map.put("code", 400);
				map.put("msg", "级联属性不能重复添加");
				return jobj.toString();
			} catch (Exception e) {
				logger.error("添加失败", e);
				map.put("code", 400);
				map.put("msg", "添加失败");
				return jobj.toString();
			}
	    	
	    }
	    
	  //保存级联属性的孩子
	    @ResponseBody
		@RequestMapping("/saveCascaseAttrChild")
		public String saveCascaseAttrChild(String code, String cnName, String description){
	    	
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jobj = new JSONObject(map);
			try {
				
				BigInteger canAddChildCount = basicItemService.canAddChildCount(code);//获取可以添加孩子的数量
				
				if (canAddChildCount==null) {
					map.put("code", 400);
					map.put("msg", "请检查本属性链接的字典是否存在！");
					return jobj.toString();
				}
				
				int count = Integer.valueOf(canAddChildCount.toString());
				if (count<=0) {
					map.put("code", 400);
					map.put("msg", "级联属性的孩子已达到上限，不能添加");
					return jobj.toString();
				}
				
				CascadeAttr cascadeAttr = basicItemService.saveCascaseAttrChild(code, cnName, description);
				map.put("code", 200);
				map.put("msg", "success");
				map.put("cascadeAttr", cascadeAttr);
				return jobj.toString();
			}catch (DataIntegrityViolationException e) {
				logger.error("添加失败", e);
				map.put("code", 400);
				map.put("msg", "级联属性不能重复添加");
				return jobj.toString();
			} catch (Exception e) {
				logger.error("添加失败", e);
				map.put("code", 400);
				map.put("msg", "添加失败");
				return jobj.toString();
			}
		}
	    
	    
	    //删除级联属性的孩子
	    @ResponseBody
		@RequestMapping("/delCascaseAttrChild")
		public String delCascaseAttrChild(String code, String  casCode){
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jobj = new JSONObject(map);
			try {
				
				basicItemService.deleteCascaseAttrChild(code,  casCode);
				map.put("code", 200);
				map.put("msg", "success");
				return jobj.toString();
			} catch (Exception e) {
				logger.error("删除失败", e);
				map.put("code", 400);
				map.put("msg", "error");
				return jobj.toString();
			} 
		}
	    
	    /**
	     * 	获取当前实体下, 指定多值或者是指定实体下 指定类型的数据
	     * @param entityId  实体id
	     * @return
	     */
	    @ResponseBody
		@RequestMapping("/getAppointTypeAttr")
		public String getAppointTypeAttr(String parentCode, Integer dataType){
	    	// 获取指定类型的枚举
	    	ValueType valueType = ValueType.getValueType(dataType);
	    	
	    	//这里判断是实体， 还是多值属性
	    	BasicItem basicItem = basicItemService.getBasicItem(parentCode);
	    	String dataType2 = basicItem.getOneLevelItem().getDataType();
	    	boolean equals = dataType2.equals(ValueType.REPEAT.getIndex()+"");
	    	
	    	if (equals) {
	    		parentCode = basicItem.getParent() + "_" + basicItem.getCode();
	    	}
	    	
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jobj = new JSONObject(map);
			try {
				List list =  basicItemService.getAppointTypeAttr(parentCode, valueType);
				map.put("code", 200);
				map.put("msg", "success");
				map.put("appointTypeAttr", list);
				return jobj.toString();
			} catch (Exception e) {
				logger.error("添加失败", e);
				map.put("code", 400);
				map.put("msg", "error");
				return jobj.toString();
			}
		}
	    
	    
	    /**
	     * 	获取当前实体下， 所有分组的级联属性
	     * @param entityId  实体id
	     * @return
	     */
	    /*@Deprecated
	    @ResponseBody
		@RequestMapping("/getGroupCascaseAttr")
		public String getGroupCascaseAttr(String entityId){
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jobj = new JSONObject(map);
			try {
				List list =  basicItemService.getAppointTypeAttr(entityId, ValueType.CASCADETYPE);
				map.put("code", 200);
				map.put("msg", "success");
				map.put("groupCascaseAttr", list);
				return jobj.toString();
			} catch (Exception e) {
				logger.error("添加失败", e);
				map.put("code", 400);
				map.put("msg", "error");
				return jobj.toString();
			}
		}*/
	    
	    
	    /**
	     * 	获取当前实体下， 当前多值属性里面的级联属性
	     * @param repeatId  多值属性id
	     * @return
	     */
	   /* @Deprecated
	    @ResponseBody
		@RequestMapping("/getMoreCascaseAttr")
		public String getMoreCascaseAttr(String repeatId){
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jobj = new JSONObject(map);
			try {
				BasicItem basicItem = basicItemService.getBasicItem(repeatId);
				List list =  basicItemService.getAppointTypeAttr(basicItem.getParent()+ "_" + basicItem.getCode(), ValueType.CASCADETYPE);
				map.put("code", 200);
				map.put("msg", "success");
				map.put("moreCascaseAttr", list);
				return jobj.toString();
			} catch (Exception e) {
				logger.error("添加失败", e);
				map.put("code", 400);
				map.put("msg", "error");
				return jobj.toString();
			}
		}*/
	    
	    //这里获取实体选择的那个标签
	    @ResponseBody
		@RequestMapping("/getLableObj")
		public String getLableObj(String code){
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jobj = new JSONObject(map);
			try {
				BasicItem lableObj = basicItemService.getLableObj(code);
				map.put("code", 200);
				map.put("msg", "操作成功");
				map.put("lableObj", lableObj);
				return jobj.toString();
			} catch (Exception e) {
				logger.error("操作失败", e);
				map.put("code", 400);
				map.put("msg", "操作失败");
				return jobj.toString();
			}
		}
	    
	    
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
				OneLevelItem oneLevelItem = new OneLevelItem();
				oneLevelItem.setDataType(String.valueOf(ValueType.REPEAT.getIndex()));
				criteria.setUsingState(1);
				criteria.setOneLevelItem(oneLevelItem);
				
				List<BasicItem> list = basicItemService.queryList(criteria, "");
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
	        	
	        	List<BasicItem> list = null;
	        	BasicItem repeat = basicItemService.getBasicItem(repeatId);
	        	if (repeat != null) {
	        		BasicItemCriteria criteria = new BasicItemCriteria();
	        		criteria.setParent(repeat.getParent() + "_" + repeatId);
	        		criteria.setUsingState(1);
	        		list = basicItemService.queryList(criteria, String.valueOf(ValueType.CASCADETYPE.getIndex()));
	        	}
	    		
	    		InlineResponse2001 inline = new InlineResponse2001();
	    		inline.repeatChild(list);
	            return new ResponseEntity<InlineResponse2001>(inline, HttpStatus.OK);
	        } catch (Exception e) { 
	            return new ResponseEntity<InlineResponse2001>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
		}
	    
	    
	    //根据实体code， 获取实体下所有的普通属性
	    @ResponseBody
		@RequestMapping("/getComm")
		public String getComm(String entityId){
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jobj = new JSONObject(map);
			try {
				//这里判断是实体， 还是多值属性
		    	BasicItem basicItem = basicItemService.getBasicItem(entityId);
		    	String dataType2 = basicItem.getOneLevelItem().getDataType();
		    	boolean equals = dataType2.equals(ValueType.REPEAT.getIndex()+"");
		    	
		    	if (equals) {
		    		entityId = basicItem.getParent() + "_" + basicItem.getCode();
		    	}
				
				List commList = basicItemService.getComm(entityId);
				map.put("code", 200);
				map.put("msg", "操作成功");
				map.put("commList", commList);
				return jobj.toString();
			} catch (Exception e) {
				logger.error("操作失败", e);
				map.put("code", 400);
				map.put("msg", "操作失败");
				return jobj.toString();
			}
		}
	    
	    //根据引用属性的code， 获取引用属性的BiRefAttr
	    @ResponseBody
		@RequestMapping("/getBiRefAttr")
		public String getBiRefAttr(String refTypeCode){
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jobj = new JSONObject(map);
			try {
				BiRefAttr biRefAttr = biRefAttrService.getOne(refTypeCode);
				map.put("code", 200);
				map.put("msg", "操作成功");
				map.put("biRefAttr", biRefAttr);
				return jobj.toString();
			} catch (Exception e) {
				logger.error("操作失败", e);
				map.put("code", 400);
				map.put("msg", "操作失败");
				return jobj.toString();
			}
		}
	    
	    //跳转到添加表达式页面
	    @RequestMapping("/addExpression")
		public String addExpression(String sourcecode, String expressionId ,Model model) {
				model.addAttribute("sourcecode", sourcecode);
				model.addAttribute("expressionId", expressionId);
				return AdminConstants.JSP_DICTIONARY + "/basicItem/expression/expression.jsp";
		}
	    
	    
	    //跳转到添加过滤条件页面
	    @RequestMapping("/addFilters")
		public String addFilters(String sourcecode, String filtersId ,Model model) {
				model.addAttribute("sourcecode", sourcecode);
				model.addAttribute("filtersId", filtersId);
				return AdminConstants.JSP_DICTIONARY + "/basicItem/expression/filters.jsp";
		}
	    
	    
	    
	    
}
