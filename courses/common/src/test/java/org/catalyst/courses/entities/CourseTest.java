package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.services.HibernateManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.catalyst.courses.entities.InstitutionTest.*;
import static org.junit.Assert.*;

public class CourseTest {
    private final static Logger logger = LogManager.getLogger(CourseTest.class);

    protected static final String caffeineCourseName = "Complex union of caffeine and felines.";
    protected static final String rohtoCourseName = "Partiality confounded Rohto-effect";
    protected static final String caffeineEmail = "caffeine@coff.ee";
    protected static final String caffeineUrl = "www.coff.ee";
    protected static final Date caffeineDate = new Date();
    protected static final String caffeineDescription = "Happy Monday morning wake up juice. How does it help the furballs?";
    protected static final String caffeineDateInfo = "Every other Fritzday";
    protected static final String caffeineHours = "2 hours";
    protected static final String caffeineMaxEnroll = "10 hoomans";
    protected static final String caffeineSearchBlob = "blobby blob blob boo";
    protected static final Date caffeineSortDate = new Date();
    protected static final String rohtoDescription = "Cure for red eyes and staring at monitors until the eyeballs bleed dry.";
    protected static final String rohtoHours = "14.5 total";
    protected static final String rohtoDateInfo = "Occurs annually (Fall)";
    protected static final String rohtoEmail = "horton.hears@who.oo";

    private final static Course caffeineCourse = new Course(caffeineCourseName);
    private final static Course rohtoCourse = new Course(rohtoCourseName);
    private final static Institution courseBmc = new Institution(bmcName, bmcAbbreviation);

    @BeforeClass
    public static void beforeClass() {
        HibernateManager.getInstance().resetSessionFactory();
    }

    @Before
    public void setUp() {
        // reset entities
        caffeineCourse.setName(caffeineCourseName);
        rohtoCourse.setName(rohtoCourseName);
        courseBmc.setName(bmcName);
        courseBmc.setAbbreviation(bmcAbbreviation);
        courseBmc.setNote(bmcNote);
        setOptionalFields(caffeineCourse, rohtoCourse, courseBmc);
    }

    @Test
    public void testBasicCourse() {
        assertEquals(caffeineCourseName, caffeineCourse.getName());

        // should default to active
        assertTrue(rohtoCourse.isActive());

        // deactivate and check
        rohtoCourse.deactivate();
        assertFalse(rohtoCourse.isActive());

        // should default to un-archived and no webcast
        assertFalse(rohtoCourse.isArchived());
        assertFalse(caffeineCourse.hasWebcast());

        // archive and set webcast
        rohtoCourse.archive();
        caffeineCourse.setWebcast(true);
        assertTrue(rohtoCourse.isArchived());
        assertTrue(caffeineCourse.hasWebcast());

        // check the optional fields
        verifyOptionalFields(caffeineCourse, rohtoCourse, courseBmc);
        assertEquals(caffeineDate, caffeineCourse.getDate());
        assertEquals(caffeineSortDate, caffeineCourse.getSortDate());
    }

    @Test
    public void testCRUD() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        saveCourses();

        Map<Integer, Course> idToCourses = getIdToCourses();
        assertEquals(2, idToCourses.size());

        Course resultRohto = idToCourses.get(rohtoCourse.getId());
        Course resultCaffeine = idToCourses.get(caffeineCourse.getId());
        assertEquals(rohtoCourseName, resultRohto.getName());
        assertTrue(resultCaffeine.isActive());
        assertFalse(resultCaffeine.isArchived());
        assertFalse(resultRohto.hasWebcast());
        assertEquals(courseBmc, rohtoCourse.getInstitution());
        verifyOptionalFields(resultCaffeine, resultRohto, courseBmc);
        assertEquals(sdf.format(caffeineDate), resultCaffeine.getDate().toString());
        assertEquals(sdf.format(caffeineSortDate), resultCaffeine.getSortDate().toString());

        rohtoCourse.removeInstitution(courseBmc);
        saveCourses();
        idToCourses = getIdToCourses();
        assertEquals(2, idToCourses.size());
        assertNull(idToCourses.get(rohtoCourse.getId()).getInstitution());
    }

    @Test
    public void testBasicEquality() {
        saveCourses();
        Course result = HibernateManager.getInstance().getEntity(Course.class, rohtoCourse.getId());

        // should be 'logically' equal
        assertEquals(rohtoCourse, result);

        // should not be 'physically' equal
        assertFalse(rohtoCourse == result);

        // should still only be 2 rows
        Map<Integer, Course> idToCourses = getIdToCourses();
        assertEquals(2, idToCourses.size());
    }

    protected static void setOptionalFields(Course caffeineCourse, Course rohtoCourse, Institution bmc) {
        caffeineCourse.setContactEmail(caffeineEmail);
        caffeineCourse.setContactUrl(caffeineUrl);
        caffeineCourse.setDate(caffeineDate);
        caffeineCourse.setDateInfo(caffeineDateInfo);
        caffeineCourse.setDescription(caffeineDescription);
        caffeineCourse.setHours(caffeineHours);
        caffeineCourse.setMaxEnroll(caffeineMaxEnroll);
        caffeineCourse.setSearchBlob(caffeineSearchBlob);
        caffeineCourse.setSortDate(caffeineSortDate);

        rohtoCourse.setContactEmail(rohtoEmail);
        rohtoCourse.setDateInfo(rohtoDateInfo);
        rohtoCourse.setDescription(rohtoDescription);
        rohtoCourse.setHours(rohtoHours);
        rohtoCourse.setInstitution(bmc);
    }

    protected static void verifyOptionalFields(Course caffeineCourse, Course rohtoCourse, BaseInstitution bmc) {
        assertEquals(caffeineUrl, caffeineCourse.getContactUrl());
        assertEquals(caffeineMaxEnroll, caffeineCourse.getMaxEnroll());
        assertEquals(caffeineSearchBlob, caffeineCourse.getSearchBlob());

        assertEquals(rohtoEmail, rohtoCourse.getContactEmail());
        assertEquals(rohtoDateInfo, rohtoCourse.getDateInfo());
        assertEquals(rohtoDescription, rohtoCourse.getDescription());
        assertEquals(rohtoHours, rohtoCourse.getHours());
        assertEquals(bmc, rohtoCourse.getInstitution());
    }

    private void saveCourses() {
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(courseBmc, caffeineCourse, rohtoCourse));
    }

    protected static Map<Integer, Course> getIdToCourses() {
        List<Course> course = HibernateManager.getInstance().getAllEntities(Course.class);
        return course.stream().collect(Collectors.toMap(Course::getId, Function.identity()));
    }
}
