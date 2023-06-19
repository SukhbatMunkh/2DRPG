package at.htlleonding.dungeonsandportals.Model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import java.util.List;

public class FrameAnimation implements Comparable<FrameAnimation>
{
    //region <fields>
    private List<Image> animation;
    private double xLocation;
    private double yLocation;
    private double xSize;
    private double ySize;
    private SceneLevel sceneLevel;
    private double duration;
    //endregion

    //region <Getter and Setter>
    public List<Image> getAnimation() {
        return animation;
    }

    public void setAnimation(List<Image> animation) {
        this.animation = animation;
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

    public double getxSize() {
        return xSize;
    }

    public void setxSize(double xSize) {
        this.xSize = xSize;
    }

    public double getySize() {
        return ySize;
    }

    public void setySize(double ySize) {
        this.ySize = ySize;
    }

    public double getDuration() {
        return duration;
    }

    public SceneLevel getSceneLevel() {
        return sceneLevel;
    }

    public void setSceneLevel(SceneLevel sceneLevel) {
        this.sceneLevel = sceneLevel;
    }
    //endregion

    public FrameAnimation(List<Image> animation, double xLocation, double yLocation, double xSize, double ySize, double duration, SceneLevel sceneLevel){
        this.animation = animation;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.xSize = xSize;
        this.ySize = ySize;
        this.duration = duration;
        this.sceneLevel = sceneLevel;
    }

    //region <Methods>
    public Image getByTime(final long nanotime) {
        final long time = nanotime/1000000;
        return animation.get((int) ((time % (animation.size() * duration)) / duration));
    }

    @Override
    public int compareTo(FrameAnimation o) {
        int thisSceneNumber = SceneLevel.getLevelAsNumber(sceneLevel);
        int otherSceneNumber = SceneLevel.getLevelAsNumber(o.sceneLevel);

        System.out.println(thisSceneNumber);
        System.out.println(otherSceneNumber);
        System.out.println(Integer.compare(thisSceneNumber, otherSceneNumber));
        System.out.println("-------------------");

        return Integer.compare(thisSceneNumber, otherSceneNumber);
    }

    public Rectangle2D getBoundary()
    {
        //TODO: adjust boundaries new fields

        if (this.sceneLevel == SceneLevel.BACKGROUND) {
            return new Rectangle2D(xLocation, yLocation, 0, 0);
        } else {
            return new Rectangle2D(xLocation, yLocation, xSize, ySize);
        }
    }

    //endregion
}
