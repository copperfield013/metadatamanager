package cn.sowell.datacenter.model.dictionary.service;


public interface BasicItemCodeGeneratorService {

	/**
	 * 	获取实体Code
	 * @return
	 * @throws Exception
	 */
	public String getEntityCode() throws Exception;
	
	/**
	 *  	输入前缀，组合分组属性，多值，普通属性的code
	 * @return
	 * @throws Exception
	 */
	public String getAttrCode() throws Exception;
	
	
	/**
	 * 输入实体code 和 中缀，组合关系code
	 * @param entityCode
	 * @param infix
	 * @return
	 * @throws Exception 
	 */
	public String getRelaCode(String entityCode) throws Exception;
	
	/**
	 * 获取实体和属性前缀
	 * @return
	 * @throws Exception
	 */
	public String getBasicItemFix() throws Exception;
	
}
