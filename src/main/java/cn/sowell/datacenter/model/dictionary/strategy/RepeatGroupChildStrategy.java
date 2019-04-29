package cn.sowell.datacenter.model.dictionary.strategy;


import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.AggregateAttr;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;
import cn.sowell.datacenter.model.dictionary.service.BiRefAttrService;

/**
 *    ValueType.REPEAT
 *    	如果父亲是重复类型， 孩子应该执行的代码
 * @author so-well
 *
 */
public class RepeatGroupChildStrategy implements BasicItemStrategy {
	@Override
	public void delete(BasicItemStrategyContext context, BasicItemDao basicItemDao, BiRefAttrService biRefAttrService, BasicItem basicItem) {
		
	}

	@Override
	public void logicOperate(BasicItemStrategyContext context, BasicItem basicItem, String flag, Integer cascadedict, BiRefAttr biRefAttr, AggregateAttr aggregateAttr) {
		 //当前对象的父亲
		BasicItem bItemPanrent = context.getBasicItemDao().get(BasicItem.class, basicItem.getParent());
		
		basicItem.setParent(bItemPanrent.getParent() + "_" +bItemPanrent.getCode());
		basicItem.getOneLevelItem().setTableName(bItemPanrent.getOneLevelItem().getTableName());
		basicItem.getOneLevelItem().setGroupName(bItemPanrent.getCode());
	}
	
}
