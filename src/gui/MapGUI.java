package gui;

import gui.GraphicalGUI.GraphicalGUIListener;
import gui.GraphicalGUI.TurnCommandListener;
import gui.MapHandler.MapActionListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import data.CellType;

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
	
	private JButton loadMapButton;
	private JButton learnPathButton;
	private JButton displayQButton;
	
	private JTextArea infoConsole;
	
	private MapActionListener cellActionListener;
	
	private TAIQLearningApp mainApp;
	
	
	private static final String FOGPATH = new String("Nebbia.png");
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
		
		this.loadMapButton = new JButton("Load Map");
		this.loadMapButton.addActionListener(new ActionButtonListener());
		this.learnPathButton = new JButton("Learn Path");
		this.learnPathButton.addActionListener(new ActionButtonListener());
		this.displayQButton = new JButton("Display Q values");
		this.displayQButton.addActionListener(new ActionButtonListener());
		
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
		this.infoConsole = new JTextArea();
		this.infoConsole.setEditable(false);
		this.infoConsole.setLineWrap(true);
		this.infoConsole.setWrapStyleWord(true);
		this.consolePanel.add(infoConsole);
		this.consoleScrollPanel = new JScrollPane(this.consolePanel);
		this.consoleScrollPanel.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.consoleScrollPanel.setBorder(consolePanelBorder);
        
		contentPane.add(mapPanel,BorderLayout.CENTER);
        contentPane.add(consoleScrollPanel,BorderLayout.SOUTH);
        contentPane.add(sidePanel,BorderLayout.EAST);
        
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
	public void refreshMap(char[][] charMap, int rowNumber, int columnNumber){
		
		for(int i=rowNumber-1;i>=0;i--){
			for(int j=0; j<columnNumber; j++){
				this.refreshSingleCell(rowNumber-1-i,j, this.mainApp.getqGridMap()[i][j].getCellType(), 0);
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
				this.refreshSingleCell(startCellGUI.getRow()-i,startCellGUI.getColumn()+j, charMap[i][j], attributesMap[i][j]);
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
		case PLAIN:currentButton.setIcon(WATERICON);
				 iconDimension = new Dimension(WATERICON.getIconHeight(),WATERICON.getIconWidth());
				 currentButton.setToolTipText("Acqua");
				 break;
				 
		case AGENT:currentButton.setIcon(FOGICON);
		 		 iconDimension = new Dimension(FOGICON.getIconHeight(),FOGICON.getIconWidth());
		 		 currentButton.setToolTipText("????");
				 break;
				 
		case BONUS:
		case MALUS:currentButton.setIcon(DEADBODYICON);
		 		 iconDimension = new Dimension(DEADBODYICON.getIconHeight(),DEADBODYICON.getIconWidth());
		 		 currentButton.setToolTipText("Terreno\ncon\nCarogna");
		 		 break;
		 		 
		case STARTPOINT:if(attribute %2 == 0){ 
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
				 }
		
		
		case ENDPOINT:currentButton.setIcon(TERRAINICON);
		 		 iconDimension = new Dimension(TERRAINICON.getIconHeight(),TERRAINICON.getIconWidth());
		 		 currentButton.setToolTipText("Terreno");
		 		 break;
		 		 
		case WALL:currentButton.setIcon(GRASSICON);
		 		 iconDimension = new Dimension(GRASSICON.getIconHeight(),GRASSICON.getIconWidth());
		 		 currentButton.setToolTipText("Terreno\ncon\nVegetazione");
		 		 break;
			
		default:break;
		}
		
		actionCommand = new String(currPosition.getX()+","+currPosition.getY()+","+type+","+attribute);
		currentButton.setBorderPainted(false);
		currentButton.setPreferredSize(iconDimension);
		currentButton.setContentAreaFilled(false);
		currentButton.setActionCommand(actionCommand);
	}


	
	class ActionButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String listenedCommand = e.getActionCommand();
			String response = null;
			
			if(listenedCommand.equals("Load Map")){
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

