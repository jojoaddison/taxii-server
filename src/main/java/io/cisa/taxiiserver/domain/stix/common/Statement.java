package io.cisa.taxiiserver.domain.stix.common;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Statement.
 */

@Document(collection = "statement")
public class Statement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("statement")
    private String statement;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public Statement statement(String statement) {
        this.statement = statement;
        return this;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Statement statement = (Statement) o;
        if (statement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), statement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Statement{" +
            "id=" + getId() +
            ", statement='" + getStatement() + "'" +
            "}";
    }
}
