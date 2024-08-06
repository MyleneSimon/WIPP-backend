package gov.nist.itl.ssd.wipp.backend.data.cvatannotation.cvatmodels;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User
 */

@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-01-22T10:53:14.701528-05:00[America/New_York]")


public class User  implements MetaUser {
  @JsonProperty("url")
  private String url = null;

  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("username")
  private String username = null;

  @JsonProperty("first_name")
  private String firstName = null;

  @JsonProperty("last_name")
  private String lastName = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("groups")
  
  private List<String> groups = new ArrayList<>();

  @JsonProperty("is_staff")
  private Boolean isStaff = null;

  @JsonProperty("is_superuser")
  private Boolean isSuperuser = null;

  @JsonProperty("is_active")
  private Boolean isActive = null;

  @JsonProperty("last_login")
  private OffsetDateTime lastLogin = null;

  @JsonProperty("date_joined")
  private OffsetDateTime dateJoined = null;

  public User url(String url) {
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

  public User id(Integer id) {
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

  public User username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Required. 150 characters or fewer. Letters, digits and @/./+/-/_ only.
   * @return username
   **/
  @Schema(required = true, description = "Required. 150 characters or fewer. Letters, digits and @/./+/-/_ only.")
    public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public User firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Get firstName
   * @return firstName
   **/
  @Schema(description = "")
    public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public User lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Get lastName
   * @return lastName
   **/
  @Schema(description = "")
    public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public User email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   **/
  @Schema(description = "")
    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public User groups(List<String> groups) {
    this.groups = groups;
    return this;
  }

  public User addGroupsItem(String groupsItem) {
    this.groups.add(groupsItem);
    return this;
  }

  /**
   * Get groups
   * @return groups
   **/
  @Schema(required = true, description = "")
    public List<String> getGroups() {
    return groups;
  }

  public void setGroups(List<String> groups) {
    this.groups = groups;
  }

  public User isStaff(Boolean isStaff) {
    this.isStaff = isStaff;
    return this;
  }

  /**
   * Designates whether the user can log into this admin site.
   * @return isStaff
   **/
  @Schema(description = "Designates whether the user can log into this admin site.")
    public Boolean isIsStaff() {
    return isStaff;
  }

  public void setIsStaff(Boolean isStaff) {
    this.isStaff = isStaff;
  }

  public User isSuperuser(Boolean isSuperuser) {
    this.isSuperuser = isSuperuser;
    return this;
  }

  /**
   * Designates that this user has all permissions without explicitly assigning them.
   * @return isSuperuser
   **/
  @Schema(description = "Designates that this user has all permissions without explicitly assigning them.")
    public Boolean isIsSuperuser() {
    return isSuperuser;
  }

  public void setIsSuperuser(Boolean isSuperuser) {
    this.isSuperuser = isSuperuser;
  }

  public User isActive(Boolean isActive) {
    this.isActive = isActive;
    return this;
  }

  /**
   * Designates whether this user should be treated as active. Unselect this instead of deleting accounts.
   * @return isActive
   **/
  @Schema(description = "Designates whether this user should be treated as active. Unselect this instead of deleting accounts.")
    public Boolean isIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public User lastLogin(OffsetDateTime lastLogin) {
    this.lastLogin = lastLogin;
    return this;
  }

  /**
   * Get lastLogin
   * @return lastLogin
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public OffsetDateTime getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(OffsetDateTime lastLogin) {
    this.lastLogin = lastLogin;
  }

  public User dateJoined(OffsetDateTime dateJoined) {
    this.dateJoined = dateJoined;
    return this;
  }

  /**
   * Get dateJoined
   * @return dateJoined
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public OffsetDateTime getDateJoined() {
    return dateJoined;
  }

  public void setDateJoined(OffsetDateTime dateJoined) {
    this.dateJoined = dateJoined;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(this.url, user.url) &&
        Objects.equals(this.id, user.id) &&
        Objects.equals(this.username, user.username) &&
        Objects.equals(this.firstName, user.firstName) &&
        Objects.equals(this.lastName, user.lastName) &&
        Objects.equals(this.email, user.email) &&
        Objects.equals(this.groups, user.groups) &&
        Objects.equals(this.isStaff, user.isStaff) &&
        Objects.equals(this.isSuperuser, user.isSuperuser) &&
        Objects.equals(this.isActive, user.isActive) &&
        Objects.equals(this.lastLogin, user.lastLogin) &&
        Objects.equals(this.dateJoined, user.dateJoined);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url, id, username, firstName, lastName, email, groups, isStaff, isSuperuser, isActive, lastLogin, dateJoined);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    groups: ").append(toIndentedString(groups)).append("\n");
    sb.append("    isStaff: ").append(toIndentedString(isStaff)).append("\n");
    sb.append("    isSuperuser: ").append(toIndentedString(isSuperuser)).append("\n");
    sb.append("    isActive: ").append(toIndentedString(isActive)).append("\n");
    sb.append("    lastLogin: ").append(toIndentedString(lastLogin)).append("\n");
    sb.append("    dateJoined: ").append(toIndentedString(dateJoined)).append("\n");
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
