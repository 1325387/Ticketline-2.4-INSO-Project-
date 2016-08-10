package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.MessageType;
import at.ac.tuwien.inso.ticketline.dto.NewsDto;
import at.ac.tuwien.inso.ticketline.dto.TicketDto;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.LocalStorageService;
import at.ac.tuwien.inso.ticketline.server.util.DtoToEntity;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.logging.log4j.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Lina Wang, 1326922
 */
@Api(value = "localstorage", description = "LocalStorage REST service")
@RestController
@RequestMapping(value = "/localstorage")
public class LocalStorageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalStorageController.class);

    @Autowired
    private LocalStorageService localStorageService;

    /**
     * Updates localstorage of given employee
     *
     * @param employee the employee
     * @return the messagedto
     * @throws ServiceException
     */
    @ApiOperation(value = "Updates localstorage", response = MessageDto.class)
    @RequestMapping(value = "/query", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto updateLocalStorage(@RequestParam(value = "employeeName") String employee,
                                         @ApiParam(name = "news", value = "already read news")
                                         @Valid @RequestBody List<NewsDto> news) throws ServiceException {
        LOGGER.info("updateLocalStorage() called");

        if(employee == null || employee.length()==0) {
            throw new ServiceException("Invalid employee username");
        }
        for(NewsDto newsDto : news) {
            if(newsDto.getNewsText() == null || newsDto.getTitle() == null) {
                throw new ServiceException("Invalid news");
            }
        }

        this.localStorageService.updateLocalStorage(employee, DtoToEntity.convertNews(news));
        MessageDto messageDto = new MessageDto();
        messageDto.setType(MessageType.SUCCESS);
        return messageDto;
    }


}
