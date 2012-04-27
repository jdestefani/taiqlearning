package gui;

import gui.GraphicalGUI.GraphicalGUIListener;
import gui.GraphicalGUI.TurnCommandListener;
import gui.MapHandler.MapActionListener;

import io.GridFileHandler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import data.CellType;
import data.QGridCell;

import main.TAIQLearningApp;

public class MapGUI {

	
	private JPanel contentPane;
	private JScrollPane mapPanel;
	private JPanel cellGrid;
	private JPanel consolePanel;
	private JScrollPane consoleScrollPanel;
	private JPanel sidePanel;
	private JPanel actionPanel;
	
	
	private JButton[][] map;
	
	private JButton saveMapButton;
	private JButton loadMapButton;
	private JButton learnPathButton;
	private JButton displayQButton;
	
	private JTextArea infoConsole;
	
	private MapActionListener cellActionListener;
	
	private TAIQLearningApp mainApp;
	
	
	
	private static final ImageIcon AGENTICON = TAIQLearningApp.importImage(new String("Agent.png"));
	private static final ImageIcon PLAINICON = TAIQLearningApp.importImage(new String("SteelFloor3.png"));
	private static final ImageIcon ENDPOINTICON = TAIQLearningApp.importImage(new String("EndPoint.png"));
	private static final ImageIcon WALLICON = TAIQLearningApp.importImage(new String("Wall.png"));
	private static final ImageIcon BONUSICON = TAIQLearningApp.importImage(new String("Box.png"));
	private static final ImageIcon PORTAL1ICON = TAIQLearningApp.importImage(new String("Portal1.png"));
	private static final ImageIcon PORTAL2ICON = TAIQLearningApp.importImage(new String("Portal2.png"));
	private static final ImageIcon PORTAL3ICON = TAIQLearningApp.importImage(new String("Portal3.png"));
	private static final ImageIcon PORTAL4ICON = TAIQLearningApp.importImage(new String("Portal4.png"));
	//private static final ImageIcon FOGICON = GUIElement.importImage(FOGPATH);
	
	

	public MapGUI(TAIQLearningApp mainApp) {
		super();
		
		this.mainApp = mainApp;
		
		this.contentPane = new JPanel();
		this.contentPane.setOpaque(true); 
		this.contentPane.setLayout(new BorderLayout());
        
		this.sidePanel = new JPanel();
		this.sidePanel.setOpaque(true);
		this.sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
	
		Border actionPanelBorder = BorderFactory.createTitledBorder("Actions");
		this.actionPanel = new JPanel();
		this.actionPanel.setBorder(actionPanelBorder);
		this.actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
		
		this.saveMapButton = new JButton("Save Map");
		this.saveMapButton.addActionListener(new ActionButtonListener(this.mainApp));
		this.loadMapButton = new JButton("Load Map");
		this.loadMapButton.addActionListener(new ActionButtonListener(this.mainApp));
		this.learnPathButton = new JButton("Learn Path");
		this.learnPathButton.addActionListener(new ActionButtonListener(this.mainApp));
		this.displayQButton = new JButton("Display Q values");
		this.displayQButton.addActionListener(new ActionButtonListener(this.mainApp));
		
		this.actionPanel.add(saveMapButton);
		this.actionPanel.add(loadMapButton);
		this.actionPanel.add(learnPathButton);
		this.actionPanel.add(displayQButton);
		
		this.sidePanel.add(actionPanel);
		
       
		this.map = new JButton[TAIQLearningApp.MAPHEIGHT][TAIQLearningApp.MAPWIDTH];
		//this.cellActionListener = new MapActionListener();
		
		Border gridBorder = BorderFactory.createLoweredBevelBorder();
		
		this.cellGrid = new JPanel();
		this.cellGrid.setOpaque(true); 
		this.cellGrid.setLayout(new GridLayout(TAIQLearningApp.MAPHEIGHT,TAIQLearningApp.MAPWIDTH,0,0));
		this.cellGrid.setBorder(gridBorder);
		
		for(int i=0; i<TAIQLearningApp.MAPHEIGHT ; i++){
			for(int j=0 ; j<TAIQLearningApp.MAPWIDTH; j++){
				this.map[i][j] = new JButton();
				this.cellGrid.add(this.map[i][j]);
				this.map[i][j].addActionListener(this.cellActionListener);
			}
		}
		
		this.mapPanel = new JScrollPane(cellGrid);
		this.mapPanel.setPreferredSize(new Dimension(600,400));
		
		Border consolePanelBorder = BorderFactory.createTitledBorder("Console");
		this.consolePanel = new JPanel();
		this.infoConsole = new JTextArea(6, 80);
		this.infoConsole.setEditable(false);
		this.infoConsole.setLineWrap(true);
		this.infoConsole.setWrapStyleWord(true);
		this.consolePanel.add(infoConsole);
		this.consoleScrollPanel = new JScrollPane(this.consolePanel);
		this.consoleScrollPanel.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.consoleScrollPanel.setBorder(consolePanelBorder);
        this.consoleScrollPanel.setBackground(Color.WHITE);
		
		contentPane.add(mapPanel,BorderLayout.CENTER);
        contentPane.add(consoleScrollPanel,BorderLayout.SOUTH);
        contentPane.add(sidePanel,BorderLayout.EAST);
        refreshMap(TAIQLearningApp.MAPHEIGHT,TAIQLearningApp.MAPWIDTH);
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
	}

