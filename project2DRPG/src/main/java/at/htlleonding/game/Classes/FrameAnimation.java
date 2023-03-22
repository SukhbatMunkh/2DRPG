package at.htlleonding.game.Classes;

import javafx.scene.image.Image;

import java.util.List;

public class FrameAnimation
{
    private List<Image> animation;
    private double xLocation;
    private double yLocation;
    private double duration;

    public FrameAnimation(List<Image> animation, double xLocation, double yLocation, double duration){
        this.animation = animation;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.duration = duration;
    }

    public Image getByTime(final long nanotime) {
        final long time = nanotime/1000000;
        return animation.get((int) ((time % (animation.size() * duration)) / duration));
    }

    public List<Image> getAnimation() {
        return animation;
    }

    public double getxLocation() {
        return xLocation;
    }

    public void setxLocation(double xLocation) {
        this.xLocation = xLocation;
    }

    public double getyLocation() {
        return yLocation;
    }

    public void setyLocation(double yLocation) {
        this.yLocation = yLocation;
    }

    public double getDuration() {
        return duration;
    }
}
