package at.ac.tuwien.inso.ticketline.server.rest.swagger;

import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.rest.PerformanceController;
import at.ac.tuwien.inso.ticketline.server.service.LocationService;
import at.ac.tuwien.inso.ticketline.server.service.PerformanceService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Sissi Zhan 1325880
 */
@Api(value = "location", description = "Location REST service")
@RestController
@RequestMapping(value = "/location")
public class LocationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private LocationService locationService;

    @ApiOperation(value = "Gets all cities")
    @RequestMapping(value = "/cities", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<String> getCities() throws ServiceException {
        LOGGER.info("getCities() called");
        return locationService.getCities();
    }

    @ApiOperation(value = "Gets all postalcodes")
    @RequestMapping(value = "/postalcodes", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<String> getPostalCodes() throws ServiceException {
        LOGGER.info("getPostalCodes() called");
        return locationService.getPostalCodes();
    }

}
