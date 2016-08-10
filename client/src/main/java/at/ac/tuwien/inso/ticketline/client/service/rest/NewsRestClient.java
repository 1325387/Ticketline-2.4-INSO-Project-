package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.client.service.NewsService;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.NewsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link at.ac.tuwien.inso.ticketline.client.service.NewsService}
 */
@Component
public class NewsRestClient implements NewsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsRestClient.class);
    
    public static final String GET_ALL_NEWS_URL = "/service/news/";
    public static final String GET_UNREAD_NEWS_URL = "/service/news/query?employeeName=%s";
    public static final String PUBLISH_NEWS_URL = "/service/news/publish";

    @Autowired
    private RestClient restClient;

	/**
	 * {@inheritDoc}
	 */
    @Override
    public List<NewsDto> getNews() throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_NEWS_URL);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving news from {}", url);
        List<NewsDto> news;
        try {
            ParameterizedTypeReference<List<NewsDto>> ref = new ParameterizedTypeReference<List<NewsDto>>() {
            };
            ResponseEntity<List<NewsDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            news = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve news: " + e.getMessage(), e);
        }
        return news;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NewsDto> getUnreadNewsOfEmployee(String name) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = String.format(GET_UNREAD_NEWS_URL,name);
        url = this.restClient.createServiceUrl(url);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving unread news of employee from {}", url);
        List<NewsDto> news;
        try {
            ParameterizedTypeReference<List<NewsDto>> ref = new ParameterizedTypeReference<List<NewsDto>>() {
            };
            ResponseEntity<List<NewsDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            news = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve news: " + e.getMessage(), e);
        }
        return news;
    }

    /**
	 * {@inheritDoc}
	 */
    @Override
    public Integer publishNews(NewsDto news) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(PUBLISH_NEWS_URL);
        LOGGER.info("Publish news at {}", url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<NewsDto> entity = new HttpEntity<>(news, headers);
        MessageDto msg;
        try {
            msg = restTemplate.postForObject(url, entity, MessageDto.class);
        } catch (HttpStatusCodeException e) {
            MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
            if (errorMsg.hasFieldErrors()) {
                throw new ValidationException(errorMsg.getFieldErrors());
            } else {
                throw new ServiceException(errorMsg.getText());
            }
        } catch (RestClientException e) {
            throw new ServiceException("Could not publish news: " + e.getMessage(), e);
        }
        Integer id;
        try {
            id = Integer.valueOf(msg.getText());
        } catch (NumberFormatException e) {
            throw new ServiceException("Invalid ID: " + msg.getText());
        }
        return id;
    }

}
