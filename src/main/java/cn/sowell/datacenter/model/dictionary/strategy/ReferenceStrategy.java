package cn.sowell.datacenter.model.dictionary.strategy;

import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.AggregateAttr;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;
import cn.sowell.datacenter.model.dictionary.service.BiRefAttrService;

/**
 *    引用类型
 * @author so-well
 *
 */
public class ReferenceStrategy implements BasicItemStrategy {
	
	@Override
	public void delete(BasicItemStrategyContext context,BasicItemDao basicItemDao, BiRefAttrService biRefAttrService, BasicItem basicItem) {
		BiRefAttr biRefAttr = new BiRefAttr();
		biRefAttr.setCode(basicItem.getCode());
		biRefAttrService.delete(biRefAttr);
	}

	@Override
	public void logicOperate(BasicItemStrategyContext context, BasicItem basicItem, String flag, Integer cascadedict, BiRefAttr biRefAttr, AggregateAttr aggregateAttr) {
		//引用类型   默认存放在  t_sc_bi_ref_attr  这个表中
		biRefAttr.setCode(basicItem.getCode());
		context.getBiRefAttrService().saveOrUpdate(biRefAttr);
	}	
		
}
