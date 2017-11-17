package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "courseDetail")
@PrimaryKeyJoinColumn(name = "detail_id")
public class CourseDetail extends Detail {
    private final static Logger logger = LogManager.getLogger(CourseDetail.class);

    @Column(name = "archived")
    private boolean archived;

    @Column(name = "webcast")
    private boolean webcast;

    @Column(name = "contactEmail")
    private String contactEmail;

    @Column(name = "contactUrl")
    private String contactUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "hours")
    private String hours;

    @Column(name = "maxEnroll")
    private String maxEnroll;

    @Column(name = "searchBlob")
    private String searchBlob;

    @Column(name = "courseDate")
    private Date date;

    @Column(name = "sortDate")
    private Date sortDate;

    @Column(name = "dateInfo")
    private String dateInfo;

    public CourseDetail() {
        // no-arg constructor for hibernate
    }

    /**
     * Creates a new course and sets it to active, not-archived, no webcast
     * @param courseName
     */
    public CourseDetail(String courseName) {
        super(courseName);
        this.archived = false;
        this.webcast = false;
    }

    public boolean isArchived() {
        return archived;
    }

    public void archive() { this.archived = true; }

    public void unArchive() { this.archived = false; }

    public boolean hasWebcast() {
        return webcast;
    }

    public void setWebcast(boolean webcast) {
        this.webcast = webcast;
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

    public String getSearchBlob() {
        return searchBlob;
    }

    public void setSearchBlob(String searchBlob) {
        this.searchBlob = searchBlob;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getSortDate() {
        return sortDate;
    }

    public void setSortDate(Date sortDate) {
        this.sortDate = sortDate;
    }

    public String getDateInfo() {
        return dateInfo;
    }

    public void setDateInfo(String dateInfo) {
        this.dateInfo = dateInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CourseDetail course = (CourseDetail) o;

        if (archived != course.archived) return false;
        if (webcast != course.webcast) return false;
        if (contactEmail != null ? !contactEmail.equals(course.contactEmail) : course.contactEmail != null)
            return false;
        if (contactUrl != null ? !contactUrl.equals(course.contactUrl) : course.contactUrl != null) return false;
        if (description != null ? !description.equals(course.description) : course.description != null) return false;
        if (hours != null ? !hours.equals(course.hours) : course.hours != null) return false;
        if (maxEnroll != null ? !maxEnroll.equals(course.maxEnroll) : course.maxEnroll != null) return false;
        if (searchBlob != null ? !searchBlob.equals(course.searchBlob) : course.searchBlob != null) return false;
        return dateInfo != null ? dateInfo.equals(course.dateInfo) : course.dateInfo == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (archived ? 1 : 0);
        result = 31 * result + (webcast ? 1 : 0);
        result = 31 * result + (contactEmail != null ? contactEmail.hashCode() : 0);
        result = 31 * result + (contactUrl != null ? contactUrl.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (hours != null ? hours.hashCode() : 0);
        result = 31 * result + (maxEnroll != null ? maxEnroll.hashCode() : 0);
        result = 31 * result + (searchBlob != null ? searchBlob.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (sortDate != null ? sortDate.hashCode() : 0);
        result = 31 * result + (dateInfo != null ? dateInfo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CourseDetail{" +
                "id=" + id +
                ", archived=" + archived +
                ", webcast=" + webcast +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactUrl='" + contactUrl + '\'' +
                ", description='" + description + '\'' +
                ", hours='" + hours + '\'' +
                ", maxEnroll='" + maxEnroll + '\'' +
                ", searchBlob='" + searchBlob + '\'' +
                ", date=" + date +
                ", sortDate=" + sortDate +
                ", dateInfo='" + dateInfo + '\'' +
                ", active=" + active +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
