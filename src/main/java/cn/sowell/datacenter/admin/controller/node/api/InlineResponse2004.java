package cn.sowell.datacenter.admin.controller.node.api;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.RecordRelationType;

import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;

/**
 * InlineResponse2004
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-06-28T06:35:07.062Z")

public class InlineResponse2004   {
  @JsonProperty("commonProper")
  private List<BasicItem> commonProper = null;

  @JsonProperty("moreProper")
  private List<BasicItem> moreProper = null;

  @JsonProperty("entityRela")
  private List<RecordRelationType> entityRela = null;

  public InlineResponse2004 commonProper(List<BasicItem> commonProper) {
    this.commonProper = commonProper;
    return this;
  }

  public InlineResponse2004 addCommonProperItem(BasicItem commonProperItem) {
    if (this.commonProper == null) {
      this.commonProper = new ArrayList<BasicItem>();
    }
    this.commonProper.add(commonProperItem);
    return this;
  }

  /**
   * Get commonProper
   * @return commonProper
  **/
  @ApiModelProperty(value = "")


  public List<BasicItem> getCommonProper() {
    return commonProper;
  }

  public void setCommonProper(List<BasicItem> commonProper) {
    this.commonProper = commonProper;
  }

  public InlineResponse2004 moreProper(List<BasicItem> moreProper) {
    this.moreProper = moreProper;
    return this;
  }

  public InlineResponse2004 addMoreProperItem(BasicItem moreProperItem) {
    if (this.moreProper == null) {
      this.moreProper = new ArrayList<BasicItem>();
    }
    this.moreProper.add(moreProperItem);
    return this;
  }

  /**
   * Get moreProper
   * @return moreProper
  **/
  @ApiModelProperty(value = "")


  public List<BasicItem> getMoreProper() {
    return moreProper;
  }

  public void setMoreProper(List<BasicItem> moreProper) {
    this.moreProper = moreProper;
  }

  public InlineResponse2004 entityRela(List<RecordRelationType> entityRela) {
    this.entityRela = entityRela;
    return this;
  }

  public InlineResponse2004 addEntityRelaItem(RecordRelationType entityRelaItem) {
    if (this.entityRela == null) {
      this.entityRela = new ArrayList<RecordRelationType>();
    }
    this.entityRela.add(entityRelaItem);
    return this;
  }

  /**
   * Get entityRela
   * @return entityRela
  **/
  @ApiModelProperty(value = "")

  public List<RecordRelationType> getEntityRela() {
    return entityRela;
  }

  public void setEntityRela(List<RecordRelationType> entityRela) {
    this.entityRela = entityRela;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse2004 inlineResponse2004 = (InlineResponse2004) o;
    return Objects.equals(this.commonProper, inlineResponse2004.commonProper) &&
        Objects.equals(this.moreProper, inlineResponse2004.moreProper) &&
        Objects.equals(this.entityRela, inlineResponse2004.entityRela);
  }

  @Override
  public int hashCode() {
    return Objects.hash(commonProper, moreProper, entityRela);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2004 {\n");
    
    sb.append("    commonProper: ").append(toIndentedString(commonProper)).append("\n");
    sb.append("    moreProper: ").append(toIndentedString(moreProper)).append("\n");
    sb.append("    entityRela: ").append(toIndentedString(entityRela)).append("\n");
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


