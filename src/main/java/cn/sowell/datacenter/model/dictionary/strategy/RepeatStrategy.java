package cn.sowell.datacenter.model.dictionary.strategy;

import com.abc.model.enun.ValueType;
import com.abc.util.AttributeParter;

import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.AggregateAttr;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;
import cn.sowell.datacenter.model.dictionary.pojo.OneLevelItem;
import cn.sowell.datacenter.model.dictionary.service.BiRefAttrService;

/**
 *    ValueType.REPEAT
 *    	重复类型删除策略
 * @author so-well
 *
 */
public class RepeatStrategy implements BasicItemStrategy {
	@Override
	public void delete(BasicItemStrategyContext context, BasicItemDao basicItemDao, BiRefAttrService biRefAttrService, BasicItem basicItem) {
		BasicItem btp = basicItemDao.get(BasicItem.class,  AttributeParter.getLeafKeyName(basicItem.getCode()));
		BasicItem btEd = basicItemDao.get(BasicItem.class,  AttributeParter.getLeafEditTimeName(basicItem.getCode()));
		
		if (btp != null) {
			basicItemDao.delete(btp);
		} 
		if (btEd != null) {
			basicItemDao.delete(btEd);
		}
		
	}

	@Override
	public void logicOperate(BasicItemStrategyContext context, BasicItem basicItem, String flag, Integer cascadedict, BiRefAttr biRefAttr, AggregateAttr aggregateAttr) {
		basicItem.getOneLevelItem().setTableName("t_" + basicItem.getParent() +"_"+ basicItem.getCode() +"_"+ basicItem.getCode());
		basicItem.getOneLevelItem().setNeedHistory(1);
		//如果是重复类型， 默认生成两个孩子， 
		if ("add".equals(flag)) {
			//生成两个伴生属性
			BasicItem itemEditTime = createAttrEditTime(basicItem, AttributeParter.getLeafEditTimeCNName(null));
			BasicItem itemOnlyCoding = createAttrOnlyCoding(basicItem, AttributeParter.getLeafKeyCNName(null));
			context.getBasicItemDao().insert(itemEditTime);
			context.getBasicItemDao().insert(itemOnlyCoding);
		}
	}
	
	//重复类型和枚举类型多选 属性编辑时间
			public BasicItem createAttrEditTime(BasicItem obj, String EditTimeCnName) {
				BasicItem basicItem = new BasicItem();//多值属性编辑时间
				OneLevelItem oneLevelItem = new OneLevelItem();
				basicItem.setOneLevelItem(oneLevelItem);
				
				basicItem.setCode(AttributeParter.getLeafEditTimeName(obj.getCode()));
				basicItem.getOneLevelItem().setCode(AttributeParter.getLeafEditTimeName(obj.getCode()));
				basicItem.setCnName(EditTimeCnName);
				oneLevelItem.setDataType(String.valueOf(ValueType.DATETIME.getIndex()));
				oneLevelItem.setDataRange(ValueType.DATETIME.getName());
				oneLevelItem.setTableName(obj.getOneLevelItem().getTableName());
				basicItem.setParent(obj.getParent()+ "_" + obj.getCode());
				basicItem.setUsingState(0);
				oneLevelItem.setDictParentId(0);
				oneLevelItem.setGroupName(obj.getCode());
				oneLevelItem.setNeedHistory(obj.getOneLevelItem().getNeedHistory());
				
				return basicItem;
			}
			
			//重复类型和枚举类型多值属性唯一编码
			public BasicItem createAttrOnlyCoding(BasicItem obj, String keyCnName) {
				BasicItem basicItem = new BasicItem();
				OneLevelItem twoItem = new OneLevelItem();
				basicItem.setOneLevelItem(twoItem);
				basicItem.setCode(AttributeParter.getLeafKeyName(obj.getCode()));
				basicItem.getOneLevelItem().setCode(AttributeParter.getLeafKeyName(obj.getCode()));
				basicItem.setCnName(keyCnName);
				twoItem.setDataType(String.valueOf(ValueType.STRING.getIndex()));
				twoItem.setDataRange("32");
				twoItem.setTableName(obj.getOneLevelItem().getTableName());
				basicItem.setParent(obj.getParent()+ "_" + obj.getCode());
				basicItem.setUsingState(0);
				twoItem.setGroupName(obj.getCode());
				twoItem.setDictParentId(0);
				twoItem.setNeedHistory(obj.getOneLevelItem().getNeedHistory());
					
				return basicItem;
			}
}
