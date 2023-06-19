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

    public static Direction getDirectionFromArray(int[] directionArray) {
        if (directionArray.length != 2) {
            return null;
        } else if (directionArray[0] != 0 && directionArray[1] == 0) {
            if (directionArray[0] > 0) {
                return Direction.EAST;
            } else {
                return Direction.WEST;
            }
        } else if (directionArray[0] == 0 && directionArray[1] != 0) {
            if (directionArray[1] > 0) {
                return Direction.NORTH;
            } else {
                return Direction.SOUTH;
            }
        } else {
            return null;
        }
    }
}
