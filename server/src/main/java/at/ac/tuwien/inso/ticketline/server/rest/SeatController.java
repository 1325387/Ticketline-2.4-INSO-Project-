package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.SeatDto;
import at.ac.tuwien.inso.ticketline.model.Seat;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.SeatService;
import at.ac.tuwien.inso.ticketline.server.util.EntityToDto;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Sissi Zhan 1325880
 */
@Api(value = "seat", description = "Seat REST service")
@RestController
@RequestMapping(value = "/seat")
public class SeatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeatController.class);

    @Autowired
    private SeatService seatService;

    @ApiOperation(value = "Gets all seats from a show", response = SeatDto.class, responseContainer = "List")
    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<SeatDto> getAllSeatsOfShow(@ApiParam(name = "id", value = "ID of the show") @PathVariable("id")int id) throws ServiceException {
        LOGGER.info("getAllSeatsOfShow() called");
        if (id < 1) {
            throw new ServiceException("Invalid show ID");
        }
        List<Seat> seats = seatService.getAllOfShow(id);
        return EntityToDto.convertSeats(seats);
    }


    @ApiOperation(value = "Gets all seats in a gallery", response = SeatDto.class, responseContainer = "List")
    @RequestMapping(value = "/gallery/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<SeatDto> getAllSeatsOfGallery(@ApiParam(name = "id", value = "ID of the gallery") @PathVariable("id")int id) throws ServiceException {
        LOGGER.info("getAllSeatsOfGallery() called");
        if (id < 1) {
            throw new ServiceException("Invalid gallery ID");
        }
        List<Seat> seats = seatService.getAllOfGallery(id);
        return EntityToDto.convertSeats(seats);
    }
}
