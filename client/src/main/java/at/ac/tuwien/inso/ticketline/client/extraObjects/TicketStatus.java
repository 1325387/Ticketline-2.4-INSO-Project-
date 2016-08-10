package at.ac.tuwien.inso.ticketline.client.extraObjects;

import at.ac.tuwien.inso.ticketline.dto.TicketDto;

/**
 * @author Lina Wang 1326922
 */
public class TicketStatus {

    private TicketDto ticket;

    private String status;

    public TicketStatus(TicketDto ticket, String status) {
        this.ticket = ticket;
        this.status = status;
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
