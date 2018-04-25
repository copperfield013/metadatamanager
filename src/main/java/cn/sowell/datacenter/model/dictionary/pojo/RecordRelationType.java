package cn.sowell.datacenter.model.dictionary.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_c_record_relation_type")
public class RecordRelationType {

	@Id
	@Column(name = "type_code")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String typeCode;// 主键规则待定

	@Column(name = "name")
	private String name;

	@Column(name = "left_record_type")
	private String leftRecordType;

	@Column(name = "right_record_type")
	private String rightRecordType;

	@Column(name = "reverse_code")
	private String reverseCode;

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getName() {
		return name;
	}

	public String getLeftRecordType() {
		return leftRecordType;
	}

	public String getRightRecordType() {
		return rightRecordType;
	}

	public String getReverseCode() {
		return reverseCode;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLeftRecordType(String leftRecordType) {
		this.leftRecordType = leftRecordType;
	}

	public void setRightRecordType(String rightRecordType) {
		this.rightRecordType = rightRecordType;
	}

	public void setReverseCode(String reverseCode) {
		this.reverseCode = reverseCode;
	}
}