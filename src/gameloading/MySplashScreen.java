/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameloading;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Buddhika
 */
public class MySplashScreen  {

    static SplashScreen mySplash;                   // instantiated by JVM we use it to get graphics
    static Graphics2D splashGraphics;               // graphics context for overlay of the splash image
    static Rectangle2D.Double splashTextArea;       // area where we draw the text
    static Rectangle2D.Double splashProgressArea;   // area where we draw the progress bar
    static Font font;
    
    public MySplashScreen(){
        if (mySplash != null)   // check if we really had a spash screen
            mySplash.close();   // we're done with it
    }
    
    public void initSplash() {
         mySplash = SplashScreen.getSplashScreen();
        if (mySplash != null)
        {   // if there are any problems displaying the splash this will be null
            Dimension ssDim = mySplash.getSize();
            int height = ssDim.height;
            int width = ssDim.width;
            // stake out some area for our status information
            splashTextArea = new Rectangle2D.Double(15., height*0.92, width * .45, 16.);
            splashProgressArea = new Rectangle2D.Double(width * .55, height*.92, width*.4, 16 );

            // create the Graphics environment for drawing status info
            splashGraphics = mySplash.createGraphics();
            font = new Font("Italic", Font.PLAIN, 12);
            splashGraphics.setFont(font);
            
            // initialize the status info
            splashText("Initiating The Game");
            splashProgress(0);
        }
        else{
            System.out.println("Error");
        }
        
    }

   
    
    public static void splashText(String str)
    {
        if (mySplash != null && mySplash.isVisible())
        {   // important to check here so no other methods need to know if there
            // really is a Splash being displayed

            // erase the last status text
            splashGraphics.setPaint(Color.LIGHT_GRAY);
            splashGraphics.fill(splashTextArea);

            // draw the text
            splashGraphics.setPaint(Color.BLACK);
            splashGraphics.drawString(str, (int)(splashTextArea.getX() + 10),(int)(splashTextArea.getY() + 15));

            // make sure it's displayed
            mySplash.update();
        }
    }
    
    public static void splashProgress(int pct)
    {
        if (mySplash != null && mySplash.isVisible())
        {

            // Note: 3 colors are used here to demonstrate steps
            // erase the old one
            splashGraphics.setPaint(Color.blue);
            splashGraphics.fill(splashProgressArea);

            // draw an outline
            splashGraphics.setPaint(Color.BLUE);
            splashGraphics.draw(splashProgressArea);

            // Calculate the width corresponding to the correct percentage
            int x = (int) splashProgressArea.getMinX();
            int y = (int) splashProgressArea.getMinY();
            int wid = (int) splashProgressArea.getWidth();
            int hgt = (int) splashProgressArea.getHeight();

            int doneWidth = Math.round(pct*wid/100.f);
            doneWidth = Math.max(0, Math.min(doneWidth, wid-1));  // limit 0-width

            // fill the done part one pixel smaller than the outline
            splashGraphics.setPaint(Color.GREEN);
            splashGraphics.fillRect(x, y+1, doneWidth, hgt-1);

            // make sure it's displayed
            mySplash.update();
        }
    }
    

    public void showMessages() {
        try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException ex)
            {
                // ignore it
            }
       for(int i=1;i<=10;i++)
        {
            int pctDone = i * 10;
            String text = "Loading Game Files ";
            for(int j=0;j<i;j++){
                text+=".";
            }
            splashText(text);
            splashProgress(pctDone);
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException ex)
            {
                // ignore it
            }
        }
       
       
    }
    
}
