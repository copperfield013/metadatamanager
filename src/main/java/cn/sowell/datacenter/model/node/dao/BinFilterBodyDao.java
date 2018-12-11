package cn.sowell.datacenter.model.node.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.node.pojo.BinFilter;
import cn.sowell.datacenter.model.node.pojo.BinFilterBody;

public interface BinFilterBodyDao {
	/**
	 * 从数据库中根据条件分页查询列表
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<BinFilterBody> queryList(BinFilterBody criteria, PageInfo pageInfo) throws Exception;

	/**
	 * 对象插入到数据表中
	 * @param demo
	 */
	void insert(Object obj) throws Exception;
	
	/**
	 * 对象插入到数据表中
	 * @param demo
	 */
	void saveOrUpdate(Object obj) throws Exception;

	/**
	 * 从数据库中查找对应的pajo对象
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

	List<BinFilterBody> getFilterBodyChild(Integer parentId) throws Exception;
	
	/**
	 * 从数据库中删除一个对象
	 * @param pojo
	 */
	void delete(Integer id) throws Exception;
	/**
	 * 从数据库中删除一个对象
	 * @param pojo
	 */
	void delete(BinFilterBody bt) throws Exception;

	BinFilter getBinFilterByNodeId(Integer parentNodeId)throws Exception;
}
