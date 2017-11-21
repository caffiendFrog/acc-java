package org.catalyst.courses.test;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Date;

public class CourseDetailTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(CourseDetailTest.class);
    protected static final String caffeineCourseName = "Complex union of caffeine and felines.";
    protected static final String rohtoCourseName = "Partiality confounded Rohto-effect";
    protected static final String caffeineEmail = "caffeine@coff.ee";
    protected static final String caffeineUrl = "www.coff.ee";
    protected static final Date caffeineDate = new Date();
    protected static final String caffeineDescription = "Happy Monday morning wake up juice. How does it help the furballs?";
    protected static final String caffeineDateInfo = "Every other Fritzday";
    protected static final String caffeineHours = "2 hours";
    protected static final String caffeineMaxEnroll = "10 hoomans";
    protected static final String caffeineSearchBlob = "blobby blob blob boo";
    protected static final Date caffeineSortDate = new Date();
    protected static final String rohtoDescription = "Cure for red eyes and staring at monitors until the eyeballs bleed dry.";
    protected static final String rohtoHours = "14.5 total";
    protected static final String rohtoDateInfo = "Occurs annually (Fall)";
    protected static final String rohtoEmail = "horton.hears@who.oo";
    // protected static so we can reference these values in CourseDetailTest
    protected final static String hkuName = "Hello Kitty University";
    protected final static String bmcName = "Badtz Maru College";
    protected final static String hkuAbbreviation = "HKU";
    protected final static String bmcAbbreviation = "BMC";
    protected final static String bmcNote = "Cartoon penguins allowed only";
    protected final static boolean hkuIsSponsor = false;

    public void testSanity() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("test_hibernate.cfg.xml")
                .build();
        SessionFactory sessionFactory = null;
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            logger.debug(e.getMessage());
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }

        Detail_new instDetail = new Detail_new(hkuName, bmcNote);
        instDetail.setAbbreviation(hkuAbbreviation);
        Detail_new courseDetail = new Detail_new(caffeineCourseName);
        CourseDetail_new course = new CourseDetail_new();

        InstitutionDetail_new inst = new InstitutionDetail_new();

        inst.setSponsor(true);
        inst.setDetails(instDetail);
        course.setContactEmail(caffeineEmail);
        course.setContactUrl(caffeineUrl);
        course.setDate(caffeineDate);
        course.setDateInfo(caffeineDateInfo);
        course.setDescription(caffeineDescription);
        course.setHours(caffeineHours);
        course.setMaxEnroll(caffeineMaxEnroll);
        course.setSearchBlob(caffeineSearchBlob);
        course.setSortDate(caffeineSortDate);
        course.setInstitutionDetail(inst);
//        course.setD

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(instDetail);
        session.saveOrUpdate(inst);
        session.saveOrUpdate(course);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
//
//        HibernateManager.getInstance().saveOrUpdate(inst);
//        HibernateManager.getInstance().saveOrUpdate(course);

    }
}
