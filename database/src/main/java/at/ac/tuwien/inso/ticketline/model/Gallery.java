package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The gallery entity.
 */
@Entity
public class Gallery implements Serializable {

    private static final long serialVersionUID = 1119065271784822587L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @Column(nullable = false, name = "sequence")
    private Integer sequence;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gallery")
    private List<Seat> seats;

    /**
     *  @author Lina Wang 1326922
     *
     *  Empty constructor
     */
    public Gallery(){}

    /**
     * @author Lina Wang 1326922
     *
     * @param name
     * @param description
     * @param sequence
     */
    public Gallery(String name, String description, Integer sequence) {
        this.name = name;
        this.description = description;
        this.sequence = sequence;
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
     * Gets the sequence.
     *
     * @return the sequence
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * Sets the sequence.
     *
     * @param sequence the new sequence
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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
