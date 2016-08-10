package at.ac.tuwien.inso.ticketline.client;

import at.ac.tuwien.inso.ticketline.client.exception.GuiException;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

/**
 * Testet das Login von einem gültigen Benutzer und überprüft, ob nach der Anmeldung die Nachrichten angezeigt werden.
 *
 * @author Aysel Oeztuerk, 0927011
 */
public class LoginGuiSeparateTest extends TestSetUp {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginGuiSeparateTest.class);

    @Test
    public void testLogin() throws GuiException {
        LOGGER.debug("Login test called...");

        /*TextField firstname = find("#txtUsername");
        PasswordField password = find("#txtPassword");
        firstname.setText("marvin");
        password.setText("42");
         Button login =  find("#btnLogin");
        click(login);
        LOGGER.debug("Logged in...");
        */
        //logging in is in TestSetup
        assertTrue(find("#news_newsBox_vb").isVisible());
    }
}

