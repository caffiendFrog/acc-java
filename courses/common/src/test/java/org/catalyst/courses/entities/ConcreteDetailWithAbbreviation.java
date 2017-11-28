package org.catalyst.courses.entities;

import javax.persistence.*;

@Entity
public class ConcreteDetailWithAbbreviation extends DetailWithAbbreviation {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name = "table-generator", pkColumnValue = "concreteDetail_id")
    @Column(name = "concreteDetail_id")
    private int id;

    protected ConcreteDetailWithAbbreviation() {

    }

    public ConcreteDetailWithAbbreviation(String name, String abbreviation) {
        super(name, abbreviation);
    }

    public Integer getId() { return this.id; }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
