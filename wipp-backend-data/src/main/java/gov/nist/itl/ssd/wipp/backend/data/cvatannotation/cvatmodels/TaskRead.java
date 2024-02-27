package gov.nist.itl.ssd.wipp.backend.data.cvatannotation.cvatmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * TaskRead
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-01-22T10:53:14.701528-05:00[America/New_York]")


public class TaskRead   {
  @JsonProperty("url")
  private String url = null;

  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("project_id")
  private Integer projectId = null;

  @JsonProperty("mode")
  private String mode = null;

  @JsonProperty("owner")
  private AllOfTaskReadOwner owner = null;

  @JsonProperty("assignee")
  private AllOfTaskReadAssignee assignee = null;

  @JsonProperty("bug_tracker")
  private String bugTracker = null;

  @JsonProperty("created_date")
  private OffsetDateTime createdDate = null;

  @JsonProperty("updated_date")
  private OffsetDateTime updatedDate = null;

  @JsonProperty("overlap")
  private Integer overlap = null;

  @JsonProperty("segment_size")
  private Integer segmentSize = null;

  @JsonProperty("status")
  private JobStatus status = null;

  @JsonProperty("data_chunk_size")
  private Integer dataChunkSize = null;

  @JsonProperty("data_compressed_chunk_type")
  private ChunkType dataCompressedChunkType = null;

  @JsonProperty("guide_id")
  private Integer guideId = null;

  @JsonProperty("data_original_chunk_type")
  private ChunkType dataOriginalChunkType = null;

  @JsonProperty("size")
  private Integer size = null;

  @JsonProperty("image_quality")
  private Integer imageQuality = null;

  @JsonProperty("data")
  private Integer data = null;

  @JsonProperty("dimension")
  private String dimension = null;

  @JsonProperty("subset")
  private String subset = null;

  @JsonProperty("organization")
  private Integer organization = null;

  @JsonProperty("target_storage")
  private AllOfTaskReadTargetStorage targetStorage = null;

  @JsonProperty("source_storage")
  private AllOfTaskReadSourceStorage sourceStorage = null;

  @JsonProperty("jobs")
  private JobsSummary jobs = null;

  @JsonProperty("labels")
  private LabelsSummary labels = null;

