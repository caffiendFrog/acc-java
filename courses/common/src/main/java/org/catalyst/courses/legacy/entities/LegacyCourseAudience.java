package org.catalyst.courses.legacy.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="courses_course_audience")
public class LegacyCourseAudience {
    private final static Logger logger = LogManager.getLogger(LegacyCourseAudience.class);

    @Id
    @Column(name="id")
    private int id;

    @Column(name="course_id")
    private int courseId;

    @Column(name="audience_id")
    private int audienceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getAudienceId() {
        return audienceId;
    }

    public void setAudienceId(int audienceId) {
        this.audienceId = audienceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LegacyCourseAudience that = (LegacyCourseAudience) o;

        if (id != that.id) return false;
        if (courseId != that.courseId) return false;
        return audienceId == that.audienceId;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + courseId;
        result = 31 * result + audienceId;
        return result;
    }

    @Override
    public String toString() {
        return "LegacyCourseAudience{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", audienceId=" + audienceId +
                '}';
    }
}
