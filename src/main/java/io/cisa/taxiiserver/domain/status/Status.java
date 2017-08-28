package io.cisa.taxiiserver.domain.status;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A Status.
 */
@Document(collection = "status")
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("url")
    private String url;

    @Field("status")
    private String status;

    @Field("request_url")
    @JsonProperty("request_url")
    private String requestUrl;

    @Field("request_timestamp")
    @JsonProperty("request_timestamp")
    private Instant requestTimestamp;

    @Field("total_objects")
    @JsonProperty("total_objects")
    private Integer totalObjects;

    @Field("success_count")
    @JsonProperty("success_count")
    private Integer successCount;

    @Field("successes")
    private Set<StatusSuccess> successes = new HashSet<>();

    @Field("failure_count")
    @JsonProperty("failure_count")
    private Integer failureCount;

    @Field("failures")
    private Set<StatusFailure> failures = new HashSet<>();

    @Field("pending_count")
    @JsonProperty("pending_count")
    private Integer pendingCount;

    @Field("pendings")
    private Set<String> pendings = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public Status url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public Status status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public Status requestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
        return this;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Instant getRequestTimestamp() {
        return requestTimestamp;
    }

    public Status requestTimestamp(Instant requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
        return this;
    }

    public void setRequestTimestamp(Instant requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public Integer getTotalObjects() {
        return totalObjects;
    }

    public Status totalObjects(Integer totalObjects) {
        this.totalObjects = totalObjects;
        return this;
    }

    public void setTotalObjects(Integer totalObjects) {
        this.totalObjects = totalObjects;
    }

    public Integer getSuccessCount() {
    	successCount = successes.size();
        return successCount;
    }


    public Set<StatusSuccess> getSuccesses() {
        return successes;
    }

    public Status successes(Set<StatusSuccess> successes) {
        this.successes = successes;
        return this;
    }

    public void setSuccesses(Set<StatusSuccess> successes) {
        this.successes = successes;
    }

    public Integer getFailureCount() {
        return failureCount;
    }

    public Status failureCount(Integer failureCount) {
        this.failureCount = failureCount;
        return this;
    }

    public void setFailureCount(Integer failureCount) {
        this.failureCount = failureCount;
    }

    public Set<StatusFailure> getFailures() {
        return failures;
    }

    public Status failures(Set<StatusFailure> failures) {
        this.failures = failures;
        return this;
    }

    public void setFailures(Set<StatusFailure> failures) {
        this.failures = failures;
    }

    public Integer getPendingCount() {
        return pendingCount;
    }

    public Status pendingCount(Integer pendingCount) {
        this.pendingCount = pendingCount;
        return this;
    }

    public void setPendingCount(Integer pendingCount) {
        this.pendingCount = pendingCount;
    }

    public Set<String> getPendings() {
        return pendings;
    }

    public Status pendings(Set<String> pendings) {
        this.pendings = pendings;
        return this;
    }

    public void setPendings(Set<String> pendings) {
        this.pendings = pendings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Status status = (Status) o;
        if (status.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), status.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Status{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            ", status='" + getStatus() + "'" +
            ", requestUrl='" + getRequestUrl() + "'" +
            ", requestTimestamp='" + getRequestTimestamp() + "'" +
            ", totalObjects='" + getTotalObjects() + "'" +
            ", successCount='" + getSuccessCount() + "'" +
            ", successes='" + getSuccesses() + "'" +
            ", failureCount='" + getFailureCount() + "'" +
            ", failures='" + getFailures() + "'" +
            ", pendingCount='" + getPendingCount() + "'" +
            ", pendings='" + getPendings() + "'" +
            "}";
    }
}
