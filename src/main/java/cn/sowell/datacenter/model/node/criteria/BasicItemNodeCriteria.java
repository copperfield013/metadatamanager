package cn.sowell.datacenter.model.node.criteria;

import cn.sowell.datacenter.model.node.pojo.BasicItemNode;

public class BasicItemNodeCriteria extends BasicItemNode {
	
	private String abcattrCode;
	private String basicItemCode;
	private String basicItemCnName;
	/**
	 * @return the basicItemCode
	 */
	public String getBasicItemCode() {
		return basicItemCode;
	}
	/**
	 * @return the basicItemCnName
	 */
	public String getBasicItemCnName() {
		return basicItemCnName;
	}
	/**
	 * @param basicItemCode the basicItemCode to set
	 */
	public void setBasicItemCode(String basicItemCode) {
		this.basicItemCode = basicItemCode;
	}
	/**
	 * @param basicItemCnName the basicItemCnName to set
	 */
	public void setBasicItemCnName(String basicItemCnName) {
		this.basicItemCnName = basicItemCnName;
	}
	/**
	 * @return the abcattrCode
	 */
	public String getAbcattrCode() {
		return abcattrCode;
	}
	/**
	 * @param abcattrCode the abcattrCode to set
	 */
	public void setAbcattrCode(String abcattrCode) {
		this.abcattrCode = abcattrCode;
	}
	
}
