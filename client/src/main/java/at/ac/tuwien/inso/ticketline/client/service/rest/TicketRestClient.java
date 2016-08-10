package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.TicketService;
import at.ac.tuwien.inso.ticketline.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * @author Lina Wang 1326922, Raphael Schotola 1225193
 */
@Component
public class TicketRestClient implements TicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketRestClient.class);

    public static final String GET_ALL_TICKETS_URL = "/service/ticket/";
    public static final String GET_AVAIBLE_TICKETS_OF_SHOW_URL = "/service/ticket/show/avaible/";
    public static final String GET_SOLD_TICKETS_OF_SHOW_URL = "/service/ticket/show/sold/";
    public static final String GET_RESERVED_TICKETS_OF_SHOW_URL = "/service/ticket/show/reserved/";
    public static final String GET_ALL_TICKETS_OF_CUSTOMER_URL = "/service/ticket/customer/";
    public static final String GET_ALL_TICKETS_OF_RESERVATION_URL = "/service/ticket/reservation/";
    public static final String SELL_TICKETS_URL = "/service/ticket/sell/";
    public static final String RESERVE_TICKETS_URL = "/service/ticket/reserve/";
    public static final String GET_ALL_TICKETS_OF_SHOW_URL = "/service/ticket/show/";
    public static final String CANCEL_TICKETS_URL = "/service/ticket/cancel/";


    @Autowired
    private RestClient restClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TicketDto> getAll() throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_TICKETS_URL);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving tickets {}", url);
        List<TicketDto> tickets;
        try {
            ParameterizedTypeReference<List<TicketDto>> ref = new ParameterizedTypeReference<List<TicketDto>>() {
            };
            ResponseEntity<List<TicketDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            tickets = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve tickets: " + e.getMessage(), e);
        }

        return tickets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TicketDto> getAllOfShow(Integer showId) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_TICKETS_OF_SHOW_URL + showId);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving tickets of show {}", url);
        List<TicketDto> tickets;
        try {
            ParameterizedTypeReference<List<TicketDto>> ref = new ParameterizedTypeReference<List<TicketDto>>() {
            };
            ResponseEntity<List<TicketDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            tickets = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve tickets of show: " + e.getMessage(), e);
        }

        return tickets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TicketDto> getSoldOfShow(Integer showId) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_SOLD_TICKETS_OF_SHOW_URL + showId);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving sold tickets of show {}", url);
        List<TicketDto> tickets;
        try {
            ParameterizedTypeReference<List<TicketDto>> ref = new ParameterizedTypeReference<List<TicketDto>>() {
            };
            ResponseEntity<List<TicketDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            tickets = response.getBody();
        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve available tickets of show: " + e.getMessage(), e);
        }

        return tickets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TicketDto> getReservedOfShow(Integer showId) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_RESERVED_TICKETS_OF_SHOW_URL + showId);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving reserved tickets of show {}", url);
        List<TicketDto> tickets;
        try {
            ParameterizedTypeReference<List<TicketDto>> ref = new ParameterizedTypeReference<List<TicketDto>>() {
            };
            ResponseEntity<List<TicketDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            tickets = response.getBody();
        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve reserved tickets of show: " + e.getMessage(), e);
        }

        return tickets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TicketDto> getAvaibleOfShow(Integer showId) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_AVAIBLE_TICKETS_OF_SHOW_URL + showId);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving avaible tickets of show {}", url);
        List<TicketDto> tickets;
        try {
            ParameterizedTypeReference<List<TicketDto>> ref = new ParameterizedTypeReference<List<TicketDto>>() {
            };
            ResponseEntity<List<TicketDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            tickets = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve avaible tickets of show: " + e.getMessage(), e);
        }

        return tickets;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ViewModelDto> getAllOfCustomer(Integer customerNumber) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_TICKETS_OF_CUSTOMER_URL + customerNumber);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving tickets of customer {}", url);
        List<ViewModelDto> viewModel;
        try {
            ParameterizedTypeReference<List<ViewModelDto>> ref = new ParameterizedTypeReference<List<ViewModelDto>>() {
            };
            ResponseEntity<List<ViewModelDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            viewModel = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve tickets of customer: " + e.getMessage(), e);
        }

        return viewModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ViewModelDto> getAllOfReservation(Integer reservationNumber) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_TICKETS_OF_RESERVATION_URL + reservationNumber);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving tickets of reservation {}", url);
        List<ViewModelDto> viewModel;
        try {
            ParameterizedTypeReference<List<ViewModelDto>> ref = new ParameterizedTypeReference<List<ViewModelDto>>() {
            };
            ResponseEntity<List<ViewModelDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            viewModel = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve tickets of reservation: " + e.getMessage(), e);
        }

        return viewModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TicketIdentifierDto> sellTicket(List<TicketDto> tickets, CustomerDto customer, EmployeeDto employee) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        int customerId;
        if (customer == null) {
            customerId = 1;
        } else {
            customerId = customer.getId();
        }

        LOGGER.debug("Customer id:" + customerId);
        String url = this.restClient.createServiceUrl(SELL_TICKETS_URL + employee.getId() + "/" + customerId);
        LOGGER.info("Sell tickets {}", url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<List<TicketDto>> entity = new HttpEntity<>(tickets, headers);
        List<TicketIdentifierDto> ticketIdentifier;
        try {
            ParameterizedTypeReference<List<TicketIdentifierDto>> ref = new ParameterizedTypeReference<List<TicketIdentifierDto>>() {
            };
            ResponseEntity<List<TicketIdentifierDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.POST, entity, ref);
            ticketIdentifier = response.getBody();
        } catch (RestClientException e) {
            LOGGER.error("Could not sell tickets: " + e.getMessage());
            throw new ServiceException("Could not sell tickets.");
        }
        return ticketIdentifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageDto reserveTicket(List<TicketDto> tickets, CustomerDto customer) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        int customerId;
        if (customer == null) {
            customerId = 1;
        } else {
            customerId = customer.getId();
        }

        String url = this.restClient.createServiceUrl(RESERVE_TICKETS_URL + customerId);
        LOGGER.info("Reserve tickets {}", url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<List<TicketDto>> entity = new HttpEntity<>(tickets, headers);
        MessageDto msg;
        try {

            msg = restTemplate.postForObject(url, entity, MessageDto.class);
            if (msg.getType().equals(MessageType.ERROR)) {
                throw new ServiceException(msg.getText());
            } else if (msg.getType().equals(MessageType.SUCCESS)) {
                LOGGER.debug("Reserve succeded");
                return msg;
            }

        } catch (RestClientException e) {
            throw new ServiceException("Could not reserve tickets: " + e.getMessage(), e);
        }
        return msg;
    }

    public void cancelTicket(List<TicketDto> tickets, String cancellationReason) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(CANCEL_TICKETS_URL + cancellationReason);
        LOGGER.info("Cancel tickets {}", url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        try {
            HttpEntity<List<TicketDto>> entity = new HttpEntity<>(tickets, headers);
            MessageDto msg;

            msg = restTemplate.postForObject(url, entity, MessageDto.class);

            if (msg.getType().equals(MessageType.ERROR)) {
                throw new ServiceException(msg.getText());
            }

        } catch (RestClientException e) {
            throw new ServiceException(e.getMessage());
        }


    }


}
