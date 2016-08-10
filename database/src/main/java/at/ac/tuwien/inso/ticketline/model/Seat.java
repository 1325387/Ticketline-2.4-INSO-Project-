package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The seat entity.
 */
@Entity
public class Seat implements Serializable {

    private static final long serialVersionUID = -5630875224592433118L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @Column(nullable = false, name = "sequence")
    private Integer sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "row_id", nullable = false)
    private Row row;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gallery_id", nullable = true)
    private Gallery gallery;

    /**
     * @author Lina Wang 1326922
     *
     * Empty construstor
     */
    public Seat(){}

    /**
     * @author Lina Wang 1326922
     *
     * @param name
     * @param description
     * @param sequence
     * @param room
     * @param category
     * @param row
     * @param gallery
     */
    public Seat(String name, String description, Integer sequence, Room room, Category category, Row row, Gallery gallery) {
        this.name = name;
        this.description = description;
        this.sequence = sequence;
        this.room = room;
        this.category = category;
        this.row = row;
        this.gallery = gallery;
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
     * Gets the room.
     *
     * @return the room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room.
     *
     * @param room the new room
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Gets the category.
     *
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the category.
     *
     * @param category the new category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Gets the row.
     *
     * @return the row
     */
    public Row getRow() {
        return row;
    }

    /**
     * Sets the row.
     *
     * @param row the new row
     */
    public void setRow(Row row) {
        this.row = row;
    }

    /**
     * Gets the gallery.
     *
     * @return the gallery
     */
    public Gallery getGallery() {
        return gallery;
    }

    /**
     * Sets the gallery.
     *
     * @param gallery the new gallery
     */
    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

}
