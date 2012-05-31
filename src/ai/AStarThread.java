package ai;

import gui.MapGUI;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.SwingWorker;

import main.TAIQLearningApp;

import data.AStarCell;
import data.CellType;
import data.MapCell;


/**
 * The Class AStarThread.
 */
public class AStarThread extends SwingWorker<Void, String> {

	/** The main app. */
	private TAIQLearningApp mainApp;
	
	/** The a star grid. */
	private AStarGrid aStarGrid;
	
	/** The a star path. */
	private ArrayList<AStarCell> aStarPath;
	
	/** The a star set. */
	private ArrayList<AStarCell> aStarSet;
	
	private static final int VISUALIZATIONDELAY = 1;
	
	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected Void doInBackground() throws Exception {
		findAStarPath();
		return null;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#process(java.util.List)
	 */
	protected void process(List<String> outputList) {
        for (String currMsg : outputList) {
        	MapGUI.printOnConsoleAndLog(this.mainApp.getMapGUI().getaStarConsole(),currMsg);
        	this.mainApp.getMapGUI().getaStarPathInfo().setText(MapGUI.PATHINFOTEXT+aStarPath.size());
        	this.mainApp.getMapGUI().getaStarSetInfo().setText(MapGUI.SETINFOTEXT+aStarSet.size());
			
        }
    }

	
	
	/**
	 * Gets the a star grid.
	 *
	 * @return the a star grid
	 */
	public AStarGrid getaStarGrid() {
		return aStarGrid;
	}

	/**
	 * Sets the a star grid.
	 *
	 * @param aStarGrid the new a star grid
	 */
	public void setaStarGrid(AStarGrid aStarGrid) {
		this.aStarGrid = aStarGrid;
	}

	/**
	 * Gets the a star path.
	 *
	 * @return the a star path
	 */
	public ArrayList<AStarCell> getaStarPath() {
		return aStarPath;
	}

	/**
	 * Gets the a star set.
	 *
	 * @return the a star set
	 */
	public ArrayList<AStarCell> getaStarSet() {
		return aStarSet;
	}

	/**
	 * Instantiates a new a star thread.
	 *
	 * @param aMainApp the a main app
	 */
	public AStarThread(TAIQLearningApp aMainApp) {
		super();
		mainApp = aMainApp;
		aStarGrid = new AStarGrid(aMainApp.getqGridMap());
		aStarSet = new ArrayList<AStarCell>();
		aStarPath = new ArrayList<AStarCell>();
		
	}



	/**
	 * Compute heuristic.
	 *
	 * @param aCell the a cell
	 */
	private void computeHeuristic(AStarCell aCell){
		switch(aCell.getCellType()){
		case PORTAL1:
		case PORTAL2:
		case PORTAL3:
		case PORTAL4: aCell.setHeuristicDistanceFromGoal(euclidianDistance(aCell, aStarGrid.getEndCell()));
					  break;
			
		default: aCell.setHeuristicDistanceFromGoal(euclidianDistance(aCell, aStarGrid.getEndCell()));
		         break;
		}
	}
	
	/**
	 * Euclidian distance.
	 *
	 * @param aCell1 the a cell1
	 * @param aCell2 the a cell2
	 * @return the double
	 */
	private double euclidianDistance(MapCell aCell1, MapCell aCell2){
		int heightDiff = Math.abs(aCell1.getRowIndex() - aCell2.getRowIndex());
		int widthDiff = Math.abs(aCell1.getColumnIndex() - aCell2.getColumnIndex());
		return Math.sqrt(Math.pow(heightDiff, 2) + Math.pow(widthDiff, 2));
	}
	

	/**
	 * Find a star path.
	 */
	public void findAStarPath(){
		
		if(aStarGrid.getStepsFromAgentToEnd() == -1){
			return;
		}
		
        ArrayList<AStarCell> searchQueue = new ArrayList<AStarCell>();
        int distanceFromAgent = 1;
        
        for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				aStarGrid.getCell(i, j).addPortalReachableCells(aStarGrid.getaStarGrid(), aStarGrid.getPortalReachableCells());
				aStarGrid.getCell(i, j).setHasBeenVisited(false);
				
			}
		}
        
        //Recursive application of A*
        publish("A* Set:");
        selectNextCell(aStarGrid.getAgentCell(), searchQueue);
     
        buildAStarPath();
	}
	
	/**
	 * Select next cell.
	 *
	 * @param currCell the curr cell
	 * @param searchQueue the search queue
	 */
	private void selectNextCell(AStarCell currCell,ArrayList<AStarCell> searchQueue){
		double minHeuristic = Double.MAX_VALUE;
		AStarCell nextCell = null;
		
		//Remove cell from the candidate set and add it to the set of cells explored by A*
		searchQueue.remove(currCell);
		currCell.visit();
		aStarSet.add(currCell);
		
		if(currCell.equals(aStarGrid.getEndCell())){
			return;
		}
		
		publish(currCell.toString() + " - d: " + currCell.getDistanceFromAgent() + " - h:" +currCell.getHeuristicDistanceFromGoal());
		
		//Compute heuristic value for all the cells adjacent to currCell and add them to candidate set if not visited.
		for(MapCell iterCell : currCell.getReachableCells()){
        	AStarCell aCell = (AStarCell)iterCell;
        	computeHeuristic(aCell);
        	if(!aCell.hasBeenVisited()){
        		searchQueue.add(aCell);
        		aCell.setPreviousAPathCell(currCell);
        	}
        }
		
		
        //Pop a cell and recall the algorithm on it (Min heuristic value is the criterion for the choice)
		for(AStarCell iterCell: searchQueue){
			if(!(iterCell.hasBeenVisited()) && iterCell.getCellType() != CellType.AGENT){
				if(iterCell.getDistanceFromAgent()+iterCell.getHeuristicDistanceFromGoal() < minHeuristic){
					minHeuristic = iterCell.getDistanceFromAgent()+iterCell.getHeuristicDistanceFromGoal();
					nextCell = iterCell;
				}
			}
		}
		
		try {
			Thread.sleep(VISUALIZATIONDELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		selectNextCell(nextCell,searchQueue);
	}
	
	/**
	 * Builds the a star path.
	 */
	private void buildAStarPath(){
		//Determine the path by going backwards from end to beginning following the chain of previousAPathCell pointers.
		publish("A* Path:");
		AStarCell currCell = aStarGrid.getEndCell();
         
		 while(currCell.getCellType() != CellType.AGENT){
                 aStarPath.add(currCell);
                 publish(currCell.toString());
                 try {
         			Thread.sleep(VISUALIZATIONDELAY);
         		} catch (InterruptedException e) {
         			
         			e.printStackTrace();
         		}
                 currCell = currCell.getPreviousAPathCell();
         }
		
	}
	

}
