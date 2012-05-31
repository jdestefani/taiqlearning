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
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;


import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ai.AStarThread;
import ai.QGrid;
import ai.QLearnThread;
import ai.QLearning;
import ai.UnreachableEndException;

import data.AStarCell;
import data.MapCell;
import data.QGridCell;

import main.TAIQLearningApp;
import gui.GUICell;

/**
 * The Class MapGUI.
 */
public class MapGUI {

	/** The QL thread. */
	private QLearning QLThread;
	
	/** The Q learning thread. */
	private QLearnThread QLearningThread;
	
	/** The content pane. */
	private JPanel contentPane;
	
	/** The map panel. */
	private JScrollPane mapPanel;
	
	/** The cell grid. */
	private JPanel cellGrid;
	
	/** The side panel. */
	private JPanel sidePanel;
	
	/** The action panel. */
	private JPanel actionPanel;
	
	/** The map action panel. */
	private JPanel mapActionPanel;
	
	/** The q learning action panel. */
	private JPanel qLearningActionPanel;
	
	/** The a star action panel. */
	private JPanel aStarActionPanel;
	
	/** The info panel. */
	private JPanel infoPanel;
	
	/** The a star info panel. */
	private JPanel aStarInfoPanel;
	
	/** The q learn info panel. */
	private JPanel qLearnInfoPanel;
	
	/** The console panel. */
	private JPanel consolePanel;
	
	/** The a star console scroll panel. */
	private JScrollPane aStarConsoleScrollPanel;
	
	/** The q learn console scroll panel. */
	private JScrollPane qLearnConsoleScrollPanel;
	
	
	/** The map. */
	private GUICell[][] map;
	
	/** The generate map button. */
	private JButton generateMapButton;
	
	/** The learn a star path button. */
	private JButton learnAStarPathButton;

	/** The display q start button. */
	private JButton displayQStartButton;
	
	/** The display q reset button. */
	private JButton displayQResetButton;
	
	/** The display q learn stop button. */
	private JButton displayQLearnStopButton;
	
	/** The display menu bar. */
	private JMenuBar displayMenuBar;
	
	/** The map menu. */
	private JMenu mapMenu;
	
	/** The save map menu item. */
	private JMenuItem saveMapMenuItem;
	
	/** The load map menu item. */
	private JMenuItem loadMapMenuItem;
	
	/** The display map menu item. */
	private JMenuItem displayMapMenuItem;
	
	/** The a star menu. */
	private JMenu aStarMenu;
	
	/** The display distance map. */
	private JCheckBoxMenuItem displayDistanceMap;
	
	/** The show a star set. */
	private JCheckBoxMenuItem showAStarSet;
	
	/** The show a star path. */
	private JCheckBoxMenuItem showAStarPath;
	
	/** The q learning menu. */
	private JMenu qLearningMenu;
	
	/** The show q visited set. */
	private JCheckBoxMenuItem showQVisitedSet;
	
	/** The show q visited path. */
	private JCheckBoxMenuItem showQVisitedPath;
	
	/** The about menu item. */
	private JMenuItem aboutMenuItem;
	
	/** The a star path info. */
	private JLabel aStarPathInfo;
	
	/** The a star set info. */
	private JLabel aStarSetInfo;
	
	/** The q learning visited info. */
	private JLabel qLearningVisitedInfo;
	
	/** The q learning path info. */
	private JLabel qLearningPathInfo;
	
	
	/** The q trial label. */
	private JLabel qTrialLabel;
	
	/** The q trials slider. */
	private JSlider qTrialsSlider;
	
	/** The a star console. */
	private JTextArea aStarConsole;
	
	/** The q learn console. */
	private JTextArea qLearnConsole;
	
	/** The cell action listener. */
	private CellButtonListener cellActionListener;
	
	/** The main app. */
	private TAIQLearningApp mainApp;
	
	/** The Constant CONSOLEROWNUMBER. */
	private static final int CONSOLEROWNUMBER = 7;
	
	/** The Constant CONSOLECOLUMNNUMBER. */
	private static final int CONSOLECOLUMNNUMBER = 50;
	
	/** The Constant MINTICKSSPACING. */
	private static final int MINTICKSSPACING = 5;
	
	/** The Constant MAXTICKSSPACING. */
	private static final int MAXTICKSSPACING = 10;
	
	/** The Constant ABOUTIMG. */
	private static final ImageIcon ABOUTIMG = TAIQLearningApp.importImage(new String("AboutTAI.jpg"));
	
	/** The Constant PATHINFOTEXT. */
	public final static String PATHINFOTEXT = new String("Path Length: ");
	
	/** The Constant SETINFOTEXT. */
	public final static String SETINFOTEXT = new String("Visited Cells: ");
	
	/** The is a star path learned. */
	private boolean isAStarPathLearned;
	
	/** The is a star path learned. */
	private boolean isQLearnPathLearned;
	
	/** The is end reachable. */
	private boolean isEndReachable;

	
	
	

