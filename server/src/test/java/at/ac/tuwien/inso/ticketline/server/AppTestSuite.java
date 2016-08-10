package at.ac.tuwien.inso.ticketline.server;

import at.ac.tuwien.inso.ticketline.server.integrationtest.service.*;
import at.ac.tuwien.inso.ticketline.server.service.LocalStorageService;
import at.ac.tuwien.inso.ticketline.server.unittest.service.LocalStorageServiceTest;
import at.ac.tuwien.inso.ticketline.server.unittest.service.NewsServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@SuiteClasses(value = {NewsServiceTest.class,
        NewsServiceIntegrationTest.class,
        TicketServiceIntegrationTest.class,
        CustomerServiceIntegrationTest.class,
        LocationServiceIntegrationTest.class,
        PerformanceServiceIntegrationTest.class,
        ReservationServiceIntegrationTest.class,
        RoomServiceIntegrationTest.class,
        SeatServiceIntegrationTest.class,
        ShowServiceIntegrationTest.class,
        PerformanceServiceIntegrationTest.class})
public class AppTestSuite {

}
