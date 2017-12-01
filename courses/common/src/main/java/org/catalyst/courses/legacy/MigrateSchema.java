package org.catalyst.courses.legacy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.courses.entities.*;
import org.catalyst.courses.legacy.entities.*;
import org.catalyst.services.HibernateManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class assumes that the legacy database has been loaded somewhere accessible and it's properties configured
 * in the file 'legacy_hibernate.cfg.xml'. We will not be using the Hibernate Manager for simplicity. We would need to
 * factor out how to specify the hibernate configuration file for the legacy entities. This will be a nice to have future
 * improvement.
 */
public class MigrateSchema {
    private static final Logger logger = LogManager.getLogger(MigrateSchema.class);
    private final static String configFilename = "legacy_hibernate.cfg.xml";
    private final static SessionFactory legacySessionFactory = initializeSessionFactory();
    private final static HibernateManager migratedSchemaManager = HibernateManager.getInstance();

    private static Map<Integer, Course> legacyIdToCourse = new HashMap<>();
    private static Map<Integer, Competency> legacyIdToCompetency = new HashMap<>();
    private static Map<Integer, BaseInstitution> legacyIdToInstitution = new HashMap<>();
    private static Map<Integer, BaseInstitution> legacyIdToSponsor = new HashMap<>();
    private static Map<Integer, Audience> legacyIdToAudience = new HashMap<>();
    private static Map<Integer, Translation> legacyIdToTranslation = new HashMap<>();
    private static Map<Integer, CourseDetails> legacyIdToCourseDetails = new HashMap<>();

    private static SessionFactory initializeSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(configFilename)
                .build();
        try {
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            logger.debug((e.getMessage()));
            StandardServiceRegistryBuilder.destroy(registry);
        }

