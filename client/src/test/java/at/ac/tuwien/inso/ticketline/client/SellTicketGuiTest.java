package at.ac.tuwien.inso.ticketline.client;

import at.ac.tuwien.inso.ticketline.client.exception.GuiException;
import at.ac.tuwien.inso.ticketline.client.extraObjects.TableRowFinder;
import at.ac.tuwien.inso.ticketline.client.service.TicketService;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.ticketline.dto.TicketDto;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.loadui.testfx.GuiTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Raphael Schotola 1225193
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SellTicketGuiTest extends TestSetUp {

    private static final Logger LOGGER = LoggerFactory.getLogger(SellTicketGuiTest.class);

    private ClassPathXmlApplicationContext classPathXmlApplicationContext;

    @Autowired
    private TableRowFinder finder;
    @Autowired
    private TicketService ticketService;

    private List<TicketDto> soldTickets;

    @Test
    public void selectTab() {
        LOGGER.debug("Open tab...");
        click((Node) find("#event_tab_tab")); //hier den Tab angeben
        LOGGER.debug("Open tab successful...");
    }

    @Test
    public void testApp() throws GuiException {

        try {
            //what to select?
            int performanceID = 2;
            int showId = 4;
            List<Integer> checkBoxes = new ArrayList<>();
            for (int i = 3; i <= 5; i++) {
                checkBoxes.add(i);
            }

            //go to events and select a show
            click(finder.row("#event_performanceTable_tv", performanceID));
            click(finder.row("#event_showTable_tv", showId));

            click((Node) find("#event_availableTickets_btn"));

            //select Checkboxes
            GridPane gridPane = find("#roomPlan_seatsGrid_gp");
            for (Integer i : checkBoxes) {
                click(gridPane.getChildren().get(i));
            }

            //click sell button
            click((Node) find("#roomPlan_sell_btn"));

            //select anonymous customer
            click((Node) find("#whichCustomer_anonymousCustomer_btn"));

            //put in Card details
            TextField card = find("#stripe_cardNumber_tf");
            card.setText("4242424242424242");
            TextField month = find("#stripe_expMonth_tf");
            month.setText("07");
            TextField year = find("#stripe_expYear_tf");
            year.setText("16");

            //click "buy with card" button
            click((Node) find("#stripe_payWithCard_btn"));

            //click OK - Want a reciept?
            click((Node)find("Ja"));

            //click OK - save receipt under default name
            push(KeyCode.ENTER);

            //click OK - Button in alert
            click((Node)find("OK"));


        } catch (Exception e) {
            throw new GuiException(e.getMessage());
        }
    }

}
