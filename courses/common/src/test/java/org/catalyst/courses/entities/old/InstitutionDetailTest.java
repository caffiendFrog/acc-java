package org.catalyst.courses.entities.old;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InstitutionDetailTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(InstitutionDetailTest.class);

    // protected static so we can reference these values in CourseDetailTest
    protected final static String hkuName = "Hello Kitty University";
    protected final static String bmcName = "Badtz Maru College";
    protected final static String hkuAbbreviation = "HKU";
    protected final static String bmcAbbreviation = "BMC";
    protected final static String bmcNote = "Cartoon penguins allowed only";
    protected final static boolean hkuIsSponsor = false;
    protected final static String t1Name = "TranslationDetail to Alpacas";
    protected final static String t1Abbreviation = "T1";

    private InstitutionDetail hku;
    private InstitutionDetail bmc;

    public void testSanity() {

    }
//    public void testSanity() {
//        Detail detail = new Detail();
//        detail.setName(bmcName);
//        detail.setNote(bmcNote);
//        detail.setAbbreviation(bmcAbbreviation);
//
//        TranslationDetail td = new TranslationDetail();
//        td.setName(t1Name);
//        td.setAbbreviation(t1Abbreviation);
//
//        InstitutionDetail instDetail = new InstitutionDetail();
//        instDetail.setDetail(detail);
//
//        HibernateManager.getInstance().saveOrUpdate(td);
//        HibernateManager.getInstance().saveOrUpdate(detail);
//        HibernateManager.getInstance().saveOrUpdate(instDetail);
//
//        TranslationDetail transResult = HibernateManager.getInstance().getEntity(TranslationDetail.class, td.getId());
//        logger.debug("[SKC] " + td.getId() + " - " + td);
//    }



//    public void setUp() {
//        hku = new InstitutionDetail(hkuName, hkuAbbreviation);
//        bmc = new InstitutionDetail(bmcName, bmcAbbreviation);
//    }
//
//    /**
//     * Quick sanity check we're still setting the basics correctly and check the InstitutionDetail specific fields and defaults
//     */
//    public void testBasicInstitution() {
//        assertEquals(bmcName, bmc.getName());
//        assertEquals(bmcAbbreviation, bmc.getAbbreviation());
//
//        // both institutions should be set to sponsor as default
//        assertTrue(bmc.isSponsor());
//        assertTrue(hku.isSponsor());
//
//        // de-activate sponsorship
//        hku.setSponsor(hkuIsSponsor);
//        assertFalse(hku.isSponsor());
//
//        bmc.setNote(bmcNote);
//        assertEquals(bmcNote, bmc.getNote());
//
//        // check abbreviations
//        hku.setAbbreviation("HK");
//        assertEquals("HK", hku.getAbbreviation());
//
//        // should be able to create institution as not sponsor
//        InstitutionDetail hkuNoSponsor = new InstitutionDetail(hkuName, hkuAbbreviation, hkuIsSponsor);
//        assertFalse(hkuNoSponsor.isSponsor());
//
//    }
//
//    /**
//     * Check that institution specific fields are being saved
//     */
//    public void testCRUD() {
//        // Create/save initial institutions
//        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(hku, bmc));
//
//        // check the abbreviations
//        InstitutionDetail result = HibernateManager.getInstance().getEntity(InstitutionDetail.class, hku.getId());
//        assertEquals(hkuAbbreviation, result.getAbbreviation());
//
//        // change sponsorship for hku
//        hku.setSponsor(hkuIsSponsor);
//        HibernateManager.getInstance().saveOrUpdate(hku);
//        result = HibernateManager.getInstance().getEntity(InstitutionDetail.class, hku.getId());
//        assertFalse(result.isSponsor());
//    }
//
//    public void testEquality() {
//        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(bmc, hku));
//
//        InstitutionDetail result = HibernateManager.getInstance().getEntity(InstitutionDetail.class, bmc.getId());
//        // should be 'logically' equal
//        assertEquals(bmc, result);
//        // should not be 'physically' equal
//        assertFalse(bmc == result);
//    }

}
