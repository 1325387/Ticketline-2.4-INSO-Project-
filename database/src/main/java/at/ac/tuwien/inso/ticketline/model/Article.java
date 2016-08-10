package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The article entity.
 */
@Entity
public class Article implements Serializable {

    private static final long serialVersionUID = 6246337708372686917L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 1024)
    private String description;

    @Column(nullable = false)
    private Integer price;

    private Integer available;

    @ManyToOne
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    /**
     * Instantiates a new article.
     */
    public Article() {
    }

    /**
     * Instantiates a new article.
     *
     * @param name the name
     * @param description the description
     * @param price the price
     * @param available the available
     * @param performance the performance
     */
    public Article(String name, String description, Integer price, Integer available, Performance performance) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
        this.performance = performance;
    }

    /**
     * Instantiates a new article.
     *
     * @param id the id
     * @param name the name
     * @param description the description
     * @param price the price
     * @param available the available
     * @param performance the performance
     */
    public Article(Integer id, String name, String description, Integer price, Integer available, Performance performance) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
        this.performance = performance;
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
     * Gets the available.
     *
     * @return the available
     */
    public Integer getAvailable() {
        return available;
    }

    /**
     * Sets the available.
     *
     * @param available the new available
     */
    public void setAvailable(Integer available) {
        this.available = available;
    }

    /**
     * Gets the performance.
     *
     * @return the performance
     */
    public Performance getPerformance() {
        return performance;
    }

    /**
     * Sets the performance.
     *
     * @param performance the new performance
     */
    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

}
