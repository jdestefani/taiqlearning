package ai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import data.AStarCell;
import data.CellType;
import data.MapCell;
import data.QGrid;
import data.QGridCell;

public class AStarPathFinder {
	
	private QGrid gridMap;
	private ArrayList<MapCell>[] portalReachableCells;
	private AStarCell[][] aStarGrid;
	private AStarCell agentCell;
	private AStarCell endCell;
	private ArrayList<AStarCell> aStarPath;
	
	
	public AStarPathFinder(QGrid gridMap) {
		super();
		this.gridMap = gridMap;
		
		aStarPath = new ArrayList<AStarCell>();
		aStarGrid = new AStarCell[QGrid.MAPHEIGHT][QGrid.MAPWIDTH];
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				aStarGrid[i][j] = new AStarCell(i,j);
				if(aStarGrid[i][j].getCellType() == CellType.AGENT){
					agentCell = aStarGrid[i][j];
				}
				if(aStarGrid[i][j].getCellType() == CellType.ENDPOINT){
					endCell = aStarGrid[i][j];
				}
			}
		}
		
		portalReachableCells = new ArrayList[QGrid.PORTALNUMBER];
		for(int i=0; i<QGrid.PORTALNUMBER ; i++){
			portalReachableCells[i] = new ArrayList<MapCell>();
		}
		
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				this.getCell(i, j).addDefaultReachableCells(aStarGrid);
			}
		}
		
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				this.getCell(i, j).addPortalReachableCells(aStarGrid,portalReachableCells);
			}
		}
	}

	private void computeHeuristic(AStarCell aCell){
		aCell.setHeuristicDistanceFromGoal(Math.sqrt(Math.pow(endCell.getRowIndex() - aCell.getRowIndex(), 2) + Math.pow(endCell.getColumnIndex() - aCell.getColumnIndex(), 2)));
	}

	private void findPath(){
		Comparator<AStarCell> comparator = new AStarCellComparator();
        PriorityQueue<AStarCell> queue = new PriorityQueue<AStarCell>(0, comparator);
        
        for(MapCell currCell : agentCell.getReachableCells()){
        	
        }
	}
	
	public AStarCell getCell(int aRowIndex, int aColumnIndex) throws IndexOutOfBoundsException{
		MapCell.verifyIndexes(aRowIndex, aColumnIndex);
		return aStarGrid[aRowIndex][aColumnIndex];
	}
	
	
	
	

	class AStarCellComparator implements Comparator<AStarCell>
	{

		@Override
		public int compare(AStarCell aCell1, AStarCell aCell2) {
			{
				double evaluationFunction1 = aCell1.getDistanceFromAgent()+aCell1.getDistanceFromAgent();
				double evaluationFunction2 = aCell2.getDistanceFromAgent()+aCell2.getDistanceFromAgent();
		       
		        if (evaluationFunction1 < evaluationFunction2)
		        {
		            return -1;
		        }
		        if (evaluationFunction1 > evaluationFunction2)
		        {
		            return 1;
		        }
		        return 0;
		    }
		}	
		 
		
		
	}

}
