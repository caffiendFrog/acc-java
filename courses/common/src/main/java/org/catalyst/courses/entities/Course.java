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

    @OneToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "institution_id")
    private Institution institution;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<Institution> sponsors = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<Translation> translations = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<Audience> audiences = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<Competency> competencies = new HashSet<>();

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

    protected Course() {
        // no-op constructor for hibernate, cannot be private
    }

    public Course(String courseName) {
        setName(courseName);
    }

    public Integer getId() {
        return this.id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addSponsor(Institution institution) {
        // check if instituiton is valid sponsor
        sponsors.add(institution);
        institution.addCourse(this);
    }

    public void removeSponsor(Institution institution) {
        sponsors.remove(institution);
        institution.removeCourse(this);
    }

    public void addTranslation(Translation translation) {
        translations.add(translation);
        translation.addCourse(this);
    }

    public void removeTranslation(Translation translation) {
        translations.remove(translation);
        translation.removeCourse(this);
    }

    public void addAudience(Audience audience) {
        audiences.add(audience);
        audience.addCourse(this);
    }

    public void removeAudience(Audience audience) {
        audiences.remove(audience);
        audience.removeCourse(this);
    }

    public void addCompetency(Competency competency) {
        competencies.add(competency);
        competency.addCourse(this);
    }

    public void removeCompetency(Competency competency) {
        competencies.remove(competency);
        competency.removeCourse(this);
    }

    public Set<Institution> getSponsors() {
        return sponsors;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", institution=" + institution +
                ", sponsors=" + sponsors +
                ", translations=" + translations +
                ", audiences=" + audiences +
                ", competencies=" + competencies +
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
