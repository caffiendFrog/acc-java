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
public class InstitutionTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(InstitutionTest.class);

    protected final static String hkuName = "Hello Kitty University";
    protected final static String bmcName = "Badtz Maru College";
    protected final static String hkuAbbreviation = "HKU";
    protected final static String bmcAbbreviation = "BMC";
    protected final static String bmcNote = "Cartoon penguins allowed only";
    protected final static boolean hkuIsSponsor = true;

    private Institution hku;
    private Institution bmc;

    public void setUp() {
        hku = new Institution(hkuName, hkuAbbreviation);
        bmc = new Institution(bmcName, bmcAbbreviation);
    }

    public void testBasicInstitution() {
        assertEquals(hkuName, hku.getName());
        assertEquals(bmcAbbreviation, bmc.getAbbreviation());

        // both institutions should default to NOT be sponsors
        assertFalse(bmc.isSponsor());
        assertFalse(hku.isSponsor());

        // activate sponsorship
        hku.setSponsor(hkuIsSponsor);
        assertTrue(hku.isSponsor());

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
    @SuppressWarnings("Duplicates")
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

    public void testCRUD() {
        saveInstitutions();

        Map<Integer, Institution> idToInstitutions = getIdToInstitutions();
        assertEquals(hkuAbbreviation, idToInstitutions.get(hku.getId()).getAbbreviation());

        // change sponsorship & abbreviation
        hku.setSponsor(hkuIsSponsor);
        bmc.setAbbreviation("qwerty");
        bmc.deactivate();
        saveInstitutions();

        idToInstitutions = getIdToInstitutions();
        assertTrue(idToInstitutions.get(hku.getId()).isSponsor());
        assertEquals("qwerty", idToInstitutions.get(bmc.getId()).getAbbreviation());
        assertFalse(idToInstitutions.get(bmc.getId()).isActive());
    }

    public void testEquality() {
        saveInstitutions();
        Institution result = HibernateManager.getInstance().getEntity(Institution.class, hku.getId());

        // should be 'logically' equal
        assertEquals(hku, result);
        // should not be 'physically' equal
        assertFalse(hku == result);
    }

    private void saveInstitutions() {
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(bmc, hku));
    }

    private Map<Integer, Institution> getIdToInstitutions() {
        List<Institution> institutions = HibernateManager.getInstance().getAllEntities(Institution.class);
        return institutions.stream().collect(Collectors.toMap(Institution::getId, Function.identity()));
    }
}
