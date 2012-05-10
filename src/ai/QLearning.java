package ai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import data.MapCell;
import data.QGridCell;
import main.TAIQLearningApp;

public class QLearning{
	private TAIQLearningApp mainApp;
	private QGrid qGrid;
	private double alpha = 0.1;
	private double gamma = 0.9; 
	private double epsilon = 0.2;
	// private agent !
	private MapCell agent;
	private MapCell endState;
	private Random rand = new Random();
	
	
	public QLearning(TAIQLearningApp aMainApp, QGrid qGridMap)  throws UnreachableEndException
	{
		super();
		
		mainApp = aMainApp;
		qGrid = qGridMap;
		agent = aMainApp.getqGridMap().getAgentCell();
		endState = aMainApp.getqGridMap().getEndCell();
	}

	public void setAgent(MapCell agent) 
	{
		this.agent = agent;
	}

	public void startIteration()
	{
		QGridCell currentState = (QGridCell) agent;
		QGridCell nextstate;
		while(agent.getColumnIndex() != endState.getColumnIndex() || agent.getRowIndex() != endState.getRowIndex())
		{
			nextstate = this.getNextAction();
			mainApp.getqGridMap().moveAgent(nextstate.getRowIndex(), nextstate.getColumnIndex());
			this.computeQValue();
			System.out.println(agent.getColumnIndex());
		}
	}
	
	public void startIteration(int nbreIteration)
	{
		for(int i = 0; i < nbreIteration; i++)
		{
			this.startIteration();
		}
	}
	
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
	
	private QGridCell getBestAction()
	{
		return this.getBestAction((QGridCell)agent);
	}
	
	private QGridCell getBestAction(QGridCell cell)
	{
		int index = rand.nextInt(cell.getReachableCells().size());
		ArrayList<MapCell> temp = cell.getReachableCells();
		ArrayList<MapCell> bestCells = new ArrayList<MapCell>();
		double Qmax = 0;
		
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
	
	private void computeQValue()
	{
		QGridCell tcell = (QGridCell) agent;
		double qValue = 0.0;
		QGridCell nextcell = this.getBestAction();
		qValue = tcell.getCellQValue() + alpha * (nextcell.getCellReward() + gamma * (this.getBestAction(nextcell)).getCellQValue() - tcell.getCellQValue());
		tcell.setCellQValue(qValue);
	}

}
