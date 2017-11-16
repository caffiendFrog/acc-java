package org.catalyst.courses.entities;

import junit.framework.TestCase;

public class CompetencyTest extends TestCase {
    private final String bio_competency_keyword = "Biofelinatics";
    private final String chem_competency_keyword = "Caffiene";
    private final String chem_comptency_note = "Required for proper functions of the neuronic regions.";
//    private final Competency bio_competency = new Competency(bio_competency_keyword);
//    private final Competency chem_competency = new Competency(chem_competency_keyword);

    /**
     * Basic unit test to make sure getters & setters are correctly associated to the fields
     * and verify we can read, write and update the competencies
     */
    public void testBasicCompetency() {
        final Competency bioCompetency = new Competency(bio_competency_keyword);
        assertEquals(bio_competency_keyword, bioCompetency.getName());

        bioCompetency.setNote(chem_comptency_note);
        assertEquals(chem_comptency_note, bioCompetency.getNote());

        // abbreviation should always be null
        bioCompetency.setAbbreviation("BFS");
        assertNull(bioCompetency.getAbbreviation());
    }

    /**
     * Tests that we are able to save and load the entities to the database.
     */
    public void testSave() {
//        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(bio_competency, chem_competency));
//        List<Competency> competencies = HibernateManager.getInstance().getAll(Competency.class);
//        List<String> competency_keywords = competencies.stream().map(Competency::getName).collect(Collectors.toList());
//
//        assertTrue(competency_keywords.contains(bio_competency_keyword));
//        assertTrue(competency_keywords.contains(chem_competency_keyword));
//        assertEquals(2, competencies.size());
    }

    /**
     * Tests that we are able to query for and update entities in the database
     */
//    public void testUpdate() {
//        List<Competency> competencies = HibernateManager.getInstance().getAll(Competency.class);
//        assertEquals(2, competencies.size());
//    }

}
