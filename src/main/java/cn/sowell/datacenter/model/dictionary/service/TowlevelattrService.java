package cn.sowell.datacenter.model.dictionary.service;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.TowlevelattrCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.Towlevelattr;

public interface TowlevelattrService {

	/**
	 * 根据条件对象查询分页
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<Towlevelattr> queryList(TowlevelattrCriteria criteria, PageInfo pageInfo);

	/**
	 * 创建一个Towlevelattr对象
	 * @param Towlevelattr
	 */
	void create(Towlevelattr basicItem);

	Towlevelattr getTowlevelattr(String code);

	/**
	 * 更新一个Towlevelattr对象
	 * @param demo
	 */
	void update(Towlevelattr basicItem);

	/**
	 * 从数据库中删除一个Towlevelattr对象
	 * @param id
	 */
	void delete(String code);

	List getListByMappingId(String id);
}
