package cn.sowell.datacenter.admin.controller.node.api;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;

import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;

/**
 * InlineResponse2001
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-06-21T08:40:33.627Z")

public class InlineResponse2001   {
  @JsonProperty("repeatChild")
  private List<BasicItem> repeatChild = null;

  public InlineResponse2001 repeatChild(List<BasicItem> repeatChild) {
    this.repeatChild = repeatChild;
    return this;
  }

  public InlineResponse2001 addRepeatChildItem(BasicItem repeatChildItem) {
    if (this.repeatChild == null) {
      this.repeatChild = new ArrayList<BasicItem>();
    }
    this.repeatChild.add(repeatChildItem);
    return this;
  }

  /**
   * Get repeatChild
   * @return repeatChild
  **/
  @ApiModelProperty(value = "")

  public List<BasicItem> getRepeatChild() {
    return repeatChild;
  }

  public void setRepeatChild(List<BasicItem> repeatChild) {
    this.repeatChild = repeatChild;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse2001 inlineResponse2001 = (InlineResponse2001) o;
    return Objects.equals(this.repeatChild, inlineResponse2001.repeatChild);
  }

  @Override
  public int hashCode() {
    return Objects.hash(repeatChild);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2001 {\n");
    
    sb.append("    repeatChild: ").append(toIndentedString(repeatChild)).append("\n");
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

