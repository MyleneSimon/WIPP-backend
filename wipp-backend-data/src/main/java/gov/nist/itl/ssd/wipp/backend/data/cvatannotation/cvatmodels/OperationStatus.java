package gov.nist.itl.ssd.wipp.backend.data.cvatannotation.cvatmodels;

import java.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * * `new` - NEW * `in progress` - IN_PROGRESS * `completed` - COMPLETED * `rejected` - REJECTED
 */
public enum OperationStatus {
  NEW("new"),
    IN_PROGRESS("in progress"),
    COMPLETED("completed"),
    REJECTED("rejected");

  private String value;

  OperationStatus(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static OperationStatus fromValue(String text) {
    for (OperationStatus b : OperationStatus.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
