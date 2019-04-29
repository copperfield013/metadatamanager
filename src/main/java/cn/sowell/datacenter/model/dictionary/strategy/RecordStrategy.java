package cn.sowell.datacenter.model.dictionary.strategy;

import com.abc.model.enun.ValueType;
import com.abc.util.AttributeParter;

import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.AggregateAttr;
import cn.sowell.datacenter.model.dictionary.pojo.BasicChange;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;
import cn.sowell.datacenter.model.dictionary.pojo.OneLevelItem;
import cn.sowell.datacenter.model.dictionary.service.BiRefAttrService;

/**
 * 	ValueType.RECORD  记录类型删除策略
 * @author so-well
 *
 */
public class RecordStrategy implements BasicItemStrategy {
	
	@Override
	public void delete(BasicItemStrategyContext context,BasicItemDao basicItemDao, BiRefAttrService biRefAttrService, BasicItem basicItem) {
		basicItem.getOneLevelItem().setDictParentId(0);
		basicItem.setParent("");
		BasicItem lableObj;
		try {
			lableObj = basicItemDao.getLableObj(basicItem.getCode());
			if (lableObj != null) {
	    		basicItemDao.delete(lableObj);
	    	}
	    	//删除实体编辑时间属性
	    	BasicItem bt = basicItemDao.get(BasicItem.class, AttributeParter.getLeafEditTimeName(basicItem.getCode()));
	    	if (bt != null) {
	    		basicItemDao.delete(bt);
	    	}
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void logicOperate(BasicItemStrategyContext context, BasicItem basicItem, String flag, Integer cascadedict, BiRefAttr biRefAttr, AggregateAttr aggregateAttr) throws Exception {
		basicItem.getOneLevelItem().setNeedHistory(1);
		//如果是记录类型， 选择一个标签字典， 生成一条标签字典类
		if ("add".equals(flag)) {
			//这里生成abcde010_ED这个属性， 
			BasicItem btItem = context.getBasicItemService().createRecordEditeTime(basicItem);
			context.getBasicItemDao().insert(btItem);
			//生成标签
			BasicItem bt = context.getBasicItemService().createLable(basicItem, cascadedict);
			context.getBasicItemDao().insert(bt);
		} else {
			BasicItem edidTimeItem = context.getBasicItemDao().get(BasicItem.class, AttributeParter.getLeafEditTimeName(basicItem.getCode()));
			
			if (edidTimeItem == null) {
				BasicItem btItem =  context.getBasicItemService().createRecordEditeTime(basicItem);
				context.getBasicItemDao().insert(btItem);
			}
			
			//编辑记录类型， 确认字典是否编辑了， 如果编辑了则修改
			BasicItem one = context.getBasicItemDao().getLableObj(basicItem.getCode());
			if (one == null) {
				BasicItem bt =  context.getBasicItemService().createLable(basicItem, cascadedict);
				context.getBasicItemDao().insert(bt);
			} else {
				if (!one.getOneLevelItem().getDictParentId().equals(cascadedict)) {
					one.getOneLevelItem().setDictParentId(cascadedict);
					context.getBasicItemDao().update(one);
				}
			}
		}
		
	}
	
}
