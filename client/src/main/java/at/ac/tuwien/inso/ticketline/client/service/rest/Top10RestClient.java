package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.Top10Service;
import at.ac.tuwien.inso.ticketline.dto.ToptenDto;
import at.ac.tuwien.inso.ticketline.dto.ToptenPerformancesDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aysel Oeztuerk 0927011
 *
 * Implementation of {@link Top10Service}
 */
@Component
public class Top10RestClient implements Top10Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(Top10RestClient.class);

    public static final String GET_ALL_SHOWS_BY_PERFORMANCETYPE_URL = "/service/top10/shows?year=%d&month=%d&performanceType=%s";
    public static final String GET_ALL_PERFORMANCES_BY_PERFORMANCETYPE_URL = "/service/top10/performances?year=%d&month=%d&performanceType=%s";

    @Autowired
    private RestClient restClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ToptenDto> getTop10Shows(int year, int month, String performanceType) throws ServiceException {
        List<ToptenDto> toptenShows = new ArrayList<>();

        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(String.format(GET_ALL_SHOWS_BY_PERFORMANCETYPE_URL, year, month, performanceType));
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());

        LOGGER.info("Retrieving top 10 shows by performance type from {}", url);
        try {
            ParameterizedTypeReference<List<ToptenDto>> ref = new ParameterizedTypeReference<List<ToptenDto>>() {
            };
            ResponseEntity<List<ToptenDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            toptenShows = response.getBody();
        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve top 10 shows by performance type: " + e.getMessage(), e);
        }
        return toptenShows;
    }

    /**
     * Returns the top 10 performances of given performance type/category by count of sold tickets.
     *
     * @param year            of the performance
     * @param month           of the performance
     * @param performanceType type of the performance
     * @return a list of top 10 performances
     * @throws ServiceException the service exception
     */
    @Override
    public List<ToptenPerformancesDto> getTop10Performances(int year, int month, String performanceType) throws ServiceException {
        List<ToptenPerformancesDto> toptenShows = new ArrayList<>();

        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(String.format(GET_ALL_PERFORMANCES_BY_PERFORMANCETYPE_URL, year, month, performanceType));
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());

        LOGGER.info("Retrieving top 10 shows by performance type from {}", url);
        try {
            ParameterizedTypeReference<List<ToptenPerformancesDto>> ref = new ParameterizedTypeReference<List<ToptenPerformancesDto>>() {
            };
            ResponseEntity<List<ToptenPerformancesDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            toptenShows = response.getBody();
        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve top 10 shows by performance type: " + e.getMessage(), e);
        }
        return toptenShows;
    }
}