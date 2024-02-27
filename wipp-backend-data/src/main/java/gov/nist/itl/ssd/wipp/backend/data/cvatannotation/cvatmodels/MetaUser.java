package gov.nist.itl.ssd.wipp.backend.data.cvatannotation.cvatmodels;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* MetaUser
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = User.class, name = "User"),
  @JsonSubTypes.Type(value = BasicUser.class, name = "BasicUser")
})
public interface MetaUser {

}
