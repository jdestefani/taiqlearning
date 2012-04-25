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
import javax.swing.SwingConstants;
import javax.swing.border.Border;

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
	
	private MapActionListener cellActionListener;
	
	
	private static final int MAPHEIGHT = 20;
	private static final int MAPWIDTH = 30;
	
	private static final String FOGPATH = new String("Nebbia.png");
	private static final ImageIcon FOGICON = GUIElement.importImage(FOGPATH);
	
	

	public MapGUI() {
		super();
		
		contentPane = new JPanel();
        contentPane.setOpaque(true); 
        contentPane.setLayout(new BorderLayout());
        
		sidePanel = new JPanel();
		sidePanel.setOpaque(true);
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
	
		Border actionPanelBorder = BorderFactory.createTitledBorder("Actions");
		actionPanel = new JPanel();
		actionPanel.setBorder(actionPanelBorder);
		actionPanel.setLayout(new GridLayout(3,2));
		
		loadMapButton = new JButton("Load Map");
		loadMapButton.addActionListener(new ActionButtonListener());
		learnPathButton = new JButton("Learn Path");
		learnPathButton.addActionListener(new ActionButtonListener());
		displayQButton = new JButton("Display Q values");
		displayQButton.addActionListener(new ActionButtonListener());
		
		this.actionPanel.add(loadMapButton);
		this.actionPanel.add(learnPathButton);
		this.actionPanel.add(displayQButton);
		
		this.sidePanel.add(actionPanel);
		
       
		this.map = new JButton[MAPHEIGHT][MAPWIDTH];
		this.cellActionListener = new MapActionListener();
		
		Border gridBorder = BorderFactory.createLoweredBevelBorder();
		
		cellGrid = new JPanel();
		cellGrid.setOpaque(true); 
		cellGrid.setLayout(new GridLayout(MAPHEIGHT,MAPWIDTH,0,0));
		cellGrid.setBorder(gridBorder);
		
		for(int i=0; i<MAPHEIGHT ; i++){
			for(int j=0 ; j<MAPWIDTH; j++){
				this.map[i][j] = new JButton();
				this.cellGrid.add(this.map[i][j]);
				this.map[i][j].addActionListener(this.cellActionListener);
			}
		}
		this.mapPanel = new JScrollPane(cellGrid);
		this.mapPanel.setPreferredSize(new Dimension(600,400));
		
		Border consolePanelBorder = BorderFactory.createTitledBorder("Console");
		actionPanel = new JPanel();
		actionPanel.setBorder(actionPanelBorder);
		actionPanel.setLayout(new GridLayout(3,2));
        
		
        contentPane.add(turnCommandPanel,BorderLayout.SOUTH);
        contentPane.add(sidePanel,BorderLayout.EAST);
        
	}

	class GraphicalGUIListener implements ActionListener{
		
		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			String listenedCommand = e.getActionCommand();
			String response = null;
			String dinoState = null;
			String localView = null;
			
			if(listenedCommand.equals("Movimento")){
				mapHandler.getCellActionListener().setMoving(true);
				mapHandler.getCellActionListener().setMovingIdDino(mapHandler.getCellActionListener().getCurrIdDino());
			}
			
			if(listenedCommand.equals("Deposizione Uovo")){
			  response = registeredClient.getCommandSender().deponiUovo(registeredClient.getUserToken(), mapHandler.getCellActionListener().getCurrIdDino());
			  
			  //La deposizione delle uova , se corretta causa un reload dello stato
			  if(handleLayEggResponse(response)){
				  dinoState = registeredClient.getCommandSender().statoDinosauro(registeredClient.getUserToken(), mapHandler.getCellActionListener().getCurrIdDino());
				  mapHandler.handleDinoStateResponse(dinoState);
				}
			  
			}
			
			if(listenedCommand.equals("Crescita")){
			  response = registeredClient.getCommandSender().cresciDinosauro(registeredClient.getUserToken(), mapHandler.getCellActionListener().getCurrIdDino());
			  //La crescita se corretta porta a ridisegnare la mappa locale.
			  if(handleGrowResponse(response)){
				  dinoState = registeredClient.getCommandSender().statoDinosauro(registeredClient.getUserToken(), mapHandler.getCellActionListener().getCurrIdDino());
				  
				  if(mapHandler.handleDinoStateResponse(dinoState)){
				  
				  localView  = 
						registeredClient.getCommandSender().vistaLocale(registeredClient.getUserToken(), mapHandler.getCellActionListener().getCurrIdDino());
				 
				  mapHandler.handleLocalViewResponse(localView);
				
				  }
				
			  }
			}
			  
		}
		
	}

	/**
	 * L'inner class {@link TurnCommandListener} implementa l'interfaccia {@link ActionListener}
	 * al fine di definire le azioni da eseguire nel caso in cui i {@link JButton} presenti nella
	 * generino un {@link ActionEvent} in seguito ad un'interazione con l'utente.
	 */
	
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

