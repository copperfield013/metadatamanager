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
@Table(name = "t_sc_cascadedict_subsection_child")
public class CascadedictSubsectionChild {
	  @Id
	  @Column(name="id")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  private String id;//单独生成规则
	  
	  @Column(name="subsection_id")
	  private String subsectionId;
	  
	  @Column(name="child_id")
	  private String childId;
	  
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
	 * @return the subsectionId
	 */
	public String getSubsectionId() {
		return subsectionId;
	}

	/**
	 * @return the childId
	 */
	public String getChildId() {
		return childId;
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
	 * @param subsectionId the subsectionId to set
	 */
	public void setSubsectionId(String subsectionId) {
		this.subsectionId = subsectionId;
	}

	/**
	 * @param childId the childId to set
	 */
	public void setChildId(String childId) {
		this.childId = childId;
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