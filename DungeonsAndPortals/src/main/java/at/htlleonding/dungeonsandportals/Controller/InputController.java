package at.htlleonding.dungeonsandportals.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class InputController {
    //region <fields>
    private Scene scene;
    private List<String> input;
    private SimpleStringProperty mouseInput;

    public List<String> getInput() {
        return input;
    }

    public String getMouseInput() {
        return mouseInput.get();
    }
    //endregion

    public InputController(Scene scene) {
        this.scene = scene;
        this.scene.getRoot().requestFocus();
        this.input = new ArrayList<>();
        this.mouseInput = new SimpleStringProperty();

        this.scene.getRoot().setOnMouseClicked(mouseEvent -> mouseInput.set(mouseEvent.getEventType().toString()));

        this.scene.getRoot().setOnKeyPressed(keyEvent -> {
            String code = keyEvent.getCode().toString();

            if (!input.contains(code)) {
                input.add(code);
            }
        });

        this.scene.getRoot().setOnKeyReleased(keyEvent -> {
            String code = keyEvent.getCode().toString();

            if (input.size() > 0) {
                input.remove(code);
            }
        });
    }

    //region <Property-Getter>
    public SimpleStringProperty getMouseInputProperty() {
        return mouseInput;
    }
    //endregion
}
