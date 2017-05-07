package client.gui;

import javafx.event.ActionEvent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import model.WinApiClass;
import org.reactfx.EventSource;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;
import static org.reactfx.EventStreams.eventsOf;


public class FindClassesWidget extends VBox {

    final EventSource<List<WinApiClass>> classes;
    final WinApiHandbookReactor reactor;
    ListView<WinApiClass> classesView;

    public FindClassesWidget(WinApiHandbookReactor reactor) {
        this.reactor = reactor;
        classes = new EventSource<>();
        this.reactor
                .getListEventSource()
                .subscribe(this::pushClasses);
        createClassList();
    }

    private void createClassList() {
        classesView = new ListView<>();
        classesView.setCellFactory(Cell::new);

        classes
                .feedTo(classesView.getItems()::setAll);

        getChildren().add(classesView);
        eventsOf(classesView, MOUSE_CLICKED)
                .filter(e -> 2 == e.getClickCount())
                .map(e -> classesView.getSelectionModel().getSelectedItems())
                .filter(e -> 1 >= e.size())
                .map(e -> e.get(0))
                .subscribe(this::chooseClass);
    }

    void pushClasses(List<WinApiClass> classes) {
        this.classes.push(classes);
    }

    void chooseClass(WinApiClass s) {
        reactor.getEditEventSource().push(new ActionEvent());
        reactor.pushClass(s);
    }

    List<WinApiClass> getClasses() {
        return new ArrayList<>(classesView.getItems());
    }

    private static class Cell extends ListCell<WinApiClass> {

        Cell(ListView<WinApiClass> winApiClassListView) {
            super();
        }

        @Override
        protected void updateItem(WinApiClass item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) setText(item.getName());
            else setText("");
        }
    }
}
