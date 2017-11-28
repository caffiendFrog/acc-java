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

public class DetailTest extends TestCase{
    private final static Logger logger = LogManager.getLogger(DetailTest.class);

    private final String aName = "Marco Polo";
    private final String bName = "Gallileo";
    private final String note = "Lorem ipsum whosum whatsum";

    private ConcreteDetail aDetail;
    private ConcreteDetail bDetail;

    public void setUp() {
        aDetail = new ConcreteDetail(aName);
        bDetail = new ConcreteDetail(bName);
    }
    /**
     * Basic unit test to check the behavior of the required name field. Also check that the setters & getters
     * are correctly associated to the fields we want.
     */
    @SuppressWarnings("Duplicates")
    public void testRequiredName() {
        assertEquals(aName, aDetail.getName());

        // should be able to change the name
        aDetail.setName(bName);
        assertEquals(bName, aDetail.getName());

        // should not be able to set the name to null
        try {
            aDetail.setName(null);
            fail();
        } catch (NullPointerException npe) {
            assertTrue(true);
        } catch (Throwable throwable) {
            // should only be throwing NPE
            fail();
        }
    }

    /**
     * Basic unit test to check the behavior of the optional fields. Also check that the setters & getters
     * are correctly associated to the fields we want.
     */
    public void testOptionalFields() {
        // detail should automatically be activated
        assertTrue(aDetail.isActive());
        aDetail.deactivate();
        assertFalse(aDetail.isActive());
        aDetail.activate();
        assertTrue(aDetail.isActive());

        // note should be null to start
        assertNull(aDetail.getNote());
        aDetail.setNote(note);
        assertEquals(note,aDetail.getNote());
    }

    /**
     * Test that crud operations to the DB for the entity work as expected
     */
    public void testCRUD() {
        // Create/Save details
        saveDetails();

        // Read details
        Map<Integer, ConcreteDetail> idToDetail = getIdToDetails();

        assertEquals(aDetail.getName(), idToDetail.get(aDetail.getId()).getName());
        assertEquals(bDetail.getName(), idToDetail.get(bDetail.getId()).getName());

        // Get a single instance of a detail
        ConcreteDetail detail = HibernateManager.getInstance().getEntity(ConcreteDetail.class, aDetail.getId());
        assertEquals(aDetail.getName(), detail.getName());

        // Update optional fields;
        aDetail.setName(note);
        bDetail.deactivate();
        saveDetails();

        // Read back from the database and check
        idToDetail = getIdToDetails();
        assertEquals(aDetail.getNote(), idToDetail.get(aDetail.getId()).getNote());
        assertEquals(aDetail.getName(), idToDetail.get(aDetail.getId()).getName());
        assertFalse(idToDetail.get(bDetail.getId()).isActive());
    }

    public void testEquality() {
        saveDetails();

        ConcreteDetail result = HibernateManager.getInstance().getEntity(ConcreteDetail.class, aDetail.getId());

        // should be 'logically' equal
        assertEquals(aDetail, result);

        // should NOT be 'physically' equal
        assertFalse(aDetail == result);
    }

    private void saveDetails() {
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(aDetail, bDetail));
    }

    private Map<Integer, ConcreteDetail> getIdToDetails() {
        List<ConcreteDetail> details = HibernateManager.getInstance().getAllEntities(ConcreteDetail.class);
        return details.stream().collect(Collectors.toMap(ConcreteDetail::getId, Function.identity()));
    }
}