package cn.sowell.datacenter.model.dictionary.pojo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "t_c_basic_item")
public class BasicItem {
	
	public BasicItem() {
		
	}
	
	public BasicItem(String code, String cnName, String dataType, Integer usingState, String groupName, String parent) {
		this.code = code;
		this.cnName = cnName;
		this.usingState = usingState;
		this.parent = parent;
	}
	
	 @ApiModelProperty(value="主键code", name="code")
	  @Id
	  @Column(name="c_code")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  private String code;//单独生成规则
	  
	 @ApiModelProperty(value="中文名称", name="cnName")
	  @Column(name="c_cn_name")
	  private String cnName;
	  
	 @ApiModelProperty(value="英文名称", name="enName")
	  @Column(name="c_en_name")
	  private String enName;
	 
	 
	 @ApiModelProperty(value="自关联父id", name="parent")
	  @Column(name="c_parent")
	  private String parent;//自关联code
	 
	 @ApiModelProperty(value="状态-只增不删", name="usingState")
	  @Column(name="c_using_state")
	  private Integer usingState;//状态-只增不删
	 
	 @OneToOne(cascade = {CascadeType.ALL})
	 @JoinColumn(name = "c_code")
	 protected OneLevelItem oneLevelItem = new OneLevelItem();
	 
	/* @OneToOne(cascade = {CascadeType.ALL})
	 @JoinColumn(name = "c_code")
	 private Towlevelattr towlevelattr = new Towlevelattr();*/
	 
	 @Transient  
	 List<BasicItem> childList = null;
	 
	 @Transient  
	 private Long twoLevelAttr;
	 
	public String getCode() {
		return code;
	}

	public List getChildList() {
		return childList;
	}

	/**
	 * @param childList the childList to set
	 */
	public void setChildList(List<BasicItem> childList) {
		this.childList = childList;
	}

	public String getCnName() {
		return cnName;
	}

	public String getEnName() {
		return enName;
	}


	public String getParent() {
		return parent;
	}


	public Integer getUsingState() {
		return usingState;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}


	public void setDataForm(String dataForm) {
		dataForm = dataForm;
	}


	public void setParent(String parent) {
		this.parent = parent;
	}


	public void setUsingState(Integer usingState) {
		this.usingState = usingState;
	}

	public Long getTwoLevelAttr() {
		return twoLevelAttr;
	}

	public void setTwoLevelAttr(Long twoLevelAttr) {
		this.twoLevelAttr = twoLevelAttr;
	}

	/**
	 * @return the oneLevelItem
	 */
	public OneLevelItem getOneLevelItem() {
		return oneLevelItem;
	}

	/**
	 * @param oneLevelItem the oneLevelItem to set
	 */
	public void setOneLevelItem(OneLevelItem oneLevelItem) {
		this.oneLevelItem = oneLevelItem;
	}

}