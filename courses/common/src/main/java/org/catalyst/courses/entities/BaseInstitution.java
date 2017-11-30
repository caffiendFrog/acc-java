package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.DiscriminatorFormula;

import javax.persistence.*;

/**
 * Using one entity to represent both the list of sponsors and the institution associated with a course was not being
 * interpreted correctly by Hibernate. Work-around (or possibly the correct way) to do this is to map 2 classes to one
 * table.
 */
@Entity
@Table(name="institution")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula(
        "CASE WHEN sponsor " +
        "THEN 'SPONSOR' " +
        "ELSE 'INSTITUTION' " +
        "END "
)
public class BaseInstitution extends DetailWithAbbreviation {
    private final static Logger logger = LogManager.getLogger(BaseInstitution.class);

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name = "table-generator", pkColumnValue = "institution_id")
    @Column(name = "institution_id")
    private int id;

    @Column(name = "sponsor")
    private boolean sponsor = false;

    protected BaseInstitution() {
        // no-op constructor for hibernate, cannot be private
    }

    public BaseInstitution(String name, String abbreviation) {
        super(name, abbreviation);
//        this.sponsor = false;
    }

    public Integer getId() {
        return this.id;
    }

    public boolean isSponsor() {
        return sponsor;
    }

    public void setSponsor(boolean sponsor) {
        this.sponsor = sponsor;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        BaseInstitution that = (BaseInstitution) o;

        return id == that.id;
    }


    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//
//        BaseInstitution that = (BaseInstitution) o;
//
//        if (id != that.id) return false;
////        return sponsor == that.sponsor;
//    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
//        result = 31 * result + (sponsor ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BaseInstitution{" +
                "id=" + id +
//                ", sponsor=" + sponsor +
                "} " + super.toString();
    }
}
