package cn.sowell.datacenter.model.dictionary.strategy;


import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.AggregateAttr;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;
import cn.sowell.datacenter.model.dictionary.service.BiRefAttrService;

/**
 *    ValueType.GROUP
 *    	普通分组的孩子
 * @author so-well
 *
 */
public class CommGroupChildStrategy implements BasicItemStrategy {
	@Override
	public void delete(BasicItemStrategyContext context, BasicItemDao basicItemDao, BiRefAttrService biRefAttrService, BasicItem basicItem) {
		
	}

	@Override
	public void logicOperate(BasicItemStrategyContext context, BasicItem basicItem, String flag, Integer cascadedict, BiRefAttr biRefAttr, AggregateAttr aggregateAttr) {
		basicItem.getOneLevelItem().setTableName("t_" + basicItem.getParent() + "_" + basicItem.getOneLevelItem().getGroupName());
	}
}
