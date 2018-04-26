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
	/**
	 * Automatically draws either intro, outro, or main
	 */
	public void paint(Graphics g) {
		switch(phase) {
		case 0: transitionInDraw(g); break;
		case 1: drawMain(g); break;
		case 2: transitionOutDraw(g); break;
		}
	}
	/**
	 * starts the intro
	 */
	public void swapIn() {
		ticks=0;
		phase=0;
		startIntro();
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
					startMain();
					phase=1;
				}
			}
		};
		t.schedule(task, 0,getIntroTickMilliseconds());
	}
	/**
	 * increases ticks by 1
	 */
	private void tick() {
		ticks++;
		totalticks++;
	}
	/**
	 * Plays the outro of this one, and when done, plays intro of the other one. Also automatically replaces this scene with the other scene on the jframe
	 * @param s
	 */
	public void swapOut(Scene s) {
		main.cancel();
		startOutro();
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
					UIMain.frame.remove(UIMain.scene);
					UIMain.scene=s;
					UIMain.frame.add(s);
					s.swapIn();
				}
			}
		};
		outro.schedule(outTask, 0,getOutroTickMilliseconds());
	}
	/**
	 * Any code that needs to be done on the start of the introduction
	 */
	abstract void startIntro();
	/**
	 * any code that needs to happen at the start of the introduction
	 */
	abstract void startMain();
	/**
	 * any code that needs to happen at the start of the outro
	 */
	abstract void startOutro();
	/**
	 * put graphics code in for the intro
	 */
	abstract void transitionInDraw(Graphics g);
	/**
	 * called whenever the intro ticks happens
	 */
	abstract void onTransitionInTick();
	/**
	 * put graphics code in for the body
	 */
	abstract void drawMain(Graphics g);
	/**
	 * called whenever the body ticks/repaints
	 */
	abstract void onMainTick();
	/**
	 * put graphics code for outro
	 */
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
	/**
	 *@return the millisecond length of each tick. What you put here determines the tick length. 
	 */
	abstract protected int getMainTickMilliseconds();
	/**
	 * @return the millisecond length of the outro. What you put here determines the run time of the outro
	 */
	abstract protected int getOutroMillisecondLength();
	abstract protected int getOutroTickMilliseconds();
	/**
	 * @return how many times the timer will call the intro
	 */
	protected int getIntroTicks() {
		return (int)(getIntroMillisecondLength()/getIntroTickMilliseconds())+1;
	}
	/**
	 * @return how many times the timer will call the outro code
	 */
	protected int getOutroTicks() {
		return (int)(getOutroMillisecondLength()/getOutroTickMilliseconds())+1;
	}
	/**
	 * @return how many ticks have happened so far in the combined intro, outro, and body
	 */
	public int getTotalTicks() {
		return totalticks;
	}
	/**
	 * @return the ticks in the phase (resets to zero at end of intro or at beginning of outro
	 */
	public int getTicks() {
		return ticks;
	}
}
