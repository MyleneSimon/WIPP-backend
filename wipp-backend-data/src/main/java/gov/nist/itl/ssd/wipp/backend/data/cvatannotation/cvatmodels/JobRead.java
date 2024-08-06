package gov.nist.itl.ssd.wipp.backend.data.cvatannotation.cvatmodels;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import gov.nist.itl.ssd.wipp.backend.data.cvatannotation.cvatmodels.IssuesSummary;
import gov.nist.itl.ssd.wipp.backend.data.cvatannotation.cvatmodels.LabelsSummary;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;

/**
 * JobRead
 */

@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-01-22T10:53:14.701528-05:00[America/New_York]")


public class JobRead   {
  @JsonProperty("url")
  private String url = null;

  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("task_id")
  private Integer taskId = null;

  @JsonProperty("project_id")
  private Integer projectId = null;

  @JsonProperty("assignee")
  private AllOfJobReadAssignee assignee = null;

  @JsonProperty("guide_id")
  private Integer guideId = null;

  @JsonProperty("dimension")
  private String dimension = null;

  @JsonProperty("bug_tracker")
  private String bugTracker = null;

  @JsonProperty("status")
  private JobStatus status = null;

  @JsonProperty("stage")
  private JobStage stage = null;

  @JsonProperty("state")
  private OperationStatus state = null;

  @JsonProperty("mode")
  private String mode = null;

  @JsonProperty("frame_count")
  private Integer frameCount = null;

  @JsonProperty("start_frame")
  private Integer startFrame = null;

  @JsonProperty("stop_frame")
  private Integer stopFrame = null;

  @JsonProperty("data_chunk_size")
  private Integer dataChunkSize = null;

  @JsonProperty("data_compressed_chunk_type")
  private ChunkType dataCompressedChunkType = null;

  @JsonProperty("created_date")
  private OffsetDateTime createdDate = null;

  @JsonProperty("updated_date")
  private OffsetDateTime updatedDate = null;

  @JsonProperty("issues")
  private IssuesSummary issues = null;

  @JsonProperty("labels")
  private LabelsSummary labels = null;

  @JsonProperty("type")
  private JobType type = null;

  @JsonProperty("organization")
  private Integer organization = null;

  @JsonProperty("target_storage")
  private Storage targetStorage = null;

  @JsonProperty("source_storage")
  private Storage sourceStorage = null;