        return null;
    }

    public static void main (String[] args) {
        migrateAudiences();
        migrateInstitutions();
        migrateSponsors();
        migrateKeywords();
        migrateTranslations();
        migrateCourses();

        migratedSchemaManager.saveOrUpdate(legacyIdToAudience.values().stream().collect(Collectors.toList()));
        migratedSchemaManager.saveOrUpdate(legacyIdToCompetency.values().stream().collect(Collectors.toList()));
        migratedSchemaManager.saveOrUpdate(legacyIdToTranslation.values().stream().collect(Collectors.toList()));
        migratedSchemaManager.saveOrUpdate(legacyIdToInstitution.values().stream().collect(Collectors.toList()));
        migratedSchemaManager.saveOrUpdate(legacyIdToSponsor.values().stream().collect(Collectors.toList()));
        migratedSchemaManager.saveOrUpdate(legacyIdToCourse.values().stream().collect(Collectors.toList()));

        // clean up the institutions table AFTER migration, will make handling the legacy keys and things easier (hopefully)
//        new
//        for (Integer legacyId : legacyIdToTranslation.keySet()) {
//            System.out.println(legacyId + " --> " + legacyIdToTranslation.get(legacyId).getId() + " : " + legacyIdToTranslation.get(legacyId).getName());
//        }


        migrateRelationships();
        legacySessionFactory.close();

        migratedSchemaManager.resetSessionFactory();
//    migratedSchemaManager.
        System.out.println(migratedSchemaManager.getAllEntities(Course.class, true));

//        dedupInstitutions();

        migratedSchemaManager.shutdown();
    }

    private static void dedupInstitutions() {
        List<BaseInstitution> keepInstitutions = new ArrayList<>();
        List<BaseInstitution> removeInstitutions = new ArrayList<>();

        Map<String, List<BaseInstitution>> abbreviationsToInstitutions = migratedSchemaManager.getAllEntities(BaseInstitution.class).
                stream().collect(Collectors.groupingBy(BaseInstitution::getAbbreviation));
        Map<Integer, Integer> institutionIdToSponsorId = new HashMap<>();
        // Assumption is that if there are two entries for an insitution, then it is a sponsor
        for(List<BaseInstitution> baseInstitutions : abbreviationsToInstitutions.values().stream().collect(Collectors.toList())) {
            if (baseInstitutions.size() > 1) {
                Integer sponsorId = 0;
                Integer institutionId = 0;
                for(BaseInstitution bi : baseInstitutions) {
                    if (bi.isSponsor()) {
                        keepInstitutions.add(bi);
                        sponsorId = bi.getId();
                    } else {
                        removeInstitutions.add(bi);
                        institutionId = bi.getId();
                    }
                }
                institutionIdToSponsorId.put(institutionId, sponsorId);
            } else {
                keepInstitutions.add(baseInstitutions.get(0));
            }
        }

        Map<Institution, List<Course>> institutionToCourse = migratedSchemaManager.getAllEntities(Course.class).
                stream().collect(Collectors.groupingBy(Course::getInstitution));

        for(Map.Entry<Institution, List<Course>> entry : institutionToCourse.entrySet()) {
            if (removeInstitutions.contains(entry.getKey())) {
                for(Course course : entry.getValue()) {
                    course.setInstitution(entry.getKey());
                }
            }
        }

        for(BaseInstitution institution : removeInstitutions) {
            migratedSchemaManager.removeEntity(BaseInstitution.class, institution.getId());
        }

        migratedSchemaManager.saveOrUpdate(institutionToCourse.values().stream().collect(Collectors.toList()));

    }

    private static void migrateRelationships() {


        for (Map.Entry<Integer, Course> entry : legacyIdToCourse.entrySet()) {
            CourseDetails courseDetails = new CourseDetails();
            courseDetails.addCourse(entry.getValue());

        }

        List<LegacyCourseAudience> legacyCourseAudiences = getListFromDB(LegacyCourseAudience.class);
        List<LegacyCourseKeyword> legacyCourseKeywords = getListFromDB(LegacyCourseKeyword.class);
        List<LegacyCourseSponsor> legacyCourseSponsors = getListFromDB(LegacyCourseSponsor.class);
        List<LegacyCourseTranslation> legacyCourseTranslations = getListFromDB(LegacyCourseTranslation.class);

        // Create a map of legacy course ids to a list of legacy course id to legacy translation/keyword/etc ids
        Map<Integer, List<LegacyCourseTranslation>> legacyCourseIdsToCourseTranslations = legacyCourseTranslations.stream().collect(
                Collectors.groupingBy(LegacyCourseTranslation::getCourseId));
        Map<Integer, List<LegacyCourseAudience>> legacyCourseIdsToCourseAudiences = legacyCourseAudiences.stream().collect(
                Collectors.groupingBy(LegacyCourseAudience::getCourseId));
        Map<Integer, List<LegacyCourseKeyword>> legacyCourseIdsToCourseKeywords = legacyCourseKeywords.stream().collect(
                Collectors.groupingBy(LegacyCourseKeyword::getCourseId));
        Map<Integer, List<LegacyCourseSponsor>> legacyCourseIdsToCourseSponsors = legacyCourseSponsors.stream().collect(
                Collectors.groupingBy(LegacyCourseSponsor::getCourseId));

        // Iterate over maps and add associations. Create course if it doesn't exist


        for (Map.Entry<Integer, List<LegacyCourseTranslation>> entry : legacyCourseIdsToCourseTranslations.entrySet()) {
            // For readability's sake, we know we are going to be working with legacy objects, rename the key & value
            Integer courseId = entry.getKey();
            List<LegacyCourseTranslation> courseTranslations = entry.getValue();
            CourseDetails migratedCourseDetails = getCourseDetails(courseId);

            // add the assocations
            for(LegacyCourseTranslation translation : courseTranslations) {
                Integer translationId = translation.getTranslationId();
                migratedCourseDetails.addTranslation(legacyIdToTranslation.get(translationId));
            }

            // update the map
            legacyIdToCourseDetails.put(courseId, migratedCourseDetails);
        }

        for (Map.Entry<Integer, List<LegacyCourseAudience>> entry : legacyCourseIdsToCourseAudiences.entrySet()) {
            // For readability's sake, we know we are going to be working with legacy objects, rename the key & value
            Integer courseId = entry.getKey();
            List<LegacyCourseAudience> courseAudiences = entry.getValue();
            CourseDetails migratedCourseDetails = getCourseDetails(courseId);

            // add the associations
            for(LegacyCourseAudience audience : courseAudiences) {
                Integer audienceId = audience.getAudienceId();
                migratedCourseDetails.addAudience(legacyIdToAudience.get(audienceId));
            }

            // update the map
            legacyIdToCourseDetails.put(courseId, migratedCourseDetails);
        }

        for (Map.Entry<Integer, List<LegacyCourseKeyword>> entry : legacyCourseIdsToCourseKeywords.entrySet()) {
            Integer courseId = entry.getKey();
            List<LegacyCourseKeyword> courseKeywords = entry.getValue();
            CourseDetails migratedCourseDetails = getCourseDetails(courseId);

            for(LegacyCourseKeyword keyword : courseKeywords) {
                Integer keywordId = keyword.getKeywordId();
                migratedCourseDetails.addCompetency(legacyIdToCompetency.get(keywordId));
            }

            legacyIdToCourseDetails.put(courseId, migratedCourseDetails);
        }

        for (Map.Entry<Integer, List<LegacyCourseSponsor>> entry : legacyCourseIdsToCourseSponsors.entrySet()) {
            Integer courseId = entry.getKey();
            List<LegacyCourseSponsor> courseSponsors = entry.getValue();
            CourseDetails migratedCourseDetails = getCourseDetails(courseId);

            for (LegacyCourseSponsor sponsor : courseSponsors) {
                Integer sponsorId = sponsor.getSponsorId();
                migratedCourseDetails.addSponsor((Sponsor)legacyIdToSponsor.get(sponsorId));
            }

            legacyIdToCourseDetails.put(courseId, migratedCourseDetails);
        }

        for (Map.Entry<Integer, Course> entry : legacyIdToCourse.entrySet()) {
            Integer legacyCourseId = entry.getKey();
            Course migratedCourse = entry.getValue();
            CourseDetails migratedCourseDetails = getCourseDetails(legacyCourseId);
            migratedCourseDetails.addCourse(migratedCourse);
            legacyIdToCourseDetails.put(legacyCourseId, migratedCourseDetails);
        }

        HibernateManager.getInstance().saveOrUpdate(legacyIdToCourseDetails.values().stream().collect(Collectors.toList()));
    }

