package at.htlleonding.game.Controller;

import at.htlleonding.game.Model.FrameAnimation;
import javafx.scene.Scene;

public class PlayerController {
    //region <fields>
    private InputController inputController;
    private MovementController movementController;
    //endregion

    //region <Constructors>
    public FrameAnimation getAnimation() {
        return movementController.getAnimation();
    }

    public MovementController getMovementController() {
        return movementController;
    }
    //endregion

    public PlayerController(Scene scene, FrameAnimation animation) {
        inputController = new InputController(scene);
        movementController = new MovementController(animation, 5);

        inputController.getMouseInputProperty().addListener((observableValue, s, t1) ->{

        });
    }

    public void movePlayer() {
        movementController.movePlayer(inputController.getInput());
    }
}
