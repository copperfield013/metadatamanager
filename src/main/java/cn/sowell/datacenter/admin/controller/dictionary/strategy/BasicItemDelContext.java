package cn.sowell.datacenter.admin.controller.dictionary.strategy;

import java.util.HashMap;
import java.util.Map;

import com.abc.util.ValueType;

import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;

public class BasicItemDelContext {
	private BasicItemDao basicItemDao;
	private BasicItemDelStrategy btStrategy;
	
	  //创建一个关系型map，用来存储对象，value值为策略类
    private static Map<String, BasicItemDelStrategy> mapStrategy = new HashMap<String, BasicItemDelStrategy>();;
    //初始化map对象，存储各个需要使用的具体类
    static {
        mapStrategy.put(String.valueOf(ValueType.REPEAT.getIndex()), new RepeatDelStrategy());
        mapStrategy.put(String.valueOf(ValueType.ENUMTYPE_MULTI.getIndex()), new RepeatDelStrategy());
        mapStrategy.put(String.valueOf(ValueType.BYTES.getIndex()), new BytesDelStrategy());
        mapStrategy.put(String.valueOf(ValueType.RECORD.getIndex()), new RecordDelStrategy());
    }
	
    public BasicItemDelContext(BasicItemDao basicItemDao) {
    	this.basicItemDao = basicItemDao;
    }

	   public void delBItem(BasicItem basicItem){
		   btStrategy = mapStrategy.get(basicItem.getOneLevelItem().getDataType());
		   if (btStrategy != null) {
			   btStrategy.delete(basicItemDao, basicItem);
		   }
	       
	       basicItemDao.delete(basicItem);
	   }
}
