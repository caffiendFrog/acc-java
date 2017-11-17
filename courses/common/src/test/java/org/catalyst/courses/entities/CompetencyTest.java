package org.catalyst.courses.entities;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.services.hibernate.HibernateManager;

import java.util.Arrays;

public class CompetencyTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(CompetencyTest.class);

    // protected static so we can reference these values in CourseTest
    protected final static String bioCompetencyName = "Biofelinatics";
    protected final static String bioCompetencyNote = "Meow mrow meow.";
    protected final static String chemCompetencyName = "Caffiene";
    protected final static String chemCompetencyNote = "Required for proper functions of the neuronic regions.";

    private Competency bioCompetency;
    private Competency chemCompetency;

    public void setUp() {
        bioCompetency = new Competency(bioCompetencyName);
        chemCompetency = new Competency(chemCompetencyName);
    }

    /**
     * Basic unit test to check the behavior of the required name field and also to check that non-applicable fields
     * (abbreviation) is always null and can't be set accidentally.
     */
    public void testBasicCompetency() {
        assertEquals(bioCompetencyName, bioCompetency.getName());

        bioCompetency.setNote(bioCompetencyNote);
        assertEquals(bioCompetencyNote, bioCompetency.getNote());

        // abbreviation should always be null
        bioCompetency.setAbbreviation("BFS");
        assertNull(bioCompetency.getAbbreviation());
    }

    /**
     * Most of the fields are already covered in the <code>DetailTest</code>. Check that we don't accidentally save
     * an abbreviation
     */
    public void testCRUD() {
        // try to set the abbreviation
        bioCompetency.setAbbreviation("BFS");

        // Create/Save competency
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(bioCompetency, chemCompetency));

        Competency result = HibernateManager.getInstance().getEntity(Competency.class, bioCompetency.getId());
        assertNull(result.getAbbreviation());

        // check the equals override
        assertEquals(bioCompetency, result);
    }
}
