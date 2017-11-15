package org.catalyst.services;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BaseEntry {
    private Long id;

    public BaseEntry() {
        // no-arg constructor for Hibernate
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }
}
