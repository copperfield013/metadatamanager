package cn.sowell.datacenter.admin.controller.node.api;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.sowell.datacenter.model.dictionary.pojo.DictionaryBasicItem;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;

/**
 * DictionaryBasicItems
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-06-12T03:30:52.056Z")

public class DictionaryBasicItems   {
  @JsonProperty("commLab")
  private List<DictionaryBasicItem> commLab = null;

  public DictionaryBasicItems commLab(List<DictionaryBasicItem> commLab) {
    this.commLab = commLab;
    return this;
  }

  public DictionaryBasicItems addCommLabItem(DictionaryBasicItem commLabItem) {
    if (this.commLab == null) {
      this.commLab = new ArrayList<DictionaryBasicItem>();
    }
    this.commLab.add(commLabItem);
    return this;
  }

  /**
   * Get commLab
   * @return commLab
  **/
  @ApiModelProperty(value = "")

  public List<DictionaryBasicItem> getCommLab() {
    return commLab;
  }

  public void setCommLab(List<DictionaryBasicItem> commLab) {
    this.commLab = commLab;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DictionaryBasicItems dictionaryBasicItems = (DictionaryBasicItems) o;
    return Objects.equals(this.commLab, dictionaryBasicItems.commLab);
  }

  @Override
  public int hashCode() {
    return Objects.hash(commLab);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DictionaryBasicItems {\n");
    
    sb.append("    commLab: ").append(toIndentedString(commLab)).append("\n");
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

