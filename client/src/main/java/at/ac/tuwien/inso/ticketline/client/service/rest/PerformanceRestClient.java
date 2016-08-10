package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.PerformanceFilterDto;
import at.ac.tuwien.inso.ticketline.client.service.PerformanceService;
import at.ac.tuwien.inso.ticketline.dto.PerformanceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Sissi Zhan 1325880
 */
@Component
public class PerformanceRestClient implements PerformanceService {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceRestClient.class);

    public static final String GET_ALL_PERFORMANCES_URL = "/service/performance/";
    public static final String GET_PERFORMANCE_OF_SHOW_URL = "/service/performance/show/";
    public static final String GET_FILTERED_PERFORMANCES_URL = "/service/performance/query?performanceName=%s" +
                                                            "&date=%s&town=%s&plz=%s&time=%s&performanceType=%s" +
                                                            "&price=%s&artist=%s";

    @Autowired
    private RestClient restClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PerformanceDto> getAll() throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_PERFORMANCES_URL);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        logger.info("Retrieving performances from {}", url);
        List<PerformanceDto> performances;
        try {
            ParameterizedTypeReference<List<PerformanceDto>> ref = new ParameterizedTypeReference<List<PerformanceDto>>() {
            };
            ResponseEntity<List<PerformanceDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            performances = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve performance: " + e.getMessage(), e);
        }
        return performances;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PerformanceDto getOfShow(int showId) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_PERFORMANCE_OF_SHOW_URL + showId);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        logger.info("Retrieving performances of show {}", url);
        PerformanceDto performanceDto;

        try {
            ParameterizedTypeReference<PerformanceDto> ref = new ParameterizedTypeReference<PerformanceDto>() {
            };
            ResponseEntity<PerformanceDto> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            performanceDto = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve performance of show: " + e.getMessage(), e);
        }

        return performanceDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PerformanceDto> filterPerformances(
            String performanceName, String date, String town, String plz, String time, String performanceType,
            String price, String artist
    ) throws ServiceException {

        List<PerformanceDto> performanceDtoList;
        if (performanceName != null && performanceName.equals("null")) {
            performanceName = "";
        }
        if (artist != null && artist.equals("null")) {
            artist = "";
        }

        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = String.format(GET_FILTERED_PERFORMANCES_URL, performanceName, date, town, plz, time,
                                                                                        performanceType, price, artist);
        url = this.restClient.createServiceUrl(url);

        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        logger.info("Filtering performances with {}", url);

        try {
            ParameterizedTypeReference<List<PerformanceDto>> ref = new ParameterizedTypeReference<List<PerformanceDto>>() {
            };

            ResponseEntity<List<PerformanceDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            performanceDtoList = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not filter performances: " + e.getMessage(), e);
        }
        return performanceDtoList;
    }
}
