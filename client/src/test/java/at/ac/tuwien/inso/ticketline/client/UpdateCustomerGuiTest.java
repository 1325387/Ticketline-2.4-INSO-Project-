package at.ac.tuwien.inso.ticketline.client;

import at.ac.tuwien.inso.ticketline.client.exception.GuiException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.loadui.testfx.controls.TableViews;
import org.loadui.testfx.exceptions.NoNodesFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.loadui.testfx.Assertions.verifyThat;

/**
 * Testet das Bearbeiten von einem bestehenden Customer im Customer - Tab.
 *
 * @author Aysel Oeztuerk 0927011
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(JUnit4.class)
public class UpdateCustomerGuiTest extends TestSetUp {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateCustomerGuiTest.class);

    @Test
    public void selectTab (){
        LOGGER.debug("Open customer tab...");
        click((Node) find("#customer_tab_tab")); //hier den Tab angeben
        LOGGER.debug("Open tab successful...");
    }

    @Test
    public void testCustomerTab() throws GuiException, InterruptedException {

        //select an existent customer and click edit button
        TableRow rw = row("#customer_customerTable_tv", 0);
        click(rw);
        click((Node) find("#customer_edit_btn"));
        LOGGER.info("Edit button clicked. ");

        //enter changes + click save
        assertTrue(find("#saveCustomer_id_tf").isVisible());
        TextField firstname = find("#saveCustomer_firstname_tf"); firstname.setText("Change");
        TextField lastname = find("#saveCustomer_lastname_tf"); lastname.setText("Change");
        TextField phonenumber = find("#saveCustomer_phonenumber_tf"); phonenumber.setText("1111111111");
        ComboBox gender = find("#saveCustomer_gender_cb"); gender.getSelectionModel().select(0);

        click((Node) find("#saveCustomer_save_btn"));
        //Klick OK Button in Alert
        push(KeyCode.ENTER);

        //verify that the changes took over
        assertTrue(find("#customer_customerTable_tv").isVisible());
        verifyThat(find("#customer_customerTable_tv"), TableViews.containsCell("Change"));
        verifyThat(find("#customer_customerTable_tv"), TableViews.containsCell("Change"));
        verifyThat(find("#customer_customerTable_tv"), TableViews.containsCell("1111111111"));
    }

    /**
     * Test cancel button.
     */
    @Test
    public void testCancelButton() {
        TableRow rw = row("#customer_customerTable_tv", 0);
        click(rw);
        click((Node) find("#customer_edit_btn"));
        LOGGER.info("Edit button clicked. ");

        assertTrue(find("#saveCustomer_id_tf").isVisible());

        click((Node) find("#saveCustomer_cancel_btn"));
        assertTrue(find("#customer_customerTable_tv").isVisible());
    }

    //Dies Klassen sind von org.loadui.testfx.Assertions.verifyThat entnommen worden, da TableViews diese Klassen
    // noch nicht public gestellt haben.

    /**
     * @param tableSelector Selektor zur Identifikation der Tabelle.
     * @param row           Zeilennummer
     * @return Die entsprechende Zeile.
     */
    static TableRow<?> row(String tableSelector, int row) {

        TableView<?> tableView = getTableView(tableSelector);

        List<Node> current = tableView.getChildrenUnmodifiable();
        while (current.size() == 1) {
            current = ((Parent) current.get(0)).getChildrenUnmodifiable();
        }

        current = ((Parent) current.get(1)).getChildrenUnmodifiable();
        while (!(current.get(0) instanceof TableRow)) {
            current = ((Parent) current.get(0)).getChildrenUnmodifiable();
        }

        Node node = current.get(row);
        if (node instanceof TableRow) {
            return (TableRow<?>) node;
        }
        else {
            throw new RuntimeException("Expected Group with only TableRows as children");
        }
    }

    /**
     * @param tableSelector Ein CSS-Selektor oder Label.
     * @return Die TableView, sofern sie anhand des Selektors gefunden wurde.
     */
    static TableView<?> getTableView(String tableSelector) {
        Node node = find(tableSelector);
        if (!(node instanceof TableView)) {
            throw new NoNodesFoundException(tableSelector + " selected " + node +
                    " which is not a TableView!");
        }
        return (TableView<?>) node;
    }

}

