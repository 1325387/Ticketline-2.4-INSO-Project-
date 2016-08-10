package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The room entity.
 */
@Entity
public class Room implements Serializable {

    private static final long serialVersionUID = -668105317259567139L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String name;

    @Column(length = 1024)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private List<Show> shows;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private List<Seat> seats;

    /**
     * Instantiates a new room.
     */
    public Room() {
    }

    /**
     * Instantiates a new room.
     *
     * @param name the name
     * @param description the description
     * @param location the location
     */
    public Room(String name, String description, Location location) {
        this.name = name;
        this.description = description;
        this.location = location;
    }

    /**
     * Instantiates a new room.
     *
     * @param id the id
     * @param name the name
     * @param description the description
     * @param location the location
     */
    public Room(Integer id, String name, String description, Location location) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
    }

    /**
     * Instantiates a new room.
     *
     * @param id the id
     * @param name the name
     * @param description the description
     * @param location the location
     * @param shows the shows
     * @param seats the seats
     */
    public Room(Integer id, String name, String description, Location location, List<Show> shows, List<Seat> seats) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.shows = shows;
        this.seats = seats;
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
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
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
     * Gets the shows.
     *
     * @return the shows
     */
    public List<Show> getShows() {
        return shows;
    }

    /**
     * Sets the shows.
     *
     * @param shows the new shows
     */
    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location.
     *
     * @param location the new location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets the seats.
     *
     * @return the seats
     */
    public List<Seat> getSeats() {
        return seats;
    }

    /**
     * Sets the seats.
     *
     * @param seats the new seats
     */
    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

}
