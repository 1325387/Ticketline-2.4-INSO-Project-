package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.ToptenDto;
import at.ac.tuwien.inso.ticketline.dto.ToptenPerformancesDto;
import at.ac.tuwien.inso.ticketline.model.PerformanceType;
import at.ac.tuwien.inso.ticketline.model.Topten;
import at.ac.tuwien.inso.ticketline.model.ToptenPerformances;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.Top10Service;
import at.ac.tuwien.inso.ticketline.server.util.EntityToDto;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the top 10 REST service
 */
@Api(value = "top10", description = "Top ten REST service")
@RestController
@RequestMapping(value = "/top10")
public class Top10Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Top10Controller.class);

    @Autowired
    private Top10Service top10Service;

    @ApiOperation(value = "Gets top 10 shows of a performance type", response = ToptenDto.class, responseContainer = "List")
    @RequestMapping(value = "/shows", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public @ResponseBody List<ToptenDto> getTop10OfPerformanceType(
           @RequestParam(value = "year") int year,
           @RequestParam(value = "month") int month,
           @RequestParam(value = "performanceType") String pType) throws ServiceException {

        LOGGER.debug("Client sent: " + year + " " + month + " " + pType);
        PerformanceType performanceType = null;
        List<ToptenDto> ttDto = new ArrayList<>();
        try {
            performanceType = performanceType.valueOf(pType);
            LOGGER.info("getTop10OfPerformanceType() called: " + performanceType);

            ttDto = EntityToDto.convert(top10Service.getTop10ShowsOfPerformanceType(year, month, performanceType));

        } catch (NullPointerException e) {
            LOGGER.error("Given performance type doesn't exist.");
        } catch (IllegalArgumentException e){
            LOGGER.error("Given performance type doesn't exist.");
        }
        return ttDto;
    }

    @ApiOperation(value = "Gets top 10 performances of a performance type", response = ToptenDto.class, responseContainer = "List")
    @RequestMapping(value = "/performances", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public @ResponseBody List<ToptenPerformancesDto> getTop10Performances(
            @RequestParam(value = "year") int year,
            @RequestParam(value = "month") int month,
            @RequestParam(value = "performanceType") String pType) throws ServiceException {

        PerformanceType performanceType = null;
        List<ToptenPerformancesDto> ttDto = new ArrayList<>();
        try {
            performanceType = performanceType.valueOf(pType);
            ttDto = EntityToDto.convertTopTenList(top10Service.getTop10PerformancesOfPerformanceType(year, month, performanceType));

        } catch (NullPointerException e) {
            LOGGER.error("Given performance type doesn't exist.");
        } catch (IllegalArgumentException e){
            LOGGER.error("Given performance type doesn't exist.");
        }
        return ttDto;
    }
}
