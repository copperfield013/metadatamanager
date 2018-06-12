package cn.sowell.datacenter.admin.controller.node.api;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.sowell.datacenter.model.node.pojo.BasicItemNode;

import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;

/**
 * BasicItemNodes
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-06-12T02:53:21.377Z")

public class BasicItemNodes   {
  @JsonProperty("childNode")
  private List<BasicItemNode> childNode = null;

  public BasicItemNodes childNode(List<BasicItemNode> childNode) {
    this.childNode = childNode;
    return this;
  }

  public BasicItemNodes addChildNodeItem(BasicItemNode childNodeItem) {
    if (this.childNode == null) {
      this.childNode = new ArrayList<BasicItemNode>();
    }
    this.childNode.add(childNodeItem);
    return this;
  }

  /**
   * Get childNode
   * @return childNode
  **/
  @ApiModelProperty(value = "")

  public List<BasicItemNode> getChildNode() {
    return childNode;
  }

  public void setChildNode(List<BasicItemNode> childNode) {
    this.childNode = childNode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BasicItemNodes basicItemNodes = (BasicItemNodes) o;
    return Objects.equals(this.childNode, basicItemNodes.childNode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(childNode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BasicItemNodes {\n");
    
    sb.append("    childNode: ").append(toIndentedString(childNode)).append("\n");
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

