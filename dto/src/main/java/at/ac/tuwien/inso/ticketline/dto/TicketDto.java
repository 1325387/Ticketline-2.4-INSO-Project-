package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Sissi Zhan 1325880
 *
 * Data transfer object for a ticket
 */
@ApiModel(value = "TicketDto", description = "Data transfer object for a ticket")
public class TicketDto {

    @NotNull
    @ApiModelProperty(value = "ID of ticket")
    private int id;

    @ApiModelProperty(value = "Description of ticket")
    @Size(min = 5, max = 255)
    private String description;

    @NotNull
    @ApiModelProperty(value = "Price of ticket", required = true)
    private double price;

    @NotNull
    @ApiModelProperty(value = "SeatID of the ticket")
    private int seatId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }
}
