package gui;

import io.GridFileHandler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import ai.AStarPathFinder;
import ai.UnreachableEndException;

import data.AStarCell;
import data.CellType;
import data.MapCell;
import data.QGrid;
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
	private JPanel mapActionPanel;
	private JPanel qLearningActionPanel;
	private JPanel aStarActionPanel;
	
	
	private JButton[][] map;
	
	private JButton generateMapButton;
	private JButton saveMapButton;
	private JButton loadMapButton;
	private JButton displayMapButton;
	private JButton learnAStarPathButton;
	private JButton displayDistanceMapButton;
	private JButton displayQButton;
	private JButton displayRButton;
	private JCheckBox showAStarSet;
	private JCheckBox showAStarPath;
	
	private JTextArea infoConsole;
	
	private CellButtonListener cellActionListener;
	
	private TAIQLearningApp mainApp;
	
	private boolean isAStarPathLearned;
	private boolean isEndReachable;
	
	
	
	private static final ImageIcon AGENTICON = TAIQLearningApp.importImage(new String("Agent.png"));
	private static final ImageIcon PLAINICON = TAIQLearningApp.importImage(new String("SteelFloor3.png"));
	private static final ImageIcon PLAINQRICON = TAIQLearningApp.importImage(new String("SteelFloor2.png"));
	private static final ImageIcon ENDPOINTICON = TAIQLearningApp.importImage(new String("EndPoint.png"));
	private static final ImageIcon WALLICON = TAIQLearningApp.importImage(new String("Wall.png"));
	private static final ImageIcon BONUSICON = TAIQLearningApp.importImage(new String("Box.png"));
	private static final ImageIcon MALUSICON = TAIQLearningApp.importImage(new String("MalusIcon.png"));
	private static final ImageIcon PORTAL1ICON = TAIQLearningApp.importImage(new String("Portal1.png"));
	private static final ImageIcon PORTAL2ICON = TAIQLearningApp.importImage(new String("Portal2.png"));
	private static final ImageIcon PORTAL3ICON = TAIQLearningApp.importImage(new String("Portal3.png"));
	private static final ImageIcon PORTAL4ICON = TAIQLearningApp.importImage(new String("Portal4.png"));
	private static final String ATTRIBUTEDELIMITER = new String(",");
	//private static final ImageIcon FOGICON = GUIElement.importImage(FOGPATH);
	
	

	public MapGUI(TAIQLearningApp aMainApp) {
		super();
		
		mainApp = aMainApp;
		isAStarPathLearned = false;
		
		contentPane = new JPanel();
		contentPane.setOpaque(true); 
		contentPane.setLayout(new BorderLayout());
        
		sidePanel = new JPanel();
		sidePanel.setOpaque(true);
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
	
		Border actionPanelBorder = BorderFactory.createTitledBorder("Actions");
		actionPanel = new JPanel();
		actionPanel.setBorder(actionPanelBorder);
		actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
		
		Border mapActionPanelBorder = BorderFactory.createTitledBorder("Map");
		mapActionPanel = new JPanel();
		mapActionPanel.setBorder(mapActionPanelBorder);
		mapActionPanel.setLayout(new BoxLayout(mapActionPanel, BoxLayout.Y_AXIS));
		
		Border aStarActionPanelBorder = BorderFactory.createTitledBorder("A*");
		aStarActionPanel = new JPanel();
		aStarActionPanel.setBorder(aStarActionPanelBorder);
  		aStarActionPanel.setLayout(new BoxLayout(aStarActionPanel, BoxLayout.Y_AXIS));

		Border qLearningActionPanelBorder = BorderFactory.createTitledBorder("Q-Learning");
		qLearningActionPanel = new JPanel();
		qLearningActionPanel.setBorder(qLearningActionPanelBorder);
		qLearningActionPanel.setLayout(new BoxLayout(qLearningActionPanel, BoxLayout.Y_AXIS));

		generateMapButton = new JButton("Generate Map");
		generateMapButton.addActionListener(new ActionElementsListener(this.mainApp));
		saveMapButton = new JButton("Save Map");
		saveMapButton.addActionListener(new ActionElementsListener(this.mainApp));
		loadMapButton = new JButton("Load Map");
		loadMapButton.addActionListener(new ActionElementsListener(this.mainApp));
		displayMapButton = new JButton("Display map");
		displayMapButton.addActionListener(new ActionElementsListener(this.mainApp));
		learnAStarPathButton = new JButton("Learn A* Path");
		learnAStarPathButton.addActionListener(new ActionElementsListener(this.mainApp));
		displayDistanceMapButton = new JButton("Display distance map");
		displayDistanceMapButton.addActionListener(new ActionElementsListener(this.mainApp));
		displayRButton = new JButton("Display r values");
		displayRButton.addActionListener(new ActionElementsListener(this.mainApp));
		displayQButton = new JButton("Display Q values");
		displayQButton.addActionListener(new ActionElementsListener(this.mainApp));
		
		showAStarPath = new JCheckBox("Show A* Path");
		showAStarPath.addItemListener(new ActionElementsListener(mainApp));
		showAStarSet = new JCheckBox("Show A* Set");
		showAStarSet.addItemListener(new ActionElementsListener(mainApp));

		showAStarPath.setEnabled(isAStarPathLearned);
		showAStarSet.setEnabled(isAStarPathLearned);
		displayDistanceMapButton.setEnabled(isAStarPathLearned);
		
		mapActionPanel.add(generateMapButton);
		mapActionPanel.add(saveMapButton);
		mapActionPanel.add(loadMapButton);
		mapActionPanel.add(displayMapButton);
		aStarActionPanel.add(learnAStarPathButton);
		aStarActionPanel.add(showAStarSet);
		aStarActionPanel.add(showAStarPath);
		aStarActionPanel.add(displayDistanceMapButton);
		qLearningActionPanel.add(displayRButton);
		qLearningActionPanel.add(displayQButton);
		
		
		actionPanel.add(mapActionPanel);
		actionPanel.add(aStarActionPanel);
		actionPanel.add(qLearningActionPanel);
		sidePanel.add(actionPanel);
		     
		map = new JButton[QGrid.MAPHEIGHT][QGrid.MAPWIDTH];
		//this.cellActionListener = new MapActionListener();
		
		Border gridBorder = BorderFactory.createLoweredBevelBorder();
		
		cellGrid = new JPanel();
		cellGrid.setOpaque(true); 
		cellGrid.setLayout(new GridLayout(QGrid.MAPHEIGHT,QGrid.MAPWIDTH,0,0));
		cellGrid.setBorder(gridBorder);
		cellActionListener = new CellButtonListener(this.mainApp);
		
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				map[i][j] = new JButton();
				cellGrid.add(this.map[i][j]);
				map[i][j].addActionListener(this.cellActionListener);
			}
		}
		
		mapPanel = new JScrollPane(cellGrid);
		mapPanel.setPreferredSize(new Dimension(600,400));
		
		Border consolePanelBorder = BorderFactory.createTitledBorder("Console");
		consolePanel = new JPanel();
		infoConsole = new JTextArea(7,100);
		infoConsole.setEditable(false);
		infoConsole.setLineWrap(true);
		infoConsole.setWrapStyleWord(true);
		consolePanel.add(infoConsole);
		consolePanel.setBackground(Color.WHITE);
		consoleScrollPanel = new JScrollPane(this.consolePanel);
		consoleScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		consoleScrollPanel.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		consoleScrollPanel.setBorder(consolePanelBorder);
        consoleScrollPanel.setBackground(Color.WHITE);
		
		contentPane.add(mapPanel,BorderLayout.CENTER);
        contentPane.add(consoleScrollPanel,BorderLayout.SOUTH);
        contentPane.add(sidePanel,BorderLayout.EAST);
        refreshMap(QGrid.MAPHEIGHT,QGrid.MAPWIDTH);
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
		return learnAStarPathButton;
	}

	public void setLearnPathButton(JButton learnPathButton) {
		this.learnAStarPathButton = learnPathButton;
	}

	public JButton getDisplayQButton() {
		return displayQButton;
	}

	public void setDisplayQButton(JButton displayQButton) {
		this.displayQButton = displayQButton;
	}
	
	public CellButtonListener getCellActionListener() {
		return cellActionListener;
	}

	public void setCellActionListener(CellButtonListener cellActionListener) {
		this.cellActionListener = cellActionListener;
	}

	public void setMainApp(TAIQLearningApp mainApp) {
		this.mainApp = mainApp;
	}

	public JButton getSaveMapButton() {
		return saveMapButton;
	}

	public void setSaveMapButton(JButton saveMapButton) {
		this.saveMapButton = saveMapButton;
	}

	public JTextArea getInfoConsole() {
		return infoConsole;
	}

	public void setInfoConsole(JTextArea infoConsole) {
		this.infoConsole = infoConsole;
	}

	public boolean isEndReachable() {
		return isEndReachable;
	}

	public void setEndReachable(boolean isEndReachable) {
		this.isEndReachable = isEndReachable;
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
				this.refreshSingleCell(i,j, this.mainApp.getqGridMap().getCell(i, j).getCellType(),this.mainApp.getqGridMap().getCell(i, j).getCellReward());		
			}
		}
	}
	
	public void refreshDistanceMap(int rowNumber, int columnNumber){
		
		for(int i=0;i<rowNumber;i++){
			for(int j=0; j<columnNumber; j++){
				this.refreshSingleCellQReward(i,j,this.mainApp.getaStarGridMap().getCell(i, j).getDistanceFromAgent()) ;
				
			}
		}
	}
	
	public void refreshMapQ(int rowNumber, int columnNumber){
		
		for(int i=0;i<rowNumber;i++){
			for(int j=0; j<columnNumber; j++){
				this.refreshSingleCellQReward(i,j,this.mainApp.getqGridMap().getCell(i, j).getCellQValue()) ;
				
			}
		}
	}
	
	public void refreshMapReward(int rowNumber, int columnNumber){
		
		for(int i=0;i<rowNumber;i++){
			for(int j=0; j<columnNumber; j++){
				this.refreshSingleCellQReward(i,j,this.mainApp.getqGridMap().getCell(i, j).getCellReward()) ;
				
			}
		}
	}
	
	public void drawAStarPath(){
		
		for(AStarCell currCell : this.mainApp.getaStarGridMap().getaStarPath()){
			switch(currCell.getCellType()){
			
			case BONUS:		break;
			case PORTAL1:
			case PORTAL2:
			case PORTAL3:
			case PORTAL4:	this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setText("A*");
							this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setHorizontalTextPosition(JButton.CENTER);
		 					this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setVerticalTextPosition(JButton.CENTER);
		 					this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setForeground(Color.GREEN); 
						 	break;
			case MALUS:
			case AGENT:
			case ENDPOINT: break;
			
			default:	this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setIcon(null);
					 	this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setContentAreaFilled(true);
					 	this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setBackground(Color.GREEN);
					 	this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setText("");
					 	break;
			}
		}
	}
	
	public void drawAStarSet(){
		
		for(AStarCell currCell : this.mainApp.getaStarGridMap().getaStarSet()){
			switch(currCell.getCellType()){
			
			case BONUS:		break;
			case PORTAL1:
			case PORTAL2:
			case PORTAL3:
			case PORTAL4:	this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setText("A*");
							this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setHorizontalTextPosition(JButton.CENTER);
		 					this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setVerticalTextPosition(JButton.CENTER);
		 					this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setForeground(Color.ORANGE); 
						 	break;
			case MALUS:
			case AGENT:
			case ENDPOINT: break;
			
			default:	this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setIcon(null);
					 	this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setContentAreaFilled(true);
					 	this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setBackground(Color.ORANGE);
					 	this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setText("");
					 	break;
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
	public void refreshPartialMap(ArrayList<AStarCell> refreshCells){
		int i = 0;
		int j = 0;
		
		for(MapCell iterCell: refreshCells){
			i = iterCell.getRowIndex();
			j = iterCell.getColumnIndex();
				this.refreshSingleCell(i,j, this.mainApp.getqGridMap().getCell(i, j).getCellType(),this.mainApp.getqGridMap().getCell(i, j).getCellReward());		
			}
	}
	
	/*public void refreshPartialMap(ArrayList<QGridCell> refreshCells){
		int i = 0;
		int j = 0;
		
		for(MapCell iterCell: refreshCells){
			i = iterCell.getRowIndex();
			j = iterCell.getColumnIndex();
				this.refreshSingleCell(i,j, this.mainApp.getqGridMap().getCell(i, j).getCellType(),this.mainApp.getqGridMap().getCell(i, j).getCellReward());		
			}
	}*/
		
		/*Coordinates startCellGUI = new Coordinates(0,0);
		
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
	public void refreshSingleCell(int aRow, int aColumn,CellType aType,double aAttribute){
		
		JButton currentButton = this.map[aRow][aColumn];
		Dimension iconDimension = null;
		String actionCommand = null;
		Double rewardValue = new Double(aAttribute);
		
		switch(aType){
		case PLAIN:currentButton.setIcon(PLAINICON);
		iconDimension = new Dimension(PLAINICON.getIconHeight(),PLAINICON.getIconWidth());
		currentButton.setToolTipText("Floor");
		break;

		case AGENT:currentButton.setIcon(AGENTICON);
		iconDimension = new Dimension(AGENTICON.getIconHeight(),AGENTICON.getIconWidth());
		currentButton.setToolTipText("Agent");
		break;

		case BONUS:currentButton.setIcon(BONUSICON);
		iconDimension = new Dimension(BONUSICON.getIconHeight(),BONUSICON.getIconWidth());
		currentButton.setToolTipText("Bonus");
		break;

		case MALUS:currentButton.setIcon(MALUSICON);
		iconDimension = new Dimension(MALUSICON.getIconHeight(),MALUSICON.getIconWidth());
		currentButton.setToolTipText("Malus");
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


		case ENDPOINT:	currentButton.setIcon(ENDPOINTICON);
		iconDimension = new Dimension(ENDPOINTICON.getIconHeight(),ENDPOINTICON.getIconWidth());
		currentButton.setToolTipText("Goal");
		break;

		case WALL:	currentButton.setIcon(WALLICON);
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
		
		actionCommand = new String(aRow+ATTRIBUTEDELIMITER+aColumn+ATTRIBUTEDELIMITER+rewardValue.toString());
		currentButton.setText("");
		currentButton.setBorderPainted(false);
		currentButton.setPreferredSize(iconDimension);
		currentButton.setContentAreaFilled(false);
		currentButton.setActionCommand(actionCommand);
	}
	
	public void refreshSingleCellQReward(int aRow, int aColumn,double aAttribute){
		
		JButton currentButton = this.map[aRow][aColumn];
		Dimension iconDimension = null;
		String actionCommand = null;
		//Double rewardQValue = new Double(aAttribute);
		
		currentButton.setIcon(PLAINQRICON);
		currentButton.setBackground(Color.WHITE);
		iconDimension = new Dimension(PLAINICON.getIconHeight(),PLAINICON.getIconWidth());
		//currentButton.setText(Double.toString(aAttribute));
		currentButton.setText(Integer.toString((int)aAttribute));
		currentButton.setHorizontalTextPosition(JButton.CENTER);
		currentButton.setVerticalTextPosition(JButton.CENTER);
		currentButton.setForeground(Color.WHITE);
		
		actionCommand = new String(aRow+ATTRIBUTEDELIMITER+aColumn+ATTRIBUTEDELIMITER+Double.toString(aAttribute));
		currentButton.setBorderPainted(false);
		currentButton.setPreferredSize(iconDimension);
		currentButton.setContentAreaFilled(false);
		currentButton.setActionCommand(actionCommand);
	}


	
	class ActionElementsListener implements ActionListener,ItemListener{
		
		private TAIQLearningApp mainApp;
		
		
		public ActionElementsListener(TAIQLearningApp mainApp) {
			super();
			this.mainApp = mainApp;
		}

		public boolean isMapDisplayed(){
			return this.mainApp.getMapGUI().getMap()[0][0].getText().equals("");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String listenedCommand = e.getActionCommand();
			String response = null;
			
			if(listenedCommand.equals("Generate Map")){
				try {
				this.mainApp.setqGridMap(new QGrid());
				this.mainApp.getMapGUI().refreshMap(QGrid.MAPHEIGHT, QGrid.MAPWIDTH);
				this.mainApp.setaStarGridMap(new AStarPathFinder(this.mainApp,this.mainApp.getqGridMap()));
				} catch (UnreachableEndException e1) {
					JOptionPane.showMessageDialog(this.mainApp.getAppWindow(),"The goal is unreachable on the generated map.\nPlease regenerate the map.","Warning",JOptionPane.WARNING_MESSAGE);
				}
				isAStarPathLearned = false;
				showAStarPath.setEnabled(isAStarPathLearned);
				showAStarSet.setEnabled(isAStarPathLearned);
				displayDistanceMapButton.setEnabled(isAStarPathLearned);
				infoConsole.setText("");
			}
			
			if(listenedCommand.equals("Save Map")){
				if(!(this.mainApp.getFileHandler().writeMapToFile("test.map"))){
					Logger.getLogger("src.appLogger").severe("Error in writing file");
					JOptionPane.showMessageDialog(this.mainApp.getAppWindow(),"Error in writing file. See log for details.","Error",JOptionPane.ERROR_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(this.mainApp.getAppWindow(),"Map saved correctly to test.map.","Map saving",JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			if(listenedCommand.equals("Load Map")){
				QGridCell[][] grid = null;
				grid = GridFileHandler.readMapFromFile("test.map");
				if(grid == null){
					Logger.getLogger("src.appLogger").severe("Error in reading file");
					JOptionPane.showMessageDialog(this.mainApp.getAppWindow(),"Error in reading file. See log for details.","Error",JOptionPane.ERROR_MESSAGE);
				}
				else{
				this.mainApp.setqGridMap(new QGrid(grid));
				this.mainApp.getMapGUI().refreshMap(QGrid.MAPHEIGHT,QGrid.MAPWIDTH);
				JOptionPane.showMessageDialog(this.mainApp.getAppWindow(),"Map loaded correcty from test.map.", "Map Loading",JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			if(listenedCommand.equals("Display map")){
				//if(!isMapDisplayed()){
				this.mainApp.getMapGUI().refreshMap(QGrid.MAPHEIGHT,QGrid.MAPWIDTH );
				//}
			}
			
			if(listenedCommand.equals("Learn A* Path")){
				this.mainApp.getaStarGridMap().findAStarPath();
				isAStarPathLearned = true;
				showAStarPath.setEnabled(isAStarPathLearned);
				showAStarSet.setEnabled(isAStarPathLearned);
				displayDistanceMapButton.setEnabled(isAStarPathLearned);
				
			}
			
			if(listenedCommand.equals("Display distance map")){
				//if(isMapDisplayed()){
				this.mainApp.getMapGUI().refreshDistanceMap(QGrid.MAPHEIGHT,QGrid.MAPWIDTH );
				//}
			}
			
			if(listenedCommand.equals("Display r values")){
				//if(isMapDisplayed()){
				this.mainApp.getMapGUI().refreshMapReward(QGrid.MAPHEIGHT,QGrid.MAPWIDTH );
				//}
			}
			
			if(listenedCommand.equals("Display Q values")){
				
				this.mainApp.getMapGUI().refreshMapQ(QGrid.MAPHEIGHT,QGrid.MAPWIDTH );
					
			}
			
			if(listenedCommand.equals("Exit")){		
			}
			
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			 Object source = e.getItemSelectable();
			 
		        if (e.getItemSelectable().equals(showAStarPath)) {
		            if(e.getStateChange() == ItemEvent.SELECTED){
		            	if(showAStarSet.isSelected()){
			            	this.mainApp.getMapGUI().drawAStarSet();
			            }
		            	this.mainApp.getMapGUI().drawAStarPath();
		            }
		            if(e.getStateChange() == ItemEvent.DESELECTED){
		            	this.mainApp.getMapGUI().refreshPartialMap(this.mainApp.getaStarGridMap().getaStarPath());
		            	if(showAStarSet.isSelected()){
			            	this.mainApp.getMapGUI().drawAStarSet();
			            }
		            }
		            
		        }
		        if (e.getItemSelectable().equals(showAStarSet)) {
		        	if(e.getStateChange() == ItemEvent.SELECTED){
		            	this.mainApp.getMapGUI().drawAStarSet();
		            	if(showAStarPath.isSelected()){
			            	this.mainApp.getMapGUI().drawAStarPath();
			            }
		            }
		            if(e.getStateChange() == ItemEvent.DESELECTED){
		            	this.mainApp.getMapGUI().refreshPartialMap(this.mainApp.getaStarGridMap().getaStarSet());
		            	if(showAStarPath.isSelected()){
			            	this.mainApp.getMapGUI().drawAStarPath();
			            }
		            }
		            
		        }
			
		}
	}
	
	class CellButtonListener implements ActionListener{
		
		private TAIQLearningApp mainApp;
		private int row;
		private int column;
		
		
		public CellButtonListener(TAIQLearningApp mainApp) {
			super();
			this.mainApp = mainApp;
		}


		@Override
		public void actionPerformed(ActionEvent e) {
			String listenedCommand = e.getActionCommand(); 
			parseActionCommand(listenedCommand);
			AStarCell currCell = this.mainApp.getaStarGridMap().getCell(this.row, this.column);
			this.mainApp.getMapGUI().getInfoConsole().append("Reachable Cells from" + "("+this.row+","+this.column+"):\n");
			for(MapCell reachableCell : currCell.getReachableCells())
			{
				AStarCell aCell = (AStarCell)reachableCell;
				this.mainApp.getMapGUI().getInfoConsole().append("("+reachableCell.getRowIndex()+","+reachableCell.getColumnIndex()+") - "+"h: "+new Double(aCell.getHeuristicDistanceFromGoal()+aCell.getDistanceFromAgent())+"\n");
			}
		}
		
		public void parseActionCommand(String actionCommand){
			String[] firstSplit = actionCommand.split(ATTRIBUTEDELIMITER);
			this.row = Integer.parseInt(firstSplit[0]);
			this.column = Integer.parseInt(firstSplit[1]);
		}
		
	}
    }

