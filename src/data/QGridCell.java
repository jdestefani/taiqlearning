package data;

import java.util.ArrayList;

public class QGridCell extends MapCell {
	
	double cellReward;
	private double cellQValue;
	
	public QGridCell() {
		super();
		this.rowIndex = 0;
		this.columnIndex = 0;
		this.cellType = CellType.PLAIN;
		this.reachableCells = new ArrayList<MapCell>();
		this.cellReward = CellType.PLAINREWARD;
		this.cellQValue = 0.0;
	}
	
	public QGridCell(CellType aCellType) {
		super();
		this.rowIndex = 0;
		this.columnIndex = 0;
		this.cellType = aCellType;
		this.reachableCells = new ArrayList<MapCell>();
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
		this.reachableCells = new ArrayList<MapCell>();
		this.cellReward = CellType.PLAINREWARD;
		this.cellQValue = 0.0;
	}

	public QGridCell(int aRowIndex, int aColumnIndex, CellType aCellType) throws IndexOutOfBoundsException {
		super();
		verifyIndexes(aRowIndex, aColumnIndex);
		this.rowIndex = aRowIndex;
		this.columnIndex = aColumnIndex;
		this.cellType = aCellType;
		this.reachableCells = new ArrayList<MapCell>();
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
	
	public void setCellReward(double cellReward) {
		this.cellReward = cellReward;
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
	
	
}
