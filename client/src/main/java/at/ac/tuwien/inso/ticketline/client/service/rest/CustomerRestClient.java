package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.CustomerService;
import at.ac.tuwien.inso.ticketline.dto.CustomerDto;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

/**
 * @author Aysel Oeztuerk 0927011
 *
 * Implementation of {@link CustomerService}
 */
@Component
public class CustomerRestClient implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRestClient.class);

    public static final String GET_ALL_CUSTOMERS_URL = "/service/customer/";
    public static final String CREATE_CUSTOMER_URL = "/service/customer/create/";
    public static final String UPDATE_CUSTOMER_URL = "/service/customer/update/";
    public static final String FIND_CUSTOMERS_BY_NAME_URL = "/service/customer/find/";

    @Autowired
    private RestClient restClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CustomerDto> getAllCustomers() throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_CUSTOMERS_URL);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving customers from {}", url);
        List<CustomerDto> customers;
        try {
            ParameterizedTypeReference<List<CustomerDto>> ref = new ParameterizedTypeReference<List<CustomerDto>>() {
            };
            ResponseEntity<List<CustomerDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            customers = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve customers: " + e.getMessage(), e);
        }
        return customers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CustomerDto> getCustomerByName(String name) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String nameUrl = null;
        try {
            nameUrl = URLEncoder.encode(name, "UTF-8"); // Client: encode to UTF-8 format; Server: decode to ISO
        } catch (UnsupportedEncodingException e) {
            throw new ServiceException("UnsupportedEncodingException, name could not be encoded to Url format: "
                    + e.getMessage(), e); // TODO rest - exception handling
        }
        String url = this.restClient.createServiceUrl(FIND_CUSTOMERS_BY_NAME_URL + nameUrl);
        LOGGER.debug("----------------URL: " + url);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving customers by name from {}", url);
        List<CustomerDto> customers;
        try {
            ParameterizedTypeReference<List<CustomerDto>> ref = new ParameterizedTypeReference<List<CustomerDto>>() {};
            ResponseEntity<List<CustomerDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            customers = response.getBody();
        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve customers: " + e.getMessage(), e);
        }
        return customers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerDto createNewCustomer(CustomerDto customer) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(CREATE_CUSTOMER_URL);
        LOGGER.info("Creating a new customer with {}", url);

        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<CustomerDto> entity = new HttpEntity<>(customer, headers);
        CustomerDto responseCustomer;
        try {
            responseCustomer = restTemplate.postForObject(url, entity, CustomerDto.class);
            // restTemplate.exchange(url,HttpMethod.POST,entity,CustomerDto.class);
        } catch (RestClientException e) {
            throw new ServiceException("Could not create the new customer: " + e.getMessage(), e);
        }
        return responseCustomer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCustomer(CustomerDto customer) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(UPDATE_CUSTOMER_URL);
        LOGGER.info("Updating an existent customer with {}", url);

        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<CustomerDto> entity = new HttpEntity<>(customer, headers);
        try {
            MessageDto msg;

            msg = restTemplate.postForObject(url, entity, MessageDto.class);
        } catch (RestClientException e) {
            throw new ServiceException("Could not update the given customer: " + e.getMessage(), e);
        }
    }
}

