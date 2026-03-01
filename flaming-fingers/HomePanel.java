/**************************************
* HomePanel displays the homescreen with level buttons,
* player name fields, player buttons, the title label,
* the begin game button, and links to the paragraphs
* and instructions for the game. 
*
* @author Atharv Gupta, Paarth Jain, Joshua Sahaya Arul
* Summer CS 2016 Final Project
***************************************/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JOptionPane.*;
import java.awt.Desktop.*;
import java.io.File;
public class HomePanel extends JPanel
{
   /***
   * public String variable for player 1's name
   ***/
   public String playerName1;
   /***
   * public String variable for player s's name
   ***/
   public String playerName2;
   /***
   * public int variable for which level to run
   ***/
   public int l;
   /***
   * public int variable for number of players to run
   ***/
   public int p;
   /***
   * public JTextField for player 1's name input
   ***/
   public JTextField name1;
   /***
   * public JTextField for player 2's name input
   ***/
   public JTextField name2;
   /***
   * HomePanel Contructor, 
   * creates the HomesPanel with a BorderLayout() and 
   * places the info panel (with the level buttons, player buttons,
   * and player name textFields) in the Center, 
   * places the title Label in the North, and 
   * places the Option subpanel (with the Begin, Information
   * and Paragraphs button) in the South. 
   ***/
   public HomePanel() throws Exception
   {  
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//sets the LookAndFeel to match those of the system
      setOpaque(true); //sets HomePanel opacity to false for Background image to show
   
      JPanel level = new JPanel(); //Sub-subpanel for row of Level radiobuttons and Level label
      level.setLayout(new FlowLayout()); 
      level.setOpaque(true);
      
      JPanel player = new JPanel(); //Sub-subpanel for row of player radiobuttons and player label
      player.setLayout(new FlowLayout()); 
      player.setOpaque(true); 
   
      JPanel name = new JPanel(); //Sub-subpanel for row of player name TextFields and Name Label
      name.setLayout(new FlowLayout()); 
      name.setOpaque(true); 
      
      JPanel info = new JPanel(); //Subpanel to store previous subpanels - all the player info inputted here
      info.setLayout(new GridLayout(3,1)); //sets to GridLayout - 3,1 for each of the three sub-subpanels
      info.setOpaque(true); 
      
      JPanel optionPanel = new JPanel(); //Subpanel to store buttons - option buttons at bottom
      optionPanel.setLayout(new GridLayout(1,3)); // sets to GridLayout - 1,3, for each button
      optionPanel.setOpaque(true); 
     
      setLayout(new BorderLayout()); //sets HomePanel to BorderLayout
      
      JLabel title = new JLabel ("FLAMIN' FINGERZ",SwingConstants.CENTER); //title label
      title.setFont(new Font("Monospaced", Font.BOLD, 65)); 
      title.setOpaque(true); 
      title.setForeground(Color.black); 
      add (title, BorderLayout.NORTH); // adds title to north of HomePanel
      
      JLabel playerLabel = new JLabel("Number of Players?"); //Creates label for Player sub-subpanel
      playerLabel.setOpaque(true); 
      playerLabel.setForeground(Color.black);
      player.add(playerLabel); //adds label to Player sub-subpanel
      
      JRadioButton player1 = new JRadioButton("1 Player"); //JRadiobutton option for one player
      player1.setForeground(Color.black); 
      player1.setOpaque(true); 
      player1.addItemListener(new PlayerListener(1)); //adds PlayerListener with int 1 passed as arguement
      player1.setSelected(true); //Starts selected
      player.add(player1); //add to Player sub-subpanel
   
      JRadioButton player2 = new JRadioButton("2 Players"); //JRadiobutton option for two players
      player2.setForeground(Color.black); 
      player2.setOpaque(true); 
      player2.addItemListener(new PlayerListener(2)); //adds PlayerListener with int 2 passed as arguement
      player2.setSelected(false); 
      player.add(player2);//add to Player sub-subpanel
      
      JLabel levelLabel = new JLabel("Which Level?"); //Label for Level sub-subpanel
      levelLabel.setForeground(Color.black); 
      levelLabel.setOpaque(true); 
      level.add(levelLabel); //add to Level sub-subpanel
   
      JRadioButton level1 = new JRadioButton("Level 1"); //JRadiobutton for Level 2
      level1.setForeground(Color.black); 
      level1.setOpaque(true); 
      level1.addItemListener(new LevelListener(1)); //add LevelListener with int 1 as arguement
      level1.setSelected(true); //Starts selected
      level.add(level1); //add to Level sub-subpanel
      
      JRadioButton level2 = new JRadioButton("Level 2"); //JRadiobutton for Level 2
      level2 .setForeground(Color.black);
      level2.setOpaque(true);
      level2.addItemListener(new LevelListener(2));//add LevelListener with int 2 as arguement
      level2.setSelected(false);
      level.add(level2); //add to Level sub-subpanel
      
      JRadioButton level3 = new JRadioButton("Level 3");//JRadiobutton for Level 3
      level3.setForeground(Color.black);
      level3.setOpaque(true);
      level3.addItemListener(new LevelListener(3));//add LevelListener with int 3 as arguement
      level3.setSelected(false);
      level.add(level3);//add to Level sub-subpanel
      
      JRadioButton level4 = new JRadioButton("Test"); //JRadiobutton for Test Level
      level4.setForeground(Color.black);
      level4.setOpaque(true); 
      level4.addItemListener(new LevelListener(4)); //add LevelListener with int 4 as arguement
      level4.setSelected(false);
      level.add(level4); //add to Level sub-subpanel
   
      
      JLabel nameLabel = new JLabel("Player Names?"); //Label displaying Player Names
      nameLabel.setOpaque(true);
      nameLabel.setForeground(Color.black);
      
      name1 = new JTextField (8); //name1 TextField with 8 columns
      name1.setForeground(Color.black);
      name1.setOpaque(true);
      
      name2 = new JTextField (8); //name2 TextField with 8 columns
      name2.setForeground(Color.black);
      name2.setOpaque(true);
      
      
      name.add(nameLabel); //add to name sub-subpanel
      name.add(name1); //add to name sub-subpanel
      name.add(name2); //add to name sub-subpanel
      
      JButton inst = new JButton("INSTRUCTIONS"); //button for Instructions display
      inst.addActionListener(new Opener(1)); //add Opener Listener with int arguement 1
      
      JButton begin = new JButton("BEGIN"); //button to open GameBoard
      begin.addActionListener(new Opener(2)); //add Opener Listener with int arguement 3
      
      JButton pg = new JButton("PARAGRAPHS"); //button for Paragraph display
      pg.addActionListener(new Opener(3)); //add Opener Listener with int arguement 3
      
      optionPanel.add(inst); //add to Option subpanel
      optionPanel.add(begin); //add to Option subpanel
      optionPanel.add(pg); //add to Option subpanel
      
      info.add(level); //add to Info subpanel
      info.add(player); //add to Info subpanel
      info.add(name); //add to Info subpanel
      
      ButtonGroup players = new ButtonGroup(); //buttonGroup for player radioButtons
      players.add(player1); //add to group
      players.add(player2); //add to group
        
      ButtonGroup levels  = new ButtonGroup(); //buttonGroup for level radioButtons
      levels.add(level1); //add to group
      levels.add(level2); //add to group
      levels.add(level3); //add to group
      levels.add(level4); //add to group
      info.setOpaque(true);
   
      add(info,BorderLayout.CENTER); //add info to center of Home Screen
      
      add (optionPanel, BorderLayout.SOUTH); //add option panel to south of HomeScreen
      
   }
   /***
   * Method closes the current frame (window) that is open
   ***/
   private void close()
   {
      Component comp = SwingUtilities.getRoot(this); //Componenet object using SwingUtilities getRoot method
      ((Window) comp).dispose(); //casts as Window object, disposes (closes window)
   }
   
