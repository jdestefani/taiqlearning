package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;

import ai.QLearning;

import data.AStarCell;
import data.CellType;
import data.MapCell;
import data.QGridCell;

import main.TAIQLearningApp;

/**
 * The Class GUICell.
 */
public class GUICell extends JButton {

	/** The Constant AGENTICON. */
	private static final ImageIcon AGENTICON = TAIQLearningApp.importImage(new String("Agent.png"));

	/** The Constant PLAINICON. */
	protected static final ImageIcon PLAINICON = TAIQLearningApp.importImage(new String("SteelFloor3.png"));
	
	/** The Constant PLAINQRICON. */
	private static final ImageIcon PLAINQRICON = TAIQLearningApp.importImage(new String("SteelFloor2.png"));
	
	/** The Constant ENDPOINTICON. */
	private static final ImageIcon ENDPOINTICON = TAIQLearningApp.importImage(new String("EndPoint.png"));
	
	/** The Constant WALLICON. */
	private static final ImageIcon WALLICON = TAIQLearningApp.importImage(new String("Wall.png"));
	
	/** The Constant BONUSICON. */
	private static final ImageIcon BONUSICON = TAIQLearningApp.importImage(new String("Box.png"));
	
	/** The Constant MALUSICON. */
	private static final ImageIcon MALUSICON = TAIQLearningApp.importImage(new String("MalusIcon.png"));
	
	/** The Constant PORTAL1ICON. */
	private static final ImageIcon PORTAL1ICON = TAIQLearningApp.importImage(new String("Portal1.png"));
	
	/** The Constant PORTAL2ICON. */
	private static final ImageIcon PORTAL2ICON = TAIQLearningApp.importImage(new String("Portal2.png"));
	
	/** The Constant PORTAL3ICON. */
	private static final ImageIcon PORTAL3ICON = TAIQLearningApp.importImage(new String("Portal3.png"));
	
	/** The Constant PORTAL4ICON. */
	private static final ImageIcon PORTAL4ICON = TAIQLearningApp.importImage(new String("Portal4.png"));
	
	/** The Constant ATTRIBUTEDELIMITER. */
	private static final String ATTRIBUTEDELIMITER = new String(",");
	
	/** The Constant ASTARSETCOLOR. */
	private static final Color ASTARSETCOLOR = Color.ORANGE;
	
	/** The Constant ASTARPATHCOLOR. */
	private static final Color ASTARPATHCOLOR = Color.GREEN;
	
	/** The Constant QVISITEDSETCOLOR. */
	private static final Color QVISITEDSETCOLOR = Color.BLUE;
	
	/** The Constant QVISITEDPATHCOLOR. */
	private static final Color QVISITEDPATHCOLOR = Color.CYAN;
	
	/** The Constant ASTARSETBORDER. */
	private static final Border ASTARSETBORDER = BorderFactory.createMatteBorder(2, 2, 2, 2, ASTARSETCOLOR);
	
	/** The Constant ASTARPATHBORDER. */
	private static final Border ASTARPATHBORDER = BorderFactory.createMatteBorder(2, 2, 2, 2, ASTARPATHCOLOR);
	
	/** The Constant QVISITEDSETBORDER. */
	private static final Border QVISITEDSETBORDER = BorderFactory.createMatteBorder(4, 4, 4, 4, QVISITEDSETCOLOR);
	
	/** The Constant QVISITEDPATHBORDER. */
	private static final Border QVISITEDPATHBORDER = BorderFactory.createMatteBorder(4, 4, 4, 4, QVISITEDPATHCOLOR);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4276493018743526930L;

	/** The row index. */
	private int rowIndex;
	
	/** The column index. */
	private int columnIndex;
	
	/** The is a star path. */
	private boolean isAStarPath;
	
	/** The is a star set. */
	private boolean isAStarSet;
	
	/** The is q learning set. */
	private boolean isQLearningSet;
	
	/** The is q learning path. */
	private boolean isQLearningPath;
	
	/**
	 * Instantiates a new gUI cell.
	 *
	 * @param aRow the a row
	 * @param aColumn the a column
	 */
	public GUICell(int aRow,int aColumn) {
		super();
		rowIndex = aRow;
		columnIndex = aColumn;
	}

	/**
	 * Reset cell.
	 */
	public void resetCell(){
		isAStarPath = false;
		isAStarSet = false;
		isQLearningSet = false;
		isQLearningPath = false;
	}

