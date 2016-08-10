package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.GalleryService;
import at.ac.tuwien.inso.ticketline.dto.GalleryDto;
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
import java.util.Map;

/**
 * @author Lina Wang 1326922
 */
@Component
public class GalleryRestClient implements GalleryService {


    private static final Logger LOGGER = LoggerFactory.getLogger(GalleryRestClient.class);

    public static final String GET_ALL_GALLERY_OF_SHOW_URL = "/service/gallery/show/";

    @Autowired
    private RestClient restClient;



    @Override
    public List<GalleryDto> getallOfShow(Integer showId) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_GALLERY_OF_SHOW_URL + showId);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving seats of show {}", url);
        List<GalleryDto> gallery;
        try{
            ParameterizedTypeReference<List<GalleryDto>> ref = new ParameterizedTypeReference<List<GalleryDto>>() {
            };
            ResponseEntity<List<GalleryDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            gallery = response.getBody();
        }catch(RestClientException e){
            throw new ServiceException("Could not retrieve seats of show: " + e.getMessage(), e);
        }

        return gallery;
    }
}
