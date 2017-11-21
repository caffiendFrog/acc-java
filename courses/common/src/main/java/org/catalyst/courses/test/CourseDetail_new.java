package org.catalyst.courses.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="courseDetail_new")
public class CourseDetail_new {
    private final static Logger logger = LogManager.getLogger(CourseDetail_new.class);

    @Id
    @GeneratedValue
    @Column(name="courseDetail_id")
    private int courseDetail_id;

    @OneToOne
    @JoinColumn(name="institutionalDetail_id")
    private InstitutionDetail_new institutionDetail;

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

    @Temporal(TemporalType.DATE)
    @Column(name = "courseDate")
    private Date date;

    @Temporal(TemporalType.DATE)
    @Column(name = "sortDate")
    private Date sortDate;

    @Column(name = "dateInfo")
    private String dateInfo;

    public int getCourseDetail_id() {
        return courseDetail_id;
    }

    public void setCourseDetail_id(int courseDetail_id) {
        this.courseDetail_id = courseDetail_id;
    }

    public InstitutionDetail_new getInstitutionDetail() {
        return institutionDetail;
    }

    public void setInstitutionDetail(InstitutionDetail_new institutionDetail) {
        this.institutionDetail = institutionDetail;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isWebcast() {
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
}
