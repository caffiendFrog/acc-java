package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="translation")
public class Translation extends DetailWithAbbreviation {
    private final static Logger logger = LogManager.getLogger(Translation.class);

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name = "table-generator", pkColumnValue = "translation_id")
    @Column(name = "translation_id")
    private int id;

    @ManyToMany(mappedBy = "translations")
    protected Set<Course> courses = new HashSet<>();

    protected Translation() {
        // no-op constructor for hibernate, cannot be private
    }

    protected Translation(String name, String abbreviation) {
        super(name, abbreviation);
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
        return "Translation{" +
                "id=" + id +
                "} " + super.toString();
    }
}
