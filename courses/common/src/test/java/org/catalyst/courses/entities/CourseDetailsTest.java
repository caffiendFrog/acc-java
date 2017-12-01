package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.services.HibernateManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.catalyst.courses.entities.AudienceTest.*;
import static org.catalyst.courses.entities.CompetencyTest.*;
import static org.catalyst.courses.entities.CourseTest.*;
import static org.catalyst.courses.entities.InstitutionTest.*;
import static org.catalyst.courses.entities.SponsorTest.getIdToSponsors;
import static org.catalyst.courses.entities.TranslationTest.*;
import static org.junit.Assert.*;

public class CourseDetailsTest {
    private final static Logger logger = LogManager.getLogger(CourseDetailsTest.class);

    private final static CourseDetails caffeineCourseDetails = new CourseDetails();
    private final static CourseDetails rohtoCourseDetails = new CourseDetails();

    private final static Course caffeineCourse = new Course(caffeineCourseName);
    private final static Course rohtoCourse = new Course(rohtoCourseName);
    private final static Institution bmc = new Institution(bmcName, bmcAbbreviation);
    private final static Sponsor hkuSponsor = new Sponsor(hkuName, hkuAbbreviation);
    private final static Translation t1 = new Translation(t1Name, t1Abbreviation);
    private final static Translation t2 = new Translation(t2Name, t2Abbreviation);
    private final static Competency bio = new Competency(bioName);
    private final static Competency chem = new Competency(chemName);
    private final static Audience faculty = new Audience(facultyName);
    private final static Audience postDoc = new Audience(postDocName);

    @BeforeClass
    public static void beforeClass() {
        HibernateManager.getInstance().resetSessionFactory();
    }

    @Before
    public void setUp() {
        clearCourseDetails(caffeineCourseDetails);
        clearCourseDetails(rohtoCourseDetails);
        bmc.setNote(bmcNote);
        t2.setNote(t2Note);
        faculty.setNote(facultyNote);
        bio.setNote(bioNote);
        chem.setNote(chemNote);
        
        setOptionalFields(caffeineCourse, rohtoCourse, bmc);
    }

    private void clearCourseDetails(CourseDetails courseDetails) {
        for(Audience audience : courseDetails.audiences) {
            audience.courseDetails.clear();
        }
        courseDetails.audiences.clear();
        for(Competency competency : courseDetails.competencies) {
            competency.courseDetails.clear();
        }
        courseDetails.competencies.clear();
        for(Course course : courseDetails.courses) {
            course.courseDetails.clear();
        }
        courseDetails.courses.clear();
        for(Sponsor sponsor : courseDetails.sponsors) {
            sponsor.courseDetails.clear();
        }
        courseDetails.sponsors.clear();
        for(Translation translation : courseDetails.translations) {
            translation.courseDetails.clear();
        }
        courseDetails.translations.clear();
    }
    
    @Test
    public void testBasicCourseDetails() {
        // sponsor
        assertTrue(rohtoCourseDetails.addSponsor(hkuSponsor));
        assertEquals(hkuSponsor, rohtoCourseDetails.sponsors.toArray()[0]);
        assertEquals(rohtoCourseDetails, hkuSponsor.courseDetails.toArray()[0]);
        assertTrue(rohtoCourseDetails.removeSponsor(hkuSponsor));
        assertTrue(rohtoCourseDetails.sponsors.isEmpty());
        assertTrue(hkuSponsor.courseDetails.isEmpty());

        // translation
        assertTrue(caffeineCourseDetails.addTranslation(t1));
        assertEquals(t1, caffeineCourseDetails.translations.toArray()[0]);
        assertEquals(caffeineCourseDetails, t1.courseDetails.toArray()[0]);
        assertTrue(caffeineCourseDetails.removeTranslation(t1));
        assertTrue(caffeineCourseDetails.sponsors.isEmpty());
        assertTrue(t1.courseDetails.isEmpty());

        // audience
        assertTrue(rohtoCourseDetails.addAudience(postDoc));
        assertEquals(postDoc, rohtoCourseDetails.audiences.toArray()[0]);
        assertEquals(rohtoCourseDetails, postDoc.courseDetails.toArray()[0]);
        assertTrue(rohtoCourseDetails.removeAudience(postDoc));
        assertTrue(rohtoCourseDetails.audiences.isEmpty());
        assertTrue(postDoc.courseDetails.isEmpty());

        // competency
        assertTrue(caffeineCourseDetails.addCompetency(bio));
        assertEquals(bio, caffeineCourseDetails.competencies.toArray()[0]);
        assertEquals(caffeineCourseDetails, bio.courseDetails.toArray()[0]);
        assertTrue(caffeineCourseDetails.removeCompetency(bio));
        assertTrue(caffeineCourseDetails.competencies.isEmpty());
        assertTrue(bio.courseDetails.isEmpty());
        
        // quick check of other fields to make sure no conflicts
        verifyOptionalFields(caffeineCourse, rohtoCourse, bmc);
        assertEquals(caffeineDate, caffeineCourse.getDate());
        assertEquals(caffeineSortDate, caffeineCourse.getSortDate());
    }