	public JScrollPane getMapPanel() {
		return mapPanel;
	}

	public void setMapPanel(JScrollPane mapPanel) {
		this.mapPanel = mapPanel;
	}

	public JPanel getCellGrid() {
		return cellGrid;
	}

	public void setCellGrid(JPanel cellGrid) {
		this.cellGrid = cellGrid;
	}

	public JPanel getConsolePanel() {
		return consolePanel;
	}

	public void setConsolePanel(JPanel consolePanel) {
		this.consolePanel = consolePanel;
	}

	public JScrollPane getConsoleScrollPanel() {
		return consoleScrollPanel;
	}

	public void setConsoleScrollPanel(JScrollPane consoleScrollPanel) {
		this.consoleScrollPanel = consoleScrollPanel;
	}

	public JPanel getSidePanel() {
		return sidePanel;
	}

	public void setSidePanel(JPanel sidePanel) {
		this.sidePanel = sidePanel;
	}

	public JPanel getActionPanel() {
		return actionPanel;
	}

	public void setActionPanel(JPanel actionPanel) {
		this.actionPanel = actionPanel;
	}

	public JButton[][] getMap() {
		return map;
	}

	public void setMap(JButton[][] map) {
		this.map = map;
	}

	public JButton getLoadMapButton() {
		return loadMapButton;
	}

	public void setLoadMapButton(JButton loadMapButton) {
		this.loadMapButton = loadMapButton;
	}

	public JButton getLearnPathButton() {
		return learnPathButton;
	}

	public void setLearnPathButton(JButton learnPathButton) {
		this.learnPathButton = learnPathButton;
	}

	public JButton getDisplayQButton() {
		return displayQButton;
	}

	public void setDisplayQButton(JButton displayQButton) {
		this.displayQButton = displayQButton;
	}

	public MapActionListener getCellActionListener() {
		return cellActionListener;
	}

	public void setCellActionListener(MapActionListener cellActionListener) {
		this.cellActionListener = cellActionListener;
	}
	
	/**
	 * Refresh della mappa completa.
	 * 
	 * @param charMap
	 *            La matrice di caratteri fornente una rappresentazione
	 *            testuale della mappa.
	 * @param rowNumber
	 *            Il numero di righe di tale matrice.
	 * @param columnNumber
	 *            Il numero di colonne di tale matrice.
	 */
	public void refreshMap(int rowNumber, int columnNumber){
		
		for(int i=0;i<rowNumber;i++){
			for(int j=0; j<columnNumber; j++){
				this.refreshSingleCell(i,j, this.mainApp.getqGridMap()[i][j].getCellType(), 0);
			}
		}
	}
	
	
	/**
	 * Refresh parziale della mappa
	 * 
	 * @param charMap
	 *            La matrice di caratteri fornente una rappresentazione
	 *            testuale della mappa.
	 * @param attributesMap
	 *            La matrice di interi rappresentante il valore numerico
	 *            associato ad una cella della mappa, ove presente.
	 *            (Id dinosauro oppure energia cella).
	 * @param startCell
	 *            La cella di partenza (in coordinate (x,y)).
	 * @param diffRow
	 *            Il numero di righe da ridisegnare a partire dalla cella di partenza.
	 * @param diffColumn
	 *            Il numero di righe da ridisegnare a partire dalla cella di partenza.
	 */
	public void refreshPartialMap(char[][] charMap, int[][] attributesMap, Coordinates startCell,int diffRow, int diffColumn){
		
		Coordinates startCellGUI = new Coordinates(0,0);
		
		startCellGUI.XYtoRC(startCell.getRow(), startCell.getColumn());
		
		int i = 0, j = 0;
		while(i<diffRow && (startCellGUI.getRow()-i)>=0){
			j=0;
			while(j<diffColumn && (startCellGUI.getColumn()+j)<40){				
				this.refreshSingleCell(startCellGUI.getRow()-i,startCellGUI.getColumn()+j, CellType.PLAIN, attributesMap[i][j]);
				j++;
			}
			i++;
			
		}
		
	}
	
