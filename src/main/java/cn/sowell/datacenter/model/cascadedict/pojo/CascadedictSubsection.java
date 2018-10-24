package cn.sowell.datacenter.model.cascadedict.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 字典再分
 * @author so-well
 *
 */
@Entity
@Table(name = "t_sc_cascadedict_subsection")
public class CascadedictSubsection {
	  @Id
	  @Column(name="id")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  private String id;//单独生成规则
	  
	  @Column(name="c_name")
	  private String name;
	 
	  @Column(name="parent_id")
	  private String parentId;
	  
	  @Column(name="c_status")
	  private String status;
	 
	  @Column(name="c_order")
	  private Integer order;
	  
	  @Column(name=" c_update_time")
	  private String  updateTime;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}