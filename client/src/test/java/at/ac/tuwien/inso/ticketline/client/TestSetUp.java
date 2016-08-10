package at.ac.tuwien.inso.ticketline.client;

import at.ac.tuwien.inso.ticketline.client.exception.GuiException;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.loadui.testfx.GuiTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Locale;

/**
 * test setup for all gui tests.
 *
 * @author Aysel Oeztuerk, 0927011
 */
@RunWith(JUnit4.class)
public class TestSetUp extends GuiTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestSetUp.class);

    private ClassPathXmlApplicationContext classPathXmlApplicationContext;

    @Override
    protected Parent getRootNode() {
        Parent parent = null;
        LocaleContextHolder.setLocale(Locale.getDefault());
        classPathXmlApplicationContext = new ClassPathXmlApplicationContext("/spring/client-context.xml");
        classPathXmlApplicationContext.start();
        SpringFxmlLoader springFxmlLoader = (SpringFxmlLoader) classPathXmlApplicationContext.getBean("springFxmlLoader");

        parent = (Parent) springFxmlLoader.load("/gui/fxml/login.fxml");

        return parent;
    }

    @Before
    public void loginAndOpenTab() throws GuiException {
        LOGGER.debug("Login called...");
        try{
            find("#txtUsername").setAccessibleText("marvin");
            find("#txtPassword").setAccessibleText("42");
            Button login = find("#btnLogin");
            click(login);
            LOGGER.debug("Logged is successfully...");
        } catch (Exception e) {
            LOGGER.debug("You are logged in!");
        }
    }

}

