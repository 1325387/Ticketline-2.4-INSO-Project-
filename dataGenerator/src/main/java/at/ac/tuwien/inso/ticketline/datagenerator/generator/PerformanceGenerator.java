package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.PerformanceDao;
import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.model.PerformanceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class generates data for performance.
 * <p>
 * Created by Aysel Oeztuerk on 02.05.2016.
 */
@Component
public class PerformanceGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceGenerator.class);

    @Autowired
    private PerformanceDao performanceDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void generate() {
        LOGGER.info("+++++ Generate Performance Data +++++");

        performanceDao.save(new Performance("Angry Birds",
                "In diesem 3D Film wir endlich heraus, warum die Voegel so sauer sind.",
                120, PerformanceType.MOVIE));
        performanceDao.save(new Performance("Jurassic World",
                "JURASSIC WORLD ist der langerwarteter vierter Teil der bahnbrechenden Kinoreihe.",
                138, PerformanceType.MOVIE));
        performanceDao.save(new Performance("Batman v Superman: Dawn Of Justice",
                "Ein actionreicher Film",
                105, PerformanceType.MOVIE));
        performanceDao.save(new Performance("Adam und Eva",
                "Viel Liebe",
                160, PerformanceType.THEATER));
        performanceDao.save(new Performance("Faust",
                "Eine Tragödie.",
                210, PerformanceType.THEATER));
        performanceDao.save(new Performance("König der Löwen",
                "Naaasipeeenjaaa",
                85, PerformanceType.MUSICAL));
        performanceDao.save(new Performance("High School Musical: The Musical Of High School",
                "Viel strebern und so",
                65, PerformanceType.MUSICAL));
        performanceDao.save(new Performance("Grease",
                "tell me more tell me more",
                128, PerformanceType.MUSICAL));
        performanceDao.save(new Performance("Die Zauberflöte",
                "trölölö",
                80, PerformanceType.OPER));
        performanceDao.save(new Performance("Der fliegende Holländer",
                "Nicht der aus Spongebob",
                35, PerformanceType.OPER));

        //Generating concerts
        performanceDao.save(new Performance("Rihanna's Konzert",
                "Anti World Tour 2016",
                120, PerformanceType.CONCERT));
        performanceDao.save(new Performance("Justin Bieber's Konzert",
                "Purpose World Tour 2016",
                120, PerformanceType.CONCERT));
        performanceDao.save(new Performance("Taylor Swift's Konzert",
                "Live Concert",
                100, PerformanceType.CONCERT));
        performanceDao.save(new Performance("The Weeknd",
                "Live Concert",
                100, PerformanceType.CONCERT));
        performanceDao.save(new Performance("LangLang",
                "Music Concert",
                100, PerformanceType.CONCERT));

        for (int i = 1; i <= 50; i++) {

            if (i <= 10) {
                performanceDao.save(new Performance("Performance " + i, "a movie", 18, PerformanceType.MOVIE));
            }
            else if (i <= 20) {
                performanceDao.save(new Performance("Performance " + i, "an oper", 40, PerformanceType.OPER));
            }
            else if (i <= 30) {
                performanceDao.save(new Performance("Performance " + i, "a theater", 60, PerformanceType.THEATER));
            }
            else if (i <= 40) {
                performanceDao.save(new Performance("Performance " + i, "a musical", 80, PerformanceType.MUSICAL));
            }
            else if (i <= 50) {
                performanceDao.save(new Performance("Performance " + i, "a concert", 110, PerformanceType.CONCERT));
            }
        }
    }
}
