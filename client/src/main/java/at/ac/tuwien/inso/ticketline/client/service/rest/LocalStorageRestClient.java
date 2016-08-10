package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.LocalStorageService;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.NewsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * @author Lina Wang, 1326922
 */
@Component
public class LocalStorageRestClient implements LocalStorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalStorageRestClient.class);

    public static final String UPDATE_STORAGE_URL = "/service/localstorage/query?employeeName=%s";

    @Autowired
    private RestClient restClient;

    @Override
    public void updateLocalStorage(String employee, List<NewsDto> news) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = String.format(UPDATE_STORAGE_URL,employee);
        url = this.restClient.createServiceUrl(url);
        LOGGER.info("Update localstorage {}", url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<List<NewsDto>> entity = new HttpEntity<>(news, headers);
        MessageDto messageDto;
        try {
            messageDto  = restTemplate.postForObject(url, entity, MessageDto.class);
        } catch (RestClientException e) {
            LOGGER.error("Could not update localstorage: " + e.getMessage());
            throw new ServiceException("Could not update localstorage");
        }

    }
}
