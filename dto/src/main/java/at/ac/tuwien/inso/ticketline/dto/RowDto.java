package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Sissi Zhan 1325880
 */
@ApiModel(value = "RowDto", description = "Data transfer object for a row")
public class RowDto {

    @NotNull
    @ApiModelProperty(value = "ID of row")
    private int id;

    @ApiModelProperty(value = "Description of row")
    @Size(min = 5, max = 255)
    private String description;

    @ApiModelProperty(value = "Name of row")
    @Size(min = 5, max = 255)
    private String name;

    @NotNull
    @ApiModelProperty(value = "Sequence of row")
    private int sequence;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
