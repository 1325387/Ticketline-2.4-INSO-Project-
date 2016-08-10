package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.dto.PerformanceFilterDto;
import at.ac.tuwien.inso.ticketline.client.service.*;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.ticketline.dto.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
//import org.controlsfx.control.NotificationPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Controller for the Main Screen
 *
 * @author Raphael Schotola 1225193
 */
@Component
public class MainScreenController implements Initializable {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * ------ General stuff --------------------------------------------------------------------------------------------
     * -----------------------------------------------------------------------------------------------------------------
     */

    private static final Logger LOGGER = LoggerFactory.getLogger(MainScreenController.class);

    @Autowired
    private SpringFxmlLoader springFxmlLoader;

    @Autowired
    private PerformanceService performanceService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private AuthService authService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private ShowService showService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private Top10Service top10Service;
    @Autowired
    private SeatService seatService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private LocalStorageService localStorageService;

    @FXML
    public TabPane ticketline_tabPane;
    @FXML
    public GridPane tickets_gridPane;
    @FXML
    public Pane tickets_pane;

    @FXML
    public Text employeeName_txt;
    @FXML
    public Button logout_btn;


    private static String _employeeName;

    private static EmployeeDto _employee;

    private Alerts alertCreator = new Alerts();
    private final ImageView warningImage = new ImageView(new Image(LoginController.class.getResourceAsStream
            ("/image/icon/warning.png")));

    //private NotificationPane notificationPane;

    /**
     * @author Lina Wang 1326922
     */
    public static void setEmployeeName(String employeeName) {
        _employeeName = employeeName;
    }

    /**
     * @author Jayson Asenjo 1325387
     */
    public static void setEmployee(EmployeeDto employee){
        _employee = employee;
    }
    public static EmployeeDto getEmployee(){
        return _employee;
    }

    private enum Month {
        JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
    }

