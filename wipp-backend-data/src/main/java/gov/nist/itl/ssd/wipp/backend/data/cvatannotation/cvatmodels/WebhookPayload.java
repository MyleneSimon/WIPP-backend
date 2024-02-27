package gov.nist.itl.ssd.wipp.backend.data.cvatannotation.cvatmodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebhookPayload {

    @JsonProperty("webhook_id")
    private Integer webhookId = null;

    @JsonProperty("event")
    private String event = null;

    @JsonProperty("before_update")
    private Object beforeUpdate = null;

    @JsonProperty("sender")
    private BasicUser sender = null;

    @JsonProperty("task")
    private TaskRead task = null;

    @JsonProperty("job")
    private JobRead job = null;

    public Integer getWebhookId() {
        return webhookId;
    }

    public void setWebhookId(Integer webhookId) {
        this.webhookId = webhookId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Object getBeforeUpdate() {
        return beforeUpdate;
    }

    public void setBeforeUpdate(Object beforeUpdate) {
        this.beforeUpdate = beforeUpdate;
    }

    public BasicUser getSender() {
        return sender;
    }

    public void setSender(BasicUser sender) {
        this.sender = sender;
    }

    public TaskRead getTask() {
        return task;
    }

    public void setTask(TaskRead task) {
        this.task = task;
    }

    public JobRead getJob() {
        return job;
    }

    public void setJob(JobRead job) {
        this.job = job;
    }
}
