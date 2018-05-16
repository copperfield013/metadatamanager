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
			put("枚举", "枚举");
		}
	};
	
	Map<String, Integer> USING_STATE_MAP = new HashMap<String, Integer>(){
		{
			put("normal", 0);//新增
			put("pastDue", 2);//已过期
			put("error", -1);//-1 有错误（执行 更新实体存储时报错）
			put("using", 1 );//1 在用 （成功执行 更新实体存储后）
		}
	};
	
}
