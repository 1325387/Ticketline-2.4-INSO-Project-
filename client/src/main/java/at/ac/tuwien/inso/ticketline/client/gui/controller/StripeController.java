package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.extraObjects.PdfWriter;
import at.ac.tuwien.inso.ticketline.client.service.ReceiptService;
import at.ac.tuwien.inso.ticketline.client.service.StripeService;
import at.ac.tuwien.inso.ticketline.client.service.TicketService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.dto.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Optional;

/**
 * Created by Raphael on 13.06.2016.
 */
@Component
public class StripeController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(StripeController.class);


    private Alerts show = new Alerts();
    @Autowired
    private StripeService stripeService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private ReceiptService receiptService;

    private int amount;
    private List<TicketDto> tickets;
    private CustomerDto selectedCustomer;
    private EmployeeDto selectedEmployee;
    private MainScreenController mainScreenController;

    public void setData(int amount, List<TicketDto> tickets, CustomerDto customer, MainScreenController msc) {
        this.amount = amount;
        this.tickets = tickets;
        this.selectedCustomer = customer;
        this.selectedEmployee = MainScreenController.getEmployee();
        this.stripe_amount_tf.setText(this.amount + "");
        this.mainScreenController = msc;
    }


    @FXML
    private TextField stripe_cardNumber_tf;
    @FXML
    private TextField stripe_expMonth_tf;
    @FXML
    private TextField stripe_expYear_tf;
    @FXML
    private TextField stripe_amount_tf;
    @FXML
    private Button stripe_payWithCard_btn;
    @FXML
    private Button stripe_payInCash_btn;
    @FXML
    private Button stripe_cancel_btn;

    @FXML
    private Label stripe_cardNumber_lbl;
    @FXML
    private Label stripe_expirationMonth_lbl;
    @FXML
    private Label stripe_expirationYear_lbl;
    @FXML
    private Label stripe_amount_lbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stripe_amount_lbl.setText(BundleManager.getBundle().getString("total.lbl"));
        stripe_expirationMonth_lbl.setText(BundleManager.getBundle().getString("expirationmonth.lbl"));
        stripe_expirationYear_lbl.setText(BundleManager.getBundle().getString("expirationyear.lbl"));
        stripe_cardNumber_lbl.setText(BundleManager.getBundle().getString("cardnumber.lbl"));
        stripe_cancel_btn.setText(BundleManager.getBundle().getString("cancel.btn"));
        stripe_payWithCard_btn.setText(BundleManager.getBundle().getString("paywithcard.btn"));
        stripe_payInCash_btn.setText(BundleManager.getBundle().getString("payincash.btn"));
    }

    @FXML
    private void stripe_payInCash_button_clicked(ActionEvent event) {
        try {
            List<TicketIdentifierDto> soldTickets = this.ticketService.sellTicket(tickets, selectedCustomer, selectedEmployee);

            // addad by jayson: receipt function
            this.createReceipt(soldTickets);

            this.show.infoAlert(BundleManager.getMessageBundle().getString("sold.success"));

            closeWindow(event);

        } catch (ServiceException e) {
            this.show.exceptionAlert(BundleManager.getExceptionBundle().getString("payincash.exception") + ": " + e.getMessage());
        }
    }

    @FXML
    private void stripe_pay_button_clicked(ActionEvent event) {
        //mainScreenController.setSoldWithStripeSuccessfully(false);
        String errormessage = "";
        String cardNumber = stripe_cardNumber_tf.getText();
        Integer expMonth;
        Integer expYear;

        if (cardNumber.isEmpty() || stripe_expMonth_tf.getText().isEmpty() || stripe_expYear_tf.getText().isEmpty()) {
            if (cardNumber == null || cardNumber.length() == 0) {
                errormessage += BundleManager.getExceptionBundle().getString("stripecard.error") + "\n";
            }
            if (stripe_expMonth_tf.getText() == null || stripe_expMonth_tf.getText().length() == 0) {
                errormessage += BundleManager.getMessageBundle().getString("expmonth.warning") + "\n";
            }

            if (stripe_expYear_tf.getText() == null || stripe_expYear_tf.getText().length() == 0) {
                errormessage += BundleManager.getMessageBundle().getString("expyear.warning") + "\n";
            }
            this.show.inputErrorAlert(errormessage);
            return;
        }

        if (!cardNumber.isEmpty()) {
            if (!cardNumber.matches("[0-9\\s]*")) {
                this.show.inputErrorAlert(BundleManager.getExceptionBundle().getString("invalidcard.error"));
                return;
            }
        }


        try {
            if (stripe_expYear_tf.getText().length() != 2 || stripe_expMonth_tf.getText().length() != 2) {
                this.show.inputErrorAlert(BundleManager.getExceptionBundle().getString("stripeinput.error"));
                return;
            }
            expMonth = Integer.parseInt(stripe_expMonth_tf.getText());
            expYear = Integer.parseInt(stripe_expYear_tf.getText());
        } catch (Exception e) {
            this.show.inputErrorAlert(BundleManager.getExceptionBundle().getString("stripeinput.error"));
            return;
        }

        LocalDate localDate = LocalDate.MAX.now();
        int currentYear = localDate.getYear() - 2000;
        int currentMonth = localDate.getMonth().getValue();

        LOGGER.debug("Current: " + currentYear);
        LOGGER.debug("Current: " + currentMonth);


        if (currentYear > expYear) {
            this.show.exceptionAlert(BundleManager.getExceptionBundle().getString("expired.error"));
            return;
        } else if (currentYear == expYear) {
            if (currentMonth > expMonth || expMonth > 12) {
                this.show.exceptionAlert(BundleManager.getExceptionBundle().getString("expired.error"));
                return;
            }

        }
        if (expMonth > 12) {
            this.show.exceptionAlert(BundleManager.getExceptionBundle().getString("invalidmonth.error"));
            return;
        }


        /*
        if(!cardNumber.matches("[0-9\\s]*]")) {
            this.show.inputErrorAlert(BundleManager.getExceptionBundle().getString("invalidcard.error"));
        }*/

        LOGGER.debug("Selected customer is null " + selectedCustomer);

        try {
            StripeDto stripeDto = new StripeDto();
            stripeDto.setCardNumber(cardNumber);
            stripeDto.setExpMonth(expMonth);
            stripeDto.setExpYear(expYear);
            stripeDto.setAmountInCent(amount);
            stripeDto.setCustomer(selectedCustomer);
            stripeDto.setTickets(tickets);
            stripeService.pay(stripeDto);

            List<TicketIdentifierDto> soldTickets = this.ticketService.sellTicket(tickets, selectedCustomer, selectedEmployee);

            // addad by jayson: receipt function
            this.createReceipt(soldTickets);

            this.show.infoAlert(BundleManager.getMessageBundle().getString("sold.success"));
            //mainScreenController.setSoldWithStripeSuccessfully(true);
            closeWindow(event);

        } catch (ServiceException e) {
            this.show.exceptionAlert(BundleManager.getExceptionBundle().getString("paywithcard.exception") + ": " + e.getMessage());
        } catch (Exception e) {
            this.show.exceptionAlert(BundleManager.getExceptionBundle().getString("paywithcard.exception") + ": " + e.getMessage());
        }

    }

    private void createReceipt(List<TicketIdentifierDto> items) {
        Alert confirmPdf = new Alert(Alert.AlertType.CONFIRMATION);
        confirmPdf.setHeaderText(BundleManager.getBundle().getString("confirm.title"));
        confirmPdf.setContentText(BundleManager.getMessageBundle().getString("savereceipt.confirm"));

        ButtonType yes = new ButtonType(BundleManager.getMessageBundle().getString("confirmbutton.yes"));
        ButtonType no = new ButtonType(BundleManager.getMessageBundle().getString("confirmbutton.no"));

        confirmPdf.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> result = confirmPdf.showAndWait();
        if (result.get() == yes) {
            LOGGER.debug("EXPORT PDF.");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(BundleManager.getMessageBundle().getString("filechooser.title"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF file", "*.pdf"));
            fileChooser.setInitialFileName("default");

            File file = fileChooser.showSaveDialog(new Stage());
            if (file != null) {
                if (!file.getName().contains(".pdf")) {
                    LOGGER.error("File extension must be .pdf!");
                    return;
                }
                PdfWriter.setOutputPath(file.getPath());
                try {
                    receiptService.createPDF(items);
                } catch (ServiceException e) {
                    LOGGER.error("ERROR: ReceiptService @StripeController: " + e.getMessage(), e);
                    e.printStackTrace();
                }
            } else {
                LOGGER.error("No filename defined!");
            }
        }
    }

    @FXML
    private void stripe_cancel_button_clicked(ActionEvent event) {
        closeWindow(event);
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
