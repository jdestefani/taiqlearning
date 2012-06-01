package ai;

import gui.MapGUI;
import main.TAIQLearningApp;

// TODO: Auto-generated Javadoc
/**
 * The Class QLearnThread.
 */
public class QLearnThread extends Thread 
{
	
	/** The main app. */
	private TAIQLearningApp mainApp;
	
	/** The q grid. */
	private QGrid qGrid;
	
	/** The my thread. */
	private QLearning myThread;
	
	/** The repeat. */
	private int repeat;
	
	/** The repeating. */
	private boolean repeating;
	
	/**
	 * Instantiates a new q learn thread.
	 *
	 * @param aMainApp the a main app
	 * @param qGridMap the q grid map
	 */
	public QLearnThread(TAIQLearningApp aMainApp, QGrid qGridMap)
	{
		super();
		
		mainApp = aMainApp;
		qGrid = qGridMap;
		repeat = 100;
	}

	/**
	 * Gets the repeat.
	 *
	 * @return the repeat
	 */
	public int getRepeat() {
		return repeat;
	}

	/**
	 * Sets the repeat.
	 *
	 * @param repeat the new repeat
	 */
	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}
	
	/**
	 * Stop iteration.
	 */
	public void stopIteration()
	{
		this.repeating = false;
	}

	/**
	 * Sets the repeating.
	 *
	 * @param repeating the new repeating
	 */
	public void setRepeating(boolean repeating) {
		this.repeating = repeating;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run()
	{
		repeating = true;
		int count = repeat;
		
		this.mainApp.getMapGUI().getDisplayQLearnStopButton().setEnabled(true);
		this.mainApp.getMapGUI().getDisplayQResetButton().setEnabled(true);
		
		MapGUI.printOnConsoleAndLog(this.mainApp.getMapGUI().getqLearnConsole(),"############## Q-Learning ##############");
		
		for(int i = 0; (i < count) && repeating; i++)
		{
			MapGUI.printOnConsoleAndLog(this.mainApp.getMapGUI().getqLearnConsole(),"Iteration "+(i+1)+" started.");
			myThread = new QLearning(mainApp, qGrid);
			myThread.start();
			try {
				myThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			myThread.reset();
			
		}
		
		this.mainApp.getMapGUI().getDisplayQLearnStopButton().setEnabled(false);
		this.mainApp.getMapGUI().getDisplayQResetButton().setEnabled(false);
	}

}
