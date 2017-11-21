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
    private final static HibernateManager newSchemaManager = HibernateManager.getInstance();
//    private static List<LegacyCourse> legacyCourses = new ArrayList<>();
//    private static List<LegacyAudience> legacyAudiences = new ArrayList<>();
    private static List<LegacyCourseAudience> legacyCourseAudiences = new ArrayList<>();
    private static List<LegacyCourseKeyword> legacyCourseKeywords = new ArrayList<>();
//    private static List<LegacyCourseSearch> legacyCourseSearches = new ArrayList<>();
    private static List<LegacyCourseSponsor> legacyCourseSponsors = new ArrayList<>();
    private static List<LegacyCourseTranslation> legacyCourseTranslations = new ArrayList<>();
    private static List<LegacyInstitution> legacyInstitutions = new ArrayList<>();
//    private static List<LegacyKeyword> legacyKeywords = new ArrayList<>();
//    private static List<LegacyTranslation> legacyTranslations = new ArrayList<>();

    private static Map<Integer, CourseDetail> legacyIdToCourseDetail = new HashMap<>();
    private static Map<Integer, CompetencyDetail> legacyIdToCompetencyDetail = new HashMap<>();
    private static Map<Integer, InstitutionDetail> legacyIdToInstitutionDetail = new HashMap<>();
    private static Map<Integer, InstitutionDetail> legacySponsorIdToInstitutionDetail = new HashMap<>();
    private static Map<Integer, AudienceDetail> legacyIdToAudienceDetail = new HashMap<>();
    private static Map<Integer, TranslationDetail> legacyIdToTranslationDetail = new HashMap<>();

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
        System.out.println("what");
