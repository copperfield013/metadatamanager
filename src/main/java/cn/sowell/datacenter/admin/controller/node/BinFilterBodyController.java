package cn.sowell.datacenter.admin.controller.node;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abc.mapping.node.NodeOpsType;
import com.abc.mapping.node.NodeType;
import com.abc.util.CriteriaSymbol;
import com.alibaba.fastjson.JSONObject;

import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.model.node.pojo.BinFilter;
import cn.sowell.datacenter.model.node.pojo.BinFilterBody;
import cn.sowell.datacenter.model.node.service.BinFilterBodyService;
import cn.sowell.datacenter.utils.NodeTypeMappingOps;

@Controller
@RequestMapping(AdminConstants.URI_NODE + "/binFilterBody")
public class BinFilterBodyController {
	
	Logger logger = Logger.getLogger(BinFilterBodyController.class);
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(ServletRequestDataBinder binder) {
		System.out.println("执行了InitBinder方法");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}
	
	@Resource
	BinFilterBodyService binFilterBodyService;
	
	
	
	/**
	 * 根据节点id获取节点的filters
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getFilters")
	public String getFilters(Integer nodeId){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			BinFilter binFilter = binFilterBodyService.getBinFilterByNodeId(nodeId);
			BinFilterBody binFilterBody = null;
			if (binFilter != null) {
				binFilterBody = binFilterBodyService.getBinFilterBody(binFilter.getFiltersId());
			}
			
			map.put("binFilter", binFilter);
			map.put("binFilterBody", binFilterBody);
			map.put("code", 200);
			map.put("msg", "操作成功！");
			return jobj.toJSONString();
		}catch (Exception e) {
			logger.debug(e);
			map.put("code", 400);
			map.put("msg", "操作失败！");
			return jobj.toJSONString();
		}
	}
	
	
	
	/**
	 * 
	 * @param criteria
	 * @param isBody  
	 * 		判断BinFilterBody还是BinFilter
	 * 		BinFilter: true
	 * 		BinFilterBody:false
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveOrUpdate")
	public String saveOrUpdate(BinFilterBody criteria, boolean isFilters){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			
			boolean isAdd = true;
			if (criteria.getId() != null) {
				isAdd = false;
			}
			BinFilter binFilter =null;
			Integer parentId = criteria.getParentId();
			if (isFilters) {//是filters
				criteria.setParentId(null);
			} 
			//添加
			if (isAdd) {
				binFilterBodyService.insert(criteria);
				
				if (isFilters) {//是filters
					binFilter = new BinFilter(criteria.getId(), parentId);
					binFilterBodyService.insert(binFilter);
				} 
			} else { //更新
				binFilterBodyService.update(criteria);
			}
			
			map.put("binFilter", binFilter);
			map.put("binFilterBody", criteria);
			map.put("code", 200);
			map.put("msg", "操作成功！");
			return jobj.toJSONString();
		}catch (Exception e) {
			e.printStackTrace();
			map.put("code", 400);
			map.put("msg", "操作失败！");
			return jobj.toJSONString();
		}
	}
	
	
	/**
	 * 根据id删除
	 * @param id 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doDelete")
	public String doDelete(Integer id){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			binFilterBodyService.delete(id);
			
			map.put("code", 200);
			map.put("msg", "操作成功！");
			return jobj.toJSONString();
		}catch (Exception e) {
			logger.debug(e);
			map.put("code", 400);
			map.put("msg", "操作失败！");
			return jobj.toJSONString();
		}
	}
	
	
	@ResponseBody
	@RequestMapping("/getCriteriaSymbol")
	public String getCriteriaSymbol(Integer dataType){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			
			Map<String, Object>  symbolMap = new HashMap<String, Object> ();
			if (NodeType.RFILTER.equals(NodeType.getNodeType(dataType))) {
				symbolMap.put(String.valueOf(CriteriaSymbol.INCLUDES.getCode()), CriteriaSymbol.INCLUDES.getName());
				symbolMap.put(String.valueOf(CriteriaSymbol.NOT_INCLUDES.getCode()), CriteriaSymbol.NOT_INCLUDES.getName());
			} else {
				CriteriaSymbol[] values = CriteriaSymbol.values();
				for (CriteriaSymbol criteriaSymbol : values) {
					symbolMap.put(String.valueOf(criteriaSymbol.getCode()), criteriaSymbol.getName());
				}
			}
			
			map.put("symbolMap", symbolMap);
			map.put("code", 200);
			map.put("msg", "操作成功！");
			return jobj.toJSONString();
		}catch (Exception e) {
			logger.debug(e);
			map.put("code", 400);
			map.put("msg", "操作失败！");
			return jobj.toJSONString();
		}
	}
	
	
	
	@ResponseBody
	@RequestMapping("/getFiltersChild")
	public String getFiltersChild(Integer parentId){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			List<BinFilterBody> filterBodyChild = binFilterBodyService.getFilterBodyChild(parentId);
			map.put("filterBodyChild", filterBodyChild);
			map.put("code", 200);
			map.put("msg", "操作成功！");
			return jobj.toJSONString();
		}catch (Exception e) {
			logger.debug(e);
			map.put("code", 400);
			map.put("msg", "操作失败！");
			return jobj.toJSONString();
		}
	}
	
	@ResponseBody
	@RequestMapping("/getFiltersOpt")
	public String getFiltersOpt(Integer opsCode){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			
			Map<String, Object>  optMap = new HashMap<String, Object> ();
			NodeType nodeType = NodeType.getNodeType(opsCode); 
			Collection<NodeOpsType> opsSetByNodeType = NodeTypeMappingOps.getOpsSetByNodeType(nodeType);
			for (NodeOpsType nodeOpsType : opsSetByNodeType) {
				optMap.put(String.valueOf(nodeOpsType.getIndex()), nodeOpsType.getName());
			}
			
			map.put("optMap", optMap);
			map.put("code", 200);
			map.put("msg", "操作成功！");
			return jobj.toJSONString();
		}catch (Exception e) {
			logger.debug(e);
			map.put("code", 400);
			map.put("msg", "操作失败！");
			return jobj.toJSONString();
		}
	}
	

}
