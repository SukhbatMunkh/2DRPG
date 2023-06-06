package at.htlleonding.dungeonsandportals.Model;

import at.htlleonding.dungeonsandportals.Controller.MovementController;
import at.htlleonding.dungeonsandportals.Controller.SceneLoader;
import javafx.scene.Scene;

import java.util.List;

public class Entity implements Comparable<Entity>{
    //region <fields>
    private int id;
    private MovementController movementController;
    private double speedTimeRatio;
    //endregion

    //region <Constructors>
    public int getId() {
        return this.id;
    }
    public FrameAnimation getAnimation() {
        return movementController.getAnimation();
    }

    public MovementController getMovementController() {
        return movementController;
    }

    public double getSpeedTimeRatio() {
        return speedTimeRatio;
    }
    //endregion

    public Entity(int id, FrameAnimation animation, double speed, Scene scene) {
        this.id = id;
        movementController = new MovementController(animation, speed);
        adjustSpeedTimeRatio(scene);
    }

    //region <Methods>
    public void adjustSpeedTimeRatio(Scene scene) {
        this.speedTimeRatio = scene.getWidth()/500;
    }

    public void move(List<FrameAnimation> frameAnimations){
        //Todo: movement playerinput or mobinput
        movementController.move(List.of("D"), frameAnimations, this.speedTimeRatio);

        /*
        if (movementController.getxPosition() <= 10) {
            SceneLoader.getInstance().moveToScene(Direction.NORTH);
            movementController.setPlayerPosition(600, movementController.getyPosition());
        }*/
    }

    public int compareTo(Entity entity) {
        return this.getAnimation().compareTo(entity.getAnimation());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return id == entity.id;
    }
    //endregion
}
