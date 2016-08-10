package at.ac.tuwien.inso.ticketline.server.util;

import at.ac.tuwien.inso.ticketline.dto.*;
import at.ac.tuwien.inso.ticketline.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides static converter methods from entities to DTOs
 */
public class EntityToDto {

    /**
     * Converts a list of news entities to news DTOs
     *
     * @param news the list of news entities
     * @return the list of news DTOs
     */
    public static List<NewsDto> convertNews(List<News> news) {
        List<NewsDto> ret = new ArrayList<>();
        if (null != news) {
            for (News n : news) {
                NewsDto dto = convert(n);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * Converts a news entity to a news DTO.
     *
     * @param news the news
     * @return the news DTO
     */
    public static NewsDto convert(News news) {
        NewsDto dto = new NewsDto();
        dto.setId(news.getId());
        dto.setTitle(news.getTitle());
        dto.setNewsText(news.getNewsText());
        dto.setSubmittedOn(news.getSubmittedOn());
        return dto;
    }

    /**
     * Converts a list of shows entities to show DTOs
     *
     * @param shows the list of show entities
     * @return the list of show DTOs
     */
    public static List<ShowDto> convertShows(List<Show> shows) {
        List<ShowDto> ret = new ArrayList<>();
        if (null != shows) {
            for (Show n : shows) {
                ShowDto dto = convert(n);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * Converts a show entity to a show DTO.
     *
     * @param show the show
     * @return the show DTO
     */
    public static ShowDto convert(Show show) {
        ShowDto dto = new ShowDto();
        dto.setCanceled(show.getCanceled());
        dto.setDateOfPerformance(show.getDateOfPerformance());
        dto.setId(show.getId());
        return dto;
    }

    /**
     * Converts a list of performance entities to performance DTOs
     *
     * @param performances the list of performance entities
     * @return the list of performance DTOs
     */
    public static List<PerformanceDto> convertPerformances(List<Performance> performances) {
        List<PerformanceDto> ret = new ArrayList<>();
        if (null != performances) {
            for (Performance n : performances) {
                PerformanceDto dto = convert(n);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * Converts a performance entity to a performance DTO.
     *
     * @param performance the performance
     * @return the performance DTO
     */
    public static PerformanceDto convert(Performance performance) {
        PerformanceDto dto = new PerformanceDto();
        dto.setName(performance.getName());
        dto.setDescription(performance.getDescription());
        dto.setDuration(performance.getDuration());
        dto.setId(performance.getId());
        String type = performance.getPerformanceType() + "";
        dto.setType(type);
        return dto;
    }

    /**
     * Converts a list of ticket entities to ticket DTOs
     *
     * @param tickets the list of ticket entities
     * @return the list of ticket DTOs
     */
    public static List<TicketDto> convertTickets(List<Ticket> tickets) {
        List<TicketDto> ret = new ArrayList<>();
        if (null != tickets) {
            for (Ticket n : tickets) {
                TicketDto dto = convert(n);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * Converts a ticket entity to a ticket DTO.
     *
     * @param ticket the ticket
     * @return the ticket DTO
     */
    public static TicketDto convert(Ticket ticket) {
        TicketDto dto = new TicketDto();
        dto.setDescription(ticket.getDescription());
        dto.setId(ticket.getId());
        dto.setPrice(ticket.getPrice());
        dto.setSeatId(ticket.getSeat().getId());
        return dto;
    }

    /**
     * Converts a list of ticketidentifier entities to ticketidentifier DTOs
     *
     * @param ticketIdentifiers the list of ticketidentifier entities
     * @return the list of ticketidentifier DTOs
     */
    public static List<TicketIdentifierDto> convertTicketIdentifiers(List<TicketIdentifier> ticketIdentifiers) {
        List<TicketIdentifierDto> ret = new ArrayList<>();
        if (null != ticketIdentifiers) {
            for (TicketIdentifier n : ticketIdentifiers) {
                TicketIdentifierDto dto = convert(n);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * Converts a ticketidentifier entity to a ticketidentifier DTO.
     *
     * @param ticketIdentifier the ticketidentifier
     * @return the ticketidentifier DTO
     */
    public static TicketIdentifierDto convert(TicketIdentifier ticketIdentifier) {
        TicketIdentifierDto dto = new TicketIdentifierDto();
        dto.setVoidedBy(ticketIdentifier.getVoidedBy());
        dto.setId(ticketIdentifier.getId());
        dto.setVoidationTime(ticketIdentifier.getVoidationTime());
        dto.setValid(ticketIdentifier.getValid());
        dto.setCancellationReason(ticketIdentifier.getCancellationReason());
        if (ticketIdentifier.getReservation() != null) {
            dto.setReservationID(ticketIdentifier.getReservation().getId());
        }
        return dto;
    }

    /**
     * Convert a list of seats entities to seat DTOs
     *
     * @param seats the list of seat entities
     * @return a list of seat DTO
     */
    public static List<SeatDto> convertSeats(List<Seat> seats) {
        List<SeatDto> ret = new ArrayList<>();
        if (null != seats) {
            for (Seat n : seats) {
                SeatDto dto = convert(n);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * Converts a seat entity to a seat DTO.
     *
     * @param seat the seat entity
     * @return a seat dto
     */
    public static SeatDto convert(Seat seat) {
        SeatDto dto = new SeatDto();
        dto.setId(seat.getId());
        dto.setDescription(seat.getDescription());
        dto.setName(seat.getName());
        dto.setSequence(seat.getSequence());
        dto.setRow(convert(seat.getRow()));
        if (seat.getGallery() != null) {
            dto.setGallery(convert(seat.getGallery()));
        }
        return dto;
    }

    /**
     * Converts a row entity to a row DTO.
     *
     * @param row the row entity
     * @return a row dto
     */
    public static RowDto convert(Row row) {
        RowDto dto = new RowDto();
        dto.setId(row.getId());
        dto.setDescription(row.getDescription());
        dto.setName(row.getName());
        dto.setSequence(row.getSequence());
        return dto;
    }

    public static GalleryDto convert(Gallery gallery) {
        GalleryDto dto = new GalleryDto();
        dto.setId(gallery.getId());
        dto.setName(gallery.getName());
        dto.setDescription(gallery.getDescription());
        dto.setSequence(gallery.getSequence());
        return dto;
    }

    /**
     * @author Aysel Oeztuerk 0927011
     *
     * Converts a customer entity to a customer DTO.
     *
     * @param customer the show
     * @return the customer DTO
     */
    public static CustomerDto convert(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setFirstname(customer.getFirstname());
        dto.setLastname(customer.getLastname());
        dto.setDateOfBirth(customer.getDateOfBirth());
        dto.setId(customer.getId());

        AddressDto address = new AddressDto();
        address.setStreet(customer.getAddress().getStreet());
        address.setPostalCode(customer.getAddress().getPostalCode());
        address.setCity(customer.getAddress().getCity());
        address.setCountry(customer.getAddress().getCountry());

        dto.setAddress(address);
        dto.setTitle(customer.getTitle());
        dto.setGender(customer.getGender().toString());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setEmail(customer.getEmail());

        return dto;
    }

    /**
     * @author Aysel Oeztuerk 0927011
     *
     * Converts a list of customer entities to a list of customer DTOs
     *
     * @param allCustomers the list of customer entities
     * @return the list of customer DTOs
     */
    public static List<CustomerDto> convertCustomers(List<Customer> allCustomers) {
        List<CustomerDto> ret = new ArrayList<>();
        if (null != allCustomers) {
            for (Customer n : allCustomers) {
                CustomerDto dto = convert(n);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * @author Aysel Oeztuerk 0927011
     *
     * Converts a list of topten entities to a list of topten DTOs
     *
     * @param top10ShowsOfPerformanceType the list of topten entities.
     * @return the list of topten DTOs
     */
    public static List<ToptenDto> convert(List<Topten> top10ShowsOfPerformanceType) {
        List<ToptenDto> toptenList = new ArrayList<>();
        if (null != top10ShowsOfPerformanceType) {
            for (Topten tt : top10ShowsOfPerformanceType) {
                ToptenDto dto = convert(tt);
                toptenList.add(dto);
            }
        }
        return toptenList;
    }

    /**
     * @author Aysel Oeztuerk 092701
     * Converts a topten entity to a topten DTO.
     *
     * @param tt topten model
     * @return the topten DTO
     */
    private static ToptenDto convert(Topten tt) {
        ToptenDto dto = new ToptenDto();
        dto.setEventID(tt.getEventID());
        dto.setSoldTickets(tt.getSoldTickets());
        dto.setName(tt.getName());
        dto.setDescription(tt.getDescription());
        dto.setDate(tt.getDate());
        return dto;
    }

    /**
     * @author Aysel Oeztuerk 0927011
     *
     * Converts a topten entity to a topten DTO.
     *
     * @param ttp topten model
     * @return the toptenPerformance DTO
     */
    private static ToptenPerformancesDto convert(ToptenPerformances ttp) {
        ToptenPerformancesDto dto = new ToptenPerformancesDto();
        dto.setEventID(ttp.getEventID());
        dto.setSoldTickets(ttp.getSoldTickets());
        dto.setName(ttp.getName());
        dto.setDescription(ttp.getDescription());

        return dto;
    }

    /**
     * @author Aysel Oeztuerk 0927011
     *
     * Converts a list of toptenPerformances entities to a list of toptenPerformances DTOs
     *
     * @param top10Performance the list of toptenPerformances entities.
     * @return the list of toptenPerformances DTOs
     */
    public static List<ToptenPerformancesDto> convertTopTenList(List<ToptenPerformances> top10Performance) {
        List<ToptenPerformancesDto> toptenList = new ArrayList<>();
        ToptenPerformancesDto dto;
        if (null != top10Performance) {
            for (ToptenPerformances ttp : top10Performance) {
                dto = convert(ttp);
                toptenList.add(dto);
            }
        }
        return toptenList;
    }
}