//    private static <T> void migrateAssociations(Map<Integer, List<T>> courseIdToAssociations) {
//        for (Map.Entry<Integer, List<T>> entry : courseIdToAssociations.entrySet()) {
//            // For readability's sake, we know we are going to be working with legacy objects, rename the key & value
//            Integer courseId = entry.getKey();
//            List<T> courseAssociations = entry.getValue();
//            CourseDetails migratedCourseDetails = getCourseDetails(courseId);
//
//            // add the associations
//            for(T association : courseAssociations ) {
////                Integer legacyAssociationId = association.get
//            }
//        }
//    }
    /**
     * Factored into it's own method for readability
     * @param legacyCourseId
     * @return
     */
    private static CourseDetails getCourseDetails(Integer legacyCourseId) {
        // Is there already a course detail object for this course?
        return legacyIdToCourseDetails.containsKey(legacyCourseId) ?
                legacyIdToCourseDetails.get(legacyCourseId) : new CourseDetails();
    }

    private static void migrateCourses() {
        List<LegacyCourse> legacyCourses = getListFromDB(LegacyCourse.class);
        Map<Integer, String> legacyIdToSearchBlob = getListFromDB(LegacyCourseSearch.class).stream()
                .collect(Collectors.toMap(LegacyCourseSearch::getCourseId, LegacyCourseSearch::getSearchblob));
        for (LegacyCourse lc : legacyCourses) {
            Course course = new Course(lc.getName());
            if (lc.isArchived()) { course.archive(); }
            course.setWebcast(lc.isWebcast());
            course.setContactEmail(lc.getContactEmail());
            course.setContactUrl(lc.getContactUrl());
            course.setDescription(lc.getDescription());
            course.setHours(lc.getHours());
            course.setMaxEnroll(lc.getMaxEnroll());
            course.setSearchBlob(legacyIdToSearchBlob.get(lc.getId()));
            course.setDate(lc.getCourseDate());
            course.setSortDate(lc.getSortDate());
            course.setDateInfo(lc.getDateInfo());

            course.setInstitution((Institution) legacyIdToInstitution.get(lc.getInstitutionId()));

            legacyIdToCourse.put(lc.getId(), course);
        }
    }

    private static void migrateKeywords() {
        List<LegacyKeyword> legacyKeywords = getListFromDB(LegacyKeyword.class);
        for (LegacyKeyword lk : legacyKeywords) {
            Competency competency = new Competency(lk.getName());
            competency.setNote(lk.getNote());
            legacyIdToCompetency.put(lk.getId(), competency);
        }
    }

    private static void migrateAudiences() {
        List<LegacyAudience> legacyAudiences = getListFromDB(LegacyAudience.class);
        for (LegacyAudience la : legacyAudiences) {
            Audience audience = new Audience(la.getName());
            audience.setNote(la.getNote());
            legacyIdToAudience.put(la.getId(), audience);
        }
    }

    private static void migrateTranslations() {
        List<LegacyTranslation> legacyTranslations = getListFromDB(LegacyTranslation.class);
        for (LegacyTranslation lt : legacyTranslations) {
            Translation translation = new Translation(lt.getName(), lt.getAbbreviation());
            translation.setNote(lt.getNote());
            legacyIdToTranslation.put(lt.getId(), translation);
        }
    }

    private static void migrateInstitutions() {
        List<LegacyInstitution> legacyInstitutions = getListFromDB(LegacyInstitution.class);

        // first migrate the institutions
        for (LegacyInstitution li : legacyInstitutions) {
            Institution institution = new Institution(li.getName(), li.getAbbreviation());
            institution.setNote(li.getNote());
            legacyIdToInstitution.put(li.getId(), institution);
        }
    }

    private static void migrateSponsors() {
        List<LegacySponsor> legacySponsors = getListFromDB(LegacySponsor.class);

        for (LegacySponsor ls : legacySponsors) {
            // There is an inconsistency with the abbreviation of Childrens' Hospital. Need to manually normalize here
            String abbreviation = ls.getAbbreviation().equals("CHB") ? "Childrens" : ls.getAbbreviation();
            Sponsor sponsor = new Sponsor(ls.getName(), abbreviation);
            sponsor.setNote(ls.getNote());
            legacyIdToSponsor.put(ls.getId(), sponsor);
        }
    }

    private static <T> List<T> getListFromDB(final Class<T> clazz) {
        Session session = legacySessionFactory.openSession();
        session.beginTransaction();
        List<T> results = session.createQuery("from " + clazz.getSimpleName()).list();
        session.getTransaction().commit();
        session.close();
        return results;
    }



    /**
     * Code morgue
     */
    //    private static List<LegacyCourse> legacyCourses = new ArrayList<>();
