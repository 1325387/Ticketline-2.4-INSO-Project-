package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.EmployeeDto;
import at.ac.tuwien.inso.ticketline.model.Employee;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.EmployeeService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jayson Asenjo 1325387
 */
@Api(value = "employee", description = "Employee REST service")
@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @ApiOperation(value = "Gets employee by username", response = EmployeeDto.class)
    @RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<EmployeeDto> findEmployeeByUsername(@ApiParam(name = "username" ,value = "Username of employee") @PathVariable("username") String username) throws ServiceException{
        List<Employee> employee = employeeService.findByUsername(username);
        List<EmployeeDto> dto = new ArrayList<>();
        for(Employee emp : employee){
            EmployeeDto eDto = new EmployeeDto();
            eDto.setId(emp.getId());
            eDto.setUsername(emp.getUsername());
            eDto.setFirstname(emp.getFirstname());
            eDto.setLastname(emp.getLastname());
            dto.add(eDto);
        }
        return dto;
    }
}
