package cn.sowell.datacenter.admin.controller.node.api;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.sowell.datacenter.model.dictionary.pojo.RecordRelationType;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;

/**
 * RecordRelationTypes
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-06-12T03:30:52.056Z")

public class RecordRelationTypes   {
  @JsonProperty("labRela")
  private List<RecordRelationType> labRela = null;

  public RecordRelationTypes labRela(List<RecordRelationType> labRela) {
    this.labRela = labRela;
    return this;
  }

  public RecordRelationTypes addLabRelaItem(RecordRelationType labRelaItem) {
    if (this.labRela == null) {
      this.labRela = new ArrayList<RecordRelationType>();
    }
    this.labRela.add(labRelaItem);
    return this;
  }

  /**
   * Get labRela
   * @return labRela
  **/
  @ApiModelProperty(value = "")

  public List<RecordRelationType> getLabRela() {
    return labRela;
  }

  public void setLabRela(List<RecordRelationType> labRela) {
    this.labRela = labRela;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RecordRelationTypes recordRelationTypes = (RecordRelationTypes) o;
    return Objects.equals(this.labRela, recordRelationTypes.labRela);
  }

  @Override
  public int hashCode() {
    return Objects.hash(labRela);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecordRelationTypes {\n");
    
    sb.append("    labRela: ").append(toIndentedString(labRela)).append("\n");
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

