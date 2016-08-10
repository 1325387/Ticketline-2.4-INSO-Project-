package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The topten entity.
 * <p>
 * Created by Aysel Öztürk 0927011.
 */
@Entity
public class Topten implements Serializable {

    private static final long serialVersionUID = 1112362271784822217L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer eventID;

    @Column(nullable = false)
    private Long soldTickets;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 1024)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /**
     * Instantiates a new topten.
     */
    public Topten() {
    }

    /**
     * For test purposes:
     * Instantiates a new topten.
     *
     * @param id
     */
    public Topten(Integer id) {
        this.eventID = id;
        this.soldTickets = (long) id;
    }

    /**
     * Instantiates a new topten.
     *
     * @param id
     */
    public Topten(Integer id, Long count) {
        this.eventID = id;
        this.soldTickets = count;
    }

    /**
     * Instantiates a new topten.
     *
     * @param id
     */
    public Topten(Integer id, Long count, String name, String description, Date date) {
        this.eventID = id;
        this.soldTickets = count;
        this.name = name;
        this.description = description;
        this.date = date;
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
     * Gets the showID.
     *
     * @return the showID
     */
    public Integer getEventID() {
        return eventID;
    }

    /**
     * Sets the showID.
     *
     * @param eventID the new showID
     */
    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    /**
     * Gets the soldTickets.
     *
     * @return the soldTickets
     */
    public Long getSoldTickets() {
        return soldTickets;
    }

    /**
     * Sets the soldTickets.
     *
     * @param soldTickets the new soldTickets
     */
    public void setSoldTickets(Long soldTickets) {
        this.soldTickets = soldTickets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
