package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.PerformanceDao;
import at.ac.tuwien.inso.ticketline.dao.RoomDao;
import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.model.PerformanceType;
import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Show;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.ac.tuwien.inso.ticketline.dao.ShowDao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

/**
 * This class generates data for show.
 * <p>
 * Created by Aysel Oeztuerk on 02.05.2016.
 */
@Component
public class ShowGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowGenerator.class);


    @Autowired
    private ShowDao showDao;

    @Autowired
    private PerformanceDao performanceDao;

    @Autowired
    private RoomDao roomDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void generate() {
        LOGGER.info("+++++ Generate Show Data +++++");

        List<Performance> performances = this.performanceDao.findAll();
        List<Room> rooms = this.roomDao.findAll();
        int roomsize = rooms.size();

        int randomRoom = 0;
        int randomTime = 0;
        int randomDay = 0;

        Iterator it = performances.iterator();
        while (it.hasNext()) {
            Performance p = (Performance) it.next();

            //15 shows per performance
            for (int i = 1; i <= 15; i++) {

                if (p.getPerformanceType() != PerformanceType.CONCERT) {
                    randomRoom = (int) ((Math.random() * 30) + 1);
                } else { //If concert -> random concert hall (last 5 rooms)
                    randomRoom = 31 + (int)(Math.random() * ((35 - 31) + 1));
                }

                if (p.getId() == 4) { //Performance 4 is in the special triangle room
                    randomRoom = 1;
                }

                randomTime = (int) ((Math.random() * 23) + 1);
                randomDay = (int) ((Math.random() * 50) + 1);

                Calendar calendar = GregorianCalendar.from(ZonedDateTime.now().plusDays(randomDay).plusHours(randomTime));
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Date date = calendar.getTime();

                showDao.save(new Show(false, date, roomDao.findOne(randomRoom), p));
            }
        }

        /*
        //Performances
        for (int i = 1; i <= 10; i++) {
            randomRoom = (int) ((Math.random() * roomsize) + 1);
            randomTime = (int) ((Math.random() * 23) + 1);
            randomDay = (int) ((Math.random() * 50) + 1);

            Calendar calendar = GregorianCalendar.from(ZonedDateTime.now().minusDays(45).plusHours(j / 3));
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date date = calendar.getTime();

            showDao.save(new Show(false, date,
                    roomDao.findOne(j), performanceDao.findOne(i)));
        }
        */


        /*

        try {

            //Performances
            for (int i = 1; i <= 10; i++) {

                //Rooms
                for (int j = 1; j <= 10; j++) {

                    Calendar calendar = GregorianCalendar.from(ZonedDateTime.now().plusDays(j * 2).plusHours(j/3));
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    Date date = calendar.getTime();

                    showDao.save(new Show(false, date,
                            roomDao.findOne(j), performanceDao.findOne(i)));
                }
            }

            //For Concerts
            for (int a = 11; a <= 15; a++) {
                //Rooms
                for (int b = 11; b <= 15; b++) {

                    Calendar calendar = GregorianCalendar.from(ZonedDateTime.now().plusDays(b * 2).plusHours(b/3));
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    Date date = calendar.getTime();

                    showDao.save(new Show(false, date,
                            roomDao.findOne(b), performanceDao.findOne(a)));
                }
            }
        } catch (Exception e) {
            LOGGER.info("Parseexception: " + e.getMessage());
        }

        */
    }
}
