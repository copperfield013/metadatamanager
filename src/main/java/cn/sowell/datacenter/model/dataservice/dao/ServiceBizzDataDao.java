package cn.sowell.datacenter.model.dataservice.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dataservice.criteria.ServiceBizzDataCriteria;
import cn.sowell.datacenter.model.dataservice.pojo.ServiceBizzData;

public interface ServiceBizzDataDao {
	/**
	 * 从数据库中根据条件分页查询列表
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<ServiceBizzData> queryList(ServiceBizzDataCriteria criteria, PageInfo pageInfo);

	/**
	 * 对象插入到数据表中
	 * @param demo
	 */
	void insert(ServiceBizzData obj);

	/**
	 * 从数据库中查找对应的pajo对象
	 * @param clazz
	 * @param id
	 * @return
	 */
	<T> T get(Class<T> clazz, Integer id);

	/**
	 * 更新一个pojo对象
	 * @param demo
	 */
	void update(ServiceBizzData obj);

	/**
	 * 从数据库中删除一个对象
	 * @param pojo
	 */
	void delete(Integer id);
	/**
	 * 从数据库中删除一个对象
	 * @param pojo
	 */
	void delete(ServiceBizzData bt);
}
