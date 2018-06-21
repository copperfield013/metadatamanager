package cn.sowell.datacenter.admin.controller.node.api;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;

import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;

/**
 * InlineResponse2002
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-06-21T09:16:15.605Z")

public class InlineResponse2002   {
  @JsonProperty("repeat")
  private List<BasicItem> repeat = null;

  public InlineResponse2002 repeat(List<BasicItem> repeat) {
    this.repeat = repeat;
    return this;
  }

  public InlineResponse2002 addRepeatItem(BasicItem repeatItem) {
    if (this.repeat == null) {
      this.repeat = new ArrayList<BasicItem>();
    }
    this.repeat.add(repeatItem);
    return this;
  }

  /**
   * Get repeat
   * @return repeat
  **/
  @ApiModelProperty(value = "")

  public List<BasicItem> getRepeat() {
    return repeat;
  }

  public void setRepeat(List<BasicItem> repeat) {
    this.repeat = repeat;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse2002 inlineResponse200 = (InlineResponse2002) o;
    return Objects.equals(this.repeat, inlineResponse200.repeat);
  }

  @Override
  public int hashCode() {
    return Objects.hash(repeat);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2002 {\n");
    
    sb.append("    repeat: ").append(toIndentedString(repeat)).append("\n");
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

