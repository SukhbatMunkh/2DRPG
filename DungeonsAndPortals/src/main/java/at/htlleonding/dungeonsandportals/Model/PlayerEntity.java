package at.htlleonding.dungeonsandportals.Model;

import at.htlleonding.dungeonsandportals.App;
import at.htlleonding.dungeonsandportals.Controller.InputController;
import at.htlleonding.dungeonsandportals.Controller.SceneLoader;
import at.htlleonding.dungeonsandportals.database.DatabaseController;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class PlayerEntity extends Entity {
    //region <fields>
    private Player player;
    private InputController inputController;
    private Scene scene;

    private List<Rectangle2D> paths;
    //endregion

    //region <Getter and setter>
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addPath(Direction direction) {
        double heightScene = 450; //TODO: changes with screen size
        double widthScene = 600;
        double aObj = 10;
        double bObj = 40;

        switch (direction) {
            case NORTH -> {
                //Rectangle2D(double minX, double minY, double width, double height)
                paths.add(new Rectangle2D((widthScene/2)-(bObj/2), 0, bObj, aObj));
            }
            case SOUTH -> {
                paths.add(new Rectangle2D((widthScene/2)-(bObj/2), heightScene-aObj, bObj, aObj));
            }
            case WEST -> {
                paths.add(new Rectangle2D(0, (heightScene/2)-(bObj/2), aObj, bObj));
            }
            case EAST -> {
                paths.add(new Rectangle2D(widthScene-aObj, (heightScene/2)-(bObj/2), aObj, bObj));
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    public void deleteAllPaths() {
        paths = new ArrayList<>();
    }
    //endregion

    public PlayerEntity(int id, double speed, Scene scene) {
        super(id, PlayerEntity.getPlayerFrameAnimation(scene, Direction.EAST), speed, scene);
        inputController = new InputController(scene);
        this.scene = scene;
        paths = new ArrayList<>();
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

            for (int i = 0; i < paths.size(); i++) {
                if (this.getMovementController().intersects(paths.get(i))) {
                    Direction playerDirection;

                    if (paths.get(i).getMinX() == 0) {
                        playerDirection = Direction.WEST;
                    } else if (paths.get(i).getMinY() == 0) {
                        playerDirection = Direction.NORTH;
                    } else if (paths.get(i).getMinX() < (App.getStage().getWidth()/2)) {
                        playerDirection = Direction.SOUTH;
                    } else {
                        playerDirection = Direction.EAST;
                    }

                    SceneLoader.moveToScene(playerDirection);
                }
            }
        }
    }
}
