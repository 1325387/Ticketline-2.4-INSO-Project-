package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.StripeService;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.MessageType;
import at.ac.tuwien.inso.ticketline.dto.StripeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @author Raphael Schotola 1225193
 */
@Component
public class StripeRestClient implements StripeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StripeRestClient.class);

    public static final String PAY_URL = "/service/stripe/";

    @Autowired
    private RestClient restClient;
    /**
     * {@inheritDoc}
     */
    @Override
    public void pay(StripeDto dto) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(PAY_URL);
        LOGGER.info("Transaction at {}", url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StripeDto> entity = new HttpEntity<>(dto, headers);
        MessageDto msg;
        try {
            msg = restTemplate.postForObject(url, entity, MessageDto.class);
        } catch(RestClientException e){
            throw new ServiceException("Could not make payment: "+e.getMessage());
        }
        if(msg.getType()== MessageType.ERROR){
            throw new ServiceException(msg.getText());
        }
    }
}
