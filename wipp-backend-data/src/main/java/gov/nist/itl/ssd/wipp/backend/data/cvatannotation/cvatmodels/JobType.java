package gov.nist.itl.ssd.wipp.backend.data.cvatannotation.cvatmodels;

import java.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * * `annotation` - ANNOTATION * `ground_truth` - GROUND_TRUTH
 */
public enum JobType {
  ANNOTATION("annotation"),
    GROUND_TRUTH("ground_truth");

  private String value;

  JobType(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static JobType fromValue(String text) {
    for (JobType b : JobType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
