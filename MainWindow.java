import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import javax.swing.*;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.*;

public class MainWindow extends JFrame implements WindowListener, ItemListener
{
    
    //copy of the database ArrayList, REPLACE WITH MySQL FUNCTIONALITY
    private ArrayList<Activity> databaseCopy;
    
    //Dropdown boxes for user input
    private JComboBox c1, c2, c3, c4;
    
    //Labels to display maxTime, idealTime, maxCost, and availableMoney user input
    private JLabel max1, idealt1, maxc1, money;

    public MainWindow(String title) {
        super(title); //Add title to window
        
        //Set background image of window
        try {
            final Image backgroundImage = javax.imageio.ImageIO.read(new File("space.jpg"));
            setContentPane(new JPanel(new BorderLayout()) {
                @Override public void paintComponent(Graphics g) {
                    Dimension d = getSize();
                    g.drawImage(backgroundImage, 0, 0, d.width, d.height, null);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        addWindowListener(this);
        JLabel text = new JLabel("Welcome to Plan-it!");
        add(text);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("Rockwell", Font.ITALIC + Font.BOLD, 35));
        text.setMinimumSize(new Dimension(400, 150));
        text.setPreferredSize(new Dimension(400, 150));
        text.setMaximumSize(new Dimension(400, 150));
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setVerticalAlignment(SwingConstants.CENTER);

        JLabel picture = new JLabel(new ImageIcon("Planit.png"));
        picture.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(picture);

        add(Box.createVerticalGlue());

        //Get data for new activity
        JLabel newActivity = new JLabel("Enter a new Activity:");
        newActivity.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(newActivity);
        newActivity.setForeground(Color.WHITE);
        newActivity.setFont(new Font("Helvetica", Font.BOLD, 20));


        JPanel inputName = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(150, 25));
        JLabel activityField = new JLabel("Activity name:");
        JLabel activityName = new JLabel("Activity is...");
        inputName.add(activityField);
        inputName.add(textField);
        inputName.add(activityName);
        activityField.setForeground(Color.WHITE);
        activityField.setFont(new Font("Helvetica", Font.PLAIN, 16));
        activityName.setForeground(Color.WHITE);
        activityName.setFont(new Font("Helvetica", Font.PLAIN, 16));
        add(inputName);
        inputName.setOpaque(false);

        //time options (in hours)
        String s1[] = { "0.5", "1", "1.5", "2", "2.5", "3", "3.5", "4", "4.5", "5", "5.5", "6" }; 
        String[] n1 = new String[100];
        for (int i = 0, j = 0; i < 100; ++i, j+=5) {
            n1[i] = Integer.toString(j);
        }
        // create checkbox 
        c1 = new JComboBox(s1); 
        c2 = new JComboBox(s1);
        c3 = new JComboBox(n1);
        
        // add ItemListener 
        c1.addItemListener(this);
        c2.addItemListener(this);
        c3.addItemListener(this);
        
        // create labels 
        JPanel inputMax = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel max = new JLabel("Max time for activity:"); 
        max1 = new JLabel("half hour selected"); 
        inputMax.add(max);
        inputMax.add(c1);
        inputMax.add(max1);
        max.setForeground(Color.WHITE);
        max.setFont(new Font("Helvetica", Font.PLAIN, 16));
        max1.setForeground(Color.WHITE);
        max1.setFont(new Font("Helvetica", Font.PLAIN, 16));
        add(inputMax);
        inputMax.setOpaque(false);

        JPanel inputIdeal = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel idealt = new JLabel("Ideal time for activity:"); 
        idealt1 = new JLabel("half hour selected");
        inputIdeal.add(idealt);
        inputIdeal.add(c2);
        inputIdeal.add(idealt1);
        idealt.setForeground(Color.WHITE);
        idealt.setFont(new Font("Helvetica", Font.PLAIN, 16));
        idealt1.setForeground(Color.WHITE);
        idealt1.setFont(new Font("Helvetica", Font.PLAIN, 16));
        add(inputIdeal);
        inputIdeal.setOpaque(false);

        JPanel inputCost = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel maxc = new JLabel("Max cost for activity:"); 
        maxc1 = new JLabel("0 selected");
        inputCost.add(maxc);
        inputCost.add(c3);
        inputCost.add(maxc1);
        maxc.setForeground(Color.WHITE);
        maxc.setFont(new Font("Helvetica", Font.PLAIN, 16));
        maxc1.setForeground(Color.WHITE);
        maxc1.setFont(new Font("Helvetica", Font.PLAIN, 16));
        add(inputCost);
        inputCost.setOpaque(false);

        JButton saveb = new JButton("Save Current Activity");
        saveb.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                activityName.setText(textField.getText());
            }
        });
        saveb.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(saveb);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        Date dateStart = new Date();
        SpinnerDateModel sdmStart = new SpinnerDateModel(dateStart, null, null, Calendar. HOUR_OF_DAY);
        JSpinner spinnerStart = new JSpinner(sdmStart);
        JSpinner.DateEditor des = new JSpinner.DateEditor(spinnerStart, "HH:mm");
        spinnerStart.setEditor(des);
        JLabel labelStart = new JLabel("Please select start time: ");
        inputPanel.add(labelStart);
        labelStart.setForeground(Color.WHITE);
        labelStart.setFont(new Font("Helvetica", Font.PLAIN, 16));
        inputPanel.add(spinnerStart);
        spinnerStart.setMinimumSize(new Dimension(100, 30));
        spinnerStart.setPreferredSize(new Dimension(100, 30));
        spinnerStart.setMaximumSize(new Dimension(100, 30));
        //setSize(100,100);
        //setVisible(true);
        // JLabel label3 = new JLabel("                                  "+
        // "                                                             ");
        // add(label3);
        Date dateFinish = new Date();
        SpinnerDateModel sdmFinish = new SpinnerDateModel(dateFinish, null, null, Calendar. HOUR_OF_DAY);
        JSpinner spinnerFinish = new JSpinner(sdmFinish);
        JSpinner.DateEditor def = new JSpinner.DateEditor(spinnerFinish, "HH:mm");
        spinnerFinish.setEditor(def);
        JLabel labelFinish = new JLabel("Please select end time: ");
        inputPanel.add(labelFinish);
        labelFinish.setForeground(Color.WHITE);
        labelFinish.setFont(new Font("Helvetica", Font.PLAIN, 16));
        inputPanel.add(spinnerFinish);
        spinnerFinish.setMinimumSize(new Dimension(100, 30));
        spinnerFinish.setPreferredSize(new Dimension(100, 30));
        spinnerFinish.setMaximumSize(new Dimension(100, 30));

