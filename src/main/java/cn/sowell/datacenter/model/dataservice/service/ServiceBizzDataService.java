package cn.sowell.datacenter.model.dataservice.service;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dataservice.criteria.ServiceBizzDataCriteria;
import cn.sowell.datacenter.model.dataservice.pojo.ServiceBizzData;

public interface ServiceBizzDataService {

	/**
	 * 根据条件对象查询分页
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<ServiceBizzData> queryList(ServiceBizzDataCriteria criteria, PageInfo pageInfo);


	ServiceBizzData getOne(Integer id);

	/**
	 * 更新一个ServiceBizzData对象
	 * @param demo
	 */
	void update(ServiceBizzData basicItem);
	
	/**
	 * 创建一个对象
	 * @param sd
	 */
	void insert(ServiceBizzData sd);

	/**
	 * 从数据库中删除一个ServiceBizzData对象
	 * @param id
	 * 
	 */
	void delete(Integer id);
}
