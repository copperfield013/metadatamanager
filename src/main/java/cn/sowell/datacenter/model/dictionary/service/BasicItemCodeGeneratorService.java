package cn.sowell.datacenter.model.dictionary.service;

import com.abc.util.ValueType;

public interface BasicItemCodeGeneratorService {
	
	
	/**
	 *   获取 BasicItem 模型code
	 * @param dataType    ValueType.RECORD.getIndex()
	 * @return
	 * @throws Exception
	 */
	public String getBasicItemCode(ValueType dataType, String entityCode) throws Exception;
	
	/**
	 * 	输入实体code 和 中缀，组合关系code
	 * @param entityCode
	 * @param infix
	 * @return
	 * @throws Exception 
	 */
	public String getRelaCode(String entityCode) throws Exception;
	
	/**
	 * 获取实体和属性前缀
	 * @param dataType    实体和属性本身的类型
	 * @param entityCode    实体的code , 例如：XFJDE001 ，实体进来可传  null
	 * @return
	 * @throws Exception
	 */
	public String getBasicItemFix(ValueType dataType, String entityCode) throws Exception;
	
}
