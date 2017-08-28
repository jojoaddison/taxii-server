package io.cisa.taxiiserver.domain.stix.common;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.cisa.taxiiserver.domain.stix.StixType;

/**
 * A MarkingDefinition.
 */

@Document(collection = "marking_definition")
public class MarkingDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("type")
    private String type;

    @Field("created_by_ref")
    @JsonProperty("created_by_ref")
    private String createdByRef;

    @Field("created")
    private ZonedDateTime created;

    @Field("external_references")
    @JsonProperty("external_references")
    private String externalReferences;

    @Field("object_marking_refs")
    @JsonProperty("object_marking_refs")
    private String objectMarkingRefs;

    @Field("granular_markings")
    @JsonProperty("granular_markings")
    private StixType granularMarkings;

    @Field("definition_type")
    @JsonProperty("definition_type")
    private String definitionType;

    @NotNull
    @Field("definition")
    private String definition;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public MarkingDefinition type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedByRef() {
        return createdByRef;
    }

    public MarkingDefinition createdByRef(String createdByRef) {
        this.createdByRef = createdByRef;
        return this;
    }

    public void setCreatedByRef(String createdByRef) {
        this.createdByRef = createdByRef;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public MarkingDefinition created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public String getExternalReferences() {
        return externalReferences;
    }

    public MarkingDefinition externalReferences(String externalReferences) {
        this.externalReferences = externalReferences;
        return this;
    }

    public void setExternalReferences(String externalReferences) {
        this.externalReferences = externalReferences;
    }

    public String getObjectMarkingRefs() {
        return objectMarkingRefs;
    }

    public MarkingDefinition objectMarkingRefs(String objectMarkingRefs) {
        this.objectMarkingRefs = objectMarkingRefs;
        return this;
    }

    public void setObjectMarkingRefs(String objectMarkingRefs) {
        this.objectMarkingRefs = objectMarkingRefs;
    }

    public StixType getGranularMarkings() {
        return granularMarkings;
    }

    public MarkingDefinition granularMarkings(StixType granularMarkings) {
        this.granularMarkings = granularMarkings;
        return this;
    }

    public void setGranularMarkings(StixType granularMarkings) {
        this.granularMarkings = granularMarkings;
    }

    public String getDefinitionType() {
        return definitionType;
    }

    public MarkingDefinition definitionType(String definitionType) {
        this.definitionType = definitionType;
        return this;
    }

    public void setDefinitionType(String definitionType) {
        this.definitionType = definitionType;
    }

    public String getDefinition() {
        return definition;
    }

    public MarkingDefinition definition(String definition) {
        this.definition = definition;
        return this;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MarkingDefinition markingDefinition = (MarkingDefinition) o;
        if (markingDefinition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), markingDefinition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarkingDefinition{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", createdByRef='" + getCreatedByRef() + "'" +
            ", created='" + getCreated() + "'" +
            ", externalReferences='" + getExternalReferences() + "'" +
            ", objectMarkingRefs='" + getObjectMarkingRefs() + "'" +
            ", granularMarkings='" + getGranularMarkings() + "'" +
            ", definitionType='" + getDefinitionType() + "'" +
            ", definition='" + getDefinition() + "'" +
            "}";
    }
}
