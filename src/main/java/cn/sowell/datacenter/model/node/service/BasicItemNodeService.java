package cn.sowell.datacenter.model.node.service;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.node.criteria.BasicItemNodeCriteria;
import cn.sowell.datacenter.model.node.pojo.BasicItemNode;

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
	 * @param BasicItemNodeApi
	 */
	void saveOrUpdate(BasicItemNode basicItem);

	BasicItemNode getOne(Integer id);

	/**
	 * 更新一个BasicItemNode对象
	 * @param demo
	 */
	void update(BasicItemNode basicItem);

	/**
	 * 从数据库中删除一个BasicItemNode对象
	 * @param id
	 * @param isDelChil  属性组出过来true,就删除孩子， 否则不删除孩子， 移动孩子到实体下面
	 * 
	 */
	 
	void delete(Integer id, boolean isDelChil);
	
	/**
	 * 节点排序
	 * @param parentId 父id
	 * @param currentId  当前排序id
	 * @param beforeId 前驱id
	 * @param afterId  后继id
	 * @return   
	 */
	public void nodeSort(BasicItemNode current, String beforeId, String afterId);
	/**
	 * 扩展语句
	 */
	public void excuExtend(String parentId);
	
	/**
	 * 通过
	 * @param nodeId
	 * @return
	 */
	List<BasicItemNode> getChildNode(String nodeId);
	
	/**
	 * 判断当前父节点下有没有重复的名字
	 * @param name  
	 * @param parentId
	 * @return  重复返回true， 不重复放回false
	 */
	boolean check(BasicItemNode basicItemNode);

	/**
	 * 获取所有abc根节点
	 * @return
	 */
	List<BasicItemNode> getAllAbc();
	
	/**
	 * 根据abc的id，获取abc下面所有的attribute以及属性组下面的attribute
	 * @param parentId
	 * @return
	 */
	List<BasicItemNode> getAttribute(String abcId);
}
