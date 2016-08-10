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
 * @author Aysel Oeztuerk 0927011
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerMultipleTestGuiTest extends TestSetUp {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerMultipleTestGuiTest.class);

    @Test
    public void selectTab (){
        LOGGER.debug("Open customer tab...");
        click((Node) find("#customer_tab_tab")); //hier den Tab angeben
        LOGGER.debug("Open tab successful...");
    }

    @Test
    public void test01_tableVisible() throws GuiException {
        LOGGER.debug("tableVisible test started...");
        try {
            assertTrue(find("#customer_customerTable_tv").isVisible());
            LOGGER.info("Customer table is visible. ");
        } catch (Exception e) {
            throw new GuiException("Customer table is not visible! \n" + e.getMessage());
        }
    }

    @Test
    public void test02_editButtonVisible() throws GuiException {
        LOGGER.debug("editButtonVisible test started...");
        try {
            assertTrue(find("#customer_edit_btn").isVisible());
            LOGGER.info("Edit button is visible. ");
        } catch (Exception e) {
            throw new GuiException("Edit button is not visible! \n" + e.getMessage());
        }
    }

    @Test
    public void test03_creatNewButtonVisible() throws GuiException {
        LOGGER.debug("creatNewButtonVisible test started...");
        try {
            assertTrue(find("#customer_createNew_btn").isVisible());
            LOGGER.info("Create New button is visible. ");
        } catch (Exception e) {
            throw new GuiException("Create New button is not visible! \n" + e.getMessage());
        }
    }

}

