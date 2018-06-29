package cn.sowell.datacenter.admin.controller.node.api;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

/**
 * InlineResponse2003
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-06-27T10:07:50.309Z")

public class InlineResponse2003   {
  @JsonProperty("digital")
  private String digital = null;

  @JsonProperty("date")
  private String date = null;

  @JsonProperty("dateTime")
  private String dateTime = null;

  @JsonProperty("char")
  private String _char = null;

  @JsonProperty("digitalDecimal")
  private String digitalDecimal = null;

  @JsonProperty("枚举")
  private String  枚举 = null;

  @JsonProperty("文件型")
  private String  文件型 = null;

  public InlineResponse2003 digital(String digital) {
    this.digital = digital;
    return this;
  }

  /**
   * Get digital
   * @return digital
  **/
  @ApiModelProperty(example = "数字型", value = "")


  public String getDigital() {
    return digital;
  }

  public void setDigital(String digital) {
    this.digital = digital;
  }

  public InlineResponse2003 date(String date) {
    this.date = date;
    return this;
  }

  /**
   * Get date
   * @return date
  **/
  @ApiModelProperty(example = "日期型", value = "")


  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public InlineResponse2003 dateTime(String dateTime) {
    this.dateTime = dateTime;
    return this;
  }

  /**
   * Get dateTime
   * @return dateTime
  **/
  @ApiModelProperty(example = "时间型", value = "")


  public String getDateTime() {
    return dateTime;
  }

  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }

  public InlineResponse2003 _char(String _char) {
    this._char = _char;
    return this;
  }

  /**
   * Get _char
   * @return _char
  **/
  @ApiModelProperty(example = "字符型", value = "")


  public String getChar() {
    return _char;
  }

  public void setChar(String _char) {
    this._char = _char;
  }

  public InlineResponse2003 digitalDecimal(String digitalDecimal) {
    this.digitalDecimal = digitalDecimal;
    return this;
  }

  /**
   * Get digitalDecimal
   * @return digitalDecimal
  **/
  @ApiModelProperty(example = "数字型小数", value = "")


  public String getDigitalDecimal() {
    return digitalDecimal;
  }

  public void setDigitalDecimal(String digitalDecimal) {
    this.digitalDecimal = digitalDecimal;
  }

  public InlineResponse2003 枚举(String 枚举) {
    this.枚举= 枚举;
    return this;
  }

  /**
   * Get 
   * @return 
  **/
  @ApiModelProperty(example = "枚举", value = "")


  public String get枚举() {
    return this.枚举;
  }

  public void set枚举(String 枚举) {
    this.枚举=枚举;
  }

  public InlineResponse2003 文件型(String 文件型) {
    this.文件型= 文件型;
    return this;
  }

  /**
   * Get 
   * @return 
  **/
  @ApiModelProperty(example = "文件型", value = "")


  public String get文件型() {
    return this.文件型;
  }

  public void set文件型(String 文件型) {
    this.文件型 = 文件型;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse2003 inlineResponse2003 = (InlineResponse2003) o;
    return Objects.equals(this.digital, inlineResponse2003.digital) &&
        Objects.equals(this.date, inlineResponse2003.date) &&
        Objects.equals(this.dateTime, inlineResponse2003.dateTime) &&
        Objects.equals(this._char, inlineResponse2003._char) &&
        Objects.equals(this.digitalDecimal, inlineResponse2003.digitalDecimal) &&
        Objects.equals(this.枚举, inlineResponse2003.枚举) &&
        Objects.equals(this.文件型, inlineResponse2003.文件型);
  }

  @Override
  public int hashCode() {
    return Objects.hash(digital, date, dateTime, _char, digitalDecimal, 枚举, 文件型);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2003 {\n");
    
    sb.append("    digital: ").append(toIndentedString(digital)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    dateTime: ").append(toIndentedString(dateTime)).append("\n");
    sb.append("    _char: ").append(toIndentedString(_char)).append("\n");
    sb.append("    digitalDecimal: ").append(toIndentedString(digitalDecimal)).append("\n");
    sb.append("    : 枚举").append(toIndentedString(枚举)).append("\n");
    sb.append("    : 文件型").append(toIndentedString(文件型)).append("\n");
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
