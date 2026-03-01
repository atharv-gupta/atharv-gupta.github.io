import javax.swing.Timer;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.sound.sampled.*;
import java.text.DecimalFormat;
/**
* This class is where all graphic elements, keyboard inputs, calculations, and outputs of the game occur. 
**/

public class GameBoard extends JPanel
{
/**
* The randomly chosen paragraph number from the paragraph file.
**/
   private int paragraph;
   /**
* The number of correctly typed words.
**/
   private int correct;
   /**
* The number of incorrectly typed words (max one per word).
**/
   private int wrong;
   /**
* The amount of time elapsed.
**/
   private int time;
   /**
* The index of the current word.
**/
   private int currentWord;
   /**
* The number of words in the paragraph.
**/
   private int numWords;
    /**
* The number of players playing.
**/
   private int numPlayers;
   /**
* Used to take into account the number of players as well as the player number.
**/
   private int pass;
  /**
* Number of correct words by player 1.
**/ 
   private int correct1;
   /**
* Number of incorrect words by player 1.
**/
   private int wrong1;
   /**
* Number of time taken by player 1.
**/
   private int time1;
   /**
* Number of correct words by player 2.
**/
   private int correct2;
   /**
* Number of incorrect words by player 2.
**/
   private int wrong2;
   /**
* Number of time taken by player 2.
**/
   private int time2;
   /**
* Player 2 name.
**/
   private String player2;
   /**
* an array of words in the paragraph.
**/
   private String[] words;
 /**
* Displays player 1 or player 2. 
**/  
   private JLabel playerNum;
   /**
* Displays the name of the player based on input in the home screen. 
**/
   private JLabel playerName;
   /**
* Displays the time elapsed.
**/
   private JLabel stopwatch;
   /**
* Displays the words per minute based on the words corect and time elapsed.
**/
   private JLabel wpm;
   /**
* Displays the words correct based on the words correct and words incorrect. 
**/
   private JLabel accuracy;
   /**
* Displays the paragraph whole.
**/
   private JLabel remaining;
   /**
* The location for keyboard input.
**/
   private JTextField type;
   /**
* Displays the score using the ActionListener
**/
   private JButton score;
   /**
* Starts the game and timer
**/
   private JButton start;
   /**
* ActionListener of the score button in 1 player game. 
**/
   private Score1 scoreListener;
   /**
* Keeps track of time throughout the game. 
**/
   private Timer t;
   /**
* ActionListener for the start button. 
**/
   private StartListener starter;
   /**
* Sets the default color for the paragraph text and labels.
**/
   private Color text = Color.black;
   /**
* The sound for an incorrectly spelled noise.
**/
   private static Clip clip;
   /**
* The sound played when time begins.
**/
   private static Clip go;
   /**
* The sound played for READY? and SET
**/
   private static Clip ready;
   /**
* Keeps the accuracy as a 2 decimal value. 
**/
   private DecimalFormat format = new DecimalFormat("00.00%");
   /**
* Constructor for a gameboard that takes 4 arguments. Sets up the game screen, and runs the game. 
**/
   public GameBoard(int players, int level, String play1, String play2) throws Exception
   {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//sets the LookAndFeel to match those of the system
      setLayout(new BorderLayout());
      
      numPlayers = players;
      player2 = play2;
      //add JLabel done and remaining, set font and size
   
      remaining = new JLabel();
      remaining.setFont(new Font("Monospaced", Font.BOLD, 23));
      remaining.setVerticalAlignment(SwingConstants.CENTER);
      remaining.setForeground(text);
      remaining.setOpaque(false);
      add(remaining, BorderLayout.CENTER);
      
      type = new JTextField();
      type.setFont(new Font("Monospaced", Font.PLAIN, 30));
      type.setEnabled(false);
      add(type, BorderLayout.SOUTH);
      
      paragraph = (int)(Math.random()*8+1);
   //new Scanner
      Scanner infile = new Scanner(new File(level+".txt"));
   
      words = new String[100];
   //read randomly chosen paragraph from the corresponding difficulty document, counts number of words (numWords)
   //print array into JLabel remianing
      boolean state = false;
      while(!state && infile.hasNext())
      {
         if((infile.next()).equals(""+paragraph))
            state = true;
      }
      words[0]=infile.next();
      remaining.setText("<html><font color=blue>"+words[0]+"</font>");
      numWords = 1;
      while(!infile.hasNextInt())
      {
         words[numWords]=infile.next();
         remaining.setText(remaining.getText()+" <b>"+words[numWords]+"</b>");
         numWords++;
      }
      remaining.setText(remaining.getText()+"<html>  ");
      infile.close();
      
   //new subpanel, contains 5 labels that have player number, name, stopwatch, wpm, and accuracy
      JPanel side = new JPanel();
      side.setLayout(new GridLayout(8,1));
      playerNum = new JLabel("Player 1");
      playerNum.setHorizontalAlignment(SwingConstants.CENTER);
      playerNum.setFont(new Font("Times New Roman", Font.BOLD, 25));
      playerNum.setForeground(text);
      side.add(playerNum);
      playerName = new JLabel(play1);
      playerName.setHorizontalAlignment(SwingConstants.CENTER);
      playerName.setFont(new Font("Times New Roman", Font.BOLD, 25));
      playerName.setForeground(text);
      side.add(playerName);
      stopwatch = new JLabel("0:00");
      stopwatch.setFont(new Font("Times New Roman", Font.BOLD, 25));
      stopwatch.setForeground(text);
      stopwatch.setHorizontalAlignment(SwingConstants.CENTER);
      side.add(stopwatch);
      wpm = new JLabel("-- WPM");
      wpm.setFont(new Font("Times New Roman", Font.BOLD, 25));
      wpm.setForeground(text);
      wpm.setHorizontalAlignment(SwingConstants.CENTER);
      side.add(wpm);
      accuracy = new JLabel("100.0% accuracy");
      accuracy.setFont(new Font("Times New Roman", Font.BOLD, 25));
      accuracy.setForeground(text);
      accuracy.setHorizontalAlignment(SwingConstants.CENTER);
      side.add(accuracy);
   
   //add 3 buttons to the subpanel: home, scores, start
   //add Score1 listener to scoresbutton
   //add GoHome listener to home button
   //add StartListener listener to start button
      JButton home = new JButton("Home");
      home.addActionListener(new GoHome());
      side.add(home);
      
      score = new JButton("Scores");
      scoreListener = new Score1();
      score.addActionListener(scoreListener);
      score.setEnabled(false);
      side.add(score);
      
      start = new JButton("Start");
      starter = new StartListener();
      start.addActionListener(starter);
      side.add(start);
      side.setOpaque(false);
      add(side, BorderLayout.EAST);
      
      //load sound files
      loadClipWav();
      loadGoWav();
      loadReadySetWav();
   }
   /**
* Formats time from milliseconds to minutes and seconds. 
**/
   private void updateTime()
   {
      int sec = (time-3000)/1000;//calcuates seconds
      int min = sec/60;//calculates minutes
      int tens = sec%60/10;//ten's place
      int ones = sec%10;//one's place
      stopwatch.setText(""+min+":"+tens+ones);//set stopwatch label text
   }
   /**
* Calculates words per minute based on time in milliseconds and number of words correct. 
**/
   private void updateWPM()
   {
      double per = (int)(correct*60000*100.0/(time-3000))/100.0;//calculate WPM with correct words and time
      wpm.setText(""+per+" WPM");//sets label to wpm
   }
   /**
* Calculates the accuracy based on the number of correct words and incorrect words. Formats to a percentage with 2 decimal points. 
**/
   private void updateAccuracy()
   {
    
      if (correct >0 )//if first word has been typed
      {
         if (wrong == 0)
         {
            accuracy.setText("100.0% accuracy");//sets 100% accuracy if no wrong words
         }
         else
         {
            double acc = (1.00*correct/(correct+wrong));//calculates accuracy with correct and wrong values
            accuracy.setText(""+ format.format(acc)+" accuracy");//sets text
         }
      }
   }
   /**
* Determines whether player 2 needs to play, or if scores can be displayed. If scores are ready to be displayed, sets score button enabled true. 
**/
   private void choosePath()
   {
      pass++;
      if(numPlayers==1)//if one player playing
      {
         score.setEnabled(true);//scores available
         start.setEnabled(false);//start unavailable
      }
      else if(numPlayers==2)
      {
         player2();//run player2() method
      }
   }
   /**
* Only executed if a 2 player game. Resets the game board such that the second player can play, and calculates all necessary information. 
**/
   private void player2()
   {
      playerNum.setText("Player 2"); //set label to player2
      playerName.setText(player2); //reset player name
      correct1 = correct;
      wrong1 = wrong;
      time1 = time;
      stopwatch.setText("0:00"); //reset stopwatch
      wpm.setText("-- WPM"); //reset wpm
      accuracy.setText("100.0% accuracy"); //reset accuracy
      score.removeActionListener(scoreListener); 
      score.addActionListener(new Score2()); //reset score button listner
      start.setEnabled(true);
   
      remaining.setText("<html><font color=blue>"+words[0]+"</font>"); //set first word to blue
      for(int y = 1; y<numWords; y++) //iterate through paragraph array
      {
         remaining.setText(remaining.getText()+" "+words[y]); //set words to blue as player goes through para
      }
      remaining.setText(remaining.getText()+"<html>  "); //sets final paragraph setting
   }
   /***
   * Closes the current frame (window) that is open
   ***/
   private void close()
   {
      Component comp = SwingUtilities.getRoot(this); //Creates component object
      ((Window) comp).dispose(); //dispose of component on window
   }
   /***
   * loads Clip file, sound clip for wrongWord.wav
   ***/
   private void loadClipWav() throws Exception
   {
      try
      {
         AudioInputStream sample; //AudioInputStream object
         sample = AudioSystem.getAudioInputStream(getClass().getResource("wrongWord.wav"));
         //set sample to inputStream of wrongWord.wav file
         clip = AudioSystem.getClip(); //create object
         clip.open(sample); //set clip to audioStream
      }
      catch (Exception e)
      {}
   }
   /***
   * loads Clip file, sound clip for wrongWord.wav
   ***/
   private void loadReadySetWav() throws Exception
   {
      try
      {
         AudioInputStream sample; //AudioInputStream object
         sample = AudioSystem.getAudioInputStream(getClass().getResource("ReadySet.wav"));
         //set sample to inputStream of ReadySet.wav file
         ready = AudioSystem.getClip(); //create object
         ready.open(sample); //set ready to audioStream
      }
      catch (Exception e)
      {}
   }
   /***
   * loads Clip file, sound clip for ReadySet.wav
   ***/
   private void loadGoWav() throws Exception
   {
      try
      {
         AudioInputStream sample; //AudioInputStream object
         sample = AudioSystem.getAudioInputStream(getClass().getResource("Go.wav"));
         //set sample to inputStream of Go.wav file
         go = AudioSystem.getClip(); //create object
         go.open(sample); //set go to audioStream
      }
      catch (Exception e)
      {}
   }
   /***
   * loads Clip file, sound clip for Go.wav
   ***/
   public void playClip()
   {
      clip.stop(); //stop previous sound
      clip.setFramePosition(0); //set audio frame to beginning of clip
      clip.start();// start playing 
   }
   /***
   * plays Clip file, Go.wav
   ***/
   public void playGo()
   {
      go.stop(); //stop previous sound
      go.setFramePosition(0); //set audio frame to beginning of go
      go.start();  // start playing 
   }
   /***
   * plays Clip file, ReadySet.wav
   ***/
   public void playReady()
   {
      ready.stop(); //stop previous sound
      ready.setFramePosition(0); //set audio frame to beginning of ready
      ready.start();  // start playing
   }
   /***
   * Listener class, runs game
   ***/
   private class Listener implements ActionListener 
   {
   /****
   * runs the game - 
   * Runs a three second countdown with 
   * preloaded soundclips, prompts the user to type the words
   * of the paragraph as fast as posible while keeping track of
   * wpm, accuracy, and time elapsed. 
   * 
   * @param e  ActionEvent object
   ***/
      public void actionPerformed(ActionEvent e)
      {
         time+=6;//increments time by 6
         if(time<1000)
         {
            if (!type.getText().equals("READY?"))//if the text field doesn't say "READY?"
            {
               playReady();//plays ReadySet.wav
               type.setText("READY?");//sets the textfiedl to say "READY?"
            }
         }
         else if(1000<=time&&time<2000)
         {
            if (!type.getText().equals("SET"))//if the text field doesn't say "SET"
            {
               playReady();//plays ReadySet.wav
               type.setText("SET");//sets the textfiedl to say "SET"
            }
         }
         else if(2000<=time&&time<3000)
         {
            if (!type.getText().equals("GO!"))//if the text field doesn't say "GO!"
            {
               type.setText("GO!");//sets the textfiedl to say "GO!"
               playGo();//plays Go.wav
            }
         }
         else if(3000<=time&&time<3050)
            type.setText("");//clears the text field after the countdown
         else
         {
            updateTime();//updates the stopwatch
            updateWPM();//updates wpm
            updateAccuracy();//updates accuracy
            if((type.getText()).contains(" "))//if the user typed a space
            {
               if((type.getText().substring(0,type.getText().indexOf(" "))).equals(words[currentWord]))//if the word is right
               {
                  type.setText("");//clears the text field
                  type.setBackground(Color.white);//sets the background to white
                  remaining.setText("<html>"+words[0]+"");//prints the first word in the array int remaining
                  for(int y = 1; y<numWords; y++)
                  {
                     if(y==currentWord+1)
                        remaining.setText(""+remaining.getText()+" <font color=blue>"+words[y]+"</font>");//prints all the words up to the index of the current word into remaining
                     else
                        remaining.setText(remaining.getText()+" "+words[y]);//prints the remaining words in bold
                  }
                  remaining.setText(remaining.getText()+"<html>  ");//adds the html tag to make the words wrap
                  currentWord++;//increases currentWord by one
                  correct++;//increases the number of correct words by one
               }
               else
               {
                  if(type.getBackground()!=Color.red)//if the word is wrong and the background isn't already red
                  {
                     type.setBackground(Color.red);//sets the background red
                     wrong++;//increases the number of wrong words by one
                     playClip();//plays wrongWord.wav
                  } 
               }
            }
            if(currentWord==numWords)
            {
               t.stop();//stops the timer
               type.setEnabled(false);//doesn't allow user to type into the text field
               if(pass == 0)
                  choosePath();//if this is the first time the game has been "played" in this run, executes choosePath()
               else if(pass == 1)
               {
                  correct2 = correct;
                  wrong2 = wrong;
                  time2 = time;//although not necessary, assigns the current run's correct words, wrong words, and time to Player 2 values
                  score.setEnabled(true);//allows user to click the score button
                  start.setEnabled(false);//doesn't allow user to click the score button
               }
            }
         }
      }
   }
   /***
   * Listner class for Start button
   ***/
   private class StartListener implements ActionListener
   {
      /***
   * Instantiates timer with Listener and begins time
   *
   * @param e  ActionEvent object
   ***/
      public void actionPerformed(ActionEvent e)
      {
         try
         {
            currentWord = time = correct = wrong = 0;//sets constants to 0
            type.setText("");//clears the text field
            type.setEnabled(true);//enables the JTextField in order for the user to be able to type immediately after the countdown is over
            start.setEnabled(false);//keeps the user from being able to use the start button
            t = new Timer(5, new Listener());//assigns t a new Timer that executes the comamnds in Listener every 5 milliseconds 
            t.start();//begins the timer
         }
         catch(Exception f) {}
      }
   }
   /***
   * Listner for Score button for the 1 player setting
   ***/
   private class Score1 implements ActionListener
   {
      /***
   * Clears gameboard panels and displays the score label, shows the wpm, accuracy, and total
   * time for one player. 
   *
   * @param e  ActionEvent object
   ***/
      public void actionPerformed(ActionEvent e)
      {
         double acc1 = (correct*1.00/(correct+wrong));//calculates the player's accuracy
         String accu1 = format.format(acc1);//formats the calculated accuracy
         remaining.setText("<html><br> WPM: " + (int)(correct*60000*100.0/(time-3000))/100.0 + "<br>Accuracy:" + accu1  + "<br> Time: " + (time-3000)/1000/60+":" +((time-3000)/1000)%60/10+""+((time-3000)/1000)%60%10 + "<html>");
         //displays the player's statistics
         playerNum.setText("");
         playerName.setText("");
         stopwatch.setText("");
         wpm.setText("");//sets the four labels on the side to show nothing
         accuracy.setText("100.0% accuracy");//sets the accuracy to maintain home, score, and start button size
         accuracy.setVisible(false);//makes the accuracy button invisible
      }
   }
   /***
   * Listner for Score button for the 2 player setting
   ***/
   private class Score2 implements ActionListener
   {
      /***
   * Clears gameboard panels and displays the score label, shows the wpm, accuracy, and total
   * time for both player, along with the winner 
   *
   * @param e  ActionEvent object
   ***/
      public void actionPerformed(ActionEvent e)
      {
         String win;//creates a String reference, win
         if (time1<time2)
            win = "Player 1 Wins!"; //Displays message if Player 1 finished faster
         else 
            win = "Player 2 Wins!"; //Displays message if Player 2 finished faster
         double acc1 = (correct1*1.00/(correct1+wrong1));//calculates accuracy for Player 1
         double acc2 = (correct2*1.00/(correct2+wrong2));//calculates accuracy for Player 2
         String accu1 = format.format(acc1);//formats accuracy for Player 1
         String accu2 = format.format(acc2);//formats accuracy for Player 2
         remaining.setText("<html>" + win + "<br>" + "--------------" + "<br> Player 1 <br> -------- <br> WPM: " + (int)(correct1*60000*100.0/(time1-3000))/100.0 + "<br>Accuracy:" + accu1  + "<br>Time: " + (time1-3000)/1000/60+":" +((time1-3000)/1000)%60/10+""+((time1-3000)/1000)%60%10+ "<br> Player 2 <br> -------- <br> WPM: " + (int)(correct2*60000*100.0/(time2-3000))/100.0 +"<br>Accuracy: " + accu2 + "<br>Time: " + (time2-3000)/1000/60+":" +((time2-3000)/1000)%60/10+""+((time2-3000)/1000)%60%10+ "<html>" );
         //sets the text to display String win and the statistics for each player
         playerNum.setText("");
         playerName.setText("");
         stopwatch.setText("");
         wpm.setText("");//sets the four labels on the side to show nothing
         accuracy.setText("100.0% accuracy");//sets the accuracy to maintain home, score, and start button size
         accuracy.setVisible(false);//makes the accuracy button invisible
      }
   }
   /***
   * Listner for the Home button 
   ***/
   private class GoHome implements ActionListener
   {
      /***
      * opens the Home Screen, closes the GameBoard frame
      * @param e  ActionEvent object
      ***/
      public void actionPerformed(ActionEvent e)
      {
         try
         {
            HomeOpener home = new HomeOpener();//opens new HomePanel
            close();//closes GameBoard
         }
         catch(Exception f){}
      }
   }
}