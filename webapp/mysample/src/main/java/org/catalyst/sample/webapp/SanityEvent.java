package org.catalyst.sample.webapp;

import org.catalyst.services.BaseEntry;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table ( name = "EVENTS" )
public class SanityEvent extends BaseEntry {
//    private Long id;

    private String title;
    private Date date;

    public SanityEvent() {
        // this form used by Hibernate
    }

    public SanityEvent(String title, Date date) {
        // for application use, to create new events
        this.title = title;
        this.date = date;
    }

//    @Id
//    @GeneratedValue(generator="increment")
//    @GenericGenerator(name="increment", strategy="increment")
//    public Long getId() {
//        return id;
//    }
//
//    private void setId(Long id) {
//        this.id = id;
//    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EVENT_DATE")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
