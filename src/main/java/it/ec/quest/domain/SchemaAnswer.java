package it.ec.quest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SchemaAnswer.
 */
@Entity
@Table(name = "schema_answer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchemaAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @Column(name = "completion_date")
    private ZonedDateTime completionDate;

    @Column(name = "flag_complete")
    private String flagComplete;

    @ManyToOne
    private Schema1 schema1;

    @OneToOne
    @JoinColumn(unique = true)
    private Person person;

    @OneToMany(mappedBy = "schemaAnswer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchemaAnswerField> answerFields = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public SchemaAnswer creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getCompletionDate() {
        return completionDate;
    }

    public SchemaAnswer completionDate(ZonedDateTime completionDate) {
        this.completionDate = completionDate;
        return this;
    }

    public void setCompletionDate(ZonedDateTime completionDate) {
        this.completionDate = completionDate;
    }

    public String getFlagComplete() {
        return flagComplete;
    }

    public SchemaAnswer flagComplete(String flagComplete) {
        this.flagComplete = flagComplete;
        return this;
    }

    public void setFlagComplete(String flagComplete) {
        this.flagComplete = flagComplete;
    }

    public Schema1 getSchema1() {
        return schema1;
    }

    public SchemaAnswer schema1(Schema1 schema1) {
        this.schema1 = schema1;
        return this;
    }

    public void setSchema1(Schema1 schema1) {
        this.schema1 = schema1;
    }

    public Person getPerson() {
        return person;
    }

    public SchemaAnswer person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<SchemaAnswerField> getAnswerFields() {
        return answerFields;
    }

    public SchemaAnswer answerFields(Set<SchemaAnswerField> schemaAnswerFields) {
        this.answerFields = schemaAnswerFields;
        return this;
    }

    public SchemaAnswer addAnswerField(SchemaAnswerField schemaAnswerField) {
        this.answerFields.add(schemaAnswerField);
        schemaAnswerField.setSchemaAnswer(this);
        return this;
    }

    public SchemaAnswer removeAnswerField(SchemaAnswerField schemaAnswerField) {
        this.answerFields.remove(schemaAnswerField);
        schemaAnswerField.setSchemaAnswer(null);
        return this;
    }

    public void setAnswerFields(Set<SchemaAnswerField> schemaAnswerFields) {
        this.answerFields = schemaAnswerFields;
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
        SchemaAnswer schemaAnswer = (SchemaAnswer) o;
        if (schemaAnswer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), schemaAnswer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SchemaAnswer{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", completionDate='" + getCompletionDate() + "'" +
            ", flagComplete='" + getFlagComplete() + "'" +
            "}";
    }
}
