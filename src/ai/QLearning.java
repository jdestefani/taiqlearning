package ai;

import gui.MapGUI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import data.MapCell;
import data.QGridCell;
import main.TAIQLearningApp;

/**
 * The Class QLearning.
 */
public class QLearning extends Thread 
{
	
	/** The main app. */
	private TAIQLearningApp mainApp;
	
	/** The q grid. */
	private QGrid qGrid;
	
	/** The alpha. */
	private double alpha = 0.2;
	
	/** The gamma. */
	private double gamma = 0.99; 
	
	/** The epsilon. */
	private double epsilon = 0.2;
	// private agent !
	/** The agent. */
	private QGridCell agent;
	
	/** The end state. */
	private QGridCell endState;
	
	/** The rand. */
	private Random rand = new Random();
	
	/** The Constant MAXTRIALSNUMBER. */
	public static final int MAXTRIALSNUMBER = 100;
	
	/** The Constant MINTRIALSNUMBER. */
	public static final int MINTRIALSNUMBER = 0;
	
	
	/**
	 * Instantiates a new q learning.
	 *
	 * @param aMainApp the a main app
	 * @param qGridMap the q grid map
	 * @throws UnreachableEndException the unreachable end exception
	 */
	public QLearning(TAIQLearningApp aMainApp, QGrid qGridMap) throws UnreachableEndException
	{
		super();
		
		mainApp = aMainApp;
		qGrid = qGridMap;
		agent = (QGridCell)qGrid.getAgentCell();
		endState = (QGridCell)qGrid.getEndCell();
	}

	/**
	 * Sets the agent.
	 *
	 * @param agent the new agent
	 */
	public void setAgent(QGridCell agent) 
	{
		this.agent = agent;
	}

	/**
	 * Start iteration.
	 */
	public void startIteration()
	{
		QGridCell laststate; QGridCell nextstate;
		int movesNumber = 0;
		qGrid.resetVisited();

		
		while(agent.getColumnIndex() != endState.getColumnIndex() || agent.getRowIndex() != endState.getRowIndex())
		{
			laststate = (QGridCell) qGrid.getAgentCell();
			nextstate = this.getNextAction();
			qGrid.moveAgent(nextstate.getRowIndex(), nextstate.getColumnIndex());
			agent = (QGridCell)qGrid.getAgentCell();
			agent.setHasBeenVisited(true);
			qGrid.getVisitedCells().add(agent);
			this.computeQValue();

//			mainApp.getMapGUI().refreshTwoCells(laststate, nextstate);
//			try {
//				this.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			movesNumber++;
		}
		MapGUI.printOnConsoleAndLog(this.mainApp.getMapGUI().getqLearnConsole(),"Iteration completed in "+movesNumber+" steps");
		this.mainApp.getMapGUI().getqLearningVisitedInfo().setText(MapGUI.SETINFOTEXT+qGrid.getVisitedCells().size());
		if(!this.mainApp.getMapGUI().getShowQVisitedSet().isEnabled()){
			this.mainApp.getMapGUI().getShowQVisitedSet().setEnabled(true);
		}
	}
	