	/**
	 * Instantiates a new map gui.
	 *
	 * @param aMainApp the a main app
	 * @param aIsEndReachable the a is end reachable
	 */
	public MapGUI(TAIQLearningApp aMainApp,boolean aIsEndReachable) {
		super();
		
		mainApp = aMainApp;
		isAStarPathLearned = false;
		isQLearnPathLearned = false;
		isEndReachable = aIsEndReachable;
		
		contentPane = new JPanel();
		contentPane.setOpaque(true); 
		contentPane.setLayout(new BorderLayout());
        
		//Side panel creation
		sidePanel = new JPanel();
		sidePanel.setOpaque(true);
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
		
		//	Subpanels creation
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
		learnAStarPathButton = new JButton("Compute A* Path");
		learnAStarPathButton.addActionListener(new ActionElementsListener(this.mainApp));
		displayQStartButton = new JButton("Start learning");
		displayQStartButton.addActionListener(new ActionElementsListener(this.mainApp));
		displayQResetButton = new JButton("Reset");
		displayQResetButton.addActionListener(new ActionElementsListener(this.mainApp));
		displayQLearnStopButton = new JButton("Stop learning");
		displayQLearnStopButton.addActionListener(new ActionElementsListener(this.mainApp));
		
		//	Menu items creation
		saveMapMenuItem = new JMenuItem("Save Map");
		saveMapMenuItem.addActionListener(new ActionElementsListener(this.mainApp));
		loadMapMenuItem = new JMenuItem("Load Map");
		loadMapMenuItem.addActionListener(new ActionElementsListener(this.mainApp));
		displayMapMenuItem = new JMenuItem("Display map");
		displayMapMenuItem.addActionListener(new ActionElementsListener(this.mainApp));
		aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new ActionElementsListener(this.mainApp));
		
		
		displayDistanceMap = new JCheckBoxMenuItem("Display distance map");
		displayDistanceMap.addItemListener(new ActionElementsListener(mainApp));
		showAStarPath = new JCheckBoxMenuItem("Show A* Path");
		showAStarPath.addItemListener(new ActionElementsListener(mainApp));
		showAStarSet = new JCheckBoxMenuItem("Show A* Set");
		showAStarSet.addItemListener(new ActionElementsListener(mainApp));
		showQVisitedSet = new JCheckBoxMenuItem("Show visited cells");
		showQVisitedSet.addItemListener(new ActionElementsListener(mainApp));
		showQVisitedPath = new JCheckBoxMenuItem("Show Q-Learning path");
		showQVisitedPath.addItemListener(new ActionElementsListener(mainApp));
		
		//Slider creation
		qTrialsSlider = new JSlider(QLearning.MINTRIALSNUMBER, QLearning.MAXTRIALSNUMBER, QLearning.MINTRIALSNUMBER);
		qTrialsSlider.setMinorTickSpacing(MINTICKSSPACING);
		qTrialsSlider.setMajorTickSpacing(MAXTICKSSPACING);
		qTrialsSlider.setPaintTicks(true);
		qTrialsSlider.setPaintLabels(true);
		qTrialsSlider.addChangeListener(new ActionElementsListener(mainApp));
		
		qTrialLabel = new JLabel("Q-Learning Trials",JLabel.CENTER);
		
		//Setting buttons initial state
		learnAStarPathButton.setEnabled(isEndReachable);
		displayQStartButton.setEnabled(isEndReachable);
		displayQLearnStopButton.setEnabled(isEndReachable);
		displayQResetButton.setEnabled(isEndReachable);
		displayDistanceMap.setEnabled(isAStarPathLearned);
		showAStarPath.setEnabled(isAStarPathLearned);
		showAStarSet.setEnabled(isAStarPathLearned);
		showQVisitedPath.setEnabled(isQLearnPathLearned);
		showQVisitedSet.setEnabled(isQLearnPathLearned);

		
		mapActionPanel.add(generateMapButton);
		aStarActionPanel.add(learnAStarPathButton);
		qLearningActionPanel.add(qTrialLabel);
		qLearningActionPanel.add(qTrialsSlider);
		qLearningActionPanel.add(displayQStartButton);
		qLearningActionPanel.add(displayQLearnStopButton);
		qLearningActionPanel.add(displayQResetButton);
		
		//Info section construction
		Border infoPanelBorder = BorderFactory.createTitledBorder("Information");
		infoPanel = new JPanel();
		infoPanel.setOpaque(true);
		infoPanel.setBorder(infoPanelBorder);
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setSize(actionPanel.getWidth(), actionPanel.getHeight());
		
		aStarInfoPanel = new JPanel();
		aStarInfoPanel.setOpaque(true);
		aStarInfoPanel.setBorder(aStarActionPanelBorder);
		aStarInfoPanel.setLayout(new BoxLayout(aStarInfoPanel, BoxLayout.Y_AXIS));
		
		aStarSetInfo = new JLabel(SETINFOTEXT+"ND");
		aStarPathInfo = new JLabel(PATHINFOTEXT+"ND");
		
		aStarInfoPanel.add(aStarSetInfo);
		aStarInfoPanel.add(aStarPathInfo);		
		infoPanel.add(aStarInfoPanel);
		
