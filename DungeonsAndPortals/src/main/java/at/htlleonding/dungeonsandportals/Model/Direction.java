package at.htlleonding.dungeonsandportals.Model;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static int[] getDirectionAsArray(Direction direction) {
        int[] result = new int[2];

        switch (direction) {
            case NORTH -> result[1] = 1;
            case SOUTH -> result[1] = -1;
            case EAST -> result[0] = 1;
            case WEST -> result[0] = -1;
        }

        return result;
    }
}
