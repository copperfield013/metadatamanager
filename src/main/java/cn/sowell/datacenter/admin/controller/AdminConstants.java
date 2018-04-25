package cn.sowell.datacenter.admin.controller;

import java.util.LinkedHashMap;
import java.util.Map;

public interface AdminConstants {
	@SuppressWarnings("serial")
	final Map<String, String> ERROR_CODE_MAP = new LinkedHashMap<String, String>(){
		{
			put("1", "用户名或密码错误");
			put("2", "");
		}
	};

	/**
	 * 管理端的访问基本路径
	 */
	final String URI_BASE = "/admin";
	
	/**
	 * 人口模块
	 */
	final String URI_PEOPLEDATA = URI_BASE + "/peopledata";

	/**
	 * 模板模块
	 */
	final String URI_TMPL = URI_BASE + "/tmpl";
	
	/**
	 * 各个数据模块的
	 */
	final String URI_MODULES = URI_BASE + "/modules";
	
	final String URI_DICTIONARY = URI_BASE + "/dictionary"; 
	
	/**
	 * 管理端jsp资源的基本路径
	 */
	final String JSP_BASE = "/admin";

	/**
	 * 
	 */
	final String JSP_DEMO = JSP_BASE + "/demo";
	
	final String JSP_DICTIONARY = JSP_BASE + "/dictionary";
	
	/**
	 *  管理端人口资源路径
	 */
	final String JSP_BASEPEOPLE = JSP_BASE + "/basepeople";


	final String JSP_FIELD = JSP_BASE + "/basefield";

	final String JSP_PEOPLEDATA = JSP_BASE + "/peopledata";
	
	final String JSP_SEARCH = JSP_BASE + "/search";


	/**
	 * 字段属性类型
	 */
	//下拉
	final String[] FRELD ={"2","3","4"};;
	
	final String JSP_ADDRESS = JSP_BASE + "/address";
	
	final String JSP_POSITION = JSP_BASE + "/position";
	
	final String JSP_SPECIAL_POSITION = JSP_BASE + "/specialposition";

	final String JSP_PEOPLEDATA_VIEWTMPL = JSP_PEOPLEDATA + "/viewtmpl";

	final String JSP_PEOPLEDATA_TMPL = JSP_PEOPLEDATA + "/tmpl";
	
	final String JSP_TMPL = JSP_BASE + "/tmpl";

	final String JSP_TMPL_LIST = JSP_TMPL + "/ltmpl";
	
	final String JSP_TMPL_DETAIL = JSP_TMPL + "/dtmpl";
	
	final String JSP_TMPL_GROUP = JSP_TMPL + "/group";
	
	final String JSP_MODULES = JSP_BASE + "/modules";
	
	/**
	 * 默认的系统id
	 */
	final long SYS_DEF = 1;

	final String EXPORT_PEOPLE_STATUS_UUID = "export_people_status_uuid";

	final String KEY_IMPORT_STATUS = "import_status_";


	/**
	 * 用于弹出框选择的页面。
	 * 该页面需要一个name为tpage的{@linkplain cn.sowell.copframe.dto.choose.ChooseTablePage ChooseTablePage}对象传到attribute中
	 * <p>ChooseTablePage对象需要设置的几个属性</p>
	 * <ol>
	 * <li>构造时需要传入页面id和构造json数据对象的前缀</li>
	 * <li>分页对象pageInfo</li>
	 * <li>是否多选</li>
	 * <li>设置表格中的数据{@linkplain cn.sowell.copframe.dto.choose.ChooseTablePage#setTableData(java.util.List, java.util.function.Consumer) setTableData}</li>
	 * </ol>
	 */
	final String PATH_CHOOSE_TABLE = URI_BASE + "/common/choose_table.jsp";


	
	
}
