package org.catalyst.courses.legacy;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is an intermediate step for converting from the legacy schema to the new schema. There should be one
 * <code>CourseMigration</code> object for each course. We do not need the course_id at this step.
 */
public class CourseMigration {
    private int legacyCourseId;
//    private int newCourseId;

    private Integer courseDetailId;
    private List<Integer> translationDetailIds = new ArrayList<>();
    private List<Integer> audienceDetailIds = new ArrayList<>();
    private List<Integer> institutionDetailIds = new ArrayList<>();
    private List<Integer> competencyDetailIds = new ArrayList<>();

    protected CourseMigration(final int legacyCourseId) {
        this.legacyCourseId = legacyCourseId;
    }

    protected CourseMigration(final int legacyCourseId, final int courseDetailId) {
        this.legacyCourseId = legacyCourseId;
        this.courseDetailId = courseDetailId;
    }

    protected void setCourseDetailId(final Integer courseDetailId){
        this.courseDetailId = courseDetailId;
    }

    protected void addTranslationDetailId(final Integer translationDetailId) {
        translationDetailIds.add(translationDetailId);
    }

    protected void addAudienceDetailId(final Integer audienceDetailId) {
        audienceDetailIds.add(audienceDetailId);
    }

    protected void addInstitutionDetailId(final Integer institutionDetailId) {
        institutionDetailIds.add(institutionDetailId);
    }

    protected void addCompetencyDetailId(final Integer competencyDetailId) {
        competencyDetailIds.add(competencyDetailId);
    }



}