   /***
   * Method opens a File onto the desktop
   * @param document   a File object
   ***/
   public static void open(File document) throws Exception 
   {
      Desktop dt = Desktop.getDesktop();//Desktop object (Computer screen)
      dt.open(document); //open document arg on desktop object
   }

   /***
   * Listener class for the Player radiobuttons
   ***/
   private class PlayerListener implements ItemListener
   {
      /***
      * private int field numPlayer
      ***/
      private int numPlayer;
      /***
      * Method sets int numPlayer to value passed in
      * Constructor method for numPlayer field
      * @param x   an int to set numPlayer to
      ***/
      public PlayerListener(int x)
      {
         numPlayer = x;
      }
      /***
      * Method sets int p, a field above, to numPlayer
      * @param e   ItemEvent object
      ***/
      public void itemStateChanged(ItemEvent e)
      {
         p = numPlayer;
      }
   }
   
   /***
   * Listener class for the Level radiobuttons
   ***/
   private class LevelListener implements ItemListener
   {
      /***
      * private int field numLevel
      ***/
      private int numLevel;
      
      /***
      * Method sets int numLevel to value passed in
      * Constructor method for numLevel field
      * @param x   an int to set numLevel to
      ***/
      public LevelListener(int x)
      {
         numLevel = x;
      }
      /***
      * Method sets int l, a field above, to numLevel
      * @param e   ItemEvent object
      ***/
      public void itemStateChanged(ItemEvent e)
      {
         l = numLevel;
      }
   }
   
   /***
   * Listener class for the Option Panel buttons (begin, 
   * paragraphs, and instructions)
   ***/
   private class Opener implements ActionListener
   {
      /***
      * private int field file
      ***/
      private int file;
      /***
      * Method sets int file to value passed in
      * Constructor method for file field
      * @param x   an int to set file to
      ***/
      public Opener (int x)
      {
         file = x;
      }
      /***
      * Method decides what to do depending on file value passed 
      * in the Listener call
      * Depending on file value, opens the Instructions text document, 
      * the Paragraphs text document, or the Game itself
      * @param e   ActionEvent object
      ***/
      public void actionPerformed (ActionEvent e)
      {
         try 
         {
            if (file == 1) //Instructions button
            {
               File instr = new File ("Instructions.txt");
               open (instr); //open file onto desktop
            }
            else if (file == 2) //Begin button
            {
               playerName2 = name2.getText(); //set playerName2 variable to textfield input
               playerName1 = name1.getText(); //set playerName2 variable to textfield input
            
               BackgroundOpener pane = new BackgroundOpener(p,l,playerName1,playerName2); //runs BackgroundOpener with 
                                                                                          //args from info panel
               pane.setSize(1232/2,1070/2); 
               pane.setLocation(200, 100);
               pane.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
               pane.setVisible(true);
               close(); //close HomeScreen
            }
            else if (file == 3) //Paragraphs button
            {
               File para = new File ("Paragraphs.txt");
               open (para); //open paragraphs doc onto desktop
            }
         }
         catch (Exception f)
         {  
         }
      }
   }
}
