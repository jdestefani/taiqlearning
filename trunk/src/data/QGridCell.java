package data;

import java.util.ArrayList;

public class QGridCell {

	private CellType cellType;
	private ArrayList<QGridCell> reachableCells;
	private double cellReward;
	
	public double getCellReward() {
		return cellReward;
	}
	public void setCellReward(double cellReward) {
		this.cellReward = cellReward;
	}
	public QGridCell() {
		super();
		this.cellType = CellType.PLAIN;
		reachableCells = new ArrayList<QGridCell>();
		cellReward = 0.0;
	}
	public QGridCell(CellType cellType) {
		super();
		this.cellType = cellType;
		reachableCells = new ArrayList<QGridCell>();
		cellReward = 0.0;
	}
	
	public QGridCell(CellType cellType, double cellReward) {
		super();
		this.cellType = cellType;
		reachableCells = new ArrayList<QGridCell>();
		this.cellReward = cellReward;
	}
	public CellType getCellType() {
		return cellType;
	}
	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}
	public ArrayList<QGridCell> getReachableCells() {
		return reachableCells;
	}
	public void setReachableCells(ArrayList<QGridCell> reachableCells) {
		this.reachableCells = reachableCells;
	}
	
	public void addReachableCell(QGridCell reachableCell){
		this.reachableCells.add(reachableCell);
	}
	
	public void removeReachableCell(QGridCell reachableCell){
		this.reachableCells.remove(reachableCell);
	}
	
}