	/**
	 * Checks if is a star path.
	 *
	 * @return true, if is a star path
	 */
	public boolean isAStarPath() {
		return isAStarPath;
	}

	/**
	 * Sets the a star path.
	 *
	 * @param isAStarPath the new a star path
	 */
	public void setAStarPath(boolean isAStarPath) {
		this.isAStarPath = isAStarPath;
	}

	/**
	 * Checks if is a star set.
	 *
	 * @return true, if is a star set
	 */
	public boolean isAStarSet() {
		return isAStarSet;
	}

	/**
	 * Sets the a star set.
	 *
	 * @param isAStarSet the new a star set
	 */
	public void setAStarSet(boolean isAStarSet) {
		this.isAStarSet = isAStarSet;
	}

	/**
	 * Checks if is q learning set.
	 *
	 * @return true, if is q learning set
	 */
	public boolean isQLearningSet() {
		return isQLearningSet;
	}

	/**
	 * Sets the q learning set.
	 *
	 * @param isQLearningSet the new q learning set
	 */
	public void setQLearningSet(boolean isQLearningSet) {
		this.isQLearningSet = isQLearningSet;
	}

	/**
	 * Checks if is q learning path.
	 *
	 * @return true, if is q learning path
	 */
	public boolean isQLearningPath() {
		return isQLearningPath;
	}

	/**
	 * Sets the q learning path.
	 *
	 * @param isQLearningPath the new q learning path
	 */
	public void setQLearningPath(boolean isQLearningPath) {
		this.isQLearningPath = isQLearningPath;
	}

	/**
	 * Gets the agenticon.
	 *
	 * @return the agenticon
	 */
	public static ImageIcon getAgenticon() {
		return AGENTICON;
	}

	/**
	 * Gets the plainicon.
	 *
	 * @return the plainicon
	 */
	public static ImageIcon getPlainicon() {
		return PLAINICON;
	}

	/**
	 * Gets the plainqricon.
	 *
	 * @return the plainqricon
	 */
	public static ImageIcon getPlainqricon() {
		return PLAINQRICON;
	}

	/**
	 * Gets the endpointicon.
	 *
	 * @return the endpointicon
	 */
	public static ImageIcon getEndpointicon() {
		return ENDPOINTICON;
	}

	/**
	 * Gets the wallicon.
	 *
	 * @return the wallicon
	 */
	public static ImageIcon getWallicon() {
		return WALLICON;
	}

	/**
	 * Gets the bonusicon.
	 *
	 * @return the bonusicon
	 */
	public static ImageIcon getBonusicon() {
		return BONUSICON;
	}

	/**
	 * Gets the malusicon.
	 *
	 * @return the malusicon
	 */
	public static ImageIcon getMalusicon() {
		return MALUSICON;
	}

	/**
	 * Gets the portal1icon.
	 *
	 * @return the portal1icon
	 */
	public static ImageIcon getPortal1icon() {
		return PORTAL1ICON;
	}

	/**
	 * Gets the portal2icon.
	 *
	 * @return the portal2icon
	 */
	public static ImageIcon getPortal2icon() {
		return PORTAL2ICON;
	}

	/**
	 * Gets the portal3icon.
	 *
	 * @return the portal3icon
	 */
	public static ImageIcon getPortal3icon() {
		return PORTAL3ICON;
	}

	/**
	 * Gets the portal4icon.
	 *
	 * @return the portal4icon
	 */
	public static ImageIcon getPortal4icon() {
		return PORTAL4ICON;
	}

	/**
	 * Gets the attributedelimiter.
	 *
	 * @return the attributedelimiter
	 */
	public static String getAttributedelimiter() {
		return ATTRIBUTEDELIMITER;
	}

	/**
	 * Gets the astarsetcolor.
	 *
	 * @return the astarsetcolor
	 */
	public static Color getAstarsetcolor() {
		return ASTARSETCOLOR;
	}

	/**
	 * Gets the astarpathcolor.
	 *
	 * @return the astarpathcolor
	 */
	public static Color getAstarpathcolor() {
		return ASTARPATHCOLOR;
	}

	/**
	 * Gets the qvisitedsetcolor.
	 *
	 * @return the qvisitedsetcolor
	 */
	public static Color getQvisitedsetcolor() {
		return QVISITEDSETCOLOR;
	}

