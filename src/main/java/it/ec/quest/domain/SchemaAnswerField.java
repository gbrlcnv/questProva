package it.ec.quest.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SchemaAnswerField.
 */
@Entity
@Table(name = "schema_answer_field")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchemaAnswerField implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_value")
    private String value;

    @ManyToOne
    private SchemaAnswer schemaAnswer;

    @OneToOne
    @JoinColumn(unique = true)
    private SchemaField field;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public SchemaAnswerField value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SchemaAnswer getSchemaAnswer() {
        return schemaAnswer;
    }

    public SchemaAnswerField schemaAnswer(SchemaAnswer schemaAnswer) {
        this.schemaAnswer = schemaAnswer;
        return this;
    }

    public void setSchemaAnswer(SchemaAnswer schemaAnswer) {
        this.schemaAnswer = schemaAnswer;
    }

    public SchemaField getField() {
        return field;
    }

    public SchemaAnswerField field(SchemaField schemaField) {
        this.field = schemaField;
        return this;
    }

    public void setField(SchemaField schemaField) {
        this.field = schemaField;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchemaAnswerField schemaAnswerField = (SchemaAnswerField) o;
        if (schemaAnswerField.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), schemaAnswerField.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SchemaAnswerField{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
