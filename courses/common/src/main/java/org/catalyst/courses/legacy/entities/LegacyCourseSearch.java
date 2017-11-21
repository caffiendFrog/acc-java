package org.catalyst.courses.legacy.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="courses_course_search")
public class LegacyCourseSearch {
    private final static Logger logger = LogManager.getLogger(LegacyCourseSearch.class);

    @Id
    @Column(name="course_id")
    private int courseId;

    @Column(name="searchblob")
    private String searchblob;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getSearchblob() {
        return searchblob;
    }

    public void setSearchblob(String searchblob) {
        this.searchblob = searchblob;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LegacyCourseSearch that = (LegacyCourseSearch) o;

        if (courseId != that.courseId) return false;
        return searchblob != null ? searchblob.equals(that.searchblob) : that.searchblob == null;
    }

    @Override
    public int hashCode() {
        int result = courseId;
        result = 31 * result + (searchblob != null ? searchblob.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LegacyCourseSearch{" +
                "courseId=" + courseId +
                ", searchblob='" + searchblob + '\'' +
                '}';
    }
}
