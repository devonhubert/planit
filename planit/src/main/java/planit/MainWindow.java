package planit;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import javax.swing.*;
import javax.imageio.*;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.*;
import java.sql.*;
/**
* MainWindow is the main screen of the plan it
* application.
*/
public class MainWindow extends JFrame implements WindowListener, ItemListener
{   
    //User availablity information
    public double availableTime;
    public double availableMoney;
    //public boolean actWindowIsOpen;
    public JButton windowLinker;

    //Dropdown boxes for user input
    private JComboBox timeSelector, moneySelector;
    
    //Labels to display maxTime, idealTime, maxCost, and availableMoney user input
    private JLabel time, money;

    public MainWindow(String title, int screenHeight) {
        super(title); //Add title to window
        
        availableTime = 30.0;
        availableMoney = 0.0;

        //Set Look and Feel
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CurrentActivities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CurrentActivities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CurrentActivities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CurrentActivities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        //Set background image of window
        try {
            final Image backgroundImage = ImageIO.read(getClass().getResourceAsStream("/images/space.jpg"));
            setContentPane(new JPanel(new BorderLayout()) {
                @Override public void paintComponent(Graphics g) {
                    Dimension d = getSize();
                    g.drawImage(backgroundImage, 0, 0, d.width, d.height, null);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        //Create and format header
        setMinimumSize(new Dimension(screenHeight, screenHeight));
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        addWindowListener(this);
        JLabel text = new JLabel("Welcome to Plan-it!");
        add(text);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("Rockwell", Font.ITALIC + Font.BOLD, 38));
        text.setMinimumSize(new Dimension(400, 150));
        text.setPreferredSize(new Dimension(400, 150));
        text.setMaximumSize(new Dimension(400, 150));
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setVerticalAlignment(SwingConstants.CENTER);

        //Add logo
        try {
        JLabel picture = new JLabel(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/Planit.png"))));
        picture.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(picture);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        add(Box.createVerticalGlue());

        //time options (in hours)
        String timeOptions[] = { "0.5", "1", "1.5", "2", "2.5", "3", "3.5", "4", "4.5", "5", "5.5", "6" }; 
        String[] costOptions = new String[100];
        for (int i = 0, j = 0; i < 100; ++i, j+=5) {
            costOptions[i] = Integer.toString(j);
        }

        //Get user information to generate new activity plan
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JLabel spacer = new JLabel("                                      ");
        spacer.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(spacer);
        spacer.setForeground(Color.WHITE);
        spacer.setFont(new Font("Helvetica", Font.BOLD, 20));
        
        JLabel newActivityPlan = new JLabel("Generate a New Activity Plan:");
        newActivityPlan.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(newActivityPlan);
        newActivityPlan.setForeground(Color.WHITE);
        newActivityPlan.setFont(new Font("Helvetica", Font.BOLD, 24));
        
        timeSelector = new JComboBox(timeOptions);
        timeSelector.addItemListener(this);
        JLabel userTime = new JLabel("Select number of free hours:");
        inputPanel.add(userTime);
        inputPanel.add(timeSelector);
        userTime.setForeground(Color.WHITE);
        userTime.setFont(new Font("Helvetica", Font.PLAIN, 20));
        timeSelector.setFont(new Font("Helvetica", Font.PLAIN, 16));
        
        moneySelector = new JComboBox(costOptions);
        moneySelector.addItemListener(this);
        JLabel userMoney = new JLabel("    Select current budget: $");
        inputPanel.add(userMoney);
        inputPanel.add(moneySelector);
        userMoney.setForeground(Color.WHITE);
        userMoney.setFont(new Font("Helvetica", Font.PLAIN, 20));
        moneySelector.setFont(new Font("Helvetica", Font.PLAIN, 16));
 
        add(inputPanel);
        inputPanel.setOpaque(false);
        add(Box.createVerticalGlue());

        JPanel viewButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));

        //"Generate Activity Plan" button
        JButton b = new JButton("Generate Plan");
        b.setFont(new Font("Helvetica", Font.PLAIN, 18));
        b.setPreferredSize(new Dimension(150, 50));
        b.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ActivityPlan(availableTime, availableMoney);
            }
        });
        viewButtons.add(b);

        //"View Current Activities" button
        final JButton vb = new JButton("Edit Activities");
        vb.setFont(new Font("Helvetica", Font.PLAIN, 18));
        vb.setPreferredSize(new Dimension(150, 50));
        vb.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCurrentActivities();
               windowLinker = vb;
               // actWindowIsOpen = true;
               // if(actWindowIsOpen){
               //      vb.setEnabled(false);
               // }

            }
        });
        viewButtons.add(vb);
    
        add(viewButtons);
    
        viewButtons.setOpaque(false);
            add(Box.createVerticalGlue());

        //Footer label
        JLabel label3 = new JLabel("Developed by Team Rocket");
        add(label3);
        label3.setAlignmentX(Component.CENTER_ALIGNMENT);
        label3.setFont(new Font("Helvetica", Font.PLAIN, 14));
        add(Box.createVerticalGlue());
    }

     public void enableMyButton(){
        windowLinker.setEnabled(true);
    }

    public void disableMyButton(){
        windowLinker.setEnabled(false);
    }

    public void itemStateChanged(ItemEvent e) 
    { 
        // if the state combobox is changed 
        if (e.getSource() == timeSelector) {
            //Update user's availableTime variable based on current selection
            availableTime = 60.0 * Double.parseDouble("" + timeSelector.getSelectedItem());
        }
        else if (e.getSource() == moneySelector) {            
            //Update user's availableMoney variable based on current selection
            availableMoney = Double.parseDouble("" + moneySelector.getSelectedItem());
        }
    }

    public void showCurrentActivities(){
        CurrentActivities a = new CurrentActivities(this);

    }

    //Handles the closing of the Main window, re-setting settings to their defaults
    public void windowClosing(WindowEvent e)
    {
        try {
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
