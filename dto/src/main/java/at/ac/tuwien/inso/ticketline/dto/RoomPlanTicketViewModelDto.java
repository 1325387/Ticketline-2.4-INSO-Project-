package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Raphael Schotola 1225193
 */
public class RoomPlanTicketViewModelDto {
    @ApiModelProperty(value="the id of seat")
    private int seatId;

    @ApiModelProperty(value="the price of the ticket")
    private double price;

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
