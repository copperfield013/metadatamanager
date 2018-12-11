package cn.sowell.datacenter.model.node.service;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.node.pojo.BinFilter;
import cn.sowell.datacenter.model.node.pojo.BinFilterBody;

public interface BinFilterBodyService {

	/**
	 * 根据条件对象查询分页
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<BinFilterBody> queryList(BinFilterBody criteria, PageInfo pageInfo) throws Exception;

	BinFilterBody getBinFilterBody(Integer id) throws Exception;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	BinFilter getBinFilterByNodeId(Integer id) throws Exception;

	/**
	 * 更新一个BinFilterBody对象
	 * @param demo
	 */
	void update(Object basicItem) throws Exception;
	
	void insert(Object basicItem) throws Exception;
	
	List<BinFilterBody> getFilterBodyChild(Integer parentId) throws Exception;
	
	/**
	 * 从数据库中删除一个BinFilterBody对象
	 * @param id
	 * @param isDelChil  属性组出过来true,就删除孩子， 否则不删除孩子， 移动孩子到实体下面
	 * 
	 */
	 
	void delete(Integer id) throws Exception;
	
	
	void copyNode(Integer nodeId) throws Exception;
}
