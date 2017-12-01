package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("INSTITUTION")
public class Institution extends BaseInstitution {
    private final static Logger logger = LogManager.getLogger(Institution.class);

    @OneToMany(mappedBy = "institution", fetch = FetchType.EAGER)
    @Cascade({CascadeType.SAVE_UPDATE})
    protected Set<Course> courses = new HashSet<>();

    protected Institution() {
        // no-op constructor for hibernate, cannot be private
    }

    public Institution(String name, String abbreviation) {
        super(name, abbreviation);
        setSponsor(false);
    }

    public boolean addCourse(Course course) {
        boolean result =  courses.add(course);
        return result;
    }

    public boolean removeCourse(Course course) {
        boolean result = courses.remove(course);
        return result;
    }

//    public BaseInstitution getAsBaseInstitution() {
//        return super;
//    }

    /**
     * Don't use the list of Course, will cause a circular reference and the list is likely to change
     */
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Institution{" +
                super.toString() +
                "} ";
    }
}
