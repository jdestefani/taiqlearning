package data;

import java.util.ArrayList;

/**
 * The Class QGridCell.
 */
public class QGridCell extends MapCell {
	
	/** The cell reward. */
	double cellReward;
	
	/** The cell q value. */
	private double cellQValue;
	
	/**
	 * Instantiates a new q grid cell.
	 */
	public QGridCell() {
		super();
		this.rowIndex = 0;
		this.columnIndex = 0;
		this.cellType = CellType.PLAIN;
		this.reachableCells = new ArrayList<MapCell>();
		this.cellReward = CellType.PLAINREWARD;
		this.cellQValue = 0.0;
	}
	
	/**
	 * Instantiates a new q grid cell.
	 *
	 * @param aCellType the a cell type
	 */
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
	
	/**
	 * Instantiates a new q grid cell.
	 *
	 * @param aRowIndex the a row index
	 * @param aColumnIndex the a column index
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 */
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

	/**
	 * Instantiates a new q grid cell.
	 *
	 * @param aRowIndex the a row index
	 * @param aColumnIndex the a column index
	 * @param aCellType the a cell type
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 */
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
	
	/* (non-Javadoc)
	 * @see data.MapCell#setCellType(data.CellType)
	 */
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
	
	/**
	 * Sets the cell reward.
	 *
	 * @param cellReward the new cell reward
	 */
	public void setCellReward(double cellReward) {
		this.cellReward = cellReward;
	}

	/**
	 * Gets the cell reward.
	 *
	 * @return the cell reward
	 */
	public double getCellReward() {
		return cellReward;
	}
	
	/**
	 * Gets the cell q value.
	 *
	 * @return the cell q value
	 */
	public double getCellQValue() {
		return cellQValue;
	}

	/**
	 * Sets the cell q value.
	 *
	 * @param cellQValue the new cell q value
	 */
	public void setCellQValue(double cellQValue) {
		this.cellQValue = cellQValue;
	}
	
	
}
