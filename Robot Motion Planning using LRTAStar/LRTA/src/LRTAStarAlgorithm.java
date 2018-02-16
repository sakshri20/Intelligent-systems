import java.awt.*;
import java.util.ArrayList;
/**
 * @author Vaijyant Tomar
 * @author Sakshi Shrivastava
 **/

public class LRTAStarAlgorithm {
    public enum Action {
        TOP, TOP_RIGHT, RIGHT, BOTTOM_RIGHT,
        BOTTOM, BOTTOM_LEFT, LEFT, TOP_LEFT,
        NONE;
    }

    double[] result = {Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY};


    static LocationTile start;
    static LocationTile goal;
    static ArrayList<LocationTile> obstacleLocationList;
    static LocationTile currentTile;
    static LocationTile nextTile;

    public LRTAStarAlgorithm(LocationTile start, LocationTile goal, ArrayList<LocationTile> obstacleLocationList) {
        this.start = start;
        this.goal = goal;
        this.obstacleLocationList = obstacleLocationList;

        currentTile = start;
        while (true) {
            if (algorithm(currentTile)) {
                LrtaGui.displayMessage("Path found!");
                LrtaGui.tiles.get(currentTile.getIndex()).setBackground(Color.RED);
                break;
            }
        }
    }

    boolean algorithm(LocationTile s_dash) {

        if ((s_dash.getTileLocation()[0] == goal.getTileLocation()[0]) && (s_dash.getTileLocation()[1] == goal.getTileLocation()[1])) {
            return true;
        }
        //getTotalCost(s_dash);
        Action a = getMinAction();
        currentTile = nextTile;
        resetResult();

        // GUI work
        LrtaGui.tiles.get(nextTile.getIndex()).setBackground(Color.pink);
        LrtaGui.displayMessage("At tile " + currentTile.getTileLocation()[0] + ", " + currentTile.getTileLocation()[1]);

        return false;
    }

    Action getMinAction() {
        Action[] a = getValidActions(currentTile);
        int index = -1;

        double min = Double.POSITIVE_INFINITY;

        for (int i = 0; i < 8; i++) {
            if (min >= result[i]) {
                index = i;
                min = result[i];
            }
        }

        int[] currentLocation = currentTile.getTileLocation(); // needs to be updated

        switch (index) {
            case 0:
                currentLocation[0] = currentLocation[0] - 1;
                nextTile = new LocationTile(currentLocation);
                return Action.TOP;
            case 1:
                currentLocation[0] = currentLocation[0] - 1;
                currentLocation[1] = currentLocation[1] + 1;
                nextTile = new LocationTile(currentLocation);
                return Action.TOP_RIGHT;

            case 2:
                currentLocation[1] = currentLocation[1] + 1;
                nextTile = new LocationTile(currentLocation);
                return Action.RIGHT;

            case 3:
                currentLocation[0] = currentLocation[0] + 1;
                currentLocation[1] = currentLocation[1] + 1;
                nextTile = new LocationTile(currentLocation);
                return Action.BOTTOM_RIGHT;

            case 4:
                currentLocation[0] = currentLocation[0] + 1;
                nextTile = new LocationTile(currentLocation);
                return Action.BOTTOM;

            case 5:
                currentLocation[0] = currentLocation[0] + 1;
                currentLocation[1] = currentLocation[1] - 1;
                nextTile = new LocationTile(currentLocation);
                return Action.BOTTOM_LEFT;

            case 6:
                currentLocation[1] = currentLocation[1] - 1;
                nextTile = new LocationTile(currentLocation);
                return Action.LEFT;

            case 7:
                currentLocation[0] = currentLocation[0] - 1;
                currentLocation[1] = currentLocation[1] - 1;
                nextTile = new LocationTile(currentLocation);
                return Action.TOP_LEFT;

            default:
                return Action.NONE;
        }
    }


