package cn.sowell.datacenter.model.demo.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class User {
	@ApiModelProperty(value = "用户id", required = true) 
	private String id;
	@ApiModelProperty(value = "昵称", required = true)
	private String name;
	@ApiModelProperty(value = "年龄", required = true)
	private Integer age;
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Integer getAge() {
		return age;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
}
