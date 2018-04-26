import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

/**
 * This class is used to transition between screens/scenes in the game
 * @author Egan Johnson
 * Please note: on any children, do NOT call super.paint(g); it will cause recursion. paintComponent is ok
 */
public abstract class Scene extends JPanel{
	protected int phase=-1;// whether the intro, body, or outro is being drawn. 0=intro, 1= body, 2=outro. 
	protected int ticks=0;//how many frames/ticks into the current phase
	private int totalticks=0;//how many frames total have been displayed
	private Timer main= new Timer();
	private TimerTask drawMainTask= new TimerTask() {
		public void run() {
			tick();
			onMainTick();
			repaint();
		}
	};
	public void paint(Graphics g) {
		switch(phase) {
		case 0: transitionInDraw(g); break;
		case 1: drawMain(g); break;
		case 2: transitionOutDraw(g); break;
		}
	}
	
	public void swapIn() {
		ticks=0;
		phase=0;
		Timer t= new Timer();
		TimerTask task= new TimerTask() {
			@Override
			public void run() {
				tick();
				repaint();
				onTransitionInTick();
				if(ticks*getIntroTickMilliseconds()>getIntroMillisecondLength()) {
					ticks=0;
					t.cancel();
					main.schedule(drawMainTask, 0,getMainTickMilliseconds());
					phase=1;
				}
			}
		};
		t.schedule(task, 0,getIntroTickMilliseconds());
	}
	private void tick() {
		ticks++;
		totalticks++;
	}
	public void swapOut(Scene s) {
		main.cancel();
		phase=2;
		ticks=0;
		Timer outro= new Timer();
		TimerTask outTask=new TimerTask() {
			@Override
			public void run() {
				tick();
				onTransitionOutTick();
				repaint();
				if(getOutroMillisecondLength()<getOutroTickMilliseconds()*ticks) {
					outro.cancel();
					s.swapIn();
				}
			}
		};
		outro.schedule(outTask, 0,getOutroTickMilliseconds());
	}
	abstract void transitionInDraw(Graphics g);
	abstract void onTransitionInTick();
	abstract void drawMain(Graphics g);
	abstract void onMainTick();
	abstract void transitionOutDraw(Graphics g);
	/**
	 * put code for when tick increase
	 */
	abstract void onTransitionOutTick();
	/**
	 * determines the length in milliseconds of the intro.
	 * @return
	 */
	abstract protected int getIntroMillisecondLength();
	/**
	 * determines the ticks (milliseconds between repaints) of the intro
	 * @return
	 */
	abstract int getIntroTickMilliseconds();
	abstract protected int getMainTickMilliseconds();
	abstract protected int getOutroMillisecondLength();
	abstract protected int getOutroTickMilliseconds();
	protected int getIntroTicks() {
		return (int)(getIntroMillisecondLength()/getIntroTickMilliseconds())+1;
	}
	protected int getOutroTicks() {
		return (int)(getOutroMillisecondLength()/getOutroTickMilliseconds())+1;
	}
	public int getTotalTicks() {
		return totalticks;
	}
	public int getTicks() {
		return ticks;
	}
}
