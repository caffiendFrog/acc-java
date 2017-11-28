package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "audience")
public class Audience extends Detail {
    private final static Logger logger = LogManager.getLogger(Audience.class);

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name = "table-generator", pkColumnValue = "audience_id")
    @Column(name = "audience_id")
    private int id;

    @ManyToMany(mappedBy = "audiences")
    protected Set<Course> courses = new HashSet<>();

    protected Audience() {
        // no-op constructor for hibernate, cannot be private
    }

    public Audience(String name) {
        setName(name);
    }

    public Integer getId() {
        return this.id;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    @Override
    public String toString() {
        return "Audience{" +
                "id=" + id +
                "} " + super.toString();
    }
}
