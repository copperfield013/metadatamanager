package cn.sowell.datacenter.model.cascadedict.dao;

import java.util.List;

import org.hibernate.HibernateException;

import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictSubsection;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictSubsectionChild;

public interface CascadedictSubsectionDao {

	/**
	 * 获取subSelection列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	List<CascadedictSubsection> getSubSelectByParentId(String parentId) throws Exception;
	
	/**
	 * 获取subChild列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	List<CascadedictSubsectionChild> getSubChildByPid(String subsectionId) throws Exception;
	
	/**
	 * 对象插入到数据表中
	 * @param demo
	 */
	void insert(Object obj) throws Exception;

	/**
	 * 从数据库中查找对应的pojo对象
	 * @param clazz
	 * @param id
	 * @return
	 */
	<T> T get(Class<T> clazz, String id) throws Exception;

	/**
	 * 更新一个pojo对象
	 * @param demo
	 */
	void update(Object obj) throws Exception;

	/**
	 * 从数据库中删除一个对象
	 * @param pojo
	 */
	void deleteByObj(Object pojo) throws Exception;
	
	/**
	 * 从数据库中删除一个对象
	 * @param pojo
	 */
	void deleteById(String id) throws Exception;
	
	/**
	 * 删除subChild， 根据id删除
	 * @param id
	 * @throws Exception
	 */
	void delSubChildById(String id) throws Exception;
	
	/**
	 * 主键id生成
	 * @return
	 */
	public String createId();
			
	
}
