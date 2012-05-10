package data;

import java.util.ArrayList;

public class AStarCell extends MapCell {
	
	private int distanceFromAgent;
	private double heuristicDistanceFromGoal;
	private AStarCell previousAPathCell;
	
	public AStarCell() {
		super();
		this.rowIndex = 0;
		this.columnIndex = 0;
		this.cellType = CellType.PLAIN;
		this.reachableCells = new ArrayList<MapCell>();
		this.distanceFromAgent = Integer.MAX_VALUE;
		this.heuristicDistanceFromGoal = 0.0;
		this.hasBeenVisited = false;
		previousAPathCell = null;
	}
	
	public AStarCell(CellType aCellType) {
		super();
		this.rowIndex = 0;
		this.columnIndex = 0;
		this.cellType = aCellType;
		this.reachableCells = new ArrayList<MapCell>();
		this.distanceFromAgent = Integer.MAX_VALUE;
		this.heuristicDistanceFromGoal = 0.0;
		this.hasBeenVisited = false;
		previousAPathCell = null;
	}
	
	public AStarCell(int aRowIndex, int aColumnIndex) throws IndexOutOfBoundsException {
		super();
		verifyIndexes(aRowIndex, aColumnIndex);
		this.rowIndex = aRowIndex;
		this.columnIndex = aColumnIndex;
		this.cellType = CellType.PLAIN;
		this.reachableCells = new ArrayList<MapCell>();
		this.distanceFromAgent = Integer.MAX_VALUE;
		this.heuristicDistanceFromGoal = 0.0;
		this.hasBeenVisited = false;
		previousAPathCell = null;
	}

	public int getDistanceFromAgent() {
		return distanceFromAgent;
	}

	public void setDistanceFromAgent(int distanceFromAgent) {
		this.distanceFromAgent = distanceFromAgent;
	}

	public double getHeuristicDistanceFromGoal() {
		return heuristicDistanceFromGoal;
	}

	public void setHeuristicDistanceFromGoal(double heuristicDistanceFromGoal) {
		this.heuristicDistanceFromGoal = heuristicDistanceFromGoal;
	}
	
	public void visit(){
		this.hasBeenVisited = true;
	}

	public AStarCell getPreviousAPathCell() {
		return previousAPathCell;
	}

	public void setPreviousAPathCell(AStarCell previousAPathCell) {
		this.previousAPathCell = previousAPathCell;
	}
	
	

}
