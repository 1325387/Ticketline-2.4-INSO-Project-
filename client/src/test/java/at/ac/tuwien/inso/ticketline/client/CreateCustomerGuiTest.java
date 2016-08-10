package at.ac.tuwien.inso.ticketline.client;

import at.ac.tuwien.inso.ticketline.client.exception.GuiException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.loadui.testfx.controls.TableViews;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;
import static org.loadui.testfx.Assertions.verifyThat;

/**
 * Testet das Erstellen von einem neuen Customer im Customer - Tab.
 *
 * @author Aysel Oeztuerk, 0927011
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(JUnit4.class)
public class CreateCustomerGuiTest extends TestSetUp {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateCustomerGuiTest.class);

    @Test
    public void selectTab (){
        LOGGER.debug("Open customer tab...");
        click((Node) find("#customer_tab_tab")); //hier den Tab angeben
        LOGGER.debug("Open tab successful...");
    }
    /**
     * Dieser Testfall testet das Erstellen von einem neuen Kunden.
     *
     * @author Aysel Oeztuerk, 0927011
     */
    @Test
    public void testCustomerTab() throws GuiException, InterruptedException {

        click((Node) find("#customer_createNew_btn"));
        LOGGER.info("Create New customer clicked. ");

        assertTrue(find("#saveCustomer_id_tf").isVisible());
        TextField firstname = find("#saveCustomer_firstname_tf"); firstname.setText("MarvinTest");
        TextField lastname = find("#saveCustomer_lastname_tf"); lastname.setText("Unknown");
        TextField phonenumber = find("#saveCustomer_phonenumber_tf"); phonenumber.setText("1234512345");
        ComboBox gender = find("#saveCustomer_gender_cb"); gender.getSelectionModel().select(0);

        click((Node) find("#saveCustomer_save_btn"));
        //Klick OK Button in Alert
        push(KeyCode.ENTER);

        assertTrue(find("#customer_customerTable_tv").isVisible());
        verifyThat(find("#customer_customerTable_tv"), TableViews.containsCell("MarvinTest"));
        verifyThat(find("#customer_customerTable_tv"), TableViews.containsCell("Unknown"));
        verifyThat(find("#customer_customerTable_tv"), TableViews.containsCell("1234512345"));

    }
    /**
     * Test cancel button.
     */
    @Test
    public void testCancelButton() {
        click((Node) find("#customer_createNew_btn"));
        LOGGER.info("Create New customer clicked. ");

        assertTrue(find("#saveCustomer_id_tf").isVisible());
        click((Node) find("#saveCustomer_cancel_btn"));
        assertTrue(find("#customer_customerTable_tv").isVisible());
    }
}

