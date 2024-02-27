package gov.nist.itl.ssd.wipp.backend.data.cvatannotation.cvatmodels;

import java.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * * `cloud_storage` - CLOUD_STORAGE * `local` - LOCAL
 */
public enum LocationEnum {
  CLOUD_STORAGE("cloud_storage"),
    LOCAL("local");

  private String value;

  LocationEnum(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static LocationEnum fromValue(String text) {
    for (LocationEnum b : LocationEnum.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
