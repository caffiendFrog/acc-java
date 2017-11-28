package org.catalyst.courses.entities.old;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="translationDetail")
public class TranslationDetail extends  Detail {
    private static Logger logger = LogManager.getLogger(TranslationDetail.class);

    @AttributeOverride(name="translationDetail_id", column=@Column(name="detail_id"))
    private int id;

    @Override
    public Integer getId() {
        return id;
    }

    protected TranslationDetail() {
        // no-op constructor for hibernate
    }

    public TranslationDetail(String translationName, String abbreviation) {
        super(translationName);
        setAbbreviation(abbreviation);
    }

    @Override
    public void setAbbreviation(String abbreviation) {
        if (abbreviation == null) {
            throw new NullPointerException("Cannot set entity name to null.");
        }

        if (abbreviation.isEmpty()) {
            logger.warn("Trying to change entity name from ["+this.abbreviation+"] to ["+abbreviation+"]. This is likely to cause" +
                    " errors downstream.");
        }

        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return "TranslationDetail{" +
                "id=" + id +
                ", active=" + active +
                ", name='" + name + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    //    public TranslationDetail() {
//        // no-arg constructor for hibernate
//    }
//
//    /**
//     * Creates a new translation and sets it to active
//     * @param translationName
//     */
//    public TranslationDetail(String translationName, String abbreviation) {
//        super(translationName);
//        this.abbreviation = abbreviation;
//    }
//
//    // TODO: cclean up the detail contsrtuctor stuff and things
//    public TranslationDetail(String translationName, String abbreviation, String note) {
//        super(translationName, note);
//        this.abbreviation = abbreviation;
//    }
//
//    /**
//     * Abbreviations are required, override to check for null and empty string
//     * @param abbreviation
//     */
//    public void setAbbreviation(String abbreviation) {
//        if (abbreviation == null) {
//            throw new NullPointerException("Cannot set entity name to null.");
//        }
//
//        if (abbreviation.isEmpty()) {
//            logger.warn("Trying to change entity name from ["+this.abbreviation+"] to ["+abbreviation+"]. This is likely to cause" +
//                    " errors downstream.");
//        }
//
//        this.abbreviation = abbreviation;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//
//        TranslationDetail that = (TranslationDetail) o;
//
//        return abbreviation.equals(that.abbreviation);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = super.hashCode();
//        result = 31 * result + abbreviation.hashCode();
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        return "TranslationDetail{" +
//                "id='" + id + '\'' +
//                ", abbreviation='" + abbreviation + '\'' +
//                ", active=" + active +
//                ", name='" + name + '\'' +
//                ", note='" + note + '\'' +
//                '}';
//    }
}
