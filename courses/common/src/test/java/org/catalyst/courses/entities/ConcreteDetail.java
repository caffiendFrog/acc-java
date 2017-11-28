package org.catalyst.courses.entities;

import javax.persistence.*;

@Entity
public class ConcreteDetail extends Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name = "table-generator", pkColumnValue = "concreteDetail_id")
    @Column(name = "concreteDetail_id")
    private int id;

    protected ConcreteDetail() {

    }

    public ConcreteDetail(String name){
        super(name);
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
