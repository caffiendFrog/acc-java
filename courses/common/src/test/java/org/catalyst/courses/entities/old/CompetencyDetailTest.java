package org.catalyst.courses.entities.old;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompetencyDetailTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(CompetencyDetailTest.class);

    // protected static so we can reference these values in CourseDetailTest
    protected final static String bioName = "Biofelinatics";
    protected final static String bioNote = "Meow mrow meow.";
    protected final static String chemName = "Caffiene";
    protected final static String chemNote = "Required for proper functions of the neuronic regions.";
    public void testThing() {
        assertTrue(true);
    }
//    private CompetencyDetail bio;
//    private CompetencyDetail chem;
//
//    public void setUp() {
//        bio = new CompetencyDetail(bioName);
//        chem = new CompetencyDetail(chemName);
//    }
//
//    /**
//     * Basic unit test to check the behavior of the required name field and also to check that non-applicable fields
//     * (abbreviation) is always null and can't be set accidentally.
//     */
//    public void testBasicCompetency() {
//        assertEquals(bioName, bio.getName());
//
//        bio.setNote(bioNote);
//        assertEquals(bioNote, bio.getNote());
//
//        // abbreviation should always be null
//        bio.setAbbreviation("BFS");
//        assertNull(bio.getAbbreviation());
//    }
//
//    /**
//     * Most of the fields are already covered in the <code>DetailTest</code>. Check that we don't accidentally save
//     * an abbreviation
//     */
//    public void testCRUD() {
//        // try to set the abbreviation
//        bio.setAbbreviation("BFS");
//
//        // Create/Save competency
//        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(bio, chem));
//
//        CompetencyDetail result = HibernateManager.getInstance().getEntity(CompetencyDetail.class, bio.getId());
//        assertNull(result.getAbbreviation());
//    }
//
//    public void testEquality() {
//        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(bio, chem));
//        CompetencyDetail result = HibernateManager.getInstance().getEntity(CompetencyDetail.class, bio.getId());
//        // should be 'logically' equal
//        assertEquals(bio, result);
//        // should not be 'physically' equal
//        assertFalse(bio == result);
//    }
}
