package org.catalyst.courses.legacy.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="courses_course_keyword")
public class LegacyCourseKeyword {
    private final static Logger logger = LogManager.getLogger(LegacyCourseKeyword.class);

    @Id
    @Column(name="id")
    private int id;

    @Column(name="course_id")
    private int courseId;

    @Column(name="keyword_id")
    private int keywordId;

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

    public int getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(int keywordId) {
        this.keywordId = keywordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LegacyCourseKeyword that = (LegacyCourseKeyword) o;

        if (id != that.id) return false;
        if (courseId != that.courseId) return false;
        return keywordId == that.keywordId;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + courseId;
        result = 31 * result + keywordId;
        return result;
    }

    @Override
    public String toString() {
        return "LegacyCourseKeyword{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", keywordId=" + keywordId +
                '}';
    }
}
