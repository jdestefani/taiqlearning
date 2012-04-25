package data;

import java.util.ArrayList;

public class QGridCell {

	private CellType cellType;
	private ArrayList<QGridCell> allowedDestinations;
	private double cellReward;
	private double Qvalue;
	
	public QGridCell() {
		super();
		this.cellType = CellType.PLAIN;
		allowedDestinations = new ArrayList<QGridCell>();
		cellReward = 0.0;
	}
	public QGridCell(CellType cellType) {
		super();
		this.cellType = cellType;
		allowedDestinations = new ArrayList<QGridCell>();
		cellReward = 0.0;
	}
	
	public QGridCell(CellType cellType, double cellReward) {
		super();
		this.cellType = cellType;
		allowedDestinations = new ArrayList<QGridCell>();
		this.cellReward = cellReward;
	}
	public CellType getCellType() {
		return cellType;
	}
	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}
	public ArrayList<QGridCell> getAllowedDestinations() {
		return allowedDestinations;
	}
	public void setAllowedDestinations(ArrayList<QGridCell> allowedDestinations) {
		this.allowedDestinations = allowedDestinations;
	}
	
	
	
}