        c4 = new JComboBox(n1); 
        c4.addItemListener(this);
        JLabel userMoney = new JLabel("Please select budget:"); 
        money = new JLabel("0 selected"); 
        inputPanel.add(userMoney);
        inputPanel.add(c4);
        inputPanel.add(money);
        userMoney.setForeground(Color.WHITE);
        userMoney.setFont(new Font("Helvetica", Font.PLAIN, 16));
        money.setForeground(Color.WHITE);
        money.setFont(new Font("Helvetica", Font.PLAIN, 16));
 
       add(inputPanel);
       inputPanel.setOpaque(false);
        add(Box.createVerticalGlue());

        JPanel viewButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton b = new JButton("Generate Activity Plan");
        b.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new activityPlan();
            }
        });
        viewButtons.add(b);

        JButton vb = new JButton("View Current Activities");
        vb.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new currentActivities();
            }
        });
        viewButtons.add(vb);
        
        add(viewButtons);
        
        viewButtons.setOpaque(false);
            add(Box.createVerticalGlue());

        JLabel label3 = new JLabel("Developed by Team Rocket");
        add(label3);
        label3.setAlignmentX(Component.CENTER_ALIGNMENT);
        label3.setFont(new Font("Helvetica", Font.PLAIN, 14));
        add(Box.createVerticalGlue());
    }
    
    public void updateDatabase(ArrayList<Activity> database) {
        this.databaseCopy = database; //REPLACE WITH CALL TO MySQL
    }

    public void itemStateChanged(ItemEvent e) 
    { 
        // if the state combobox is changed 
        if (e.getSource() == c1) {
            String selection = "" + c1.getSelectedItem();
            double selectionDouble = Double.parseDouble(selection);
            String hours = "";
            if(selectionDouble == 1.0) {
                hours = "hour";
            } else if(selectionDouble == 0.5) {
                selection = "";
                hours = "half hour";
            } else {
                hours = "hours";
            }
            max1.setText(selection + " " + hours + " selected");
            //NEED --> VARIABLE TO DRAW FROM WHEN addActivity() IS CALLED
        }
        else if (e.getSource() == c2) {
            String selection = "" + c2.getSelectedItem();
            double selectionDouble = Double.parseDouble(selection);
            String hours = "";
            if(selectionDouble == 1.0) {
                hours = "hour";
            } else if(selectionDouble == 0.5) {
                selection = "";
                hours = "half hour";
            } else {
                hours = "hours";
            }
            idealt1.setText(selection + " " + hours + " selected");
            //NEED --> VARIABLE TO DRAW FROM WHEN addActivity() IS CALLED
        }
        else if (e.getSource() == c3) {
            maxc1.setText(c3.getSelectedItem() + " dollars selected");
            //NEED --> VARIABLE TO DRAW FROM WHEN addActivity() IS CALLED
        }
        else if (e.getSource() == c4) {
            money.setText(c4.getSelectedItem() + " dollars selected");
            //NEED --> VARIABLE TO DRAW FROM WHEN addActivity() IS CALLED
        }
    }

    /**
     * Handles the closing of the Main window, re-setting settings to
     * their defaults
     *
     * @param e The WindowEvent triggered by closing the Main window
     */
    public void windowClosing(WindowEvent e)
    {
        try {
            PlanitRunner.saveData(databaseCopy); //Update the database file with current database info
            dispose(); //Close window
            System.exit(0); //Exit program
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void windowOpened(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
}