	/**
	 * Refresh della singola cella.
	 * 
	 * @param row
	 *            L' indice di riga identificante la cella.
	 * @param column
	 *            L' indice di colonna identificante la cella.
	 * @param type
	 *            Il tipo della cella.
	 * @param attribute
	 *            L'eventuale attributo numerico associato alla cella,
	 *            altrimenti 0 se non presente.
	 */
	public void refreshSingleCell(int row, int column,CellType type,int attribute){
		
		JButton currentButton = this.map[row][column];
		Dimension iconDimension = null;
		String actionCommand = null;
		Coordinates currPosition = new Coordinates(row,column);

		
		switch(type){
		case PLAIN:currentButton.setIcon(PLAINICON);
				 iconDimension = new Dimension(PLAINICON.getIconHeight(),PLAINICON.getIconWidth());
				 currentButton.setToolTipText("Floor");
				 break;
				 
		case AGENT:currentButton.setIcon(AGENTICON);
		 		 iconDimension = new Dimension(AGENTICON.getIconHeight(),AGENTICON.getIconWidth());
		 		 currentButton.setToolTipText("Agent");
				 break;
				 
		case BONUS:
		case MALUS:currentButton.setIcon(BONUSICON);
		 		 iconDimension = new Dimension(BONUSICON.getIconHeight(),BONUSICON.getIconWidth());
		 		 currentButton.setToolTipText("Bonus");
		 		 break;
		 		 
		/*case STARTPOINT:if(attribute %2 == 0){ 
				 	currentButton.setIcon(HERBIVOROUSICON);
				 	iconDimension = new Dimension(HERBIVOROUSICON.getIconHeight(),HERBIVOROUSICON.getIconWidth());
				 	currentButton.setToolTipText("Terreno\ncon\nErbivoro");
				 	break;
				 }
				 else{
					 currentButton.setIcon(CARNIVOROUSICON);
					 iconDimension = new Dimension(CARNIVOROUSICON.getIconHeight(),CARNIVOROUSICON.getIconWidth());
					 currentButton.setToolTipText("Terreno\ncon\nErbivoro");
					 break;
				 }*/
		
		
		case ENDPOINT:currentButton.setIcon(ENDPOINTICON);
		 		 iconDimension = new Dimension(ENDPOINTICON.getIconHeight(),ENDPOINTICON.getIconWidth());
		 		 currentButton.setToolTipText("Goal");
		 		 break;
		 		 
		case WALL:currentButton.setIcon(WALLICON);
		 		 iconDimension = new Dimension(WALLICON.getIconHeight(),WALLICON.getIconWidth());
		 		 currentButton.setToolTipText("Wall");
		 		 break;
		
		case PORTAL1:currentButton.setIcon(PORTAL1ICON);
		 		iconDimension = new Dimension(PORTAL1ICON.getIconHeight(),PORTAL1ICON.getIconWidth());
		 		currentButton.setToolTipText("Portal-Type 1");
		 		break;
		
		case PORTAL2:currentButton.setIcon(PORTAL2ICON);
 		iconDimension = new Dimension(PORTAL2ICON.getIconHeight(),PORTAL2ICON.getIconWidth());
 		currentButton.setToolTipText("Portal-Type 2");
 		break;

		case PORTAL3:currentButton.setIcon(PORTAL3ICON);
 		iconDimension = new Dimension(PORTAL3ICON.getIconHeight(),PORTAL3ICON.getIconWidth());
 		currentButton.setToolTipText("Portal-Type 3");
 		break;

		case PORTAL4:currentButton.setIcon(PORTAL4ICON);
 		iconDimension = new Dimension(PORTAL4ICON.getIconHeight(),PORTAL4ICON.getIconWidth());
 		currentButton.setToolTipText("Portal-Type 4");
 		break;

		 		
		default:break;
		}
		
		//actionCommand = new String(currPosition.getX()+","+currPosition.getY()+","+type+","+attribute);
		currentButton.setBorderPainted(false);
		currentButton.setPreferredSize(iconDimension);
		currentButton.setContentAreaFilled(false);
		currentButton.setActionCommand(actionCommand);
	}


	
	class ActionButtonListener implements ActionListener{
		
		private TAIQLearningApp mainApp;
		
		
		public ActionButtonListener(TAIQLearningApp mainApp) {
			super();
			this.mainApp = mainApp;
		}


		@Override
		public void actionPerformed(ActionEvent e) {
			String listenedCommand = e.getActionCommand();
			String response = null;
			
			if(listenedCommand.equals("Save Map")){
				if(!(this.mainApp.getFileHandler().writeMapToFile("test.map"))){
					Logger.getLogger("src.appLogger").severe("Error in writing file");
					JOptionPane.showMessageDialog(this.mainApp.getAppWindow(),
						    "Error","Error in writing file. See log for details.",JOptionPane.ERROR_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(this.mainApp.getAppWindow(),"Map saving",
						    "Map saved correctly to test.map.",JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			if(listenedCommand.equals("Load Map")){
				QGridCell[][] grid = null;
				grid = GridFileHandler.readMapFromFile("test.map");
				if(grid == null){
					Logger.getLogger("src.appLogger").severe("Error in reading file");
					JOptionPane.showMessageDialog(this.mainApp.getAppWindow(),
						    "Error","Error in reading file. See log for details.",JOptionPane.ERROR_MESSAGE);
				}
				else{
				this.mainApp.setqGridMap(grid);
				this.mainApp.getMapGUI().refreshMap(TAIQLearningApp.MAPHEIGHT,TAIQLearningApp.MAPWIDTH);
				JOptionPane.showMessageDialog(this.mainApp.getAppWindow(),"Map Loading",
					    "Map loaded correcty from test.map.",JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			if(listenedCommand.equals("Learn Path")){	
			}
			
			if(listenedCommand.equals("Display Q values")){	
			}
			
			if(listenedCommand.equals("Exit")){		
			}
			
		}
}

}

