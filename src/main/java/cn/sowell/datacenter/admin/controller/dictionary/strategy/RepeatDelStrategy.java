package cn.sowell.datacenter.admin.controller.dictionary.strategy;

import com.abc.util.AttributeParter;
import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;

/**
 * 重复类型删除策略和枚举类型多选
 * @author so-well
 *
 */
public class RepeatDelStrategy implements BasicItemDelStrategy {
	@Override
	public void delete(BasicItemDao basicItemDao, BasicItem basicItem) {
		BasicItem btp = basicItemDao.get(BasicItem.class,  AttributeParter.getLeafKeyName(basicItem.getCode()));
		BasicItem btEd = basicItemDao.get(BasicItem.class,  AttributeParter.getLeafEditTimeName(basicItem.getCode()));
		
		if (btp != null) {
			basicItemDao.delete(btp);
		} 
		if (btEd != null) {
			basicItemDao.delete(btEd);
		}
		
	}

}
