package at.ac.tuwien.inso.ticketline.server.rest;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.MessageType;
import at.ac.tuwien.inso.ticketline.dto.NewsDto;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.NewsService;
import at.ac.tuwien.inso.ticketline.server.util.DtoToEntity;
import at.ac.tuwien.inso.ticketline.server.util.EntityToDto;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * Controller for the news REST service
 */
@Api(value = "news", description = "News REST service")
@RestController
@RequestMapping(value = "/news")
public class NewsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;

    /**
     * Gets the news by id.
     *
     * @param id the id
     * @return the news by id
     * @throws ServiceException the service exception
     */
    @ApiOperation(value = "Gets the news by id", response = NewsDto.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public NewsDto getNewsById(@ApiParam(name = "id", value = "ID of the news") @PathVariable("id") Integer id) throws ServiceException {
        LOGGER.info("getNewsById() called");
        if (id < 1) {
            throw new ServiceException("Invalid ID");
        }
        return EntityToDto.convert(newsService.getNews(id));
    }

    /**
     * Gets the all news items.
     *
     * @return list of all news items
     * @throws ServiceException the service exception
     */
    @ApiOperation(value = "Gets all news", response = NewsDto.class, responseContainer = "List")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<NewsDto> getAll() throws ServiceException {
        LOGGER.info("getAll() called");
        return EntityToDto.convertNews(newsService.getAllNews());
    }

    /**
     * Gets all unread news of given employee
     *
     * @param name name of the employee
     * @return list of all unread news
     * @throws ServiceException
     */
    @ApiOperation(value = "Gets all unread news of employee", response = NewsDto.class, responseContainer = "List")
    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<NewsDto> getUnreadNews(@RequestParam(value = "employeeName") String name) throws ServiceException {
        LOGGER.info("getUnreadNews() called");
        if (name == null || name.length() == 0) {
            throw new ServiceException("Invali employee username.");
        }
        return EntityToDto.convertNews(newsService.getUnreadNews(name));
    }

    /**
     * Publishes a new news.
     *
     * @param news the news
     * @return the message dto
     * @throws ServiceException the service exception
     */
    @ApiOperation(value = "Pubilishes a new news", response = NewsDto.class)
    @RequestMapping(value = "/publish", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto publishNews(@ApiParam(name = "news", value = "News to publish") @Valid @RequestBody NewsDto news) throws ServiceException {
        LOGGER.info("publishNews() called");
        if(news.getNewsText() == null || news.getTitle() == null) {
            throw new ServiceException("Invalid news to publish");
        }
        Integer id = this.newsService.save(DtoToEntity.convert(news)).getId();
        MessageDto msg = new MessageDto();
        msg.setType(MessageType.SUCCESS);
        msg.setText(id.toString());
        return msg;
    }

}
