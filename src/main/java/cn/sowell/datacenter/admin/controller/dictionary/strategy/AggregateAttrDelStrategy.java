package cn.sowell.datacenter.admin.controller.dictionary.strategy;

import com.abc.util.AttributeParter;

import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.AggregateAttr;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.service.BiRefAttrService;

/**
 *    聚合属性删除策略
 * @author so-well
 *
 */
public class AggregateAttrDelStrategy implements BasicItemDelStrategy {
	@Override
	public void delete(BasicItemDelContext context,BasicItemDao basicItemDao, BiRefAttrService biRefAttrService, BasicItem basicItem) {
		try {
			AggregateAttr aggregateAttr = context.getAggregateAttrService().getOne(basicItem.getCode());
			context.getBinFilterBodyService().delete(aggregateAttr.getFiltersId());
			context.getStatExpressionService().delete(aggregateAttr.getExpressionId());
			context.getAggregateAttrService().delete(aggregateAttr.getCode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
