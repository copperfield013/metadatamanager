package cn.sowell.datacenter.model.dictionary.service;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemNodeCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItemNode;

public interface BasicItemNodeService {

	/**
	 * 根据条件对象查询分页
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<BasicItemNode> queryList(BasicItemNodeCriteria criteria, PageInfo pageInfo);

	/**
	 * 创建一个BasicItemNode对象
	 * @param BasicItemNode
	 */
	void create(BasicItemNode basicItem);

	BasicItemNode getOne(Integer id);

	/**
	 * 更新一个BasicItemNode对象
	 * @param demo
	 */
	void update(BasicItemNode basicItem);

	/**
	 * 从数据库中删除一个BasicItemNode对象
	 * @param id
	 */
	void delete(Integer id);
}