		qLearnInfoPanel = new JPanel();
		qLearnInfoPanel.setOpaque(true);
		qLearnInfoPanel.setBorder(qLearningActionPanelBorder);
		qLearnInfoPanel.setLayout(new BoxLayout(qLearnInfoPanel, BoxLayout.Y_AXIS));
		
		qLearningVisitedInfo = new JLabel(SETINFOTEXT+"ND");
		qLearningPathInfo = new JLabel(PATHINFOTEXT+"ND");
		
		qLearnInfoPanel.add(qLearningVisitedInfo);
		qLearnInfoPanel.add(qLearningPathInfo);
		infoPanel.add(qLearnInfoPanel);
		
		actionPanel.add(mapActionPanel);
		actionPanel.add(aStarActionPanel);
		actionPanel.add(qLearningActionPanel);
		sidePanel.add(actionPanel);
		sidePanel.add(infoPanel);
		     
		map = new GUICell[QGrid.MAPHEIGHT][QGrid.MAPWIDTH];
		//this.cellActionListener = new MapActionListener();
		
		Border gridBorder = BorderFactory.createLoweredBevelBorder();
		
		cellGrid = new JPanel();
		cellGrid.setOpaque(true); 
		cellGrid.setLayout(new GridLayout(QGrid.MAPHEIGHT,QGrid.MAPWIDTH,0,0));
		cellGrid.setBorder(gridBorder);
		cellActionListener = new CellButtonListener(this.mainApp);
		
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				map[i][j] = new GUICell(i,j);
				cellGrid.add(this.map[i][j]);
				map[i][j].addActionListener(this.cellActionListener);
			}
		}
		
		mapPanel = new JScrollPane(cellGrid);
		mapPanel.setPreferredSize(new Dimension(600,400));
		
		
		consolePanel = new JPanel();
		
		Border aStarConsolePanelBorder = BorderFactory.createTitledBorder("A* console");
		aStarConsole = new JTextArea(CONSOLEROWNUMBER, CONSOLECOLUMNNUMBER);
		aStarConsole.setEditable(false);
		aStarConsole.setLineWrap(true);
		aStarConsoleScrollPanel = new JScrollPane(aStarConsole);
		aStarConsoleScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		aStarConsoleScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		aStarConsoleScrollPanel.setBorder(aStarConsolePanelBorder);
		aStarConsoleScrollPanel.setBackground(Color.WHITE);
		
		Border qLearnConsolePanelBorder = BorderFactory.createTitledBorder("Q-Learning console");
		qLearnConsole = new JTextArea(CONSOLEROWNUMBER, CONSOLECOLUMNNUMBER);
		qLearnConsole.setEditable(false);
		qLearnConsole.setLineWrap(true);
		qLearnConsoleScrollPanel = new JScrollPane(qLearnConsole);
		qLearnConsoleScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		qLearnConsoleScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		qLearnConsoleScrollPanel.setBorder(qLearnConsolePanelBorder);
		qLearnConsoleScrollPanel.setBackground(Color.WHITE);
		
		consolePanel = new JPanel();
		consolePanel.setOpaque(true);
		consolePanel.setLayout(new BoxLayout(consolePanel, BoxLayout.X_AXIS));
		consolePanel.add(aStarConsoleScrollPanel);
		consolePanel.add(qLearnConsoleScrollPanel);
		
        setupDisplayMenu();
        
		contentPane.add(mapPanel,BorderLayout.CENTER);
        contentPane.add(consolePanel,BorderLayout.SOUTH);
        contentPane.add(sidePanel,BorderLayout.EAST);
        refreshMap(QGrid.MAPHEIGHT,QGrid.MAPWIDTH);
	}
	
	public JCheckBoxMenuItem getShowQVisitedPath() {
		return showQVisitedPath;
	}

	/**
	 * Setup display menu.
	 */
	private void setupDisplayMenu() {
		//Create the menu bar.
		displayMenuBar = new JMenuBar();

		mapMenu = new JMenu("Map");
		mapMenu.setMnemonic(KeyEvent.VK_M);
		
		mapMenu.add(saveMapMenuItem);
		mapMenu.add(loadMapMenuItem);
		mapMenu.addSeparator();
		mapMenu.add(displayMapMenuItem);
		mapMenu.addSeparator();
		mapMenu.add(aboutMenuItem);
		
		displayMenuBar.add(mapMenu);
		
		//Build the first menu.
		aStarMenu = new JMenu("A*");
		aStarMenu.setMnemonic(KeyEvent.VK_A);
		
		aStarMenu.add(displayDistanceMap);
		aStarMenu.addSeparator();
		aStarMenu.add(showAStarSet);
		aStarMenu.add(showAStarPath);
		
		displayMenuBar.add(aStarMenu);

		qLearningMenu = new JMenu("Q-Learning");
		qLearningMenu.setMnemonic(KeyEvent.VK_Q);
		qLearningMenu.getAccessibleContext().setAccessibleDescription(
		        "The only menu in this program that has menu items");
		
		qLearningMenu.add(showQVisitedSet);
		qLearningMenu.add(showQVisitedPath);
		
		displayMenuBar.add(qLearningMenu);
		
	}


	
	
	/**
	 * Gets the qL thread.
	 *
	 * @return the qL thread
	 */
	public QLearning getQLThread() {
		return QLThread;
	}

	/**
	 * Gets the q learning thread.
	 *
	 * @return the q learning thread
	 */
	public QLearnThread getQLearningThread() {
		return QLearningThread;
	}

	/**
	 * Gets the content pane.
	 *
	 * @return the content pane
	 */
	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * Gets the map panel.
	 *
	 * @return the map panel
	 */
	public JScrollPane getMapPanel() {
		return mapPanel;
	}

	/**
	 * Gets the cell grid.
	 *
	 * @return the cell grid
	 */
	public JPanel getCellGrid() {
		return cellGrid;
	}

	/**
	 * Gets the side panel.
	 *
	 * @return the side panel
	 */
	public JPanel getSidePanel() {
		return sidePanel;
	}

	/**
	 * Gets the action panel.
	 *
	 * @return the action panel
	 */
	public JPanel getActionPanel() {
		return actionPanel;
	}

	/**
	 * Gets the map action panel.
	 *
	 * @return the map action panel
	 */
	public JPanel getMapActionPanel() {
		return mapActionPanel;
	}

	/**
	 * Gets the q learning action panel.
	 *
	 * @return the q learning action panel
	 */
	public JPanel getqLearningActionPanel() {
		return qLearningActionPanel;
	}

	/**
	 * Gets the a star action panel.
	 *
	 * @return the a star action panel
	 */
	public JPanel getaStarActionPanel() {
		return aStarActionPanel;
	}

	/**
	 * Gets the console panel.
	 *
	 * @return the console panel
	 */
	public JPanel getConsolePanel() {
		return consolePanel;
	}

	/**
	 * Gets the a star console scroll panel.
	 *
	 * @return the a star console scroll panel
	 */
	public JScrollPane getaStarConsoleScrollPanel() {
		return aStarConsoleScrollPanel;
	}
	
	/**
	 * Gets the q learn console scroll panel.
	 *
	 * @return the q learn console scroll panel
	 */
	public JScrollPane getqLearnConsoleScrollPanel() {
		return qLearnConsoleScrollPanel;
	}

	/**
	 * Gets the map.
	 *
	 * @return the map
	 */
	public JButton[][] getMap() {
		return map;
	}

	/**
	 * Gets the generate map button.
	 *
	 * @return the generate map button
	 */
	public JButton getGenerateMapButton() {
		return generateMapButton;
	}

	/**
	 * Gets the learn a star path button.
	 *
	 * @return the learn a star path button
	 */
	public JButton getLearnAStarPathButton() {
		return learnAStarPathButton;
	}

	/**
	 * Gets the display distance map button.
	 *
	 * @return the display distance map button
	 */
	public JCheckBoxMenuItem getDisplayDistanceMapButton() {
		return displayDistanceMap;
	}

	/**
	 * Gets the display q start button.
	 *
	 * @return the display q start button
	 */
	public JButton getDisplayQStartButton() {
		return displayQStartButton;
	}

	/**
	 * Gets the display q reset button.
	 *
	 * @return the display q reset button
	 */
	public JButton getDisplayQResetButton() {
		return displayQResetButton;
	}

	/**
	 * Gets the display q learn stop button.
	 *
	 * @return the display q learn stop button
	 */
	public JButton getDisplayQLearnStopButton() {
		return displayQLearnStopButton;
	}

	/**
	 * Gets the display menu bar.
	 *
	 * @return the display menu bar
	 */
	public JMenuBar getDisplayMenuBar() {
		return displayMenuBar;
	}

	/**
	 * Gets the map menu.
	 *
	 * @return the map menu
	 */
	public JMenu getMapMenu() {
		return mapMenu;
	}

	/**
	 * Gets the save map menu item.
	 *
	 * @return the save map menu item
	 */
	public JMenuItem getSaveMapMenuItem() {
		return saveMapMenuItem;
	}

	/**
	 * Gets the load map menu item.
	 *
	 * @return the load map menu item
	 */
	public JMenuItem getLoadMapMenuItem() {
		return loadMapMenuItem;
	}

	/**
	 * Gets the display map menu item.
	 *
	 * @return the display map menu item
	 */
	public JMenuItem getDisplayMapMenuItem() {
		return displayMapMenuItem;
	}

	/**
	 * Gets the a star menu.
	 *
	 * @return the a star menu
	 */
	public JMenu getaStarMenu() {
		return aStarMenu;
	}

	/**
	 * Gets the show a star set.
	 *
	 * @return the show a star set
	 */
	public JCheckBoxMenuItem getShowAStarSet() {
		return showAStarSet;
	}

	/**
	 * Gets the show a star path.
	 *
	 * @return the show a star path
	 */
	public JCheckBoxMenuItem getShowAStarPath() {
		return showAStarPath;
	}

	/**
	 * Gets the q learning menu.
	 *
	 * @return the q learning menu
	 */
	public JMenu getqLearningMenu() {
		return qLearningMenu;
	}

	/**
	 * Gets the show q visited set.
	 *
	 * @return the show q visited set
	 */
	public JCheckBoxMenuItem getShowQVisitedSet() {
		return showQVisitedSet;
	}

	/**
	 * Gets the q trial label.
	 *
	 * @return the q trial label
	 */
	public JLabel getqTrialLabel() {
		return qTrialLabel;
	}

	/**
	 * Gets the q trials slider.
	 *
	 * @return the q trials slider
	 */
	public JSlider getqTrialsSlider() {
		return qTrialsSlider;
	}

	/**
	 * Gets the a star console.
	 *
	 * @return the a star console
	 */
	public JTextArea getaStarConsole() {
		return aStarConsole;
	}

	/**
	 * Gets the q learn console.
	 *
	 * @return the q learn console
	 */
	public JTextArea getqLearnConsole() {
		return qLearnConsole;
	}

	/**
	 * Gets the cell action listener.
	 *
	 * @return the cell action listener
	 */
	public CellButtonListener getCellActionListener() {
		return cellActionListener;
	}

	/**
	 * Gets the main app.
	 *
	 * @return the main app
	 */
	public TAIQLearningApp getMainApp() {
		return mainApp;
	}

	/**
	 * Checks if is a star path learned.
	 *
	 * @return true, if is a star path learned
	 */
	public boolean isAStarPathLearned() {
		return isAStarPathLearned;
	}

	/**
	 * Checks if is end reachable.
	 *
	 * @return true, if is end reachable
	 */
	public boolean isEndReachable() {
		return isEndReachable;
	}
	
	/**
	 * Prints the on console and log.
	 *
	 * @param console the console
	 * @param aMsg the a msg
	 */
	public static void printOnConsoleAndLog(JTextArea console,String aMsg){
		Logger.getLogger(TAIQLearningApp.LOGGERNAME).info(aMsg);
		console.append(aMsg+System.getProperty("line.separator"));
		console.setCaretPosition(console.getDocument().getLength());
	}

	
	/**
	 * Refresh map.
	 *
	 * @param rowNumber the row number
	 * @param columnNumber the column number
	 */
	public void refreshMap(int rowNumber, int columnNumber){
		
		for(int i=0;i<rowNumber;i++){
			for(int j=0; j<columnNumber; j++){
				if(!displayDistanceMap.isSelected()){
					this.map[i][j].refreshSingleCell(this.mainApp.getqGridMap().getCell(i, j).getCellType());
				}
				else{
					this.map[i][j].refreshSingleCellDistance(this.mainApp.getaStarPathfinder().getaStarGrid().getCell(i, j).getDistanceFromAgent()) ;
				}
			}
		}
	}
	
	/**
	 * Refresh distance map.
	 *
	 * @param rowNumber the row number
	 * @param columnNumber the column number
	 */
	public void refreshDistanceMap(int rowNumber, int columnNumber){
		
		for(int i=0;i<rowNumber;i++){
			for(int j=0; j<columnNumber; j++){
				this.map[i][j].refreshSingleCellDistance(this.mainApp.getaStarPathfinder().getaStarGrid().getCell(i, j).getDistanceFromAgent()) ;
			}
		}
	}
	
	
	/**
	 * Refresh two cells.
	 *
	 * @param firstCell the first cell
	 * @param secondCell the second cell
	 */
	public void refreshTwoCells(MapCell firstCell,MapCell secondCell){
		if(!displayDistanceMap.isSelected()){
			this.map[firstCell.getRowIndex()][firstCell.getColumnIndex()].refreshSingleCell( this.mainApp.getqGridMap().getCell(firstCell.getRowIndex(), firstCell.getColumnIndex()).getCellType());
			this.map[secondCell.getRowIndex()][secondCell.getColumnIndex()].refreshSingleCell( this.mainApp.getqGridMap().getCell(secondCell.getRowIndex(), secondCell.getColumnIndex()).getCellType());
		}
		else{
			this.map[firstCell.getRowIndex()][firstCell.getColumnIndex()].refreshSingleCellDistance(this.mainApp.getaStarPathfinder().getaStarGrid().getCell(firstCell.getRowIndex(), firstCell.getColumnIndex()).getDistanceFromAgent());
			this.map[secondCell.getRowIndex()][secondCell.getColumnIndex()].refreshSingleCellDistance(this.mainApp.getaStarPathfinder().getaStarGrid().getCell(secondCell.getRowIndex(), secondCell.getColumnIndex()).getDistanceFromAgent());
		}
		
		
	}
	
	
	/**
	 * Sets the a star results.
	 *
	 * @param aIsSet the new a star results
	 */
	public void setAStarResults(boolean aIsSet){
		
		for(MapCell currCell : aIsSet?this.mainApp.getaStarPathfinder().getaStarSet():this.mainApp.getaStarPathfinder().getaStarPath()){
			if(aIsSet){
				this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setAStarSet(true);
			}
			else{
				this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setAStarPath(true);
			}
		}
	}
	
	/**
	 * Sets the q learning results.
	 *
	 * @param aIsSet the new q learning results
	 */
	public void setQLearningResults(boolean aIsSet){
		//Modify when QPath is ready!!!
		//mainApp.getaQlearning().calculateBestPath();
		
		if(!aIsSet)
		{
			QLearning QL = new QLearning(mainApp, mainApp.getqGridMap());
			QL.calculateBestPath();
		}
		for(MapCell currCell : aIsSet?this.mainApp.getqGridMap().getVisitedCells():this.mainApp.getqGridMap().getHasBeenVisitedCells()){
			if(aIsSet){
				this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setQLearningSet(true);
			}
			else{
				this.map[currCell.getRowIndex()][currCell.getColumnIndex()].setQLearningPath(true);
			}
		}
	}
	
	
	
	
	/**
	 * Draw paths.
	 */
	public void drawPaths(){
		if(showQVisitedSet.isSelected()){
			setQLearningResults(true);
		}
		if(showQVisitedPath.isSelected()){
			setQLearningResults(false);
		}
		if(showAStarPath.isSelected()){
			setAStarResults(false);
		}
		if(showAStarSet.isSelected()){
			setAStarResults(true);
		}
		
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				map[i][j].drawPath();
			}
		}
	}
	
		
	
	/**
	 * Refresh map.
	 *
	 * @param refreshCells the refresh cells
	 */
	public void refreshMap(ArrayList<MapCell> refreshCells)
	{
		Iterator<MapCell> iter = refreshCells.iterator();
		while(iter.hasNext())
		{
			MapCell curCell = iter.next();
			if(!displayDistanceMap.isSelected()){
				this.map[curCell.getRowIndex()][curCell.getColumnIndex()].refreshSingleCell(this.mainApp.getqGridMap().getCell(curCell.getRowIndex(), curCell.getColumnIndex()).getCellType());
			}
			else{
				this.map[curCell.getRowIndex()][curCell.getColumnIndex()].refreshSingleCellDistance(this.mainApp.getaStarPathfinder().getaStarGrid().getCell(curCell.getRowIndex(), curCell.getColumnIndex()).getDistanceFromAgent());
			}
		}
	}

	
	
	/**
	 * Refresh partial map.
	 *
	 * @param refreshCells the refresh cells
	 */
	public void refreshPartialMap(ArrayList<AStarCell> refreshCells){
		for(MapCell iterCell: refreshCells){
			if(!displayDistanceMap.isSelected()){
				this.map[iterCell.getRowIndex()][iterCell.getColumnIndex()].refreshSingleCell(this.mainApp.getqGridMap().getCell(iterCell.getRowIndex(), iterCell.getColumnIndex()).getCellType());
			}
			else{
				this.map[iterCell.getRowIndex()][iterCell.getColumnIndex()].refreshSingleCellDistance(this.mainApp.getaStarPathfinder().getaStarGrid().getCell(iterCell.getRowIndex(), iterCell.getColumnIndex()).getDistanceFromAgent());
			}
			}
	}
	
	/**
	 * Refresh partial map q.
	 *
	 * @param refreshCells the refresh cells
	 */
	public void refreshPartialMapQ(ArrayList<MapCell> refreshCells){
		for(MapCell iterCell: refreshCells){
			this.map[iterCell.getRowIndex()][iterCell.getColumnIndex()].refreshSingleCell(this.mainApp.getqGridMap().getCell(iterCell.getRowIndex(), iterCell.getColumnIndex()).getCellType());
			}
	}
	
		
	
	/**
	 * The listener interface for receiving actionElements events.
	 * The class that is interested in processing a actionElements
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addActionElementsListener<code> method. When
	 * the actionElements event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see ActionElementsEvent
	 */
	/**
	 * The listener interface for receiving actionElements events.
	 * The class that is interested in processing a actionElements
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addActionElementsListener<code> method. When
	 * the actionElements event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see ActionElementsEvent
	 */
	class ActionElementsListener implements ActionListener,ItemListener,ChangeListener{
		
		/** The main app. */
		private TAIQLearningApp mainApp;
		
		
		/**
		 * Instantiates a new action elements listener.
		 *
		 * @param mainApp the main app
		 */
		public ActionElementsListener(TAIQLearningApp mainApp) {
			super();
			this.mainApp = mainApp;
		}

		/**
		 * Checks if is map displayed.
		 *
		 * @return true, if is map displayed
		 */
		public boolean isMapDisplayed(){
			return this.mainApp.getMapGUI().getMap()[0][0].getText().equals("");
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */

		public void actionPerformed(ActionEvent e) {
			String listenedCommand = e.getActionCommand();
			
			if(listenedCommand.equals("Generate Map")){
				try {
				this.mainApp.setqGridMap(new QGrid());
				this.mainApp.getMapGUI().refreshMap(QGrid.MAPHEIGHT, QGrid.MAPWIDTH);
				this.mainApp.setaStarPathfinder(new AStarThread(this.mainApp));
				isEndReachable = true;
				} catch (UnreachableEndException e1) {
					isEndReachable = false;
					JOptionPane.showMessageDialog(this.mainApp.getAppWindow(),"The goal is unreachable on the generated map.\nPlease regenerate the map.","Warning",JOptionPane.WARNING_MESSAGE);
				}
				
				isAStarPathLearned = false;
				isQLearnPathLearned = false;
				learnAStarPathButton.setEnabled(isEndReachable);
				showAStarPath.setEnabled(isAStarPathLearned);
				showAStarPath.setSelected(false);
				showAStarSet.setEnabled(isAStarPathLearned);
				showAStarSet.setSelected(false);
				showQVisitedPath.setEnabled(isQLearnPathLearned);
				showQVisitedPath.setSelected(false);
				showQVisitedSet.setEnabled(isQLearnPathLearned);
				showQVisitedSet.setSelected(false);
				displayDistanceMap.setEnabled(isAStarPathLearned);
				displayDistanceMap.setSelected(false);
				aStarConsole.setText("");
				qLearnConsole.setText("");
				aStarPathInfo.setText(PATHINFOTEXT+"ND");
				aStarSetInfo.setText(SETINFOTEXT+"ND");
				qLearningPathInfo.setText(PATHINFOTEXT+"ND");
				qLearningVisitedInfo.setText(SETINFOTEXT+"ND");
				refreshMap(QGrid.MAPHEIGHT, QGrid.MAPWIDTH);
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
				this.mainApp.getMapGUI().refreshMap(QGrid.MAPHEIGHT,QGrid.MAPWIDTH );
				
			}
			
			if(listenedCommand.equals("Compute A* Path")){
				this.mainApp.getaStarPathfinder().execute();
				isAStarPathLearned = true;
				showAStarPath.setEnabled(isAStarPathLearned);
				showAStarSet.setEnabled(isAStarPathLearned);
				displayDistanceMap.setEnabled(isAStarPathLearned);
			}
			
			
			/*if(listenedCommand.equals("Start learning")){
				if(QLThread != null)
				{
					if(!QLThread.isAlive())
					{
						QLThread = new QLearning(mainApp, mainApp.getqGridMap());
						QLThread.start();
					}
				}
				else
				{
					QLThread = new QLearning(mainApp, mainApp.getqGridMap());
					QLThread.start();
				}
			}*/
			
			if(listenedCommand.equals("Reset")){
				if(QLThread != null)
					if(!QLThread.isAlive())
						QLThread.reset();
			}
			
			
			if(listenedCommand.equals("Start learning")){
				
				if(qTrialsSlider.getValue() > 0){
					if(QLearningThread != null)
					{
						if(!QLearningThread.isAlive())
						{
							QLearningThread = new QLearnThread(mainApp, mainApp.getqGridMap());
							QLearningThread.setRepeat(qTrialsSlider.getValue());
							QLearningThread.start();
						}
					}
					else
					{
						QLearningThread = new QLearnThread(mainApp, mainApp.getqGridMap());
						QLearningThread.setRepeat(qTrialsSlider.getValue());
						QLearningThread.start();
					}
				}
			}
			
			if(listenedCommand.equals("Stop learning")){		
				if(QLearningThread != null)
				{
					QLearningThread.stopIteration();
				}
			}
			
			if(listenedCommand.equals("About")){
				JOptionPane.showMessageDialog(this.mainApp.getAppWindow(), ABOUTIMG, 
						"About TAI-QLearning", JOptionPane.PLAIN_MESSAGE);
			}
			
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
		 */
		@Override
		public void itemStateChanged(ItemEvent e) {
			 
		        if (e.getItemSelectable().equals(showAStarPath)) {
		            if(e.getStateChange() == ItemEvent.SELECTED){
		            	drawPaths();
		            }
		            if(e.getStateChange() == ItemEvent.DESELECTED){
		            	this.mainApp.getMapGUI().refreshPartialMap(this.mainApp.getaStarPathfinder().getaStarPath());
		            	drawPaths();
		            }
		            
		        }
		        
		        if (e.getItemSelectable().equals(showAStarSet)) {
		        	if(e.getStateChange() == ItemEvent.SELECTED){
		            	drawPaths();
		            }
		            if(e.getStateChange() == ItemEvent.DESELECTED){
		            	this.mainApp.getMapGUI().refreshPartialMap(this.mainApp.getaStarPathfinder().getaStarSet());
		            	drawPaths();
		            }
		            
		        }
			
		        if (e.getItemSelectable().equals(showQVisitedSet)) {
		        	if(e.getStateChange() == ItemEvent.SELECTED){
		        		drawPaths();
		            }
		            if(e.getStateChange() == ItemEvent.DESELECTED){
		            	//this.mainApp.getMapGUI().refreshPartialMapQ(this.mainApp.getqGridMap().getVisitedCells());
		            	this.mainApp.getMapGUI().refreshMap(QGrid.MAPHEIGHT, QGrid.MAPWIDTH);
		            	drawPaths();
		            }
		            
		        }
			
		        if (e.getItemSelectable().equals(showQVisitedPath)) {
		        	if(e.getStateChange() == ItemEvent.SELECTED){
		        		drawPaths();
		            }
		            if(e.getStateChange() == ItemEvent.DESELECTED){
		            	//this.mainApp.getMapGUI().refreshPartialMapQ(this.mainApp.getqGridMap().getVisitedCells());
		            	this.mainApp.getMapGUI().refreshMap(QGrid.MAPHEIGHT, QGrid.MAPWIDTH);
		            	drawPaths();
		            }
		            
		        }
		        
		        if (e.getItemSelectable().equals(displayDistanceMap)) {
		        	if(e.getStateChange() == ItemEvent.SELECTED){
		        		this.mainApp.getMapGUI().refreshDistanceMap(QGrid.MAPHEIGHT, QGrid.MAPWIDTH);
		        		drawPaths();
		            }
		            if(e.getStateChange() == ItemEvent.DESELECTED){
		            	this.mainApp.getMapGUI().refreshMap(QGrid.MAPHEIGHT, QGrid.MAPWIDTH);
		            	drawPaths();
		            }
		            
		        }
		}

		/* (non-Javadoc)
		 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
		 */
		@Override
		public void stateChanged(ChangeEvent e) {
			 JSlider source = (JSlider)e.getSource();
			 if(!(source.getValueIsAdjusting())){
				 qTrialLabel.setText("Q Learning Trials: "+source.getValue());
			 }
			
		}
	}
	
	/**
	 * Gets the a star path info.
	 *
	 * @return the a star path info
	 */
	public JLabel getaStarPathInfo() {
		return aStarPathInfo;
	}

	/**
	 * Sets the a star path info.
	 *
	 * @param aStarPathInfo the new a star path info
	 */
	public void setaStarPathInfo(JLabel aStarPathInfo) {
		this.aStarPathInfo = aStarPathInfo;
	}

	/**
	 * Gets the a star set info.
	 *
	 * @return the a star set info
	 */
	public JLabel getaStarSetInfo() {
		return aStarSetInfo;
	}

	/**
	 * Sets the a star set info.
	 *
	 * @param aStarSetInfo the new a star set info
	 */
	public void setaStarSetInfo(JLabel aStarSetInfo) {
		this.aStarSetInfo = aStarSetInfo;
	}

	/**
	 * Gets the q learning visited info.
	 *
	 * @return the q learning visited info
	 */
	public JLabel getqLearningVisitedInfo() {
		return qLearningVisitedInfo;
	}

	/**
	 * Sets the q learning visited info.
	 *
	 * @param qLearningVisitedInfo the new q learning visited info
	 */
	public void setqLearningVisitedInfo(JLabel qLearningVisitedInfo) {
		this.qLearningVisitedInfo = qLearningVisitedInfo;
	}

	/**
	 * Gets the q learning path info.
	 *
	 * @return the q learning path info
	 */
	public JLabel getqLearningPathInfo() {
		return qLearningPathInfo;
	}

	/**
	 * Sets the q learning path info.
	 *
	 * @param qLearningPathInfo the new q learning path info
	 */
	public void setqLearningPathInfo(JLabel qLearningPathInfo) {
		this.qLearningPathInfo = qLearningPathInfo;
	}

	/**
	 * The listener interface for receiving cellButton events.
	 * The class that is interested in processing a cellButton
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addCellButtonListener<code> method. When
	 * the cellButton event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see CellButtonEvent
	 */
	public class CellButtonListener implements ActionListener{
		
		/** The main app. */
		private TAIQLearningApp mainApp;
		
		/** The row. */
		private int row;
		
		/** The column. */
		private int column;
		
		
		/**
		 * Instantiates a new cell button listener.
		 *
		 * @param mainApp the main app
		 */
		public CellButtonListener(TAIQLearningApp mainApp) {
			super();
			this.mainApp = mainApp;
		}


		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			String listenedCommand = e.getActionCommand(); 
			parseActionCommand(listenedCommand);
			AStarCell currACell = this.mainApp.getaStarPathfinder().getaStarGrid().getCell(this.row, this.column);
			QGridCell currQCell = this.mainApp.getqGridMap().getCell(this.row, this.column);
			mainApp.getMapGUI().getaStarConsole().append("Reachable Cells from" + "("+this.row+","+this.column+"):\n");
			for(MapCell reachableCell : currACell.getReachableCells())
			{
				AStarCell aCell = (AStarCell)reachableCell;
				mainApp.getMapGUI().getaStarConsole().append("("+reachableCell.getRowIndex()+","+reachableCell.getColumnIndex()+") - "+"d: "+aCell.getDistanceFromAgent()+"- h: "+new Double(aCell.getHeuristicDistanceFromGoal())+"\n");
			}
			mainApp.getMapGUI().getqLearnConsole().append("Reachable Cells from" + "("+this.row+","+this.column+"):\n");
			for(MapCell reachableCell : currQCell.getReachableCells())
			{
				QGridCell aCell = (QGridCell)reachableCell;
				mainApp.getMapGUI().getqLearnConsole().append("("+reachableCell.getRowIndex()+","+reachableCell.getColumnIndex()+") - "+"r: "+aCell.getCellReward()+"- Q: "+aCell.getCellQValue()+"\n");
			}
		}
		
		/**
		 * Parses the action command.
		 *
		 * @param actionCommand the action command
		 */
		public void parseActionCommand(String actionCommand){
			String[] firstSplit = actionCommand.split(GUICell.getAttributedelimiter());
			this.row = Integer.parseInt(firstSplit[0]);
			this.column = Integer.parseInt(firstSplit[1]);
		}
		
	}
	
	
}

