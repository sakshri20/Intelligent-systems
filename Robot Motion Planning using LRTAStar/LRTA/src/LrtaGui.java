import javax.swing.*;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * @author Vaijyant Tomar
 */

public class LrtaGui extends JFrame {

    static ArrayList<LocationTile> obstacleLocationList = new ArrayList<>();
    static ArrayList<LocationTile> tiles = new ArrayList<>();
    static LocationTile START;
    static LocationTile GOAL;

    public static void main(String[] args) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LrtaGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LrtaGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LrtaGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LrtaGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        //Setting up the GUI Components
        LrtaGui LrtaGui = new LrtaGui();

        LrtaGui.setSize(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        LrtaGui.setLocationRelativeTo(null);
        LrtaGui.setVisible(true);
        LrtaGui.setResizable(false);


        JOptionPane.showMessageDialog(null, "Please select input csv file to render obstacles.\n" +
                "\nPress OK to continue...");
        drawObstacles();
        setStartAndGoal();
    }


    /**
     * Constructor to Initialize GUI components
     */
    JPanel envFloorPanel;
    static JTextArea messageArea;
    JScrollPane jMessageScrollPane;

    public LrtaGui() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Robot Motion Planning using LRTA*");

        JOptionPane.showMessageDialog(null, "Welcome to Robot Motion Planning using LRTA*.\n" +
                "\nPress OK to continue...");

        //setting up containers
        envFloorPanel = new JPanel();
        envFloorPanel.setMaximumSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));
        envFloorPanel.setMinimumSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));
        envFloorPanel.setPreferredSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));

        messageArea = new JTextArea();
        messageArea.setLineWrap(true);
        jMessageScrollPane = new JScrollPane(messageArea);
        jMessageScrollPane.setMaximumSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT - Config.SCREEN_WIDTH));
        jMessageScrollPane.setMinimumSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT - Config.SCREEN_WIDTH));
        jMessageScrollPane.setPreferredSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT - Config.SCREEN_WIDTH));


        envFloorPanel.setBackground(Color.lightGray);
        getContentPane().add(envFloorPanel, BorderLayout.CENTER);
        getContentPane().add(jMessageScrollPane, BorderLayout.SOUTH);


        //drawing the floor
        drawFloor();


        //adding listeners


    }

    private void drawFloor() {

        if (Config.SIZE * Config.SIZE != envFloorPanel.getComponentCount()) {
            envFloorPanel.setLayout(new GridLayout(Config.SIZE, Config.SIZE, 1, 1));

            envFloorPanel.removeAll();

            for (int i = 0; i < Config.SIZE; i++) {
                for (int j = 0; j < Config.SIZE; j++) {
                    int[] location = {i, j}; //Row, Column
                    LocationTile locationTile = new LocationTile(location);
                    obstacleLocationList.add(locationTile);

                    locationTile.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (locationTile.hasObstacle) {
                                JOptionPane.showMessageDialog(null, "Cannot add a point on an obstacle.\n" +
                                        "Select a valid area.\n" +
                                        "\nPress OK to continue...");
                                return;
                            }
                            switch (Config.CLICK_COUNT) {
                                case 0:
                                    locationTile.setBackground(Color.GREEN);
                                    START = locationTile;
                                    Config.CLICK_COUNT++;

                                    String messageStart = "You selected row " + START.getTileLocation()[0] + " and column " + START.getTileLocation()[1] + " as Start state.";
                                    displayMessage(messageStart);
                                    break;
                                case 1:
                                    locationTile.setBackground(Color.RED);
                                    Config.CLICK_COUNT++;
                                    GOAL = locationTile;
                                    String messageGoal = "You selected row " + GOAL.getTileLocation()[0] + " and column " + GOAL.getTileLocation()[1] + " as Goal state.";
                                    displayMessage(messageGoal);

                                    startLRTAStarAlgorithm();
                                    break;
                                default:
                            }
                        }
                    });
                    tiles.add(locationTile);
                    envFloorPanel.add(locationTile);
                }
            }
            envFloorPanel.revalidate();
            envFloorPanel.repaint();
        }
    }

    private static void drawObstacles() {
        ArrayList<int[]> obstacleList = Config.getObstacleList();

        for (int[] obstacle : obstacleList) {
            LocationTile locationTile = obstacleLocationList.get(obstacle[0] * Config.SIZE + obstacle[1]);
            locationTile.setHasObstacle(true);
        }
    }

    private static void setStartAndGoal() {
        JOptionPane.showMessageDialog(null, "Click on the grid to mark start and goal positions.\n" +
                "\nPress OK to continue...");

    }

    void startLRTAStarAlgorithm() {
        displayMessage("Starting algorithm...");

        new Thread(new Runnable() {
            public void run() {
                // code goes here.
                LRTAStarAlgorithm lrtaStarAlgorithm = new LRTAStarAlgorithm(START, GOAL, obstacleLocationList);
            }
        }).start();

    }

    static void displayMessage(String message) {
        messageArea.setText((messageArea.getText() + "\n\n"
                + message).trim());
    }
}
