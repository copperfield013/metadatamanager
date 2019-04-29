package cn.sowell.datacenter.model.dictionary.strategy;

import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.AggregateAttr;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;
import cn.sowell.datacenter.model.dictionary.service.BiRefAttrService;

/**
 *    聚合分组的孩子
 * @author so-well
 *
 */
public class AggregateGroupChildStrategy implements BasicItemStrategy {
	@Override
	public void delete(BasicItemStrategyContext context,BasicItemDao basicItemDao, BiRefAttrService biRefAttrService, BasicItem basicItem) {
		try {
			AggregateAttr aggregateAttr = context.getAggregateAttrService().getOne(basicItem.getCode());
			context.getBinFilterBodyService().delete(aggregateAttr.getFiltersId());
			context.getStatExpressionService().delete(aggregateAttr.getExpressionId());
			context.getAggregateAttrService().delete(aggregateAttr.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void logicOperate(BasicItemStrategyContext context, BasicItem basicItem, String flag, Integer cascadedict, BiRefAttr biRefAttr, AggregateAttr aggregateAttr) {
		basicItem.getOneLevelItem().setTableName("t_" + basicItem.getParent() + "_" + basicItem.getOneLevelItem().getGroupName());
		aggregateAttr.setCode(basicItem.getCode());
		context.getAggregateAttrService().saveOrUpdate(aggregateAttr);
	}

}
