package cn.sowell.datacenter.model.cascadedict.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "t_sc_cascadedict_basic_item")
public class CascadedictBasicItem {

	@ApiModelProperty(value="主键id")
	 @Id
	  @Column(name="id")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  private Integer id;
	
	@Column(name = "c_cas_pid")
	private String casPid;
	
	@ApiModelProperty(value="父id")
	@Column(name = "parent_id")
	private Integer parentId;
	
	@ApiModelProperty(value="父亲下的孩子的排序order")
	@Column(name = "c_order")
	private Integer order;
	
	@ApiModelProperty(value="名称")
	@Column(name = "c_name")
	private String name;
	
	@ApiModelProperty(value="英文名称")
	@Column(name = "c_en_name")
	private String enName;
	
	@ApiModelProperty(value="状态")
	@Column(name = "c_status")
	private String status;
	
	@Column(name="c_update_time")
	private String updateTime;

	public Integer getId() {
		return id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public String getName() {
		return name;
	}

	public String getEnName() {
		return enName;
	}

	public String getStatus() {
		return status;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the casPid
	 */
	public String getCasPid() {
		return casPid;
	}

	/**
	 * @param casPid the casPid to set
	 */
	public void setCasPid(String casPid) {
		this.casPid = casPid;
	}
	
}