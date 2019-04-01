package cn.sowell.datacenter.model.dictionary.dao;

import java.util.List;

public interface BasicItemCodeGeneratorDao {
	/**
	 * 对象插入到数据表中
	 * @param demo
	 */
	void insert(Object obj);
	
	
	/**
	 * 获取实体和属性前缀
	 * @return
	 * @throws Exception
	 */
	public String getBasicItemFix() throws Exception;
		
}
