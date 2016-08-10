package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Raphael Schotola 1225193
 */
@ApiModel(value = "StripeDto", description = "Data transfer object for a stripe transaction")
public class StripeDto {
    @NotNull
    @ApiModelProperty(value = "amount in Cent", required = true)
    private int amountInCent;

    @NotNull
    @ApiModelProperty(value = "card number", required = true)
    private String cardNumber;

    @NotNull
    @ApiModelProperty(value = "expire month", required = true)
    private int expMonth;

    @NotNull
    @ApiModelProperty(value = "expire year", required = true)
    private int expYear;

    @ApiModelProperty(value = "customer", required = true)
    private CustomerDto customer;

    @ApiModelProperty(value = "tickets", required = true)
    private List<TicketDto> tickets;

    public List<TicketDto> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketDto> tickets) {
        this.tickets = tickets;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public int getAmountInCent() {
        return amountInCent;
    }

    public void setAmountInCent(int amountInCent) {
        this.amountInCent = amountInCent;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(int expMonth) {
        this.expMonth = expMonth;
    }

    public int getExpYear() {
        return expYear;
    }

    public void setExpYear(int expYear) {
        this.expYear = expYear;
    }
}
