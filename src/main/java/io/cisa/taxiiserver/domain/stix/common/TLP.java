package io.cisa.taxiiserver.domain.stix.common;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TLP.
 */

@Document(collection = "tlp")
public class TLP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("tlp")
    private String tlp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTlp() {
        return tlp;
    }

    public TLP tlp(String tlp) {
        this.tlp = tlp;
        return this;
    }

    public void setTlp(String tlp) {
        this.tlp = tlp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TLP tLP = (TLP) o;
        if (tLP.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tLP.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TLP{" +
            "id=" + getId() +
            ", tlp='" + getTlp() + "'" +
            "}";
    }
}
