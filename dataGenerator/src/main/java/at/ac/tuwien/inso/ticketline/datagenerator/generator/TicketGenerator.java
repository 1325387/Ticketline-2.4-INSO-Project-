package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.*;
import at.ac.tuwien.inso.ticketline.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class generates data for ticket.
 *
 * @author Lina Wang 1326922, Sissi Zhan 1325880
 */
@Component
public class TicketGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketGenerator.class);

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private ShowDao showDao;

    @Autowired
    private SeatDao seatDao;

    @Autowired
    private PerformanceDao performanceDao;

    @Autowired
    private GalleryDao galleryDao;

    /**
     * @author Sissi Zhan 1325880
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void generate() {
        LOGGER.info("+++++ Generate Ticket Data +++++");

        List<Performance> performanceList = this.performanceDao.findAll();
        //for every performance
        for (Performance performance : performanceList) {
            List<Show> showsOfPerformance = this.showDao.findByPerformanceId(performance.getId());
            PerformanceType performanceType = performance.getPerformanceType();
            //for every show
            for (Show show : showsOfPerformance) {
                Room room = show.getRoom();
                List<Seat> seats = this.seatDao.findAllOf(show.getId());
                //find room size
                int maxRoomSeq = 1;
                for (Seat seat : seats) {
                    if (seat.getRow().getSequence() > maxRoomSeq) {
                        maxRoomSeq = seat.getRow().getSequence();
                    }
                }

                //for every seats
                for (Seat seat : seats) {
                    int rowSequence = seat.getRow().getSequence();

                    if (performanceType == performanceType.CONCERT) { //if concert
                        int gallery = (this.galleryDao.findBySeat(seat.getId())).getId();
                        //For side gallery
                        if (gallery == 1) {
                            ticketDao.save(new Ticket("This is an ticket in the side gallery for Performance. Nr " + performance.getId() + ", Show Nr. " + show.getId(), 50, seat, show));
                        }
                        //For middle gallery
                        if (gallery == 2) {
                            ticketDao.save(new Ticket("This is an ticket in the middle gallery for Performance. Nr " + performance.getId() + ", Show Nr. " + show.getId(), 80, seat, show));
                        }
                        //For back gallery
                        if (gallery == 3) {
                            ticketDao.save(new Ticket("This is an ticket in the back gallery for Performance. Nr " + performance.getId() + ", Show Nr. " + show.getId(), 35, seat, show));
                        }

                    } else { //not concert
                        //Economy seats (first two rows)
                        if (rowSequence <= 2) {
                            ticketDao.save(new Ticket("This is an economy ticket for Performance. Nr " + performance.getId() + ", Show Nr. " + show.getId(), 5, seat, show));
                        }
                        //Normal seats
                        else if (rowSequence <= (maxRoomSeq - 2)) {
                            ticketDao.save(new Ticket("This is a standard ticket for Performance. Nr " + performance.getId() + ", Show Nr. " + show.getId(), 12, seat, show));

                        }
                        //VIP seats (last two rows)
                        else {
                            ticketDao.save(new Ticket("This is a VIP ticket for Performance. Nr " + performance.getId() + ", Show Nr. " + show.getId(), 25, seat, show));

                        }
                    }
                }
            }
        }
    }
}
