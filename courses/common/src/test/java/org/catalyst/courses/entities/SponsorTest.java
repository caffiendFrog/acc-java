package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.services.HibernateManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.catalyst.courses.entities.InstitutionTest.*;
import static org.junit.Assert.*;

public class SponsorTest{
    private final static Logger logger = LogManager.getLogger(SponsorTest.class);

    private final static Sponsor hkuSponsor = new Sponsor(hkuName, hkuAbbreviation);

    @BeforeClass
    public static void before() {
        // need to clear the database because we are using the table in two different places
        // cause confusion
        removeAllBaseInstitutions();
    }

    @Before
    public void setUp() {
        // reset entities
        hkuSponsor.setName(hkuName);
        hkuSponsor.setNote(null);
        hkuSponsor.setAbbreviation(hkuAbbreviation);
        hkuSponsor.activate();
        hkuSponsor.courseDetails.clear();
    }

    @Test
    public void testBasicSponsor() {
        assertEquals(hkuName, hkuSponsor.getName());
        assertEquals(hkuAbbreviation, hkuSponsor.getAbbreviation());

        assertTrue(hkuSponsor.isSponsor());

        hkuSponsor.setNote(bmcNote);
        assertEquals(bmcNote, hkuSponsor.getNote());

        assertTrue(hkuSponsor.isActive());

        hkuSponsor.deactivate();
        assertFalse(hkuSponsor.isActive());
    }
    @Test
    public void testBasicCRUD() {
        saveSponsors();

        Map<Integer, Sponsor> idToSponsors = getIdToSponsors();
        assertEquals(hkuAbbreviation, idToSponsors.get(hkuSponsor.getId()).getAbbreviation());
        assertEquals(1, idToSponsors.size());
        hkuSponsor.setAbbreviation("qwerty");
        hkuSponsor.deactivate();
        saveSponsors();

        idToSponsors = getIdToSponsors();
        assertEquals(1, idToSponsors.size());
        assertEquals("qwerty", idToSponsors.get(hkuSponsor.getId()).getAbbreviation());
        assertFalse(idToSponsors.get(hkuSponsor.getId()).isActive());
    }

    /**
     * Sponsor & Institution both map to one table, check that everything is mapping correctly
     */
    @Test
    public void testAdvancedCRUD() {
        Institution bmc = new Institution(bmcName, bmcAbbreviation);
        HibernateManager.getInstance().saveOrUpdate(bmc);
        saveSponsors();

        Map<Integer, BaseInstitution> idToBaseInstitutions = getIdToBaseInstitutions();
        assertEquals(2, idToBaseInstitutions.size());
        BaseInstitution resultBmc = idToBaseInstitutions.get(bmc.getId());
        BaseInstitution resultHku = idToBaseInstitutions.get(hkuSponsor.getId());

        // We should get the hku institution as sponsor back here
        assertNotNull(resultHku);
        assertEquals(hkuAbbreviation, resultHku.getAbbreviation());
        assertTrue(resultHku.isActive());

        assertNotNull(resultBmc);
        assertEquals(bmcAbbreviation, resultBmc.getAbbreviation());

        // resultHku (institution) and hkuSponsor (sponsor) should be interchangeable
        bmc.deactivate();
        resultHku.deactivate();
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(bmc, resultHku));

        idToBaseInstitutions = getIdToBaseInstitutions();
        assertEquals(2, idToBaseInstitutions.size());
        resultBmc = idToBaseInstitutions.get(bmc.getId());
        BaseInstitution resultHku2 = idToBaseInstitutions.get(hkuSponsor.getId());

        assertFalse(resultBmc.isActive());
        assertFalse(resultHku2.isActive());
    }
    @Test
    public void testEquality() {
        saveSponsors();
        Sponsor result = HibernateManager.getInstance().getEntity(Sponsor.class, hkuSponsor.getId());

        // should be 'logically' equal
        assertEquals(hkuSponsor, result);

        // should not be 'physically' equal
        assertFalse(hkuSponsor == result);

        // there should only be 1
        Map<Integer, Sponsor> idToBaseInstitutions = getIdToSponsors();
        assertEquals(1, idToBaseInstitutions.size());
    }

    private void saveSponsors() {
        HibernateManager.getInstance().saveOrUpdate(hkuSponsor);
    }

    protected static Map<Integer, Sponsor> getIdToSponsors() {
        List<Sponsor> sponsors = HibernateManager.getInstance().getAllEntities(Sponsor.class);
        return sponsors.stream().collect(Collectors.toMap(Sponsor::getId, Function.identity()));
    }

    protected static Map<Integer, BaseInstitution> getIdToBaseInstitutions() {
        List<BaseInstitution> baseInstitutions = HibernateManager.getInstance().getAllEntities(BaseInstitution.class);
        return baseInstitutions.stream().collect(Collectors.toMap(BaseInstitution::getId, Function.identity()));
    }
}
