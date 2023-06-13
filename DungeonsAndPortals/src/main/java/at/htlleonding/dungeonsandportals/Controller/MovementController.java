package at.htlleonding.dungeonsandportals.Controller;

import at.htlleonding.dungeonsandportals.Model.Direction;
import at.htlleonding.dungeonsandportals.Model.FrameAnimation;
import at.htlleonding.dungeonsandportals.Model.SceneLevel;
import javafx.geometry.Rectangle2D;

import java.util.List;

public class MovementController {
    //region <fields>
    private FrameAnimation animation;
    private double speed;
    private double extraSpeed;
    //endregion

    //region <Constructors>
    public FrameAnimation getAnimation() {
        return this.animation;
    }

    public double getSpeed() {
        return speed;
    }

    public double getExtraSpeed() {
        return extraSpeed;
    }

    public void setExtraSpeed(int extraSpeed) {
        this.extraSpeed = extraSpeed;
    }

    public double getxPosition() {
        return animation.getxLocation();
    }

    public double getyPosition() {
        return animation.getyLocation();
    }

    public void setPlayerPosition(double x, double y) {
        animation.setxLocation(x);
        animation.setyLocation(y);
    }
    //endregion

    public MovementController(FrameAnimation animation, double speed) {
        extraSpeed = 0;
        this.speed = speed;
        this.animation = animation;
    }

    //region <Methods>

    /**
     * Moves the player via the keyboard input while also looking out for collisions.
     * The movement is also connected to the speed-time-ratio (ratio on with the screen size)
     * @param keyboardInput
     * @param frameAnimations
     * @param speedTimeRatio
     */
    public Direction move(List<String> keyboardInput, List<FrameAnimation> frameAnimations, double speedTimeRatio) {
        Direction direction = Direction.NORTH;

        // don't move if it's not supposed to
        if (SceneLevel.getLevelAsNumber(this.animation.getSceneLevel()) > 1) {
            for (String input : keyboardInput) {
                double[] movement = convertToMovement(input);

                if (movement[0] > 0) {
                    direction = Direction.EAST;
                } else if (movement[0] < 0){
                    direction = Direction.WEST;
                }

                if (movement.length == 2) {
                    double xMovement = (movement[0] * (speed + extraSpeed) * speedTimeRatio);
                    double yMovement = (movement[1] * (speed + extraSpeed) * speedTimeRatio);

                    animation.setxLocation(animation.getxLocation() + xMovement);
                    animation.setyLocation(animation.getyLocation() + yMovement);

                    boolean intersectsWithSomething = false;

                    for (FrameAnimation frameAnimation : frameAnimations) {
                        if (this.intersects(frameAnimation) && SceneLevel.getLevelAsNumber(frameAnimation.getSceneLevel()) == 1) {
                            intersectsWithSomething = true;
                        }
                    }

                    if (intersectsWithSomething) {
                        animation.setxLocation(animation.getxLocation() - xMovement);
                        animation.setyLocation(animation.getyLocation() - yMovement);
                    }
                }
            }
        }

        return direction;
    }

    public double[] convertToMovement(String input) {
        double[] movement = new double[2];

        switch (input) {
            case "W":
                movement[1] = -1;
                break;
            case "A":
                movement[0] = -1;
                break;
            case "S":
                movement[1] = 1;
                break;
            case "D":
                movement[0] = 1;
                break;
        }
        return movement;
    }

    public boolean intersects(FrameAnimation animation) {
        return animation.getBoundary().intersects( this.getAnimation().getBoundary() );
    }

    public boolean intersects(Rectangle2D gameObjectBoundary) {
        return gameObjectBoundary.intersects( this.getAnimation().getBoundary() );
    }
    //endregion
}
