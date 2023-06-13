package at.htlleonding.dungeonsandportals.Model;

import at.htlleonding.dungeonsandportals.Controller.InputController;
import at.htlleonding.dungeonsandportals.database.DatabaseController;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.util.List;

public class Player extends Entity {
    //region <fields>
    private InputController inputController;
    private Scene scene;
    //endregion

    public Player(int id, double speed, Scene scene) {
        super(id, Player.getPlayerFrameAnimation(scene, Direction.EAST), speed, scene);
        inputController = new InputController(scene);
        this.scene = scene;
    }

    private static FrameAnimation getPlayerFrameAnimation(Scene scene, Direction direction) {
        List<Image> playerImages = DatabaseController.getPlayerImages(direction);
        double ratio = 50 / playerImages.get(0).getHeight();

        return new FrameAnimation(playerImages, 0, 0, playerImages.get(0).getWidth() * ratio, playerImages.get(0).getHeight() * ratio, 300, SceneLevel.PLAYER);
    }

    @Override
    public void move(List<FrameAnimation> frameAnimations) {
        Direction direction = super.getMovementController().move(inputController.getInput(), frameAnimations, super.getSpeedTimeRatio());

        if (direction == Direction.EAST || direction == Direction.WEST) {
            this.getMovementController().getAnimation().setAnimation(getPlayerFrameAnimation(this.scene, direction).getAnimation());
        }
    }
}
