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
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abc.util.ValueType;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.ajax.JsonArrayResponse;
import cn.sowell.copframe.dto.ajax.NoticeType;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.admin.controller.node.api.BasicItems;
import cn.sowell.datacenter.admin.controller.node.api.InlineResponse2003;
import cn.sowell.datacenter.admin.controller.node.api.InlineResponse2004;
import cn.sowell.datacenter.admin.controller.node.api.InlineResponse2005;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictBasicItem;
import cn.sowell.datacenter.model.cascadedict.service.CascadedictBasicItemService;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.CascadeAttr;
import cn.sowell.datacenter.model.dictionary.pojo.OneLevelItem;
import cn.sowell.datacenter.model.dictionary.pojo.Towlevelattr;
import cn.sowell.datacenter.model.dictionary.pojo.TowlevelattrMultiattrMapping;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
import cn.sowell.datacenter.model.dictionary.service.TowlevelattrMultiattrMappingService;
import cn.sowell.datacenter.model.dictionary.service.TowlevelattrService;
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
		System.out.println("执行了InitBinder方法");
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
	
	@ApiOperation(value = "跳转到list页面获取实体信息", nickname = "list", notes = "跳转到list页面获取实体信息", response = ModelAndView.class, tags={ "entityManager", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = ModelAndView.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/list",
        method = RequestMethod.POST)
	public ModelAndView list(){
		BasicItemCriteria criteria = new BasicItemCriteria();
		criteria.getOneLevelItem().setDataType(String.valueOf(ValueType.RECORD.getIndex()));
		List<BasicItem> list = basicItemService.queryList(criteria);
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.setViewName(AdminConstants.JSP_DICTIONARY + "/basicItem/list.jsp");
		return mv;
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
			criteria.getOneLevelItem().setDataType(String.valueOf(ValueType.RECORD.getIndex()));
			List<BasicItem> list = basicItemService.queryList(criteria);
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
				criteria.getOneLevelItem().setDataType(String.valueOf(ValueType.RECORD.getIndex()));
				criteria.setUsingState(1);
				List<BasicItem> list = basicItemService.queryList(criteria);
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
	public ResponseEntity doAdd(@ApiParam(name="BasicItem", value="传入json格式", required=true)BasicItem basicItem, OneLevelItem oneLevelItem){
			String dType = oneLevelItem.getDataType();
			String comm = null;
			
			if ("5".equals(dType)) {
				oneLevelItem.setDictParentId(0);
			} else if ("1".equals(dType)) {
				oneLevelItem.setDictParentId(0);
			}else if ("15".equals(dType)) {
				oneLevelItem.setDictParentId(0);
			}else if ("6".equals(dType)) {
				oneLevelItem.setDictParentId(0);
			}else if ("7".equals(dType)) {
				oneLevelItem.setDictParentId(0);
			}else if ("10".equals(dType)) {
			}else if ("9".equals(dType)) {
				oneLevelItem.setDictParentId(0);
			}else if ("16".equals(dType)) {
			} else if ("14".equals(dType)) {
			} else if ("8".equals(dType)) {
				oneLevelItem.setDictParentId(0);
			} else if ("11".equals(dType)) {
				oneLevelItem.setDictParentId(0);
			}
			oneLevelItem.setDataType(String.valueOf(dType));
			//记录类型
			if (String.valueOf(ValueType.RECORD.getIndex()).equals(oneLevelItem.getDataType())) {
				oneLevelItem.setDictParentId(0);
				basicItem.setParent("");
			} else if (String.valueOf(ValueType.GROUP.getIndex()).equals(oneLevelItem.getDataType())) {
				oneLevelItem.setDictParentId(0);
			} else if (String.valueOf(ValueType.REPEAT.getIndex()).equals(oneLevelItem.getDataType())) {
				
			} else {
				// 到这儿来是普通属性  和多值属性下的普通属性
				//它们的区别是父亲不同， 所以先求父亲    默认前端传来的都是父亲的code， 
				BasicItem bItemPanrent = basicItemService.getBasicItem(basicItem.getParent());
				if (String.valueOf(ValueType.REPEAT.getIndex()).equals(bItemPanrent.getOneLevelItem().getDataType())) {//多值属性下的普通属性
					basicItem.setParent(bItemPanrent.getParent() + "_" +bItemPanrent.getCode());
					oneLevelItem.setTableName(bItemPanrent.getOneLevelItem().getTableName());
					oneLevelItem.setGroupName(bItemPanrent.getCode());
				} else {//普通属性
					oneLevelItem.setTableName("t_" + basicItem.getParent() + "_" + oneLevelItem.getGroupName());
					comm = "comm";
				}
			}
			basicItem.setUsingState(0);
			
			String flag = "";
			if (basicItem.getCode()== null ||basicItem.getCode() == "" || basicItem.getCode().length()<1) {
				flag = "add";
			}
			
			basicItem.setOneLevelItem(oneLevelItem);
			
			for(int i=0; i<10; i++) {
                try {
                	basicItemService.saveOrUpdate(basicItem, flag, comm);
        			
        			if (String.valueOf(ValueType.RECORD.getIndex()).equals(basicItem.getOneLevelItem().getDataType())) {
        				return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("操作成功", "basicItem_list"), HttpStatus.OK);
        			} else {
        				AjaxPageResponse response = new AjaxPageResponse();
        				response.setNotice("操作成功");
        				response.setNoticeType(NoticeType.SUC);
        				return new ResponseEntity(basicItem, HttpStatus.OK);
        			}
                } catch (DataIntegrityViolationException e) {
                    if (i <9) {
                        continue;
                    } else {
                         return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("主键重复或者名称重复, 请重新添加"), HttpStatus.OK);
                    }
                } catch (Exception e) {
                	if (e.getMessage().contains("ids")) {
                		return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("t_sc_basic_item_fix：没有可用数据"), HttpStatus.OK);
                   	}
                    return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("操作失败"), HttpStatus.OK);
                }           
                
            }
			return null;
	}
	
	
	
	/*@ResponseBody
	@ApiOperation(value = "添加", nickname = "doAdd",response = AjaxPageResponse.class, notes = "新增实体,新增普通属性， 多值属性， 分组", tags={ "entityManager", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = AjaxPageResponse.class),
        @ApiResponse(code = 401, message = "操作失败") })
    @RequestMapping(value = "/do_add",
        method = RequestMethod.POST)
	public CommonResponseEntity doAdd(@ApiParam(name="BasicItem", value="传入json格式", required=true)BasicItem basicItem, OneLevelItem oneLevelItem){
			String dType = oneLevelItem.getDataType();
			String comm = null;
			
			if ("5".equals(dType)) {
				oneLevelItem.setDictParentId(0);
			} else if ("1".equals(dType)) {
				oneLevelItem.setDictParentId(0);
			}else if ("15".equals(dType)) {
				oneLevelItem.setDictParentId(0);
			}else if ("6".equals(dType)) {
				oneLevelItem.setDictParentId(0);
			}else if ("7".equals(dType)) {
				oneLevelItem.setDictParentId(0);
			}else if ("10".equals(dType)) {
			}else if ("9".equals(dType)) {
				oneLevelItem.setDictParentId(0);
			}else if ("16".equals(dType)) {
			} else if ("14".equals(dType)) {
			} else if ("8".equals(dType)) {
				oneLevelItem.setDictParentId(0);
			} else if ("11".equals(dType)) {
				oneLevelItem.setDictParentId(0);
			}
			oneLevelItem.setDataType(String.valueOf(dType));
			//记录类型
			if (String.valueOf(ValueType.RECORD.getIndex()).equals(oneLevelItem.getDataType())) {
				oneLevelItem.setDictParentId(0);
				basicItem.setParent("");
			} else if (String.valueOf(ValueType.GROUP.getIndex()).equals(oneLevelItem.getDataType())) {
				oneLevelItem.setDictParentId(0);
			} else if (String.valueOf(ValueType.REPEAT.getIndex()).equals(oneLevelItem.getDataType())) {
				
			} else {
				// 到这儿来是普通属性  和多值属性下的普通属性
				//它们的区别是父亲不同， 所以先求父亲    默认前端传来的都是父亲的code， 
				BasicItem bItemPanrent = basicItemService.getBasicItem(basicItem.getParent());
				if (String.valueOf(ValueType.REPEAT.getIndex()).equals(bItemPanrent.getOneLevelItem().getDataType())) {//多值属性下的普通属性
					basicItem.setParent(bItemPanrent.getParent() + "_" +bItemPanrent.getCode());
					oneLevelItem.setTableName(bItemPanrent.getOneLevelItem().getTableName());
					oneLevelItem.setGroupName(bItemPanrent.getCode());
				} else {//普通属性
					oneLevelItem.setTableName("t_" + basicItem.getParent() + "_" + oneLevelItem.getGroupName());
					comm = "comm";
				}
			}
			basicItem.setUsingState(0);
			
			String flag = "";
			if (basicItem.getCode()== null ||basicItem.getCode() == "" || basicItem.getCode().length()<1) {
				flag = "add";
			}
			
			basicItem.setOneLevelItem(oneLevelItem);
			
			for(int i=0; i<10; i++) {
                try {
                	basicItemService.saveOrUpdate(basicItem, flag, comm);
        			
        			if (String.valueOf(ValueType.RECORD.getIndex()).equals(basicItem.getOneLevelItem().getDataType())) {
        				CommonResponseEntity commonResponseEntity = new CommonResponseEntity();
        				commonResponseEntity.CLOSE_AND_REFRESH_PAGE("操作成功", "basicItem_list");
        				return commonResponseEntity;
        			} else {
        				CommonResponseEntity response = new CommonResponseEntity();
        				response.setNotice("操作成功");
        				response.setNoticeType(NoticeType.SUC);
        				response.setObj(basicItem); 
        				return response;
        			}
                } catch (DataIntegrityViolationException e) {
                    if (i <9) {
                        continue;
                    } else {
                    	return (CommonResponseEntity) CommonResponseEntity.FAILD("主键重复或者名称重复, 请重新添加");
                    }
                } catch (Exception e) {
                	if (e.getMessage().contains("ids")) {
                		return (CommonResponseEntity) CommonResponseEntity.FAILD("t_sc_basic_item_fix：没有可用数据");
                   	}
                	return (CommonResponseEntity) CommonResponseEntity.FAILD("操作失败");
                }           
                
            }
			return null;
	}
*/
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
	@ApiOperation(value = "属性管理", nickname = "attrByPid", notes = "根据实体id， 获取实体下面的普通属性， 多值属性 和实体关系", response = InlineResponse2004.class, tags={ "entityManager", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "操作成功", response = InlineResponse2004.class),
        @ApiResponse(code = 404, message = "操作失败") })
	@RequestMapping(value="/attrByPid", method=RequestMethod.POST)
	public ResponseEntity<InlineResponse2004> attrByPid(String parentId) {
        try {
        	System.out.println();
        	Map<String, List> attrByPid = basicItemService.getAttrByPid(parentId);
        	InlineResponse2004 inline = new InlineResponse2004();
    		inline.commonProper(attrByPid.get("commonProper"));//普通属性
    		inline.moreProper(attrByPid.get("moreProper"));//多值属性
    		inline.entityRela(attrByPid.get("entityRela"));//实体关系
            return new ResponseEntity<InlineResponse2004>(inline, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<InlineResponse2004>(HttpStatus.INTERNAL_SERVER_ERROR);
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
	@ApiOperation(value = "创建表", nickname = "createTab", notes = "创建实体存储", response = AjaxPageResponse.class, tags={ "entityManager", })
	@ApiResponses(value = { 
    @ApiResponse(code = 200, message = "操作成功", response = AjaxPageResponse.class),
    @ApiResponse(code = 404, message = "操作失败") })
	@RequestMapping(value="/createTab", method=RequestMethod.POST)
	public ResponseEntity<AjaxPageResponse> createTab(){
		try {
			basicItemService.createTabCol();
			return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("操作成功", "basicItem_list"), HttpStatus.OK);
		} catch (Exception e) {
		 return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("操作失败"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//删除实体， 属性
	@ResponseBody
	@ApiOperation(value = "删除", nickname = "delete", notes = "删除节点", response = AjaxPageResponse.class, tags={ "entityManager", })
	@ApiResponses(value = { 
    @ApiResponse(code = 200, message = "操作成功", response = AjaxPageResponse.class),
    @ApiResponse(code = 404, message = "操作失败") })
	@RequestMapping(value="/delete", method=RequestMethod.POST)
		public ResponseEntity<AjaxPageResponse> delete(String id){
			try {
				AjaxPageResponse response = new AjaxPageResponse();
				BasicItem bt = basicItemService.getBasicItem(id);
				
				if (bt!=null && bt.getParent() != null && bt.getParent().contains("_")) {//包含下划线就说明它父亲是重复类型
					//判断重复类型有没有二级属性
					TowlevelattrMultiattrMapping oneByRelaMulAttr = tmms.getOneByRelaMulAttr(bt.getOneLevelItem().getGroupName());
					if (oneByRelaMulAttr != null) {
						if (id.equals(oneByRelaMulAttr.getDictionaryAttr()) || id.equals(oneByRelaMulAttr.getValueAttr())) {
							response.setNotice("二级属性正在使用, 请先删除二级属性");
							response.setNoticeType(NoticeType.INFO);
							return new ResponseEntity<AjaxPageResponse>(response, HttpStatus.OK);
						}
					}
					
				}
				
				List<BasicItem> btList = null;
				if (String.valueOf(ValueType.GROUP.getIndex()).equals(bt.getOneLevelItem().getDataType())) {
					btList = basicItemService.getAttrByPidGroupName(bt.getParent(), bt.getCode());
				} else if (String.valueOf(ValueType.REPEAT.getIndex()).equals(bt.getOneLevelItem().getDataType())) {
					btList = basicItemService.getDataByPId(bt.getParent() + "_" + bt.getCode());
				} else {
					btList = basicItemService.getDataByPId(id);
				}
				 
				if (!btList.isEmpty()) {
					response.setNotice("请先删除孩子");
					response.setNoticeType(NoticeType.INFO);
					return new ResponseEntity<AjaxPageResponse>(response, HttpStatus.OK);
				} else {
					
					BasicItem basicItem = basicItemService.getBasicItem(id);
					basicItemService.delete(basicItem);
					
					if (String.valueOf(ValueType.RECORD.getIndex()).equals(basicItem.getOneLevelItem().getDataType())) {
						return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("删除成功", "basicItem_list"), HttpStatus.OK);
					} else {
						response.setNotice("删除成功");
						response.setNoticeType(NoticeType.SUC);
						return new ResponseEntity<AjaxPageResponse>(response, HttpStatus.OK);
					}
				}
			} catch (Exception e) {
				 return new ResponseEntity<AjaxPageResponse>(AjaxPageResponse.FAILD("删除失败"), HttpStatus.INTERNAL_SERVER_ERROR);
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
			List<BasicItem> btList = basicItemService.getDataByPId(basicItem.getParent()+ "_" + basicItem.getCode());
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
				// TODO Auto-generated catch block
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
		
		 
	    //这里写获取枚举值的方法         
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
	    
	  //这里获取普通分组下的可选属性    , 级联属性专用，其他勿用
	    @ResponseBody
		@RequestMapping("/getAttrByPidGroupName")
		public String getAttrByPidGroupName(String cascadeCode){
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jobj = new JSONObject(map);
			try {
				BasicItem basicItem = basicItemService.getBasicItem(cascadeCode);
				List attrByPidGroupName = basicItemService.getAttrByPidGroupName(basicItem.getParent(), basicItem.getOneLevelItem().getGroupName());
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
				List attrByPidGroupName = basicItemService.getDataByPId(basicItem.getParent());
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
		@RequestMapping("/saveCascaseAttrChild")
		public String saveCascaseAttrChild(CascadeAttr cascadeAttr){
	    	
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jobj = new JSONObject(map);
			try {
				basicItemService.saveCascaseAttrChild(cascadeAttr);
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
				basicItemService.delCascaseAttrChild(code,  casCode);
				map.put("code", 200);
				map.put("msg", "success");
				return jobj.toString();
			} catch (Exception e) {
				logger.error("添加失败", e);
				map.put("code", 400);
				map.put("msg", "error");
				return jobj.toString();
			} 
		}
}
