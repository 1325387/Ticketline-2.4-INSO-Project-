package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.extraObjects.ConcertPlanReferencer;
import at.ac.tuwien.inso.ticketline.client.extraObjects.RoomPlanReferencer;
import at.ac.tuwien.inso.ticketline.client.extraObjects.TicketStatus;
import at.ac.tuwien.inso.ticketline.client.service.SeatService;
import at.ac.tuwien.inso.ticketline.client.service.TicketService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.ticketline.dto.GalleryDto;
import at.ac.tuwien.inso.ticketline.dto.SeatDto;
import at.ac.tuwien.inso.ticketline.dto.TicketDto;
import at.ac.tuwien.inso.ticketline.dto.TicketIdentifierDto;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.security.krb5.internal.Ticket;

import java.net.URL;
import java.util.*;

/**
 * @author Lina Wang 1326922
 */
@Component
public class ConcertPlanController implements Initializable {

    @Autowired
    private SpringFxmlLoader springFxmlLoader;
    @Autowired
    private SeatService seatService;
    @Autowired
    private TicketService ticketService;

    @FXML
    private BorderPane concertPlan_galleryPane_bp;
    @FXML
    private GridPane concertPlan_sideLeftGrid_gp;
    @FXML
    private GridPane concertPlan_sideRightGrid_gp;

    @FXML
    private Label concertPlan_performanceName_tl;

    @FXML
    private Button concertPlan_cancel_btn;
    @FXML
    private Button concertPlan_sell_btn;
    @FXML
    private Button concertPlan_reserve_btn;

    @FXML
    private ChoiceBox<Integer> concertPlan_amount_cb;
    @FXML
    private TextField concertPlan_gallery_tf;
    @FXML
    private TextField concertPlan_totalSum_tf;
    @FXML
    private Text concertPlan_availability_txt;
    @FXML
    private Label availability_lbl;
    @FXML
    private Label gallery_lbl;
    @FXML
    private Label amount_lbl;
    @FXML
    private Label total_lbl;
    @FXML
    private Label stage_lbl;






    private static final Logger LOGGER = LoggerFactory.getLogger(ConcertPlanController.class);

    private static int showId;

    private boolean sideGalleryInitialized = false;

    private boolean middleGalleryInitialized = false;

    private boolean backGalleryInitialized = false;

    private EventHandler eh;
    private GalleryDto selectedGallery;

    private List<ConcertPlanReferencer> concertPlanReferencers;

    private HashMap<Integer, TicketDto> ticketMap = new HashMap<>();

    private List<CheckBox> checkBoxes = new ArrayList<>();

    private Alerts alertCreator = new Alerts();

