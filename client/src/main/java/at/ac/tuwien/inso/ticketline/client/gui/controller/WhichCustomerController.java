package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.TicketService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.TicketDto;
import at.ac.tuwien.inso.ticketline.dto.TicketIdentifierDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for selecting anonymous or new or existent customer.
 *
 * @author Aysel Oeztuerk 0927011
 */
@Component
public class WhichCustomerController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhichCustomerController.class);
    private Alerts show = new Alerts();

    @Autowired
    private TicketService ticketService;
    @Autowired
    private SpringFxmlLoader springFxmlLoader;

    @FXML
    public Button whichCustomer_anonymousCustomer_btn;
    @FXML
    public Button whichCustomer_newOrExistentCustomer_btn;
    @FXML
    public Button whichCustomer_cancel_btn;

    private List<TicketDto> tickets;
    private Boolean sell;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        whichCustomer_anonymousCustomer_btn.setText(BundleManager.getBundle().getString("anonymous.btn"));
        whichCustomer_newOrExistentCustomer_btn.setText(BundleManager.getBundle().getString("neworexistent.btn"));
        whichCustomer_cancel_btn.setText(BundleManager.getBundle().getString("cancel.btn"));
    }

    public void setData(List<TicketDto> setTickets, boolean setSell) {
        this.tickets = setTickets;
        this.sell = setSell;

        LOGGER.debug("Boolean:" + sell);
        for (TicketDto t : tickets) {
            LOGGER.debug("Ticket id:" + t.getId());
            LOGGER.debug("Description:" + t.getDescription());
            LOGGER.debug("Seat id:" + t.getSeatId());
        }
    }

    public void whichCustomer_anonymousCustomer_btn_clicked(ActionEvent event) {
        LOGGER.debug("Boolean:" + sell);
        for (TicketDto t : tickets) {
            LOGGER.debug("Ticket id:" + t.getId());
            LOGGER.debug("Description:" + t.getDescription());
            LOGGER.debug("Seat id:" + t.getSeatId());
        }
        if (sell) {
            //TODO Zahlungsmöglichkeiten
            Stage stripeStage = new Stage();
            stripeStage.initModality(Modality.APPLICATION_MODAL);
            SpringFxmlLoader.LoadWrapper sfLoader = springFxmlLoader.loadAndWrap("/gui/fxml/stripe.fxml");
            stripeStage.setScene(new Scene((Parent) sfLoader.getLoadedObject()));
            stripeStage.setTitle(BundleManager.getBundle().getString("inputpayment.info"));
            StripeController controller = (StripeController) sfLoader.getController();

            int priceInCent = 0;
            for(TicketDto t:tickets){
                priceInCent += t.getPrice();
            }
            controller.setData(priceInCent, tickets, null, null);

            stripeStage.show();
            closeWindow(event);

        } else { //reserve
            try {
                //@author Sissi Zhan 1325880, Raphael Schotola 1225193
                //Reserve tickets for anonymous customer -> customer = null
                MessageDto reservation = this.ticketService.reserveTicket(tickets, null);

                int reservationNumber = Integer.parseInt(reservation.getText());
                LOGGER.debug("reservationNumber: " + reservationNumber);

                closeWindow(event);

                this.show.infoAlert(BundleManager.getMessageBundle().getString("reserved.success")+"\n \n"+
                        BundleManager.getMessageBundle().getString("reservationnumber.info") + " " +
                        reservationNumber);
                LOGGER.info("Tickets reserved successfully");
            } catch (ServiceException e) {
                this.show.exceptionAlert(BundleManager.getExceptionBundle().getString("reserved.error")+": "+e.getMessage());
            }
        }

    }

    public void whichCustomer_newOrExistentCustomer_btn_clicked(ActionEvent event) {
        //@author Aysel Oeztuerk 0927011
        //setData: selectedTickets and true (true means sell tickets and false means reserve tickets)
        //SelectCustomerController.setData(tickets, sell);
        Stage selectCustomerStage = new Stage();
        selectCustomerStage.initModality(Modality.APPLICATION_MODAL);
        SpringFxmlLoader.LoadWrapper sfLoader = springFxmlLoader.loadAndWrap
                ("/gui/fxml/sellOrReserveTicketsToNewOrExistentCustomer.fxml");
        selectCustomerStage.setScene(new Scene((Parent) sfLoader.getLoadedObject()));
        selectCustomerStage.setTitle(BundleManager.getBundle().getString("selectcustomer.info"));
        SelectCustomerController controller = (SelectCustomerController) sfLoader.getController();
        controller.setData(tickets, sell);

        selectCustomerStage.show();
        closeWindow(event);
    }

    public void whichCustomer_cancel_btn_clicked(ActionEvent event) {
        closeWindow(event);
    }

    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


}
