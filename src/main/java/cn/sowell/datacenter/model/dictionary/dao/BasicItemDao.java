package cn.sowell.datacenter.model.dictionary.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	 * 查询除了多值属性唯一编码和多值属性编辑时间
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
	
	void saveOrUpdate(BasicItem obj, String flag);
	
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
	 * 查询需要更新的字段语句
	 * 页面修改的属性， 需要重新  更新实体存储
	 * @return
	 */
	List queryEditCol();
	
	List queryCreRelaTabFun();
	
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
	
	//实体code  生成规则
	public String getEntityCode();
	
	//其他code， 生成规则
	public String getAttrCode();
	
	/**
	 * ##查询出当前实体生成的表，，
	 *  如果为null, 证明没生成表， 则标记为新增， 否则， 修改实体为再用
	 * @param entityCode
	 * @return
	 */
	public List queryEntityTable(String entityCode);
	
	/**
	 * ## 查询当前实体已经生成的字段
	 * @return
	 */
	public List queryEntityCol(String entityCode);
	
	/**
	 *  * 根据父id获取所有孩子
	 * @param parent
	 * @return
	 */
	public List<BasicItem> getChilByPid(String parent);
	/**
	 * 根据cnName和实体id， 查询二级属性中是否有相同的名字， 有则返回数字大于0无则返回0
	 * @param cnName
	 * @param entityId
	 * @return
	 */
	BigInteger geSameCount(String cnName, String entityId);

	/**
	 * 根据name和实体id， 查询普通属性中是否有相同的名字， 有则返回数字大于0无则返回0
	 * @param name
	 * @param entityId
	 * @return
	 */
	BigInteger getTwoSameCount(String name, String entityId);
	
	/**
	 * 根据实体id， 获取当前实体下的所有普通属性， 
		//还有当前实体下的所有二级属性
	 * @param entityId
	 * @return
	 */
	public List getComm(String entityId);
	
	/**
	 * 根据左实体id， 获取与左实体有关系的右实体
	 * @param leftRecordType 左实体id
	 * @return
	 */
	public List<BasicItem> getEntityList(String leftRecordType);
}