    Boolean errorShown = false;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL url, ResourceBundle resBundle) {
        LOGGER.debug("Initialization");

        //@author Lina Wang
        //Internationalization
        logout_btn.setText(BundleManager.getBundle().getString("logout"));
        news_tab_tab.setText(BundleManager.getBundle().getString("news.tab"));
        customer_tab_tab.setText(BundleManager.getBundle().getString("customers.tab"));
        ticket_tab_tab.setText(BundleManager.getBundle().getString("tickets.tab"));
        event_tab_tab.setText(BundleManager.getBundle().getString("events.tab"));
        top10_tab_tab.setText(BundleManager.getBundle().getString("top10.tab"));
        newsTab_showUnread_rb.setText(BundleManager.getBundle().getString("showunread.rb"));
        newsTab_showAll_rb.setText(BundleManager.getBundle().getString("showallnews.rb"));

        customer_searchForName_lbl.setText(BundleManager.getBundle().getString("searchforname.lbl"));
        customer_text_lbl.setText(BundleManager.getBundle().getString("text.lbl"));
        customer_search_btn.setText(BundleManager.getBundle().getString("search.btn"));
        customer_clear_btn.setText(BundleManager.getBundle().getString("clear.btn"));
        customer_edit_btn.setText(BundleManager.getBundle().getString("edit.btn"));
        customer_createNew_btn.setText(BundleManager.getBundle().getString("createnewcustomer.btn"));
        customer_customerTable_id_tc.setText(BundleManager.getBundle().getString("customerid.tc"));
        customer_customerTable_firstname_tc.setText(BundleManager.getBundle().getString("firstname.tc"));
        customer_customerTable_lastname_tc.setText(BundleManager.getBundle().getString("lastname.tc"));
        customer_customerTable_phoneNumber_tc.setText(BundleManager.getBundle().getString("phone.tc"));
        //customer_customerTable_address_tc.setText(BundleManager.getBundle().getString("address.tc"));
        customer_customerTable_dateOfBirth_tc.setText(BundleManager.getBundle().getString("dob.tc"));
        customer_searchByCustomerName_tf.setPromptText(BundleManager.getBundle().getString("searchnamefield.tf"));

        ticket_customernumber_lbl.setText(BundleManager.getBundle().getString("customernumber.lbl"));
        ticket_reservationnumber_lbl.setText(BundleManager.getBundle().getString("reservationnumber.lbl"));
        ticket_customername_lbl.setText(BundleManager.getBundle().getString("customername.lbl"));
        ticket_selectCustomer_lbl.setText(BundleManager.getBundle().getString("selectcustomer.lbl"));
        ticket_or1_lbl.setText(BundleManager.getBundle().getString("or.lbl"));
        ticket_or2_lbl.setText(BundleManager.getBundle().getString("or.lbl"));
        ticket_ticketTable_ticketId_tc.setText(BundleManager.getBundle().getString("ticketid.tc"));
        ticket_ticketTable_show_tc.setText(BundleManager.getBundle().getString("show.tc"));
        ticket_ticketTable_seatNumber_tc.setText(BundleManager.getBundle().getString("seatnumber.tc"));
        ticket_ticketTable_price_tc.setText(BundleManager.getBundle().getString("price.tc"));
        ticket_ticketTable_status_tc.setText(BundleManager.getBundle().getString("status.tc"));
        ticket_findTicket_btn.setText(BundleManager.getBundle().getString("search.btn"));
        ticket_clear_btn.setText(BundleManager.getBundle().getString("clear.btn"));
        ticket_cancelTicket_btn.setText(BundleManager.getBundle().getString("cancelseats.btn"));
        ticket_sellSelected_btn.setText(BundleManager.getBundle().getString("sellseats.btn"));

        event_artist_lbl.setText(BundleManager.getBundle().getString("artist.lbl"));
        event_performanceName_lbl.setText(BundleManager.getBundle().getString("performancename.lbl"));
        event_date_lbl.setText(BundleManager.getBundle().getString("date.lbl"));
        event_town_lbl.setText(BundleManager.getBundle().getString("town.lbl"));
        event_plz_lbl.setText(BundleManager.getBundle().getString("plz.lbl"));
        event_time_lbl.setText(BundleManager.getBundle().getString("time.lbl"));
        event_performanceType_lbl.setText(BundleManager.getBundle().getString("performancetype.lbl"));
        event_price_lbl.setText(BundleManager.getBundle().getString("price.lbl")+"*");
        event_upToPrice_lbl.setText("* " +BundleManager.getBundle().getString("uptoprice.lbl"));
        event_clear_btn.setText(BundleManager.getBundle().getString("clear.btn"));
        event_search_btn.setText(BundleManager.getBundle().getString("search.btn"));
        event_availableTickets_btn.setText(BundleManager.getBundle().getString("availabletickets.btn"));
        event_performanceTable_performanceName_tc.setText(BundleManager.getBundle().getString("performance.tc"));
        event_performanceTable_description_tc.setText(BundleManager.getBundle().getString("description.tc"));
        event_performanceTable_duration_tc.setText(BundleManager.getBundle().getString("duration.tc"));
        event_showTable_showId_tc.setText(BundleManager.getBundle().getString("showid.tc"));
        event_showTable_showDate_tc.setText(BundleManager.getBundle().getString("date.tc"));

        top10_Shows_availableTickets_btn.setText(BundleManager.getBundle().getString("availabletickets.btn"));
        top10_Performances_availableTickets_btn.setText(BundleManager.getBundle().getString("availabletickets.btn"));
        top10_top10_lbl.setText(BundleManager.getBundle().getString("top10.lbl"));
        top10_performances_tab.setText(BundleManager.getBundle().getString("performances.tab"));
        top10_shows_tab.setText(BundleManager.getBundle().getString("shows.tab"));
        top10_topTenShowTable_showID_tc.setText(BundleManager.getBundle().getString("showid.tc"));
        top10_topTenShowTable_performance_tc.setText(BundleManager.getBundle().getString("performance.tc"));
        top10_topTenShowTable_description_tc.setText(BundleManager.getBundle().getString("description.tc"));
        top10_Performances_performanceTable_performanceID_tc.setText(BundleManager.getBundle().getString("performanceid.tc"));
        top10_Performances_performanceTable_performanceName_tc.setText(BundleManager.getBundle().getString("performance.tc"));
        top10_Performances_performanceTable_description_tc.setText(BundleManager.getBundle().getString("description.tc"));
        top10_Performances_showTable_showId_tc.setText(BundleManager.getBundle().getString("showid.tc"));
        top10_Performances_showTable_showDate_tc.setText(BundleManager.getBundle().getString("date.tc"));

        eventShowTableInitialised = false;
        ticketTicketTableInitialised = false;
        eventPerformanceTableInitialised = false;
        soldWithStripeSuccessfully = false;
        newsMapInitialised = false;
        this.newsMap = new HashMap<>();

        customerList.clear();
        ticketTicketList.clear();
        eventShowList.clear();
        foundTopTenShowsList.clear();

        if (null != news_newsBox_vb) {
            if (newsTab_showUnread_rb.isSelected()) {
                LOGGER.debug("Init Unread News");
                initUnreadNews();
            } else if (newsTab_showAll_rb.isSelected()) {
                LOGGER.debug("Init all news");
                initAllNews();
            }
        }
        //@author Aysel Öztürk 0927011
        //tab Tickets
        /*notificationPane = new NotificationPane(tickets_pane.getChildren().get(0));
        tickets_pane.getChildren().clear();
        tickets_pane.getChildren().add(notificationPane);
        notificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);

        //@author Aysel Öztürk 0927011
        //tab top10
        top10_notificationPane = new NotificationPane(top10_pane.getChildren().get(0));
        top10_pane.getChildren().clear();
        top10_pane.getChildren().add(top10_notificationPane);
        top10_notificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
        //top10_notificationPane.closeButtonVisibleProperty().setValue(false);
        */

        //@author Aysel Öztürk 0927011
        // Top 10 Tab: set categories
        top10_selectCategory_cb.setItems(FXCollections.observableArrayList(
                "MOVIE", "FESTIVAL", "CONCERT", "MUSICAL", "OPER", "THEATER"));
        top10_selectYear_cb.getItems().add(LocalDate.now().getYear() - 1);
        top10_selectYear_cb.getItems().add(LocalDate.now().getYear());


        employeeName_txt.setText(BundleManager.getBundle().getString("hello.txt") + " " + _employeeName + "!");

        // set employee
    //    try {
     //       _employee = employeeService.getEmployee(_employee.getUsername(), _employee.getFirstname(), _employee.getLastname());
     //   } catch (ServiceException e) {
     //       LOGGER.error("ERROR: Can´t find employee: "+e.getMessage(), e);
      //      e.printStackTrace();
       // }
    }

    @FXML
    public void logout_btn_clicked(ActionEvent event) {

        Stage loginStage = new Stage();
        loginStage.setScene(new Scene((Parent) springFxmlLoader.load("/gui/fxml/login.fxml")));
        loginStage.show();
        try {
            addReadNews();
            this.authService.logout();
        } catch (ServiceException e) {
            this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("error.logout"));
        }
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private Button event_availableTickets_btn;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * ------ NewsBox --------------------------------------------------------------------------------------------------
     * -----------------------------------------------------------------------------------------------------------------
     */
    @FXML
    private Tab news_tab_tab;

    @FXML
    private VBox news_newsBox_vb;

    @FXML
    private RadioButton newsTab_showUnread_rb;
    @FXML
    private RadioButton newsTab_showAll_rb;

    private boolean newsMapInitialised = false;

    public HashMap<CheckBox, NewsDto> newsMap = new HashMap<>();

    /**
     * @author Lina Wang, 1326922
     */
    private void initUnreadNews() {
        news_newsBox_vb.getChildren().clear();
        if (!newsMapInitialised) {
            try {
                List<NewsDto> unreadNews = this.newsService.getUnreadNewsOfEmployee(_employeeName);
                for (Iterator<NewsDto> newsDtoIterator = unreadNews.iterator(); newsDtoIterator.hasNext(); ) {
                    NewsDto newsDto = newsDtoIterator.next();
                    ObservableList<Node> vbNewsBoxChildren = news_newsBox_vb.getChildren();
                    CheckBox cb = new CheckBox();
                    cb.setText(BundleManager.getBundle().getString("markasread"));
                    vbNewsBoxChildren.add(cb);
                    vbNewsBoxChildren.add(generateNewsElement(newsDto));
                    newsMap.put(cb, newsDto);
                    if (newsDtoIterator.hasNext()) {
                        Separator separator = new Separator();
                        vbNewsBoxChildren.add(separator);
                    }
                }

                newsMapInitialised = true;

            } catch (ServiceException e) {
                this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("loadunreadnews.error"));
            }
        } else {
            for (Iterator it = this.newsMap.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry entry = (Map.Entry) it.next();
                ObservableList<Node> vbNewsBoxChildren = news_newsBox_vb.getChildren();
                vbNewsBoxChildren.add((CheckBox) entry.getKey());
                vbNewsBoxChildren.add(generateNewsElement((NewsDto) entry.getValue()));
                if (it.hasNext()) {
                    Separator separator = new Separator();
                    vbNewsBoxChildren.add(separator);
                }
            }
        }

    }

    /**
     * @author Lina Wang, 1326922
     */
    private void initAllNews() {
        news_newsBox_vb.getChildren().clear();
        try {
            List<NewsDto> allNews = this.newsService.getNews();
            for (Iterator<NewsDto> newsDtoIterator = allNews.iterator(); newsDtoIterator.hasNext(); ) {
                NewsDto newsDto = newsDtoIterator.next();
                ObservableList<Node> vbNewsBoxChildren = news_newsBox_vb.getChildren();
                vbNewsBoxChildren.add(generateNewsElement(newsDto));
                if (newsDtoIterator.hasNext()) {
                    Separator separator = new Separator();
                    vbNewsBoxChildren.add(separator);
                }
            }
        } catch (ServiceException e) {
            this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("loadallnews.error"));
        }
    }


    /**
     * Generates the news element.
     *
     * @param newsDto the news dto
     * @return the node
     */
    private Node generateNewsElement(NewsDto newsDto) {
        SpringFxmlLoader.LoadWrapper loadWrapper = springFxmlLoader.loadAndWrap("/gui/fxml/newsElement.fxml");
        ((NewsElementController) loadWrapper.getController()).initializeData(newsDto);
        return (Node) loadWrapper.getLoadedObject();
    }


    /**
     * @author Lina Wang, 1326922
     * <p>
     * Add read news to local storage of employee
     */
    private void addReadNews() {
        LOGGER.debug("Mark as Read");
        Iterator it = this.newsMap.entrySet().iterator();
        List<NewsDto> readNews = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            CheckBox cb = (CheckBox) entry.getKey();
            if (cb.isSelected()) {
                readNews.add((NewsDto) entry.getValue());
                LOGGER.debug("this checkbox is selected");
            }
        }
        try {
            this.localStorageService.updateLocalStorage(_employeeName, readNews);
        } catch (ServiceException e) {
            this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("addreadnews.error"));
        }
    }

    @FXML
    public void newsTab_showUnread_rb_clicked() {
        LOGGER.info("Show Unread Button selected");
        initUnreadNews();
    }

    @FXML
    public void newsTab_showAll_rb_clicked() {
        LOGGER.info("Show All Button selected");
        initAllNews();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * ------ Customer -------------------------------------------------------------------------------------------------
     * -----------------------------------------------------------------------------------------------------------------
     */
    @FXML
    private Tab customer_tab_tab;
    @FXML
    private TableView customer_customerTable_tv;
    @FXML
    private TableColumn customer_customerTable_firstname_tc;
    @FXML
    private TableColumn customer_customerTable_lastname_tc;
    @FXML
    private TableColumn customer_customerTable_phoneNumber_tc;
    @FXML
    private TableColumn customer_customerTable_dateOfBirth_tc;
    @FXML
    private TableColumn customer_customerTable_id_tc;
    @FXML
    private TextField customer_searchByCustomerName_tf;
    @FXML
    private Label customer_searchForName_lbl;
    @FXML
    private Label customer_text_lbl;
    @FXML
    private Button customer_search_btn;
    @FXML
    private Button customer_clear_btn;
    @FXML
    private Button customer_edit_btn;
    @FXML
    private Button customer_createNew_btn;


    private final ObservableList<CustomerDto> customerList = observableArrayList();

    /**
     * @author Aysel Oeztuerk 0927011
     */
    @FXML
    private void customer_tab_tab_selected() {
        loadCustomerTable();
        tableDoubleClick(customer_customerTable_tv, "customer_edit_btn_clicked");
    }

    /**
     * @author Aysel Oeztuerk 0927011
     */
    @FXML
    private void customer_edit_btn_clicked() {
        CustomerDto selectedCustomer;
        if (doubleClickedCustomer != null) {
            selectedCustomer = doubleClickedCustomer;
        } else {
            selectedCustomer = (CustomerDto) customer_customerTable_tv.getSelectionModel().getSelectedItem();
        }
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
            alertCreator.warningAlert(BundleManager.getMessageBundle().getString("selectcustomer.warning"));
            LOGGER.warn("No customer selected!");
        }
    }

    /**
     * @author Aysel Oeztuerk 0927011
     */
    @FXML
    private void customer_createNew_btn_clicked() {

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

    /**
     * @author Aysel Oeztuerk 0927011
     */
    @FXML
    private void customer_search_btn_clicked() {
        try {
            if (customer_searchByCustomerName_tf.getText().isEmpty() || !customer_searchByCustomerName_tf.getText().matches("[a-zA-Z\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")) {
                this.alertCreator.warningAlert(BundleManager.getMessageBundle().getString("entersearchword.warning"));
            } else {
                List<CustomerDto> foundCustomersList =
                        this.customerService.getCustomerByName(customer_searchByCustomerName_tf.getText());
                customerList.clear();
                customerList.addAll(foundCustomersList);
            }
        } catch (ServiceException e) {
            this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("searchcustomer.error"));
        }
    }

    /**
     * @author Aysel Oeztuerk 0927011
     */
    @FXML
    private void customer_clear_btn_clicked() {
        loadCustomerTable();
    }

    /**
     * @author Aysel Oeztuerk 0927011
     */
    private void loadCustomerTable() {
        LOGGER.info("load CustomerTable()");
        customer_searchByCustomerName_tf.setText("");
        initCustomerTable();

        List<CustomerDto> customers;
        try {
            customers = customerService.getAllCustomers();
        } catch (ServiceException e) {
            LOGGER.error("Could not retrieve customers: {}", e.getMessage());
            alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("loadcustomertable.exception"));
            return;
        }
        customerList.clear();
        customerList.addAll(customers);
    }

    /**
     * @author Aysel Oeztuerk 0927011
     */
    private void initCustomerTable() {
        LOGGER.info("init CustomerTable()");

        customer_customerTable_id_tc.setCellValueFactory(new PropertyValueFactory<CustomerDto, Integer>("id"));
        customer_customerTable_firstname_tc.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("firstname"));
        customer_customerTable_lastname_tc.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("lastname"));
        customer_customerTable_dateOfBirth_tc.setCellValueFactory(new PropertyValueFactory<CustomerDto, Date>("dateOfBirth"));
        customer_customerTable_phoneNumber_tc.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("phoneNumber"));
        customer_customerTable_tv.setItems(customerList);

        formatDate(customer_customerTable_dateOfBirth_tc);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * ------ Tickets --------------------------------------------------------------------------------------------------
     * -----------------------------------------------------------------------------------------------------------------
     */
    @FXML
    private TextField tickets_reserveNr_tf;
    @FXML
    private TableView<TicketDto> tickets_tv;

    private final ObservableList<ViewModelDto> ticketTicketList = observableArrayList();
    private final ObservableList<ShowDto> ticketShowList = observableArrayList();

    private boolean ticketTicketTableInitialised = false;
    public boolean soldWithStripeSuccessfully = false;

    @FXML
    private Tab ticket_tab_tab;
    @FXML
    private TableView ticket_ticketTable_tv;

    @FXML
    private TableColumn ticket_ticketTable_ticketId_tc;
    @FXML
    private TableColumn ticket_ticketTable_seatNumber_tc;
    @FXML
    private TableColumn ticket_ticketTable_show_tc;
    @FXML
    private TableColumn ticket_ticketTable_price_tc;
    @FXML
    private TableColumn ticket_ticketTable_status_tc;

    @FXML
    private TextField ticket_customerNumber_tf;
    @FXML
    private TextField ticket_reservationNumber_tf;
    @FXML
    private TextField ticket_customerName_tf;
    @FXML
    private ComboBox ticket_selectCustomer_cb;
    @FXML
    private Label ticket_customernumber_lbl;
    @FXML
    private Label ticket_reservationnumber_lbl;
    @FXML
    private Label ticket_customername_lbl;
    @FXML
    private Label ticket_selectCustomer_lbl;
    @FXML
    private Label ticket_or1_lbl;
    @FXML
    private Label ticket_or2_lbl;

    @FXML
    private Button ticket_sellSelected_btn;
    @FXML
    private Button ticket_cancelTicket_btn;
    @FXML
    private Button ticket_findTicket_btn;
    @FXML
    private Button ticket_clear_btn;

    private List<CustomerDto> findCustomersList;
    private String selectedCustomerCb;
    private final ObservableList<String> foundCustomersList = observableArrayList();


    @FXML
    private void ticket_tab_tab_selected() {
        ticketTicketList.clear();
        //@author Aysel Oeztuerk 0927011
        if (!(ticket_customerName_tf.getText().equals(""))) {
            LOGGER.debug("Clear search.");
            ticket_selectCustomer_cb.getSelectionModel().clearSelection();
            //ticket_selectCustomer_cb.setValue(null);
            ticket_selectCustomer_cb.setItems(null);
            ticketTicketList.clear();

            /*if (!notificationPane.isShowing()) {
                notificationPane.show("Maybe the data of the selected customer changed meanwhile. \n" +
                        "Please find and select your customer again.", warningImage);
            }*/
        } else {
            ticket_selectCustomer_cb.setDisable(true);
        }
        if (!ticketTicketTableInitialised) {
            initTicketTicketTable();
            ticketTicketTableInitialised = true;
            LOGGER.debug("Initialization of ticket table");
        }
    }

    /**
     * @author Lina Wang 1326922
     */
    private void initTicketTicketTable() {
        ticket_ticketTable_ticketId_tc.setCellValueFactory(new PropertyValueFactory<ViewModelDto, Integer>("ticketId"));
        ticket_ticketTable_seatNumber_tc.setCellValueFactory(new PropertyValueFactory<ViewModelDto, Integer>
                ("seatNumber"));
        ticket_ticketTable_show_tc.setCellValueFactory(new PropertyValueFactory<ViewModelDto, String>("showName"));
        ticket_ticketTable_price_tc.setCellValueFactory(new PropertyValueFactory<ViewModelDto, Double>("price"));
        ticket_ticketTable_status_tc.setCellValueFactory(new PropertyValueFactory<ViewModelDto, String>("status"));
        ticket_ticketTable_tv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ticket_ticketTable_tv.setItems(ticketTicketList);
    }

    /**
     * @author Lina Wang 1326922 and Aysel Oeztuerk 0927011
     */
    @FXML
    private void ticket_customerNumber_tf_keyReleased() {
        if (!ticket_customerNumber_tf.getText().equals("")) {
            ticket_reservationNumber_tf.setEditable(false);
            ticket_customerName_tf.setEditable(false);
            ticket_selectCustomer_cb.setItems(null);
        } else if (ticket_reservationNumber_tf.getText().equals("") && ticket_customerName_tf.getText().equals("")) {
            ticket_reservationNumber_tf.setEditable(true);
            ticket_customerName_tf.setEditable(true);
        }
    }

    /**
     * @author Lina Wang 1326922 and Aysel Oeztuerk 0927011
     */
    @FXML
    private void ticket_reservationNumber_tf_keyReleased() {
        if (!ticket_reservationNumber_tf.getText().equals("")) {
            ticket_customerNumber_tf.setEditable(false);
            ticket_customerName_tf.setEditable(false);
            ticket_selectCustomer_cb.setItems(null);
        } else if (ticket_customerNumber_tf.getText().equals("") && ticket_customerName_tf.getText().equals("")) {
            ticket_customerNumber_tf.setEditable(true);
            ticket_customerName_tf.setEditable(true);
        }
    }

    /**
     * @author Aysel Oeztuerk 0927011
     */
    @FXML
    public void ticket_customerName_tf_keyReleased() {
        if (!ticket_customerName_tf.getText().equals("")) {
            ticket_reservationNumber_tf.setEditable(false);
            ticket_customerNumber_tf.setEditable(false);
        } else if (ticket_customerNumber_tf.getText().equals("") && ticket_reservationNumber_tf.getText().equals("")) {
            ticket_reservationNumber_tf.setEditable(true);
            ticket_customerNumber_tf.setEditable(true);
        }
    }

    /**
     * @author Lina Wang 1326922, Aysel Öztürk 0927011
     */
    @FXML
    public void ticket_findTicket_btn_clicked() {
        List<ShowDto> shows = new ArrayList<>();
        List<ViewModelDto> viewModel = new ArrayList<>();
        CustomerDto selectedCustomer = null;

        if (ticket_customerNumber_tf.getText().equals("") && ticket_reservationNumber_tf.getText().equals("")
                && ticket_customerName_tf.getText().equals("")) {
            this.alertCreator.inputErrorAlert(BundleManager.getMessageBundle().getString("entersearchword.warning"));
            return;
        }
        LOGGER.info("Start");
        if (!ticket_customerNumber_tf.getText().equals("")) {
            //ticket_selectCustomer_cb.setItems(null);
            if (ticket_customerNumber_tf.getText().matches("[0-9]+")) {
                Integer customerNumber = Integer.parseInt(ticket_customerNumber_tf.getText());
                try {
                    viewModel = this.ticketService.getAllOfCustomer(customerNumber);
                } catch (ServiceException e) {
                    LOGGER.error("Error while loading tickets of customer: {}", e.getMessage());
                    alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("loadticketstable.error"));
                    return;
                }
            } else {
                this.alertCreator.inputErrorAlert(BundleManager.getExceptionBundle().getString("numbersinput.error"));
                return;
            }

        } else if (!ticket_reservationNumber_tf.getText().equals("")) {
            if (ticket_reservationNumber_tf.getText().matches("[0-9]+")) {
                Integer reservationNumber = Integer.parseInt(ticket_reservationNumber_tf.getText());
                try {
                    viewModel = this.ticketService.getAllOfReservation(reservationNumber);
                } catch (ServiceException e) {
                    LOGGER.error("Error while loading tickets of reservation: {}", e.getMessage());
                    alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("loadticketstable.error"));
                    return;
                }
            } else {
                this.alertCreator.inputErrorAlert(BundleManager.getExceptionBundle().getString("numbersinput.error"));
                return;
            }
        } else if (!ticket_customerName_tf.getText().equals("")) {
            ticket_selectCustomer_cb.setDisable(false);
            LOGGER.debug("Search customer: " + ticket_customerName_tf.isEditable());
            try {
                if (ticket_customerName_tf.getText().matches("[a-zA-Z\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")) {
                    findCustomersList = this.customerService.getCustomerByName(ticket_customerName_tf.getText());
                    foundCustomersList.clear();
                } else {
                    this.alertCreator.inputErrorAlert(BundleManager.getExceptionBundle().getString("lettersinput.error"));
                    return;
                }

                for (CustomerDto c : findCustomersList) {
                    // add only customers, who has reservation or a ticket/s
                    if (!this.ticketService.getAllOfCustomer(c.getId()).isEmpty()) {
                        foundCustomersList.add(c.getFirstname() + " " + c.getLastname());
                        LOGGER.debug("Firstname: " + c.getFirstname());
                    }
                }

                if (!findCustomersList.isEmpty()) {
                    ticket_selectCustomer_cb.setItems(foundCustomersList);
                    //ticket_selectCustomer_cb.setPromptText(foundCustomersList.get(0));
                    ticket_selectCustomer_cb.getSelectionModel().select(0);
                    viewModel = tickets_customerCb_customerSelected();
                }

                ticket_selectCustomer_cb.valueProperty().addListener((observable, oldValue, newValue)
                        -> tickets_customerCb_customerSelected());

            } catch (ServiceException e) {
                LOGGER.error("Error while loading tickets of customer: {}", e.getMessage());
                this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("loadticketstable.error"));
                return;
            }
        }

        if (viewModel.isEmpty()) {
            this.alertCreator.infoAlert(BundleManager.getMessageBundle().getString("nomatchfound.warning"));
            ticketTicketList.clear();
            ticketTicketList.addAll(viewModel);
            return;
        }

        for (ViewModelDto v : viewModel) {
            LOGGER.debug(v.getShowName());
        }

        ticketTicketList.clear();
        ticketTicketList.addAll(viewModel);

    }

    /**
     * @author Aysel Oeztuerk 0927011
     */
    @FXML
    private void ticket_clear_btn_clicked() {
        ticket_customerName_tf.setText("");
        ticket_customerNumber_tf.setText("");
        ticket_reservationNumber_tf.setText("");
        ticket_selectCustomer_cb.setItems(null);
        ticket_reservationNumber_tf.setEditable(true);
        ticket_customerName_tf.setEditable(true);
        ticket_customerNumber_tf.setEditable(true);
        ticketTicketList.clear();
        ticket_selectCustomer_cb.setDisable(true);
    }

    /**
     * @author Aysel Oeztuerk 0927011
     */
    private List<ViewModelDto> tickets_customerCb_customerSelected() {

        if (!findCustomersList.isEmpty()) {
            List<ViewModelDto> viewModeltemp = new ArrayList<>();
            CustomerDto selectedCustomertemp = null;
            if (!(ticket_selectCustomer_cb.getSelectionModel().getSelectedItem() == null)) {
                selectedCustomerCb = (String) ticket_selectCustomer_cb.getSelectionModel().getSelectedItem();
                LOGGER.debug("selectedCustomerCb " + selectedCustomerCb);

                if (!selectedCustomerCb.isEmpty()) {
                    String selectedFirstname = selectedCustomerCb.split(" ", 2)[0];
                    String selectedLastname = selectedCustomerCb.split(" ", 2)[1];
                    for (CustomerDto c : findCustomersList) {
                        if (selectedFirstname.equals(c.getFirstname()) && selectedLastname.equals(c.getLastname())) {
                            selectedCustomertemp = c;
                            LOGGER.debug("Selected Customer: " + selectedCustomertemp.getFirstname());
                        }
                    }
                    try {
                        if (selectedCustomertemp != null) {
                            viewModeltemp = this.ticketService.getAllOfCustomer(selectedCustomertemp.getId());
                        }
                        ticketTicketList.clear();
                        ticketTicketList.addAll(viewModeltemp);
                    } catch (ServiceException e) {
                        LOGGER.error("Error while loading tickets of customer: {}", e.getMessage());
                        this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("loadticketstable.error"));
                    }
                } else {
                    LOGGER.warn("Please select a customer.");
                    this.alertCreator.warningAlert(BundleManager.getMessageBundle().getString("selectcustomer.warning"));
                }
            }
            return viewModeltemp;
        } else {
            ticket_selectCustomer_cb.setItems(null);
            return null;
        }

    }

    @FXML
    public void ticket_cancelTicket_btn_clicked() {
        LOGGER.debug("Cancel Ticket button clicked");
        List<ViewModelDto> selectedTicket = ticket_ticketTable_tv.getSelectionModel().getSelectedItems();

        if (selectedTicket.isEmpty()) {
            alertCreator.warningAlert(BundleManager.getMessageBundle().getString("selectticket.warning"));
            return;
        }

        List<TicketDto> ticketsToCancel = new ArrayList<>();

        //Convert viewmodelDTO to ticketDTO
        for (ViewModelDto v : selectedTicket) {
            TicketDto ticket = new TicketDto();
            ticket.setId(v.getTicketId());
            ticket.setSeatId(v.getSeatNumber());
            ticket.setPrice(v.getPrice());
            ticketsToCancel.add(ticket);
        }

        String cancellationReason = "";

        //Dialog for cancellation Reason
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(BundleManager.getBundle().getString("canceldialog.title"));
        dialog.setHeaderText(BundleManager.getBundle().getString("canceldialog.headertxt"));

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            cancellationReason = result.get();
            if (cancellationReason.equals("")) {
                cancellationReason = "No Reason";
            }
        } else {
            dialog.close();
            return;
        }

        try {
            this.ticketService.cancelTicket(ticketsToCancel, cancellationReason);
            this.alertCreator.infoAlert(BundleManager.getMessageBundle().getString("cancel.success"));
            ticketTicketList.removeAll(selectedTicket);

        } catch (ServiceException e) {
            LOGGER.error("Error while cancelling tickets");
            this.alertCreator.exceptionAlert(e.getMessage());
        }
    }

    public void tickets_sellSelected_btn_clicked(ActionEvent actionEvent) {
        LOGGER.debug("Sell Selected Seats Button clicked!");
        if (ticket_ticketTable_tv.getSelectionModel().getSelectedItems() != null &&
                !ticket_ticketTable_tv.getSelectionModel().getSelectedItems().isEmpty()) {
            List<ViewModelDto> items = ticket_ticketTable_tv.getSelectionModel().getSelectedItems();

            if (items.isEmpty()) {
                alertCreator.warningAlert(BundleManager.getMessageBundle().getString("selectticket.warning"));
                return;

            }

            List<TicketDto> tickets = new ArrayList<>();
            CustomerDto customer = new CustomerDto();
            for (ViewModelDto vm : items) {
                if (vm != null) {
                    if (!vm.getStatus().equals("sold")) {
                        TicketDto ticket = new TicketDto();
                        ticket.setId(vm.getTicketId());
                        ticket.setPrice(vm.getPrice());
                        ticket.setSeatId(vm.getSeatNumber());
                        tickets.add(ticket);
                        customer.setId(vm.getCustomerNumber());
                    } else {
                        alertCreator.warningAlert(BundleManager.getMessageBundle().getString("alreadysold.warning"));
                        return;
                    }
                }
            }

            Stage stripeStage = new Stage();
            stripeStage.initModality(Modality.APPLICATION_MODAL);
            SpringFxmlLoader.LoadWrapper sfLoader = springFxmlLoader.loadAndWrap("/gui/fxml/stripe.fxml");
            stripeStage.setScene(new Scene((Parent) sfLoader.getLoadedObject()));
            stripeStage.setTitle(BundleManager.getBundle().getString("inputpayment.info"));
            StripeController controller = (StripeController) sfLoader.getController();

            int priceInCent = 0;
            for (TicketDto t : tickets) {
                priceInCent += t.getPrice();
            }
            MainScreenController cont = this;
            controller.setData(priceInCent, tickets, customer/*, _employee*/, cont);

            stripeStage.showAndWait();
            /**
             LOGGER.debug("After stripe stage");
             LOGGER.debug("sold: " + this.soldWithStripeSuccessfully);
             if(soldWithStripeSuccessfully) {
             LOGGER.debug("Items removed");
             ticketTicketList.removeAll(items);
             }
             */

            //List<TicketIdentifierDto> dto = ticketService.sellTicket(tickets, customer);
            //this.alertCreator.infoAlert("Reserved tickets sold successfully");
            //ticketTicketList.removeAll(items);
            //ticket_findTicket_btn_clicked();
        }
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * ------ Events ---------------------------------------------------------------------------------------------------
     * -----------------------------------------------------------------------------------------------------------------
     */

    private final ObservableList<PerformanceDto> eventPerformanceList = observableArrayList();
    private final ObservableList<ShowDto> eventShowList = observableArrayList();
    private boolean eventPerformanceTableInitialised = false;
    private boolean eventShowTableInitialised = false;
    private boolean eventSearchFieldsInitialised = false;

    @FXML
    private Tab event_tab_tab;

    @FXML
    private TableView<PerformanceDto> event_performanceTable_tv;
    @FXML
    private TableColumn event_performanceTable_performanceName_tc;
    @FXML
    private TableColumn event_performanceTable_description_tc;
    @FXML
    private TableColumn event_performanceTable_duration_tc;

    @FXML
    private TableView<ShowDto> event_showTable_tv;
    @FXML
    private TableColumn event_showTable_showId_tc;
    @FXML
    private TableColumn event_showTable_showDate_tc;

    @FXML
    private TextField event_performanceName_tf;
    @FXML
    private DatePicker event_date_dp;
    @FXML
    private ChoiceBox event_town_cb;
    @FXML
    private ChoiceBox event_plz_cb;
    @FXML
    private ChoiceBox event_time_cb;
    @FXML
    private TextField event_artist_tf;
    @FXML
    private ChoiceBox event_performanceType_cb;
    @FXML
    private ChoiceBox event_price_cb;

    @FXML
    private Label event_performanceName_lbl;
    @FXML
    private Label event_date_lbl;
    @FXML
    private Label event_town_lbl;
    @FXML
    private Label event_plz_lbl;
    @FXML
    private Label event_time_lbl;
    @FXML
    private Label event_artist_lbl;
    @FXML
    private Label event_performanceType_lbl;
    @FXML
    private Label event_price_lbl;
    @FXML
    private Label event_upToPrice_lbl;

    @FXML
    private Button event_clear_btn;
    @FXML
    private Button event_search_btn;
    @FXML
    private Button availableTickets_btn;

    /**
     * @author Raphael Schotola 1225193, Sissi Zhan 1325880
     */
    @FXML
    private void event_tab_tab_selected() {

        loadEventPerformanceTable();
        if (!eventShowTableInitialised) {
            initEventSearchFields();
            eventSearchFieldsInitialised = true;
            initEventShowTable();
            eventShowTableInitialised = true;
            LOGGER.info("Done with initialization");
        }

        resetEventSearchFields();

        //Listens to selection changes
        event_performanceTable_tv.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue,
                                                                                          newValue) -> {
            try {
                if (event_performanceTable_tv.getSelectionModel().getSelectedItem() != null) {
                    PerformanceDto selectedPerformance = event_performanceTable_tv.getSelectionModel()
                            .getSelectedItem();
                    //Show all shows
                    this.loadEventShowTable(selectedPerformance.getId());

                }
            } catch (Exception e) {
                LOGGER.error("Error while loading shows: {}", e.getMessage());
                this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("loadshowtable.exception"));
            }
        });
        // (Aysel)
        tableDoubleClick(event_showTable_tv, "event_availableTickets_btn_clicked");
    }

    private void resetEventSearchFields() {
        event_performanceName_tf.clear();
        event_date_dp.getEditor().clear();
        event_date_dp.setValue(null);
        event_town_cb.setValue(null);
        event_plz_cb.setValue(null);
        event_time_cb.setValue(null);
        event_artist_tf.clear();
        event_performanceType_cb.setValue(null);
        event_price_cb.setValue(null);
    }

    /**
     * @author Sissi Zhan 1325880
     * <p>
     * initiates the event search fields in GUI
     */
    private void initEventSearchFields() {

        //TODO: Get real data from database for town, plz
        try {

            List<String> resultSet = this.locationService.getCities();
            event_town_cb.getItems().setAll(resultSet);

            resultSet = this.locationService.getPostalCodes();
            event_plz_cb.getItems().setAll(resultSet);

            event_time_cb.getItems().setAll("00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
                    "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00");
            event_performanceType_cb.getItems().setAll(BundleManager.getBundle().getString("movie"),
                    BundleManager.getBundle().getString("theater"),
                    BundleManager.getBundle().getString("musical"),
                    BundleManager.getBundle().getString("opera"),
                    BundleManager.getBundle().getString("concert"));
            event_price_cb.getItems().setAll("5", "10", "20", "30", "40", "50", "60", "70", "80", "90");

        } catch (ServiceException e) {
            this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("initsearchfields.exception"));
        }
    }

    /**
     * @author Sissi Zhan 1325880
     */
    @FXML
    private void event_search_btn_clicked() {

        try {

            String performanceName = null;
            String date = null;
            String town = null;
            String plz = null;
            String time = null;
            String performanceType = null;
            String price = null;
            String artist = null;

            if (!(event_performanceName_tf.getText().equals(""))) {
                performanceName = event_performanceName_tf.getText();
                if (performanceName.matches("[a-zA-Z0-9\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")) {
                    performanceName = replaceSpaces(performanceName);
                } else {
                    this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("lettersnumbersinput.error"));
                    return;
                }

            }
            if (event_date_dp.getValue() != null) {
                date = event_date_dp.getValue().toString();
            }

            if (event_town_cb.getSelectionModel().getSelectedItem() != null) {
                town = event_town_cb.getSelectionModel().getSelectedItem().toString();
                town = replaceSpaces(town);
            }
            if (event_plz_cb.getSelectionModel().getSelectedItem() != null) {
                plz = event_plz_cb.getSelectionModel().getSelectedItem().toString();
                plz = replaceSpaces(plz);
            }
            if (event_time_cb.getSelectionModel().getSelectedItem() != null) {
                time = event_time_cb.getSelectionModel().getSelectedItem().toString();
                time = replaceSpaces(time);
            }
            if (event_performanceType_cb.getSelectionModel().getSelectedItem() != null) {
                performanceType = event_performanceType_cb.getSelectionModel().getSelectedItem().toString();
                performanceType = replaceSpaces(performanceType);
            }
            if (event_price_cb.getSelectionModel().getSelectedItem() != null) {
                price = event_price_cb.getSelectionModel().getSelectedItem().toString();
                price = replaceSpaces(price);
            }
            if (!(event_artist_tf.getText().equals(""))) {
                artist = event_artist_tf.getText();
                if (artist.matches("[a-zA-Z0-9\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")) {
                    artist = replaceSpaces(artist);
                } else {
                    this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("lettersnumbersinput.error"));
                    return;
                }
            }

            List<PerformanceDto> filteredPerformances = this.performanceService.filterPerformances(
                    performanceName, date, town, plz, time, performanceType, price, artist
            );
            LOGGER.info("filtered performances");

            if (!eventPerformanceList.isEmpty()) {
                eventPerformanceList.clear();
            }
            if (!filteredPerformances.isEmpty()) {
                eventPerformanceList.addAll(filteredPerformances);
            }

        } catch (ServiceException e) {
            LOGGER.error("ServiceException thrown: " + e.getMessage());
            this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("searchperformance.error"));
        }
    }

    /**
     * @author Sissi Zhan 1325880
     */
    @FXML
    private void event_clear_btn_clicked() {
        resetEventSearchFields();

        try {
            List<PerformanceDto> eventPerformances = this.performanceService.getAll();
            eventPerformanceList.clear();
            eventPerformanceList.addAll(eventPerformances);
            eventShowList.clear();

        } catch (ServiceException e) {
            LOGGER.error("ServiceException thrown: " + e.getMessage());
            this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("loadperformancetable.exception"));
        }

    }


    private String replaceSpaces(String word) {
        return word.replaceAll("\\s", "_");
    }

    /**
     * @author Aysel Oeztuerk 0927011
     */
    @FXML
    private void event_availableTickets_btn_clicked() {
        event_availableTickets_btn_clicked(event_showTable_tv, false);
    }

    /**
     * @author Sissi Zhan 1325880, Aysel Oeztuerk 0927011
     * <p>
     * This method opens a new stage with available Tickets from the selected show
     */
    @FXML
    private void event_availableTickets_btn_clicked(TableView tv, Boolean topten) {
        try {
            // (Aysel)
            Integer showId = 0;
            if (tv.getSelectionModel().getSelectedItem() instanceof ShowDto) {
                ShowDto selectedItem = (ShowDto) tv.getSelectionModel().getSelectedItem();
                showId = selectedItem.getId();
                tv.getSelectionModel().clearSelection();
            } else if (tv.getSelectionModel().getSelectedItem() instanceof ToptenDto) {
                ToptenDto selectedItem = (ToptenDto) tv.getSelectionModel().getSelectedItem();
                showId = selectedItem.getEventID();
                tv.getSelectionModel().clearSelection();
            } else if (tv.getSelectionModel().getSelectedItem() == null) {
                LOGGER.error("No show selected");
                this.alertCreator.inputErrorAlert(BundleManager.getMessageBundle().getString("selectshow.warning"));
                return;
            }

            // (Sissi)
            if (!(showId == 0)) {
                PerformanceDto performance = this.performanceService.getOfShow(showId);
                Stage selectTicketsStage = new Stage();
                selectTicketsStage.initModality(Modality.APPLICATION_MODAL);

                if (performance.getType().equals("CONCERT")) {
                    LOGGER.debug("A show of concert was selected");

                    ConcertPlanController.setShowId(showId);
                    selectTicketsStage.setScene(new Scene((Parent) springFxmlLoader.load("/gui/fxml/concertPlan.fxml")));

                } else {
                    RoomPlanController.setShowId(showId);

                    //choose fxml file based on maximum seats per row
                    Integer[] result = calculateRoomSize(showId);
                    int roomSize = result[0];
                    RoomPlanController.setMaxWidth(result[1]);
                    RoomPlanController.setMaxHeight(result[2]);
                    String fxmlPath;
                    if (roomSize == 1) fxmlPath = "/gui/fxml/roomPlanSmall.fxml";
                    else if (roomSize == 2) fxmlPath = "/gui/fxml/roomPlanMedium.fxml";
                    else fxmlPath = "/gui/fxml/roomPlanBig.fxml";

                    selectTicketsStage.setScene(new Scene((Parent) springFxmlLoader.load(fxmlPath)));

                }

                selectTicketsStage.setTitle(BundleManager.getBundle().getString("showforperformance.title") + " " + performance.getName());
                selectTicketsStage.setResizable(false);
                selectTicketsStage.show();

                //(Aysel)
                /*if (topten && !top10_notificationPane.isShowing()) {
                    top10_notificationPane.show("Maybe the data changed meanwhile. Please click refresh - button.", warningImage);
                }*/
            } else {
                this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("showforperformance.exception"));
            }
        } catch (NullPointerException e) {
            LOGGER.error("No show selected");
            this.alertCreator.inputErrorAlert(BundleManager.getMessageBundle().getString("selectshow.warning"));
        } catch (ServiceException e) {
            this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("showforperformance.exception"));
        }

    }

    /**
     * @param showId ID of the show
     * @return Room Size as int (see above)
     * @author Raphael Schotola 1225193
     * Calculates Room size based on amount of seats in rows
     * 0 ... small, 1 ... medium, 2 ... big
     */
    private Integer[] calculateRoomSize(int showId) throws ServiceException {
        List<SeatDto> seats = this.seatService.getAllOfShow(showId);
        int currentMaxWidth = 0;
        int currentMaxHeight = 0;
        int roomSize;

        //go through all seats and remember the biggest seatID
        for (SeatDto seat : seats) {
            //check if the current maximum must be increased
            if (seat.getSequence() > currentMaxWidth) {
                currentMaxWidth = seat.getSequence();
            }
            if (seat.getRow().getSequence() > currentMaxHeight) {
                currentMaxHeight = seat.getRow().getSequence();
            }
        }

        if (currentMaxWidth <= 7 && currentMaxHeight <= 3) roomSize = 1;
        else if (currentMaxWidth <= 9 && currentMaxHeight <= 4) roomSize = 2;
        else roomSize = 3;
        Integer[] ret = new Integer[3];
        ret[0] = roomSize;
        ret[1] = currentMaxWidth;
        ret[2] = currentMaxHeight;
        return ret;
    }

    /**
     * @param performanceId the ID of the performance for which all shows should be loaded
     * @author Raphael Schotola 1225193
     */
    private void loadEventShowTable(int performanceId) {
        LOGGER.info("load EventShowTable()");
        List<ShowDto> eventShows;
        try {
            eventShows = showService.getAllOfPerformance(performanceId);
        } catch (ServiceException e) {
            LOGGER.error("Could not retrieve shows: {}", e.getMessage());
            this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("loadshowtable.exception"));
            return;
        }
        eventShowList.clear();
        eventShowList.addAll(eventShows);
    }

    /**
     * @author Raphael Schotola 1225193
     */
    private void initEventShowTable() {
        LOGGER.info("init EventShowTable()");

        event_showTable_showId_tc.setCellValueFactory(new PropertyValueFactory<ShowDto, Integer>("id"));
        event_showTable_showDate_tc.setCellValueFactory(new PropertyValueFactory<ShowDto, Date>("dateOfPerformance"));
        event_showTable_tv.setItems(eventShowList);

        //(Aysel)
        formatDate(event_showTable_showDate_tc);
    }

    /**
     * @author Raphael Schotola 1225193
     */
    private void loadEventPerformanceTable() {
        LOGGER.info("loadEventPerformanceTable()");
        if (!eventPerformanceTableInitialised) {
            eventPerformanceTableInitialised = true;
            initEventPerformanceTable();
        }

        List<PerformanceDto> eventPerformances;
        try {

            eventPerformances = performanceService.getAll();

        } catch (ServiceException e) {
            LOGGER.error("Could not retrieve performances: {}", e.getMessage());
            this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("loadperformancetable.exception"));
            return;
        }
        eventPerformanceList.clear();
        eventPerformanceList.addAll(eventPerformances);
    }

    /**
     * @author Raphael Schotola 1225193
     */
    private void initEventPerformanceTable() {
        LOGGER.info("initEventPerformanceTable()");
        event_performanceTable_performanceName_tc.setCellValueFactory(new PropertyValueFactory<PerformanceDto,
                String>("name"));
        event_performanceTable_description_tc.setCellValueFactory(new PropertyValueFactory<PerformanceDto, String>
                ("description"));
        event_performanceTable_duration_tc.setCellValueFactory(new PropertyValueFactory<PerformanceDto, String>
                ("duration"));
        event_performanceTable_tv.setItems(eventPerformanceList);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * ------ Top 10 -------------------------------------------------------------------------------------------------
     * -----------------------------------------------------------------------------------------------------------------
     */
    @FXML
    private BarChart<Long, String> top10_barChart_bc;
    @FXML
    private TableView top10_topTenShowTable_tv;
    @FXML
    private TableColumn top10_topTenShowTable_showID_tc;
    @FXML
    private TableColumn top10_topTenShowTable_performance_tc;
    @FXML
    private TableColumn top10_topTenShowTable_description_tc;
    @FXML
    private TableColumn top10_topTenShowTable_date_tc;
    @FXML
    private ComboBox top10_selectCategory_cb;
    @FXML
    private NumberAxis top10_barChart_xAxis;
    @FXML
    private CategoryAxis top10_barChart_yAxis;
    @FXML
    private Pane top10_pane;
    @FXML
    private Label top10_notify_l;
    @FXML
    private ComboBox top10_selectMonth_cb;
    @FXML
    private ComboBox top10_selectYear_cb;
    @FXML
    private Label top10_top10_lbl;
    @FXML
    private Button top10_refresh_btn;
    @FXML
    private Button top10_Performances_availableTickets_btn;
    @FXML
    private Button top10_Shows_availableTickets_btn;
    @FXML
    private Tab top10_performances_tab;
    @FXML
    private Tab top10_shows_tab;

    private ToptenDto clickedRow = null;
    private ToptenPerformancesDto clickedRowPerformance = null;
    //private NotificationPane top10_notificationPane;

    private final ObservableList<ToptenDto> topTenShowsList = observableArrayList();
    private List<ToptenDto> foundTopTenShowsList = new ArrayList<>();
    private ObservableList<String> showNames = FXCollections.observableArrayList();
    private ObservableList<Long> soldTicketsOfShows = FXCollections.observableArrayList();
    XYChart.Series<Long, String> seriesOfShows = null;

    /**
     * ------ Top 10 performances-----------------------------------------------------------------------------------------------------
     */
    @FXML
    private Tab top10_tab_tab;
    @FXML
    private BarChart top10_Performances_barChart_bc;
    @FXML
    private NumberAxis top10_Performances_barChart_xAxis;
    @FXML
    private CategoryAxis top10_Performances_barChart_yAxis;
    @FXML
    private TableView<ToptenPerformancesDto> top10_Performances_performanceTable_tv;
    @FXML
    private TableColumn top10_Performances_performanceTable_performanceName_tc;
    @FXML
    private TableColumn top10_Performances_performanceTable_performanceID_tc;
    @FXML
    private TableColumn top10_Performances_performanceTable_description_tc;
    @FXML
    private TableView top10_Performances_showTable_tv;
    @FXML
    private TableColumn top10_Performances_showTable_showId_tc;
    @FXML
    private TableColumn top10_Performances_showTable_showDate_tc;

    //Performance table and chart
    private final ObservableList<ToptenPerformancesDto> topTenPerformancesList = observableArrayList();
    private List<ToptenPerformancesDto> foundTopTenPerformancesList = new ArrayList<>();
    private ObservableList<String> performanceNames = FXCollections.observableArrayList();
    private ObservableList<Long> soldTicketsOfPerformances = FXCollections.observableArrayList();
    XYChart.Series<Long, String> seriesOfPerformances = null;

    String errorMsg = "";

    //Shows of performance table
    private final ObservableList<ShowDto> topTenShowsOfPerformancesList = observableArrayList();

    /**
     * @author Aysel Oeztuerk 0927011
     */
    @FXML
    private void top10_tab_tab_selected() throws InterruptedException, ServiceException {
        top10_selectCategory_cb.setPromptText(BundleManager.getBundle().getString("category.cb"));
        top10_selectYear_cb.setPromptText(BundleManager.getBundle().getString("year.cb"));
        top10_selectMonth_cb.setPromptText(BundleManager.getBundle().getString("month.cb"));
        top10_barChart_bc.setLegendVisible(false);
        top10_notify_l.setVisible(false);
        if (isTop10InputValid(1)) {
            loadTop10((String) top10_selectCategory_cb.getSelectionModel().getSelectedItem());
            top10_notify_l.setVisible(true);
            top10_Performances_showTable_tv.getItems().clear();
        }

        /*top10_selectCategory_cb.valueProperty().addListener((observable, oldValue, newValue) -> {
            //initBarChart();
            top10_notify_l.setVisible(false);
            if (isTop10InputValid(1)) {
                try {
                    loadTop10((String) top10_selectCategory_cb.getSelectionModel().getSelectedItem());
                } catch (ServiceException e) {
                    this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("loadtop10.exception"));
                }
            }
        });*/
        top10_selectYear_cb.valueProperty().addListener((observable, oldValue, newValue) -> {
            int selectedMonth = top10_selectMonth_cb.getSelectionModel().getSelectedIndex();
            top10_selectMonth_cb.getItems().clear();
            //initBarChart();
            top10_notify_l.setVisible(false);
            if ((int) newValue == LocalDate.now().getYear()) {
                LOGGER.debug("Newvalue 2016");
                for (int i = 0; i < LocalDate.now().getMonthValue(); i++) {
                    top10_selectMonth_cb.getItems().add(Month.values()[i].toString());
                    LOGGER.debug("Month: " + LocalDate.now().getMonthValue() + Month.values()[i].toString());
                }
            } else {
                if ((int) newValue == LocalDate.now().getYear() - 1) {
                    top10_selectMonth_cb.getItems().addAll(EnumSet.allOf(Month.class));
                }
            }

            if (selectedMonth <= top10_selectMonth_cb.getVisibleRowCount()) {
                top10_selectMonth_cb.getSelectionModel().select(selectedMonth);
            }
            LOGGER.debug("rows: " + top10_selectMonth_cb.getVisibleRowCount() + "selectedMonth: " + selectedMonth + " selectedItem: " + top10_selectMonth_cb.getSelectionModel().getSelectedIndex());

            //initBarChart();
           /* if (isTop10InputValid(1)) {
                try {
                    loadTop10((String) top10_selectCategory_cb.getSelectionModel().getSelectedItem());
                } catch (ServiceException e) {
                    this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("loadtop10.exception"));
                }
            }*/

        });

        /*top10_selectMonth_cb.valueProperty().addListener((observable, oldValue, newValue) -> {
            // initBarChart();
            top10_notify_l.setVisible(false);
            if (isTop10InputValid(1)) {
                try {
                    loadTop10((String) top10_selectCategory_cb.getSelectionModel().getSelectedItem());
                } catch (ServiceException e) {
                    this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("loadtop10.exception"));
                }
            }
        });*/


        top10_topTenShowTable_tv.setRowFactory(tv -> {
            TableRow<ToptenDto> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    clickedRow = row.getItem();
                    top10_notify_l.setVisible(false);
                    clickedRow = null;
                }
            });
            return row;
        });

        top10_Performances_performanceTable_tv.setRowFactory(tv -> {
            TableRow<ToptenPerformancesDto> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    clickedRowPerformance = row.getItem();
                    top10_notify_l.setVisible(false);
                    clickedRow = null;
                }
            });
            return row;
        });

        initTopTenShowTable();
        //Listens to selection changes
        top10_Performances_performanceTable_tv.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                LOGGER.debug("selecteditem: " + top10_Performances_performanceTable_tv.getSelectionModel().getSelectedItem() + " " + newValue);
                if (top10_Performances_performanceTable_tv.getSelectionModel().getSelectedItem() != null) {
                    ToptenPerformancesDto selectedPerformance = top10_Performances_performanceTable_tv.getSelectionModel()
                            .getSelectedItem();
                    LOGGER.debug("selected performance: " + selectedPerformance.getEventID() + " " + selectedPerformance.getName());
                    //Show all shows
                    this.loadToptenShowsOfPerformanceTable(selectedPerformance.getEventID());

                }
            } catch (Exception e) {
                LOGGER.error("Error while loading shows: {}", e.getMessage());
                this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("loadshowtable.exception"));
            }
        });

        tableDoubleClick(event_showTable_tv, "event_availableTickets_btn_clicked");

        if (performanceNames.isEmpty()) {

        }
    }

    private void loadToptenShowsOfPerformanceTable(int performanceId) {
        LOGGER.info("load ToptenShowTable()");
        List<ShowDto> eventShows;
        try {
            eventShows = showService.getAllOfPerformance(performanceId);
        } catch (ServiceException e) {
            LOGGER.error("Could not retrieve shows: {}", e.getMessage());
            this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("loadshowtable.exception"));
            return;
        }
        for (ShowDto sd : eventShows) {
            LOGGER.debug("load show: " + sd.getId());
        }
        topTenShowsOfPerformancesList.clear();
        topTenShowsOfPerformancesList.addAll(eventShows);
    }

    @FXML
    private void top10_refresh_btn_clicked() throws ServiceException {
        errorMsg = "";
        if (isTop10InputValid(null)) {
            LOGGER.debug("Refresh clicked..");
            loadTop10((String) top10_selectCategory_cb.getSelectionModel().getSelectedItem());
            top10_notify_l.setVisible(true);
            top10_Performances_showTable_tv.getItems().clear();
            if (!errorMsg.equals("")) {
                alertCreator.warningAlert(errorMsg);
            }
        }
    }

    /**
     * @author Aysel Oeztuerk 0927011
     */
    @FXML
    private void top10_Performances_availableTickets_btn_clicked() throws InterruptedException, ServiceException {
        LOGGER.debug("Available tickets called..");
        event_availableTickets_btn_clicked(top10_Performances_showTable_tv, true);
    }

    /**
     * @author Aysel Oeztuerk 0927011
     */
    @FXML
    private void top10_Shows_availableTickets_btn_clicked() throws InterruptedException, ServiceException {
        LOGGER.debug("Available tickets called..");
        event_availableTickets_btn_clicked(top10_topTenShowTable_tv, true);
    }

    /**
     * @author Aysel Oeztuerk 0927011
     */
    private void loadTop10(String selectedItem) throws ServiceException {
        LOGGER.debug("LoadTop10 called..");
        String category = (String) top10_selectCategory_cb.getSelectionModel().getSelectedItem();
        try {
            initTopTenTable();
            errorMsg = "";
            loadTopTenTable(category, "foundTopTenShowsList");
            loadTopTenTable(category, "foundTopTenPerformancesList");
        } catch (ServiceException e) {
            this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("loadtop10.exception"));
        }
        initBarChart();
        loadtopTenBarChart("foundTopTenShowsList");
        loadtopTenBarChart("foundTopTenPerformancesList");

      /*  if (!errorMsg.equals("")) {
            alertCreator.warningAlert(errorMsg);
            errorShown= true;
        }*/

        LOGGER.warn(errorMsg);

    }

    /**
     * @author Aysel Oeztuerk 0927011
     */
    private void loadTopTenTable(String category, String foundTopTenList) throws ServiceException {
        LOGGER.debug("loadtopTenTable call.." + category);

        if (foundTopTenList.equals("foundTopTenShowsList")) {

            LOGGER.debug("foundTopTenShowsList");
            foundTopTenShowsList =
                    this.top10Service.getTop10Shows(
                            (int) top10_selectYear_cb.getSelectionModel().getSelectedItem(),
                            top10_selectMonth_cb.getSelectionModel().getSelectedIndex() + 1,
                            category);
            topTenShowsList.clear();
            if (!foundTopTenShowsList.isEmpty()) {
                topTenShowsList.addAll(foundTopTenShowsList);
            } else {
                errorMsg += BundleManager.getMessageBundle().getString("nosoldperformancesfound.warning") + "\n";
            }

        } else {
            LOGGER.debug("foundTopTenPerformancesList");
            foundTopTenPerformancesList =
                    this.top10Service.getTop10Performances(
                            (int) top10_selectYear_cb.getSelectionModel().getSelectedItem(),
                            top10_selectMonth_cb.getSelectionModel().getSelectedIndex() + 1,
                            category);

            topTenPerformancesList.clear();
            if (!foundTopTenPerformancesList.isEmpty()) {
                topTenPerformancesList.addAll(foundTopTenPerformancesList);
            } else {
                errorMsg += BundleManager.getMessageBundle().getString("nosoldshowsfound.warning") + "\n";
            }
        }
    }

    /**
     * @author Aysel Oeztuerk 0927011
     */
    private void loadtopTenBarChart(String foundTopTenList) throws ServiceException {
        top10_barChart_bc.setLegendVisible(false);
        if (foundTopTenList.equals("foundTopTenShowsList")) {

            LOGGER.debug("loadtopTenBarChart Dto: ToptenDto ");
            String showName;
            for (ToptenDto toptenShow : foundTopTenShowsList) {
                showName = toptenShow.getName();

                if (showName.length() > 15) {
                    showName = showName.substring(0, 12) + "...";
                }
                showNames.add(showName + " " + toptenShow.getEventID());
                soldTicketsOfShows.add(toptenShow.getSoldTickets());
                LOGGER.debug(toptenShow.getEventID() + " " + toptenShow.getName() + " " + toptenShow.getSoldTickets());
            }

            top10_barChart_yAxis.setCategories(showNames);
            seriesOfShows = new XYChart.Series<>();

            // Create a XYChart.Data object for each month. Add it to the series.
            for (int i = 0; i < showNames.size(); i++) {
                seriesOfShows.getData().add(new XYChart.Data<>(soldTicketsOfShows.get(i), showNames.get(i)));
            }

            top10_barChart_bc.getData().add(seriesOfShows);
            top10_barChart_bc.setLegendVisible(false);
        } else {
            String getName;
            // ToptenPerformancesDto topten = new ToptenPerformancesDto();
            List<ToptenPerformancesDto> variable = foundTopTenPerformancesList;
            LOGGER.debug("loadtopTenBarChart Dto: ToptenPerformancesDto ");
            for (ToptenPerformancesDto topten : variable) {
                getName = topten.getName();

                if (getName.length() > 15) {
                    getName = getName.substring(0, 12) + "...";
                }

                performanceNames.add(getName + " " + topten.getEventID());
                soldTicketsOfPerformances.add(topten.getSoldTickets());
                LOGGER.debug("Performances: " + topten.getEventID() + " " + topten.getName() + " " + topten.getSoldTickets());
            }

            top10_Performances_barChart_yAxis.setCategories(performanceNames);
            seriesOfPerformances = new XYChart.Series<>();
            // Create a XYChart.Data object for each month. Add it to the series.
            for (int i = 0; i < performanceNames.size(); i++) {
                seriesOfPerformances.getData().add(new XYChart.Data<>(soldTicketsOfPerformances.get(i), performanceNames.get(i)));
            }

            top10_Performances_barChart_bc.getData().add(seriesOfPerformances);
            top10_Performances_barChart_bc.setLegendVisible(false);
        }
    }

    /**
     * @author Aysel Oeztuerk 0927011
     */
    private void initTopTenTable() {
        LOGGER.info("init TopTenTable()");

        //shows
        LOGGER.info("init TopTenShowsTable()");

        top10_topTenShowTable_showID_tc.setCellValueFactory(new PropertyValueFactory<ToptenDto, Integer>("eventID"));
        top10_topTenShowTable_performance_tc.setCellValueFactory(new PropertyValueFactory<ToptenDto, String>("name"));
        top10_topTenShowTable_description_tc.setCellValueFactory(new PropertyValueFactory<ToptenDto, String>("description"));
        top10_topTenShowTable_date_tc.setCellValueFactory(new PropertyValueFactory<ToptenDto, String>("date"));
        top10_topTenShowTable_tv.setItems(topTenShowsList);

        formatDate(top10_topTenShowTable_date_tc);

        //performances
        top10_Performances_performanceTable_performanceID_tc.setCellValueFactory(new PropertyValueFactory<ToptenPerformancesDto, Integer>("eventID"));
        top10_Performances_performanceTable_performanceName_tc.setCellValueFactory(new PropertyValueFactory<ToptenPerformancesDto, String>("name"));
        top10_Performances_performanceTable_description_tc.setCellValueFactory(new PropertyValueFactory<ToptenPerformancesDto, String>("description"));
        top10_Performances_performanceTable_tv.setItems(topTenPerformancesList);
    }

    /**
     * @author Aysel Oeztuerk 0927011
     */
    private void initTopTenShowTable() {
        //shows of performances
        top10_Performances_showTable_showId_tc.setCellValueFactory(new PropertyValueFactory<ShowDto, Integer>("id"));
        top10_Performances_showTable_showDate_tc.setCellValueFactory(new PropertyValueFactory<ShowDto, Date>("dateOfPerformance"));
        top10_Performances_showTable_tv.setItems(topTenShowsOfPerformancesList);

        formatDate(top10_Performances_showTable_showDate_tc);
    }

    /**
     * @author Aysel Oeztuerk 0927011
     */
    private void initBarChart() {
        //shows
        top10_barChart_bc.setLegendVisible(false);
        top10_barChart_xAxis.setLabel(BundleManager.getBundle().getString("soldtickets.lbl"));
        top10_barChart_bc.getData().clear();
        showNames.clear();
        soldTicketsOfShows.clear();
        seriesOfShows = new XYChart.Series<>();

        //performances
        top10_Performances_barChart_bc.setLegendVisible(false);
        top10_Performances_barChart_xAxis.setLabel(BundleManager.getBundle().getString("soldticketsperperformance"));
        top10_Performances_barChart_bc.getData().clear();
        performanceNames.clear();
        soldTicketsOfPerformances.clear();
        seriesOfPerformances = new XYChart.Series<>();
    }

    /**
     * @author Aysel Oeztuerk 0927011
     */
    private boolean isTop10InputValid(Object obj) {
        String errorMessage = "";
        Boolean dateError = false;


        if (top10_selectCategory_cb.getSelectionModel().isEmpty()) {
            errorMessage += BundleManager.getMessageBundle().getString("selectcategory.warning") + "\n";
        }
        if (top10_selectYear_cb.getSelectionModel().isEmpty()) {
            errorMessage += BundleManager.getMessageBundle().getString("selectyear.warning") + "\n";
        }
        if (top10_selectMonth_cb.getSelectionModel().isEmpty()) {
            errorMessage += BundleManager.getMessageBundle().getString("selectmonth.warning") + "\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            if (obj == null) {
                alertCreator.inputErrorAlert(errorMessage);
            }
            return false;
        }
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * ------ Misc -----------------------------------------------------------------------------------------------------
     * -----------------------------------------------------------------------------------------------------------------
     */
    private CustomerDto doubleClickedCustomer = null;
    private ShowDto doubleClickedShow = null;

    /**
     * @param table  the table.
     * @param action the name of the method to be called after a doubleclick is detected.
     * @author Aysel Oeztuerk 0927011
     * <p>
     * Detects doubleclick on row of a Tableview.
     */
    private void tableDoubleClick(TableView table, String action) {
        table.setRowFactory(tv -> {
            TableRow<CustomerDto> row = new TableRow<>();
            TableRow<ShowDto> show_row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    if (action.equals("customer_edit_btn_clicked")) {
                        doubleClickedCustomer = row.getItem();
                        customer_edit_btn_clicked();
                        doubleClickedCustomer = null;
                    } else if (action.equals("event_availableTickets_btn_clicked")) {
                        doubleClickedShow = show_row.getItem();
                        event_availableTickets_btn_clicked();
                        doubleClickedShow = null;
                    } // for other buttons, you have to add here an else if clause
                }
            });
            return row;
        });
    }

    /**
     * @param tc table column.
     * @author Aysel Oeztuerk 0927011
     * <p>
     * Formats the date column in tableview to "dd.MM.yyyy" format.
     */
    private void formatDate(TableColumn tc) {
        DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");

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
                        LocalTime lTime = item.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
                        setText(myDateFormatter.format(date)+" - "+time.format(lTime));
                    }
                }
            };
        });
    }

    /**
     * Exits the application
     *
     * @param event the event
     */
    @FXML
    private void handleExit(ActionEvent event) {
        try {
            LOGGER.debug("Close windows");
            addReadNews();
            this.authService.logout();

        } catch (ServiceException e) {
            LOGGER.error("Logout failed: " + e.getMessage(), e);
            this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("error.logout"));
        }
        //Back to Login
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public void setSoldWithStripeSuccessfully(boolean sold) {
        this.soldWithStripeSuccessfully = sold;
    }

}