package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * @author Aysel Oeztuer 0927011
 */
@ApiModel(value = "ToptenDto", description = "Data transfer object for a top10")
public class ToptenDto {

    @ApiModelProperty(value = "ID of topten")
    private int id;

    @NotNull
    @ApiModelProperty(value = "ID of Show")
    private Integer eventID;

    @NotNull
    @ApiModelProperty(value = "count of sold Tickets of the show")
    private Long soldTickets;

    @NotNull
    @ApiModelProperty(value = "Name of the performance")
    private String name;

    @NotNull
    @ApiModelProperty(value = "Description of the performance")
    private String description;

    @NotNull
    @ApiModelProperty(value = "Date of the show")
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
