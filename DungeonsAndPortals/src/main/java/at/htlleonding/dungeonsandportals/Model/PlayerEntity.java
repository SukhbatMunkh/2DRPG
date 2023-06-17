package at.htlleonding.dungeonsandportals.Model;

import at.htlleonding.dungeonsandportals.Controller.InputController;
import at.htlleonding.dungeonsandportals.database.DatabaseController;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.util.List;

public class PlayerEntity extends Entity {
    //region <fields>
    private InputController inputController;
    private Scene scene;
    //endregion

    public PlayerEntity(int id, double speed, Scene scene) {
        super(id, PlayerEntity.getPlayerFrameAnimation(scene, Direction.EAST), speed, scene);
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
        List<String> input = inputController.getInput();

        if (input.size() != 0) {
            Direction direction = super.getMovementController().move(input, frameAnimations, super.getSpeedTimeRatio());

            if (direction == Direction.EAST || direction == Direction.WEST) {
                this.getMovementController().getAnimation().setAnimation(getPlayerFrameAnimation(this.scene, direction).getAnimation());
            }
        }
    }
}
