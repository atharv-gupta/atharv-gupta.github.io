import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
* Sets the background image of the gaming screen to back3.jpg
**/

class BackgroundOpener extends JFrame
{
/**
* A JDesktopPane that layers the background image and GameBoard.
**/
   JDesktopPane dp = new JDesktopPane();
   /**
   * A label that retrieves the background image
   **/
   JLabel lbl = new JLabel(new ImageIcon(((new ImageIcon("back3.jpg")).getImage()).getScaledInstance(1200, 1000, java.awt.Image.SCALE_SMOOTH)));
   /**
*Creates JFrame "FLAMIN' FINGERS" with desired location, size, visiblity, and closing operations
**/

   public BackgroundOpener(int players, int level, String play1, String play2) throws Exception
   {
      super("Flamin' Fingerz");//frame object with Flamin' Fingerz title
      GameBoard gameboard = new GameBoard(players, level, play1, play2); //opens Gameboard with passed args
      lbl.setBounds(0,0,1200/2,1000/2);//set size
       
      gameboard.setOpaque(false);
      gameboard.setBounds(0,0,1200/2,1000/2);//size
        
      dp.add(lbl,new Integer(0)); //add image label
      dp.add(gameboard,new Integer(100));//add Gameboard on top
     
      setLayeredPane(dp);//adds the layered desktop pane to the JFrame
   }
}