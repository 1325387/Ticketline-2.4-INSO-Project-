package at.ac.tuwien.inso.ticketline.client.extraObjects;

import at.ac.tuwien.inso.ticketline.dto.GalleryDto;
import at.ac.tuwien.inso.ticketline.dto.SeatDto;
import at.ac.tuwien.inso.ticketline.dto.TicketDto;
import javafx.scene.control.CheckBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Lina Wang 1326922
 */
public class ConcertPlanReferencer {

    private CheckBox cb;

    private List<TicketStatus> tickets = new ArrayList<>();

    private List<SeatDto> seats = new ArrayList<>();

    private GalleryDto gallery;

    private double price;

    public ConcertPlanReferencer(CheckBox cb,
                                 List<TicketStatus> tickets,
                                 List<SeatDto> seats,
                                 GalleryDto gallery,
                                 double price) {
        this.cb = cb;
        this.tickets = tickets;
        this.seats = seats;
        this.gallery = gallery;
        this.price = price;
    }

    public CheckBox getCb() {
        return cb;
    }

    public void setCb(CheckBox cb) {
        this.cb = cb;
    }

    public List<TicketStatus> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketStatus> tickets) {
        this.tickets = tickets;
    }

    public List<SeatDto> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatDto> seats) {
        this.seats = seats;
    }

    public GalleryDto getGallery() {
        return gallery;
    }

    public void setGallery(GalleryDto gallery) {
        this.gallery = gallery;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
