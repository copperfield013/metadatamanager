package cn.sowell.datacenter.admin.controller.dictionary.strategy;

import javax.annotation.Resource;

import com.abc.util.AttributeParter;

import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;

/**
 * 重复类型删除策略和枚举类型多选
 * @author so-well
 *
 */
public class BytesDelStrategy implements BasicItemDelStrategy {
	@Override
	public void delete(BasicItemDao basicItemDao, BasicItem basicItem) {
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
