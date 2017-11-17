package org.catalyst.courses.entities.legacy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="courses_course")
public class LegacyCourse {
    private final static Logger logger = LogManager.getLogger(LegacyCourse.class);

    @Id
    @Column(name="id")
    private int id;

    @Column(name="institution_id")
    private int institutionId;

    @Column(name="archived")
    private boolean archived;

    @Column(name="contact_email")
    private String contactEmail;

    @Column(name="contact_url")
    private String contactUrl;

    @Column(name="course_date")
    private Date courseDate;

    @Column(name="date_info")
    private String dateInfo;

    @Column(name="description")
    private String description;

    @Column(name="hours")
    private String hours;

    @Column(name="max_enroll")
    private String maxEnroll;

    @Column(name="note")
    private String note;

    @Column(name="sort_date")
    private Date sortDate;

    @Column(name="title")
    private String title;

    @Column(name="updated")
    private Timestamp updated;

    @Column(name="webcast")
    private boolean webcast;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(int institutionId) {
        this.institutionId = institutionId;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }

    public Date getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(Date courseDate) {
        this.courseDate = courseDate;
    }

    public String getDateInfo() {
        return dateInfo;
    }

    public void setDateInfo(String dateInfo) {
        this.dateInfo = dateInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMaxEnroll() {
        return maxEnroll;
    }

    public void setMaxEnroll(String maxEnroll) {
        this.maxEnroll = maxEnroll;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getSortDate() {
        return sortDate;
    }

    public void setSortDate(Date sortDate) {
        this.sortDate = sortDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public boolean isWebcast() {
        return webcast;
    }

    public void setWebcast(boolean webcast) {
        this.webcast = webcast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LegacyCourse that = (LegacyCourse) o;

        if (id != that.id) return false;
        if (institutionId != that.institutionId) return false;
        if (archived != that.archived) return false;
        if (webcast != that.webcast) return false;
        if (contactEmail != null ? !contactEmail.equals(that.contactEmail) : that.contactEmail != null) return false;
        if (contactUrl != null ? !contactUrl.equals(that.contactUrl) : that.contactUrl != null) return false;
        if (courseDate != null ? !courseDate.equals(that.courseDate) : that.courseDate != null) return false;
        if (dateInfo != null ? !dateInfo.equals(that.dateInfo) : that.dateInfo != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (hours != null ? !hours.equals(that.hours) : that.hours != null) return false;
        if (maxEnroll != null ? !maxEnroll.equals(that.maxEnroll) : that.maxEnroll != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (sortDate != null ? !sortDate.equals(that.sortDate) : that.sortDate != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return updated != null ? updated.equals(that.updated) : that.updated == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + institutionId;
        result = 31 * result + (archived ? 1 : 0);
        result = 31 * result + (contactEmail != null ? contactEmail.hashCode() : 0);
        result = 31 * result + (contactUrl != null ? contactUrl.hashCode() : 0);
        result = 31 * result + (courseDate != null ? courseDate.hashCode() : 0);
        result = 31 * result + (dateInfo != null ? dateInfo.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (hours != null ? hours.hashCode() : 0);
        result = 31 * result + (maxEnroll != null ? maxEnroll.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (sortDate != null ? sortDate.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (webcast ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LegacyCourse{" +
                "id=" + id +
                ", institutionId=" + institutionId +
                ", archived=" + archived +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactUrl='" + contactUrl + '\'' +
                ", courseDate=" + courseDate +
                ", dateInfo='" + dateInfo + '\'' +
                ", description='" + description + '\'' +
                ", hours='" + hours + '\'' +
                ", maxEnroll='" + maxEnroll + '\'' +
                ", note='" + note + '\'' +
                ", sortDate=" + sortDate +
                ", title='" + title + '\'' +
                ", updated=" + updated +
                ", webcast=" + webcast +
                '}';
    }
}
