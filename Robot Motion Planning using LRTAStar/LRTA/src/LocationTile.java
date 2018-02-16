import javax.swing.*;
import java.awt.*;

/**
 * @author Vaijyant Tomar
 * @author Sakshi Shrivastava
 */
public class LocationTile extends JLabel {
    private int[] location;
    boolean hasObstacle;
    int stepCost;


    public LocationTile(int[] location) {
        this.location = location;
        this.hasObstacle = false;
        this.stepCost = 0;

        //GUI stuff
        Dimension dimension = new Dimension(50, 50);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setOpaque(true);
        setBackground(Color.WHITE);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    int[] getTileLocation() {
        return location;
    }

    void setHasObstacle(boolean hasObstacle) {
        this.hasObstacle = hasObstacle;
        this.setBackground(Color.BLACK);
    }

    int getIndex() {
        return location[0] * Config.SIZE + location[1];
    }

    int getStepCost() {
        stepCost = stepCost + 1;
        return stepCost;
    }

    boolean hasObstacle() {
        return hasObstacle;
    }
}
