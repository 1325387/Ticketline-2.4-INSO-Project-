package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * The ticket identifier entity.
 */
@Entity
public class TicketIdentifier implements Serializable {

    private static final long serialVersionUID = -6943821226312476281L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
    @Column(unique = true, columnDefinition = "UUID")
    private UUID uuid;
    */
    @Column(nullable = false)
    private Boolean valid;

    @Column(nullable = true)
    private String cancellationReason;

    @Column(nullable = true)
    private String voidedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date voidationTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_id", nullable = true)
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @OneToOne(mappedBy = "ticketIdentifier", fetch = FetchType.EAGER)
    private ReceiptEntry receiptEntry;


    public TicketIdentifier(){}

    public TicketIdentifier(Ticket ticket, Boolean valid, String cancellationReason, String voidedBy, Date voidationTime, Reservation reservation) {
        this.ticket = ticket;
        this.valid = valid;
        this.cancellationReason = cancellationReason;
        this.voidedBy = voidedBy;
        this.voidationTime = voidationTime;
        this.reservation = reservation;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Integer id) {
        this.id = id;
    }


    /**
     * Gets the valid.
     *
     * @return the valid
     */
    public Boolean getValid() {
        return valid;
    }

    /**
     * Sets the valid.
     *
     * @param valid the new valid
     */
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    /**
     * Gets the cancellation reason.
     *
     * @return the cancellation reason
     */
    public String getCancellationReason() {
        return cancellationReason;
    }

    /**
     * Sets the cancellation reason.
     *
     * @param cancellationReason the new cancellation reason
     */
    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    /**
     * Gets the voided by.
     *
     * @return the voided by
     */
    public String getVoidedBy() {
        return voidedBy;
    }

    /**
     * Sets the voided by.
     *
     * @param voidedBy the new voided by
     */
    public void setVoidedBy(String voidedBy) {
        this.voidedBy = voidedBy;
    }

    /**
     * Gets the voidation time.
     *
     * @return the voidation time
     */
    public Date getVoidationTime() {
        return voidationTime;
    }

    /**
     * Sets the voidation time.
     *
     * @param voidationTime the new voidation time
     */
    public void setVoidationTime(Date voidationTime) {
        this.voidationTime = voidationTime;
    }

    /**
     * Gets the reservation.
     *
     * @return the reservation
     */
    public Reservation getReservation() {
        return reservation;
    }

    /**
     * Sets the reservation.
     *
     * @param reservation the new reservation
     */
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    /**
     * Gets the ticket.
     *
     * @return the ticket
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * Sets the ticket.
     *
     * @param ticket the new ticket
     */
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    /**
     * Gets the receipt entry.
     *
     * @return the receipt entry
     */
    public ReceiptEntry getReceiptEntry() {
        return receiptEntry;
    }

    /**
     * Sets the receipt entry.
     *
     * @param receiptEntry the new receipt entry
     */
    public void setReceiptEntry(ReceiptEntry receiptEntry) {
        this.receiptEntry = receiptEntry;
    }

}
