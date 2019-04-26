package cn.sowell.datacenter.admin.controller.dictionary.strategy;

import java.util.HashMap;
import java.util.Map;

import com.abc.exface.dto.AggregateExpressionDTO;
import com.abc.model.IAggregateExpressionDAO;
import com.abc.model.enun.ValueType;

import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.service.AggregateAttrService;
import cn.sowell.datacenter.model.dictionary.service.BiRefAttrService;
import cn.sowell.datacenter.model.node.service.BinFilterBodyService;
import cn.sowell.datacenter.model.stat.service.StatExpressionService;

public class BasicItemDelContext {
	private BasicItemDao basicItemDao;
	private BasicItemDelStrategy btStrategy;
	private BiRefAttrService biRefAttrService;
	
	private AggregateAttrService aggregateAttrService;
	private BinFilterBodyService binFilterBodyService;
	private StatExpressionService statExpressionService;
	
	  //创建一个关系型map，用来存储对象，value值为策略类
    private static Map<String, BasicItemDelStrategy> mapStrategy = new HashMap<String, BasicItemDelStrategy>();;
    //初始化map对象，存储各个需要使用的具体类
    static {
        mapStrategy.put(String.valueOf(ValueType.REPEAT.getIndex()), new RepeatDelStrategy());
        mapStrategy.put(String.valueOf(ValueType.ENUMTYPE_MULTI.getIndex()), new RepeatDelStrategy());
        mapStrategy.put(String.valueOf(ValueType.BYTES.getIndex()), new BytesDelStrategy());
        mapStrategy.put(String.valueOf(ValueType.RECORD.getIndex()), new RecordDelStrategy());
        mapStrategy.put(String.valueOf(ValueType.REFERENCE.getIndex()), new ReferenceDelStrategy());
        mapStrategy.put(String.valueOf(ValueType.GROUP.getIndex()), new AggregateAttrDelStrategy());
    }
	
    public BasicItemDelContext(BasicItemDao basicItemDao, 
    		BiRefAttrService biRefAttrService,
    		 AggregateAttrService aggregateAttrService,
    		 BinFilterBodyService binFilterBodyService,
    		 StatExpressionService statExpressionService) {
    	this.basicItemDao = basicItemDao;
    	this.biRefAttrService = biRefAttrService;
    	this.aggregateAttrService = aggregateAttrService;
    	this.binFilterBodyService = binFilterBodyService;
    	this.statExpressionService = statExpressionService;
    }

	   public void delBItem(BasicItem basicItem){
		   
		   BasicItem groupBasicItem = basicItemDao.get(BasicItem.class, basicItem.getOneLevelItem().getGroupName());
		   if ("aggregate".equals(groupBasicItem.getOneLevelItem().getDataRange())) {
			   BasicItemDelStrategy basicItemDelStrategy = mapStrategy.get(groupBasicItem.getOneLevelItem().getDataType());
			   basicItemDelStrategy.delete(this, basicItemDao, biRefAttrService, basicItem);
		   }
		   
		   btStrategy = mapStrategy.get(basicItem.getOneLevelItem().getDataType());
		   	//如果我是一个普通属性， 并且我的分组是聚合分组， 我要处理的事情		   
		   if (btStrategy != null) {
			   btStrategy.delete(this, basicItemDao,biRefAttrService, basicItem);
		   }
	       
	       basicItemDao.delete(basicItem);
	   }

	public BasicItemDao getBasicItemDao() {
		return basicItemDao;
	}

	public void setBasicItemDao(BasicItemDao basicItemDao) {
		this.basicItemDao = basicItemDao;
	}

	public BasicItemDelStrategy getBtStrategy() {
		return btStrategy;
	}

	public void setBtStrategy(BasicItemDelStrategy btStrategy) {
		this.btStrategy = btStrategy;
	}

	public BiRefAttrService getBiRefAttrService() {
		return biRefAttrService;
	}

	public void setBiRefAttrService(BiRefAttrService biRefAttrService) {
		this.biRefAttrService = biRefAttrService;
	}

	public AggregateAttrService getAggregateAttrService() {
		return aggregateAttrService;
	}

	public void setAggregateAttrService(AggregateAttrService aggregateAttrService) {
		this.aggregateAttrService = aggregateAttrService;
	}

	public BinFilterBodyService getBinFilterBodyService() {
		return binFilterBodyService;
	}

	public void setBinFilterBodyService(BinFilterBodyService binFilterBodyService) {
		this.binFilterBodyService = binFilterBodyService;
	}

	public StatExpressionService getStatExpressionService() {
		return statExpressionService;
	}

	public void setStatExpressionService(StatExpressionService statExpressionService) {
		this.statExpressionService = statExpressionService;
	}

	public static Map<String, BasicItemDelStrategy> getMapStrategy() {
		return mapStrategy;
	}

	public static void setMapStrategy(Map<String, BasicItemDelStrategy> mapStrategy) {
		BasicItemDelContext.mapStrategy = mapStrategy;
	}
	   
}
