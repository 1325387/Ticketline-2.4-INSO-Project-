package at.ac.tuwien.inso.ticketline.client.extraObjects;

import at.ac.tuwien.inso.ticketline.dto.PerformanceDto;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import org.loadui.testfx.GuiTest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Raphael Schotola 1225193
 *
 * Has a method to return a specific row from a specific tableView to use in in
 * click(...) in TestFX
 */
@Component
public class TableRowFinder extends GuiTest {

    //returns Row 'row' from TableView with ID 'tableId' (zB "#event_performanceTable_tv")
    public static TableRow<?> row(String tableId, int row) {

        TableView<PerformanceDto> table = find(tableId);

        List<Node> current = table.getChildrenUnmodifiable();
        while (current.size() == 1) {
            current = ((Parent) current.get(0)).getChildrenUnmodifiable();
        }

        current = ((Parent) current.get(1)).getChildrenUnmodifiable();
        while (!(current.get(0) instanceof TableRow)) {
            current = ((Parent) current.get(0)).getChildrenUnmodifiable();
        }

        Node node = current.get(row);
        if (node instanceof TableRow) {
            return (TableRow<?>) node;
        } else {
            throw new RuntimeException("Expected Group with only TableRows as children");
        }
    }

    @Override
    protected Parent getRootNode() {
        return null;
    }
}
