package cn.sowell.datacenter.admin.controller.dictionary;

import java.util.HashMap;
import java.util.Map;

public interface Constants {
	
	/**
	 * 数据类型
	 */
	 Map<String, Object> DATA_TYPE_MAP = new HashMap<String, Object>(){
		{
			put("char", "字符型");
			put("digital", "数字型");
			put("digitalDecimal", "数字型小数");
			put("date", "日期型");
			put("dateTime", "时间型");
			put("record", "记录类型");
			put("repeat", "重复类型");
			put("group", "分组类型");
		}
	};
	
	Map<String, Integer> USING_STATE_MAP = new HashMap<String, Integer>(){
		{
			put("normal", 1);//正常
			put("pastDue", 2);//过期
		}
	};
	
}
