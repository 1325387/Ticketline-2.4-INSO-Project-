package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.ArtistDao;
import at.ac.tuwien.inso.ticketline.model.Artist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sissi Zhan 1325880
 */
@Component
public class ArtistGenerator implements DataGenerator{
    private static final Logger LOGGER = LoggerFactory.getLogger(ArtistGenerator.class);

    @Autowired
    ArtistDao artistDao;

    @Override
    public void generate() {

        LOGGER.info("+++++ Generate Artist Data +++++");

        artistDao.save(new Artist(1, "Rihanna", "", "A singer"));
        artistDao.save(new Artist(2, "Justin", "Bieber", "A singer"));
        artistDao.save(new Artist(3, "Taylor", "Swift", "A singer"));
        artistDao.save(new Artist(4, "The Weeknd", "", "A singer"));
        artistDao.save(new Artist(5, "Lang Lang", "", "A pianist"));
        artistDao.save(new Artist(6, "Artist firstnameA", "Artist lastnameA", "An artist"));
        artistDao.save(new Artist(7, "Artist firstnameB", "Artist lastnameB", "A singer"));
        artistDao.save(new Artist(8, "Artist firstnameC", "Artist lastnameC", "A singer"));
        artistDao.save(new Artist(9, "Artist firstnameD", "Artist lastnameD", "A singer"));
        artistDao.save(new Artist(10, "Artist firstnameE", "Artist lastnameE", "A pianist"));
        artistDao.save(new Artist(11, "Artist firstnameF", "Artist lastnameF", "A singer"));
        artistDao.save(new Artist(12, "Artist firstnameG", "Artist lastnameG", "An artist"));
        artistDao.save(new Artist(13, "Artist firstnameH", "Artist lastnameH", "A singer"));
        artistDao.save(new Artist(14, "Artist firstnameI", "Artist lastnameI", "A singer"));
        artistDao.save(new Artist(15, "Artist firstnameJ", "Artist lastnameJ", "A pianist"));

    }
}
