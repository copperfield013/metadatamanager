package cn.sowell.datacenter.admin.controller.dictionary;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.ajax.JsonArrayResponse;
import cn.sowell.copframe.dto.ajax.NoticeType;
import cn.sowell.copframe.dto.ajax.ResponseJSON;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.model.demo.criteria.DemoCriteria;
import cn.sowell.datacenter.model.demo.pojo.PlainDemo;
import cn.sowell.datacenter.model.demo.service.DemoService;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryBasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.Towlevelattr;
import cn.sowell.datacenter.model.dictionary.pojo.TowlevelattrMultiattrMapping;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
import cn.sowell.datacenter.model.dictionary.service.TowlevelattrMultiattrMappingService;
import cn.sowell.datacenter.model.dictionary.service.TowlevelattrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@Api(value="entityManager", description="实体管理接口")
@Controller
@RequestMapping(AdminConstants.URI_DICTIONARY + "/basicItem")
public class BasicItemController {
	
	@Resource
	BasicItemService basicItemService;
	
	@Resource
	TowlevelattrMultiattrMappingService tmms;
	
	@Resource
	TowlevelattrService towlevelattrService;
	
	Logger logger = Logger.getLogger(BasicItemController.class);
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(ServletRequestDataBinder binder) {
		System.out.println("执行了InitBinder方法");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	
	@RequestMapping("/list")
	public String list(BasicItemCriteria criteria, Model model){
		criteria.setDataType("记录类型");
		List<BasicItem> list = basicItemService.queryList(criteria);
		model.addAttribute("list", list);
		model.addAttribute("criteria", criteria);
		return AdminConstants.JSP_DICTIONARY + "/basicItem/list.jsp";
	}
	
	//ajax 获取实体列表
	@ApiOperation(value="获取实体列表信息", notes="获取实体列表")
	@ResponseBody
	@RequestMapping(value="/entityList", method=RequestMethod.POST)
	public String entityList(){
		BasicItemCriteria criteria = new BasicItemCriteria();
		criteria.setDataType("记录类型");
		List<BasicItem> list = basicItemService.queryList(criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", list);
		JSONObject jobj = new JSONObject(map);
		return jobj.toString();
	}
	
	@ResponseBody
	@RequestMapping("/getDataType")
	public String getDataType(BasicItem	basicItem){
		
		Map<String, Object> map = Constants.DATA_TYPE_MAP;
		map.remove("record");
		map.remove("repeat");
		map.remove("group");
		JSONObject jobj = new JSONObject(map);
		return jobj.toString();
	}
	
	@ApiOperation(value="添加", notes="新增实体,新增普通属性， 多值属性， 分组")
	@ResponseBody
	@RequestMapping(value="/do_add", method=RequestMethod.POST)
	public AjaxPageResponse doAdd(@ApiParam(name="basicItem对象", value="传入json格式", required=true)BasicItem basicItem){
			String dType = basicItem.getDataType();
			String dataType = "";
			String comm = null;
			
			if ("char".equals(dType)) {
				dataType = "字符型";
			} else if ("digital".equals(dType)) {
				dataType = "数字型";
			}else if ("digitalDecimal".equals(dType)) {
				dataType = "数字型小数";
			}else if ("date".equals(dType)) {
				dataType = "日期型";
			}else if ("dateTime".equals(dType)) {
				dataType = "时间型";
			}else if ("record".equals(dType)) {
				dataType = "记录类型";
			}else if ("repeat".equals(dType)) {
				dataType = "重复类型";
			}else if ("group".equals(dType)) {
				dataType = "分组类型";
			} else if ("枚举".equals(dType)) {
				dataType = "字符型";
			} else if ("文件型".equals(dType)) {
				dataType = "二进制型";
			}
			basicItem.setDataType(dataType);
			//记录类型
			if ("记录类型".equals(basicItem.getDataType())) {
				basicItem.setDictParentId(0);
			} else if ("分组类型".equals(basicItem.getDataType())) {
				basicItem.setDictParentId(0);
			} else if ("重复类型".equals(basicItem.getDataType())) {
				basicItem.setDictParentId(0);
			} else {
				// 到这儿来是普通属性  和多值属性下的普通属性
				//它们的区别是父亲不同， 所以先求父亲    默认前端传来的都是父亲的code， 
				BasicItem bItemPanrent = basicItemService.getBasicItem(basicItem.getParent());
				if ("重复类型".equals(bItemPanrent.getDataType())) {//多值属性下的普通属性
					basicItem.setParent(bItemPanrent.getParent() + "_" +bItemPanrent.getCode());
					basicItem.setTableName(bItemPanrent.getTableName());
					basicItem.setGroupName(bItemPanrent.getCode());
				} else {//普通属性
					basicItem.setTableName("t_" + basicItem.getParent() + "_" + basicItem.getGroupName());
					comm = "comm";
				}
			}
			basicItem.setUsingState(0);
			
			String flag = "";
			if (basicItem.getCode()== null ||basicItem.getCode() == "" || basicItem.getCode().length()<1) {
				flag = "add";
			}
			
			for(int i=0; i<10; i++) {
                try {
                	basicItemService.saveOrUpdate(basicItem, flag, comm);
        			
        			if ("记录类型".equals(basicItem.getDataType())) {
        				return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "basicItem_list");
        			} else {
        				AjaxPageResponse response = new AjaxPageResponse();
        				response.setNotice("操作成功");
        				response.setNoticeType(NoticeType.SUC);
        				return response;
        			}
                } catch (DataIntegrityViolationException e) {
                    if (i <9) {
                        continue;
                    } else {
                        return AjaxPageResponse.FAILD("主键重复, 请重新添加");
                    }
                } catch (Exception e) {
                    logger.error("操作失败", e);
                    return AjaxPageResponse.FAILD("操作失败");
                }
            }
			return null;
	}

	
	@ApiOperation(value="get节点信息", notes="获取单个节点信息")
	@ApiImplicitParams({
        @ApiImplicitParam(name="id", value="节点ID", required=true, paramType="query", dataType="String")
 })
	@ResponseBody
	@RequestMapping(value="/getOne", method=RequestMethod.POST)
	public String getOne(String id){
		BasicItem basicItem = basicItemService.getBasicItem(id);
		String jsonString = JSONObject.toJSONString(basicItem);
		return jsonString;
	}
	
	@ApiIgnore
	@ResponseBody
	@RequestMapping("/do_update")
	public AjaxPageResponse doUpdate(BasicItem basicItem){
		try {
			basicItemService.update(basicItem);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "basicItem_list");
		} catch (Exception e) {
			logger.error("修改失败", e);
			return AjaxPageResponse.FAILD("修改失败");
		}
	}
	
