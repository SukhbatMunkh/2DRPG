package at.htlleonding.game.Controller;

import at.htlleonding.game.Model.FrameAnimation;
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
        setPlayerPosition(0, 0);
    }

    //region <Methods>
    /**
     * moves the player via the keyboard input
     * @param keyboardInput
     */
    public void movePlayer(List<String> keyboardInput) {
            for (String input : keyboardInput) {
                switch (input) {
                    case "W":
                        animation.setyLocation(animation.getyLocation() - (speed + extraSpeed));
                        //TODO:check if block or border
                        break;
                    case "A":
                        animation.setxLocation(animation.getxLocation() - (speed + extraSpeed));
                        //TODO:check if block or border
                        break;
                    case "S":
                        animation.setyLocation(animation.getyLocation() + (speed + extraSpeed));
                        //TODO:check if block or border
                        break;
                    case "D":
                        animation.setxLocation(animation.getxLocation() + (speed + extraSpeed));
                        //TODO:check if block or border
                        break;
                }
            }
        }
    //endregion
}
