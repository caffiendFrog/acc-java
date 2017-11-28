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
 * Tests only the additional required abbreviation field. Other fields are tested in <code>DetailTest</code>
 */
public class DetailWithAbbreviationTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(DetailWithAbbreviationTest.class);

    private final String aName = "Marco Polo";
    private final String aAbbreviation = "MP";
    private final String anotherAbbrev = "SKC";

    private ConcreteDetailWithAbbreviation aDetail;

    public void setUp() {
        aDetail = new ConcreteDetailWithAbbreviation(aName, aAbbreviation);
    }

    public void testBasicDetailWithAbbreviation() {
        assertEquals(aName, aDetail.getName());

        // should default to be active
        assertTrue(aDetail.isActive());

        // deactivate and check
        aDetail.deactivate();
        assertFalse(aDetail.isActive());
    }

    /**
     * Basic unit test to check the behavior of the required abbreviation field. Also check that the setters & getters
     * are correctly associated to the fields we want.
     */
    @SuppressWarnings("Duplicates")
    public void testRequiredAbbreviation() {
        assertEquals(aAbbreviation, aDetail.getAbbreviation());

        // Should be able to change the abbreviation
        aDetail.setAbbreviation(anotherAbbrev);
        assertEquals(anotherAbbrev, aDetail.getAbbreviation());

        // should not be able to set the abbreviation to null
        try {
            aDetail.setAbbreviation(null);
            fail();
        } catch (NullPointerException npe) {
            assertTrue(true);
        } catch (Throwable throwable) {
            // should only be throwing an NPE
            fail();
        }
    }

    /**
     * Test that crud operations to the DB for the entity work as expected
     */
    public void testCRUD() {
        // Create/Save details
        saveDetails();

        // Read details
        Map<Integer, ConcreteDetailWithAbbreviation> idToDetail = getIdToDetails();

        assertEquals(aDetail.getAbbreviation(), idToDetail.get(aDetail.getId()).getAbbreviation());

        // Get a single instance of a detail
        ConcreteDetailWithAbbreviation detail = HibernateManager.getInstance().getEntity(ConcreteDetailWithAbbreviation.class, aDetail.getId());
        assertEquals(aDetail.getAbbreviation(), detail.getAbbreviation());
        assertTrue(detail.isActive());

        // Update abbreviation and save
        aDetail.setAbbreviation(anotherAbbrev);
        aDetail.deactivate();
        saveDetails();

        // Read back from the database and check
        idToDetail = getIdToDetails();
        assertEquals(aDetail.getAbbreviation(), idToDetail.get(aDetail.getId()).getAbbreviation());
        assertFalse(idToDetail.get(aDetail.getId()).isActive());
    }

    public void testEquality() {
        saveDetails();

        ConcreteDetailWithAbbreviation result = HibernateManager.getInstance().getEntity(ConcreteDetailWithAbbreviation.class, aDetail.getId());

        // should be 'logically' equal
        assertEquals(aDetail, result);

        // should NOT be 'physically' equal
        assertFalse(aDetail == result);
    }

    private void saveDetails() {
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(aDetail));
    }

    private Map<Integer, ConcreteDetailWithAbbreviation> getIdToDetails() {
        List<ConcreteDetailWithAbbreviation> details = HibernateManager.getInstance().getAllEntities(ConcreteDetailWithAbbreviation.class);
        return details.stream().collect(Collectors.toMap(ConcreteDetailWithAbbreviation::getId, Function.identity()));

    }
}