	//根据实体id， 获取实体下面的普通属性， 多值属性 和实体关系
	@ApiOperation(value="属性管理", notes="根据实体id， 获取实体下面的普通属性， 多值属性 和实体关系")
	@ResponseBody
	@RequestMapping(value="/attrByPid", method=RequestMethod.POST)
	public String getAttrByPid(@ApiParam(name="parentId", value="实体id", required=true)String parentId) {
		JSONObject attr = basicItemService.getAttrByPid(parentId);
		return attr.toJSONString();
	}
	
	//过期实体or正常  普通属性和多值属性
	@ApiOperation(value="过期实体", notes="过期实体or正常  普通属性和多值属性")
	@ResponseBody
	@RequestMapping(value="/saveStatus", method=RequestMethod.POST)
	public AjaxPageResponse savePastDue(@ApiParam(name="id", value="节点id", required=true)String id, @ApiParam(name="statusStr", value="过期状态值", required=true)String statusStr){
		try {
			BasicItem basicItem = basicItemService.getBasicItem(id);
			basicItemService.saveUsingStatus(basicItem, statusStr);
			
			if ("记录类型".equals(basicItem.getDataType())) {
				return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "basicItem_list");
			} else {
				AjaxPageResponse response = new AjaxPageResponse();
				response.setNotice("操作成功");
				response.setNoticeType(NoticeType.SUC);
				return response;
			}
		} catch (Exception e) {
			logger.error("操作失败", e);
			return AjaxPageResponse.FAILD("操作失败");
		}
	}
	
		//创建表
		@ApiOperation(value="创建表", notes="创建实体存储")
		@ResponseBody
		@RequestMapping(value="/createTab", method=RequestMethod.POST)
		public AjaxPageResponse createTab(){
			try {
				basicItemService.createTabCol();
				return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("操作成功", "basicItem_list");
			} catch (Exception e) {
				logger.error("操作失败", e);
				return AjaxPageResponse.FAILD("操作失败");
			}
		}
	
		//删除实体， 属性
		@ApiOperation(value="删除", notes="删除节点")
		@ApiImplicitParams({
			@ApiImplicitParam(name="id", value="节点id", required=true, dataType="String", paramType="query")
		})
		@ResponseBody
		@RequestMapping(value="/delete", method=RequestMethod.POST)
		public AjaxPageResponse delete(String id){
			try {
				AjaxPageResponse response = new AjaxPageResponse();
				List<BasicItem> btList = basicItemService.getDataByPId(id);
				if (!btList.isEmpty()) {
					
					response.setNotice("请先删除孩子");
					response.setNoticeType(NoticeType.INFO);
					return response;
				} else {
					BasicItem basicItem = basicItemService.getBasicItem(id);
					basicItemService.delete(basicItem);
					
					if ("记录类型".equals(basicItem.getDataType())) {
						return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("删除成功", "basicItem_list");
					} else {
						response.setNotice("删除成功");
						response.setNoticeType(NoticeType.SUC);
						return response;
					}
				}
			} catch (Exception e) {
				logger.error("删除失败", e);
				return AjaxPageResponse.FAILD("删除失败");
			}
		}
	
	@ResponseBody
	@RequestMapping("/getDataByPid")
	public String getDataByPid(String id){
		BasicItem basicItem = basicItemService.getBasicItem(id);
		List<BasicItem> btList = basicItemService.getDataByPId(basicItem.getParent()+ "_" + basicItem.getCode());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("child", btList);
		JSONObject json = new JSONObject(map);
		return json.toString();
	}
	
	//添加一个二级属性
	@ResponseBody
	@RequestMapping("/saveTwoLevelAttr")
	public void saveTwoLevelAttr(TowlevelattrMultiattrMapping tmm){
			tmm.setUsingState(0);
			tmms.saveOrUpdate(tmm);
	}
	
	//添加一个二级属性的孩子
	@ResponseBody
	@RequestMapping("/saveTwoLevelAttrChild")
	public void saveTwoLevelAttrChild(Towlevelattr criteria){
		criteria.setUsingState(0);
		basicItemService.createTowLevel(criteria);
	}
	
	//根据id查询一个二级属性
	@ResponseBody
	@RequestMapping("/getTwoLevelAttr")
	public String getTwoLevelAttr(Long id){
		TowlevelattrMultiattrMapping tmmObj = tmms.getTowlevelattrMultiattrMapping(id);
		String mappingId = Long.toString(tmmObj.getId());
		List<Towlevelattr> listByMappingId = towlevelattrService.getListByMappingId(mappingId);
		tmmObj.setChildList(listByMappingId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tmm", tmmObj);
		JSONObject json = new JSONObject(map);
		return json.toString();
	}
	
		//根据twolevelId   查询出字典里面的字段
		@ResponseBody
		@RequestMapping("/getDictCode")
		public String getDictCode(Long id){
			List<DictionaryBasicItem> dictCode = basicItemService.getDictCode(id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dictList", dictCode);
			JSONObject json = new JSONObject(map);
			return json.toString();
			
		}
		
		//获取多值属性的父亲， 传入参数为多值属性的code
		@ResponseBody
		@RequestMapping("/getMoreParent")
		public String getMoreParent(String code){
			BasicItem btMore = basicItemService.getBasicItem(code);
			BasicItem btParent = basicItemService.getBasicItem(btMore.getParent());
			String str  = String.valueOf(btParent.getUsingState());
			return str;
		}	
		
		//添加普通属性，c_cn_name 和当前实体下的二级属性的孩子的名称不能相同
		@ResponseBody
		@RequestMapping("/getSameCount")
		public String getSameCount(String cnName, String parent){
			BigInteger geSameCount = basicItemService.geSameCount(cnName, parent);
			if (geSameCount.equals(BigInteger.ZERO)) {
				return "true";
			} else {
				return "false";
			}
		}
		
		//添加二级属性的孩子， 二级属性孩子的名称和当前实体普通属性的名称不能相同
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
		
}
