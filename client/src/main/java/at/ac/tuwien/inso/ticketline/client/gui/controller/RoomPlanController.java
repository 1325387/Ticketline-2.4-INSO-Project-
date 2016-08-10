package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.extraObjects.RoomPlanReferencer;
import at.ac.tuwien.inso.ticketline.client.service.PerformanceService;
import at.ac.tuwien.inso.ticketline.client.service.SeatService;
import at.ac.tuwien.inso.ticketline.client.service.TicketService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.ticketline.dto.RoomPlanTicketViewModelDto;
import at.ac.tuwien.inso.ticketline.dto.SeatDto;
import at.ac.tuwien.inso.ticketline.dto.TicketDto;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.GridCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * @author Raphael Schotola 1225193,
 * @author Sissi Zhan 1325880,
 * @author Aysel Öztürk 0927011
 */
@Component
public class RoomPlanController implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomPlanController.class);

    @Autowired
    private TicketService ticketService;
    @Autowired
    private SeatService seatService;
    @Autowired
    private PerformanceService performanceService;
    //@author Aysel Oeztuerk 0927011
    @Autowired
    private SpringFxmlLoader springFxmlLoader;

    private Alerts alertCreator = new Alerts();

    private static int showId;

    //for multiSelection
    RoomPlanReferencer[][] refArray;
    private static int maxWidth;
    private static int maxHeight;

    //variables for changeListener
    private EventHandler eh;
    private int selectedSeatsCounter;
    private double selectedSeatsTotalPrice;

    //contains tickets for ticket table
    private ObservableList<RoomPlanTicketViewModelDto> selectedTicketsList = observableArrayList();
    RoomPlanTicketViewModelDto view;

    //contains available and sold Tickets
    private Map<Integer, TicketDto> ticketMap = new HashMap<>();

    //for easier access to all those checkboxes
    private Map<String, CheckBox> checkBoxMap = new HashMap<>();

    //Objects with String checkBoxId (like in the fxml file), seatDto, ticketDto, status(available,sold,reserved)
    List<RoomPlanReferencer> roomPlanReferencers;

    @FXML
    ToggleGroup roomPlan_selectionStyle_tg;
    @FXML
    RadioButton roomPlan_manualSelection_rb;
    @FXML
    RadioButton roomPlan_multiSelection_rb;
    @FXML
    TextField roomPlan_selectionAmount_tf;

    @FXML
    TableView roomPlan_ticketsTable_tv;
    @FXML
    TableColumn roomPlan_ticketsTable_seat_tc;
    @FXML
    TableColumn roomPlan_ticketsTable_price_tc;

    @FXML
    Tooltip roomPlan_multiSelection_tt;

    @FXML
    private GridPane roomPlan_seatsGrid_gp;

    @FXML
    private Label roomPlan_performanceName_tl;
    @FXML
    private Label screen_lbl;
    @FXML
    private Label available_lbl;
    @FXML
    private Label reserved_lbl;
    @FXML
    private Label sold_lbl;
    @FXML
    private Label selectedSeats_lbl;
    @FXML
    private Label total_lbl;


    @FXML
    private Button roomPlan_cancel_btn;
    @FXML
    private Button roomPlan_sell_btn;
    @FXML
    private Button roomPlan_reserve_btn;

    @FXML
    private TextField roomPlan_selectedSeats_tf;
    @FXML
    private TextField roomPlan_totalSum_tf;

    public static void setShowId(int newShowId) {
        showId = newShowId;
    }

    public static void setMaxWidth(int max) {
        maxWidth = max;
    }

    public static void setMaxHeight(int max) {
        maxHeight = max;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL url, ResourceBundle resBundle) {

        roomPlan_cancel_btn.setText(BundleManager.getBundle().getString("cancel.btn"));
        roomPlan_reserve_btn.setText(BundleManager.getBundle().getString("reserve.btn"));
        roomPlan_sell_btn.setText(BundleManager.getBundle().getString("sell.btn"));
        roomPlan_ticketsTable_seat_tc.setText(BundleManager.getBundle().getString("seat.tc"));
        roomPlan_ticketsTable_price_tc.setText(BundleManager.getBundle().getString("priceineuro.tc"));

        screen_lbl.setText(BundleManager.getBundle().getString("screen.lbl"));
        available_lbl.setText(BundleManager.getBundle().getString("available.lbl"));
        reserved_lbl.setText(BundleManager.getBundle().getString("reserved.lbl"));
        sold_lbl.setText(BundleManager.getBundle().getString("sold.lbl"));
        selectedSeats_lbl.setText(BundleManager.getBundle().getString("selectedseats.lbl"));
        total_lbl.setText(BundleManager.getBundle().getString("total.lbl"));

        roomPlan_manualSelection_rb.setText(BundleManager.getBundle().getString("manualselection.rb"));
        roomPlan_multiSelection_rb.setText(BundleManager.getBundle().getString("multiselection.rb"));


        initRoomPlanReferencers();
        initRoomPlan();
        initChangeListener();
        initTfAmountChangeListener();
        selectedSeatsCounter = 0;
        selectedSeatsTotalPrice = 0.0;
        initTicketTable();
        selectedTicketsList.clear();
    }

    /**
     * @author Raphael Schotola 1225193
     */
    @FXML
    public void roomPlan_sell_btn_clicked() {

        List<RoomPlanReferencer> selectedCheckBoxReferencers = new ArrayList<>();
        List<TicketDto> selectedTickets = new ArrayList<>();

        //get all selected checkBoxes
        if (!roomPlanReferencers.isEmpty()) {
            for (RoomPlanReferencer r : roomPlanReferencers) {
                if (r.getCheckBox().isSelected()) {
                    selectedCheckBoxReferencers.add(r);
                }
            }
        }

        //get all tickets from selected checkBoxes
        if (!selectedCheckBoxReferencers.isEmpty()) {
            for (RoomPlanReferencer r : selectedCheckBoxReferencers) {
                selectedTickets.add(r.getTicket());
            }
        } else {
            this.alertCreator.inputErrorAlert(BundleManager.getMessageBundle().getString("selectseats.warning"));
            return;
        }

        //@author Aysel Oeztuerk 0927011
        //setData: selectedTickets and true (true means sell tickets and false means reserve tickets)
        LOGGER.debug("Boolean:" + true);
        for (TicketDto t : selectedTickets) {
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
        controller.setData(selectedTickets, true);
        //SelectCustomerController.setData(selectedTickets, true);
        selectCustomerStage.show();

        closeWindow();
    }

    /**
     * @author Sissi Zhan 1325880, Raphael Schotola 1225193, Aysel Oeztuerk 0927011
     */
    @FXML
    public void roomPlan_reserve_btn_clicked() {

        List<RoomPlanReferencer> selectedCheckBoxReferencers = new ArrayList<>();
        List<TicketDto> selectedTickets = new ArrayList<>();

        //get all selected checkBoxes
        if (!roomPlanReferencers.isEmpty()) {
            for (RoomPlanReferencer r : roomPlanReferencers) {
                if (r.getCheckBox().isSelected()) {
                    selectedCheckBoxReferencers.add(r);
                }
            }
        }

        //get all tickets from selected checkBoxes
        if (!selectedCheckBoxReferencers.isEmpty()) {
            for (RoomPlanReferencer r : selectedCheckBoxReferencers) {
                selectedTickets.add(r.getTicket());
            }
        } else {
            this.alertCreator.inputErrorAlert(BundleManager.getMessageBundle().getString("selectseats.warning"));
            return;
        }

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
        controller.setData(selectedTickets, false);
        selectCustomerStage.show();

        closeWindow();
    }

    @FXML
    public void roomPlan_cancel_btn_clicked() {
        closeWindow();
    }

    @FXML
    public void SelectionTypeChanged() {
        //clear all checkBoxes
        for (RoomPlanReferencer r : roomPlanReferencers) {
            if (r.getStatus().equals("available")) {
                r.getCheckBox().setSelected(false);
            }
        }
        selectedSeatsCounter = 0;
        selectedSeatsTotalPrice = 0;
        roomPlan_selectedSeats_tf.setText(selectedSeatsCounter + "");
        roomPlan_totalSum_tf.setText(selectedSeatsTotalPrice + "");
        selectedTicketsList.clear();
    }

    /**
     * Automaticly selects "amount" seats when you click one seat
     *
     * @param amount    the amount of seats to be selected. amount >=1
     * @param clickedCB the seat you selected
     * @author Raphael Schotola 1225193
     */
    private void calcSeats(double amount, CheckBox clickedCB) {

        //clear all checkBoxes
        for (RoomPlanReferencer r : roomPlanReferencers) {
            if (r.getStatus().equals("available")) {
                r.getCheckBox().setSelected(false);
            }
        }
        //clear table
        selectedTicketsList.clear();

        //List for the ticket table
        List<TicketDto> selectedTickets = new ArrayList<>();

        if (amount < 1 || amount > 9) {
            alertCreator.inputErrorAlert(BundleManager.getExceptionBundle().getString("numbersinput.error"));
            return;
        }

        clickedCB.setSelected(true);
        selectedSeatsCounter = 1;
        selectedSeatsTotalPrice = getTicketOfCheckBox(clickedCB).getPrice();

        //find RoomPlanReferencer for the Checkbox
        RoomPlanReferencer clickedRPR = null;
        for (RoomPlanReferencer r : roomPlanReferencers) {
            if (r.getCheckBox().equals(clickedCB)) {
                clickedRPR = r;
                selectedTickets.add(r.getTicket());
                break;
            }
        }

        int xPos = clickedRPR.getSeat().getSequence() - 1;
        int yPos = clickedRPR.getSeat().getRow().getSequence() - 1;

        //try 1 to the left, 1 to the right, 2 to the left etc
        int selectedSideways = 1;
        boolean freeToTheLeft = true;
        boolean freeToTheRight = true;
        List<RoomPlanReferencer> seatsToTheRight = new ArrayList<>();
        List<RoomPlanReferencer> seatsToTheLeft = new ArrayList<>();
        int count = 0;
        TicketDto t;
        while (selectedSideways < amount) {
            if (!freeToTheLeft && !freeToTheRight) {
                break;
            }
            count++;
            if (count >= amount) {
                break;
            }
            //go right first
            if (freeToTheRight) {
                RoomPlanReferencer next = checkSeat(xPos + count, yPos);
                if (next != null) {
                    seatsToTheRight.add(next);
                    next.getCheckBox().setSelected(true);
                    selectedSideways++;
                    t = getTicketOfCheckBox(next.getCheckBox());
                    selectedSeatsTotalPrice += t.getPrice();
                    selectedSeatsCounter++;
                    selectedTickets.add(t);
                } else freeToTheRight = false;
            }

            //check if enough seats already
            if (selectedSideways >= amount) {
                break;
            }

            //go left second
            if (freeToTheLeft) {
                RoomPlanReferencer next = checkSeat(xPos - count, yPos);
                if (next != null) {
                    seatsToTheLeft.add(next);
                    next.getCheckBox().setSelected(true);
                    selectedSideways++;
                    t = getTicketOfCheckBox(next.getCheckBox());
                    selectedSeatsTotalPrice += t.getPrice();
                    selectedSeatsCounter++;
                    selectedTickets.add(t);
                } else freeToTheLeft = false;
            }
        }


        //if more selections need to be made
        if (selectedSideways < amount) {
            LOGGER.debug("selected: " + selectedSideways + ", amount: " + amount);

            //if only one split off -> split another (last of the list to not split the rest)
            if (selectedSideways == amount - 1) {
                if (seatsToTheLeft.size() > 0) {
                    Iterator<RoomPlanReferencer> it = seatsToTheLeft.iterator();
                    while (it.hasNext()) {
                        RoomPlanReferencer r = it.next();
                        if (!it.hasNext()) {
                            it.remove();
                            r.getCheckBox().setSelected(false);
                            selectedSideways--;
                            t = getTicketOfCheckBox(r.getCheckBox());
                            selectedSeatsTotalPrice -= t.getPrice();
                            selectedSeatsCounter--;
                            selectedTickets.remove(t);
                        }
                    }
                } else if (seatsToTheRight.size() > 0) {
                    Iterator<RoomPlanReferencer> it = seatsToTheRight.iterator();
                    while (it.hasNext()) {
                        RoomPlanReferencer r = it.next();
                        if (!it.hasNext()) {
                            it.remove();
                            r.getCheckBox().setSelected(false);
                            selectedSideways--;
                            t = getTicketOfCheckBox(r.getCheckBox());
                            selectedSeatsTotalPrice -= t.getPrice();
                            selectedSeatsCounter--;
                            selectedTickets.remove(t);
                        }
                    }
                }
            }

            //if one person would sit alone -> don't let them sit alone
            if (seatsToTheLeft.size() + seatsToTheRight.size() == 0) {
                clickedCB.setSelected(false);
                selectedSideways--;
                t = getTicketOfCheckBox(clickedCB);
                selectedSeatsTotalPrice -= t.getPrice();
                selectedSeatsCounter--;
                selectedTickets.remove(t);
            }


            //find seats for the unseated
            int unseated = (int) amount - selectedSideways;
            //try left and right to find the nearest space for them
            freeToTheLeft = true;
            freeToTheRight = true;
            int amountRight = 0;
            int amountLeft = 0;
            List<RoomPlanReferencer> left = new ArrayList<>();
            List<RoomPlanReferencer> right = new ArrayList<>();
            count = 0;
            boolean didItWork = false;
            boolean twoNewGroups = false;
            List<RoomPlanReferencer> bestSoFar1 = new ArrayList<>();

            List<RoomPlanReferencer> bestSoFar2 = new ArrayList<>();

            boolean stillInSmallerLeft = false;
            boolean stillInSmallerRight = false;
            List<RoomPlanReferencer> smallerLeft = new ArrayList<>();
            List<RoomPlanReferencer> smallerRight = new ArrayList<>();

            while (unseated > 0) {
                count++;
                if (!freeToTheLeft && !freeToTheRight) {
                    break;
                }
                if (freeToTheLeft && xPos - count >= 0) {
                    RoomPlanReferencer ref = checkSeat(xPos - count, yPos);
                    if (ref != null) {
                        left.add(ref);
                        amountLeft++;
                        if (amountLeft == unseated) {
                            didItWork = true;
                            break;
                        }
                        if (!stillInSmallerLeft) {
                            smallerLeft = findSmallerList(bestSoFar1, bestSoFar2);
                        }
                        if (left.size() > 1 && left.size() > smallerLeft.size()) {
                            smallerLeft.clear();
                            smallerLeft.addAll(left);
                            stillInSmallerLeft = true;
                            if (bestSoFar1.size() + bestSoFar2.size() >= unseated && unseated != 3) {
                                didItWork = true;
                                twoNewGroups = true;
                                break;
                            }
                        }
                    } else {
                        left.clear();
                        amountLeft = 0;
                        stillInSmallerLeft = false;
                    }
                } else {
                    freeToTheLeft = false;
                }
                if (freeToTheRight && xPos + count < maxWidth) {
                    RoomPlanReferencer ref = checkSeat(xPos + count, yPos);
                    if (ref != null) {
                        right.add(ref);
                        amountRight++;
                        if (amountRight == unseated) {
                            didItWork = true;
                            break;
                        }
                        if (!stillInSmallerRight) {
                            smallerRight = findSmallerList(bestSoFar1, bestSoFar2);
                        }
                        if (right.size() > 1 && right.size() > smallerRight.size()) {
                            smallerRight.clear();
                            smallerRight.addAll(right);
                            stillInSmallerRight = true;
                            if (bestSoFar1.size() + bestSoFar2.size() >= unseated && unseated != 3) {
                                didItWork = true;
                                twoNewGroups = true;
                                break;
                            }
                        }
                    } else {
                        right.clear();
                        amountRight = 0;
                        stillInSmallerRight = false;
                    }
                } else {
                    freeToTheRight = false;
                }
            }

            if (didItWork) {
                if (twoNewGroups) {
                    if (!(bestSoFar1.size() + bestSoFar2.size() >= amount)) {
                        for (RoomPlanReferencer ref : roomPlanReferencers) {
                            if (ref.getCheckBox() != null) {
                                ref.getCheckBox().setSelected(false);
                            }
                        }
                        selectedTickets.clear();
                        selectedTicketsList.clear();
                        alertCreator.inputErrorAlert(BundleManager.getExceptionBundle().getString("unabletosplit.error"));
                        return;
                    }
                    CheckBox cb;
                    int i = 0;
                    while (unseated > 0) {
                        if (bestSoFar1.size() > i) {
                            cb = bestSoFar1.get(i).getCheckBox();
                            cb.setSelected(true);
                            unseated--;
                            t = getTicketOfCheckBox(cb);
                            selectedSeatsTotalPrice += t.getPrice();
                            selectedSeatsCounter++;
                            selectedTickets.add(t);
                        }
                        if (unseated == 0) {
                            break;
                        }
                        if (bestSoFar2.size() > i) {
                            cb = bestSoFar2.get(i).getCheckBox();
                            cb.setSelected(true);
                            unseated--;
                            t = getTicketOfCheckBox(cb);
                            selectedSeatsTotalPrice += t.getPrice();
                            selectedSeatsCounter++;
                            selectedTickets.add(t);
                        }
                        i++;
                    }
                } else {
                    if (left.size() == unseated) {
                        for (RoomPlanReferencer r : left) {
                            r.getCheckBox().setSelected(true);
                            t = getTicketOfCheckBox(r.getCheckBox());
                            selectedSeatsTotalPrice += t.getPrice();
                            selectedSeatsCounter++;
                            selectedTickets.add(t);
                        }
                    } else if (right.size() == unseated) {
                        for (RoomPlanReferencer r : right) {
                            r.getCheckBox().setSelected(true);
                            t = getTicketOfCheckBox(r.getCheckBox());
                            selectedSeatsTotalPrice += t.getPrice();
                            selectedSeatsCounter++;
                            selectedTickets.add(t);
                        }
                    }
                }
            }


            //if it was impossible to seat everyone without having someone seat alone and without splitting the group
            //into more than 2 subgroups -> don't do it
            else {
                for (RoomPlanReferencer r : seatsToTheLeft) {
                    r.getCheckBox().setSelected(false);
                }
                for (RoomPlanReferencer r : seatsToTheRight) {
                    r.getCheckBox().setSelected(false);
                }
                for (RoomPlanReferencer r : left) {
                    r.getCheckBox().setSelected(false);
                }
                for (RoomPlanReferencer r : right) {
                    r.getCheckBox().setSelected(false);
                }
                clickedCB.setSelected(false);
                selectedTickets.clear();
                selectedTicketsList.clear();
                alertCreator.inputErrorAlert(BundleManager.getExceptionBundle().getString("unabletosplit.error"));
            }
        }

        roomPlan_selectedSeats_tf.setText(selectedSeatsCounter + "");
        roomPlan_totalSum_tf.setText(selectedSeatsTotalPrice + "");
        for(TicketDto ticket: selectedTickets){
            view = new RoomPlanTicketViewModelDto();
            view.setSeatId(ticket.getSeatId());
            view.setPrice(ticket.getPrice());
            selectedTicketsList.add(view);
        }
    }

    //checks wether seat at xpos,ypos is not null and available
    private RoomPlanReferencer checkSeat(int xPos, int yPos) {
        RoomPlanReferencer next;
        if (xPos < maxWidth && xPos >= 0 && yPos < maxHeight && yPos >= 0) {
            next = refArray[xPos][yPos];
            if (next != null) {
                if (next.getStatus().equals("available") && !next.getCheckBox().isSelected()) {
                    return next;
                }
            }
        }

        return null;
    }

    //returns smaller list, if the have the same size -> returns second
    private List<RoomPlanReferencer> findSmallerList(List<RoomPlanReferencer> first, List<RoomPlanReferencer> second) {
        if (first.size() < second.size()) {
            return first;
        } else if (second.size() < first.size()) {
            return second;
        } else {
            return second;
        }
    }

    /**
     * @author Sissi Zhan 1325880
     * <p>
     * Gets the ticket of a checkbox
     */
    public TicketDto getTicketOfCheckBox(CheckBox cb) {
        TicketDto ret = null;
        for (RoomPlanReferencer rpr : roomPlanReferencers) {
            if (rpr.getCheckBox() == cb) {
                ret = rpr.getTicket();
            }
        }
        return ret;
    }

    private void initTicketTable(){
        LOGGER.info("init EventShowTable()");

        roomPlan_ticketsTable_seat_tc.setCellValueFactory(new PropertyValueFactory<RoomPlanTicketViewModelDto, Integer>("seatId"));
        roomPlan_ticketsTable_price_tc.setCellValueFactory(new PropertyValueFactory<RoomPlanTicketViewModelDto, Double>("price"));
        roomPlan_ticketsTable_tv.setItems(selectedTicketsList);
    }

    /**
     * @author Raphael Schotola 1225193
     * Listens to the multiselection-amount tf and only alows input of 1-9
     */
    private void initTfAmountChangeListener() {
        eh = new EventHandler<KeyEvent>() {
            String current;

            @Override
            public void handle(KeyEvent event) {
                if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
                    LOGGER.debug(roomPlan_selectionAmount_tf.getText());
                    if (roomPlan_selectionAmount_tf.getText().matches("[1-9]")) {
                        current = roomPlan_selectionAmount_tf.getText();
                    } else {
                        current = "";
                    }
                }
                if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
                    if (event.getCode().isArrowKey() || roomPlan_selectionAmount_tf.getText().equals("")) {
                        return;
                    }
                    if (event.getText().matches("[1-9]")) {
                        roomPlan_selectionAmount_tf.setText(event.getText());
                    } else {
                        roomPlan_selectionAmount_tf.setText(current);
                    }
                }
            }
        };
        roomPlan_selectionAmount_tf.setOnKeyReleased(eh);
        roomPlan_selectionAmount_tf.setOnKeyPressed(eh);
    }


    /**
     * @author Sissi Zhan 1325880, Raphael Schotola 1225193
     * <p>
     * Listens to selection of checkboxes/seats and sets the amount of selected seats and
     * total price of selected seats in GUI
     */
    private void initChangeListener() {
        eh = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() instanceof CheckBox) {
                    CheckBox cb = (CheckBox) event.getSource();
                    TicketDto t = getTicketOfCheckBox(cb);

                    if (roomPlan_multiSelection_rb.isSelected()) {
                        int amount;
                        try {
                            amount = Integer.parseInt(roomPlan_selectionAmount_tf.getText());
                        } catch (Exception e) {
                            amount = 0;
                        }
                        if(roomPlan_selectionAmount_tf.getText().equals("")){
                            amount = 0;
                        }
                        calcSeats(amount, cb);
                        return;
                    }

                    if (cb.isSelected()) {
                        selectedSeatsTotalPrice += t.getPrice();
                        selectedSeatsCounter++;

                        view = new RoomPlanTicketViewModelDto();
                        view.setPrice(t.getPrice());
                        view.setSeatId(t.getSeatId());
                        selectedTicketsList.add(view);
                    } else {
                        selectedSeatsTotalPrice -= t.getPrice();
                        selectedSeatsCounter--;
                        //reset ticketList
                        selectedTicketsList.clear();
                        for(RoomPlanReferencer r: roomPlanReferencers){
                            if(r.getCheckBox().isSelected()){
                                view = new RoomPlanTicketViewModelDto();
                                view.setPrice(r.getTicket().getPrice());
                                view.setSeatId(r.getSeat().getId());
                                selectedTicketsList.add(view);
                            }
                        }
                    }
                }
                roomPlan_selectedSeats_tf.setText(selectedSeatsCounter + "");
                roomPlan_totalSum_tf.setText(selectedSeatsTotalPrice + "");
            }
        };

        for (RoomPlanReferencer roomPlanReferencer : roomPlanReferencers) {
            CheckBox cb = roomPlanReferencer.getCheckBox();
            cb.setOnAction(eh);
        }
    }

    /**
     * @author Raphael Schotola 1225193
     */
    private void initRoomPlanReferencers() {
        try {
            refArray = new RoomPlanReferencer[maxWidth][maxHeight];

            List<TicketDto> availableTicketDtos = ticketService.getAvaibleOfShow(showId);
            List<TicketDto> soldTicketDtos = ticketService.getSoldOfShow(showId);
            List<SeatDto> seats = seatService.getAllOfShow(showId);

            for (TicketDto t : availableTicketDtos) {
                ticketMap.put(t.getSeatId(), t);
            }
            for (TicketDto t : soldTicketDtos) {
                ticketMap.put(t.getSeatId(), t);
            }

            roomPlanReferencers = new ArrayList<>();

            for (SeatDto seat : seats) {
                CheckBox checkBox = new CheckBox();
                roomPlan_seatsGrid_gp.add(checkBox, seat.getSequence() - 1, seat.getRow().getSequence() - 1);
                TicketDto ticket;
                String status = "reserved";
                if (ticketMap.containsKey(seat.getId())) {
                    ticket = ticketMap.get(seat.getId());
                    if (availableTicketDtos.contains(ticket)) {
                        status = "available";
                    } else if (soldTicketDtos.contains(ticket)) {
                        status = "sold";
                    }
                } else {
                    ticket = null;
                }

                RoomPlanReferencer ref = new RoomPlanReferencer(checkBox, seat, ticket, status);
                roomPlanReferencers.add(ref);
                refArray[seat.getSequence() - 1][seat.getRow().getSequence() - 1] = ref;
            }

        } catch (ServiceException e) {
            this.alertCreator.exceptionAlert(BundleManager.getExceptionBundle().getString("roomplanreferencer.exception"));
        }
    }

    /**
     * @author Raphael Schotola 1225193
     */
    private void initRoomPlan() {

        roomPlan_selectionStyle_tg = new ToggleGroup();
        roomPlan_manualSelection_rb.setToggleGroup(roomPlan_selectionStyle_tg);
        roomPlan_multiSelection_rb.setToggleGroup(roomPlan_selectionStyle_tg);

        roomPlan_performanceName_tl.setText(BundleManager.getBundle().getString("roomplan.lbl"));

        for (RoomPlanReferencer ref : roomPlanReferencers) {

            CheckBox cb;
            cb = ref.getCheckBox();
            cb.getStylesheets().clear();
            if (ref.getStatus().equals("available")) {
                cb.getStylesheets().add(getClass().getResource("/gui/css/SeatsStyleSheetAvailable.css")
                        .toExternalForm());
            } else if (ref.getStatus().equals("sold")) {
                cb.getStylesheets().add(getClass().getResource("/gui/css/SeatsStyleSheetSold.css").toExternalForm());
                cb.setDisable(true);
            } else if (ref.getStatus().equals("reserved")) {
                cb.getStylesheets().add(getClass().getResource("/gui/css/SeatsStyleSheetReserved.css").toExternalForm
                        ());
                cb.setDisable(true);
            }
        }

        roomPlan_multiSelection_tt.setText(BundleManager.getBundle().getString("multiselection.info"));
        hackTooltipStartTiming(roomPlan_multiSelection_tt);
    }

    public static void hackTooltipStartTiming(Tooltip tooltip) {
        try {
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(tooltip);

            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(150)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Exits the roomPlan window
     *
     * @param event the event
     */
    @FXML
    private void handleExit(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    /**
     * Closes the roomPlan window
     */
    private void closeWindow() {
        ((Stage) roomPlan_cancel_btn.getScene().getWindow()).close();
    }

}
