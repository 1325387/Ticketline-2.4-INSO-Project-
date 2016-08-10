package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.dto.CustomerDto;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.MessageType;
import at.ac.tuwien.inso.ticketline.model.Customer;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.CustomerService;
import at.ac.tuwien.inso.ticketline.server.util.DtoToEntity;
import at.ac.tuwien.inso.ticketline.server.util.EntityToDto;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @author Aysel Oeztuer 0927011
 */
@Api(value = "customer", description = "Customer REST service")
@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    /**
     * Gets customer by given ID
     *
     * @param id the ID
     * @return the customer
     * @throws ServiceException
     */
    @ApiOperation(value = "Gets customer by ID", response = CustomerDto.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public CustomerDto getCustomerById(@ApiParam(name = "id", value = "ID of the customer") @PathVariable("id") Integer id) throws ServiceException {
        LOGGER.info("getCustomerById() called");
        if (id < 1) {
            throw new ServiceException("Invalid customer ID");
        }
        return EntityToDto.convert(customerService.getCustomer(id));
    }


    /**
     * Gets all customers.
     *
     * @return list of all customers
     * @throws ServiceException the service exception
     */
    @ApiOperation(value = "Gets all customers", response = CustomerDto.class, responseContainer = "List")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<CustomerDto> getAllCustomers() throws ServiceException {
        LOGGER.info("getAllCustomers() called");

       /* for(Customer d: customerService.getAllCustomers()){
            LOGGER.debug("Customer: " + d.getFirstname() + " " + d.getGender());
        }*/

        return EntityToDto.convertCustomers(customerService.getAllCustomers());
    }

    /**
     * Gets customers, who resemble the given name
     *
     * @param name the given name
     * @return a list of customers who resemble the given name
     * @throws ServiceException
     */

    @ApiOperation(value = "Gets customer by name", response = CustomerDto.class, responseContainer = "List")
    @RequestMapping(value = "/find/{name}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<CustomerDto> getCustomerByName(@ApiParam(name = "name", value = "name pattern to find") @PathVariable("name") String name) throws ServiceException {
        LOGGER.info("getCustomerByName() called: " + name);
        LOGGER.debug("Is name empty" + name.isEmpty());
        if (name.isEmpty()) {
            LOGGER.debug("Name is empty");
            throw new ServiceException("Name is empty.");
        }

        String nameDecode;
        try {
            LOGGER.info("------------------------------Name: " + name);
            nameDecode = URLDecoder.decode(name, "ISO-8859-1");
            LOGGER.info("------------------------------NameUrlDecoded: " + nameDecode);

             /* nameDecode = "aö";
            LOGGER.info("------------------------------NameUrlDecoded aö: " + nameDecode);
            nameDecode = URLDecoder.decode(name, "ISO-8859-1");
            LOGGER.info("------------------------------name ISO decode: " + nameDecode);
            nameDecode = URLDecoder.decode(name, "UTF-8");
            LOGGER.info("------------------------------name ISO decode than utf8: " + nameDecode);

            byte[] bytes = name.getBytes("ISO-8859-1");
            LOGGER.info("------------------------------bytes: " + bytes);
            new String(bytes, "UTF-8");

            LOGGER.info("------------------------------bytes to string: " + bytes);
            /*nameDecode = URLDecoder.decode(bytes, "ISO-8859-1");
            LOGGER.debug("------------------------------NameUrlDecoded: " + nameDecode);
            nameDecode = "aö";
            LOGGER.debug("------------------------------NameUrlDecoded aö: " + nameDecode);*/


        } catch (UnsupportedEncodingException e) {
            throw new ServiceException("UnsupportedEncodingException, name could not be decoded from Url-format: "
                    + e.getMessage()); // TODO rest - exception handling
        }
        LOGGER.debug("Name: " + name);
        if (!nameDecode.matches("[a-zA-Z\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")) {
            LOGGER.debug("Name is invalid");
            throw new ServiceException("Invalid name to search");
        }

        return EntityToDto.convertCustomers(customerService.getCustomersByName(nameDecode));
    }

    /**
     * Creates a new customer with the given customerDto
     *
     * @param customerDto the customer to create
     * @return customerDto of created customer
     * @throws ServiceException
     */
    @ApiOperation(value = "Creats a new Customer", response = CustomerDto.class)
    @RequestMapping(value = "/create/", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public CustomerDto createNewCustomer(@ApiParam(name = "customer", value = "customer to create") @Valid @RequestBody CustomerDto customerDto) throws ServiceException {
        LOGGER.info("createNewCustomer() called");
        String validateMessage = validateCustomerDto(customerDto);
        //Input Validation
        if (validateMessage.length() != 0) {
            throw new ServiceException(validateMessage);
        }

        return EntityToDto.convert(customerService.createNewCustomer(DtoToEntity.convert(customerDto)));
    }

    /**
     * Updates the given existent customer with the given customerDto
     *
     * @param customerDto the customer to update
     * @return customerDto of updated customer
     * @throws ServiceException
     */
    @ApiOperation(value = "Updates an existent customer", response = CustomerDto.class)
    @RequestMapping(value = "/update/", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto updateCustomer(@ApiParam(name = "customer", value = "customer to update") @Valid @RequestBody CustomerDto customerDto) throws ServiceException {
        LOGGER.info("updateCustomer() called");
        MessageDto msg = new MessageDto();

        try {
            String validateMessage = validateCustomerDto(customerDto);
            if (validateMessage.length() != 0) {
                msg.setType(MessageType.ERROR);
                msg.setText(validateMessage);
                throw new ServiceException(validateMessage);
            }
            customerService.createNewCustomer(DtoToEntity.convertForUpdate(customerDto));
            msg.setType(MessageType.SUCCESS);
            return msg;
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public String validateCustomerDto(CustomerDto customerDto) {
        String errorMessage = "";
        String address = customerDto.getAddress().getStreet();
        LocalDate dob = customerDto.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String firstname = customerDto.getFirstname();
        String lastname = customerDto.getLastname();
        String phone = customerDto.getPhoneNumber();
        String email = customerDto.getEmail();
        String city = customerDto.getAddress().getCity();
        String country = customerDto.getAddress().getCity();
        String plz = customerDto.getAddress().getPostalCode();

        if (dob == null) {
            errorMessage += BundleManager.getMessageBundle().getString("dob.warning") + "\n";
        }
        if (phone == null) {
            errorMessage += BundleManager.getMessageBundle().getString("phone.warning") + "\n";
        } else if (!phone.matches("[0-9]+")) {
            errorMessage += BundleManager.getExceptionBundle().getString("onlynumbersallowed.error") + "\n";
        }
        //Check if customer is over 14 and under 110
        LocalDate currentDate = LocalDate.now(); //
        LocalDate ageLimitOver14 = currentDate.minusYears(14);
        LocalDate ageLimitUnder110 = currentDate.minusYears(110);

        if (dob.isAfter(ageLimitOver14)) {
            errorMessage += BundleManager.getMessageBundle().getString("14older.warning") + "\n";
        }
        if (dob.isBefore(ageLimitUnder110)) {
            errorMessage += BundleManager.getMessageBundle().getString("110younger.warning") + "\n";
        }
        if (firstname == null || firstname.length() == 0) {
            errorMessage += BundleManager.getMessageBundle().getString("firstname.warning") + "\n";
        } else if (!firstname.matches("[a-zA-Z\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")) {
            errorMessage += BundleManager.getExceptionBundle().getString("lettersinput.error") + "\n";
        }

        if (lastname == null || lastname.length() == 0) {
            errorMessage += BundleManager.getMessageBundle().getString("lastname.warning") + "\n";
        } else if (!lastname.matches("[a-zA-Z\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")) {

            errorMessage += BundleManager.getExceptionBundle().getString("lettersinput.error") + "\n";
        }

        return errorMessage;
    }
}
