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
public class RecordDelStrategy implements BasicItemDelStrategy {
	
	@Override
	public void delete(BasicItemDao basicItemDao, BasicItem basicItem) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		
		
	}

}
