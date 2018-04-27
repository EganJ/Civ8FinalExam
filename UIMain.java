import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class UIMain {
	static JFrame frame;
	static Scene scene;
	public static Font mainFont;
	public static Font mainFontThin;
	static final Dimension SCREEN_SIZE=Toolkit.getDefaultToolkit().getScreenSize();
	public static void setUp() {
		//loads in font 
		try {
			mainFont= Font.createFont(Font.TRUETYPE_FONT, new File("enemy_sub.ttf"));
			mainFontThin=Font.createFont(Font.TRUETYPE_FONT, new File("enemy_sub_thin.ttf"));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Initialize a full-screen undecorated JFrame
		frame= new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		scene=new MainMenu();
		frame.add(scene);
		scene.swapIn();
		frame.setVisible(true);
	}

	/**
	 * @param component - a JComponent that needs to be centered horizontally in its container
	 * @return Box b- a box with the component in it that will center the component
	 */
	public static Box centerComponentHorizontal(JComponent component) {
		Box b= Box.createHorizontalBox();
		b.add(Box.createHorizontalGlue());
		b.add(component);
		b.add(Box.createHorizontalGlue());
		return b;
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