	public void calculateBestPath() throws QLearningLoopException
	{
		QGridCell laststate; QGridCell nextstate;
		int movesNumber = 0;
		qGrid.resetVisited();

		while((agent.getColumnIndex() != endState.getColumnIndex() || agent.getRowIndex() != endState.getRowIndex()) && movesNumber < 1000)
		{
			laststate = (QGridCell) qGrid.getAgentCell();
			nextstate = this.getBestAction();
			qGrid.moveAgent(nextstate.getRowIndex(), nextstate.getColumnIndex());
			agent = (QGridCell)qGrid.getAgentCell();
			agent.setHasBeenVisited(true);
			qGrid.getVisitedCells().add(agent);

			mainApp.getMapGUI().refreshTwoCells(laststate, nextstate);
//			try {
//				this.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			movesNumber++;
		}
		if(movesNumber < 1000)
		{
			MapGUI.printOnConsoleAndLog(this.mainApp.getMapGUI().getqLearnConsole(),"Best path found in "+movesNumber+" steps");
			this.mainApp.getMapGUI().getqLearningPathInfo().setText(MapGUI.PATHINFOTEXT+movesNumber);
			this.mainApp.getMapGUI().getShowQVisitedPath().setEnabled(true);
		}
		else
		{
			MapGUI.printOnConsoleAndLog(this.mainApp.getMapGUI().getqLearnConsole(),"Could not find the best path, it's cycling with the current QValues.");
			throw new QLearningLoopException("Could not find the best path.\nAgent cycling with the current QValues.");
			
		}
		//MapGUI.printOnConsoleAndLog(this.mainApp.getMapGUI().getqLearnConsole(),"Ok done");
		
		this.reset();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run()
	{
		this.startIteration();
	}
	
	/**
	 * Reset.
	 */
	public void reset()
	{
		QGridCell initialPos = qGrid.getInitAgentCell();
		QGridCell actualPos = (QGridCell)qGrid.getAgentCell();
		qGrid.moveAgent(initialPos.getRowIndex(), initialPos.getColumnIndex());
		mainApp.getMapGUI().refreshTwoCells(initialPos, actualPos);
		ArrayList<MapCell> bonusCells = new ArrayList<MapCell>();
		bonusCells.addAll(qGrid.getBonusCells());

		qGrid.resetBonus();
		mainApp.getMapGUI().refreshMap(bonusCells);
	}
	
	/**
	 * Start iteration.
	 *
	 * @param nbreIteration the nbre iteration
	 */
	public void startIteration(int nbreIteration)
	{
		for(int i = 0; i < nbreIteration; i++)
		{
			//qGrid.resetAgent();
			reset();
			this.startIteration();
		}
	}
	
	
	
	/**
	 * Gets the next action.
	 *
	 * @return the next action
	 */
	private QGridCell getNextAction()
	{
		if(Math.random() < epsilon) //choose randomly an action with a probability epsilon
		{
			int index = rand.nextInt(agent.getReachableCells().size());
			return (QGridCell) agent.getReachableCells().get(index);
		}
		else
		{
			return getBestAction();
		}
	}
	
	/**
	 * Gets the best action.
	 *
	 * @return the best action
	 */
	private QGridCell getBestAction()
	{
		return this.getBestAction((QGridCell)agent);
	}
	
	/**
	 * Gets the best action.
	 *
	 * @param cell the cell
	 * @return the best action
	 */
	private QGridCell getBestAction(QGridCell cell)
	{
		int index = rand.nextInt(cell.getReachableCells().size());
		ArrayList<MapCell> temp = cell.getReachableCells();
		ArrayList<MapCell> bestCells = new ArrayList<MapCell>();
		double Qmax = -1000000.0;
		
		Iterator<MapCell> itr = temp.iterator();
		while(itr.hasNext()) //find the cell with the best QValue
		{
			QGridCell tCell = (QGridCell)itr.next();
			
			if(tCell.getCellQValue() < Qmax)
				continue;
			
			if(tCell.getCellQValue() == Qmax)
			{
				bestCells.add(tCell);
			}
			else
				if(tCell.getCellQValue() > Qmax)
				{
					bestCells.clear();
					bestCells.add(tCell);
					Qmax = tCell.getCellQValue();
				}
		}
		
		if(bestCells.size() == 1)
		{
			return (QGridCell) bestCells.get(0);
		}
		else
		{
			int randint = rand.nextInt(bestCells.size());
			return (QGridCell) bestCells.get(randint);
		}
	}
	
	/**
	 * Compute q value.
	 */
	private void computeQValue()
	{
		QGridCell tcell = (QGridCell) agent;
		double qValue = 0.0;
		if(tcell != qGrid.getEndCell())
		{
			QGridCell nextcell = this.getBestAction();
			qValue = tcell.getCellQValue() + alpha * (nextcell.getCellReward() + gamma * (this.getBestAction(nextcell)).getCellQValue() - tcell.getCellQValue());
			tcell.setCellQValue(qValue);
		}
		
	}

}
