import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class TicTacToe extends JFrame{

	private JButton [][] buttons;
	private JButton [] sbuttons;
	private JButton start,exit;
	private JPanel startPage,nextPage;
	private GridPanel g;
	private SidePanel s;
	private JLabel name,statusBar;
	private String player1,player2;
	private int sign = 0;
	private Timer t;

	public enum GameState{
		START,SEL,PVP,PVC,CVA,PVA
	}

	private GameState currentState = GameState.START;

	private int[][] m = { {1,1},{0,0},{0,2},{2,0},{2,2},{0,1},{1,0},{2,1},{1,2} };

	TicTacToe(){
		UIManager.put("Button.disabledBackground", new Color(7,51,0));
	    GridBagLayout gridbag = new GridBagLayout();
	    GridBagConstraints c = new GridBagConstraints();
	    
	    this.setTitle("TicTacToe");
		this.setLayout(new BorderLayout());
		startPage = new JPanel();
		nextPage = new JPanel();

		startPage.setLayout(gridbag);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.HORIZONTAL;
		name = new JLabel("Tic Tac Toe");
		name.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
		start = new JButton("START GAME");
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(start == e.getSource()){
					startPage.setVisible(false);
					nextPage.setVisible(true);
				}
			}
		});
		exit = new JButton("EXIT");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(exit == e.getSource()){
					System.exit(0);
				}
			}
		});
		startPage.add(name,c);
		startPage.add(Box.createVerticalStrut(10),c);
		startPage.add(start,c);
		startPage.add(Box.createVerticalStrut(10),c);
		startPage.add(exit,c);
		
		//next page starts here :)
		
		g = new GridPanel();	
		s = new SidePanel();
		
		statusBar = new JLabel("Select an option to start game.");
		statusBar.setHorizontalAlignment(SwingConstants.CENTER);
		statusBar.setVerticalAlignment(SwingConstants.CENTER);
		statusBar.setForeground(Color.BLACK);
