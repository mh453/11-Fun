
import javax.swing.*;

/**
 * Created by Majid on 22/12/2015.
 */
public class App
{
  public static void main(String[]args)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      @Override
      public void run()
      {
        new MainFrame();
      }
    });
  }
}
