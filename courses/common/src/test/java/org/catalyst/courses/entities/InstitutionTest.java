package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.services.HibernateManager;
import org.hibernate.Session;
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
public class InstitutionTest {
    private final static Logger logger = LogManager.getLogger(InstitutionTest.class);

    protected final static String hkuName = "Hello Kitty University";
    protected final static String bmcName = "Badtz Maru College";
    protected final static String hkuAbbreviation = "HKU";
    protected final static String bmcAbbreviation = "BMC";
    protected final static String bmcNote = "Cartoon penguins allowed only";

    private final static Institution hku = new Institution(hkuName, hkuAbbreviation);
    private final static Institution bmc = new Institution(bmcName, bmcAbbreviation);

    @BeforeClass
    public static void before() {
        // need to clear the database because we are using the table in two different places
        // cause confusion
        removeAllBaseInstitutions();
    }

    @Before
    public void setUp() {
        // reset entities
        hku.setName(hkuName);
        hku.setAbbreviation(hkuAbbreviation);
        hku.setNote(null);
        hku.activate();
        hku.courses.clear();

        bmc.setName(bmcName);
        bmc.setAbbreviation(bmcAbbreviation);
        bmc.setNote(null);
        bmc.activate();
        bmc.courses.clear();
    }

    @Test
    public void testBasicInstitution() {
        assertEquals(hkuName, hku.getName());
        assertEquals(bmcAbbreviation, bmc.getAbbreviation());

        bmc.setNote(bmcNote);
        assertEquals(bmcNote, bmc.getNote());

        // should default to active
        assertTrue(hku.isActive());

        // deactivate and check
        hku.deactivate();
        assertFalse(hku.isActive());
    }

    /**
     * Should be able to update require fields, but shouldn't be able to set them to null
     */
    @Test
    public void testRequired() {
        hku.setAbbreviation("HK");
        assertEquals("HK", hku.getAbbreviation());

        try {
            hku.setAbbreviation(null);
            fail();
        } catch (NullPointerException npe) {
            assertTrue(true);
        } catch (Throwable throwable) {
            // should onluy be throwing an NPE
            fail();
        }

        bmc.setName("qwerty");
        assertEquals("qwerty", bmc.getName());

        try {
            bmc.setName(null);
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
        saveInstitutions();

        Map<Integer, Institution> idToInstitutions = getIdToInstitutions();
        assertEquals(2, idToInstitutions.size());
        assertEquals(hkuAbbreviation, idToInstitutions.get(hku.getId()).getAbbreviation());

        // change sponsorship & abbreviation
        bmc.setAbbreviation("qwerty");
        bmc.deactivate();
        saveInstitutions();

        idToInstitutions = getIdToInstitutions();
        assertEquals(2, idToInstitutions.size());
        assertEquals("qwerty", idToInstitutions.get(bmc.getId()).getAbbreviation());
        assertFalse(idToInstitutions.get(bmc.getId()).isActive());
    }

    @Test
    public void testEquality() {
        saveInstitutions();
        BaseInstitution result = HibernateManager.getInstance().getEntity(BaseInstitution.class, hku.getId());

        // should be 'logically' equal
        assertEquals(hku, result);
        // should not be 'physically' equal
        assertFalse(hku == result);

        Map<Integer, Institution> idToInstitutions = getIdToInstitutions();
        assertEquals(2, idToInstitutions.size());
    }

    private void saveInstitutions() {
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(bmc, hku));
    }

    protected static Map<Integer, Institution> getIdToInstitutions() {
        List<Institution> institutions = HibernateManager.getInstance().getAllEntities(Institution.class);
        return institutions.stream().collect(Collectors.toMap(Institution::getId, Function.identity()));
    }

    protected static void removeAllBaseInstitutions() {
        final Session session =HibernateManager.getInstance().startTransaction();
        List<BaseInstitution> results = HibernateManager.getInstance().getAllEntities(BaseInstitution.class);
        for(BaseInstitution result : results) {
            session.delete(result);
        }
        HibernateManager.getInstance().endTransaction(session);
    }


}
