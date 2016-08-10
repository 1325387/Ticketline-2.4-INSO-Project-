package at.ac.tuwien.inso.ticketline.server.util;

import at.ac.tuwien.inso.ticketline.dto.*;
import at.ac.tuwien.inso.ticketline.dto.Gender;
import at.ac.tuwien.inso.ticketline.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides static converter methods from DTOs to entities
 */
public class DtoToEntity {

    /**
     * Converts a news DTO to a news entity.
     *
     * @param newsDto the news DTO
     * @return the news
     */
    public static News convert(NewsDto newsDto) {
        News news = new News();
        news.setId(newsDto.getId());
        news.setTitle(newsDto.getTitle());
        news.setNewsText(newsDto.getNewsText());
        return news;
    }

    /**
     * Converts a list of news DTO to a list of news entity
     *
     * @param news the news DTO
     * @return a list of news
     */
    public static List<News> convertNews(List<NewsDto> news) {
        List<News> ret = new ArrayList<>();
        for(NewsDto dto : news) {
            ret.add(convert(dto));
        }
        return ret;
    }


    /**
     * Converts a show DTO to a show entity.
     *
     * @param showDto the show DTO
     * @return the show
     */
    public static Show convert(ShowDto showDto) {
        Show show = new Show();
        show.setDateOfPerformance(showDto.getDateOfPerformance());
        //TODO
        return show;
    }

    /**
     * Converts a performance DTO to a performance entity.
     *
     * @param performanceDto the show DTO
     * @return the performance
     */
    public static Performance convert(PerformanceDto performanceDto) {
        Performance performance = new Performance();
        performance.setName(performanceDto.getName());
        performance.setDescription(performanceDto.getDescription());
        performance.setDuration(performanceDto.getDuration());
        //TODO
        //PerformanceType is Enumeration
        //performance.setPerformanceType(performanceDto.getType());
        return performance;
    }

    public static Ticket convert(TicketDto ticketDto){
        Ticket ticket = new Ticket();
        ticket.setId(ticketDto.getId());
        ticket.setDescription(ticketDto.getDescription());
        ticket.setPrice((int) ticketDto.getPrice());
        return ticket;
    }

    public static List<Ticket> convertTickets(List<TicketDto> tickets){
        List<Ticket> ret = new ArrayList<>();
        for (TicketDto dto : tickets) {
            ret.add(convert(dto));
        }
        return ret;
    }

    public static TicketIdentifier convert(TicketIdentifierDto ticketIdentifierDto){
        TicketIdentifier ticketIdentifier = new TicketIdentifier();
        return ticketIdentifier;
    }

    /**
     * @author Aysel Oeztuerk 0927011
     *
     * Converts a customer DTO to a customer entity.
     *
     * @param customerDto the customer DTO
     * @return the customer entity
     */
    public static Customer convert(CustomerDto customerDto){
        Customer customer = new Customer();
        customer.setFirstname(customerDto.getFirstname());
        customer.setLastname(customerDto.getLastname());
        customer.setDateOfBirth(customerDto.getDateOfBirth());

        Address address =  new Address();
        address.setStreet(customerDto.getAddress().getStreet());
        address.setPostalCode(customerDto.getAddress().getPostalCode());
        address.setCity(customerDto.getAddress().getCity());
        address.setCountry(customerDto.getAddress().getCountry());

        customer.setAddress(address);
        customer.setTitle(customerDto.getTitle());
        customer.setGender(at.ac.tuwien.inso.ticketline.model.Gender.valueOf(customerDto.getGender()));
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setEmail(customerDto.getEmail());
        return customer;
    }
    /**
     * @author Aysel Oeztuerk 0927011
     *
     * Converts a customer DTO to a customer entity.
     *
     * @param customerDto the customer DTO
     * @return the customer entity
     */
    public static Customer convertForUpdate(CustomerDto customerDto){
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setFirstname(customerDto.getFirstname());
        customer.setLastname(customerDto.getLastname());
        customer.setDateOfBirth(customerDto.getDateOfBirth());

        Address address =  new Address();
        address.setStreet(customerDto.getAddress().getStreet());
        address.setPostalCode(customerDto.getAddress().getPostalCode());
        address.setCity(customerDto.getAddress().getCity());
        address.setCountry(customerDto.getAddress().getCountry());

        customer.setAddress(address);

        customer.setTitle(customerDto.getTitle());
        customer.setGender(at.ac.tuwien.inso.ticketline.model.Gender.valueOf(customerDto.getGender()));
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setEmail(customerDto.getEmail());
        return customer;
    }
}
