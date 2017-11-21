package org.catalyst.courses.legacy.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="courses_course_sponsor")
public class LegacyCourseSponsor {
    private final static Logger logger = LogManager.getLogger(LegacyCourseSponsor.class);

    @Id
    @Column(name="id")
    private int id;

    @Column(name="course_id")
    private int courseId;

    @Column(name="sponsor_id")
    private int sponsorId;

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

    public int getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(int sponsorId) {
        this.sponsorId = sponsorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LegacyCourseSponsor that = (LegacyCourseSponsor) o;

        if (id != that.id) return false;
        if (courseId != that.courseId) return false;
        return sponsorId == that.sponsorId;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + courseId;
        result = 31 * result + sponsorId;
        return result;
    }

    @Override
    public String toString() {
        return "LegacyCourseSponsor{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", sponsorId=" + sponsorId +
                '}';
    }
}
