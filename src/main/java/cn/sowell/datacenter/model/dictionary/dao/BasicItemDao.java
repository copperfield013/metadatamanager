package cn.sowell.datacenter.model.dictionary.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;

public interface BasicItemDao {
	/**
	 * 从数据库中根据条件分页查询列表
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<BasicItem> queryList(BasicItemCriteria criteria);

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
	<T> T get(Class<T> clazz, String id);

	/**
	 * 更新一个pojo对象
	 * @param demo
	 */
	void update(Object obj);

	/**
	 * 从数据库中删除一个对象
	 * @param pojo
	 */
	void delete(Object pojo);
	
	/**
	 * 根据父id获取孩子
	 * @param parent
	 * @return
	 */
	List<BasicItem> getDataByPId(String parent);

	/**
	 * 
	 * @param parent  父亲id
	 * @param groupName   分组名称
	 * @return
	 */
	List getAttrByPidGroupName(String parent, String groupName);
	
	void saveOrUpdate(Object obj);
	
	/**
	 * -- 查询需要创建的表
	 */
	List queryCreTab();
	
	/**
	 * -- 要新增的字段  
	 * @return
	 */
	List queryNewAddCol();
	
	/**
	 *  创建关系表
	 * @return
	 */
	List queryCreRelaTab();
	
	/**
	 * 执行传进来的sql语句
	 * @param sql
	 */
	void excuteBySql(String sql);
}
