import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;

public class MainMenu extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image screenshot;
	static Image background;
	static boolean isSetUp=false;
	MainMenu(){
		setBackground(Color.BLACK);
		screenshot=UIMain.getScreenshot();
		if(!isSetUp) {
			//loads in images
			try {
				background=ImageIO.read(new File("mainMenuBackground.jpg"));
				while((background.getHeight(null)<UIMain.SCREEN_SIZE.getHeight())||(UIMain.SCREEN_SIZE.getWidth()>background.getWidth(null))) {
					background=background.getScaledInstance((int)(background.getWidth(null)*1.25), (int)(background.getHeight(null)*1.25), Image.SCALE_SMOOTH);
				}
				isSetUp=true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	void transitionInDraw(Graphics g) {
		if(ticks>1) {
		g.drawImage(screenshot, 0, 0, null);
		for(int x=0;x<UIMain.SCREEN_SIZE.width/40+1;x++) {
			for(int y=0;y<UIMain.SCREEN_SIZE.width/40+1;y++) {
				int tileSize=(int) Math.min(40, Math.max(0,ticks-Math.hypot(x,y)));
				g.fillRect(40*x, 40*y, tileSize, tileSize);
			}
		}
		}
	}

	@Override
	void onTransitionInTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void drawMain(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, UIMain.SCREEN_SIZE.width, UIMain.SCREEN_SIZE.height);
		((Graphics2D)(g)).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.min(1f, Math.max(0f,(ticks-10)/(100f)))));
		g.drawImage(background, 0, 0, null);
		if(ticks>110) {
		super.paintComponents(g);
		((Graphics2D)(g)).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.min(1f, Math.max(0f,(210-ticks)/(100f)))));
		g.drawImage(background, 0, 0, null);
		}
	}

	@Override
	void onMainTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void transitionOutDraw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void onTransitionOutTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int getIntroMillisecondLength() {
		// TODO Auto-generated method stub
		return 2000;
	}

	@Override
	int getIntroTickMilliseconds() {
		// TODO Auto-generated method stub
		return 20;
	}

	@Override
	protected int getMainTickMilliseconds() {
		// TODO Auto-generated method stub
		return 40;
	}

	@Override
	protected int getOutroMillisecondLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int getOutroTickMilliseconds() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	void startIntro() {
		// TODO Auto-generated method stub
		
	}
	@Override
	void startMain() {
		//Initialize content objects
		JButton startGame= new JButton("Start");
		JButton rules= new JButton("Rules");
		JButton options= new JButton("Options");
		JButton exit= new JButton("Exit");
		//formats each button
		formatButton(startGame);
		formatButton(rules);
		formatButton(options);
		formatButton(exit);
		//adds each button to a vertical box, centered
		Box buttons= Box.createVerticalBox();
		buttons.setMinimumSize(UIMain.SCREEN_SIZE);
		buttons.setMaximumSize(UIMain.SCREEN_SIZE);
		buttons.setPreferredSize(UIMain.SCREEN_SIZE);
		buttons.add(Box.createVerticalGlue());
		buttons.add(UIMain.centerComponentHorizontal(startGame));
		buttons.add(UIMain.centerComponentHorizontal(rules));
		buttons.add(UIMain.centerComponentHorizontal(options));
		buttons.add(UIMain.centerComponentHorizontal(exit));
		buttons.add(Box.createVerticalGlue());
		//adds to this
		add(UIMain.centerComponentHorizontal(buttons));
		revalidate();
		
		//adds button listeners
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Initialization.end();
			}
		});
		repaint();
	}
	/**
	 * Formats parameter JButton to have the size, style, and attributes desired of a button on the menu
	 * @param b
	 */
	private static void formatButton(JButton b) {
		b.setPreferredSize(new Dimension(200,70));
		b.setFont(UIMain.mainFont.deriveFont(25f));
		b.setBackground(new Color(100,100,100,100));
		b.setForeground(new Color(10,20,30));
		b.setBorder(BorderFactory.createEtchedBorder());
	}
	@Override
	void startOutro() {
		// TODO Auto-generated method stub
		
	}
}
