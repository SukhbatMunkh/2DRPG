package at.htlleonding.game.Model;

import at.htlleonding.game.Controller.MovementController;
import javafx.scene.Scene;

import java.util.List;

public class Entity implements Comparable<Entity>{
    //region <fields>
    private MovementController movementController;
    private double speedTimeRatio;
    //endregion

    //region <Constructors>
    public FrameAnimation getAnimation() {
        return movementController.getAnimation();
    }

    public MovementController getMovementController() {
        return movementController;
    }
    //endregion

    public Entity(FrameAnimation animation, double speed, Scene scene) {
        movementController = new MovementController(animation, speed);
        adjustSpeedTimeRatio(scene);
    }

    public void adjustSpeedTimeRatio(Scene scene) {
        this.speedTimeRatio = scene.getWidth()/500;
    }

    public void move(List<FrameAnimation> frameAnimations){
        //Todo: movement playerinput or mobinput
        movementController.move(List.of("D"), frameAnimations, this.speedTimeRatio);
    }

    public int compareTo(Entity entity) {
        return this.getAnimation().compareTo(entity.getAnimation());
    }
}
