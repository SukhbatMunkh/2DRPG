package at.htlleonding.dungeonsandportals.Model;

public enum SceneLevel{
    BACKGROUND,
    BACKGROUND_OBJECTS,
    MOBS,
    EFFECTS,
    PLAYER;

    public static int getLevelAsNumber(SceneLevel level) {
        switch (level) {
            case BACKGROUND:
                return 0;
            case BACKGROUND_OBJECTS:
                return 1;
            case MOBS:
                return 2;
            case EFFECTS:
                return 3;
            case PLAYER:
                return 4;
            default:
                return -1;
        }
    }

    public static int compareTo(SceneLevel sceneLevel1, SceneLevel sceneLevel2) {
        return Integer.compare(SceneLevel.getLevelAsNumber(sceneLevel1), SceneLevel.getLevelAsNumber(sceneLevel2));
    }
}
