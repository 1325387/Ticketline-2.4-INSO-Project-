package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.CustomerService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.client.util.Countries;
import at.ac.tuwien.inso.ticketline.dto.AddressDto;
import at.ac.tuwien.inso.ticketline.dto.CustomerDto;

import at.ac.tuwien.inso.ticketline.dto.Gender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller for creating and editing a customer.
 *
 * @author Aysel Oeztuerk 0927011
 */
@Component
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private Alerts show = new Alerts();

    @Autowired
    private CustomerService customerService;

    @FXML
    private TextField saveCustomer_id_tf;
    @FXML
    private TextField saveCustomer_firstname_tf;
    @FXML
    private TextField saveCustomer_lastname_tf;
    @FXML
    private TextField saveCustomer_address_tf;
    @FXML
    private DatePicker saveCustomer_dateOfBirth_dp;
    @FXML
    private TextField saveCustomer_title_tf;
    @FXML
    private TextField saveCustomer_city_tf;
    @FXML
    private TextField saveCustomer_phonenumber_tf;
    @FXML
    private ComboBox saveCustomer_gender_cb;
    @FXML
    private TextField saveCustomer_postalcode_tf;
    @FXML
    private ComboBox<Countries> saveCustomer_country_cb;
    @FXML
    private TextField saveCustomer_email_tf;

    @FXML
    private Label save_title_lbl;
    @FXML
    private Label save_firstname_lbl;
    @FXML
    private Label save_lastname_lbl;
    @FXML
    private Label save_city_lbl;
    @FXML
    private Label save_street_lbl;
    @FXML
    private Label save_phone_lbl;
    @FXML
    private Label save_gender_lbl;
    @FXML
    private Label save_plz_lbl;
    @FXML
    private Label save_country_lbl;
    @FXML
    private Label save_mail_lbl;
    @FXML
    private Label save_dob_lbl;

    @FXML
    private Button saveCustomer_save_btn;
    @FXML
    private Button saveCustomer_cancel_btn;


    private CustomerDto updateCustomer;

    @FXML
    public void initialize(CustomerDto customer) {
        this.updateCustomer = customer;
        save_title_lbl.setText(BundleManager.getBundle().getString("title.lbl"));
        save_firstname_lbl.setText(BundleManager.getBundle().getString("firstname.tc") +" *");
        save_lastname_lbl.setText(BundleManager.getBundle().getString("lastname.tc") +" *");
        save_street_lbl.setText(BundleManager.getBundle().getString("address.tc"));
        save_dob_lbl.setText(BundleManager.getBundle().getString("dob.tc") +" *");
        save_city_lbl.setText(BundleManager.getBundle().getString("town.lbl"));
        save_phone_lbl.setText(BundleManager.getBundle().getString("phone.lbl") +" *");
        save_gender_lbl.setText(BundleManager.getBundle().getString("gender.lbl") +" *");
        save_plz_lbl.setText(BundleManager.getBundle().getString("plz.lbl"));
        save_country_lbl.setText(BundleManager.getBundle().getString("country.lbl"));
        save_mail_lbl.setText(BundleManager.getBundle().getString("mail.lbl"));
        saveCustomer_save_btn.setText(BundleManager.getBundle().getString("save.btn"));
        saveCustomer_cancel_btn.setText(BundleManager.getBundle().getString("cancel.btn"));


        saveCustomer_gender_cb.getItems().clear();
        saveCustomer_gender_cb.getItems().addAll(EnumSet.allOf(Gender.class));
        saveCustomer_country_cb.getItems().clear();
        saveCustomer_country_cb.getItems().addAll(EnumSet.allOf(Countries.class));

        if (updateCustomer != null) {
            saveCustomer_id_tf.clear();
            saveCustomer_id_tf.setText(String.valueOf(updateCustomer.getId()));
            saveCustomer_firstname_tf.clear();
            saveCustomer_firstname_tf.setText(updateCustomer.getFirstname());
            saveCustomer_lastname_tf.clear();
            saveCustomer_lastname_tf.setText(updateCustomer.getLastname());
            saveCustomer_address_tf.clear();
            saveCustomer_address_tf.setText(updateCustomer.getAddress().getStreet());
            // DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
            //String date = df.format(updateCustomer.getDateOfBirth());

            LocalDate date = updateCustomer.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            saveCustomer_dateOfBirth_dp.getEditor().clear();
            saveCustomer_dateOfBirth_dp.setValue(date);
            saveCustomer_title_tf.clear();
            saveCustomer_title_tf.setText(updateCustomer.getTitle());
            saveCustomer_city_tf.clear();
            saveCustomer_city_tf.setText(updateCustomer.getAddress().getCity());
            saveCustomer_phonenumber_tf.clear();
            saveCustomer_phonenumber_tf.setText(updateCustomer.getPhoneNumber());

            saveCustomer_gender_cb.getSelectionModel().select(updateCustomer.getGender());
            saveCustomer_postalcode_tf.clear();
            saveCustomer_postalcode_tf.setText(updateCustomer.getAddress().getPostalCode());

            //LOGGER.debug("Country: " + updateCustomer.getAddress().getCountry().length());
            if (!(updateCustomer.getAddress().getCountry() == null) && updateCustomer.getAddress().getCountry().length()!=0) {
                saveCustomer_country_cb.getSelectionModel().select(Countries.valueOf(updateCustomer.getAddress().getCountry()));
            }
            saveCustomer_email_tf.clear();
            saveCustomer_email_tf.setText(updateCustomer.getEmail());
            LOGGER.debug("The parameters has been set successfully.");
        } else {
            saveCustomer_dateOfBirth_dp.setValue(LocalDate.now().minusYears(30));
            //saveCustomer_country_cb.getSelectionModel().select(0);
        }

        //isInputValidListener();
    }

    @FXML
    public void saveCustomer_save_btn_clicked(ActionEvent event) {
        // Instant instant = Instant.from(saveCustomer_dateOfBirth_dp.getValue().atStartOfDay(ZoneId.systemDefault()));
        //   Date date = Date.from(instant);

        if (isInputValid()) {
            if (updateCustomer != null) {

                updateCustomer.setId(Integer.parseInt(saveCustomer_id_tf.getText()));
                updateCustomer.setTitle(saveCustomer_title_tf.getText());
                updateCustomer.setFirstname(saveCustomer_firstname_tf.getText());
                updateCustomer.setLastname(saveCustomer_lastname_tf.getText());

                updateCustomer.setDateOfBirth(java.sql.Date.valueOf(saveCustomer_dateOfBirth_dp.getValue()));
                updateCustomer.setTitle(saveCustomer_title_tf.getText());
                updateCustomer.getAddress().setStreet(saveCustomer_address_tf.getText());
                updateCustomer.getAddress().setCity(saveCustomer_city_tf.getText());
                updateCustomer.getAddress().setPostalCode(saveCustomer_postalcode_tf.getText());
                if (!(saveCustomer_country_cb.getSelectionModel().getSelectedItem() == null)) {
                    updateCustomer.getAddress().setCountry(saveCustomer_country_cb.getSelectionModel().getSelectedItem().toString());
                }
                updateCustomer.setPhoneNumber(saveCustomer_phonenumber_tf.getText());
                updateCustomer.setEmail(saveCustomer_email_tf.getText());
                updateCustomer.setGender(saveCustomer_gender_cb.getSelectionModel().getSelectedItem().toString());
                try {

                    customerService.updateCustomer(updateCustomer);
                    closeWindow(event);
                    show.infoAlert(BundleManager.getMessageBundle().getString("update.success"));

                } catch (ServiceException e) {
                    LOGGER.error("Error while updating a customer: " + e.getMessage() + "\nMaybe name is too long?");
                    show.exceptionAlert(BundleManager.getExceptionBundle().getString("update.error"));
                }
            } else {
                CustomerDto newCustomer = new CustomerDto();
                AddressDto address = new AddressDto();

                newCustomer.setTitle(saveCustomer_title_tf.getText());
                newCustomer.setFirstname(saveCustomer_firstname_tf.getText());
                newCustomer.setLastname(saveCustomer_lastname_tf.getText());
                address.setStreet(saveCustomer_address_tf.getText());
                newCustomer.setDateOfBirth(java.sql.Date.valueOf(saveCustomer_dateOfBirth_dp.getValue()));
                newCustomer.setTitle(saveCustomer_title_tf.getText());
                address.setCity(saveCustomer_city_tf.getText());
                address.setPostalCode(saveCustomer_postalcode_tf.getText());
                if (!(saveCustomer_country_cb.getSelectionModel().getSelectedItem() == null)) {
                    address.setCountry(saveCustomer_country_cb.getSelectionModel().getSelectedItem().toString());
                }
                newCustomer.setAddress(address);
                newCustomer.setPhoneNumber(saveCustomer_phonenumber_tf.getText());
                newCustomer.setEmail(saveCustomer_email_tf.getText());
                newCustomer.setGender(saveCustomer_gender_cb.getSelectionModel().getSelectedItem().toString());
                try {
                    CustomerDto createdNewCustomer = this.customerService.createNewCustomer(newCustomer);
                    closeWindow(event);
                    show.infoAlert(BundleManager.getBundle().getString("customer.lbl") + " " + createdNewCustomer.getFirstname() + " " + createdNewCustomer.getLastname()
                            + " " + BundleManager.getMessageBundle().getString("create.success"));
                    LOGGER.info(BundleManager.getBundle().getString("customer.lbl") + " " + createdNewCustomer.getFirstname() + " " + createdNewCustomer.getLastname()
                            + " " + BundleManager.getMessageBundle().getString("create.success"));
                } catch (ServiceException e) {
                    LOGGER.error("Error while creating a new customer: {}", e.getMessage());
                    show.exceptionAlert(BundleManager.getExceptionBundle().getString("create.error"));
                }
            }
        }
    }

    @FXML
    public void saveCustomer_cancel_btn_clicked(ActionEvent event) {
        closeWindow(event);
    }

    private void isInputValidListener() {
        saveCustomer_title_tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")) {
                show.inputErrorAlert(BundleManager.getExceptionBundle().getString("lettersinput.error")+"\n");
            }
        });
        saveCustomer_firstname_tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")) {
                show.inputErrorAlert(BundleManager.getExceptionBundle().getString("lettersinput.error")+"\n");
            }
        });
        saveCustomer_lastname_tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")) {
                show.inputErrorAlert(BundleManager.getExceptionBundle().getString("lettersinput.error")+"\n");
            }
        });
        saveCustomer_address_tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9-\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF-]*")) {
                show.inputErrorAlert(BundleManager.getExceptionBundle().getString("lettersnumbersinput.error")+"\n");
            }
        });
        saveCustomer_city_tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF-]*")) {
                show.inputErrorAlert(BundleManager.getExceptionBundle().getString("lettersinput.error")+"\n");
            }
        });
        saveCustomer_phonenumber_tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]+")) {
                show.inputErrorAlert(BundleManager.getExceptionBundle().getString("numbersinput.error")+"\n");
            }
        });
        saveCustomer_postalcode_tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]+")) {
                show.inputErrorAlert(BundleManager.getExceptionBundle().getString("numbersinput.error") + "\n");
            }
        });
    }

    private boolean isInputValid() {
        String errorMessage = "";
        Boolean dateError = false;

        if (!(saveCustomer_title_tf.getText() == null)) {
            if (saveCustomer_title_tf.getText().length() != 0 && !saveCustomer_title_tf.getText().matches("[a-zA-Zß\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")) {
                errorMessage += BundleManager.getMessageBundle().getString("title.warning") + "\n";
            }
        }

        if (saveCustomer_firstname_tf.getText() == null || saveCustomer_firstname_tf.getText().length() == 0 ||
                saveCustomer_firstname_tf.getText().trim().length() <= 0 ||! saveCustomer_firstname_tf.getText().matches("[a-zA-Zß\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")) {
            errorMessage += BundleManager.getMessageBundle().getString("firstname.warning") + "\n";
        }
        if (saveCustomer_lastname_tf.getText() == null || saveCustomer_lastname_tf.getText().length() == 0 ||
                saveCustomer_lastname_tf.getText().trim().length() <= 0 || !saveCustomer_lastname_tf.getText().matches("[a-zA-Zß\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")) {
            errorMessage += BundleManager.getMessageBundle().getString("lastname.warning") + "\n";
        }
        if (!(saveCustomer_address_tf.getText() == null)){
            if(saveCustomer_address_tf.getText().length() != 0
                    && !saveCustomer_address_tf.getText().matches("[a-zA-Zß\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]+[a-zA-Zß0-9-\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")){
                errorMessage += BundleManager.getMessageBundle().getString("street.warning") + "\n";
            }
        }
        if (!(saveCustomer_city_tf.getText() == null)){
            if(saveCustomer_city_tf.getText().length() != 0 && !saveCustomer_city_tf.getText().matches("[ßa-zA-Z-\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")) {
                errorMessage += BundleManager.getMessageBundle().getString("city.warning") + "\n";
            }
        }
        if (saveCustomer_dateOfBirth_dp.getValue() == null || saveCustomer_dateOfBirth_dp.getValue().equals("")) {
            errorMessage += BundleManager.getMessageBundle().getString("dob.warning") + "\n";
        } else {
            try {
                java.sql.Date date = java.sql.Date.valueOf(saveCustomer_dateOfBirth_dp.getValue());
            } catch (Exception e) {
                errorMessage += BundleManager.getExceptionBundle().getString("dobinput.error") + "\n";
                LOGGER.debug("Please enter your date of birth in the correct format (year-month-day)!");
                dateError = true;
            }

            // Alter sollte >=14 Jahre sein:
            LocalDate currentDate = LocalDate.now(); //
            LocalDate ageLimitOver14 = currentDate.minusYears(14);
            LocalDate ageLimitUnder110 = currentDate.minusYears(110);
            LOGGER.debug("Age limit over 14: " + ageLimitOver14);
            LOGGER.debug("Age limit under 110: " + ageLimitUnder110);

            try {
                //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dateOfBirth = saveCustomer_dateOfBirth_dp.getValue();
                LOGGER.debug("dateOfBirth: " + dateOfBirth);

                LOGGER.debug("dateOfBirth.isAfter: " + dateOfBirth.isAfter(ageLimitOver14));
                if (dateOfBirth.isAfter(ageLimitOver14)) {
                    errorMessage += BundleManager.getMessageBundle().getString("14older.warning") + "\n";
                    LOGGER.debug("The customer must be 14 or older.");
                }

                if (dateOfBirth.isBefore(ageLimitUnder110)) {
                    errorMessage += BundleManager.getMessageBundle().getString("110younger.warning") + "\n";
                    LOGGER.debug("The customer must be 110 or younger.");
                }
            } catch (Exception e) {
                if (dateError == true) {
                    errorMessage += BundleManager.getExceptionBundle().getString("ageinput.error") + "\n";
                    LOGGER.debug("The customer must be >=14 years and <= 110 years old.");
                } else {
                    errorMessage += BundleManager.getExceptionBundle().getString("dobinput.error") + "\n " +
                            BundleManager.getExceptionBundle().getString("ageinput.error") + "\n";
                    LOGGER.debug("Please enter your date of birth in the correct format (year-month-day)! " +
                            "The customer must be >=14 years and <= 110 years old.");
                }
            }
        }

        if (saveCustomer_phonenumber_tf.getText() == null || saveCustomer_phonenumber_tf.getText().length() == 0 ||
                saveCustomer_phonenumber_tf.getText().trim().length() <= 0 || !saveCustomer_phonenumber_tf.getText().matches("[0-9]+")) {
            errorMessage += BundleManager.getMessageBundle().getString("phone.warning")+"\n";
        }

        if (!(saveCustomer_postalcode_tf.getText() == null)) {
            if((saveCustomer_postalcode_tf.getText().length() != 0 || saveCustomer_postalcode_tf.getText().trim().length() > 0 ) &&
                !saveCustomer_postalcode_tf.getText().matches("[0-9]+")) {
                errorMessage += BundleManager.getMessageBundle().getString("plz.warning") + "\n";
            }
        }

        LOGGER.debug("Email: " + saveCustomer_email_tf.getText());

        Matcher matcher = null;
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        if (!(saveCustomer_email_tf.getText() == null)) {
            matcher = pattern.matcher(saveCustomer_email_tf.getText());
            if (!(saveCustomer_email_tf.getText().length() == 0) && !matcher.find()) {
                errorMessage += BundleManager.getMessageBundle().getString("email.warning")+"\n";
                LOGGER.debug("Please enter a valid e-mail address. \n");
            }
        }


        if (saveCustomer_gender_cb.getSelectionModel().getSelectedItem() == null) {
            errorMessage += BundleManager.getMessageBundle().getString("gender.warning")+"\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            show.inputErrorAlert(errorMessage);
            return false;
        }
    }


    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
