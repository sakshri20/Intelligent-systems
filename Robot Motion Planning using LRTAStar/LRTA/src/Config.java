import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Vaijyant Tomar
 *
 */
public class Config {
    static int SCREEN_WIDTH = 700;
    static int SCREEN_HEIGHT = 750;
    static int SIZE = 100; // Number of rows and columns
    static int CLICK_COUNT = 0; // to mark start and end

    static String getSelectedFilePath() {
        String selectedFilePath = "";
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "CSV files", "csv");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            selectedFilePath = chooser.getSelectedFile().getAbsolutePath();
        } else {
            JOptionPane.showMessageDialog(null, "No file selected.\n" +
                    "Unable to render environment.\n" +
                    "\nPress OK to exit.");
            System.exit(0);
        }

        return selectedFilePath;
    }

    static ArrayList<int[]> getObstacleList() {
        ArrayList<int[]> obstacleList = new ArrayList<>();
        String csvFile = getSelectedFilePath();
        BufferedReader br = null;

        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                String[] stPosition = line.split(cvsSplitBy);
                int[] position = {Integer.parseInt(stPosition[0]), Integer.parseInt(stPosition[1])};
                obstacleList.add(position);
        }
        return obstacleList;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return obstacleList;
    }
}
