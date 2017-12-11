package org.catalyst.courses.gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.courses.entities.Competency;
import org.catalyst.courses.entities.Sponsor;
import org.catalyst.courses.entities.Translation;
import org.catalyst.courses.gwt.rpc.CourseService;
import org.catalyst.services.HibernateManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CourseServlet extends RemoteServiceServlet implements CourseService {
    private final static Logger logger = LogManager.getLogger(CourseServlet.class);

    private final HibernateManager hibernateManager = HibernateManager.getInstance();
    
    public Map<Integer,String> getCompetencies() {
        List<Competency> competencies = hibernateManager.getAllEntities(Competency.class);
        return competencies.stream().collect(Collectors.toMap(Competency::getId, Competency::getName));
    }

    public Map<Integer, String> getTranslations() {
        List<Translation> translations = hibernateManager.getAllEntities(Translation.class);
        return translations.stream().collect(Collectors.toMap(Translation::getId, Translation::getName));
    }

    public Map<Integer, String> getSponsors() {
        List<Sponsor> sponsors = hibernateManager.getAllEntities(Sponsor.class);
        return sponsors.stream().collect(Collectors.toMap(Sponsor::getId, Sponsor::getName));
    }

    public String getLastUpdated() {
        return "2025-92-12";
    }
}
