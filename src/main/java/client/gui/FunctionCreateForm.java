package client.gui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.WinApiFunction;
import model.WinApiParameter;
import org.reactfx.EventSource;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static javafx.collections.FXCollections.observableArrayList;

public class FunctionCreateForm extends VBox {

    final EventSource<WinApiFunction> function;
    private final WinApiHandbookReactor reactor;
    ObservableList<Node> paramsForms;
    TextField name;
    TextArea description;
    long id;
    private Runnable o;

    FunctionCreateForm(WinApiHandbookReactor reactor) {
        this.reactor = reactor;
        function = new EventSource<>();
        paramsForms = observableArrayList();
        createNameBlock();
        createDescriptionBlock();
        createFunctionsParams();
        id = -1l;
        createIdBlock();
    }

    private void createNameBlock() {
        Label label = new Label("Name");
        name = new TextField();
        function
                .map(WinApiFunction::getName)
                .feedTo(name.textProperty());


        Button remove = new Button("Remove");
        remove.setOnAction(e -> o.run());
        HBox hBox = new HBox(label, name, remove);
        hBox.setSpacing(10);
        getChildren().add(hBox);
    }

    private void createDescriptionBlock() {
        Label label = new Label("Description");
        description = new TextArea();
        description.setWrapText(true);
        function
                .map(WinApiFunction::getDescription)
                .feedTo(description.textProperty());

        VBox vBox = new VBox(label, description);
        getChildren().add(vBox);
    }

    private void createFunctionsParams() {
        VBox vBox = new VBox();
        Button addParam = new Button("addParam");
        addParam.setOnAction(this::addNewParam);

        paramsForms = vBox.getChildren();
        VBox vBox1 = new VBox(addParam, vBox);

        function
                .map(WinApiFunction::getParams)
                .map(f -> f.stream().map(ParamsForm::new).collect(toList()))
                .hook(list -> {
                    for (int i = 0; i < list.size(); i++) {
                        int num = i;
                        list.get(i).removeAction(() -> removeParam(num));
                    }
                })
                .subscribe(f -> vBox.getChildren().setAll(f));
        getChildren().add(vBox1);
    }

    private void createIdBlock() {
        function
                .map(WinApiFunction::getId)
                .subscribe(l -> id = l);
    }

    void removeParam(int number) {
        WinApiFunction function = getFunction();
        WinApiParameter winApiParameter = function.getParams().get(number);
        reactor.removeParameter(winApiParameter);
    }

    public WinApiFunction getFunction() {
        List<WinApiParameter> collect = getParams();
        return new WinApiFunction(id, name.getText(), description.getText(), collect);
    }

    public void pushFunction(WinApiFunction function) {
        this.function.push(function);
    }

    List<WinApiParameter> getParams() {
        return paramsForms.stream()
                .map(ParamsForm.class::cast)
                .map(ParamsForm::getParam)
                .collect(toList());
    }

    void addNewParam(ActionEvent actionEvent) {
        WinApiFunction function = getFunction();
        function.getParams().add(new WinApiParameter(0l, "", ""));
        pushFunction(function);
    }

    List<ParamsForm> getParamsForm() {
        return paramsForms
                .stream()
                .map(ParamsForm.class::cast)
                .collect(toList());
    }

    public void removeAction(Runnable o) {
        this.o = o;
    }
}
