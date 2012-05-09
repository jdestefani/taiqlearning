package ai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import data.MapCell;
import data.QGrid;
import data.QGridCell;
import main.TAIQLearningApp;

public class QLearning {
	private TAIQLearningApp mainApp;
	private QGrid qGrid;
	private double alpha= 0.1;
	private double gamma=0.9; 
	private double epsilon=0.2;
	// private agent !
	private MapCell agent;
	private Random rand = new Random();
	
	
	
	public void setAgent(MapCell agent) {
		this.agent = agent;
	}

	public QLearning(TAIQLearningApp aMainApp, QGrid aQGrid) {
		super();
		
		mainApp = aMainApp;
		qGrid = aQGrid;
		
	}
	
	public void start()
	{
		;
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
		int index = rand.nextInt(agent.getReachableCells().size());
		ArrayList<MapCell> temp = agent.getReachableCells();
		ArrayList<MapCell> bestCells;
		double Qmax = 0;
		
		Iterator<MapCell> itr = temp.iterator();
		while(itr.hasNext())
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
			int index = rand.nextInt(bestCells.size());
			return (QGridCell) bestCells.get(index);
		}
	}

}
