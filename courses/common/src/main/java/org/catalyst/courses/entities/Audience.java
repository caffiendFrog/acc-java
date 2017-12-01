package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
@Table(name = "audience")
public class Audience extends Detail {
    private final static Logger logger = LogManager.getLogger(Audience.class);

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name = "table-generator", pkColumnValue = "audience_id")
    @Column(name = "audience_id")
    private int id;

    @ManyToMany(mappedBy = "audiences", fetch = FetchType.EAGER)
    protected Set<CourseDetails> courseDetails = new HashSet<>();

    protected Audience() {
        // no-op constructor for hibernate, cannot be private
    }

    public Audience(String name) {
        super(name);
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

    protected Map<Integer, CourseDetails> getCourseDetailsIdToCourseDetails() {
        return courseDetails.stream().collect(Collectors.toMap(CourseDetails::getId, Function.identity()));
    }

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

    /**
     * Intentionally omitting <code>courseDetails</code> because of circular reference
     * @return
     */
    @Override
    public String toString() {
        return "Audience{" +
                "id=" + id +
                "} " + super.toString();
    }
}
