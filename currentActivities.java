import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.*;

public class currentActivities {
    JFrame f;
    JTable j;
    ArrayList<Activity> database;

    currentActivities()
    {
        f = new JFrame();
        f.setTitle("Current Activities");
	    this.database = PlanitRunner.database;

        // Test Data to be displayed in the JTable
        int rowSize = database.size();
        int columnSize = 3;
        
        String[][] data = new String[rowSize][columnSize];
        
        //Fill table with corresponding data from PlanitRunner.database
        //REPLACE WITH DATA FROM MYSQL DATABASE
        for(int row = 0; row < rowSize; row++) {
	    for(int column = 1; column <= columnSize; column++) {
                Activity temp = database.get(row);
                if(column == 1) {
		    data[row][column - 1] = "" + temp.getName();
		} else if(column == 2) {
            
            //convert raw time in minutes to hours, minutes, etc.
            String timeString = "";
            double rawTime = temp.getIdealTime();
            int hours = (int) (rawTime/60);
            int minutes = (int) (rawTime % 60);
            if(hours > 0) {
                if(hours == 1) {
                    timeString += "" + hours + " hour";
                } else {
                    timeString += "" + hours + " hours";
                }
                if(minutes != 0) {
                    timeString += ", ";
                }
            }
            if(minutes > 0) {
                timeString += "" + minutes + " minutes";
            }
            
            data[row][column - 1] = timeString;
		} else if(column == 3) {
            double cost = temp.getMaxCost();
            if(cost == 0) {
                data[row][column - 1] = "Free!";
            } else {
                data[row][column - 1] = "$" + cost;
            }
		} else {
            System.out.println("Invalid Column Number");
		}
	    }
	}


        String[] columnNames = { "Activity", "Ideal Time", "Max Cost" };

        j = new JTable(data, columnNames);
        j.setBounds(30, 40, 200, 300);

        JScrollPane sp = new JScrollPane(j);
        f.add(sp);
        f.setSize(500, 200);
        f.setVisible(true);
    }
}
