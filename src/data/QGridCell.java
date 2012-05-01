package data;

import java.util.ArrayList;

public class QGridCell {
	
	private int rowIndex;
	private int columnIndex;
	private CellType cellType;
	private ArrayList<QGridCell> reachableCells;
	private double cellReward;
	private double cellQValue;
	
	public QGridCell() {
		super();
		this.rowIndex = 0;
		this.columnIndex = 0;
		this.cellType = CellType.PLAIN;
		this.reachableCells = new ArrayList<QGridCell>();
		this.cellReward = 0.0;
		this.cellQValue = 0.0;
	}
	
	public QGridCell(CellType aCellType) {
		super();
		this.rowIndex = 0;
		this.columnIndex = 0;
		this.cellType = aCellType;
		this.reachableCells = new ArrayList<QGridCell>();
		switch(this.cellType){
		case BONUS: 	this.cellReward = CellType.BONUSREWARD;
						break;
		case ENDPOINT: 	this.cellReward = CellType.ENDPOINTREWARD;
					   	break;
		case WALL: 		this.cellReward = CellType.WALLREWARD;
				   		break;
		case MALUS: 	this.cellReward = CellType.MALUSREWARD;
						break;
		default: 		this.cellReward = CellType.PLAINREWARD;
				 		break;
	}
		this.cellQValue = 0.0;
	}
	
	public QGridCell(int aRowIndex, int aColumnIndex) throws IndexOutOfBoundsException {
		super();
		verifyIndexes(aRowIndex, aColumnIndex);
		this.rowIndex = aRowIndex;
		this.columnIndex = aColumnIndex;
		this.cellType = CellType.PLAIN;
		this.reachableCells = new ArrayList<QGridCell>();
		this.cellReward = 0.0;
		this.cellQValue = 0.0;
	}

	public QGridCell(int aRowIndex, int aColumnIndex, CellType aCellType) throws IndexOutOfBoundsException {
		super();
		verifyIndexes(aRowIndex, aColumnIndex);
		this.rowIndex = aRowIndex;
		this.columnIndex = aColumnIndex;
		this.cellType = aCellType;
		this.reachableCells = new ArrayList<QGridCell>();
		switch(this.cellType){
			case BONUS: 	this.cellReward = CellType.BONUSREWARD;
							break;
			case ENDPOINT: 	this.cellReward = CellType.ENDPOINTREWARD;
						   	break;
			case WALL: 		this.cellReward = CellType.WALLREWARD;
					   		break;
			case MALUS: 	this.cellReward = CellType.MALUSREWARD;
							break;
			default: 		this.cellReward = CellType.PLAINREWARD;
					 		break;
		}
		
		this.cellQValue = 0.0;
	}
	
	public int getRowIndex() {
		return rowIndex;
	}
	
	public int getColumnIndex() {
		return columnIndex;
	}

	public CellType getCellType() {
		return cellType;
	}
	
	public void setCellType(CellType cellType) {
		this.cellType = cellType;
		switch(cellType){
		case BONUS: this.cellReward = CellType.BONUSREWARD;
					break;
		case ENDPOINT: this.cellReward = CellType.ENDPOINTREWARD;
					   break;
		case WALL: this.cellReward = CellType.WALLREWARD;
				   break;
		case MALUS: this.cellReward = CellType.MALUSREWARD;
					break;
		default: this.cellReward = CellType.PLAINREWARD;
				 break;
	}
	}
	
	public double getCellReward() {
		return cellReward;
	}
	
	public double getCellQValue() {
		return cellQValue;
	}

	public void setCellQValue(double cellQValue) {
		this.cellQValue = cellQValue;
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
	
	public static void verifyIndexes(int aRowIndex,int aColumnIndex){
		if(aRowIndex < 0 || aRowIndex > QGrid.MAPHEIGHT){
			throw new IndexOutOfBoundsException("Row index out of bounds.");
		}
		if(aColumnIndex < 0 || aColumnIndex > QGrid.MAPWIDTH){
			throw new IndexOutOfBoundsException("Row index out of bounds.");
		}
		
	}
}