	/**
	 * Gets the astarsetborder.
	 *
	 * @return the astarsetborder
	 */
	public static Border getAstarsetborder() {
		return ASTARSETBORDER;
	}

	/**
	 * Gets the astarpathborder.
	 *
	 * @return the astarpathborder
	 */
	public static Border getAstarpathborder() {
		return ASTARPATHBORDER;
	}

	/**
	 * Gets the qvisitedsetborder.
	 *
	 * @return the qvisitedsetborder
	 */
	public static Border getQvisitedsetborder() {
		return QVISITEDSETBORDER;
	}

	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Gets the row index.
	 *
	 * @return the row index
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * Gets the column index.
	 *
	 * @return the column index
	 */
	public int getColumnIndex() {
		return columnIndex;
	}

	/**
	 * Refresh single cell.
	 *
	 * @param aType the a type
	 */
	public void refreshSingleCell(CellType aType){
		
		Dimension iconDimension = null;
		String actionCommand = null;
		
		switch(aType){
		case PLAIN:this.setIcon(PLAINICON);
		iconDimension = new Dimension(PLAINICON.getIconHeight(),PLAINICON.getIconWidth());
		this.setToolTipText("Floor");
		break;

		case AGENT:this.setIcon(GUICell.AGENTICON);
		iconDimension = new Dimension(GUICell.AGENTICON.getIconHeight(),GUICell.AGENTICON.getIconWidth());
		this.setToolTipText("Agent");
		break;

		case BONUS:this.setIcon(BONUSICON);
		iconDimension = new Dimension(BONUSICON.getIconHeight(),BONUSICON.getIconWidth());
		this.setToolTipText("Bonus");
		break;

		case MALUS:this.setIcon(MALUSICON);
		iconDimension = new Dimension(MALUSICON.getIconHeight(),MALUSICON.getIconWidth());
		this.setToolTipText("Malus");
		break;
		
		case ENDPOINT:	this.setIcon(ENDPOINTICON);
		iconDimension = new Dimension(ENDPOINTICON.getIconHeight(),ENDPOINTICON.getIconWidth());
		this.setToolTipText("Goal");
		break;

		case WALL:	this.setIcon(WALLICON);
		iconDimension = new Dimension(WALLICON.getIconHeight(),WALLICON.getIconWidth());
		this.setToolTipText("Wall");
		break;

		case PORTAL1:this.setIcon(PORTAL1ICON);
		iconDimension = new Dimension(PORTAL1ICON.getIconHeight(),PORTAL1ICON.getIconWidth());
		this.setToolTipText("Portal-Type 1");
		break;

		case PORTAL2:this.setIcon(PORTAL2ICON);
		iconDimension = new Dimension(PORTAL2ICON.getIconHeight(),PORTAL2ICON.getIconWidth());
		this.setToolTipText("Portal-Type 2");
		break;

		case PORTAL3:this.setIcon(PORTAL3ICON);
		iconDimension = new Dimension(PORTAL3ICON.getIconHeight(),PORTAL3ICON.getIconWidth());
		this.setToolTipText("Portal-Type 3");
		break;

		case PORTAL4:this.setIcon(PORTAL4ICON);
		iconDimension = new Dimension(PORTAL4ICON.getIconHeight(),PORTAL4ICON.getIconWidth());
		this.setToolTipText("Portal-Type 4");
		break;


		default:break;
		}
		
		actionCommand = new String(this.rowIndex+ATTRIBUTEDELIMITER+this.columnIndex+ATTRIBUTEDELIMITER);
		this.setText("");
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBorderPainted(false);
		this.resetCell();
		this.setPreferredSize(iconDimension);
		this.setContentAreaFilled(false);
		this.setActionCommand(actionCommand);
	}
	