  public JobRead url(String url) {
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

  public JobRead id(Integer id) {
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

  public JobRead taskId(Integer taskId) {
    this.taskId = taskId;
    return this;
  }

  /**
   * Get taskId
   * @return taskId
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public Integer getTaskId() {
    return taskId;
  }

  public void setTaskId(Integer taskId) {
    this.taskId = taskId;
  }

  public JobRead projectId(Integer projectId) {
    this.projectId = projectId;
    return this;
  }

  /**
   * Get projectId
   * @return projectId
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public Integer getProjectId() {
    return projectId;
  }

  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }

  public JobRead assignee(AllOfJobReadAssignee assignee) {
    this.assignee = assignee;
    return this;
  }

  /**
   * Get assignee
   * @return assignee
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public AllOfJobReadAssignee getAssignee() {
    return assignee;
  }

  public void setAssignee(AllOfJobReadAssignee assignee) {
    this.assignee = assignee;
  }

  public JobRead guideId(Integer guideId) {
    this.guideId = guideId;
    return this;
  }

  /**
   * Get guideId
   * @return guideId
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public Integer getGuideId() {
    return guideId;
  }

  public void setGuideId(Integer guideId) {
    this.guideId = guideId;
  }

  public JobRead dimension(String dimension) {
    this.dimension = dimension;
    return this;
  }

  /**
   * Get dimension
   * @return dimension
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public String getDimension() {
    return dimension;
  }

  public void setDimension(String dimension) {
    this.dimension = dimension;
  }

  public JobRead bugTracker(String bugTracker) {
    this.bugTracker = bugTracker;
    return this;
  }

  /**
   * Get bugTracker
   * @return bugTracker
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public String getBugTracker() {
    return bugTracker;
  }

  public void setBugTracker(String bugTracker) {
    this.bugTracker = bugTracker;
  }

  public JobRead status(JobStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public JobStatus getStatus() {
    return status;
  }

  public void setStatus(JobStatus status) {
    this.status = status;
  }

  public JobRead stage(JobStage stage) {
    this.stage = stage;
    return this;
  }

  /**
   * Get stage
   * @return stage
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public JobStage getStage() {
    return stage;
  }

  public void setStage(JobStage stage) {
    this.stage = stage;
  }

  public JobRead state(OperationStatus state) {
    this.state = state;
    return this;
  }

  /**
   * Get state
   * @return state
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public OperationStatus getState() {
    return state;
  }

  public void setState(OperationStatus state) {
    this.state = state;
  }

  public JobRead mode(String mode) {
    this.mode = mode;
    return this;
  }

  /**
   * Get mode
   * @return mode
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public JobRead frameCount(Integer frameCount) {
    this.frameCount = frameCount;
    return this;
  }

  /**
   * Get frameCount
   * @return frameCount
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public Integer getFrameCount() {
    return frameCount;
  }

  public void setFrameCount(Integer frameCount) {
    this.frameCount = frameCount;
  }

  public JobRead startFrame(Integer startFrame) {
    this.startFrame = startFrame;
    return this;
  }

  /**
   * Get startFrame
   * minimum: -2147483648
   * maximum: 2147483647
   * @return startFrame
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public Integer getStartFrame() {
    return startFrame;
  }

  public void setStartFrame(Integer startFrame) {
    this.startFrame = startFrame;
  }

  public JobRead stopFrame(Integer stopFrame) {
    this.stopFrame = stopFrame;
    return this;
  }

  /**
   * Get stopFrame
   * minimum: -2147483648
   * maximum: 2147483647
   * @return stopFrame
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public Integer getStopFrame() {
    return stopFrame;
  }

  public void setStopFrame(Integer stopFrame) {
    this.stopFrame = stopFrame;
  }

  public JobRead dataChunkSize(Integer dataChunkSize) {
    this.dataChunkSize = dataChunkSize;
    return this;
  }

  /**
   * Get dataChunkSize
   * minimum: 0
   * maximum: 2147483647
   * @return dataChunkSize
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public Integer getDataChunkSize() {
    return dataChunkSize;
  }

  public void setDataChunkSize(Integer dataChunkSize) {
    this.dataChunkSize = dataChunkSize;
  }

  public JobRead dataCompressedChunkType(ChunkType dataCompressedChunkType) {
    this.dataCompressedChunkType = dataCompressedChunkType;
    return this;
  }

  /**
   * Get dataCompressedChunkType
   * @return dataCompressedChunkType
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public ChunkType getDataCompressedChunkType() {
    return dataCompressedChunkType;
  }

  public void setDataCompressedChunkType(ChunkType dataCompressedChunkType) {
    this.dataCompressedChunkType = dataCompressedChunkType;
  }

  public JobRead createdDate(OffsetDateTime createdDate) {
    this.createdDate = createdDate;
    return this;
  }

  /**
   * Get createdDate
   * @return createdDate
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public OffsetDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(OffsetDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public JobRead updatedDate(OffsetDateTime updatedDate) {
    this.updatedDate = updatedDate;
    return this;
  }

  /**
   * Get updatedDate
   * @return updatedDate
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public OffsetDateTime getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(OffsetDateTime updatedDate) {
    this.updatedDate = updatedDate;
  }

  public JobRead issues(IssuesSummary issues) {
    this.issues = issues;
    return this;
  }

  /**
   * Get issues
   * @return issues
   **/
  @Schema(required = true, description = "")
    public IssuesSummary getIssues() {
    return issues;
  }

  public void setIssues(IssuesSummary issues) {
    this.issues = issues;
  }

  public JobRead labels(LabelsSummary labels) {
    this.labels = labels;
    return this;
  }

  /**
   * Get labels
   * @return labels
   **/
  @Schema(required = true, description = "")
    public LabelsSummary getLabels() {
    return labels;
  }

  public void setLabels(LabelsSummary labels) {
    this.labels = labels;
  }

  public JobRead type(JobType type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public JobType getType() {
    return type;
  }

  public void setType(JobType type) {
    this.type = type;
  }

  public JobRead organization(Integer organization) {
    this.organization = organization;
    return this;
  }

  /**
   * Get organization
   * @return organization
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public Integer getOrganization() {
    return organization;
  }

  public void setOrganization(Integer organization) {
    this.organization = organization;
  }

  public JobRead targetStorage(Storage targetStorage) {
    this.targetStorage = targetStorage;
    return this;
  }

  /**
   * Get targetStorage
   * @return targetStorage
   **/
  @Schema(description = "")
    public Storage getTargetStorage() {
    return targetStorage;
  }

  public void setTargetStorage(Storage targetStorage) {
    this.targetStorage = targetStorage;
  }

  public JobRead sourceStorage(Storage sourceStorage) {
    this.sourceStorage = sourceStorage;
    return this;
  }

  /**
   * Get sourceStorage
   * @return sourceStorage
   **/
  @Schema(description = "")
    public Storage getSourceStorage() {
    return sourceStorage;
  }

  public void setSourceStorage(Storage sourceStorage) {
    this.sourceStorage = sourceStorage;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JobRead jobRead = (JobRead) o;
    return Objects.equals(this.url, jobRead.url) &&
        Objects.equals(this.id, jobRead.id) &&
        Objects.equals(this.taskId, jobRead.taskId) &&
        Objects.equals(this.projectId, jobRead.projectId) &&
        Objects.equals(this.assignee, jobRead.assignee) &&
        Objects.equals(this.guideId, jobRead.guideId) &&
        Objects.equals(this.dimension, jobRead.dimension) &&
        Objects.equals(this.bugTracker, jobRead.bugTracker) &&
        Objects.equals(this.status, jobRead.status) &&
        Objects.equals(this.stage, jobRead.stage) &&
        Objects.equals(this.state, jobRead.state) &&
        Objects.equals(this.mode, jobRead.mode) &&
        Objects.equals(this.frameCount, jobRead.frameCount) &&
        Objects.equals(this.startFrame, jobRead.startFrame) &&
        Objects.equals(this.stopFrame, jobRead.stopFrame) &&
        Objects.equals(this.dataChunkSize, jobRead.dataChunkSize) &&
        Objects.equals(this.dataCompressedChunkType, jobRead.dataCompressedChunkType) &&
        Objects.equals(this.createdDate, jobRead.createdDate) &&
        Objects.equals(this.updatedDate, jobRead.updatedDate) &&
        Objects.equals(this.issues, jobRead.issues) &&
        Objects.equals(this.labels, jobRead.labels) &&
        Objects.equals(this.type, jobRead.type) &&
        Objects.equals(this.organization, jobRead.organization) &&
        Objects.equals(this.targetStorage, jobRead.targetStorage) &&
        Objects.equals(this.sourceStorage, jobRead.sourceStorage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url, id, taskId, projectId, assignee, guideId, dimension, bugTracker, status, stage, state, mode, frameCount, startFrame, stopFrame, dataChunkSize, dataCompressedChunkType, createdDate, updatedDate, issues, labels, type, organization, targetStorage, sourceStorage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JobRead {\n");
    
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    taskId: ").append(toIndentedString(taskId)).append("\n");
    sb.append("    projectId: ").append(toIndentedString(projectId)).append("\n");
    sb.append("    assignee: ").append(toIndentedString(assignee)).append("\n");
    sb.append("    guideId: ").append(toIndentedString(guideId)).append("\n");
    sb.append("    dimension: ").append(toIndentedString(dimension)).append("\n");
    sb.append("    bugTracker: ").append(toIndentedString(bugTracker)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    stage: ").append(toIndentedString(stage)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    frameCount: ").append(toIndentedString(frameCount)).append("\n");
    sb.append("    startFrame: ").append(toIndentedString(startFrame)).append("\n");
    sb.append("    stopFrame: ").append(toIndentedString(stopFrame)).append("\n");
    sb.append("    dataChunkSize: ").append(toIndentedString(dataChunkSize)).append("\n");
    sb.append("    dataCompressedChunkType: ").append(toIndentedString(dataCompressedChunkType)).append("\n");
    sb.append("    createdDate: ").append(toIndentedString(createdDate)).append("\n");
    sb.append("    updatedDate: ").append(toIndentedString(updatedDate)).append("\n");
    sb.append("    issues: ").append(toIndentedString(issues)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    organization: ").append(toIndentedString(organization)).append("\n");
    sb.append("    targetStorage: ").append(toIndentedString(targetStorage)).append("\n");
    sb.append("    sourceStorage: ").append(toIndentedString(sourceStorage)).append("\n");
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
