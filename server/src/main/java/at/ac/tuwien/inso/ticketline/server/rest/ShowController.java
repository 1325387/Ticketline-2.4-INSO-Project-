package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.ShowDto;
import at.ac.tuwien.inso.ticketline.dto.TicketDto;
import at.ac.tuwien.inso.ticketline.model.Show;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.ShowService;
import at.ac.tuwien.inso.ticketline.server.service.TicketService;
import at.ac.tuwien.inso.ticketline.server.util.EntityToDto;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Lina Wang 1326922
 */
@Api(value = "show", description = "Show REST service")
@RestController
@RequestMapping(value = "/show")
public class ShowController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowController.class);

    @Autowired
    private ShowService showService;

    /**
     * Gets show by ID
     *
     * @param id the ID of the show
     * @return the show of the given ID
     * @throws ServiceException
     */
    @ApiOperation(value = "Gets specific show by ID", response = ShowDto.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ShowDto getShowById(@ApiParam(name = "id", value = "ID of the show") @PathVariable("id")Integer id) throws ServiceException {
        LOGGER.info("getShowById() called");
        if(id < 1){
            throw new ServiceException("Invalid show ID");
        }
        return EntityToDto.convert(showService.getShow(id));
    }


    /**
     * Gets the all show items.
     *
     * @return list of all show items
     * @throws ServiceException the service exception
     */
    @ApiOperation(value = "Gets all shows", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ShowDto> getAllShows() throws ServiceException {
        LOGGER.info("getAllShows() called");
        return EntityToDto.convertShows(showService.getAllShows());
    }

    /**
     * @author Sissi Zhan 1325880
     *
     * Gets all shows from a specific performance
     *
     * @param id the ID of performance
     * @return all shows of the given performance
     * @throws ServiceException
     */
    @ApiOperation(value = "Gets all shows of a performance", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/performance/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ShowDto> getAllShowsOfPerformance(@ApiParam(name = "id", value = "ID of the performance") @PathVariable("id")Integer id) throws ServiceException {
        LOGGER.info("getAllShowsOfPerformance() called");
        if(id < 1){
            throw new ServiceException("Invalid performance ID");
        }
        List<Show> shows = showService.getAllShowsByPerformance(id);

        return EntityToDto.convertShows(shows);

    }

    @ApiOperation(value = "Gets show of a ticket", response = ShowDto.class)
    @RequestMapping(value = "/ticket/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ShowDto getShowOfTicket(@ApiParam(name = "id", value ="ID of ticket") @PathVariable("id") Integer id) throws ServiceException{
        LOGGER.info("getShowOfTicket() called");
        if(id < 1){
            throw new ServiceException("Invalid ticket ID");
        }
        return EntityToDto.convert(showService.getShowByTicket(id));
    }
}
