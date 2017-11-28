package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "competency")
public class Competency extends Detail {
    private final static Logger logger = LogManager.getLogger(Competency.class);

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name = "table-generator", pkColumnValue = "competency_id")
    @Column(name = "competency_id")
    private int id;

    @ManyToMany(mappedBy = "competencies")
    protected Set<Course> courses = new HashSet<>();

    protected Competency() {
        // no-op constructor for hibernate, cannot be private
    }

    public Competency(String name) { super(name); };

    public Integer getId() {
        return this.id;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    /**
     * Intentionally omitting <code>courses</code> because of circular reference
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Competency that = (Competency) o;

        return id == that.id;
    }

    /**
     * Intentionally omitting <code>courses</code> because of circular reference
     * @return
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        return result;
    }

    /**
     * Intentionally omitting <code>courses</code> because of circular reference
     * @return
     */
    @Override
    public String toString() {
        return "Competency{" +
                "id=" + id +
                "} " + super.toString();
    }
}
