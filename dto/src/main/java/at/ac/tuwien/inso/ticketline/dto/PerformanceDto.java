package at.ac.tuwien.inso.ticketline.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author Sissi Zhan 1325880
 */
@ApiModel(value = "PerformanceDto", description = "Data transfer object for an performance")
public class PerformanceDto {

    @NotNull
    @ApiModelProperty(value = "ID of Performance")
    private int id;

    @NotNull
    @Size(min = 1, max = 50)
    @ApiModelProperty(value = "Name of performance", required = true)
    private String name;

    @NotNull
    @Size(min = 5)
    @ApiModelProperty(value = "Description of performance", required = true)
    private String description;

    @NotNull
    @ApiModelProperty(value = "Duration of performance", required = true)
    private int duration;

    @NotNull
    @Size(min = 5, max = 50)
    @ApiModelProperty(value = "Type of performance", required = true)
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
