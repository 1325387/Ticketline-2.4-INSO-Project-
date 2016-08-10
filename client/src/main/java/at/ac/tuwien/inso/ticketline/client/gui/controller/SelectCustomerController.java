package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.CustomerService;
import at.ac.tuwien.inso.ticketline.client.service.EmployeeService;
import at.ac.tuwien.inso.ticketline.client.service.StripeService;
import at.ac.tuwien.inso.ticketline.client.service.TicketService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.ticketline.dto.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Controller for selecting a customer to sell or reserve tickets.
 *
 * @author Aysel Oeztuerk 0927011
 */
@Component
public class SelectCustomerController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SelectCustomerController.class);
    private Alerts show = new Alerts();

    @Autowired
    private CustomerService customerService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private SpringFxmlLoader springFxmlLoader;
    //@Autowired
    //private EmployeeService employeeService;

    @FXML
    public TableView<CustomerDto> sellReserve_customerTable_tv;
    @FXML
    public TableColumn sellReserve_customerTable_id_tc;
    @FXML
    public TableColumn sellReserve_customerTable_firstname_tc;
    @FXML
    public TableColumn sellReserve_customerTable_lastname_tc;
    @FXML
    public TableColumn sellReserve_customerTable_phone_tc;
    @FXML
    public TableColumn sellReserve_customerTable_dateOfBirth_tc;
    @FXML
    public TextField sellReserve_searchByCustomerName_tf;
    @FXML
    public Button sellReserve_search_btn;
    @FXML
    public Button sellReserve_clear_btn;
    @FXML
    public Button sellReserve_cancel_btn;
    @FXML
    public Button sellReserve_createNewCustomer_btn;
    @FXML
    public Button sellReserve_finish_btn;
    @FXML
    public Button sellReserve_edit_btn;

    @FXML
    public Label sellReserve_searchForName_lbl;
    @FXML
    public Label sellReserve_customer_lbl;
    @FXML
    public Label sellReserve_txt_lbl;


    private List<TicketDto> tickets;
    private Boolean sell;
    private final ObservableList<CustomerDto> customerList = observableArrayList();
    private CustomerDto selectedCustomer = null;

    public void setData(List<TicketDto> setTickets, boolean setSell) {
        this.tickets = setTickets;
        this.sell = setSell;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL url, ResourceBundle resBundle) {
        sellReserve_searchForName_lbl.setText(BundleManager.getBundle().getString("searchforname.lbl"));
        sellReserve_customer_lbl.setText(BundleManager.getBundle().getString("customer.lbl"));
        sellReserve_txt_lbl.setText(BundleManager.getBundle().getString("text.lbl"));
        sellReserve_searchByCustomerName_tf.setPromptText(BundleManager.getBundle().getString("searchnamefield.tf"));
        sellReserve_customerTable_id_tc.setText(BundleManager.getBundle().getString("customerid.tc"));
        sellReserve_customerTable_firstname_tc.setText(BundleManager.getBundle().getString("firstname.tc"));
        sellReserve_customerTable_lastname_tc.setText(BundleManager.getBundle().getString("lastname.tc"));
        sellReserve_customerTable_phone_tc.setText(BundleManager.getBundle().getString("phone.tc"));
        sellReserve_customerTable_dateOfBirth_tc.setText(BundleManager.getBundle().getString("dob.tc"));
        sellReserve_cancel_btn.setText(BundleManager.getBundle().getString("cancel.btn"));
        sellReserve_clear_btn.setText(BundleManager.getBundle().getString("clear.btn"));
        sellReserve_search_btn.setText(BundleManager.getBundle().getString("search.btn"));
        sellReserve_createNewCustomer_btn.setText(BundleManager.getBundle().getString("createnewcustomer.btn"));
        sellReserve_edit_btn.setText(BundleManager.getBundle().getString("edit.btn"));
        loadCustomerTable();
        tableDoubleClick(sellReserve_customerTable_tv);
    }

    @FXML
    private void sellReserve_search_btn_clicked() {
        try {
            if (sellReserve_searchByCustomerName_tf.getText().isEmpty()) {
                this.show.warningAlert(BundleManager.getMessageBundle().getString("entersearchword.warning"));
            } else {
                List<CustomerDto> foundCustomersList =
                        this.customerService.getCustomerByName(sellReserve_searchByCustomerName_tf.getText());
                customerList.clear();
                customerList.addAll(foundCustomersList);
            }
        } catch (ServiceException e) {
            this.show.exceptionAlert(e.getMessage());
        }
    }

    @FXML
    private void sellReserve_clear_btn_clicked() {
        loadCustomerTable();
    }

    @FXML
    private void sellReserve_cancel_btn_clicked(ActionEvent event) {
        closeWindow(event);
    }

    @FXML
    private void sellReserve_createNewCustomer_btn_clicked() {
        Stage createNewCustomerStage = new Stage();
        createNewCustomerStage.initModality(Modality.APPLICATION_MODAL);

        SpringFxmlLoader.LoadWrapper sfLoader = springFxmlLoader.loadAndWrap("/gui/fxml/saveCustomer.fxml");

        createNewCustomerStage.setScene(new Scene((Parent) sfLoader.getLoadedObject()));
        createNewCustomerStage.setTitle(BundleManager.getBundle().getString("createnewcustomer.info"));

        CustomerController controller = (CustomerController) sfLoader.getController();
        //null means, load no customer
        controller.initialize(null);

        createNewCustomerStage.showAndWait();
        loadCustomerTable();
    }

    @FXML
    private void sellReserve_finish_btn_clicked(ActionEvent event) {
        CustomerDto selectedCustomer = sellReserve_customerTable_tv.getSelectionModel().getSelectedItem();
        LOGGER.debug("Selected customer: " + selectedCustomer);
        if (selectedCustomer != null) {
            if (sell) {
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
                controller.setData(priceInCent, tickets, selectedCustomer, null);

                stripeStage.show();
                closeWindow(event);
            } else { //reserve
                try {
                    LOGGER.debug("Reserve tickets called.");
                    //@author Sissi Zhan 1325880, Raphael Schotola 1225193
                    //Reserve tickets for anonymous customer -> customer = null
                    MessageDto reservation = this.ticketService.reserveTicket(tickets,
                            selectedCustomer);

                    int reservationNumber = Integer.parseInt(reservation.getText());

                    closeWindow(event);

                    this.show.infoAlert(BundleManager.getMessageBundle().getString("reserved.success")+"\n \n"+
                            BundleManager.getMessageBundle().getString("reservationnumber.info") + " " +
                            reservationNumber);
                    LOGGER.info("Tickets reserved successfully");
                } catch (ServiceException e) {
                    this.show.exceptionAlert(BundleManager.getExceptionBundle().getString("reserved.error")+": "+e.getMessage());
                }
            }
        } else {
            show.warningAlert(BundleManager.getMessageBundle().getString("selectcustomer.warning"));
            LOGGER.warn("No customer selected! " + selectedCustomer);
        }
    }

    @FXML
    private void sellReserve_edit_btn_clicked() {
        selectedCustomer = sellReserve_customerTable_tv.getSelectionModel().getSelectedItem();
        LOGGER.debug("Selected customer: " + selectedCustomer);
        if (selectedCustomer != null) {
            LOGGER.debug("Edit customer called...");

            Stage createNewCustomerStage = new Stage();
            createNewCustomerStage.initModality(Modality.APPLICATION_MODAL);

            SpringFxmlLoader.LoadWrapper sfLoader = springFxmlLoader.loadAndWrap("/gui/fxml/saveCustomer.fxml");
            createNewCustomerStage.setScene(new Scene((Parent) sfLoader.getLoadedObject()));
            createNewCustomerStage.setTitle(BundleManager.getBundle().getString("updatenewcustomer.info"));

            CustomerController controller = (CustomerController) sfLoader.getController();
            controller.initialize(selectedCustomer);

            createNewCustomerStage.showAndWait();

            loadCustomerTable();

        } else {
            show.warningAlert(BundleManager.getMessageBundle().getString("selectcustomer.warning"));
            LOGGER.warn("No customer selected!");
        }
    }

    @FXML
    private void loadCustomerTable() {
        LOGGER.info("load CustomerTable()");
        sellReserve_searchByCustomerName_tf.setText("");
        initCustomerTable();

        List<CustomerDto> customers;
        try {
            customers = customerService.getAllCustomers();
        } catch (ServiceException e) {
            LOGGER.error("Could not retrieve customers: {}", e.getMessage());
            show.exceptionAlert(BundleManager.getExceptionBundle().getString("loadcustomertable.exception"));
            return;
        }
        customerList.clear();
        customerList.addAll(customers);
    }

    @FXML
    private void initCustomerTable() {
        LOGGER.info("init CustomerTable()");

        sellReserve_customerTable_id_tc.setCellValueFactory(new PropertyValueFactory<CustomerDto, Integer>("id"));
        sellReserve_customerTable_firstname_tc.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("firstname"));
        sellReserve_customerTable_lastname_tc.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("lastname"));
        sellReserve_customerTable_phone_tc.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("phoneNumber"));
        sellReserve_customerTable_dateOfBirth_tc.setCellValueFactory(new PropertyValueFactory<CustomerDto, Date>("dateOfBirth"));
        sellReserve_customerTable_tv.setItems(customerList);

        formatDate(sellReserve_customerTable_dateOfBirth_tc);
    }

    private void formatDate(TableColumn tc) {
        DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        tc.setCellFactory(column -> {
            return new TableCell<CustomerDto, Date>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        LocalDate date = item.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        setText(myDateFormatter.format(date));
                    }
                }
            };
        });
    }

    private CustomerDto doubleClickedCustomer = null;

    /**
     * Detects doubleclick on row of a TableView.
     *
     * @param table the table.
     */
    private void tableDoubleClick(TableView table) {
        table.setRowFactory(tv -> {
            TableRow<CustomerDto> row = new TableRow<>();
            TableRow<ShowDto> show_row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        doubleClickedCustomer = row.getItem();
                    sellReserve_edit_btn_clicked();
                        doubleClickedCustomer = null;
                }
            });
            return row;
        });
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
