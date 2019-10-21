import java.util.*;
import java.io.*;
import java.awt.Toolkit;
import java.awt.Dimension;

public class PlanitRunner
{
	//Fields
    
    //The main "database" of activities, collected from user input using gatherInfo()
    public static ArrayList<Activity> database = new ArrayList<Activity>();
    private static MainWindow mainWindow;

    public static void main(String[] args)
    throws UnsupportedEncodingException, FileNotFoundException, IOException
    {
        try {
            boolean dataLoaded = loadData(); //true if data loaded successfully
            
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //gets user's screen size
            int width = screenSize.width; //width of user's screen
            int height = screenSize.height; //height of user's screen
            mainWindow = new MainWindow("Plan-it"); //creates main window for user
            mainWindow.setSize(width, height); //sets size of main window
            mainWindow.setResizable(true); //allows user to resize main window
            mainWindow.setVisible(true); //makes main window visible
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Check for existing "save file", and if found, load in data to create database
    //and return true, else return false
	public static boolean loadData()
    throws UnsupportedEncodingException, FileNotFoundException, IOException
    {
		//load data from existing database on user's computer (if it exists) 
		//to this.database
        try {
            File file = new File("Database.txt");
            if(file.exists()) {
                Scanner scanner = new Scanner(file);
            
                while(scanner.hasNextLine()) {
                    scanner.nextLine();
                
                    Activity temp = new Activity();
                    String name = scanner.nextLine();
                    temp.setName(name);
                
                    double maxTime = Double.parseDouble(scanner.nextLine());
                    temp.setMaxTime(maxTime);
                
                    double idealTime = Double.parseDouble(scanner.nextLine());
                    temp.setIdealTime(idealTime);
                
                    double maxCost = Double.parseDouble(scanner.nextLine());
                    temp.setMaxCost(maxCost);
                
                    database.add(temp);
                }
                return true;
            } else {
                return false;
            }
                } catch (Exception e) {
                        e.printStackTrace();
                }
        return false;
	}
	
    //Overwrite "save file" with current database information
	public static void saveData(ArrayList<Activity> database)
    throws UnsupportedEncodingException, FileNotFoundException, IOException
    {
        try {
            if(database != null) {
                String text = "";
                for(int i = 0; i < database.size(); i++) {
                    Activity temp = database.get(i);
            
                    text += "\n" + temp.getName();
                    text += "\n" + temp.getMaxTime();
                    text += "\n" + temp.getIdealTime();
                    text += "\n" + temp.getMaxCost();
                    text += "\n";
                }
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    ("Database.txt"), false), "utf-8"))) {
                    writer.write(text);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
