package org.catalyst.courses.legacy;

import org.catalyst.courses.entities.InstitutionDetail;

/**
 * A POJO to assist with migrating the institutions & sponsors table from the legacy database to the new schema
 * which combines these redundant tables
 */
public class InstitutionMigration {
    private int legacyInstitutionId;
    private int legacySponsorId;
    private InstitutionDetail institutionDetail;

    public InstitutionMigration(int legacyId, InstitutionDetail detail) {
        this.legacyInstitutionId = legacyId;
        this.institutionDetail = detail;
    }

    public void setLegacySponsorId(int legacySponsorId) {
        this.legacySponsorId = legacySponsorId;
    }

    public int getLegacyInstitutionId() {
        return legacyInstitutionId;
    }

    public int getLegacySponsorId() {
        return legacySponsorId;
    }

    public InstitutionDetail getInstitutionDetail() {
        return institutionDetail;
    }

    public void setIsSponsor(boolean isSponsor) {
        institutionDetail.setSponsor(isSponsor);
    }
}
