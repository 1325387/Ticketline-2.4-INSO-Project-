package at.ac.tuwien.inso.ticketline.server.rest;

/**
 * @author Lina Wang 1326922
 */

import at.ac.tuwien.inso.ticketline.dto.PerformanceDto;
import at.ac.tuwien.inso.ticketline.dto.PerformanceFilterDto;
import at.ac.tuwien.inso.ticketline.dto.ShowDto;
import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.PerformanceService;
import at.ac.tuwien.inso.ticketline.server.util.EntityToDto;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.DoubleAccumulator;

/**
 * Controller for the performance REST service
 */
@Api(value = "performance", description = "Performance REST service")
@RestController
@RequestMapping(value = "/performance")
public class PerformanceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceController.class);

    @Autowired
    private PerformanceService performanceService;

    /**
     * Gets performance by ID
     *
     * @param id the ID of the performance
     * @return the performance of the given ID
     * @throws ServiceException
     */
    @ApiOperation(value = "Gets specific performance by ID", response = PerformanceDto.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public PerformanceDto getPerformanceById(@ApiParam(name = "id", value = "ID of the performance") @PathVariable("id")int id) throws ServiceException {
        LOGGER.info("getShowById() called");
        if(id < 1){
            throw new ServiceException("Invalid performance ID");
        }
        return EntityToDto.convert(performanceService.getPerformance(id));
    }


    /**
     * Gets the all performance items.
     *
     * @return list of all performance items
     * @throws ServiceException the service exception
     */
    @ApiOperation(value = "Gets all performances", response = PerformanceDto.class, responseContainer = "List")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<PerformanceDto> getAllPerformances() throws ServiceException {
        LOGGER.info("getAllPerformances() called");
        return EntityToDto.convertPerformances(performanceService.getAllPerformances());

    }

    /**
     * @author Sissi Zhan 1325880
     *
     * @param id ID of the show
     * @return the performance of the show
     * @throws ServiceException
     */
    @ApiOperation(value = "Gets performance of show", response = PerformanceDto.class)
    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public PerformanceDto getPerformanceOfShow(@ApiParam(name = "id", value = "ID of the show") @PathVariable("id")int id) throws ServiceException {
        LOGGER.info("getPerformanceOfShow() called");
        if(id < 1){
            throw new ServiceException("Invalid show ID");
        }
        Performance performance = performanceService.getPerformanceOfShow(id);
        return EntityToDto.convert(performance);
    }

    /**
     * Sissi Zhan 1325880
     *
     *
     * @param performanceName
     * @param date
     * @param town
     * @param plz
     * @param time
     * @param performanceType
     * @param price
     * @param artist
     *
     * @return Performances with given attributes
     *
     * @throws ServiceException
     */
    @ApiOperation(value = "Gets performance after filtering",  response = PerformanceDto.class, responseContainer = "List")
    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public @ResponseBody List<PerformanceDto> filterPerformances(
        @RequestParam(value = "performanceName") String performanceName,
        @RequestParam(value = "date") String date,
        @RequestParam(value = "town") String town,
        @RequestParam(value = "plz") String plz,
        @RequestParam(value = "time") String time,
        @RequestParam(value = "performanceType") String performanceType,
        @RequestParam(value = "price") String price,
        @RequestParam(value = "artist") String artist) throws ServiceException {
        LOGGER.info("filterPerformances() called");

        String validateMessage = validatePerformanceAttributes(performanceName,date,town,plz,time,performanceType,price,artist);
        LOGGER.debug("Validate Message length: " + validateMessage.length());
        LOGGER.debug("Message: " + validateMessage);

        if(validateMessage.length()!=0) {
            throw new ServiceException(validateMessage);
        }

        LOGGER.info("filterPerformances() called");

        List<Performance> list;
        list = this.performanceService.filterPerformances(performanceName, date, town, plz, time, performanceType, price, artist);

        return EntityToDto.convertPerformances(list);
    }

    public String validatePerformanceAttributes(String pName, String date, String town, String plz, String time, String pType, String price, String artist) throws ServiceException {
        String errorMessage = "";
        town = town.replaceAll("_"," ");

        if(!date.equals("null") && !date.matches("[0-9-]*")) {
            errorMessage += "Invalid performance date.\n";
        }
        if(!town.equals("null") && !town.matches("[a-zA-Z\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")) {
            errorMessage += "Invalid town.\n";
        }
        if(!plz.equals("null") && !plz.matches("[0-9]*")) {
            errorMessage += "Invalid PLZ.\n";
        }

        if(pType.length() != 0 && !pType.matches("[a-zA-Z\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")) {
            errorMessage += "Invalid performance type\n";
        }

        try {
            if (!price.equals("null")) {
                LOGGER.debug("Price length: " + price.length());
                if(Integer.parseInt(price) < 1) {
                    errorMessage += "Invalid price";
                }
            }
        }catch(Exception e) {
            errorMessage += "Invalid price";
        }

        return errorMessage;
    }


}
