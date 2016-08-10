package at.ac.tuwien.inso.ticketline.client.gui.controller;


import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import javafx.scene.control.Alert;

/**
 * Different alert-types.
 * @author Aysel Oeztuerk 0927011.
 */
public class Alerts {
    public void warningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(BundleManager.getBundle().getString("warning.title"));
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.show();
    }

    public void exceptionAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(BundleManager.getBundle().getString("error.title"));
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.show();
    }

    public void inputErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(BundleManager.getBundle().getString("invalidinput.title"));
        alert.setHeaderText(BundleManager.getMessageBundle().getString("correctinput.txt"));
        alert.setContentText(message);

        alert.show();
    }

    public void infoAlert(String message) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Information");
        info.setHeaderText(null);
        info.setContentText(message);

        info.show();
    }

}
