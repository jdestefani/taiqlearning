package ai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

import data.AStarCell;
import data.CellType;
import data.MapCell;
import data.QGrid;
import data.QGridCell;

public class AStarPathFinder {
	
	private ArrayList<MapCell>[] portalReachableCells;
	private ArrayList<AStarCell>[] portalCells;
	private int[] maxDistancePortalIndex;
	private int[] minDistancePortalIndex;
	private int[] portalDiscoveryOrder;
	private AStarCell[][] aStarGrid;
	private AStarCell agentCell;
	private AStarCell endCell;
	private int stepsFromAgentToEnd;
	private ArrayList<AStarCell> aStarPath;
	
	
	public AStarPathFinder(QGrid aQGrid) {
		super();
		
		
		stepsFromAgentToEnd = -1;
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
		
		//Detection of the furthest and closest portals to the agent.
		computeDistanceFrom(agentCell);
		
		System.out.println("Is end reachable?"+(stepsFromAgentToEnd == -1 ? false : true));
		
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
		boolean swap = false;
		int swapElement = 0;
		while(!swap){
			swap = false;
			for(int i = 0; i<QGrid.PORTALNUMBER - 1 ; i++){
				if(portalCells[i].get(minDistancePortalIndex[i]).getDistanceFromAgent() < portalCells[i+1].get(minDistancePortalIndex[i+1]).getDistanceFromAgent()){
					swapElement = portalDiscoveryOrder[i];
					portalDiscoveryOrder[i] = portalDiscoveryOrder[i+1];
					portalDiscoveryOrder[i+1] = swapElement;
					swap = true;
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

	public ArrayList<MapCell>[] getPortalReachableCells() {
		return portalReachableCells;
	}

	public AStarCell[][] getaStarGrid() {
		return aStarGrid;
	}

	public AStarCell getAgentCell() {
		return agentCell;
	}

	public AStarCell getEndCell() {
		return endCell;
	}

	public ArrayList<AStarCell> getaStarPath() {
		return aStarPath;
	}

	public AStarCell getCell(int aRowIndex, int aColumnIndex) throws IndexOutOfBoundsException{
		MapCell.verifyIndexes(aRowIndex, aColumnIndex);
		return aStarGrid[aRowIndex][aColumnIndex];
	}

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

	private void computeHeuristic(AStarCell aCell){
		aCell.setHeuristicDistanceFromGoal(euclidianDistance(aCell, endCell));
	}
	
	private double euclidianDistance(MapCell aCell1, MapCell aCell2){
		int heightDiff = Math.abs(aCell1.getRowIndex() - aCell2.getRowIndex());
		int widthDiff = Math.abs(aCell1.getColumnIndex() - aCell2.getColumnIndex());
		return Math.sqrt(Math.pow(heightDiff, 2) + Math.pow(widthDiff, 2));
	}
	

	public void findPath(){
		Comparator<AStarCell> comparator = new AStarCellComparator();
        PriorityQueue<AStarCell> searchQueue = new PriorityQueue<AStarCell>(10, comparator);
        int distanceFromAgent = 1;
        
        for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				this.getCell(i, j).setHasBeenVisited(false);
			}
		}
        
        selectNextCell(agentCell, searchQueue, distanceFromAgent);
        //Add to path if correct, remove to path if wrong
        buildAStarPath();
	}
	
	private void selectNextCell(AStarCell currCell,PriorityQueue<AStarCell> searchQueue, int distanceFromAgent){
		currCell.visit();
		aStarPath.add(currCell);
		if(currCell.equals(endCell)){
			return;
		}
		
		for(MapCell iterCell : currCell.getReachableCells()){
        	AStarCell aCell = (AStarCell)iterCell;
        	//aCell.setDistanceFromAgent(distanceFromAgent);
        	computeHeuristic(aCell);
        	if(!aCell.hasBeenVisited()){
        		searchQueue.add(aCell);
        	}
        }
        //Pop a cell and recall the algorithm on it.
		searchQueue.peek().setPreviousAPathCell(currCell);
		selectNextCell(searchQueue.poll(),searchQueue,distanceFromAgent+1);
	}
	
	private void buildAStarPath(){
		AStarCell currCell = endCell;
		while(currCell.getCellType() != CellType.AGENT){
			aStarPath.add(currCell);
			System.out.println(currCell.toString());
			currCell = currCell.getPreviousAPathCell();
		}
	}
	
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
				if(((AStarCell)iterCell).getPreviousAPathCell() == null && ((AStarCell)iterCell).getCellType() != CellType.AGENT){
					cellsToVisit[insertionPoint++] = ((AStarCell)iterCell);
					((AStarCell)iterCell).setDistanceFromAgent(currCell.getDistanceFromAgent()+1);
					((AStarCell)iterCell).setPreviousAPathCell(currCell);
				}
				
				if(((AStarCell)iterCell).getCellType() == CellType.ENDPOINT){
					stepsFromAgentToEnd = ((AStarCell)iterCell).getDistanceFromAgent();
				}
			}
			
			
			//3. Explore next cell
			currCell = cellsToVisit[++index];
			
		}while(index != insertionPoint);
		
		
	}
	
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
				if(distanceFromCell+1 < ((AStarCell)iterCell).getDistanceFromAgent() && ((AStarCell)iterCell).getCellType() != CellType.AGENT){
					cellsToVisit[insertionPoint++] = ((AStarCell)iterCell);
					((AStarCell)iterCell).setDistanceFromAgent(currCell.getDistanceFromAgent()+1);
					((AStarCell)iterCell).setPreviousAPathCell(currCell);
				}
				
				if(((AStarCell)iterCell).getCellType() == CellType.ENDPOINT){
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
	
	class AStarCellComparator implements Comparator<AStarCell>
	{

		@Override
		public int compare(AStarCell aCell1, AStarCell aCell2) {
			{
				double evaluationFunction1 = aCell1.getDistanceFromAgent()+aCell1.getHeuristicDistanceFromGoal();
				double evaluationFunction2 = aCell2.getDistanceFromAgent()+aCell2.getHeuristicDistanceFromGoal();
				
				//System.out.println(aCell1.toString() + " - " + aCell2.toString());
				//System.out.println("h1: " + evaluationFunction1 + " - h2:" + evaluationFunction2);
				
				//Best choice is the one having the smallest heuristic value
		        if (evaluationFunction1 < evaluationFunction2)
		        {
		            return -1;
		        }
		        if (evaluationFunction1 >= evaluationFunction2)
		        {
		            return 1;
		        }
		        return 0;
		    }
		}	
		 
		
		
	}

}
