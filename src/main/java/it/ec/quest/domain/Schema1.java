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
 * A Schema1.
 */
@Entity
@Table(name = "schema_1")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Schema1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    @NotNull
    @Column(name = "version_date", nullable = false)
    private ZonedDateTime versionDate;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "schema1")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchemaField> fields = new HashSet<>();

    @OneToMany(mappedBy = "schema1")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchemaAnswer> answers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Schema1 code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getVersion() {
        return version;
    }

    public Schema1 version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public ZonedDateTime getVersionDate() {
        return versionDate;
    }

    public Schema1 versionDate(ZonedDateTime versionDate) {
        this.versionDate = versionDate;
        return this;
    }

    public void setVersionDate(ZonedDateTime versionDate) {
        this.versionDate = versionDate;
    }

    public String getDescription() {
        return description;
    }

    public Schema1 description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<SchemaField> getFields() {
        return fields;
    }

    public Schema1 fields(Set<SchemaField> schemaFields) {
        this.fields = schemaFields;
        return this;
    }

    public Schema1 addField(SchemaField schemaField) {
        this.fields.add(schemaField);
        schemaField.setSchema1(this);
        return this;
    }

    public Schema1 removeField(SchemaField schemaField) {
        this.fields.remove(schemaField);
        schemaField.setSchema1(null);
        return this;
    }

    public void setFields(Set<SchemaField> schemaFields) {
        this.fields = schemaFields;
    }

    public Set<SchemaAnswer> getAnswers() {
        return answers;
    }

    public Schema1 answers(Set<SchemaAnswer> schemaAnswers) {
        this.answers = schemaAnswers;
        return this;
    }

    public Schema1 addAnswer(SchemaAnswer schemaAnswer) {
        this.answers.add(schemaAnswer);
        schemaAnswer.setSchema1(this);
        return this;
    }

    public Schema1 removeAnswer(SchemaAnswer schemaAnswer) {
        this.answers.remove(schemaAnswer);
        schemaAnswer.setSchema1(null);
        return this;
    }

    public void setAnswers(Set<SchemaAnswer> schemaAnswers) {
        this.answers = schemaAnswers;
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
        Schema1 schema1 = (Schema1) o;
        if (schema1.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), schema1.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Schema1{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", version=" + getVersion() +
            ", versionDate='" + getVersionDate() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
