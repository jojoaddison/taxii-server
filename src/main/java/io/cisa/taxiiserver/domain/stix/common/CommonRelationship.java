package io.cisa.taxiiserver.domain.stix.common;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A CommonRelationship.
 */

@Document(collection = "common_relationship")
public class CommonRelationship implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("derived_from")
    @JsonProperty("derived_from")
    private String derivedFrom;

    @Field("duplicate_of")
    @JsonProperty("duplicate_of")
    private String duplicateOf;

    @Field("related_to")
    @JsonProperty("related_to")
    private String relatedTo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDerivedFrom() {
        return derivedFrom;
    }

    public CommonRelationship derivedFrom(String derivedFrom) {
        this.derivedFrom = derivedFrom;
        return this;
    }

    public void setDerivedFrom(String derivedFrom) {
        this.derivedFrom = derivedFrom;
    }

    public String getDuplicateOf() {
        return duplicateOf;
    }

    public CommonRelationship duplicateOf(String duplicateOf) {
        this.duplicateOf = duplicateOf;
        return this;
    }

    public void setDuplicateOf(String duplicateOf) {
        this.duplicateOf = duplicateOf;
    }

    public String getRelatedTo() {
        return relatedTo;
    }

    public CommonRelationship relatedTo(String relatedTo) {
        this.relatedTo = relatedTo;
        return this;
    }

    public void setRelatedTo(String relatedTo) {
        this.relatedTo = relatedTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommonRelationship commonRelationship = (CommonRelationship) o;
        if (commonRelationship.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commonRelationship.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommonRelationship{" +
            "id=" + getId() +
            ", derivedFrom='" + getDerivedFrom() + "'" +
            ", duplicateOf='" + getDuplicateOf() + "'" +
            ", relatedTo='" + getRelatedTo() + "'" +
            "}";
    }
}