//		statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
		statusBar.setBorder(BorderFactory.createLineBorder(new Color(0,100,100)));
	    
	    nextPage.setLayout(gridbag);
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 1;
	    c.gridheight = 2;
	    c.gridx = 0;
	    c.gridy = 0;
	    c.weightx = 50;
	    c.weighty = 200;
	    gridbag.setConstraints(s, c);
	    nextPage.add(s);
	    
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 1;
	    c.gridy = 0;
	    c.weightx = 100;
	    c.weighty = 100;
	    gridbag.setConstraints(g, c);
	    nextPage.add(g);

	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 1;
	    c.gridy = 2;
	    gridbag.setConstraints(statusBar, c);
	    nextPage.add(statusBar);
		
	    nextPage.setVisible(false);
		this.getContentPane().add(startPage);
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.PAGE_AXIS));
		this.getContentPane().add(nextPage);

		this.setSize(500,500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//timer
		
		int delay = 1000; //milliseconds
		ActionListener taskPerformer = new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {
				int a;
			int x,y;
			Random rand = new Random();				
				if (sign == 1)
				{
					int e = moveh(sign);
					if(e==0)
					{ int h =0;
						do
						{
							x = m[h][0];
							y = m[h][1];
							h++;
						}while (buttons[x][y].getText() != "" && h<9);
						buttons[x][y].setText("O");
						buttons[x][y].setEnabled(false);
					}
					sign = 0;
					statusBar.setForeground(Color.BLACK);
					statusBar.setText("CPU's turn!");
				}
				else
				{
					do
					{
						x = rand.nextInt(3);
						y = rand.nextInt(3);
					}while (buttons[x][y].getText() != "");
					buttons[x][y].setText("X");
					buttons[x][y].setEnabled(false);
					sign = 1;
					statusBar.setForeground(Color.BLACK);
					statusBar.setText("AI Bot's turn!");
				}
				a = checkState();				
			if(a == 1){
		        statusBar.setForeground(Color.RED);
		        statusBar.setText("AI Bot Won!!!");
		        t.stop();
			}
			else if (a == 2){
				statusBar.setForeground(Color.RED);
		        statusBar.setText("CPU Won!!!");
		        t.stop();
			}
			else if(a == 3){
				statusBar.setForeground(Color.RED);
		        statusBar.setText("It's a Tie!");
		        t.stop();
			}
		  }
		};
		t = new Timer(delay, taskPerformer);

	}
	
	//SIDE PANEL
	private class SidePanel extends JPanel{
		SidePanel(){
			this.setLayout(new GridLayout(6,1,1,20));
			this.setSize(new Dimension(200,500));
			sbuttons = new JButton[6];
			sbuttons[0] = new JButton("User1 vs User2");
			sbuttons[1] = new JButton("   User vs CPU  ");
			sbuttons[2] = new JButton("  CPU vs AI Bot ");
			sbuttons[3] = new JButton(" User vs AI Bot ");
			sbuttons[4] = new JButton("Restart");
			sbuttons[5] = new JButton("Exit");
			for(int i=0; i<=5; i++){
				this.add(sbuttons[i]);
				sbuttons[i].setBackground(new Color(17,204,242));
				sbuttons[i].setBorder(BorderFactory.createLineBorder(new Color(7,51,0)));
				sbuttons[i].setEnabled(true);
				sbuttons[i].addActionListener(new sideButtonListener());
			}
		}

		private class sideButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				for(int i = 0; i<=5; i++){
					if(sbuttons[i]== e.getSource()){
						sideLock();
						if (sbuttons[i].getText().equals("User1 vs User2")){
							playerVsplayer();
							if(currentState != GameState.PVP){
								reset();
								break;
							}
							sbuttons[i].setEnabled(false);
							sbuttons[i].setBackground(new Color(0,43,255));
//							sbuttons[i].setForeground(new Color(239,19,19));
//							sbuttons[i].setBorder(BorderFactory.createLineBorder(new Color(239,19,19)));
							statusBar.setForeground(Color.BLACK);
							statusBar.setText(player1 +"'s turn!");
						}
						else if (sbuttons[i].getText().equals("   User vs CPU  ")){
							playerVsCPU();
							if(currentState != GameState.PVC){
								reset();
								break;
							}
							sbuttons[i].setEnabled(false);
							sbuttons[i].setBackground(new Color(0,43,255));
//							sbuttons[i].setForeground(new Color(239,19,19));
//							sbuttons[i].setBorder(BorderFactory.createLineBorder(new Color(239,19,19)));
							statusBar.setForeground(Color.BLACK);
							statusBar.setText(player1 +"'s turn!");
						}
						else if (sbuttons[i].getText().equals("  CPU vs AI Bot ")){
							
							sbuttons[i].setEnabled(false);
							sbuttons[i].setBackground(new Color(0,43,255));
//							sbuttons[i].setForeground(new Color(239,19,19));
//							sbuttons[i].setBorder(BorderFactory.createLineBorder(new Color(239,19,19)));
							CPUVsAI();
						}
						else if (sbuttons[i].getText().equals(" User vs AI Bot ")){
							playerVsAI();
							if(currentState != GameState.PVA){
								reset();
								break;
							}
							sbuttons[i].setEnabled(false);
							sbuttons[i].setBackground(new Color(0,43,255));
//							sbuttons[i].setForeground(new Color(239,19,19));
//							sbuttons[i].setBorder(BorderFactory.createLineBorder(new Color(239,19,19)));
							statusBar.setForeground(Color.BLACK);
							statusBar.setText(player1 +"'s turn!");
						}
						else if (sbuttons[i].getText().equals("Restart")){
							reset();
						}
						else{
							System.exit(0);
						}
					}
				}
			}
		}
	}

	//GRID PANEL

	private class GridPanel extends JPanel implements ActionListener {

		GridPanel(){
			this.setLayout(new GridLayout(3,3));
			this.setSize(new Dimension(200,200));
			buttons = new JButton[3][3];
			for(int i=0; i<3; i++){
				for(int j=0; j<3;j++){
					buttons[i][j] = new JButton();
					this.add(buttons[i][j]);
					buttons[i][j].setEnabled(true);
					buttons[i][j].addActionListener(this);
			        buttons[i][j].setBackground(new Color(255,255,255));
				}
			}
		}
			
		public void actionPerformed(ActionEvent e){
			
			for (int i=0; i<3; i++){
				for (int j =0; j<3; j++){
					if(buttons[i][j]== e.getSource() && currentState == GameState.PVP){
						if (sign == 0){
							buttons[i][j].setText("X");
							buttons[i][j].setEnabled(false);
				            statusBar.setForeground(Color.BLACK);
				            statusBar.setText(player2 +"'s turn!");
				            sign = 1;
						}
						else{
							buttons[i][j].setText("O");
							buttons[i][j].setEnabled(false);
				            statusBar.setForeground(Color.BLACK);
				            statusBar.setText(player1 +"'s turn!");
				            sign = 0;						
						}
						check();
					}
					if(buttons[i][j] == e.getSource() && currentState == GameState.PVC){
						int x,y,a;

						buttons[i][j].setText("X");
						buttons[i][j].setEnabled(false);
						statusBar.setForeground(Color.BLACK);
						statusBar.setText(player2 +"'s turn");
						
						a = checkState();
						sign = 1;
						if(a == 0){
							do
							{
								Random rand = new Random();
								x = rand.nextInt(3);
								y = rand.nextInt(3);
							}while (buttons[x][y].getText() != "");

							buttons[x][y].setText("O");
							buttons[x][y].setEnabled(false);
							a = checkState();
							statusBar.setForeground(Color.BLACK);
							statusBar.setText(player1 +"'s turn!");
							sign = 0;
						}
						if(a == 1){
							gridLock();
				            statusBar.setForeground(Color.RED);
				            statusBar.setText(player2 +" Won!!!");
						}
						else if (a == 2){
							gridLock();
							statusBar.setForeground(Color.RED);
				            statusBar.setText(player1 +" Won!!!");
						}
						else if(a == 3){
							statusBar.setForeground(Color.RED);
				            statusBar.setText("It's a Tie!");
						}
						
					}
					
					if(buttons[i][j] == e.getSource() && currentState == GameState.PVA){
						int x,y,a;

						buttons[i][j].setText("X");
						buttons[i][j].setEnabled(false);
						statusBar.setForeground(Color.BLACK);
						statusBar.setText(player2 +"'s turn");
						
						a = checkState();
						sign = 1;
						if(a == 0){			
							int e1 = moveh(sign);
							if(e1==0)
							{
								int h=0;
								do
								{
									x = m[h][0];
									y = m[h][1];
									h++;
								}while (buttons[x][y].getText() != "" && h<9);
								buttons[x][y].setText("O");
								buttons[x][y].setEnabled(false);	
							}
							statusBar.setForeground(Color.BLACK);
							statusBar.setText(player1 +"'s turn!");
							sign = 0;
							a = checkState();
						}
						if(a == 1){
							gridLock();
				            statusBar.setForeground(Color.RED);
				            statusBar.setText(player2 +" Won!!!");
						}
						else if (a == 2){
							gridLock();
							statusBar.setForeground(Color.RED);
				            statusBar.setText(player1 +" Won!!!");
						}
						else if(a == 3){
							statusBar.setForeground(Color.RED);
				            statusBar.setText("It's a Tie!");
						}
						
					}
				}
				

			}
		}

		public void check(){
			if(checkState() == 1){
				gridLock();
	            statusBar.setForeground(Color.RED);
	            statusBar.setText(player2 +" Won!!!");
			}
			else if (checkState() == 2){
				gridLock();
				statusBar.setForeground(Color.RED);
	            statusBar.setText(player1 +" Won!!!");

			}
			else if(checkState() == 3){
				statusBar.setForeground(Color.RED);
	            statusBar.setText("It's a Tie!");

			}
		}

	}
	
	public void reset(){
		for(int j=0;j<3; j++){
			for (int i=0; i<3; i++){
				buttons[j][i].setText("");
				buttons[j][i].setEnabled(true);
		        buttons[j][i].setBackground(new Color(255,255,255));
			}
		}
		currentState = GameState.SEL;
		sign = 0;
		player1 = "";
		player2 = "";
		for (int i = 0 ; i<=5;i++){
			sbuttons[i].setEnabled(true);
			sbuttons[i].setBackground(new Color(17,204,242));
			sbuttons[i].setBorder(BorderFactory.createLineBorder(new Color(7,51,0)));
		}
        statusBar.setForeground(Color.BLACK);
        statusBar.setText("Select an option to start game.");
        if(t.isRunning()){
        	t.stop();
        }
	}
	
	public void gridLock(){
		for(int j=0;j<3; j++){
			for (int i=0; i<3; i++){
				buttons[j][i].setEnabled(false);
			}
		}
		
	}
	
	public void sideLock(){
		for (int i=0; i<4; i++){
			sbuttons[i].setEnabled(false);
		}
	}
	
	public int checkState(){
		for(int i =0; i<3 ; i++)
		{
			if(buttons[i][0].getText().equals(buttons[i][1].getText())&& buttons[i][1].getText().equals(buttons[i][2].getText()) && buttons[i][0].getText().equals("O"))
		    {
		        buttons[i][0].setBackground(new Color(0,43,255));
		        buttons[i][1].setBackground(new Color(0,43,255));
		        buttons[i][2].setBackground(new Color(0,43,255));
				return 1;
		    }			
		}
		for(int i=0;i<3;i++)
		{
			if(buttons[0][i].getText().equals(buttons[1][i].getText())&& buttons[1][i].getText().equals(buttons[2][i].getText()) && buttons[0][i].getText().equals("O"))
		    {
				buttons[0][i].setBackground(new Color(0,43,255));
				buttons[1][i].setBackground(new Color(0,43,255));
				buttons[2][i].setBackground(new Color(0,43,255));
				
		        return 1;
		    }
		}
	    if(buttons[0][0].getText().equals(buttons[1][1].getText())&& buttons[1][1].getText().equals(buttons[2][2].getText()) && buttons[0][0].getText().equals("O"))
	    {
	    	buttons[0][0].setBackground(new Color(0,43,255));
	    	buttons[1][1].setBackground(new Color(0,43,255));
	    	buttons[2][2].setBackground(new Color(0,43,255));
	        return 1;
	    }
	    if(buttons[0][2].getText().equals(buttons[1][1].getText())&& buttons[1][1].getText().equals(buttons[2][0].getText()) && buttons[0][2].getText().equals("O"))
	    {
	    	buttons[0][2].setBackground(new Color(0,43,255));
	    	buttons[1][1].setBackground(new Color(0,43,255));
	    	buttons[2][0].setBackground(new Color(0,43,255));
	        return 1;
	    }

	    for(int i =0; i<3 ; i++)
		{
			if(buttons[i][0].getText().equals(buttons[i][1].getText())&& buttons[i][1].getText().equals(buttons[i][2].getText()) && buttons[i][0].getText().equals("X"))
		    {
		        buttons[i][0].setBackground(new Color(0,43,255));
		        buttons[i][1].setBackground(new Color(0,43,255));
		        buttons[i][2].setBackground(new Color(0,43,255));
		        return 2;
		    }			
		}
		for(int i=0;i<3;i++)
		{
			if(buttons[0][i].getText().equals(buttons[1][i].getText())&& buttons[1][i].getText().equals(buttons[2][i].getText()) && buttons[0][i].getText().equals("X"))
		    {
				buttons[0][i].setBackground(new Color(0,43,255));
				buttons[1][i].setBackground(new Color(0,43,255));
				buttons[2][i].setBackground(new Color(0,43,255));
		        return 2;
		    }
		}
	    if(buttons[0][0].getText().equals(buttons[1][1].getText())&& buttons[1][1].getText().equals(buttons[2][2].getText()) && buttons[0][0].getText().equals("X"))
	    {
	    	buttons[0][0].setBackground(new Color(0,43,255));
	    	buttons[1][1].setBackground(new Color(0,43,255));
	    	buttons[2][2].setBackground(new Color(0,43,255));
	        return 2;
	    }
	    if(buttons[0][2].getText().equals(buttons[1][1].getText())&& buttons[1][1].getText().equals(buttons[2][0].getText()) && buttons[0][2].getText().equals("X"))
	    {
	    	buttons[0][2].setBackground(new Color(0,43,255));
	    	buttons[1][1].setBackground(new Color(0,43,255));
	    	buttons[2][0].setBackground(new Color(0,43,255));
	        return 2;
	    }
	    int count=0;
	    for(int i=0;i<3;i++)
	    {
	    	for(int j=0;j<3;j++)
	    	{
	    		if(buttons[i][j].getText() != "")
	    		{
	    			count++;
	    		}
	    	}
	    }
	    if(count == 9)
	    {
	    	return 3;
	    }
	    else
	    {
	        return 0;
	    }
	}

	public int [][] gridStatus(String player) {
		int [][] status = new int[2][8];
		String opponent = (player.equals("X")) ? "O" : "X";
		// System.out.println(opponent);
		int players, others;
		for (int i = 0; i < 3; i++)  
		{
			players = others = 0;
			for (int j = 0; j < 3; j++)  
			{
			  if (buttons[i][j].getText() == player)
			    players += 1;
			  else if (buttons[i][j].getText() == opponent)
			    others +=1;
			}
			status[0][i] = players;
			status[1][i] = others;
		}
		for (int i = 0; i < 3; i++)  
		{
			players = others = 0;
			for (int j = 0; j < 3; j++)  
			{
			  if (buttons[j][i].getText() == player)
			    players += 1;
			  else if (buttons[j][i].getText() == opponent)
			    others += 1;
			}
			status[0][i+3] = players;
			status[1][i+3] = others;
		}
		players = others = 0;
		for (int i = 0; i < 3; i++)  
		{
		  if (buttons[i][i].getText() == player)
		    players+=1;
		  else if (buttons[i][i].getText() == opponent)
		    others+=1;
		}
		status[0][6] = players;
		status[1][6] = others;

		players = others = 0;
		for (int i = 0; i < 3; i++)  
		{
		  if (buttons[2-i][i].getText() == player)
		    players+=1;
		  else if (buttons[2-i][i].getText() == opponent)
		    others+=1;
		}
		status[0][7] = players;
		status[1][7] = others;

		return status;
	}

	public int moveOrBlock(String player,int action){
		int [][] status = new int[2][8];
		status = gridStatus(player);
		int flag = 0;
		int revAction = (action == 1) ? 0 : 1;
		for (int i=0;i<8;i++)
		{
			if (status[action][i] == 2 && status[revAction][i] == 0 )
			{
				flag =1;
				if(i<3)
				{
					for(int j = 0; j< 3 ;j++)
					{
						if (buttons[i][j].getText() == "")
						{
							buttons[i][j].setText(player);
							buttons[i][j].setEnabled(false);
							return 1;
						}
					}
				}
				else if(i<6)
				{
					for(int j = 0; j< 3 ;j++)
					{
						if (buttons[j][i-3].getText() == "")
						{
							buttons[j][i-3].setText(player);
							buttons[j][i-3].setEnabled(false);
							return 1;
						}
					}				
				}
				else if(i==6)
				{
					for(int j = 0; j< 3 ;j++)
					{
						if (buttons[j][j].getText() == "")
						{
							buttons[j][j].setText(player);
							buttons[j][j].setEnabled(false);
							return 1;
						}
					}
				}
				else if(i==7)
				{
					for(int j = 0; j< 3 ;j++)
					{
						if (buttons[j][2-j].getText() == "")
						{
							buttons[j][2-j].setText(player);
							buttons[j][2-j].setEnabled(false);
							return 1;
						}
					}				
				}
			}
			else
			{
				continue;
			}
		}
		return flag;
	}

	public int moveh(int ch){
		String player;
		if(ch==1)
		{
			player="O";
		}
		else
		{
			player="X";
		}
		int f,g;
		f=g=0;
		f = moveOrBlock(player,0);
		if(f==0)
			g = moveOrBlock(player,1);
		if(f==0 && g==0)
		{
			return 0;
		}
		return 1;
	}
	
	public void playerVsplayer(){
		JTextField username1 = new JTextField();
		JTextField username2 = new JTextField();
		
		JLabel error = new JLabel("Username feild can't be empty");
		error.setForeground(Color.RED);
		JPanel err = new JPanel();
		err.add(error);
		
		Object[] message = {
				err,
				"Username1: ", username1,
				"Username1: ", username2
		};
		
		err.setVisible(false);
		
		int option = JOptionPane.showConfirmDialog(null, message, "Enter Usernames", JOptionPane.OK_CANCEL_OPTION);		
		while(option == JOptionPane.OK_OPTION) {
		    if (username1.getText().isEmpty() || username2.getText().isEmpty()) {
		    	err.setVisible(true);
				option = JOptionPane.showConfirmDialog(null, message, "Enter Usernames", JOptionPane.OK_CANCEL_OPTION);
				
		    }
		    else {
		        player1 = username1.getText();
		        player2 = username2.getText();
		        currentState = GameState.PVP;
		        break;
		    }
		}
		if(option == JOptionPane.CANCEL_OPTION) {
			
			JOptionPane.showMessageDialog(null, "Login Cancelled!!", "Login Cancelled", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void playerVsCPU(){
		JTextField username = new JTextField();
		JLabel error = new JLabel("Username feild can't be empty");
		error.setForeground(Color.RED);
		JPanel err = new JPanel();
		err.add(error);
		
		Object[] message = {
				err,
				"Username : ", username 
		};
		
		err.setVisible(false);
		
		int option = JOptionPane.showConfirmDialog(null, message, "Enter Usernames", JOptionPane.OK_CANCEL_OPTION);
		
		while(option == JOptionPane.OK_OPTION) {
		    if (username.getText().equals("")) {
		    	err.setVisible(true);
				option = JOptionPane.showConfirmDialog(null, message, "Enter Usernames", JOptionPane.OK_CANCEL_OPTION);
		    }
		    else {
		        player1 = username.getText();
		        player2 = "CPU";
		        currentState = GameState.PVC;
		        break;
		    }
		}
		if(option == JOptionPane.CANCEL_OPTION) {
			
			JOptionPane.showMessageDialog(null, "Login Cancelled!!", "Login Cancelled", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void CPUVsAI(){
		  Random rand = new Random();
		  sign = rand.nextInt(2);
		  if(sign == 1){
			  statusBar.setText("AI Bot's turn");
		  }
		  else{
			  statusBar.setText("CPU's turn");
		  }
		  t.start();
	}
	
	public void playerVsAI(){
		JTextField username = new JTextField();
		JLabel error = new JLabel("Username feild can't be empty");
		error.setForeground(Color.RED);
		JPanel err = new JPanel();
		err.add(error);
		
		Object[] message = {
				err,
				"Username : ", username 
		};
		
		err.setVisible(false);
		
		int option = JOptionPane.showConfirmDialog(null, message, "Enter Usernames", JOptionPane.OK_CANCEL_OPTION);		
		while(option == JOptionPane.OK_OPTION) {
		    if (username.getText().equals("")) {
		    	err.setVisible(true);
				option = JOptionPane.showConfirmDialog(null, message, "Enter Usernames", JOptionPane.OK_CANCEL_OPTION);
		    }
		    else {
		        player1 = username.getText();
		        player2 = "AI Bot";
		        currentState = GameState.PVA;
		        break;
		    }
		}
		if(option == JOptionPane.CANCEL_OPTION) {
			
			JOptionPane.showMessageDialog(null, "Login Cancelled!!", "Login Cancelled", JOptionPane.ERROR_MESSAGE);
		}
		
	}	
	
	public static void main (String args[]){
		new TicTacToe();
	}
}