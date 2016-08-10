package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.ArtistDao;
import at.ac.tuwien.inso.ticketline.dao.ParticipationDao;
import at.ac.tuwien.inso.ticketline.dao.PerformanceDao;
import at.ac.tuwien.inso.ticketline.model.Participation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sissi Zhan 1325880
 */
@Component
public class ParticipationGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtistGenerator.class);

    @Autowired
    ParticipationDao participationDao;
    @Autowired
    PerformanceDao performanceDao;
    @Autowired
    ArtistDao artistDao;

    @Override
    public void generate() {
        LOGGER.info("+++++ Generate Participation Data +++++");

        participationDao.save(new Participation(1, "Singer", "Description1", performanceDao.findOne(11), artistDao.findOne(1)));
        participationDao.save(new Participation(2, "Singer", "Description2", performanceDao.findOne(12), artistDao.findOne(2)));
        participationDao.save(new Participation(3, "Singer", "Description3", performanceDao.findOne(13), artistDao.findOne(3)));
        participationDao.save(new Participation(4, "Singer", "Description4", performanceDao.findOne(14), artistDao.findOne(4)));
        participationDao.save(new Participation(5, "Singer", "Description5", performanceDao.findOne(15), artistDao.findOne(5)));

        participationDao.save(new Participation(6, "Singer", "Description", performanceDao.findOne(55), artistDao.findOne(6)));
        participationDao.save(new Participation(7, "Singer", "Description", performanceDao.findOne(56), artistDao.findOne(7)));
        participationDao.save(new Participation(8, "Singer", "Description", performanceDao.findOne(57), artistDao.findOne(3)));
        participationDao.save(new Participation(9, "Singer", "Description", performanceDao.findOne(58), artistDao.findOne(9)));
        participationDao.save(new Participation(10, "Singer", "Description", performanceDao.findOne(59), artistDao.findOne(6)));

        participationDao.save(new Participation(11, "Singer", "Description", performanceDao.findOne(60), artistDao.findOne(11)));
        participationDao.save(new Participation(12, "Singer", "Description", performanceDao.findOne(61), artistDao.findOne(3)));
        participationDao.save(new Participation(13, "Singer", "Description", performanceDao.findOne(62), artistDao.findOne(13)));
        participationDao.save(new Participation(14, "Singer", "Description", performanceDao.findOne(63), artistDao.findOne(1)));
        participationDao.save(new Participation(15, "Singer", "Description", performanceDao.findOne(64), artistDao.findOne(14)));
        participationDao.save(new Participation(15, "Singer", "Description", performanceDao.findOne(65), artistDao.findOne(15)));

    }
}
