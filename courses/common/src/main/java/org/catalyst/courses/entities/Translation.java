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

    @ManyToMany(mappedBy = "translations", fetch = FetchType.EAGER)
    protected Set<CourseDetails> courseDetails = new HashSet<>();

    protected Translation() {
        // no-op constructor for hibernate, cannot be private
    }

    protected Translation(String name, String abbreviation) {
        super(name, abbreviation);
    }

    public Integer getId() {
        return this.id;
    }

    /**
     * @param courseDetails
     * @return <tt>true</tt> if this set did not already contain the specified
     *         courseDetails
     */
    protected boolean addCourseDetails(CourseDetails courseDetails) {
        return this.courseDetails.add(courseDetails);
    }

    /**
     * @param courseDetails
     * @return <tt>true</tt> if this set contained the specified courseDetails
     */
    protected boolean removeCourseDetails(CourseDetails courseDetails) { return this.courseDetails.remove(courseDetails); }

    /**
     * Don't use the id, which will change after saving
     * Don't use the list of CourseDetails, will cause a circular reference and the list is likely to change
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
        return "Translation{" +
                super.toString() +
                "} ";
    }
}
