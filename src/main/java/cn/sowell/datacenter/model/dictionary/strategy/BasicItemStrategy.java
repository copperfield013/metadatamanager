package cn.sowell.datacenter.model.dictionary.strategy;

import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.AggregateAttr;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;
import cn.sowell.datacenter.model.dictionary.service.BiRefAttrService;

public interface BasicItemStrategy {
	/**
	 * 	删除操作
	 * @param basicItemDelContext
	 * @param basicItemDao
	 * @param biRefAttrService
	 * @param basicItem
	 */
	public void delete(BasicItemStrategyContext basicItemDelContext, BasicItemDao basicItemDao, BiRefAttrService biRefAttrService, BasicItem basicItem);
	
	/**
	 *	 逻辑操作
	 */
	public void logicOperate(BasicItemStrategyContext basicItemDelContext, BasicItem basicItem, String flag, Integer cascadedict, BiRefAttr biRefAttr, AggregateAttr aggregateAttr) throws Exception;
}
