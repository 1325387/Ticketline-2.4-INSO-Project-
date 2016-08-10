package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.service.AuthService;
import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.EmployeeService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.ticketline.dto.EmployeeDto;
import at.ac.tuwien.inso.ticketline.dto.UserEvent;
import at.ac.tuwien.inso.ticketline.dto.UserStatusDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller for the login window
 */
@Component
public class LoginController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SpringFxmlLoader springFxmlLoader;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthService authService;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin, btnExit;

    @FXML
    private ComboBox<Locale> cbLanguage;

    @FXML
    private Pane root;

    private NotificationPane notificationPane;

    private final ImageView errorImage = new ImageView(new Image(LoginController.class.getResourceAsStream("/image/icon/warning.png")));

	/**
	 * {@inheritDoc}
	 */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        notificationPane = new NotificationPane(root.getChildren().get(0));
        //LOGGER.debug("root children: " + root.getChildren());
        //LOGGER.debug("root children 0: " + root.getChildren().get(0));
        root.getChildren().clear();
        root.getChildren().add(notificationPane);
        notificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
        cbLanguage.getItems().clear();
        cbLanguage.getItems().addAll(BundleManager.getSupportedLocales());
        cbLanguage.valueProperty().addListener((observable, oldValue, newValue) -> reinitLocale(newValue));
        cbLanguage.getSelectionModel().select(resourceBundle.getLocale());
    }

    /**
     * Reinitializes the UI with the given locale.
     *
     * @param newValue the new value
     */
    private void reinitLocale(Locale newValue) {
        LocaleContextHolder.setLocale(newValue);
        BundleManager.changeLocale(newValue);
        btnExit.setText(BundleManager.getBundle().getString("generic.exit"));
        btnLogin.setText(BundleManager.getBundle().getString("login.login"));
        txtPassword.setPromptText(BundleManager.getBundle().getString("login.password"));
        txtUsername.setPromptText(BundleManager.getBundle().getString("login.username"));
    }

    /**
     * Logs the user in
     *
     * @param event the event
     */
    @FXML
    private void handleLogin(ActionEvent event) {
        UserStatusDto login;
        try {
            login = this.authService.login(txtUsername.getText(), txtPassword.getText());
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            notificationPane.show(BundleManager.getExceptionBundle().getString("error.connection"), errorImage);
            return;
        }
        if (login.getEvent() == UserEvent.AUTH_FAILURE) {
            LOGGER.debug("Auth Failure");
            notificationPane.show(BundleManager.getExceptionBundle().getString("error.loginData"), errorImage);
            return;
        } else if (login.getEvent() == UserEvent.LOCKED) {
            LOGGER.debug("User is locked");
            notificationPane.show(BundleManager.getExceptionBundle().getString("error.userLocked"), errorImage);
            return;
        }
        LOGGER.debug("Begin to initialize");
        ((Node) event.getSource()).setCursor(Cursor.WAIT);
        MainScreenController.setEmployeeName(login.getUsername());
        EmployeeDto employee = null;
        try {
            employee = employeeService.getEmployee(login.getUsername(), login.getFirstName(), login.getLastName());
        } catch (ServiceException e) {
            LOGGER.error(BundleManager.getExceptionBundle().getString("employeeNotFound.exception"));
            e.printStackTrace();
        }
        MainScreenController.setEmployee(employee);

        Stage newsStage = new Stage();
        newsStage.setScene(new Scene((Parent) springFxmlLoader.load("/gui/fxml/mainscreen.fxml")));
        newsStage.setResizable(false);
        newsStage.setTitle(BundleManager.getBundle().getString("app.name"));
        newsStage.getIcons().add(new Image(LoginController.class.getResourceAsStream("/image/ticketlineLogo.png")));
        newsStage.show();
        ((Node) event.getSource()).setCursor(Cursor.DEFAULT);
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Exits the application
     *
     * @param event the event
     */
    @FXML
    private void handleExit(ActionEvent event) {
        LOGGER.debug("Close Window");
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

}
