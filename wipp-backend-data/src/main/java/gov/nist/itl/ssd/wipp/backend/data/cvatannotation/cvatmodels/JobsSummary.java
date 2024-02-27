package gov.nist.itl.ssd.wipp.backend.data.cvatannotation.cvatmodels;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * JobsSummary
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-01-22T10:53:14.701528-05:00[America/New_York]")


public class JobsSummary   {
  @JsonProperty("count")
  private Integer count = 0;

  @JsonProperty("completed")
  private Integer completed = null;

  @JsonProperty("validation")
  private Integer validation = null;

  @JsonProperty("url")
  private String url = null;

  public JobsSummary count(Integer count) {
    this.count = count;
    return this;
  }

  /**
   * Get count
   * @return count
   **/
  @Schema(description = "")
    public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public JobsSummary completed(Integer completed) {
    this.completed = completed;
    return this;
  }

  /**
   * Get completed
   * @return completed
   **/
  @Schema(required = true, description = "")
    public Integer getCompleted() {
    return completed;
  }

  public void setCompleted(Integer completed) {
    this.completed = completed;
  }

  public JobsSummary validation(Integer validation) {
    this.validation = validation;
    return this;
  }

  /**
   * Get validation
   * @return validation
   **/
  @Schema(required = true, description = "")
    public Integer getValidation() {
    return validation;
  }

  public void setValidation(Integer validation) {
    this.validation = validation;
  }

  public JobsSummary url(String url) {
    this.url = url;
    return this;
  }

  /**
   * Get url
   * @return url
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JobsSummary jobsSummary = (JobsSummary) o;
    return Objects.equals(this.count, jobsSummary.count) &&
        Objects.equals(this.completed, jobsSummary.completed) &&
        Objects.equals(this.validation, jobsSummary.validation) &&
        Objects.equals(this.url, jobsSummary.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(count, completed, validation, url);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JobsSummary {\n");
    
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("    completed: ").append(toIndentedString(completed)).append("\n");
    sb.append("    validation: ").append(toIndentedString(validation)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
