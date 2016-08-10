package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.SeatService;
import at.ac.tuwien.inso.ticketline.dto.SeatDto;
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

/**
 * @author Raphael Schotola 1225193
 */
@Component
public class SeatRestClient implements SeatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeatRestClient.class);

    public static final String GET_ALL_SEATS_OF_SHOW_URL = "/service/seat/show/";

    public static final String GET_ALL_SEATS_OF_GALLERY_URL = "/service/seat/gallery/";

    @Autowired
    private RestClient restClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SeatDto> getAllOfShow(Integer showId) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_SEATS_OF_SHOW_URL + showId);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving seats of show {}", url);
        List<SeatDto> seats;
        try{
            ParameterizedTypeReference<List<SeatDto>> ref = new ParameterizedTypeReference<List<SeatDto>>() {
            };
            ResponseEntity<List<SeatDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            seats = response.getBody();
        }catch(RestClientException e){
            throw new ServiceException("Could not retrieve seats of show: " + e.getMessage(), e);
        }

        return seats;
    }

    @Override
    public List<SeatDto> getAllOfGallery(Integer galleryId) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_SEATS_OF_GALLERY_URL + galleryId);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving seats of gallery {}", url);
        List<SeatDto> seats;
        try{
            ParameterizedTypeReference<List<SeatDto>> ref = new ParameterizedTypeReference<List<SeatDto>>() {
            };
            ResponseEntity<List<SeatDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            seats = response.getBody();
        }catch(RestClientException e){
            throw new ServiceException("Could not retrieve seats of gallery: " + e.getMessage(), e);
        }

        return seats;
    }
}
