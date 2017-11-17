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

public class DetailTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(DetailTest.class);

    private final String aName = "Marco Polo";
    private final String bName = "Gallileo";
    private final String note = "Lorem ipsum whosum whatsum";
    private final String aAbbreviation = "MP";

    private Detail aDetail;
    private Detail bDetail;

    public void setUp() {
        aDetail = new Detail(aName);
        bDetail = new Detail(bName);
    }

    /**
     * Basic unit test to check the behavior of the required name field. Also check that the setters & getters
     * are correctly associated to the fields we want.
     */
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

        // abbreviation should be null to start
        assertNull(aDetail.getAbbreviation());
        aDetail.setAbbreviation(aAbbreviation);
        assertEquals(aAbbreviation, aDetail.getAbbreviation());

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
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(aDetail, bDetail));

        // Read details
        List<Detail> details = HibernateManager.getInstance().getAllEntities(Detail.class);
        Map<Integer, Detail> idToDetail = details.stream().collect(Collectors.toMap(Detail::getId, Function.identity()));

        assertEquals(aDetail.getName(), idToDetail.get(aDetail.getId()).getName());
        assertEquals(bDetail.getName(), idToDetail.get(bDetail.getId()).getName());

        // Get a single detail
        Detail detail = HibernateManager.getInstance().getEntity(Detail.class, aDetail.getId());
        assertEquals(aDetail.getName(), detail.getName());
        assertEquals(aDetail, detail);

        // Update optional fields
        aDetail.setNote(note);
        aDetail.setAbbreviation(aAbbreviation);
        bDetail.deactivate();
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(aDetail, bDetail));

        // Read back from database and check
        details = HibernateManager.getInstance().getAllEntities(Detail.class);
        idToDetail = details.stream().collect(Collectors.toMap(Detail::getId, Function.identity()));
        assertEquals(aDetail.getNote(), idToDetail.get(aDetail.getId()).getNote());
        assertEquals(aDetail.getAbbreviation(), idToDetail.get(aDetail.getId()).getAbbreviation());
        assertEquals(aDetail.getName(), idToDetail.get(aDetail.getId()).getName());
        assertFalse(idToDetail.get(bDetail.getId()).isActive());
    }

    public void testEquality() {
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(bDetail, aDetail));

        Detail result = HibernateManager.getInstance().getEntity(Detail.class, aDetail.getId());
        // should be 'logically' equal
        assertEquals(aDetail, result);
        // should NOT be 'physically' equal
        assertFalse(aDetail == result);
    }
}
