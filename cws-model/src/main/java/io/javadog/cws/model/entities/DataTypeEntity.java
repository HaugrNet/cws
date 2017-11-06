/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-model)
 * =============================================================================
 */
package io.javadog.cws.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "type.findAll",
                query = "select d " +
                        "from DataTypeEntity d " +
                        "order by d.name asc"),
        @NamedQuery(name = "type.findMatching",
                query = "select d from DataTypeEntity d " +
                        "where lower(d.name) = lower(:name)"),
        @NamedQuery(name = "type.countUsage",
                query = "select count(m.id) " +
                        "from MetadataEntity m " +
                        "where m.type.id = :id")
})
@Table(name = "cws_datatypes")
public final class DataTypeEntity extends CWSEntity {

    @Column(name = "datatype_name", unique = true, nullable = false)
    private String name = null;

    @Column(name = "datatype_value", nullable = false)
    private String type = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
