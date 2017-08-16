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
 * A Discovery.
 */
@Document(collection = "discovery")
public class Discovery implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Field("display_name")
    private String displayName;

    @Field("description")
    private String description;

    @Field("contact")
    private String contact;

    @Field("default")
    private String defaultURL;

    @Field("api_roots")
    private Set<String> apiRoots = new HashSet<>();

    @Field("last_modified")
    private ZonedDateTime lastModified;

    @Field("created_date")
    private ZonedDateTime createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Discovery displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public Discovery description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public Discovery contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDefaultURL() {
        return defaultURL;
    }

    public Discovery defaultURL(String defaultURL) {
        this.defaultURL = defaultURL;
        return this;
    }

    public void setDefaultURL(String defaultURL) {
        this.defaultURL = defaultURL;
    }

    public Set<String> getApiRoots() {
        return apiRoots;
    }

    public Discovery apiRoots(Set<String> apiRoots) {
        this.apiRoots = apiRoots;
        return this;
    }

    public void setApiRoots(Set<String> apiRoots) {
        this.apiRoots = apiRoots;
    }

    public ZonedDateTime getLastModified() {
        return lastModified;
    }

    public Discovery lastModified(ZonedDateTime lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public void setLastModified(ZonedDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Discovery createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Discovery discovery = (Discovery) o;
        if (discovery.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), discovery.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Discovery{" +
            "id=" + getId() +
            ", displayName='" + getDisplayName() + "'" +
            ", description='" + getDescription() + "'" +
            ", contact='" + getContact() + "'" +
            ", defaultURL='" + getDefaultURL() + "'" +
            ", apiRoots='" + getApiRoots() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
