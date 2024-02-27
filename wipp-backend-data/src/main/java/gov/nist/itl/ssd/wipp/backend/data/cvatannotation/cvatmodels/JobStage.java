package gov.nist.itl.ssd.wipp.backend.data.cvatannotation.cvatmodels;

import java.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * * `annotation` - ANNOTATION * `validation` - VALIDATION * `acceptance` - ACCEPTANCE
 */
public enum JobStage {
  ANNOTATION("annotation"),
    VALIDATION("validation"),
    ACCEPTANCE("acceptance");

  private String value;

  JobStage(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static JobStage fromValue(String text) {
    for (JobStage b : JobStage.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
