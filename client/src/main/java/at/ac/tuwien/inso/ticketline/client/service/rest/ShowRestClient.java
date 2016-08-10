package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.ShowService;
import at.ac.tuwien.inso.ticketline.dto.ShowDto;
import at.ac.tuwien.inso.ticketline.dto.TicketDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * @author Lina Wang 1326922
 */
@Component
public class ShowRestClient implements ShowService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowRestClient.class);

    public static final String GET_ALL_SHOWS_URL = "/service/show/";
    public static final String GET_ALL_SHOWS_OF_PERFORMANCE_URL = "/service/show/performance/";
    public static final String GET_SHOW_OF_TICKET_URL = "/service/show/ticket/";

    @Autowired
    private RestClient restClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ShowDto> getAll() throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_SHOWS_URL);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving shows from {}", url);
        List<ShowDto> shows;
        try {
            ParameterizedTypeReference<List<ShowDto>> ref = new ParameterizedTypeReference<List<ShowDto>>() {
            };
            ResponseEntity<List<ShowDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            shows = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve shows: " + e.getMessage(), e);
        }
        return shows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ShowDto> getAllOfPerformance(int performanceId) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_SHOWS_OF_PERFORMANCE_URL + performanceId);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving shows of performance {}", url);
        List<ShowDto> shows;
        try {
            ParameterizedTypeReference<List<ShowDto>> ref = new ParameterizedTypeReference<List<ShowDto>>() {
            };
            ResponseEntity<List<ShowDto>> response = restTemplate.exchange(url, HttpMethod.GET, entity, ref);
            shows = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve show of performance: " + e.getMessage(), e);
        }

        return shows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShowDto getOfTicket(TicketDto ticket) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_SHOW_OF_TICKET_URL+ticket.getId());
        LOGGER.info("Get show of ticket at {}", url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        ShowDto result;
        try {
            ParameterizedTypeReference<ShowDto> ref = new ParameterizedTypeReference<ShowDto>() {
            };
            ResponseEntity<ShowDto> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            result = response.getBody();
        } catch (RestClientException e) {
            throw new ServiceException("Could not get show of ticket: " + e.getMessage(), e);
        }
        return result;
    }
}
