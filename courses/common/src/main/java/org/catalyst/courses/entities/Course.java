package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course extends Detail {
    private final static Logger logger = LogManager.getLogger(Course.class);

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name = "table-generator", pkColumnValue = "course_id")
    @Column(name = "course_id")
    private int id;

    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    private Institution institution;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
    protected Set<CourseDetails> courseDetails = new HashSet<>();

    @Column(name = "archived")
    private boolean archived = false;

    @Column(name = "webcast")
    private boolean webcast = false;

    @Column(name = "contactEmail")
    private String contactEmail;

    @Column(name = "contactUrl")
    private String contactUrl;

    @Column(name = "description")
    @Lob
    private String description;

    @Column(name = "hours")
    private String hours;

    @Column(name = "maxEnroll")
    private String maxEnroll;

    @Column(name = "searchBlob")
    @Lob
    private String searchBlob;

    @Temporal(TemporalType.DATE)
    @Column(name = "courseDate")
    private Date date;

    @Temporal(TemporalType.DATE)
    @Column(name = "sortDate")
    private Date sortDate;

    @Column(name = "dateInfo")
    private String dateInfo;

    protected Course() {
        // no-op constructor for hibernate, cannot be private
    }

    public Course(String courseName) {
        super(courseName);
        archived = false;
        webcast = false;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        institution.addCourse(this);
        this.institution = institution;
    }

    public void removeInstitution(Institution institution) {
        institution.removeCourse(this);
        this.institution = null;
    }

    public boolean isArchived() {
        return archived;
    }

    public void archive() {
        archived = true;
    }

    public void unarchive() {
        archived = false;
    }

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

    /**
     * Don't use the id, which will change after saving
     * Don't use the list of CourseDetails, will cause a circular reference and the list is likely to change
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Course course = (Course) o;

        if (archived != course.archived) return false;
        if (webcast != course.webcast) return false;
        if (institution != null ? !institution.equals(course.institution) : course.institution != null) return false;
        if (contactEmail != null ? !contactEmail.equals(course.contactEmail) : course.contactEmail != null)
            return false;
        if (contactUrl != null ? !contactUrl.equals(course.contactUrl) : course.contactUrl != null) return false;
        if (description != null ? !description.equals(course.description) : course.description != null) return false;
        if (hours != null ? !hours.equals(course.hours) : course.hours != null) return false;
        if (maxEnroll != null ? !maxEnroll.equals(course.maxEnroll) : course.maxEnroll != null) return false;
        if (searchBlob != null ? !searchBlob.equals(course.searchBlob) : course.searchBlob != null) return false;
        if (date != null ? !date.equals(course.date) : course.date != null) return false;
        if (sortDate != null ? !sortDate.equals(course.sortDate) : course.sortDate != null) return false;
        return dateInfo != null ? dateInfo.equals(course.dateInfo) : course.dateInfo == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (institution != null ? institution.hashCode() : 0);
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
        return "Course{" +
                "id=" + id +
                ", institution=" + institution +
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
                "} " + super.toString();
    }
}
