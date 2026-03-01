import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
* Sets the background image of the home screen to back3.jpg
**/
class HomeOpener extends JFrame
{
/**
* A JDesktopPane that layers the background image and HomePanel
**/
   JDesktopPane dp = new JDesktopPane();
   /**
   * A label that retrieves the background image
   **/
   JLabel lbl = new JLabel(new ImageIcon(((new ImageIcon("back3.jpg")).getImage()).getScaledInstance(1200, 1000, java.awt.Image.SCALE_SMOOTH)));
   /**
   * Displays the HomeScreen
   **/
   HomePanel homepanel = new HomePanel();
   /**
*Creates JFrame "FLAMIN' FINGERS" with desired location, size, visiblity, and closing operations
**/
   public HomeOpener() throws Exception
   {
      super("FLAMIN' FINGERZ: Home"); //set frame to home title
      setSize(1232/2,1070/2); //sets the size
      setVisible(true);//makes it visible
      setLocation(200, 100);//sets the location
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//sets the close operation
      
      lbl.setBounds(0,0,1200/2,1000/2);//sets the size of the JLabel
       
      homepanel.setOpaque(false); //makes homepanel transparent
      homepanel.setBounds(0,0,1200/2,1000/2);//sets the size of homepanel
        
      dp.add(lbl,new Integer(0));//add image label
      dp.add(homepanel,new Integer(100));//add HomePanel on top
     
      setLayeredPane(dp);//adds the layered desktop pane to the JFrame
   }
}