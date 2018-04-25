package cn.sowell.datacenter;

import java.util.HashMap;
import java.util.Map;

public interface DataCenterConstants {

	final Integer VALUE_TRUE = 1;
	final String COMMON_SPLITER = ";";
	
	final String TEMPLATE_TYPE_LIST = "list"; 
	final String TEMPLATE_TYPE_DETAIL = "detail";
	
	final String MODULE_KEY_PEOPLE = "people";
	final String MODULE_KEY_ADDRESS = "address";
	final String MODULE_KEY_STUDENT = "student";
	final String MODULE_KEY_DISABLEDPEOPLE = "disabledpeople";
	final String MODULE_KEY_HSPEOPLE = "hspeople";
	final Map<Class<?>, String> _TEMPLATE_MAP = new HashMap<>();
	
	

}
