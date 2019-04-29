package cn.sowell.datacenter.model.dictionary.strategy;

import com.abc.model.enun.ValueType;
import com.abc.util.AttributeParter;

import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.AggregateAttr;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;
import cn.sowell.datacenter.model.dictionary.service.BiRefAttrService;

/**
 *    ValueType.BYTES	文件型 数据删除策略
 * @author so-well
 *
 */
public class BytesStrategy implements BasicItemStrategy {
	@Override
	public void delete(BasicItemStrategyContext context,BasicItemDao basicItemDao, BiRefAttrService biRefAttrService, BasicItem basicItem) {
		BasicItem btKey =basicItemDao.get(BasicItem.class, AttributeParter.getFileKeyName(basicItem.getCode()));
        BasicItem btSuffix = basicItemDao.get(BasicItem.class, AttributeParter.getFileSuffixName(basicItem.getCode()));
        BasicItem btKBSize = basicItemDao.get(BasicItem.class, AttributeParter.getFileKBSizeName(basicItem.getCode()));
        BasicItem btName = basicItemDao.get(BasicItem.class, AttributeParter.getFileNameName(basicItem.getCode()));
        if (btKey != null) {
             basicItemDao.delete(btKey);
        }
        if (btSuffix != null) {
            basicItemDao.delete(btSuffix);                      
        }
        if (btKBSize != null) {
            basicItemDao.delete(btKBSize);
        }
        if (btName != null) {
            basicItemDao.delete(btName);
        }
        
	}

	@Override
	public void logicOperate(BasicItemStrategyContext context, BasicItem basicItem, String flag, Integer cascadedict, BiRefAttr biRefAttr, AggregateAttr aggregateAttr) {
		
			//如果是文件类型， 默认生成四个半生记录
			if ("add".equals(flag)) {
				context.getBasicItemService().fileAssociatProper(basicItem);//生成文件伴生属性
			} else {//如果是编辑， 
				if (String.valueOf(ValueType.BYTES.getIndex()).equals(basicItem.getOneLevelItem().getDataType())) {//之前是文件型
					BasicItem btKey =context.getBasicItemService().getBasicItem(AttributeParter.getFileKeyName(basicItem.getCode()));
					BasicItem btSuffix = context.getBasicItemService().getBasicItem(AttributeParter.getFileSuffixName(basicItem.getCode()));
					BasicItem btKBSize = context.getBasicItemService().getBasicItem(AttributeParter.getFileKBSizeName(basicItem.getCode()));
				    BasicItem btName = context.getBasicItemService().getBasicItem(AttributeParter.getFileNameName(basicItem.getCode()));
					
					btKey.setCnName(AttributeParter.getFileKeyCNName(basicItem.getCnName()));
					btKey.getOneLevelItem().setTableName(basicItem.getOneLevelItem().getTableName());
					btKey.setEnName(basicItem.getEnName());
					btKey.setParent(basicItem.getParent());
					btKey.setUsingState(0);
					btKey.getOneLevelItem().setGroupName(basicItem.getOneLevelItem().getGroupName());
					btKey.getOneLevelItem().setDictParentId(0);
					btKey.getOneLevelItem().setNeedHistory(basicItem.getOneLevelItem().getNeedHistory());
					
					btSuffix.setCnName(AttributeParter.getFileSuffixCNName(basicItem.getCnName()));
					btSuffix.getOneLevelItem().setTableName(basicItem.getOneLevelItem().getTableName());
					btSuffix.setEnName(basicItem.getEnName());
					btSuffix.setParent(basicItem.getParent());
					btSuffix.setUsingState(0);
					btSuffix.getOneLevelItem().setGroupName(basicItem.getOneLevelItem().getGroupName());
					btSuffix.getOneLevelItem().setDictParentId(0);
					btSuffix.getOneLevelItem().setNeedHistory(basicItem.getOneLevelItem().getNeedHistory());
						
					btKBSize.setCnName(AttributeParter.getFileKBSizeCNName(basicItem.getCnName()));
					btKBSize.getOneLevelItem().setTableName(basicItem.getOneLevelItem().getTableName());
					btKBSize.setEnName(basicItem.getEnName());
					btKBSize.setParent(basicItem.getParent());
					btKBSize.setUsingState(0);
					btKBSize.getOneLevelItem().setGroupName(basicItem.getOneLevelItem().getGroupName());
					btKBSize.getOneLevelItem().setDictParentId(0);
					btKBSize.getOneLevelItem().setNeedHistory(basicItem.getOneLevelItem().getNeedHistory());
						
					btName.setCnName(AttributeParter.getFileNameCNName(basicItem.getCnName()));
					btName.getOneLevelItem().setTableName(basicItem.getOneLevelItem().getTableName());
					btName.setEnName(basicItem.getEnName());
					btName.setParent(basicItem.getParent());
					btName.setUsingState(0);
					btName.getOneLevelItem().setGroupName(basicItem.getOneLevelItem().getGroupName());
					btName.getOneLevelItem().setDictParentId(0);
					btName.getOneLevelItem().setNeedHistory(basicItem.getOneLevelItem().getNeedHistory());
						
					context.getBasicItemDao().update(btKey);
					context.getBasicItemDao().update(btSuffix);
					context.getBasicItemDao().update(btKBSize);
					context.getBasicItemDao().update(btName);
				} 
				
			}
		
	}

}
