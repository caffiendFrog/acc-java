package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("INSTITUTION")
public class Institution extends BaseInstitution {
    private final static Logger logger = LogManager.getLogger(Institution.class);

    @OneToMany(mappedBy = "institution")
    @Cascade({CascadeType.SAVE_UPDATE})
    protected Set<Course> courses = new HashSet<>();

    protected Institution() {
        // no-op constructor for hibernate, cannot be private
    }

    protected Institution(String name, String abbreviation) {
        super(name, abbreviation);
        setSponsor(false);
    }

    public boolean addCourse(Course course) {
        boolean result =  courses.add(course);
//        course.setInstitution(this);
        return result;
    }

    public boolean removeCourse(Course course) {
        boolean result = courses.remove(course);
//        course.setInstitution(null);
        return result;
    }

    public BaseInstitution getAsBaseInstitution() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }
}
