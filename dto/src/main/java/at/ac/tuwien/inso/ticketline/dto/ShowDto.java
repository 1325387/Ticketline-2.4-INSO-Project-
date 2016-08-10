package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Raphael Schotola 1225193
 */
@ApiModel(value = "ShowDto", description = "Data transfer object for a show")
public class ShowDto {

    @NotNull
    @ApiModelProperty(value = "ID of Show")
    private int id;

    @NotNull
    @ApiModelProperty(value = "Canceled", required = true)
    private boolean canceled;

    @NotNull
    @ApiModelProperty(value = "Date of Show", required = true)
    private Date dateOfPerformance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public Date getDateOfPerformance() {
        return dateOfPerformance;
    }

    public void setDateOfPerformance(Date dateOfPerformance) {
        this.dateOfPerformance = dateOfPerformance;
    }
}
