package gov.nist.itl.ssd.wipp.backend.data.cvatannotation.cvatmodels;

import java.util.Objects;
import gov.nist.itl.ssd.wipp.backend.data.cvatannotation.cvatmodels.BasicUser;

/**
 * AllOfTaskReadOwner
 */

@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-01-22T10:53:14.701528-05:00[America/New_York]")


public class AllOfTaskReadOwner extends BasicUser  {

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllOfTaskReadOwner {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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
