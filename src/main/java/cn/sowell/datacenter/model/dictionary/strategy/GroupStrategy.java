package cn.sowell.datacenter.model.dictionary.strategy;


import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.AggregateAttr;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;
import cn.sowell.datacenter.model.dictionary.service.BiRefAttrService;

/**
 *    ValueType.GROUP
 *    	分组类型->包括普通分组和聚合分组
 * @author so-well
 *
 */
public class GroupStrategy implements BasicItemStrategy {
	@Override
	public void delete(BasicItemStrategyContext context, BasicItemDao basicItemDao, BiRefAttrService biRefAttrService, BasicItem basicItem) {
		
	}

	@Override
	public void logicOperate(BasicItemStrategyContext context, BasicItem basicItem, String flag, Integer cascadedict, BiRefAttr biRefAttr, AggregateAttr aggregateAttr) {
		basicItem.getOneLevelItem().setNeedHistory(1);
		basicItem.getOneLevelItem().setDictParentId(0);
	}
}
