package gov.nist.itl.ssd.wipp.backend.data.cvatannotation.cvatmodels;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import gov.nist.itl.ssd.wipp.backend.data.cvatannotation.cvatmodels.LocationEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Storage
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-01-22T10:53:14.701528-05:00[America/New_York]")


public class Storage   {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("location")
  private LocationEnum location = null;

  @JsonProperty("cloud_storage_id")
  private Integer cloudStorageId = null;

  public Storage id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Storage location(LocationEnum location) {
    this.location = location;
    return this;
  }

  /**
   * Get location
   * @return location
   **/
  @Schema(description = "")
    public LocationEnum getLocation() {
    return location;
  }

  public void setLocation(LocationEnum location) {
    this.location = location;
  }

  public Storage cloudStorageId(Integer cloudStorageId) {
    this.cloudStorageId = cloudStorageId;
    return this;
  }

  /**
   * Get cloudStorageId
   * @return cloudStorageId
   **/
  @Schema(description = "")
    public Integer getCloudStorageId() {
    return cloudStorageId;
  }

  public void setCloudStorageId(Integer cloudStorageId) {
    this.cloudStorageId = cloudStorageId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Storage storage = (Storage) o;
    return Objects.equals(this.id, storage.id) &&
        Objects.equals(this.location, storage.location) &&
        Objects.equals(this.cloudStorageId, storage.cloudStorageId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, location, cloudStorageId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Storage {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    cloudStorageId: ").append(toIndentedString(cloudStorageId)).append("\n");
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