    public static void setShowId(int newShowId) {
        showId = newShowId;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        availability_lbl.setText(BundleManager.getBundle().getString("availability.lbl"));
        gallery_lbl.setText(BundleManager.getBundle().getString("gallery.lbl"));
        amount_lbl.setText(BundleManager.getBundle().getString("amount.lbl"));
        total_lbl.setText(BundleManager.getBundle().getString("total.lbl"));
        stage_lbl.setText(BundleManager.getBundle().getString("stage.lbl"));
        concertPlan_cancel_btn.setText(BundleManager.getBundle().getString("cancel.btn"));
        concertPlan_reserve_btn.setText(BundleManager.getBundle().getString("reserve.btn"));
        concertPlan_sell_btn.setText(BundleManager.getBundle().getString("sell.btn"));

        for (int i = 1; i <= 10; i++) {
            concertPlan_amount_cb.getItems().add(i);
        }
        concertPlan_amount_cb.setValue(1);
        concertPlan_amount_cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                concertPlan_totalSum_tf.setText((Integer) observable.getValue() * getPriceOfGallery(selectedGallery) + "");
            }
        });
        initRoomPlanReferencers();
        initConcertPlan();
        initChangeListener();
    }

    @FXML
    public void concertPlan_cancel_btn_clicked() {
        closeWindow();
    }

    @FXML
    public void concertPlan_sell_btn_clicked() {
        List<TicketDto> ticketsToSell;
        List<TicketDto> availableTickets = new ArrayList<>();
        ConcertPlanReferencer selectedReferencer = null;

        for (ConcertPlanReferencer c : concertPlanReferencers) {
            if (c.getCb().isSelected()) {
                selectedReferencer = c;
                //Get all tickets where status is available
                for (TicketStatus t : c.getTickets()) {
                    if (t.getStatus().equals("available")) {
                        availableTickets.add(t.getTicket());
                    }
                }
                //Check if there is enough available tickets to sell
                if (availableTickets.size() < concertPlan_amount_cb.getValue()) {
                    alertCreator.warningAlert(BundleManager.getMessageBundle().getString("thereareonly.warning") +" "+ availableTickets.size() + " " + BundleManager.getBundle().getString("available"));
                    return;
                }

                //Get the first i tickets of the list
                ticketsToSell = new ArrayList<>(availableTickets.subList(0, concertPlan_amount_cb.getValue()));

                    //@author Aysel Oeztuerk 0927011
                    //setData: selectedTickets and true (true means sell tickets and false means reserve tickets)
                    LOGGER.debug("Boolean:" + true);
                    for(TicketDto t: ticketsToSell){
                        LOGGER.debug("Ticket id:" + t.getId());
                        LOGGER.debug("Description:" + t.getDescription());
                        LOGGER.debug("Seat id:" + t.getSeatId());
                    }
                    //WhichCustomerController.setData(selectedTickets,true);
                    Stage selectCustomerStage = new Stage();
                    selectCustomerStage.initModality(Modality.APPLICATION_MODAL);

                    SpringFxmlLoader.LoadWrapper sfLoader = springFxmlLoader.loadAndWrap
                            ("/gui/fxml/whichCustomerToSellOrReserveTickets.fxml");
                    selectCustomerStage.setScene(new Scene((Parent) sfLoader.getLoadedObject()));
                    selectCustomerStage.setTitle(BundleManager.getBundle().getString("selectcustomer.info"));

                    WhichCustomerController controller = (WhichCustomerController) sfLoader.getController();
                    controller.setData(ticketsToSell, true);
                    //SelectCustomerController.setData(selectedTickets, true);
                    selectCustomerStage.show();

                    closeWindow();


                    //change customer
                    //List<TicketIdentifierDto> identifiers = this.ticketService.sellTicket(ticketsToSell, null);
                    //this.alertCreator.infoAlert("Tickets sold successfully");
                    //closeWindow();

            }
        }

        if (selectedReferencer == null) {
            this.alertCreator.warningAlert(BundleManager.getMessageBundle().getString("selectseats.warning"));
        }

    }

    @FXML
    public void concertPlan_reserve_btn_clicked() {
        List<TicketDto> ticketsToReserve;
        List<TicketDto> availableTickets = new ArrayList<>();
        ConcertPlanReferencer selectedReferencer = null;

        for (ConcertPlanReferencer c : concertPlanReferencers) {
            if (c.getCb().isSelected()) {
                selectedReferencer = c;
                //Get all tickets where status is available
                for (TicketStatus t : c.getTickets()) {
                    if (t.getStatus().equals("available")) {
                        availableTickets.add(t.getTicket());
                    }
                }
                //Check if there is enough available tickets to reserve
                if (availableTickets.size() < concertPlan_amount_cb.getValue()) {
                    alertCreator.warningAlert(BundleManager.getMessageBundle().getString("thereareonly.warning") +" "+ availableTickets.size() +" "+ BundleManager.getBundle().getString("available"));
                    return;
                }

                //Get the first i tickets of the list
                ticketsToReserve = new ArrayList<>(availableTickets.subList(0, concertPlan_amount_cb.getValue()));


                    //TODO

                    //@author Aysel Oeztuerk 0927011
                    //setData: selectedTickets and true (true means sell tickets and false means reserve tickets)
                    //WhichCustomerController.setData(selectedTickets, false);
                    Stage selectCustomerStage = new Stage();
                    selectCustomerStage.initModality(Modality.APPLICATION_MODAL);

                    SpringFxmlLoader.LoadWrapper sfLoader = springFxmlLoader.loadAndWrap
                            ("/gui/fxml/whichCustomerToSellOrReserveTickets.fxml");
                    selectCustomerStage.setScene(new Scene((Parent) sfLoader.getLoadedObject()));
                    selectCustomerStage.setTitle(BundleManager.getBundle().getString("selectcustomer.info"));

                    WhichCustomerController controller = (WhichCustomerController) sfLoader.getController();
                    controller.setData(ticketsToReserve, false);
                    selectCustomerStage.show();

                    closeWindow();

                    //change customer
                    //List<TicketIdentifierDto> identifiers = this.ticketService.reserveTicket(ticketsToReserve, null);

                    //int reservationNumber = identifiers.get(0).getReservationID();

                    //this.alertCreator.infoAlert("Tickets reserved successfully.\n \n Your reservation number is " + reservationNumber);
                    //closeWindow();

            }
        }

        if (selectedReferencer == null) {
            this.alertCreator.warningAlert(BundleManager.getMessageBundle().getString("selectseats.warning"));
        }

    }

    /**
     * @param column   the column index of gridpane
     * @param row      the row index of gridpane
     * @param gridPane the given gridpane
     * @return the node at position (column,row)
     * @author Lina Wang 1326922
     */
    public Node getNodeByRowColIndex(int column, int row, GridPane gridPane) {
        Node res = null;
        ObservableList<Node> childrens = gridPane.getChildren();
        for (Node n : childrens) {
            if (gridPane.getRowIndex(n) == row && gridPane.getColumnIndex(n) == column) {
                res = n;
            }
        }
        return res;
    }

    public void initConcertPlan() {
        LOGGER.debug("Initialization of concertplan");

        int rowForLeft = 0;
        int rowForRight = 0;
        int column = 0;
        int counter = 0;
        int rowCount = concertPlan_sideLeftGrid_gp.getRowConstraints().size();

        for (ConcertPlanReferencer c : concertPlanReferencers) {

            LOGGER.debug("This is gallery " + c.getGallery().getName() + " " + c.getTickets().size());
            CheckBox cb = c.getCb();
            cb.getStylesheets().clear();
            checkBoxes.add(cb);
            if (c.getGallery().getId() == 1) {
                cb.setSelected(false);
                cb.getStylesheets().add(getClass().getResource("/gui/css/GalleryStyleSheetSide.css")
                        .toExternalForm());
                if (getNodeByRowColIndex(column, rowForLeft, concertPlan_sideLeftGrid_gp) == null
                        && rowForLeft < rowCount) {
                    concertPlan_sideLeftGrid_gp.add(cb, column, rowForLeft);
                    rowForLeft++;
                } else {
                    concertPlan_sideRightGrid_gp.add(cb, column, rowForRight);
                    rowForRight++;
                }

            }

            if (c.getGallery().getId() == 2) {
                cb.setSelected(false);
                cb.getStylesheets().add(getClass().getResource("/gui/css/GalleryStyleSheetMiddle.css")
                        .toExternalForm());

                concertPlan_galleryPane_bp.setCenter(cb);
            }

            if (c.getGallery().getId() == 3) {
                cb.setSelected(false);
                cb.getStylesheets().add(getClass().getResource("/gui/css/GalleryStyleSheetBack.css")
                        .toExternalForm());

                concertPlan_galleryPane_bp.setBottom(cb);
                concertPlan_galleryPane_bp.setAlignment(cb, Pos.CENTER);
                //Insets : top, right, bottom, left
                concertPlan_galleryPane_bp.setMargin(cb, new Insets(0, 0, 10, 0));
            }

        }
    }

    public void initRoomPlanReferencers() {
        try {
            LOGGER.debug("Initialization of Concertplan Referencers");

            List<TicketDto> availableTickets = ticketService.getAvaibleOfShow(showId);
            List<TicketDto> soldTickets = ticketService.getSoldOfShow(showId);
            List<SeatDto> seats = seatService.getAllOfShow(showId);

            List<SeatDto> sideGallerySeats = new ArrayList<>();
            List<SeatDto> middleGallerySeats = new ArrayList<>();
            List<SeatDto> backGallerySeats = new ArrayList<>();

            // Retrieve seats of given Gallery
            for (SeatDto s : seats) {
                if (s.getGallery().getId() == 1) {
                    sideGallerySeats.add(s);
                } else if (s.getGallery().getId() == 2) {
                    middleGallerySeats.add(s);
                } else if (s.getGallery().getId() == 3) {
                    backGallerySeats.add(s);
                }
            }

            for (TicketDto t : availableTickets) {
                ticketMap.put(t.getSeatId(), t);
            }
            for (TicketDto t : soldTickets) {
                ticketMap.put(t.getSeatId(), t);
            }

            concertPlanReferencers = new ArrayList();

            //Initialization for side gallery
            int sizeOfSideGallery = sideGallerySeats.size();
            int amountOfCheckBox = (int) Math.round(sizeOfSideGallery / 25);
            LOGGER.debug("Size Of Side Gallery: " + sizeOfSideGallery);
            LOGGER.debug("Amount Of CheckBox: " + +amountOfCheckBox);

            //Counter for generating checkboxes of side gallery
            int generatedCheckBoxForSide = 1;
            int generatedSeatsForSide = 0;

            LOGGER.debug("Initialization of Side Gallery : " + sideGallerySeats.size());
            while (generatedCheckBoxForSide <= amountOfCheckBox) {
                List<TicketStatus> tmpSideGalleryTickets = new ArrayList<>();
                List<SeatDto> tmpSideGallerySeats = new ArrayList<>();
                LOGGER.debug("Generated Seats for Side: " + generatedSeatsForSide);
                while (generatedSeatsForSide < generatedCheckBoxForSide * 25) {
                    SeatDto s = sideGallerySeats.get(generatedSeatsForSide);
                    String status = "reserved";
                    if (ticketMap.containsKey(s.getId())) {
                        if (availableTickets.contains(ticketMap.get(s.getId()))) {
                            status = "available";
                        } else if (soldTickets.contains(ticketMap.get(s.getId()))) {
                            status = "sold";
                        }
                        tmpSideGallerySeats.add(s);
                        tmpSideGalleryTickets.add(new TicketStatus(ticketMap.get(s.getId()), status));
                        generatedSeatsForSide++;
                    }
                }
                CheckBox side = new CheckBox();
                concertPlanReferencers.add(new ConcertPlanReferencer(side, tmpSideGalleryTickets, tmpSideGallerySeats,
                        tmpSideGallerySeats.get(1).getGallery(), 50.0));
                LOGGER.debug("Gallery Tickets Size: " + tmpSideGalleryTickets.size());
                LOGGER.debug("Gallery Seats Size: " + tmpSideGallerySeats.size());
                generatedCheckBoxForSide++;
            }
            sideGalleryInitialized = true;

            //Initialization for middle gallery
            List<TicketStatus> middleGalleryTickets = new ArrayList<>();
            for (SeatDto s : middleGallerySeats) {
                String status = "reserved";
                if (ticketMap.containsKey(s.getId())) {
                    if (availableTickets.contains(ticketMap.get(s.getId()))) {
                        status = "available";
                    } else if (soldTickets.contains(ticketMap.get(s.getId()))) {
                        status = "sold";
                    }

                    middleGalleryTickets.add(new TicketStatus(ticketMap.get(s.getId()), status));
                }
            }
            CheckBox middle = new CheckBox();
            concertPlanReferencers.add(new ConcertPlanReferencer(middle, middleGalleryTickets, middleGallerySeats,
                    middleGallerySeats.get(1).getGallery(), 80.0));
            middleGalleryInitialized = true;


            //Initialization for back gallery
            List<TicketStatus> backGalleryTickets = new ArrayList<>();
            for (SeatDto s : backGallerySeats) {
                String status = "reserved";
                if (ticketMap.containsKey(s.getId())) {
                    if (availableTickets.contains(ticketMap.get(s.getId()))) {
                        status = "available";
                    } else if (soldTickets.contains(ticketMap.get(s.getId()))) {
                        status = "sold";
                    }

                    backGalleryTickets.add(new TicketStatus(ticketMap.get(s.getId()), status));
                }
            }
            CheckBox back = new CheckBox();
            concertPlanReferencers.add(new ConcertPlanReferencer(back, backGalleryTickets, backGallerySeats,
                    backGallerySeats.get(1).getGallery(), 35.0));
            backGalleryInitialized = true;


        } catch (ServiceException e) {

        }
    }

    public int getAvailableOfGallery(GalleryDto gallery) {
        int available = 0;
        for (ConcertPlanReferencer c : concertPlanReferencers) {
            if (c.getGallery() == gallery) {
                for (TicketStatus t : c.getTickets()) {
                    if (t.getStatus() == "available") {
                        available++;
                    }
                }
            }
        }
        return available;
    }

    public GalleryDto getGalleryOfCheckBox(CheckBox cb) {
        for (ConcertPlanReferencer c : concertPlanReferencers) {
            if (c.getCb() == cb) {
                return c.getGallery();
            }
        }
        return null;
    }

    public double getPriceOfGallery(GalleryDto gallery) {
        for (ConcertPlanReferencer c : concertPlanReferencers) {
            if (c.getGallery() == gallery) {
                return c.getPrice();
            }
        }
        return 0;
    }

    private void initChangeListener() {
        eh = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() instanceof CheckBox) {
                    CheckBox cb = (CheckBox) event.getSource();
                    //Select one checkbox at time
                    for (CheckBox checkBox : checkBoxes) {
                        if (checkBox != cb) {
                            checkBox.setSelected(false);
                        }
                    }
                    selectedGallery = getGalleryOfCheckBox(cb);

                }
                int available = getAvailableOfGallery(selectedGallery);
                concertPlan_availability_txt.setText(available +" "+ BundleManager.getBundle().getString("available"));
                concertPlan_gallery_tf.setText(selectedGallery.getName());
                concertPlan_totalSum_tf.setText(concertPlan_amount_cb.getValue() * getPriceOfGallery(selectedGallery) + "");

            }
        };

        for (ConcertPlanReferencer c : concertPlanReferencers) {
            CheckBox cb = c.getCb();
            cb.setOnAction(eh);

        }
    }

    /**
     * Closes the roomPlan window
     */
    private void closeWindow() {
        ((Stage) concertPlan_cancel_btn.getScene().getWindow()).close();
    }
}
