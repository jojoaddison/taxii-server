package io.cisa.taxiiserver.domain.stix.common;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A TLPMarking.
 */

@Document(collection = "tlpmarking")
public class TLPMarking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("type")
    private String type;

    @Field("created")
    private ZonedDateTime created;

    @Field("definition_type")
    @JsonProperty("definition_type")
    private String definitionType;

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

    public TLPMarking type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public TLPMarking created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public String getDefinitionType() {
        return definitionType;
    }

    public TLPMarking definitionType(String definitionType) {
        this.definitionType = definitionType;
        return this;
    }

    public void setDefinitionType(String definitionType) {
        this.definitionType = definitionType;
    }

    public String getDefinition() {
        return definition;
    }

    public TLPMarking definition(String definition) {
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
        TLPMarking tLPMarking = (TLPMarking) o;
        if (tLPMarking.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tLPMarking.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TLPMarking{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", created='" + getCreated() + "'" +
            ", definitionType='" + getDefinitionType() + "'" +
            ", definition='" + getDefinition() + "'" +
            "}";
    }
}
