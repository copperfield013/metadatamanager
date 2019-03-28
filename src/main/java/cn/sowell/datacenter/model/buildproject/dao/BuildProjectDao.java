package cn.sowell.datacenter.model.buildproject.dao;

import java.util.List;

public interface BuildProjectDao {
	/**
	 *  	获取枚举数据
	 * @return
	 */
	List<String> getEnumData();
	
	/**
	 * 	获取关系数据
	 * @return
	 */
	List<String> getRelationData();
	
	 /**
	  * 	获取实体的item 属性字段
	  * @param entityCode    实体code
	  * @param entityPrefix   实体前缀
	  * @return
	  */
	List<String> getItemData(String entityCode, String entityPrefix);
	 
	 /**
	  * 建立项目
	  * @return
	  */
	 String buildProject();
}
