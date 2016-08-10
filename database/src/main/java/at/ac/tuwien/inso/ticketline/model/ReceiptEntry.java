package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The entry entity.
 */
@Entity
public class ReceiptEntry implements Serializable {

    private static final long serialVersionUID = 2151550295868898930L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer position;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Integer unitPrice;

    @ManyToOne
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receipt;

    @OneToOne
    @JoinColumn(name = "ticketidentifier_id", nullable = true)
    private TicketIdentifier ticketIdentifier;

    /**
     * @author Lina Wang 1326922
     *
     * Empty constructor
     */
    public ReceiptEntry(){}

    /**
     * @author Lina Wang 1326922
     *
     * @param position
     * @param amount
     * @param unitPrice
     * @param receipt
     * @param ticketIdentifier
     */
    public ReceiptEntry(Integer position, Integer amount, Integer unitPrice, Receipt receipt, TicketIdentifier ticketIdentifier) {
        this.position = position;
        this.amount = amount;
        this.unitPrice = unitPrice;
        this.receipt = receipt;
        this.ticketIdentifier = ticketIdentifier;
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
     * Gets the position.
     *
     * @return the position
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * Sets the position.
     *
     * @param position the new position
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * Gets the amount.
     *
     * @return the amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets the amount.
     *
     * @param amount the new amount
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * Gets the unit price.
     *
     * @return the unit price
     */
    public Integer getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the unit price.
     *
     * @param unitPrice the new unit price
     */
    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Gets the receipt.
     *
     * @return the receipt
     */
    public Receipt getReceipt() {
        return receipt;
    }

    /**
     * Sets the receipt.
     *
     * @param receipt the new receipt
     */
    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    /**
     * Gets the ticket identifier.
     *
     * @return the ticket identifier
     */
    public TicketIdentifier getTicketIdentifier() {
        return ticketIdentifier;
    }

    /**
     * Sets the ticket identifier.
     *
     * @param ticketIdentifier the new ticket identifier
     */
    public void setTicketIdentifier(TicketIdentifier ticketIdentifier) {
        this.ticketIdentifier = ticketIdentifier;
    }

}
