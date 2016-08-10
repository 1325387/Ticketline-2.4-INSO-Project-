package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The category entity.
 */
@Entity
public class Category implements Serializable {
    private static final long serialVersionUID = 8419325347256879562L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<Seat> seats;

    /**
     *  @author Lina Wang 1326922
     *
     *  Empty constructor
     */
    public Category(){}

    /**
     * @author Lina Wang 1326922
     *
     * @param name
     * @param description
     */
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
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
     * Gets the seat.
     *
     * @return the seat
     */
    public List<Seat> getSeat() {
        return seats;
    }

    /**
     * Sets the seat.
     *
     * @param seats the new seat
     */
    public void setSeat(List<Seat> seats) {
        this.seats = seats;
    }
}
