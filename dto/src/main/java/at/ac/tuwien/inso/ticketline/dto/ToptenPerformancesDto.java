package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * @author Aysel Oeztuer 0927011
 */
@ApiModel(value = "ToptenDto", description = "Data transfer object for a top10")
public class ToptenPerformancesDto {

    @ApiModelProperty(value = "ID of topten")
    private int id;

    @NotNull
    @ApiModelProperty(value = "ID of Performance")
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    /**
     * Gets the performanceID.
     *
     * @return the performanceID
     */
    public Integer getEventID() {
        return eventID;
    }

    /**
     * Sets the performanceID.
     *
     * @param eventID the new performanceID
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


}
