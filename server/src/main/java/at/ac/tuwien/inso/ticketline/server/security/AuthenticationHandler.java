package at.ac.tuwien.inso.ticketline.server.security;

import at.ac.tuwien.inso.ticketline.dto.UserEvent;
import at.ac.tuwien.inso.ticketline.dto.UserStatusDto;
import at.ac.tuwien.inso.ticketline.model.Employee;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Implementation of a Spring Security authentication handler.
 * Used by Spring Security
 */
public class AuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler {

    private ObjectMapper mapper;

    @Autowired
    private EmployeeService employeeService;


    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationHandler.class);


    /**
     * Instantiates a new authentication handler.
     */
    public AuthenticationHandler() {
        this.mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //Zähler runterstellen
        UserStatusDto userStatusDto = AuthUtil.getUserStatusDto(authentication);
        userStatusDto.setEvent(UserEvent.AUTH_SUCCESS);
        try {
            //Reset attempt
            List<Employee> employees = employeeService.findByUsername(userStatusDto.getUsername());
            employeeService.updateAttempt(employees.get(0), 0);
        } catch (ServiceException e) {
            userStatusDto = new UserStatusDto();
            userStatusDto.setEvent(UserEvent.AUTH_FAILURE);
            this.printUserStatusDto(userStatusDto, response);
        }
        this.printUserStatusDto(userStatusDto, response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        LOGGER.debug("Exception: " + exception.getClass());
        UserStatusDto userStatusDto = new UserStatusDto();
        LOGGER.debug("Request: " + request.getParameter("user"));
        //Check if user exists
        //Update attempt
        //Lock user if attempt >= 5
        try {
            List<Employee> employees = employeeService.findByUsername(request.getParameter("user"));
            if (employees.isEmpty() || employees.size() > 1) {
                userStatusDto.setEvent(UserEvent.AUTH_FAILURE);
            } else {
                employeeService.updateAttempt(employees.get(0), employees.get(0).getAttempts() + 1);
                userStatusDto.setEvent(UserEvent.AUTH_FAILURE);
            }
        } catch (ServiceException e) {
            LOGGER.debug("Authentication failed");
            userStatusDto.setEvent(UserEvent.AUTH_FAILURE);
        } catch (LockedException e) {
            LOGGER.debug("User is locked");
            userStatusDto.setEvent(UserEvent.LOCKED);
        }
        this.printUserStatusDto(userStatusDto, response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserStatusDto userStatusDto = new UserStatusDto();
        userStatusDto.setEvent(UserEvent.LOGOUT);
        this.printUserStatusDto(userStatusDto, response);
    }

    /**
     * Prints the user status dto on the HTTP response stream.
     *
     * @param usd      the user status DTO
     * @param response the HTTP response
     * @throws IOException      Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    private void printUserStatusDto(UserStatusDto usd, HttpServletResponse response) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        String result;
        try {
            result = this.mapper.writeValueAsString(usd);
        } catch (JsonProcessingException e) {
            throw new ServletException(e);
        }
        response.getOutputStream().print(result);
    }
}
