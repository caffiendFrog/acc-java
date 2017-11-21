package org.catalyst.courses.legacy.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="courses_course_translation")
public class LegacyCourseTranslation {
    private final static Logger logger = LogManager.getLogger(LegacyCourseTranslation.class);

    @Id
    @Column(name="id")
    private int id;

    @Column(name="course_id")
    private int courseId;

    @Column(name="translation_id")
    private int translationId;

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

    public int getTranslationId() {
        return translationId;
    }

    public void setTranslationId(int translationId) {
        this.translationId = translationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LegacyCourseTranslation that = (LegacyCourseTranslation) o;

        if (id != that.id) return false;
        if (courseId != that.courseId) return false;
        return translationId == that.translationId;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + courseId;
        result = 31 * result + translationId;
        return result;
    }

    @Override
    public String toString() {
        return "LegacyCourseTranslation{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", translationId=" + translationId +
                '}';
    }
}
