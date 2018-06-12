package cn.sowell.datacenter.admin.controller.node.api;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;

import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;

/**
 * BasicItems
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-06-11T08:41:35.885Z")


public class BasicItems   {
  @JsonProperty("entity")
  private List<BasicItem> entity = null;

  public BasicItems entity(List<BasicItem> entity) {
    this.entity = entity;
    return this;
  }

  public BasicItems addEntityItem(BasicItem entityItem) {
    if (this.entity == null) {
      this.entity = new ArrayList<BasicItem>();
    }
    this.entity.add(entityItem);
    return this;
  }

  /**
   * Get entity
   * @return entity
  **/
  @ApiModelProperty(value = "")

  public List<BasicItem> getEntity() {
    return entity;
  }

  public void setEntity(List<BasicItem> entity) {
    this.entity = entity;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BasicItems basicItems = (BasicItems) o;
    return Objects.equals(this.entity, basicItems.entity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(entity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BasicItems {\n");
    
    sb.append("    entity: ").append(toIndentedString(entity)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

