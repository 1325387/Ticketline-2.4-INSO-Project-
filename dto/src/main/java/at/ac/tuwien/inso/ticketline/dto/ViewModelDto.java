package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import com.wordnik.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;

/**
 * @author Lina Wang 1326922
 */
@ApiModel(value="ViewModelDto")
public class ViewModelDto {

    @ApiModelProperty(value="the id of ticket")
    private int ticketId;

    @ApiModelProperty(value="the number of the customer" )
    private int customerNumber;

    @ApiModelProperty(value="the name of the show" )
    private String showName;

    @ApiModelProperty(value="the seat number")
    private int seatNumber;

    @ApiModelProperty(value="the price of the ticket")
    private double price;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ApiModelProperty(value ="status of the ticket")
    private String status;

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }
}


