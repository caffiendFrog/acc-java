package org.catalyst.courses.entities.old;

public interface AbstractDetail {
    void deactivate();
    void activate();

    boolean isActive();

    String getName();

    void setName(String name);
    String getNote();

    void setNote(String note);

    String getAbbreviation();

    void setAbbreviation(String abbreviation);

    Integer getId();
}
