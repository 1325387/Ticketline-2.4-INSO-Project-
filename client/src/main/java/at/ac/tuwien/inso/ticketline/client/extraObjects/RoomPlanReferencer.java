package at.ac.tuwien.inso.ticketline.client.extraObjects;

import at.ac.tuwien.inso.ticketline.dto.SeatDto;
import at.ac.tuwien.inso.ticketline.dto.TicketDto;
import javafx.scene.control.CheckBox;

/**
 * @author Raphael Schotola 1225193
 */
public class RoomPlanReferencer {
    public RoomPlanReferencer(CheckBox checkBox, SeatDto seat, TicketDto ticket, String status){
        this.checkBox = checkBox;
        this.seat = seat;
        this.ticket = ticket;
        this.status = status;
    }

    private CheckBox checkBox;
    private SeatDto seat;
    private TicketDto ticket;
    //may only be available, reserved or sold
    private String status;

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public SeatDto getSeat() {
        return seat;
    }

    public void setSeat(SeatDto seat) {
        this.seat = seat;
    }

    public TicketDto getTicket() {
        return ticket;
    }

    public void setTicket(TicketDto ticket) {
        this.ticket = ticket;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
