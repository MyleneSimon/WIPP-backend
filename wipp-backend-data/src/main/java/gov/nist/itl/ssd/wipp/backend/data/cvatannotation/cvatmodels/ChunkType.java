package gov.nist.itl.ssd.wipp.backend.data.cvatannotation.cvatmodels;

import java.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * * `video` - VIDEO * `imageset` - IMAGESET * `list` - LIST
 */
public enum ChunkType {
  VIDEO("video"),
    IMAGESET("imageset"),
    LIST("list");

  private String value;

  ChunkType(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ChunkType fromValue(String text) {
    for (ChunkType b : ChunkType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
