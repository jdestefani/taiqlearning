/*
 * 
 */
package ai;

import gui.MapGUI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Logger;

import main.TAIQLearningApp;

import data.AStarCell;
import data.CellType;
import data.MapCell;
import data.QGridCell;

/**
 * The Class AStarPathFinder.
 */
public class AStarPathFinder implements GameGrid {
	
	/** The main app. */
	private TAIQLearningApp mainApp;
	
	/** The portal reachable cells. */
	private ArrayList<MapCell>[] portalReachableCells;
	
	/** The portal cells. */
	private ArrayList<AStarCell>[] portalCells;
	
	/** The max distance portal index. */
	private int[] maxDistancePortalIndex;
	
	/** The min distance portal index. */
	private int[] minDistancePortalIndex;
	
	/** The portal discovery order. */
	private int[] portalDiscoveryOrder;
	
	/** The a star grid. */
	private AStarCell[][] aStarGrid;
	
	/** The agent cell. */
	private AStarCell agentCell;
	
	/** The end cell. */
	private AStarCell endCell;
	
	/** The steps from agent to end. */
	private int stepsFromAgentToEnd;
	
	/** The a star path. */
	private ArrayList<AStarCell> aStarPath;
	
	/** The a star set. */
	private ArrayList<AStarCell> aStarSet;
	
	
	/**
	 * Instantiates a new a star path finder.
	 *
	 * @param aMainApp the a main app
	 * @param aQGrid the a q grid
	 * @throws UnreachableEndException the unreachable end exception
	 */
	public AStarPathFinder(TAIQLearningApp aMainApp,QGrid aQGrid) throws UnreachableEndException {
		super();
		
		mainApp = aMainApp;
		stepsFromAgentToEnd = -1;
		aStarSet = new ArrayList<AStarCell>();
		aStarPath = new ArrayList<AStarCell>();
		aStarGrid = new AStarCell[QGrid.MAPHEIGHT][QGrid.MAPWIDTH];
		
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				aStarGrid[i][j] = new AStarCell(i,j);
			}
		}
		
		portalReachableCells = new ArrayList[QGrid.PORTALNUMBER];
		portalCells = new ArrayList[QGrid.PORTALNUMBER];
		maxDistancePortalIndex = new int[QGrid.PORTALNUMBER];
		minDistancePortalIndex = new int[QGrid.PORTALNUMBER];
		portalDiscoveryOrder = new int[QGrid.PORTALNUMBER];
		
		for(int i=0; i<QGrid.PORTALNUMBER ; i++){
			portalReachableCells[i] = new ArrayList<MapCell>();
			portalCells[i] = new ArrayList<AStarCell>();
			maxDistancePortalIndex[i] = 0;
			minDistancePortalIndex[i] = 0;
			portalDiscoveryOrder[i] = i;
		}
		
		importQGridMap(aQGrid.getGrid());
		
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				this.getCell(i, j).addDefaultReachableCells(aStarGrid);
			}
		}
		
		updatePortalReachableCells();
		
		
		//Detection of the furthest and closest portals to the agent.
		computeDistanceFrom(agentCell);
		
		
		if(stepsFromAgentToEnd == -1){
			throw new UnreachableEndException();
		}
		
		for(int i = 0; i<QGrid.PORTALNUMBER ; i++){
			for(int j=0; j< portalCells[i].size(); j++){
				if(portalCells[i].get(j).getDistanceFromAgent() < portalCells[i].get(minDistancePortalIndex[i]).getDistanceFromAgent()){
					minDistancePortalIndex[i] = j;
				}
				if(portalCells[i].get(j).getDistanceFromAgent() >= portalCells[i].get(maxDistancePortalIndex[i]).getDistanceFromAgent()){
					maxDistancePortalIndex[i] = j;
				}	
			}
			portalCells[i].get(maxDistancePortalIndex[i]).setDistanceFromAgent(portalCells[i].get(minDistancePortalIndex[i]).getDistanceFromAgent());	
		}
	
		//Sorting of the update order of the portals according to the distance
		//from the agent
		int swapElement = 0;
		for(int j = 0; j<QGrid.PORTALNUMBER ; j++){
			for(int i = 0; i<QGrid.PORTALNUMBER - 1 ; i++){
				if(portalCells[i].get(minDistancePortalIndex[i]).getDistanceFromAgent() <= portalCells[i+1].get(minDistancePortalIndex[i+1]).getDistanceFromAgent()){
					swapElement = portalDiscoveryOrder[i];
					portalDiscoveryOrder[i] = portalDiscoveryOrder[i+1];
					portalDiscoveryOrder[i+1] = swapElement;
				
				}
			}
			
		}
		
		//Update of the distance
		for(int i = 0; i<QGrid.PORTALNUMBER ; i++){
			for(int k=0; k<QGrid.MAPHEIGHT ; k++){
				for(int j=0 ; j<QGrid.MAPWIDTH; j++){
					this.getCell(k, j).setHasBeenVisited(false);
				}
			}
		
			updateDistance((portalCells[portalDiscoveryOrder[i]].get(maxDistancePortalIndex[portalDiscoveryOrder[i]])));
			
		}
	}

	/**
	 * Gets the portal reachable cells.
	 *
	 * @return the portal reachable cells
	 */
	public ArrayList<MapCell>[] getPortalReachableCells() {
		return portalReachableCells;
	}

	/**
	 * Gets the a star grid.
	 *
	 * @return the a star grid
	 */
	public MapCell[][] getaStarGrid() {
		return aStarGrid;
	}

	/* (non-Javadoc)
	 * @see ai.GameGrid#getAgentCell()
	 */
	public MapCell getAgentCell() {
		return agentCell;
	}
	/* (non-Javadoc)
	 * @see ai.GameGrid#getEndCell()
	 */
	@Override
	public MapCell getEndCell() {
		return endCell;
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
	 * Gets the a star path.
	 *
	 * @return the a star path
	 */
	public ArrayList<AStarCell> getaStarPath() {
		return aStarPath;
	}

	/**
	 * Gets the steps from agent to end.
	 *
	 * @return the steps from agent to end
	 */
	public int getStepsFromAgentToEnd() {
		return stepsFromAgentToEnd;
	}


	/**
	 * Gets the cell.
	 *
	 * @param aRowIndex the a row index
	 * @param aColumnIndex the a column index
	 * @return the cell
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 */
	public AStarCell getCell(int aRowIndex, int aColumnIndex) throws IndexOutOfBoundsException{
		MapCell.verifyIndexes(aRowIndex, aColumnIndex);
		return aStarGrid[aRowIndex][aColumnIndex];
	}

	/**
	 * Import q grid map.
	 *
	 * @param aQGrid the a q grid
	 */
	public void importQGridMap(MapCell aQGrid[][]){
		
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				this.getCell(i, j).setCellType(aQGrid[i][j].getCellType());			
				switch(this.getCell(i, j).getCellType()){
				case AGENT:agentCell = this.getCell(i, j);
						   agentCell.setDistanceFromAgent(0);
						   break;
				case PORTAL1:portalCells[0].add(this.getCell(i, j));
							 break;
				case PORTAL2:portalCells[1].add(this.getCell(i, j));
				 			 break;
				case PORTAL3:portalCells[2].add(this.getCell(i, j));
				 			 break;
				case PORTAL4:portalCells[3].add(this.getCell(i, j));
				 			 break;
				case ENDPOINT:endCell = this.getCell(i, j);
							  break;
				default:break;
				}
			}
		}
		
		
	}
	
	/**
	 * Update portal reachable cells.
	 */
	private void updatePortalReachableCells(){
		
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				switch(this.getCell(i, j).getCellType()){
				case PORTAL1:for(MapCell iterCell : this.getCell(i,j).getReachableCells()){
								 if(!(portalReachableCells[0].contains(iterCell))){
									portalReachableCells[0].add(iterCell); 
								 }
							 }
							 break;
				case PORTAL2:portalCells[1].add(this.getCell(i, j));
							 for(MapCell iterCell : this.getCell(i,j).getReachableCells()){
								 if(!(portalReachableCells[1].contains(iterCell))){
									 portalReachableCells[1].add(iterCell); 
								 }
							 }
				 			 break;
				case PORTAL3:portalCells[2].add(this.getCell(i, j));
							 for(MapCell iterCell : this.getCell(i,j).getReachableCells()){
								 if(!(portalReachableCells[2].contains(iterCell))){
									 portalReachableCells[2].add(iterCell); 
								 }
							 }
				 			 break;
				case PORTAL4:portalCells[3].add(this.getCell(i, j));
							 for(MapCell iterCell : this.getCell(i,j).getReachableCells()){
								 if(!(portalReachableCells[3].contains(iterCell))){
									 portalReachableCells[3].add(iterCell); 
								 }
							 }
				 			 break;
				default:break;
				}
			}
		}
		
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				this.getCell(i, j).addPortalReachableCells(aStarGrid, portalReachableCells);
			}
		}
			
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
		case PORTAL4: aCell.setHeuristicDistanceFromGoal(euclidianDistance(aCell, endCell));
					  break;
			
		default: aCell.setHeuristicDistanceFromGoal(euclidianDistance(aCell, endCell));
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
		
		if(stepsFromAgentToEnd == -1){
			return;
		}
		
        ArrayList<AStarCell> searchQueue = new ArrayList<AStarCell>();
        int distanceFromAgent = 1;
        
        for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				this.getCell(i, j).addPortalReachableCells(aStarGrid, portalReachableCells);
				this.getCell(i, j).setHasBeenVisited(false);
				
			}
		}
        
        //Recursive application of A*
        MapGUI.printOnConsoleAndLog(this.mainApp.getMapGUI().getaStarConsole(),"A* Set:");
        selectNextCell(agentCell, searchQueue);
     
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
		
		if(currCell.equals(endCell)){
			return;
		}
		
		MapGUI.printOnConsoleAndLog(this.mainApp.getMapGUI().getaStarConsole(),currCell.toString() + " - d: " + currCell.getDistanceFromAgent() + " - h:" +currCell.getHeuristicDistanceFromGoal());
		
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
		
		selectNextCell(nextCell,searchQueue);
	}
	
	/**
	 * Builds the a star path.
	 */
	private void buildAStarPath(){
		/*AStarCell currCell = agentCell;
		AStarCell nextCell = null;
		ArrayList<AStarCell> adjAStarCells = new ArrayList<AStarCell>();
		ArrayList<AStarCell> visitedCells = new ArrayList<AStarCell>();
		int minManhattanDistance = Integer.MAX_VALUE;
		int currDistanceFromAgent = 0;*/
		
		//Determine the path by going backwards from end to beginning following the chain of previousAPathCell pointers.
		Logger.getLogger(TAIQLearningApp.LOGGERNAME).info("A* Path:");
		 AStarCell currCell = endCell;
         while(currCell.getCellType() != CellType.AGENT){
                 aStarPath.add(currCell);
                 MapGUI.printOnConsoleAndLog(this.mainApp.getMapGUI().getaStarConsole(),currCell.toString());
                 currCell = currCell.getPreviousAPathCell();
         }
		
		
		/*while(currCell.getCellType() != CellType.ENDPOINT){
			visitedCells.add(currCell);
			aStarPath.add(currCell);
			System.out.println(currCell);
			adjAStarCells = new ArrayList<AStarCell>();
			System.gc();
			minManhattanDistance = Integer.MAX_VALUE;
			nextCell = null;
			
			
			for(MapCell iterCell : currCell.getReachableCells()){
				if(aStarSet.contains((AStarCell)iterCell) && ((AStarCell)iterCell).getDistanceFromAgent() > currDistanceFromAgent && !visitedCells.contains((AStarCell)iterCell)){
					adjAStarCells.add(aStarSet.get(aStarSet.indexOf((AStarCell)iterCell)));
				}
			}
			for(MapCell iterCell : adjAStarCells){
				System.out.println(iterCell + " - h: " + ((AStarCell)iterCell).getDistanceFromAgent()+((AStarCell)iterCell).getHeuristicDistanceFromGoal());
				if( stepsFromAgentToEnd - ((AStarCell)iterCell).getDistanceFromAgent() < minManhattanDistance ){
					minManhattanDistance = ((AStarCell)iterCell).getDistanceFromAgent()+((AStarCell)iterCell).getHeuristicDistanceFromGoal();
					nextCell = ((AStarCell)iterCell);
				}
			}
			aStarSet.remove(currCell);
			currCell = nextCell;
			currDistanceFromAgent = nextCell.getDistanceFromAgent();
		}*/
	}
	
	/**
	 * Compute distance from.
	 *
	 * @param aCell the a cell
	 */
	private void computeDistanceFrom(AStarCell aCell){
		AStarCell[] cellsToVisit = new AStarCell[QGrid.MAPHEIGHT*QGrid.MAPWIDTH];
		int index = 0;
		int insertionPoint = 0;
		cellsToVisit[insertionPoint++] = (aCell);
		AStarCell currCell = cellsToVisit[index];
		
		do{
			//1. Visit current cell
			currCell.visit();
			
			//2. Update neighbour distance
			for(MapCell iterCell : currCell.getReachableCells()){
				if(((AStarCell)iterCell).getPreviousAPathCell() == null && ((MapCell)iterCell).getCellType() != CellType.AGENT){
					cellsToVisit[insertionPoint++] = ((AStarCell)iterCell);
					((AStarCell)iterCell).setDistanceFromAgent(currCell.getDistanceFromAgent()+1);
					((AStarCell)iterCell).setPreviousAPathCell(currCell);
				}
				
				if(((MapCell)iterCell).getCellType() == CellType.ENDPOINT){
					stepsFromAgentToEnd = ((AStarCell)iterCell).getDistanceFromAgent();
				}
			}
			
			
			//3. Explore next cell
			currCell = cellsToVisit[++index];
			
		}while(index != insertionPoint);
		
		
	}
	
	/**
	 * Update distance.
	 *
	 * @param aCell the a cell
	 */
	private void updateDistance(AStarCell aCell){
		AStarCell[] cellsToVisit = new AStarCell[QGrid.MAPHEIGHT*QGrid.MAPWIDTH];
		int index = 0;
		int insertionPoint = 0;
		int distanceFromCell = 0;
		cellsToVisit[insertionPoint++] = (aCell);
		AStarCell currCell = cellsToVisit[index];
		
		do{
			//1. Visit current cell
			currCell.visit();
			distanceFromCell = currCell.getDistanceFromAgent();
			
			//2. Update neighbour distance
			for(MapCell iterCell : currCell.getReachableCells()){
				if(distanceFromCell+1 < ((AStarCell)iterCell).getDistanceFromAgent() && ((MapCell)iterCell).getCellType() != CellType.AGENT){
					cellsToVisit[insertionPoint++] = ((AStarCell)iterCell);
					((AStarCell)iterCell).setDistanceFromAgent(currCell.getDistanceFromAgent()+1);
					((AStarCell)iterCell).setPreviousAPathCell(currCell);
				}
				
				if(((MapCell)iterCell).getCellType() == CellType.ENDPOINT){
					stepsFromAgentToEnd = ((AStarCell)iterCell).getDistanceFromAgent();
				}
			}
			
			
			//3. Explore next cell
			currCell = cellsToVisit[++index];
			
		}while(index != insertionPoint);
		/*currCell.visit();
			
		for(MapCell iterCell : currCell.getReachableCells()){
        	AStarCell aCell = (AStarCell)iterCell;
        	if(distanceFromCell+1 < aCell.getDistanceFromAgent() && aCell.getCellType() != CellType.AGENT){
        		aCell.setDistanceFromAgent(distanceFromCell+1);
        		aCell.setPreviousAPathCell(currCell);
        	}
        }
		
		for(MapCell iterCell : currCell.getReachableCells()){
        	AStarCell aCell = (AStarCell)iterCell;
        	if(!aCell.hasBeenVisited()){
        		updateDistance(aCell, aCell.getDistanceFromAgent());
        	}
        }*/
	}
	
}
