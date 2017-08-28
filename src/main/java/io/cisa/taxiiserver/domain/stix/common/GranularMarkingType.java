package io.cisa.taxiiserver.domain.stix.common;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A GranularMarkingType.
 */

@Document(collection = "granular_marking_type")
public class GranularMarkingType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("marking_ref")
    @JsonProperty("marking_ref")
    private String markingRef;

    @NotNull
    @Field("selectors")
    private String selectors;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarkingRef() {
        return markingRef;
    }

    public GranularMarkingType markingRef(String markingRef) {
        this.markingRef = markingRef;
        return this;
    }

    public void setMarkingRef(String markingRef) {
        this.markingRef = markingRef;
    }

    public String getSelectors() {
        return selectors;
    }

    public GranularMarkingType selectors(String selectors) {
        this.selectors = selectors;
        return this;
    }

    public void setSelectors(String selectors) {
        this.selectors = selectors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GranularMarkingType granularMarkingType = (GranularMarkingType) o;
        if (granularMarkingType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), granularMarkingType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GranularMarkingType{" +
            "id=" + getId() +
            ", markingRef='" + getMarkingRef() + "'" +
            ", selectors='" + getSelectors() + "'" +
            "}";
    }
}
