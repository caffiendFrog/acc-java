package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Extends the details that are common to all of the tables and adds an abbreviation column
 */
@MappedSuperclass
public abstract class DetailWithAbbreviation extends Detail {
    private final static Logger logger = LogManager.getLogger(DetailWithAbbreviation.class);

    @Column(name = "abbreviation")
    private String abbreviation;

    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * An abbreviation is required
     * @param abbreviation
     */
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
        return "DetailWithAbbreviation{" +
                "abbreviation='" + abbreviation + '\'' +
                "} " + super.toString();
    }
}
