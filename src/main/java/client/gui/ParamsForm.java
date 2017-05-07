package client.gui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.WinApiParameter;
import org.reactfx.EventSource;

class ParamsForm extends HBox {
    final EventSource<WinApiParameter> parameter;
    TextField param;
    long id;
    private Runnable o;

    ParamsForm(WinApiParameter parameter) {

        this.parameter = new EventSource<>();
        this.parameter
                .map(WinApiParameter::getId)
                .subscribe(e -> id = e);
        crateForm();
        pushParameter(parameter);
    }

    private void crateForm() {
        param = new TextField();
        parameter
                .map(WinApiParameter::getName)
                .feedTo(param.textProperty());
        Button remove = new Button("Remove");
        remove.setOnAction(e -> o.run());
        setSpacing(10);
        getChildren().addAll(param, remove);
    }

    void pushParameter(WinApiParameter parameter) {
        this.parameter.push(parameter);
    }

    WinApiParameter getParam() {
        return new WinApiParameter(id, "", param.getText());
    }

    public void removeAction(Runnable o) {
        this.o = o;
    }
}