//    private static List<LegacyAudience> legacyAudiences = new ArrayList<>();
//    private static List<LegacyCourseAudience> legacyCourseAudiences = new ArrayList<>();
//    private static List<LegacyCourseKeyword> legacyCourseKeywords = new ArrayList<>();
//    private static List<LegacyCourseSearch> legacyCourseSearches = new ArrayList<>();
//    private static List<LegacyCourseSponsor> legacyCourseSponsors = new ArrayList<>();
//    private static List<LegacyCourseTranslation> legacyCourseTranslations = new ArrayList<>();
//    private static List<LegacyInstitution> legacyInstitutions = new ArrayList<>();
//    private static List<LegacyKeyword> legacyKeywords = new ArrayList<>();
//    private static List<LegacyTranslation> legacyTranslations = new ArrayList<>();

//    private static Course buildCourseDetail(LegacyCourse lc) {
//        int legacyCourseId = lc.getId();
//        Course course = new Course(lc.getName());
////        courseDetail.setNote(lc.getNote());
////        if (lc.isArchived()) {
////            courseDetail.archive();
////        } else {
////            courseDetail.unArchive();
////        }
////        courseDetail.setContactEmail(lc.getContactEmail());
////        courseDetail.setContactUrl(lc.getContactUrl());
////        courseDetail.setDate(lc.getCourseDate());
////        courseDetail.setDateInfo(lc.getDateInfo());
////        courseDetail.setDescription(lc.getDescription());
////        courseDetail.setHours(lc.getHours());
////        courseDetail.setMaxEnroll(lc.getMaxEnroll());
////        courseDetail.setSearchBlob(getSearchBlob(legacyCourseId));
////        courseDetail.setSortDate(lc.getSortDate());
////        courseDetail.setWebcast(lc.isWebcast());
//        return course;
//    }
//
//    private static String getSearchBlob(final int legacyCourseId) {
//        Session session = legacySessionFactory.openSession();
//        session.beginTransaction();
//        LegacyCourseSearch courseSearch = session.get(LegacyCourseSearch.class, legacyCourseId);
//        return courseSearch.getSearchblob();
//    }

