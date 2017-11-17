package org.catalyst.courses.entities;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.services.hibernate.HibernateManager;

import java.util.Arrays;

public class InstitutionTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(InstitutionTest.class);

    // protected static so we can reference these values in CourseTest
    protected final static String hkuName = "Hello Kitty University";
    protected final static String bmcName = "Badtz Maru College";
    protected final static String hkuAbbreviation = "HKU";
    protected final static String bmcAbbreviation = "BMC";
    protected final static String bmcNote = "Cartoon penguins allowed only";
    protected final static boolean hkuIsSponsor = false;

    private Institution hku;
    private Institution bmc;

    public void setUp() {
        hku = new Institution(hkuName, hkuAbbreviation);
        bmc = new Institution(bmcName, bmcAbbreviation);
    }

    /**
     * Quick sanity check we're still setting the basics correctly and check the Institution specific fields and defaults
     */
    public void testBasicInstitution() {
        assertEquals(bmcName, bmc.getName());
        assertEquals(bmcAbbreviation, bmc.getAbbreviation());

        // both institutions should be set to sponsor as default
        assertTrue(bmc.isSponsor());
        assertTrue(hku.isSponsor());

        // de-activitate sponsorship
        hku.setSponsor(hkuIsSponsor);
        assertFalse(hku.isSponsor());

        bmc.setNote(bmcNote);
        assertEquals(bmcNote, bmc.getNote());

        // check abbreviations
        hku.setAbbreviation("HK");
        assertEquals("HK", hku.getAbbreviation());

        // should be able to create institution as not sponsor
        Institution hkuNoSponsor = new Institution(hkuName, hkuAbbreviation, hkuIsSponsor);
        assertFalse(hkuNoSponsor.isSponsor());

    }

    /**
     * Check that institution specific fields are being saved
     */
    public void testCRUD() {
        // Create/save initial institutions
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(hku, bmc));

        // check the abbreviations
        Institution result = HibernateManager.getInstance().getEntity(Institution.class, hku.getId());
        assertEquals(hkuAbbreviation, result.getAbbreviation());

        // change sponsorship for hku
        hku.setSponsor(hkuIsSponsor);
        HibernateManager.getInstance().saveOrUpdate(hku);
        result = HibernateManager.getInstance().getEntity(Institution.class, hku.getId());
        assertFalse(result.isSponsor());

        result = HibernateManager.getInstance().getEntity(Institution.class, bmc.getId());
        assertTrue(result.isSponsor());

        // check the equals override
        assertEquals(result, bmc);
    }

}
