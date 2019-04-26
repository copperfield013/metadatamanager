package cn.sowell.datacenter.admin.controller.dictionary;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abc.model.enun.AggregateAttrType;
import com.alibaba.fastjson.JSONObject;

import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.model.dictionary.pojo.AggregateAttr;
import cn.sowell.datacenter.model.dictionary.service.AggregateAttrService;

@Controller
@RequestMapping(AdminConstants.URI_DICTIONARY + "/aggregateAttr")
public class AggregateAttrController {
	
	@Resource
	AggregateAttrService aggregateAttrService;
	
	//获取聚合属性
    @ResponseBody
	@RequestMapping("/getAggregateAttr")
	public String getAggregateAttr(String code){
    	Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			AggregateAttr aggregateAttr = aggregateAttrService.getOne(code);
			map.put("code", 200);
			map.put("msg", "操作成功");
			map.put("aggregateAttr", aggregateAttr);
			return jobj.toString();
		} catch (Exception e) {
			map.put("code", 400);
			map.put("msg", "操作失败");
			return jobj.toString();
		}
	}

    
  //获取聚合属性类型枚举
    @ResponseBody
	@RequestMapping("/getAggregateAttrType")
	public String getAggregateAttrType(){
    	Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			AggregateAttrType[] values = AggregateAttrType.values();
			Map<String, Object> aggregateAttrTypeMap = new HashMap<String, Object>();
			for (AggregateAttrType aggregateAttrType : values) {
				if (AggregateAttrType.NOTHING.equals(aggregateAttrType) ) {
					continue;
				}
				aggregateAttrTypeMap.put(String.valueOf(aggregateAttrType.getIndex()), aggregateAttrType.getCName());
			}
			
			map.put("code", 200);
			map.put("msg", "操作成功");
			map.put("aggregateAttrTypeMap", aggregateAttrTypeMap);
			return jobj.toString();
		} catch (Exception e) {
			map.put("code", 400);
			map.put("msg", "操作失败");
			return jobj.toString();
		}
	}

}
