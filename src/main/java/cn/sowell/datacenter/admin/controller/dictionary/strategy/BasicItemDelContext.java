package cn.sowell.datacenter.admin.controller.dictionary.strategy;

import java.util.HashMap;
import java.util.Map;

import com.abc.model.enun.ValueType;

import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.service.BiRefAttrService;

public class BasicItemDelContext {
	private BasicItemDao basicItemDao;
	private BasicItemDelStrategy btStrategy;
	private BiRefAttrService biRefAttrService;
	
	  //创建一个关系型map，用来存储对象，value值为策略类
    private static Map<String, BasicItemDelStrategy> mapStrategy = new HashMap<String, BasicItemDelStrategy>();;
    //初始化map对象，存储各个需要使用的具体类
    static {
        mapStrategy.put(String.valueOf(ValueType.REPEAT.getIndex()), new RepeatDelStrategy());
        mapStrategy.put(String.valueOf(ValueType.ENUMTYPE_MULTI.getIndex()), new RepeatDelStrategy());
        mapStrategy.put(String.valueOf(ValueType.BYTES.getIndex()), new BytesDelStrategy());
        mapStrategy.put(String.valueOf(ValueType.RECORD.getIndex()), new RecordDelStrategy());
        mapStrategy.put(String.valueOf(ValueType.REFERENCE.getIndex()), new ReferenceDelStrategy());
    }
	
    public BasicItemDelContext(BasicItemDao basicItemDao, BiRefAttrService biRefAttrService) {
    	this.basicItemDao = basicItemDao;
    	this.biRefAttrService = biRefAttrService;
    }

	   public void delBItem(BasicItem basicItem){
		   btStrategy = mapStrategy.get(basicItem.getOneLevelItem().getDataType());
		   if (btStrategy != null) {
			   btStrategy.delete(basicItemDao,biRefAttrService, basicItem);
		   }
	       
	       basicItemDao.delete(basicItem);
	   }
}
