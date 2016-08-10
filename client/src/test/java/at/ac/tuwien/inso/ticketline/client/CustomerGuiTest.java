package at.ac.tuwien.inso.ticketline.client;

import at.ac.tuwien.inso.ticketline.client.exception.GuiException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

/**
 * Testet Customer - Tab. Überprüft die Anzeige von Customer-Tab, nach dem Login.
 *
 * @author Aysel Oeztuerk 0927011
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerGuiTest extends TestSetUp {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerGuiTest.class);

    @Test
    public void selectTab (){
        LOGGER.debug("Open customer tab...");
        click((Node) find("#customer_tab_tab")); //hier den Tab angeben
        LOGGER.debug("Open tab successful...");
    }

    @Test
    public void testCustomerTab() throws GuiException {
        try {
            assertTrue(find("#customer_customerTable_tv").isVisible());
            LOGGER.info("Customer table is visible. ");
        } catch (Exception e) {
            throw new GuiException("Customer table is not visible! \n" + e.getMessage());
        }
        try {
            assertTrue(find("#customer_edit_btn").isVisible());
            LOGGER.info("Edit button is visible. ");
        } catch (Exception e) {
            throw new GuiException("Edit button is not visible! \n" + e.getMessage());
        }
        try {
            assertTrue(find("#customer_createNew_btn").isVisible());
            LOGGER.info("Create New button is visible. ");
        } catch (Exception e) {
            throw new GuiException("Create New button is not visible! \n" + e.getMessage());
        }
    }
}

