package ai;

import gui.MapGUI;
import main.TAIQLearningApp;

public class QLearnThread extends Thread 
{
	private TAIQLearningApp mainApp;
	private QGrid qGrid;
	private QLearning myThread;
	private int repeat;
	private boolean repeating;
	
	public QLearnThread(TAIQLearningApp aMainApp, QGrid qGridMap)
	{
		super();
		
		mainApp = aMainApp;
		qGrid = qGridMap;
		repeat = 100;
	}

	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}
	
	public void stopIteration()
	{
		this.repeating = false;
	}

	public void setRepeating(boolean repeating) {
		this.repeating = repeating;
	}
	
	public void run()
	{
		repeating = true;
		int count = repeat;
		
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
	}

}
