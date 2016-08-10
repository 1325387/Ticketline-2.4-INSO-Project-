package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.LocationService;
import at.ac.tuwien.inso.ticketline.dto.PerformanceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static at.ac.tuwien.inso.ticketline.client.service.rest.PerformanceRestClient.GET_ALL_PERFORMANCES_URL;

/**
 * @author Sissi Zhan 1325880
 */
@Component
public class LocationRestClient implements LocationService {

    private static final Logger logger = LoggerFactory.getLogger(LocationRestClient.class);

    public static final String GET_ALL_CITIES_URL = "/service/location/cities";
    public static final String GET_ALL_POSTALCODES_URL = "/service/location/postalcodes/";

    @Autowired
    private RestClient restClient;

    @Override
    public List<String> getCities() throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_CITIES_URL);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        logger.info("Retrieving cities from {}", url);
        List<String> cities;
        try {
            ParameterizedTypeReference<List<String>> ref = new ParameterizedTypeReference<List<String>>() {
            };
            ResponseEntity<List<String>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            cities = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve cities: " + e.getMessage(), e);
        }
        return cities;
    }

    @Override
    public List<String> getPostalCodes() throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_POSTALCODES_URL);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        logger.info("Retrieving cities from {}", url);
        List<String> postalCodes;
        try {
            ParameterizedTypeReference<List<String>> ref = new ParameterizedTypeReference<List<String>>() {
            };
            ResponseEntity<List<String>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            postalCodes = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve postalcodes: " + e.getMessage(), e);
        }
        return postalCodes;
    }
}
