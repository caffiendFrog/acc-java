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

import static org.junit.Assert.*;
/**
 * Testing all fields to make sure we don't accidentally override methods incorrectly in the future
 */
@SuppressWarnings("Duplicates")
public class CompetencyTest {
    private final static Logger logger = LogManager.getLogger(CompetencyTest.class);

    protected final static String bioName = "Biofelinatics";
    protected final static String bioNote = "Meow mrow meow.";
    protected final static String chemName = "Caffiene";
    protected final static String chemNote = "Required for proper functions of the neuronic regions.";

    private final static Competency bio = new Competency(bioName);
    private final static Competency chem = new Competency(chemName);

    @BeforeClass
    public static void beforeClass() {
        HibernateManager.getInstance().resetSessionFactory();
    }

    @Before
    public void setUp() {
        // reset competencies
        bio.setName(bioName);
        bio.setNote(null);
        bio.activate();
        bio.courseDetails.clear();

        chem.setName(chemName);
        chem.setNote(null);
        chem.activate();
        chem.courseDetails.clear();
    }

    /**
     * Basic unit test to check the behavior of the required name field and also to check that non-applicable fields
     * (abbreviation) is always null and can't be set accidentally.
     */
    @Test
    public void testBasicCompetency() {
        assertEquals(bioName, bio.getName());

        bio.setNote(bioNote);
        assertEquals(bioNote, bio.getNote());

        // should default to active
        assertTrue(chem.isActive());

        // deactivate and check
        chem.deactivate();
        assertFalse(chem.isActive());
    }

    /**
     * Should be able to update require fields, but shouldn't be able to set them to null
     */
    @Test
    public void testRequired() {
        bio.setName("qwerty");
        assertEquals("qwerty", bio.getName());

        try {
            bio.setName(null);
            fail();
        } catch (NullPointerException npe) {
            assertTrue(true);
        } catch (Throwable t) {
            //should only be throwing an NPE
            fail();
        }
    }

    @Test
    public void testCRUD() {
        chem.setNote(chemNote);

        // Create/save competencies
        saveCompetencies();

        // Read them back
        Map<Integer, Competency> idToCompetencies = getIdToCompetencies();
        assertEquals(2, idToCompetencies.size());
        assertEquals(chem.getNote(), idToCompetencies.get(chem.getId()).getNote());

        // Update one of the competencies and save
        bio.setNote(bioNote);
        chem.deactivate();
        saveCompetencies();

        // Read them back
        idToCompetencies = getIdToCompetencies();
        assertEquals(2, idToCompetencies.size());
        assertEquals(bio.getNote(), idToCompetencies.get(bio.getId()).getNote());
        assertFalse(idToCompetencies.get(chem.getId()).isActive());
    }

    @Test
    public void testEquality() {
        saveCompetencies();
        Competency result = HibernateManager.getInstance().getEntity(Competency.class, chem.getId());

        // should be 'logically' equal
        assertEquals(chem, result);

        // should not be 'physically' equal
        assertFalse(chem == result);

        // we should still only have 2 rows
        Map<Integer, Competency> idToCompetencies = getIdToCompetencies();
        assertEquals(2, idToCompetencies.size());
    }

    private void saveCompetencies() {
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(bio, chem));
    }

    protected static Map<Integer, Competency> getIdToCompetencies() {
        List<Competency> competencies = HibernateManager.getInstance().getAllEntities(Competency.class);
        return competencies.stream().collect(Collectors.toMap(Competency::getId, Function.identity()));
    }
}
