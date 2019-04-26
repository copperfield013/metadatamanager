package cn.sowell.datacenter.model.stat.service;

import java.util.List;

import cn.sowell.datacenter.model.stat.pojo.StatExpression;

public interface StatExpressionService {
	/**
	 * 从数据库中根据条件分页查询列表
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List queryList(StatExpression criteria) throws Exception;

	/**
	 * 对象插入到数据表中
	 * @param demo
	 */
	void insert(Object obj) throws Exception;
	
	/**
	 * 对象插入到数据表中
	 * @param demo
	 */
	void saveOrUpdate(StatExpression criteria) throws Exception;

	/**
	 * 从数据库中查找对应的pojo对象
	 * @param clazz
	 * @param id
	 * @return
	 */
	<T> T get(Class<T> clazz, Integer id) throws Exception;

	/**
	 * 更新一个pojo对象
	 * @param demo
	 */
	void update(Object obj) throws Exception;

	/**
	 * 从数据库中删除一个对象
	 * @param pojo
	 */
	void delete(Object pojo) throws Exception;
	
	/**
	 * 从数据库中删除一个对象
	 * @param pojo
	 */
	void delete(Integer id) throws Exception;
	
	
	/**
	 * 获取表达式最终结果  avg(xxxx)
	 * @param expressionId
	 * @return
	 */
	String getExpressionStr(Integer expressionId);
	
}