//        loadLegacyEntities();
        migrateAudiences();
        migrateInstitutions();
        migrateKeywords();
        migrateTranslations();
        migrateCourses();
        for (Integer legacyId : legacyIdToTranslationDetail.keySet()) {
            System.out.println(legacyId + " --> " + legacyIdToTranslationDetail.get(legacyId).getId() + " : " + legacyIdToTranslationDetail.get(legacyId).getName());
        }
        legacySessionFactory.close();
        newSchemaManager.shutdown();
    }

    private static void loadLegacyRelationships() {
        legacyCourseAudiences = getListFromDB(LegacyCourseAudience.class);
        legacyCourseKeywords = getListFromDB(LegacyCourseKeyword.class);
        legacyCourseSponsors = getListFromDB(LegacyCourseSponsor.class);
        legacyCourseTranslations = getListFromDB(LegacyCourseTranslation.class);
    }

    private static void migrateRelationships() {
//        Map<Integer, List<Integer>> legacyCoursesToLegacyTranslations = legacyCourseTranslations.stream().collect(
//                Collectors.toMap(LegacyCourseTranslation::getId, LegacyCourseTranslation::getTranslationId)
//        )

//        Collectors.groupingBy(String::length, HashMap::new, Collectors.toCollection(ArrayList::new))
//    );
        Map<Integer, List<LegacyCourseTranslation>> legacyCoursesToCourseTranslations = legacyCourseTranslations.stream().collect(
                Collectors.groupingBy(LegacyCourseTranslation::getCourseId));
        Map<Integer, List<LegacyCourseAudience>> legacyCoursesToCourseAudiences = legacyCourseAudiences.stream().collect(
                Collectors.groupingBy(LegacyCourseAudience::getCourseId));
        Map<Integer, List<LegacyCourseKeyword>> legacyCoursesToCourseKeywords = legacyCourseKeywords.stream().collect(
                Collectors.groupingBy(LegacyCourseKeyword::getCourseId));
        Map<Integer, List<LegacyCourseSponsor>> legacyCoursesToCourseSponsors = legacyCourseSponsors.stream().collect(
                Collectors.groupingBy(LegacyCourseSponsor::getCourseId));

        Map<Integer, List<Course>> legacyCourseIdToNewCourses = new HashMap<>();
        for (Integer legacyId : legacyIdToCourseDetail.keySet()) {
            List<Course> migratedCourses = new ArrayList<>();
//            Course course = new Course()
//            if (legacyCoursesToCourseTranslations.containsKey(legacyId)) {
//                migratedCourses.add(new Course(legacyIdToCourseDetail.get(legacyId).getId());
//            }
            int numTranslations = legacyCoursesToCourseTranslations.get(legacyId).size();
            int numAudiences = legacyCoursesToCourseAudiences.get(legacyId).size();
            int numKeywords = legacyCoursesToCourseKeywords.get(legacyId).size();
            int numSponsors = legacyCoursesToCourseSponsors.get(legacyId).size();
        }
    }

    private static void migrateCourses() {
        List<LegacyCourse> legacyCourses = getListFromDB(LegacyCourse.class);
        for (LegacyCourse lc : legacyCourses) {
            CourseDetail courseDetail = buildCourseDetail(lc);
            newSchemaManager.saveOrUpdate(courseDetail);
            legacyIdToCourseDetail.put(lc.getId(), courseDetail);
        }
    }

    private static void migrateKeywords() {
        List<LegacyKeyword> legacyKeywords = getListFromDB(LegacyKeyword.class);
        for (LegacyKeyword lk : legacyKeywords) {
            CompetencyDetail competencyDetail = new CompetencyDetail(lk.getName(), lk.getNote());
            newSchemaManager.saveOrUpdate(competencyDetail);
            legacyIdToCompetencyDetail.put(lk.getId(), competencyDetail);
        }
    }

    private static void migrateAudiences() {
        List<LegacyAudience> legacyAudiences = getListFromDB(LegacyAudience.class);
        for (LegacyAudience la : legacyAudiences) {
            AudienceDetail audienceDetail = new AudienceDetail(la.getName(), la.getNote());
            newSchemaManager.saveOrUpdate(audienceDetail);
            legacyIdToAudienceDetail.put(la.getId(), audienceDetail);
        }
    }

    private static void migrateTranslations() {
        List<LegacyTranslation> legacyTranslations = getListFromDB(LegacyTranslation.class);
        for (LegacyTranslation lt : legacyTranslations) {
            TranslationDetail translationDetail = new TranslationDetail(lt.getName(), lt.getAbbreviation(), lt.getNote());
            newSchemaManager.saveOrUpdate(translationDetail);
            legacyIdToTranslationDetail.put(lt.getId(), translationDetail);
        }
    }

    private static void migrateInstitutions() {
//        List<LegacySponsor> legacySponsors = getListFromDB(LegacySponsor.class);
//        List<LegacyInstitution> legacyInstitutions = getListFromDB(LegacyInstitution.class);
//        Map<String, InstitutionDetail> abbreviationToInstitutionDetails = new HashMap<>();
//
//        // first migrate the institutions
//        for (LegacyInstitution li : legacyInstitutions) {
//            InstitutionDetail institutionDetail = new InstitutionDetail(li.getName(), li.getAbbreviation(), li.getNote());
//            abbreviationToInstitutionDetails.put(li.getAbbreviation(), institutionDetail);
//            legacyIdToInstitutionDetail.put(li.getId(), institutionDetail);
//        }
//
//        // now loop over the sponsors and update the institution detail's sponsor field
//        for (LegacySponsor ls : legacySponsors) {
//            // There is an inconsistency with the abbreviation of Childrens' Hospital. Need to manually normalize here
//            String abbreviation = ls.getAbbreviation().equals("CHB") ? "Childrens" : ls.getAbbreviation();
//            InstitutionDetail institutionDetail = abbreviationToInstitutionDetails.get(abbreviation);
//            institutionDetail.setSponsor(true);
//            newSchemaManager.saveOrUpdate(institutionDetail);
//            legacySponsorIdToInstitutionDetail.put(ls.getId(), institutionDetail);
//        }
    }

    private static CourseDetail buildCourseDetail(LegacyCourse lc) {
        int legacyCourseId = lc.getId();
        CourseDetail courseDetail = new CourseDetail(lc.getName());
//        courseDetail.setNote(lc.getNote());
        if (lc.isArchived()) {
            courseDetail.archive();
        } else {
            courseDetail.unArchive();
        }
        courseDetail.setContactEmail(lc.getContactEmail());
        courseDetail.setContactUrl(lc.getContactUrl());
        courseDetail.setDate(lc.getCourseDate());
        courseDetail.setDateInfo(lc.getDateInfo());
        courseDetail.setDescription(lc.getDescription());
        courseDetail.setHours(lc.getHours());
        courseDetail.setMaxEnroll(lc.getMaxEnroll());
        courseDetail.setSearchBlob(getSearchBlob(legacyCourseId));
        courseDetail.setSortDate(lc.getSortDate());
        courseDetail.setWebcast(lc.isWebcast());
        return courseDetail;
    }

    private static String getSearchBlob(final int legacyCourseId) {
        Session session = legacySessionFactory.openSession();
        session.beginTransaction();
        LegacyCourseSearch courseSearch = session.get(LegacyCourseSearch.class, legacyCourseId);
        return courseSearch.getSearchblob();
    }

    private static <T> List<T> getListFromDB(final Class<T> clazz) {
        Session session = legacySessionFactory.openSession();
        session.beginTransaction();
        List<T> results = session.createQuery("from " + clazz.getSimpleName()).list();
        session.getTransaction().commit();
        session.close();
        return results;
    }

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


}
