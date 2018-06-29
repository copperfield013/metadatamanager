package cn.sowell.datacenter.admin.controller.node.api;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;

/**
 * InlineResponse2005
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-06-28T08:26:17.204Z")

public class InlineResponse2005   {
  @JsonProperty("child")
  private List<BasicItem> child = null;

  public InlineResponse2005 child(List<BasicItem> child) {
    this.child = child;
    return this;
  }

  public InlineResponse2005 addChildItem(BasicItem childItem) {
    if (this.child == null) {
      this.child = new ArrayList<BasicItem>();
    }
    this.child.add(childItem);
    return this;
  }

  /**
   * Get child
   * @return child
  **/
  @ApiModelProperty(value = "")

  public List<BasicItem> getChild() {
    return child;
  }

  public void setChild(List<BasicItem> child) {
    this.child = child;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse2005 inlineResponse2005 = (InlineResponse2005) o;
    return Objects.equals(this.child, inlineResponse2005.child);
  }

  @Override
  public int hashCode() {
    return Objects.hash(child);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2005 {\n");
    
    sb.append("    child: ").append(toIndentedString(child)).append("\n");
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

