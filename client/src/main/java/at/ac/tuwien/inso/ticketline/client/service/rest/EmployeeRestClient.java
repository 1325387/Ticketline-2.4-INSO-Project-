package at.ac.tuwien.inso.ticketline.client.service.rest;


import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.EmployeeService;
import at.ac.tuwien.inso.ticketline.dto.EmployeeDto;
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
 * @author Jayson Asenjo 1325387
 */
@Component
public class EmployeeRestClient implements EmployeeService {

    @Autowired
    private RestClient restClient;

    public static final String GET_EMPLOYEE = "/service/employee/";

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeDto getEmployee(String username, String firstname, String lastname) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_EMPLOYEE+username);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        EmployeeDto employee = null;
        try {
            ParameterizedTypeReference<List<EmployeeDto>> ref = new ParameterizedTypeReference<List<EmployeeDto>>() {
            };
            ResponseEntity<List<EmployeeDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            List<EmployeeDto> employees = response.getBody();

            for(EmployeeDto emp:employees){
                if(emp.getFirstname().equals(firstname)&&emp.getLastname().equals(lastname)){
                    employee = emp;
                    break;
                }
            }

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve employee: " + e.getMessage(), e);
        }
        return employee;
    }
}
