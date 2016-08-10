package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.*;
import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Seat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Lina Wang 1326922
 */
@Component
public class SeatGenerator implements DataGenerator {


    private static final Logger LOGGER = LoggerFactory.getLogger(SeatGenerator.class);

    @Autowired
    private SeatDao seatDao;

    @Autowired
    private RowDao rowDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private GalleryDao galleryDao;

    /**
     * @author Sissi Zhan 1325880
     */
    @Override
    public void generate() {
        LOGGER.info("+++++ Generate Seat Data +++++");

        List<Room> rooms = this.roomDao.findAll();

        //for every room
        for (int a = 1; a <= rooms.size()-5; a++) {

            int roomsize = (int)(Math.random()*10+1);

            int rowThreshold = ((Long) Math.round(((double) roomsize) / 2)).intValue();
            int sequenceThreshold = ((Long) Math.round(((double) roomsize) / 2)).intValue() + 6;
            int hallway1 = 0;
            int hallway2 = 0;

            if (sequenceThreshold <= 7) {
                hallway1 = ((Long) Math.round(((double) sequenceThreshold) / 2)).intValue();
            } else if (sequenceThreshold > 7) {
                hallway1 = 3;
                hallway2 = sequenceThreshold - 2;
            }

            if (a == 1) {
                seatDao.save(new Seat("Seat row " + 1 + " seq " + 5, "Normal seat", 5, roomDao.findOne(a), categoryDao.findOne(1), rowDao.findOne(1), null));

                seatDao.save(new Seat("Seat row " + 2 + " seq " + 4, "Normal seat", 4, roomDao.findOne(a), categoryDao.findOne(1), rowDao.findOne(2), null));
                seatDao.save(new Seat("Seat row " + 2 + " seq " + 5, "Normal seat", 5, roomDao.findOne(a), categoryDao.findOne(1), rowDao.findOne(2), null));
                seatDao.save(new Seat("Seat row " + 2 + " seq " + 6, "Normal seat", 6, roomDao.findOne(a), categoryDao.findOne(1), rowDao.findOne(2), null));

                seatDao.save(new Seat("Seat row " + 3 + " seq " + 3, "Normal seat", 3, roomDao.findOne(a), categoryDao.findOne(2), rowDao.findOne(3), null));
                seatDao.save(new Seat("Seat row " + 3 + " seq " + 4, "Normal seat", 4, roomDao.findOne(a), categoryDao.findOne(2), rowDao.findOne(3), null));
                seatDao.save(new Seat("Seat row " + 3 + " seq " + 5, "Normal seat", 5, roomDao.findOne(a), categoryDao.findOne(2), rowDao.findOne(3), null));
                seatDao.save(new Seat("Seat row " + 3 + " seq " + 6, "Normal seat", 6, roomDao.findOne(a), categoryDao.findOne(2), rowDao.findOne(3), null));
                seatDao.save(new Seat("Seat row " + 3 + " seq " + 7, "Normal seat", 7, roomDao.findOne(a), categoryDao.findOne(2), rowDao.findOne(3), null));

                seatDao.save(new Seat("Seat row " + 4 + " seq " + 2, "Normal seat", 2, roomDao.findOne(a), categoryDao.findOne(3), rowDao.findOne(4), null));
                seatDao.save(new Seat("Seat row " + 4 + " seq " + 3, "Normal seat", 3, roomDao.findOne(a), categoryDao.findOne(3), rowDao.findOne(4), null));
                seatDao.save(new Seat("Seat row " + 4 + " seq " + 4, "Normal seat", 4, roomDao.findOne(a), categoryDao.findOne(3), rowDao.findOne(4), null));
                seatDao.save(new Seat("Seat row " + 4 + " seq " + 5, "Normal seat", 5, roomDao.findOne(a), categoryDao.findOne(3), rowDao.findOne(4), null));
                seatDao.save(new Seat("Seat row " + 4 + " seq " + 6, "Normal seat", 6, roomDao.findOne(a), categoryDao.findOne(3), rowDao.findOne(4), null));
                seatDao.save(new Seat("Seat row " + 4 + " seq " + 7, "Normal seat", 7, roomDao.findOne(a), categoryDao.findOne(3), rowDao.findOne(4), null));
                seatDao.save(new Seat("Seat row " + 4 + " seq " + 8, "Normal seat", 8, roomDao.findOne(a), categoryDao.findOne(3), rowDao.findOne(4), null));


            } else {
                //for every row
                for (int i = 1; i <= rowThreshold; i++) {
                    //for every seat in row
                    for (int j = 1; j <= sequenceThreshold; j++) {
                        //no seats when hallway
                        if (!(j == hallway1 || j == hallway2)) {
                            //Economy seats (first two rows)
                            if (i <= 2) {
                                seatDao.save(new Seat("Seat row " + i + " seq " + j, "Normal seat", j, roomDao.findOne(a), categoryDao.findOne(1), rowDao.findOne(i), null));
                            }
                            //Normal seats
                            else if (i <= (rowThreshold - 2)) {
                                seatDao.save(new Seat("Seat row " + i + " seq " + j, "Normal seat", j, roomDao.findOne(a), categoryDao.findOne(2), rowDao.findOne(i), null));
                            }
                            //VIP seats (last two rows)
                            else {
                                seatDao.save(new Seat("Seat row " + i + " seq " + j, "Normal seat", j, roomDao.findOne(a), categoryDao.findOne(3), rowDao.findOne(i), null));
                            }
                        }
                    }
                }
            }
        }


        //For Concerts
        //We got side, middle and back gallery
        //For each gallery, generate seats
        //side: each 25, middle: 30, back: 10;
        int sideCount = 0;
        int middleCount = 0;
        int backCount = 0;
        for (int room = rooms.size()-4; room <= rooms.size(); room++) {
            for (int gallery = 1; gallery <= 4; gallery++) {
                for (int row = 1; row <= 5; row++) {
                    for (int seq = 1; seq <= 5; seq++) {
                        seatDao.save(new Seat("Seat row " + row + " seq " + seq, "Seat in side gallery", seq, roomDao.findOne(room), categoryDao.findOne(2), rowDao.findOne(row), galleryDao.findOne(1)));
                        sideCount++;
                    }
                }
            }

            for (int row = 1; row <= 5; row++) {
                for (int seq = 1; seq <= 6; seq++) {
                    seatDao.save(new Seat("Seat row " + row + " seq " + seq, "Seat in middle gallery", seq, roomDao.findOne(room), categoryDao.findOne(1), rowDao.findOne(row), galleryDao.findOne(2)));
                    middleCount++;
                }
            }

            for (int row = 1; row <= 2; row++) {
                for (int seq = 1; seq <= 5; seq++) {
                    seatDao.save(new Seat("Seat row " + row + " seq " + seq, "Seat in back gallery", seq, roomDao.findOne(room), categoryDao.findOne(3), rowDao.findOne(row), galleryDao.findOne(3)));
                    backCount++;
                }
            }
        }
    }
}
