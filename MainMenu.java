import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MainMenu extends Scene {
	Image screenshot;
	static Image background;
	static boolean isSetUp=false;
	MainMenu(){
		screenshot=UIMain.getScreenshot();
		if(!isSetUp) {
			//loads in images
			try {
				background=ImageIO.read(new File("mainMenuBackground.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	void transitionInDraw(Graphics g) {
		g.drawImage(screenshot, 0, 0, null);
		Polygon p = new Polygon();
		p.addPoint(0, 0);
		p.addPoint(getWidth(), 0);
		p.addPoint(getWidth(), getHeight());
		p.addPoint(0, getHeight());
		//add points of center box. (Radius-constant*ticks)*cos(startingAngle*constant*ticks)+centercoords,(Radius-constant*ticks)*sin(startingAngle*constant*ticks)+centercoords,
		double radiusShrink=(UIMain.SCREEN_SIZE.getWidth()/2)/getIntroTicks();
		double angleUp=0.03;
		int radius=(int) (getWidth()/2-ticks*radiusShrink);
		//bottom left
		p.addPoint((int)(radius*Math.cos(5*Math.PI/4+angleUp*ticks))+getWidth()/2,(int)(radius*Math.sin(5*Math.PI/4+angleUp*ticks))+getHeight()/2);
		//bottom right
		p.addPoint((int)(radius*Math.cos(7*Math.PI/4+angleUp*ticks))+getWidth()/2,(int)(radius*Math.sin(7*Math.PI/4+angleUp*ticks))+getHeight()/2);
		//top right
		p.addPoint((int)(radius*Math.cos(1*Math.PI/4+angleUp*ticks))+getWidth()/2,(int)(radius*Math.sin(1*Math.PI/4+angleUp*ticks))+getHeight()/2);
		//top left
		p.addPoint((int)(radius*Math.cos(3*Math.PI/4+angleUp*ticks))+getWidth()/2,(int)(radius*Math.sin(3*Math.PI/4+angleUp*ticks))+getHeight()/2);
		//close box with bottom left point again
		p.addPoint((int)(radius*Math.cos(5*Math.PI/4+angleUp*ticks))+getWidth()/2,(int)(radius*Math.sin(5*Math.PI/4+angleUp*ticks))+getHeight()/2);
		//close polygon
		p.addPoint(0, getHeight());
		g.fillPolygon(p);
	}

	@Override
	void onTransitionInTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void drawMain(Graphics g) {
		System.out.println(ticks);
		g.fillRect(0,0, getWidth(), getHeight());
		((Graphics2D)(g)).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.min(1f, Math.max(0f,4*ticks/(100f)))));
		g.drawImage(background, 0, 0, null);
		super.paintComponents(g);
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
		return 1000;
	}

	@Override
	int getIntroTickMilliseconds() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	protected int getMainTickMilliseconds() {
		// TODO Auto-generated method stub
		return 10;
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
}