  public TaskRead url(String url) {
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

  public TaskRead id(Integer id) {
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

  public TaskRead name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TaskRead projectId(Integer projectId) {
    this.projectId = projectId;
    return this;
  }

  /**
   * Get projectId
   * @return projectId
   **/
  @Schema(description = "")
    public Integer getProjectId() {
    return projectId;
  }

  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }

  public TaskRead mode(String mode) {
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

  public TaskRead owner(AllOfTaskReadOwner owner) {
    this.owner = owner;
    return this;
  }

  /**
   * Get owner
   * @return owner
   **/
  @Schema(description = "")
    public AllOfTaskReadOwner getOwner() {
    return owner;
  }

  public void setOwner(AllOfTaskReadOwner owner) {
    this.owner = owner;
  }

  public TaskRead assignee(AllOfTaskReadAssignee assignee) {
    this.assignee = assignee;
    return this;
  }

  /**
   * Get assignee
   * @return assignee
   **/
  @Schema(description = "")
    public AllOfTaskReadAssignee getAssignee() {
    return assignee;
  }

  public void setAssignee(AllOfTaskReadAssignee assignee) {
    this.assignee = assignee;
  }

  public TaskRead bugTracker(String bugTracker) {
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

  public TaskRead createdDate(OffsetDateTime createdDate) {
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

  public TaskRead updatedDate(OffsetDateTime updatedDate) {
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

  public TaskRead overlap(Integer overlap) {
    this.overlap = overlap;
    return this;
  }

  /**
   * Get overlap
   * @return overlap
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public Integer getOverlap() {
    return overlap;
  }

  public void setOverlap(Integer overlap) {
    this.overlap = overlap;
  }

  public TaskRead segmentSize(Integer segmentSize) {
    this.segmentSize = segmentSize;
    return this;
  }

  /**
   * Get segmentSize
   * @return segmentSize
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public Integer getSegmentSize() {
    return segmentSize;
  }

  public void setSegmentSize(Integer segmentSize) {
    this.segmentSize = segmentSize;
  }

  public TaskRead status(JobStatus status) {
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

  public TaskRead dataChunkSize(Integer dataChunkSize) {
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

  public TaskRead dataCompressedChunkType(ChunkType dataCompressedChunkType) {
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

  public TaskRead guideId(Integer guideId) {
    this.guideId = guideId;
    return this;
  }

  /**
   * Get guideId
   * @return guideId
   **/
  @Schema(description = "")
    public Integer getGuideId() {
    return guideId;
  }

  public void setGuideId(Integer guideId) {
    this.guideId = guideId;
  }

  public TaskRead dataOriginalChunkType(ChunkType dataOriginalChunkType) {
    this.dataOriginalChunkType = dataOriginalChunkType;
    return this;
  }

  /**
   * Get dataOriginalChunkType
   * @return dataOriginalChunkType
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public ChunkType getDataOriginalChunkType() {
    return dataOriginalChunkType;
  }

  public void setDataOriginalChunkType(ChunkType dataOriginalChunkType) {
    this.dataOriginalChunkType = dataOriginalChunkType;
  }

  public TaskRead size(Integer size) {
    this.size = size;
    return this;
  }

  /**
   * Get size
   * minimum: 0
   * maximum: 2147483647
   * @return size
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public TaskRead imageQuality(Integer imageQuality) {
    this.imageQuality = imageQuality;
    return this;
  }

  /**
   * Get imageQuality
   * minimum: 0
   * maximum: 32767
   * @return imageQuality
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public Integer getImageQuality() {
    return imageQuality;
  }

  public void setImageQuality(Integer imageQuality) {
    this.imageQuality = imageQuality;
  }

  public TaskRead data(Integer data) {
    this.data = data;
    return this;
  }

  /**
   * Get data
   * @return data
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public Integer getData() {
    return data;
  }

  public void setData(Integer data) {
    this.data = data;
  }

  public TaskRead dimension(String dimension) {
    this.dimension = dimension;
    return this;
  }

  /**
   * Get dimension
   * @return dimension
   **/
  @Schema(description = "")
    public String getDimension() {
    return dimension;
  }

  public void setDimension(String dimension) {
    this.dimension = dimension;
  }

  public TaskRead subset(String subset) {
    this.subset = subset;
    return this;
  }

  /**
   * Get subset
   * @return subset
   **/
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "")
    public String getSubset() {
    return subset;
  }

  public void setSubset(String subset) {
    this.subset = subset;
  }

  public TaskRead organization(Integer organization) {
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

  public TaskRead targetStorage(AllOfTaskReadTargetStorage targetStorage) {
    this.targetStorage = targetStorage;
    return this;
  }

  /**
   * Get targetStorage
   * @return targetStorage
   **/
  @Schema(description = "")
    public AllOfTaskReadTargetStorage getTargetStorage() {
    return targetStorage;
  }

  public void setTargetStorage(AllOfTaskReadTargetStorage targetStorage) {
    this.targetStorage = targetStorage;
  }

  public TaskRead sourceStorage(AllOfTaskReadSourceStorage sourceStorage) {
    this.sourceStorage = sourceStorage;
    return this;
  }

  /**
   * Get sourceStorage
   * @return sourceStorage
   **/
  @Schema(description = "")
    public AllOfTaskReadSourceStorage getSourceStorage() {
    return sourceStorage;
  }

  public void setSourceStorage(AllOfTaskReadSourceStorage sourceStorage) {
    this.sourceStorage = sourceStorage;
  }

  public TaskRead jobs(JobsSummary jobs) {
    this.jobs = jobs;
    return this;
  }

  /**
   * Get jobs
   * @return jobs
   **/
  @Schema(required = true, description = "")
    public JobsSummary getJobs() {
    return jobs;
  }

  public void setJobs(JobsSummary jobs) {
    this.jobs = jobs;
  }

  public TaskRead labels(LabelsSummary labels) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaskRead taskRead = (TaskRead) o;
    return Objects.equals(this.url, taskRead.url) &&
        Objects.equals(this.id, taskRead.id) &&
        Objects.equals(this.name, taskRead.name) &&
        Objects.equals(this.projectId, taskRead.projectId) &&
        Objects.equals(this.mode, taskRead.mode) &&
        Objects.equals(this.owner, taskRead.owner) &&
        Objects.equals(this.assignee, taskRead.assignee) &&
        Objects.equals(this.bugTracker, taskRead.bugTracker) &&
        Objects.equals(this.createdDate, taskRead.createdDate) &&
        Objects.equals(this.updatedDate, taskRead.updatedDate) &&
        Objects.equals(this.overlap, taskRead.overlap) &&
        Objects.equals(this.segmentSize, taskRead.segmentSize) &&
        Objects.equals(this.status, taskRead.status) &&
        Objects.equals(this.dataChunkSize, taskRead.dataChunkSize) &&
        Objects.equals(this.dataCompressedChunkType, taskRead.dataCompressedChunkType) &&
        Objects.equals(this.guideId, taskRead.guideId) &&
        Objects.equals(this.dataOriginalChunkType, taskRead.dataOriginalChunkType) &&
        Objects.equals(this.size, taskRead.size) &&
        Objects.equals(this.imageQuality, taskRead.imageQuality) &&
        Objects.equals(this.data, taskRead.data) &&
        Objects.equals(this.dimension, taskRead.dimension) &&
        Objects.equals(this.subset, taskRead.subset) &&
        Objects.equals(this.organization, taskRead.organization) &&
        Objects.equals(this.targetStorage, taskRead.targetStorage) &&
        Objects.equals(this.sourceStorage, taskRead.sourceStorage) &&
        Objects.equals(this.jobs, taskRead.jobs) &&
        Objects.equals(this.labels, taskRead.labels);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url, id, name, projectId, mode, owner, assignee, bugTracker, createdDate, updatedDate, overlap, segmentSize, status, dataChunkSize, dataCompressedChunkType, guideId, dataOriginalChunkType, size, imageQuality, data, dimension, subset, organization, targetStorage, sourceStorage, jobs, labels);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskRead {\n");
    
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    projectId: ").append(toIndentedString(projectId)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
    sb.append("    assignee: ").append(toIndentedString(assignee)).append("\n");
    sb.append("    bugTracker: ").append(toIndentedString(bugTracker)).append("\n");
    sb.append("    createdDate: ").append(toIndentedString(createdDate)).append("\n");
    sb.append("    updatedDate: ").append(toIndentedString(updatedDate)).append("\n");
    sb.append("    overlap: ").append(toIndentedString(overlap)).append("\n");
    sb.append("    segmentSize: ").append(toIndentedString(segmentSize)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    dataChunkSize: ").append(toIndentedString(dataChunkSize)).append("\n");
    sb.append("    dataCompressedChunkType: ").append(toIndentedString(dataCompressedChunkType)).append("\n");
    sb.append("    guideId: ").append(toIndentedString(guideId)).append("\n");
    sb.append("    dataOriginalChunkType: ").append(toIndentedString(dataOriginalChunkType)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    imageQuality: ").append(toIndentedString(imageQuality)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    dimension: ").append(toIndentedString(dimension)).append("\n");
    sb.append("    subset: ").append(toIndentedString(subset)).append("\n");
    sb.append("    organization: ").append(toIndentedString(organization)).append("\n");
    sb.append("    targetStorage: ").append(toIndentedString(targetStorage)).append("\n");
    sb.append("    sourceStorage: ").append(toIndentedString(sourceStorage)).append("\n");
    sb.append("    jobs: ").append(toIndentedString(jobs)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
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