//        Map<Integer, List<Integer>> legacyCoursesToLegacyTranslations = legacyCourseTranslations.stream().collect(
//                Collectors.toMap(LegacyCourseTranslation::getId, LegacyCourseTranslation::getTranslationId)
//        )

//        Collectors.groupingBy(String::length, HashMap::new, Collectors.toCollection(ArrayList::new))
//    );

//        for (Course course : legacyIdToCourse.values()) {
//
//
//        }


//        Map<Integer, List<Course>> legacyCourseIdToNewCourses = new HashMap<>();
//        for (Integer legacyId : legacyIdToCourse.keySet()) {
//            List<Course> migratedCours = new ArrayList<>();
////            Course course = new Course()
////            if (legacyCoursesToCourseTranslations.containsKey(legacyId)) {
////                migratedCours.add(new Course(legacyIdToCourse.get(legacyId).getId());
////            }
//            int numTranslations = legacyCoursesToCourseTranslations.get(legacyId).size();
//            int numAudiences = legacyCoursesToCourseAudiences.get(legacyId).size();
//            int numKeywords = legacyCoursesToCourseKeywords.get(legacyId).size();
//            int numSponsors = legacyCoursesToCourseSponsors.get(legacyId).size();
//
//        }

    // now migrate the sponsors


//        // now update institutions in the legacyIdToInstutition Map with a Sponsor object
//        List<BaseInstitution> migratedInstitutions = legacyIdToInstitution.values().stream().collect(Collectors.toList());
//        for (BaseInstitution institution : migratedInstitutions) {
//            BaseInstitution mergedInstitution = abbreviationToInstitutionDetails.get(institution.getAbbreviation());
//
//        }

//        tempLegacyIdToInstitution.forEach((key,value) ->
//                legacyIdToInstitution.put(key, abbreviationToInstitutionDetails.get(value.getAbbreviation())));
//        tempLegacyIdToSponsor.forEach((key,value) ->
//                legacyIdToInstitution.put(key, abbreviationToInstitutionDetails.get(value.getAbbreviation())));

//        for (LegacyInstitution li : legacyInstitutions) {
//            BaseInstitution institutionDetail = new BaseInstitution(li.getName(), li.getAbbreviation(), li.getNote());
//            abbreviationToInstitutionDetails.put(li.getAbbreviation(), institutionDetail);
//            legacyIdToInstitution.put(li.getId(), institutionDetail);
//        }
//
//        // now loop over the sponsors and update the institution detail's sponsor field
//        for (LegacySponsor ls : legacySponsors) {
//            // There is an inconsistency with the abbreviation of Childrens' Hospital. Need to manually normalize here
//            String abbreviation = ls.getAbbreviation().equals("CHB") ? "Childrens" : ls.getAbbreviation();
//            BaseInstitution institutionDetail = abbreviationToInstitutionDetails.get(abbreviation);
//            institutionDetail.setSponsor(true);
//            migratedSchemaManager.saveOrUpdate(institutionDetail);
//            legacySponsorIdToInstitutionDetail.put(ls.getId(), institutionDetail);
//        }



    // SKC_NOTE: Trying to be clever and factor out a generic function to do the migrating, but can't figure the
//    incantation for the class of detail
//
//    private static <LDI extends LegacyDetailInterface, D extends Detail> LDI migrateEntity(Class<LDI> legacyDetail, Supplier<D> detail) {
//        List<LDI> legacyEntities = getListFromDB(legacyDetail);
//        for (LDI legacyEntity : legacyEntities ) {
//            detail = new (legacyEntity.getName(), legacyEntity.getNote());
//        }
//        return null;
//    }


//    private static void loadLegacyRelationships() {
//
//    }

}
