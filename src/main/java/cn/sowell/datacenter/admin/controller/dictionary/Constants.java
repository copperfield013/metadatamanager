package cn.sowell.datacenter.admin.controller.dictionary;

import java.util.HashMap;
import java.util.Map;

import com.abc.util.ValueType;

public interface Constants {
	
	/**
	 * 元数据管理--数据类型
	 */
	Map<String, Object> DATA_TYPE_MAP = new HashMap<String, Object>(){
		{
			put(String.valueOf(ValueType.STRING.getIndex()), ValueType.STRING.getCName());
			put(String.valueOf(ValueType.INT.getIndex()), ValueType.INT.getCName());
			put(String.valueOf(ValueType.NUMBER.getIndex()), ValueType.NUMBER.getCName());
			put(String.valueOf(ValueType.DATE.getIndex()), ValueType.DATE.getCName());
			put(String.valueOf(ValueType.DATETIME.getIndex()), ValueType.DATETIME.getCName());
			put(String.valueOf(ValueType.RECORD.getIndex()), ValueType.RECORD.getCName());
			put(String.valueOf(ValueType.REPEAT.getIndex()), ValueType.REPEAT.getCName());
			put(String.valueOf(ValueType.GROUP.getIndex()), ValueType.GROUP.getCName());
			put(String.valueOf(ValueType.ENUMTYPE.getIndex()), ValueType.ENUMTYPE.getCName());
			put(String.valueOf(ValueType.BYTES.getIndex()), ValueType.BYTES.getCName());
			put(String.valueOf(ValueType.REFERENCE.getIndex()), ValueType.REFERENCE.getCName());
			put(String.valueOf(ValueType.CASCADETYPE.getIndex()), ValueType.CASCADETYPE.getCName());
			
			put(String.valueOf(ValueType.STRING_PREENUM.getIndex()), ValueType.STRING_PREENUM.getCName());
			put(String.valueOf(ValueType.ENUMTYPE_MULTI.getIndex()), ValueType.ENUMTYPE_MULTI.getCName());
		}
	};
	
	/**
	 * 元数据管理--数据类型
	 *//*
	Map<String, ValueType> DATA_TYPE_MAP = new HashMap<String, ValueType>(){
		{
			put("char", ValueType.STRING);
			put("digital", ValueType.INT);
			put("digitalDecimal", ValueType.NUMBER);
			put("date", ValueType.DATE);
			put("dateTime", ValueType.DATETIME);
			put("record", ValueType.RECORD);
			put("repeat", ValueType.REPEAT);
			put("group", ValueType.GROUP);
			put("enumtype", ValueType.ENUMTYPE);
			put("bytes", ValueType.BYTES);
			put("referenceType", ValueType.REFERENCE);
		}
	};*/
	
	/**
	 * 数据类型
	 *//*
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
			put("文件型", "文件型");
			put("referenceType", "引用类型");
		}
	};*/
	
	Map<String, Integer> USING_STATE_MAP = new HashMap<String, Integer>(){
		{
			put("normal", 0);//新增
			put("pastDue", 2);//已过期
			put("error", -1);//-1 有错误（执行 更新实体存储时报错）
			put("using", 1 );//1 在用 （成功执行 更新实体存储后）
		}
	};
	
}
