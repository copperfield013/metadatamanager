package cn.sowell.datacenter.model.buildproject.service;

import java.io.File;
import java.util.List;

public interface BuildProjectService {
	/**
	 *  	获取枚举数据
	 * @return
	 */
	void createEnumFile(File file, String fileName);
	
	/**
	 * 	获取关系数据
	 * @return
	 */
	void createRelationFile(File file, String fileName);
	
	 /**
	  * 	获取实体的item 属性字段
	  * @param entityCode    实体code
	  * @param entityPrefix   实体前缀
	  * @return
	  */
	void createItemFile(File file, String fileName, String entityCode, String entityPrefix);
	 
	 /**
	  * 建立项目
	  * @return
	  */
	 String buildProject(List<String> entityCodes);
	 
	 /**
	  * 压缩文件
	  * @return
	  */
	 String zipCompress();
			 
	 
}
