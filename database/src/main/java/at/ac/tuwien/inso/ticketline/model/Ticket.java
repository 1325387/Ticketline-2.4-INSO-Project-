package at.ac.tuwien.inso.ticketline.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The ticket entity.
 */
@Entity
public class Ticket implements Serializable {

    private static final long serialVersionUID = 2355163364458707580L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticket", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<TicketIdentifier> ticketIdentifiers;

    /**
     * @author Lina Wang 1326922
     *
     * Empty constructor
     */
    public Ticket(){}

    /**
     * @author Lina Wang 1326922
     *
     * Instantiates a new ticket
     *
     * @param description the description of the ticket
     * @param price the price
     * @param seat the seat
     * @param show the show
     */
    public Ticket(String description, Integer price, Seat seat, Show show) {
        this.description = description;
        this.price = price;
        this.seat = seat;
        this.show = show;
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
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Sets the price.
     *
     * @param price the new price
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * Gets the show.
     *
     * @return the show
     */
    public Show getShow() {
        return show;
    }

    /**
     * Sets the show.
     *
     * @param show the new show
     */
    public void setShow(Show show) {
        this.show = show;
    }

    /**
     * Gets the seat.
     *
     * @return the seat
     */
    public Seat getSeat() {
        return seat;
    }

    /**
     * Sets the seat.
     *
     * @param seat the new seat
     */
    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    /**
     * Gets the ticket identifiers.
     *
     * @return the ticket identifiers
     */
    public List<TicketIdentifier> getTicketIdentifiers() {
        return ticketIdentifiers;
    }

    /**
     * Sets the ticket identifiers.
     *
     * @param ticketIdentifiers the new ticket identifiers
     */
    public void setTicketIdentifiers(List<TicketIdentifier> ticketIdentifiers) { this.ticketIdentifiers = ticketIdentifiers; }

}
