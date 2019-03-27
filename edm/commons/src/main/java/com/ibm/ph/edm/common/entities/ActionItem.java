package com.ibm.ph.edm.common.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Entity
@Table(name ="action_item")
public class ActionItem extends AbstractEntity {

    @Column(name="name")
    private String name;

    @Column(name="description")
    @Type(type="text")
    private String description;

    @Column(name="references")
    @Type(type="text")
    private String references;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_added")
    private Date dateAdded;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "added_by", nullable = true)
    private Employee addedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated")
    private Date dateUpdated;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "updated_by", nullable = true)
    private Employee updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_due")
    private Date dateDue;

    private Boolean required;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Employee getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Employee addedBy) {
        this.addedBy = addedBy;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Employee getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}
