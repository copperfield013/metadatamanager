package cn.sowell.datacenter.model.dictionary.strategy;

import com.abc.model.enun.ValueType;

/**
 * BasicItemStrategy 策略工厂
 * @author so-well
 *
 */
public class BasicItemStrategyFactory {
	public static final String AGGREGATEGROUPCHILD= "aggregateGroupChildStrategy";
	public static final String REPEATGROUPCHILD= "repeatGroupChildStrategy";
	public static final String COMMGROUPCHILDSTRATEGY= "commGroupChildStrategy";
	
	
	 public static BasicItemStrategy creator(ValueType valueType, String strategy){
		 if (AGGREGATEGROUPCHILD.equals(strategy)) {
			 return new AggregateGroupChildStrategy();
		 } else if (REPEATGROUPCHILD.equals(strategy)){
			 return new RepeatGroupChildStrategy();
		 } else if (COMMGROUPCHILDSTRATEGY.equals(strategy)) {
			 return new CommGroupChildStrategy();
		 }
		 
		 	switch (valueType) {
			case REPEAT:
				return new RepeatStrategy();
			case ENUMTYPE_MULTI:
				return new EnumtypeMultiStrategy();
			case BYTES:
				return new BytesStrategy();
			case RECORD:
				return new RecordStrategy();
			case REFERENCE:
				return new ReferenceStrategy();
			case GROUP:
				return new GroupStrategy();
			}
		 	return null;
	    }
}