    @Test
    public void testCRUD() {
        rohtoCourseDetails.addCourse(rohtoCourse);
        rohtoCourseDetails.addAudience(postDoc);
        rohtoCourseDetails.addAudience(faculty);
        rohtoCourseDetails.addSponsor(hkuSponsor);
        rohtoCourseDetails.addTranslation(t1);
        rohtoCourseDetails.addTranslation(t2);
        rohtoCourseDetails.addCompetency(bio);

        caffeineCourseDetails.addCourse(caffeineCourse);
        caffeineCourseDetails.addCompetency(chem);
        caffeineCourseDetails.addCompetency(bio);
        caffeineCourseDetails.addTranslation(t2);

        saveCourseDetails();

        Map<Integer, CourseDetails> idToCourseDetails = getIdToCourseDetails();
        assertEquals(2, idToCourseDetails.size());

        Map<Integer, Course> idToCourses = getIdToCourses();
        assertEquals(2, idToCourses.size());

        Map<Integer, Sponsor> idToSponsor = getIdToSponsors();
        assertEquals(1, idToSponsor.size());

        Map<Integer, Translation> idToTranslations = getIdToTranslations();
        assertEquals(2, idToTranslations.size());

        Map<Integer, Audience> idToAudiences = getIdToAudiences();
        assertEquals(2, idToAudiences.size());

        Map<Integer, Competency> idToCompetencies = getIdToCompetencies();
        assertEquals(2, idToCompetencies.size());

        CourseDetails resultRohtoDetails = idToCourseDetails.get(rohtoCourseDetails.getId());
        CourseDetails resultCaffeineDetails = idToCourseDetails.get(caffeineCourseDetails.getId());
        Course resultRohto = idToCourses.get(rohtoCourse.getId());
        Course resultCaffeine = idToCourses.get(caffeineCourse.getId());
        Sponsor resultHkuSponsor = idToSponsor.get(hkuSponsor.getId());
        Translation resultT1 = idToTranslations.get(t1.getId());
        Translation resultT2 = idToTranslations.get(t2.getId());
        Competency resultBio = idToCompetencies.get(bio.getId());
        Competency resultChem = idToCompetencies.get(chem.getId());
        Audience resultFaculty = idToAudiences.get(faculty.getId());
        Audience resultPostDoc = idToAudiences.get(postDoc.getId());

        // NB: Testing things that shouldn't have happened to make sure relationships didn't get mapped wrong
        // audiences
        verifyInitialAudiences(resultRohtoDetails, resultCaffeineDetails, resultFaculty, resultPostDoc);

        // competencies
        verifyInitialCompetencies(resultRohtoDetails, resultCaffeineDetails, resultBio, resultChem);

        // translations
        verifyInitialTranslations(resultRohtoDetails, resultCaffeineDetails, resultT1, resultT2);

        // sponsors
        verifyInitialSponsors(resultRohtoDetails, resultCaffeineDetails, resultHkuSponsor);

        // Check courses, it also has a reference to institution. make sure there's no weird conflict
        verifyOptionalFields(resultCaffeine, resultRohto, bmc);

        assertTrue(caffeineCourseDetails.removeCompetency(chem));
        saveCourseDetails();
        idToCourseDetails = getIdToCourseDetails();
        assertEquals(2, idToCourseDetails.size());
        CourseDetails updatedCourseDetails = idToCourseDetails.get(caffeineCourseDetails.getId());
        assertEquals(1,updatedCourseDetails.getCompetencies().size());
    }

    private void verifyInitialSponsors(CourseDetails resultRohto, CourseDetails resultCaffeine, Sponsor resultHku) {
        assertTrue(resultRohto.sponsors.contains(hkuSponsor));
        assertFalse(resultCaffeine.sponsors.contains(hkuSponsor));
        assertTrue(resultHku.courseDetails.contains(resultRohto));
        assertFalse(resultHku.courseDetails.contains(resultCaffeine));
    }

    private void verifyInitialTranslations(CourseDetails resultRohto, CourseDetails resultCaffeine, Translation resultT1, Translation resultT2) {
        assertTrue(resultRohto.translations.contains(t1));
        assertTrue(resultRohto.translations.contains(t2));
        assertTrue(resultCaffeine.translations.contains(t2));
        assertFalse(resultCaffeine.translations.contains(t1));
        assertTrue(resultT1.courseDetails.contains(resultRohto));
        assertFalse(resultT1.courseDetails.contains(resultCaffeine));
        assertTrue(resultT2.courseDetails.contains(resultCaffeine));
        assertTrue(resultT2.courseDetails.contains(resultRohto));
    }

    private void verifyInitialCompetencies(CourseDetails resultRohto, CourseDetails resultCaffeine, Competency resultBio, Competency resultChem) {
        assertTrue(resultRohto.competencies.contains(bio));
        assertFalse(resultRohto.competencies.contains(chem));
        assertTrue(resultCaffeine.competencies.contains(chem));
        assertTrue(resultCaffeine.competencies.contains(bio));
        assertTrue(resultBio.courseDetails.contains(resultRohto));
        assertTrue(resultBio.courseDetails.contains(resultCaffeine));
        assertTrue(resultChem.courseDetails.contains(resultCaffeine));
        assertFalse(resultChem.courseDetails.contains(resultRohto));
    }

    private void verifyInitialAudiences(CourseDetails resultRohtoDetails, CourseDetails resultCaffeineDetails, Audience resultFaculty, Audience resultPostDoc) {
        assertTrue(resultRohtoDetails.audiences.contains(postDoc));
        assertTrue(resultRohtoDetails.audiences.contains(faculty));
        assertTrue(resultPostDoc.courseDetails.contains(resultRohtoDetails));
        assertTrue(resultFaculty.courseDetails.contains(resultRohtoDetails));
        assertFalse(resultFaculty.courseDetails.contains(resultCaffeineDetails));
        assertFalse(resultPostDoc.courseDetails.contains(resultCaffeineDetails));
    }

    private void saveCourseDetails() {
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(caffeineCourseDetails, rohtoCourseDetails));
    }

    private Map<Integer, CourseDetails> getIdToCourseDetails() {
        List<CourseDetails> courseDetails = HibernateManager.getInstance().getAllEntities(CourseDetails.class);
        return courseDetails.stream().collect(Collectors.toMap(CourseDetails::getId, Function.identity()));
    }
}
