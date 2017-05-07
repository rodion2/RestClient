package client.gui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.WinApiClass;

import java.util.ArrayList;

public class MainWindow extends VBox {

    private final WinApiHandbookReactor reactor;
    private final ClassCreateForm classCreateForm;
    private final FindClassesWidget findClassesWidget;
    TextField search;

    public MainWindow(WinApiHandbookReactor reactor, ClassCreateForm classCreateForm, FindClassesWidget findClassesWidget) {

        this.reactor = reactor;
        this.classCreateForm = classCreateForm;
        this.findClassesWidget = findClassesWidget;
        reactor.listEventSource.subscribe(e -> changeToBrowseState(new ActionEvent()));
        reactor.refreshEventSource.subscribe(this::find);
        reactor.classEventSource.subscribe(e -> changeToEditorState(new ActionEvent()));
        classCreateForm.managedProperty().bind(classCreateForm.visibleProperty());
        findClassesWidget.managedProperty().bind(findClassesWidget.visibleProperty());
        reactor.getEditEventSource().subscribe(this::changeToEditorState);
        reactor.getFindEventSource().subscribe(this::changeToBrowseState);
        createSearchLine();
        reactor.getUpdateEventSource().subscribe(this::find);
        getChildren().addAll(classCreateForm, findClassesWidget);
        changeToEditorState(new ActionEvent());
//        reactor.pushClass(new WinApiClass(1, "class", "description", "example",
//                asList(new WinApiFunction(1, "function", "description", "example",
//                        asList(new WinApiParameter(1, "type", "parameter"))))));
    }

    void changeToBrowseState(Event e) {
        classCreateForm.setVisible(false);
        findClassesWidget.setVisible(true);
    }

    private void createSearchLine() {
        search = new TextField();
        search.setPromptText("write class name");
        Button button = new Button("search");
        button.setOnAction(this::find);

        Button create = new Button("Create");
        create.setOnAction(this::createClass);
        HBox hBox = new HBox(create, search, button);
        getChildren().add(hBox);
    }

    void changeToEditorState(Event e) {
        classCreateForm.setVisible(true);
        findClassesWidget.setVisible(false);
    }

    private void createClass(ActionEvent actionEvent) {
        classCreateForm.submit(new ActionEvent());
        reactor.pushClass(new WinApiClass(0, "", "", new ArrayList<>()));
    }

    void find(ActionEvent actionEvent) {
        reactor.search(search.getText());
    }
}
