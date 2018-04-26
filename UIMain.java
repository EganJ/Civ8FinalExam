import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class UIMain {
	static JFrame frame;
	static Scene scene;
	static final Dimension SCREEN_SIZE=Toolkit.getDefaultToolkit().getScreenSize();
	public static void setUp() {
		
		//Initialize a full-screen undecorated JFrame
		frame= new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		scene=new MainMenu();
		scene.swapIn();
		frame.add(scene);
		frame.revalidate();
	}

	/**
	 * 
	 * @return Image i - a screenshot of the screen when called. Returns null if cannot get screenshot. Returns whole screen if it can, else just a shot of frame. They should be the same except when starting the program
	 */
	public static Image getScreenshot() {
		try {
			return (new Robot()).createScreenCapture(new Rectangle(SCREEN_SIZE));
		}catch(Exception e) {
			e.printStackTrace();
			try {
				BufferedImage b= new BufferedImage(frame.getWidth(),frame.getHeight(),BufferedImage.TYPE_INT_ARGB);
				frame.paint(b.getGraphics());//this bit of code was borrowed from https://stackoverflow.com/users/876497/dejanlekic 
				return (Image)b;
			}catch(Exception e2) {
				System.out.println("Exception 2");
				e.printStackTrace();
				return null;
			}
		}
	}
}
