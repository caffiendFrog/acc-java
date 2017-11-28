package org.catalyst.courses.entities;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.services.HibernateManager;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Testing all fields to make sure we don't accidentally override methods incorrectly in the future
 */
public class CompetencyTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(CompetencyTest.class);

    protected final static String bioName = "Biofelinatics";
    protected final static String bioNote = "Meow mrow meow.";
    protected final static String chemName = "Caffiene";
    protected final static String chemNote = "Required for proper functions of the neuronic regions.";

    private Competency bio;
    private Competency chem;

    public void setUp() {
        bio = new Competency(bioName);
        chem = new Competency(chemName);
    }

    /**
     * Basic unit test to check the behavior of the required name field and also to check that non-applicable fields
     * (abbreviation) is always null and can't be set accidentally.
     */
    @SuppressWarnings("Duplicates")
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
    @SuppressWarnings("Duplicates")
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
    
    public void testCRUD() {
        chem.setNote(chemNote);

        // Create/save competencies
        saveCompetencies();

        // Read them back
        Map<Integer, Competency> idToCompetencies = getIdToCompetencies();
        assertEquals(chem.getNote(), idToCompetencies.get(chem.getId()).getNote());

        // Update one of the competencies and save
        bio.setNote(bioNote);
        chem.deactivate();
        saveCompetencies();

        // Read them back
        idToCompetencies = getIdToCompetencies();
        assertEquals(bio.getNote(), idToCompetencies.get(bio.getId()).getNote());
        assertFalse(idToCompetencies.get(chem.getId()).isActive());
    }

    public void testEquality() {
        saveCompetencies();
        Competency result = HibernateManager.getInstance().getEntity(Competency.class, chem.getId());

        // should be 'logically' equal
        assertEquals(chem, result);

        // should not be 'physically' equal
        assertFalse(chem == result);
    }

    private void saveCompetencies() {
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(bio, chem));
    }

    private Map<Integer, Competency> getIdToCompetencies() {
        List<Competency> competencies = HibernateManager.getInstance().getAllEntities(Competency.class);
        return competencies.stream().collect(Collectors.toMap(Competency::getId, Function.identity()));
    }
}
