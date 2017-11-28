package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="institution")
public class Institution extends DetailWithAbbreviation {
    private final static Logger logger = LogManager.getLogger(Institution.class);

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name = "table-generator", pkColumnValue = "institution_id")
    @Column(name = "institution_id")
    private int id;

    @Column(name = "sponsor")
    private boolean sponsor;

    @ManyToMany(mappedBy = "sponsors")
    protected Set<Course> courses = new HashSet<>();

    protected Institution() {
        // no-op constructor for hibernate, cannot be private
    }

    public Institution(String name, String abbreviation) {
        setName(name);
        setAbbreviation(abbreviation);
    }

    public Integer getId() {
        return this.id;
    }

    public boolean isSponsor() {
        return sponsor;
    }

    public void setSponsor(boolean sponsor) {
        this.sponsor = sponsor;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    @Override
    public String toString() {
        return "Institution{" +
                "id=" + id +
                ", sponsor=" + sponsor +
                "} " + super.toString();
    }
}
