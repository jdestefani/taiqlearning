package main;

import ai.QGrid;
import data.CellType;
import data.QGridCell;

public class StartApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TAIQLearningApp mainApp = new TAIQLearningApp();
		
		/*double alpha= 0.1;
		double gamma=0.9; 
		double epsilon=0.2;
		int N_episodes=5000;
		int rg=10; int rs=-1;
		double reward;

		QGridCell s0 = new QGridCell(1,4);
		QGridCell sf = new QGridCell(10,4);

		Action[] actions={new Action(-1,0), new Action (-1,-1), new Action(0,-1), new Action(1,-1), 
				new Action(1,0), new Action (1,1), new Action (0,1), new Action (-1,1)};
		int N_actions= actions.length;//number of lines
		int Q[][][]= new int [QGrid.MAPHEIGHT][QGrid.MAPWIDTH][N_actions];//Q(s_line, s_column, a)

		int N_steps[]= new int [N_episodes];
		int totalReward[]= new int [N_episodes];
		for (int i=1; i<= N_episodes; i++){ //ith episode
			QGridCell s=s0; //initialize s
			while (s.getRowIndex()!=sf.getRowIndex() || s.getColumnIndex()!=sf.getColumnIndex()){
				Action a=epsGreedy(N_actions,epsilon,Q[s.getRowIndex()][s.getColumnIndex()]); //choose a from s using (eps greedy)
				QGridCell sp= moving(s,a);
				reward=sp.getCellReward();
				/*if (sp.getRowIndex()==sf.getRowIndex() && sp.getColumnIndex()==sf.getColumnIndex())
					reward=rg;
				else
					reward =rs;*/

			/*	totalReward[i]+=reward;
				int Qspap[]= new int [N_actions]; //Q(s',a') for all a'
				for (int j=1; j<=N_actions; j++){
					Qspap[j]=Q[sp.getRowIndex()][sp.getColumnIndex()][j];
				}
				Q[s.getRowIndex()][s.getColumnIndex()][a.getInt()]+=alpha*(reward+gamma*max(Qspap)-Q[s.getRowIndex()][s.getColumnIndex()][a.getInt()]);
				s=sp;
				N_steps[i]+=1;
			}
		}
		//Q= best action


		//One run from start to goal (with Greedy action selection) on the learned policy
		QGridCell s=s0;
		QGridCell sp;
		while (s.getRowIndex()!=sf.getRowIndex()||s.getColumnIndex()!=sf.getColumnIndex()){
			Action a=epsGreedy(N_actions,0,Q[s.getRowIndex()][s.getColumnIndex()]);
			sp= moving(s,a);
			draw (s,sp);
			s=sp;
		}
		*/
		
		
	}

}
