package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Raphael Schotola 1225193
 */
@ApiModel(value = "SeatDto", description = "Data transfer object for a seat")
public class SeatDto {

    @NotNull
    @ApiModelProperty(value = "ID of seat")
    private int id;

    @ApiModelProperty(value = "Description of seat")
    @Size(min = 5, max = 255)
    private String description;

    @ApiModelProperty(value = "Name of seat")
    @Size(min = 5, max = 255)
    private String name;

    @NotNull
    @ApiModelProperty(value = "Sequence of seat")
    private int sequence;

    @NotNull
    @ApiModelProperty(value = "Row of seat")
    private RowDto row;

    @ApiModelProperty(value = "Gallery of seat")
    private GalleryDto gallery;


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

    public RowDto getRow() {
        return row;
    }

    public void setRow(RowDto row) {
        this.row = row;
    }

    public GalleryDto getGallery() {
        return gallery;
    }

    public void setGallery(GalleryDto gallery) {
        this.gallery = gallery;
    }
}
