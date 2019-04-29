package cn.sowell.datacenter.model.dictionary.strategy;

import com.abc.model.enun.ValueType;
import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.AggregateAttr;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;
import cn.sowell.datacenter.model.dictionary.service.AggregateAttrService;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
import cn.sowell.datacenter.model.dictionary.service.BiRefAttrService;
import cn.sowell.datacenter.model.node.service.BinFilterBodyService;
import cn.sowell.datacenter.model.stat.service.StatExpressionService;

public class BasicItemStrategyContext {
	private BasicItemService basicItemService;
	private BasicItemDao basicItemDao;
	private BasicItemStrategy btStrategy;
	private BiRefAttrService biRefAttrService;
	
	private AggregateAttrService aggregateAttrService;
	private BinFilterBodyService binFilterBodyService;
	private StatExpressionService statExpressionService;
	
    public BasicItemStrategyContext(BasicItemDao basicItemDao, 
    		BiRefAttrService biRefAttrService,
    		 AggregateAttrService aggregateAttrService,
    		 BinFilterBodyService binFilterBodyService,
    		 StatExpressionService statExpressionService,
    		 BasicItemService basicItemService) {
    	this.basicItemDao = basicItemDao;
    	this.biRefAttrService = biRefAttrService;
    	this.aggregateAttrService = aggregateAttrService;
    	this.binFilterBodyService = binFilterBodyService;
    	this.statExpressionService = statExpressionService;
    	this.basicItemService = basicItemService;
    }

	   public void delBItem(BasicItem basicItem){
		   Integer dataType = Integer.parseInt(basicItem.getOneLevelItem().getDataType());

		   //获取父亲策略， 孩子应该执行的代码
		   BasicItem groupBasicItem = null; 
		  
		   if (ValueType.RECORD.getIndex() != dataType &&
				   ValueType.REPEAT.getIndex() != dataType &&
				   ValueType.GROUP.getIndex() != dataType) {
			   //当前对象的父亲
			   BasicItem bItemPanrent = basicItemDao.get(BasicItem.class, basicItem.getParent());
			   //父亲的类型
			   Integer parentDataType = Integer.parseInt(bItemPanrent.getOneLevelItem().getDataType());
			   
			   BasicItemStrategy basicItemStrategy = null;
			   if(ValueType.REPEAT.getIndex() == parentDataType) {
				   basicItemStrategy = BasicItemStrategyFactory
						   .creator(null, BasicItemStrategyFactory.REPEATGROUPCHILD);
			   } else {
				   groupBasicItem = basicItemDao.get(BasicItem.class, basicItem.getOneLevelItem().getGroupName());
				   if (groupBasicItem !=null && "aggregate".equals(groupBasicItem.getOneLevelItem().getDataRange())) {
					   basicItemStrategy = BasicItemStrategyFactory.creator(null, BasicItemStrategyFactory.AGGREGATEGROUPCHILD);
					} else {
					   basicItemStrategy = BasicItemStrategyFactory.creator(null, BasicItemStrategyFactory.COMMGROUPCHILDSTRATEGY);
					}
			   }
			   
			   if (basicItemStrategy != null) {
				   basicItemStrategy.delete(this, basicItemDao, biRefAttrService, basicItem);
			   
			   }
			   
		   }
		   
		   // 执行本身的类型
		   btStrategy = BasicItemStrategyFactory
				   .creator(ValueType.getValueType(dataType), null);
		   if (btStrategy != null) {
			   btStrategy.delete(this, basicItemDao,biRefAttrService, basicItem);
		   }
	       
	       basicItemDao.delete(basicItem);
	   }
	   
	   /**
	    *  添加修改处理的事情
	    * @param basicItem
	 * @throws Exception 
	    */
	   public void saveOrUpdateBItem(BasicItem basicItem, String flag, Integer cascadedict, BiRefAttr biRefAttr, AggregateAttr aggregateAttr) throws Exception{ 
		   Integer dataType = Integer.parseInt(basicItem.getOneLevelItem().getDataType());
		   
		   // 添加修改后， 状态改为新增
		   basicItem.setUsingState(0);
		   if (basicItem.getOneLevelItem().getDictParentId() == null) {
			   basicItem.getOneLevelItem().setDictParentId(0);
			}
		   
		   //获取父亲策略， 孩子应该执行的代码
		   BasicItem groupBasicItem = null; 
		  
		   if (ValueType.RECORD.getIndex() != dataType &&
				   ValueType.REPEAT.getIndex() != dataType &&
				   ValueType.GROUP.getIndex() != dataType) {
			   //当前对象的父亲
			   BasicItem bItemPanrent = basicItemDao.get(BasicItem.class, basicItem.getParent());
			   //父亲的类型
			   Integer parentDataType = Integer.parseInt(bItemPanrent.getOneLevelItem().getDataType());
			   
			   BasicItemStrategy basicItemStrategy = null;
			   if(ValueType.REPEAT.getIndex() == parentDataType) {
				   basicItemStrategy = BasicItemStrategyFactory
						   .creator(null, BasicItemStrategyFactory.REPEATGROUPCHILD);
			   } else {
				   groupBasicItem = basicItemDao.get(BasicItem.class, basicItem.getOneLevelItem().getGroupName());
				   if (groupBasicItem !=null && "aggregate".equals(groupBasicItem.getOneLevelItem().getDataRange())) {
					   basicItemStrategy = BasicItemStrategyFactory.creator(null, BasicItemStrategyFactory.AGGREGATEGROUPCHILD);
					} else {
					   basicItemStrategy = BasicItemStrategyFactory.creator(null, BasicItemStrategyFactory.COMMGROUPCHILDSTRATEGY);
					}
			   }
			   
			   if (basicItemStrategy != null) {
				   basicItemStrategy.logicOperate(this, basicItem, flag, cascadedict, biRefAttr, aggregateAttr);
			   
			   }
			   
		   }
		   
		   //获取本身策略
		   btStrategy = BasicItemStrategyFactory.creator(ValueType.getValueType(dataType), null);
		   
		   if (btStrategy != null) {
			   btStrategy.logicOperate(this, basicItem, flag, cascadedict, biRefAttr, aggregateAttr);
		   }
		   basicItemDao.saveOrUpdate(basicItem, flag);
	   }
	   

	public BasicItemDao getBasicItemDao() {
		return basicItemDao;
	}

	public void setBasicItemDao(BasicItemDao basicItemDao) {
		this.basicItemDao = basicItemDao;
	}

	public BasicItemStrategy getBtStrategy() {
		return btStrategy;
	}

	public void setBtStrategy(BasicItemStrategy btStrategy) {
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

	public BasicItemService getBasicItemService() {
		return basicItemService;
	}

	public void setBasicItemService(BasicItemService basicItemService) {
		this.basicItemService = basicItemService;
	}
	
}
