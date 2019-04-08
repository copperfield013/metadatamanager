package cn.sowell.datacenter.admin.controller.dictionary.strategy;

import com.abc.util.AttributeParter;

import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.service.BiRefAttrService;

/**
 *    ValueType.BYTES	文件型 数据删除策略
 * @author so-well
 *
 */
public class BytesDelStrategy implements BasicItemDelStrategy {
	@Override
	public void delete(BasicItemDao basicItemDao, BiRefAttrService biRefAttrService, BasicItem basicItem) {
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

}