    Action[] getValidActions(LocationTile current) {

        Action[] validAction = new Action[8];
        int index = 0;

        int[] location = current.getTileLocation();
        int row = location[0];
        int col = location[1];

        int stepCost = LrtaGui.tiles.get(current.getIndex()).getStepCost();

        int nextRow;
        int nextColumn;

        if (row - 1 >= 0) {
            nextRow = row - 1;
            nextColumn = col;
            if (obstacleLocationList.get(nextRow * Config.SIZE + nextColumn).hasObstacle) {
                result[0] = Double.POSITIVE_INFINITY;

            } else {
                validAction[index] = Action.TOP;
                int[] position = {nextRow, nextColumn};
                LocationTile nextTile = LrtaGui.tiles.get(nextRow * Config.SIZE + nextColumn);
                int nextTileStepCost = nextTile.getStepCost();
                result[0] = nextTileStepCost;
                result[0] = result[0] + calculateHeuristic(position);

                index++;
            }
        }
        if (row - 1 >= 0 && col + 1 <= 99) {
            nextRow = row - 1;
            nextColumn = col + 1;
            if (obstacleLocationList.get(nextRow * Config.SIZE + nextColumn).hasObstacle) {
                result[1] = Double.POSITIVE_INFINITY;

            } else {
                validAction[index] = Action.TOP_RIGHT;
                int[] position = {nextRow, nextColumn};
                LocationTile nextTile = LrtaGui.tiles.get(nextRow * Config.SIZE + nextColumn);
                int nextTileStepCost = nextTile.getStepCost();
                result[1] = nextTileStepCost;
                result[1] = result[1] + calculateHeuristic(position);
                index++;
            }
        }
        if (col + 1 <= 99) {
            nextRow = row;
            nextColumn = col + 1;
            if (obstacleLocationList.get(nextRow * Config.SIZE + nextColumn).hasObstacle) {
                result[2] = Double.POSITIVE_INFINITY;

            } else {
                validAction[index] = Action.RIGHT;
                int[] position = {nextRow, nextColumn};
                LocationTile nextTile = LrtaGui.tiles.get(nextRow * Config.SIZE + nextColumn);
                int nextTileStepCost = nextTile.getStepCost();
                result[2] = nextTileStepCost;
                result[2] = result[2] + calculateHeuristic(position);
                index++;
            }
        }
        if (row + 1 <= 99 && col + 1 <= 99) {
            nextRow = row + 1;
            nextColumn = col + 1;
            if (obstacleLocationList.get(nextRow * Config.SIZE + nextColumn).hasObstacle) {
                result[3] = Double.POSITIVE_INFINITY;

            } else {
                validAction[index] = Action.BOTTOM_RIGHT;
                int[] position = {nextRow, nextColumn};
                LocationTile nextTile = LrtaGui.tiles.get(nextRow * Config.SIZE + nextColumn);
                int nextTileStepCost = nextTile.getStepCost();
                result[3] = nextTileStepCost;
                result[3] = result[3] + calculateHeuristic(position);
                index++;
            }
        }
        if (row + 1 <= 99) {
            nextRow = row + 1;
            nextColumn = col;
            if (obstacleLocationList.get(nextRow * Config.SIZE + nextColumn).hasObstacle) {
                result[4] = Double.POSITIVE_INFINITY;

            } else {
                validAction[index] = Action.BOTTOM;
                int[] position = {nextRow, nextColumn};
                LocationTile nextTile = LrtaGui.tiles.get(nextRow * Config.SIZE + nextColumn);
                int nextTileStepCost = nextTile.getStepCost();
                result[4] = nextTileStepCost;
                result[4] = result[4] + calculateHeuristic(position);
                index++;
            }
        }
        if (row + 1 <= 99 && col - 1 >= 0) {
            nextRow = row + 1;
            nextColumn = col - 1;
            if (obstacleLocationList.get(nextRow * Config.SIZE + nextColumn).hasObstacle) {
                result[5] = Double.POSITIVE_INFINITY;

            } else {
                validAction[index] = Action.BOTTOM_LEFT;
                int[] position = {nextRow, nextColumn};
                LocationTile nextTile = LrtaGui.tiles.get(nextRow * Config.SIZE + nextColumn);
                int nextTileStepCost = nextTile.getStepCost();
                result[5] = nextTileStepCost;
                result[5] = result[5] + calculateHeuristic(position);
                index++;
            }
        }
        if (col - 1 >= 0) {
            nextRow = row;
            nextColumn = col - 1;
            if (obstacleLocationList.get(nextRow * Config.SIZE + nextColumn).hasObstacle) {
                result[6] = Double.POSITIVE_INFINITY;

            } else {
                validAction[index] = Action.LEFT;
                int[] position = {nextRow, nextColumn};
                LocationTile nextTile = LrtaGui.tiles.get(nextRow * Config.SIZE + nextColumn);
                int nextTileStepCost = nextTile.getStepCost();
                result[6] = nextTileStepCost;
                result[6] = result[6] + calculateHeuristic(position);
                index++;
            }
        }
        if (row - 1 >= 0 && col - 1 >= 0) {
            nextRow = row - 1;
            nextColumn = col - 1;
            if (obstacleLocationList.get(nextRow * Config.SIZE + nextColumn).hasObstacle) {
                result[7] = Double.POSITIVE_INFINITY;

            } else {
                validAction[index] = Action.TOP_LEFT;
                int[] position = {nextRow, nextColumn};
                LocationTile nextTile = LrtaGui.tiles.get(nextRow * Config.SIZE + nextColumn);
                int nextTileStepCost = nextTile.getStepCost();
                result[7] = nextTileStepCost;
                result[7] = result[7] + calculateHeuristic(position);
                index++;
            }
        }
        return validAction;
    }


    private void resetResult() {
        for (int i = 0; i < 8; i++) {
            result[i] = Double.POSITIVE_INFINITY;
        }
    }

    public static int calculateHeuristic(int[] next) {
        int manhattanDistance = 0;
        int[] goalLocation = goal.getTileLocation();
        manhattanDistance = Math.abs(next[0] - goalLocation[0]) + Math.abs(next[1] - goalLocation[1]);
        return manhattanDistance;
    }
}

