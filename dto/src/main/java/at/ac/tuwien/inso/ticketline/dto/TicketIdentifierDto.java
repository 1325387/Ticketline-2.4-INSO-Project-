package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Lina Wang 1326922
 *
 * Data transer object for ticket identifier
 */
@ApiModel(value = "TicketIdentifierDto", description = "Data transfer object for ticket identifier")
public class TicketIdentifierDto {

    @NotNull
    @ApiModelProperty(value = "ID of ticket identifier")
    private int id;

    @ApiModelProperty(value = "The reason of cancellation")
    @Size(min = 5, max = 255)
    private String cancellationReason;

    @NotNull
    @ApiModelProperty (value = "If ticket is valid")
    private boolean valid;

    @NotNull
    @ApiModelProperty (value = "Timestamp of ticket")
    private Date voidationTime;

    @NotNull
    @ApiModelProperty (value = "Voided by")
    private String voidedBy;

    @ApiModelProperty(value = "reservation ID")
    private int reservationID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Date getVoidationTime() {
        return voidationTime;
    }

    public void setVoidationTime(Date voidationTime) {
        this.voidationTime = voidationTime;
    }

    public String getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(String voidedBy) {
        this.voidedBy = voidedBy;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }
}
