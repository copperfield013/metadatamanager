package cn.sowell.datacenter.model.node.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.node.criteria.BasicItemNodeCriteria;
import cn.sowell.datacenter.model.node.pojo.BasicItemNode;

public interface BasicItemNodeDao {
	/**
	 * 从数据库中根据条件分页查询列表
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<BasicItemNode> queryList(BasicItemNodeCriteria criteria, PageInfo pageInfo);

	/**
	 * 对象插入到数据表中
	 * @param demo
	 */
	void insert(Object obj);

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
	void update(BasicItemNode obj);

	/**
	 * 从数据库中删除一个对象
	 * @param pojo
	 */
	void delete(Integer id);
	/**
	 * 从数据库中删除一个对象
	 * @param pojo
	 */
	void delete(BasicItemNode bt);

	/**
	 * 根据父id， 获取所有孩子
	 * @param integer
	 * @return
	 */
	List<BasicItemNodeCriteria> getChildByPid(Integer parentId);
	
	
	/**
	 * 根据父id， 获取所有孩子
	 * @param integer
	 * @return
	 */
	public List<BasicItemNode> getChildByParent(Integer parentId);
	
	/**
	 * 
	 * @param parentId   父id
	 * @param abcattrCode  实体的多指code， 
	 * @return  获取已被选择的多值属性
	 */
	public List<BasicItemNode> getChildByParent(Integer parentId, String abcattrCode);
	
	public void executeSql(String sql);

	/**
	 * 获取当前父亲下面的排序值
	 * @param basicItemNode
	 * @return
	 */
	Integer getOrder(BasicItemNode basicItemNode);
	
	/**
	 * 获取当前实体下和实体下的属性组下的所有字段的名字
	 * @param parentId
	 * @return
	 */
	List<String> getNameByPid(BasicItemNode basicItemNode);
	
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
	
	/**
	 * 获取排序执行语句
	 * @param parentId
	 * @return
	 */
	List<String> getNoteSort(Integer parentId);
	
	/**
	 * 根据abc的id 获取abc对象
	 * @param name
	 * @return
	 */
	BasicItemNode getAbc(Long id);

	BasicItemNode getRelaNodeChil(Integer parentId, String id, Integer type);
	
	/**
	 * 获取所有数据
	 * @return
	 */
	List<BasicItemNode> getAllData()  throws Exception;

	/**
	 * 根据父id， 获取孩子的opt集合
	 * @param id
	 * @return
	 */
	List getChildOptList(Integer id);
}
