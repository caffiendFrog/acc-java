package org.catalyst.courses.entities;

public interface Detail_delete {
    void deactivate();
    void activate();
    boolean isActive();
    Integer getId();
    String getName();
    void setName(String name);
    String getNote();
    void setNote(String note);
    String getAbbreviation();
    void setAbbreviation(String abbreviation);
}

//import com.mysql.jdbc.log.LogFactory;
//import org.apache.logging.log4j.Logger;
//
//public abstract class Detail {
//    private final static Logger logger = LogFactory.getLogger(Detail.class);
//    private int id;
//    private boolean active;
//    private String name;
//    private String note;
//    private String abbreviation;
//
//    protected void deactivate() {
//        this.active = false;
//    }
//
//    protected void activate() {
//        this.active = true;
//    }
//    protected boolean isActive() {
//        return this.active;
//    }
//    protected Integer getId() {
//        return this.id;
//    }
//    protected String getName() {
//        return this.name;
//    }
//    protected void setName(String name) {
//        this.name = name;
//    }
//    protected String getNote() {
//        return this.note;
//    }
//    protected void setNote(String note) {
//        this.note = note;
//    }
//    protected String getAbbreviation() {
//        return this.abbreviation;
//    }
//    protected void setAbbreviation(String abbreviation) {
//        logger.debug("YO WASABI?");
//        this.abbreviation = abbreviation;
//    }
//}