	/**
	 * Refresh single cell distance.
	 *
	 * @param aDistance the a distance
	 */
	public void refreshSingleCellDistance(int aDistance){
		
		Dimension iconDimension = null;
		String actionCommand = null;
		
		this.setIcon(PLAINQRICON);
		this.setBackground(Color.WHITE);
		iconDimension = new Dimension(PLAINICON.getIconHeight(),PLAINICON.getIconWidth());
		this.setText(aDistance == Integer.MAX_VALUE ? "NR" : Integer.toString(aDistance));
		this.setHorizontalTextPosition(JButton.CENTER);
		this.setVerticalTextPosition(JButton.CENTER);
		this.setForeground(Color.WHITE);
		
		actionCommand = new String(this.rowIndex+ATTRIBUTEDELIMITER+this.columnIndex+ATTRIBUTEDELIMITER+(aDistance == Integer.MAX_VALUE ? "NR" : Integer.toString(aDistance)));
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBorderPainted(false);
		this.resetCell();
		this.setPreferredSize(iconDimension);
		this.setContentAreaFilled(false);
		this.setActionCommand(actionCommand);
	}

	
	/**
	 * Draw path.
	 */
	public void drawPath(){
		
		//Convention: Path is always included in set, hence if a cell is both Path and Set cell for the
		//same algorithm, only the color related to the path is displayed
		if(isAStarPath && isAStarSet && isQLearningSet && isQLearningPath){
			this.setBorder(BorderFactory.createCompoundBorder(ASTARPATHBORDER, QVISITEDPATHBORDER));
			this.setBorderPainted(true);
			return;
		}
		if(isAStarPath && isAStarSet && isQLearningSet){
			this.setBorder(BorderFactory.createCompoundBorder(ASTARPATHBORDER, QVISITEDSETBORDER));
			this.setBorderPainted(true);
			return;
		}
		if(isAStarPath && isAStarSet && isQLearningPath){
			this.setBorder(BorderFactory.createCompoundBorder(ASTARPATHBORDER, QVISITEDPATHBORDER));
			this.setBorderPainted(true);
			return;
		}
		if(isAStarPath && isQLearningSet && isQLearningPath){
			this.setBorder(BorderFactory.createCompoundBorder(ASTARPATHBORDER, QVISITEDPATHBORDER));
			this.setBorderPainted(true);
			return;
		}
		if(isAStarSet && isQLearningSet && isQLearningPath){
			this.setBorder(BorderFactory.createCompoundBorder(ASTARSETBORDER, QVISITEDPATHBORDER));
			this.setBorderPainted(true);
			return;
		}
		if(isAStarPath && isAStarSet){
			this.setBorder(ASTARPATHBORDER);
			this.setBorderPainted(true);
			return;
		}
		if(isAStarPath && isQLearningSet){
			this.setBorder(BorderFactory.createCompoundBorder(ASTARPATHBORDER, QVISITEDSETBORDER));
			this.setBorderPainted(true);
			return;
		}
		if(isAStarPath && isQLearningPath){
			this.setBorder(BorderFactory.createCompoundBorder(ASTARPATHBORDER, QVISITEDPATHBORDER));
			this.setBorderPainted(true);
			return;
		}
		if(isAStarSet && isQLearningSet){
			this.setBorder(BorderFactory.createCompoundBorder(ASTARSETBORDER, QVISITEDSETBORDER));
			this.setBorderPainted(true);
			return;
		}
		if(isAStarSet && isQLearningPath){
			this.setBorder(BorderFactory.createCompoundBorder(ASTARSETBORDER, QVISITEDPATHBORDER));
			this.setBorderPainted(true);
			return;
		}
		if(isQLearningSet && isQLearningPath){
			this.setBorder(QVISITEDPATHBORDER);
			this.setBorderPainted(true);
			return;
		}
		if(isAStarPath){
			this.setBorder(ASTARPATHBORDER);
			this.setBorderPainted(true);
			return;
		}
		if(isAStarSet){
			this.setBorder(ASTARSETBORDER);
			this.setBorderPainted(true);
			return;
		}
		if(isQLearningSet){
			this.setBorder(QVISITEDSETBORDER);
			this.setBorderPainted(true);
			return;
		}
		if(isQLearningPath){
			this.setBorder(QVISITEDPATHBORDER);
			this.setBorderPainted(true);
			return;
		}
		
		/*switch(currCell.getCellType()){
		
		case PORTAL1:
		case PORTAL2:
		case PORTAL3:
		case PORTAL4:	this.setText("A*");
						this.setHorizontalTextPosition(JButton.CENTER);
	 					this.setVerticalTextPosition(JButton.CENTER);
	 					this.setForeground(aIsSet?ASTARSETCOLOR:ASTARPATHCOLOR);
	 					break;
					 	
		case BONUS:		
		case MALUS:
		case AGENT:
		case ENDPOINT:
		default:	this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setText("");
				 	break;
		}*/
	}
	
}
