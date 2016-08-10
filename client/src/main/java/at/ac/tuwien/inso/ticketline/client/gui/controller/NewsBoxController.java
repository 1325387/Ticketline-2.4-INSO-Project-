package at.ac.tuwien.inso.ticketline.client.gui.controller;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.gui.JavaFXUtils;
import at.ac.tuwien.inso.ticketline.client.service.AuthService;
import at.ac.tuwien.inso.ticketline.client.service.NewsService;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.ticketline.dto.NewsDto;

/**
 * Controller for the news window
 */
@Component
public class NewsBoxController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsBoxController.class);

    @Autowired
    private SpringFxmlLoader springFxmlLoader;

    @Autowired
    private NewsService newsService;

    @Autowired
    private AuthService authService;

    @FXML
    private VBox vbNewsBox;

	/**
	 * {@inheritDoc}
	 */
    @Override
    public void initialize(URL url, ResourceBundle resBundle) {
        if (null != vbNewsBox) {
            initNewsBox();
        }
    }

    /**
     * Inits the news box.
     */
    private void initNewsBox() {
        List<NewsDto> news;
        try {
            news = this.newsService.getNews();
        } catch (ServiceException e) {
            LOGGER.error("Could not retrieve news: {}", e.getMessage());
            Alert alert = JavaFXUtils.createAlert(e);
            alert.showAndWait();
            return;
        }
        for (Iterator<NewsDto> newsDtoIterator = news.iterator(); newsDtoIterator.hasNext(); ) {
            NewsDto newsDto = newsDtoIterator.next();
            ObservableList<Node> vbNewsBoxChildren = vbNewsBox.getChildren();
            vbNewsBoxChildren.add(generateNewsElement(newsDto));
            if(newsDtoIterator.hasNext()) {
                Separator separator = new Separator();
                vbNewsBoxChildren.add(separator);
            }
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
        ((NewsElementController)loadWrapper.getController()).initializeData(newsDto);
        return (Node) loadWrapper.getLoadedObject();
    }

    /**
     * Exits the application
     *
     * @param event the event
     */
    @FXML
    private void handleExit(ActionEvent event) {
        try {
            this.authService.logout();
        } catch (ServiceException e) {
            LOGGER.error("Logout failed: " + e.getMessage(), e);
        }
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }


}
