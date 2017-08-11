package io.cisa.taxiiserver.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ApiRoot.
 */
@Document(collection = "api_root")
public class ApiRoot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("display_name")
    private String displayName;

    @Field("description")
    private String description;

    @NotNull
    @Field("versions")
    private Set<String> versions = new HashSet<>();

    @Field("channels")
    private Set<Channel> channels = new HashSet<>();

    @Field("collections")
    private Set<Collection> collections = new HashSet<>();

    @Field("max_content_length")
    private Long maxContentLength;

    @Field("created_date")
    private ZonedDateTime createdDate;

    @Field("last_modified_date")
    private ZonedDateTime lastModifiedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ApiRoot displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public ApiRoot description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getVersions() {
        return versions;
    }

    public ApiRoot versions(Set<String> versions) {
        this.versions = versions;
        return this;
    }

    public void setVersions(Set<String> versions) {
        this.versions = versions;
    }

    public Set<Channel> getChannels() {
        return channels;
    }

    public ApiRoot channels(Set<Channel> channels) {
        this.channels = channels;
        return this;
    }

    public void setChannels(Set<Channel> channels) {
        this.channels = channels;
    }

    public Set<Collection> getCollections() {
        return collections;
    }

    public ApiRoot collections(Set<Collection> collections) {
        this.collections = collections;
        return this;
    }

    public void setCollections(Set<Collection> collections) {
        this.collections = collections;
    }

    public Long getMaxContentLength() {
        return maxContentLength;
    }

    public ApiRoot maxContentLength(Long maxContentLength) {
        this.maxContentLength = maxContentLength;
        return this;
    }

    public void setMaxContentLength(Long maxContentLength) {
        this.maxContentLength = maxContentLength;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public ApiRoot createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public ApiRoot lastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApiRoot apiRoot = (ApiRoot) o;
        if (apiRoot.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apiRoot.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApiRoot{" +
            "id=" + getId() +
            ", displayName='" + getDisplayName() + "'" +
            ", description='" + getDescription() + "'" +
            ", versions='" + getVersions() + "'" +
            ", channels='" + getChannels() + "'" +
            ", collections='" + getCollections() + "'" +
            ", maxContentLength='" + getMaxContentLength() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
