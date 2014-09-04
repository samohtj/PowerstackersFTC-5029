package userinterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import matches.Match;
import teams.TeamsList;
import teams.Team;

/**
 * A JPanel to visually display a list of FTC teams.
 * @author Jonathan
 *
 */
public class ShowTeamsListFrame extends JPanel{
	
	// Create a teams list, and a JScrollPane to store it.
	public TeamsList list = new TeamsList();
	private JScrollPane scrollPane = new JScrollPane(list.table);
	private ShowTeamFrame teamDisplayPanel = new ShowTeamFrame();
	
	//public ConsoleWindow cons = new ConsoleWindow();
	
	// Create buttons to add, delete, and edit teams
	private JButton teamAddBtn = new JButton("Add");
	private JButton teamDeleteBtn = new JButton("Delete");
	private JButton teamEditBtn = new JButton("Edit");
	
	/**
	 * Constructor that creates a new border around the pane, sets the selection mode, and adds the pane to the JPanel.
	 */
	public ShowTeamsListFrame(){
		this.setBorder(new TitledBorder("Teams"));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		list.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(scrollPane);
		
		// Button Action Listeners
		teamAddBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showAddTeamDialog();
			}
		});
		
		teamDeleteBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(list.getSelectedIndex() != -1)
					deleteTeam();
				else
					list.cons.printConsoleLine("Cannot delete team; no row selected"); 
			}
		});
		
		
		JPanel btnsPanel = new JPanel();
		btnsPanel.setLayout(new BoxLayout(btnsPanel, BoxLayout.X_AXIS));
		//btnsPanel.setMaximumSize(new Dimension(300, 100));

		teamAddBtn.setPreferredSize(new Dimension(100,100));
		teamEditBtn.setPreferredSize(new Dimension(100,100));
		teamDeleteBtn.setPreferredSize(new Dimension(100,100));
		
		btnsPanel.add(teamAddBtn);
		btnsPanel.add(teamEditBtn);
		btnsPanel.add(teamDeleteBtn);
		add(btnsPanel);
		
	}
	
	public void setConsoleWindow(ConsoleWindow console){
		list.setConsoleWindow(console);
	}
	
	public void showAddTeamDialog(){
		JTextField teamName = new JTextField(10);
		JTextField teamNumber = new JTextField(5);
		
		JPanel panel = new JPanel(new GridLayout(2, 2));
		panel.add(new JLabel("Team Name:"));
		panel.add(teamName);
		panel.add(new JLabel("Team Number:"));
		panel.add(teamNumber);
		
		int result = JOptionPane.showConfirmDialog(null, panel, "Enter Team Information", JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.OK_OPTION)
			list.addTeam(new Team(Integer.parseInt(teamNumber.getText()), teamName.getText()));
	}
	
	public void deleteTeam(){
		//list.cons.printConsoleLine("Entered 'delete somebody' method");
		try{
			list.cons.printConsoleLine("Deleting team " + list.getTeam(list.getSelectedIndex()));
			list.removeTeam(list.getSelectedIndex());
		}catch(teams.TeamsList.IndexOutOfBoundsException e) {
			list.cons.printConsoleLine("ERROR: Index out of bounds. Index not selected or does not exist.");
		}
	}
	
	/**
	 * Return the list of teams.
	 * @return
	 */
	public TeamsList getList(){
		return list;
	}

	public static void main(String[] args) {
		
		Team[] teamsArray = new Team[4];
		teamsArray[Match.RED_1] = new Team(5029, "Powerstackers");
		teamsArray[Match.RED_2] = new Team(4251, "Cougar Robotics");
		teamsArray[Match.BLUE_1] = new Team(5501, "USS Enterprise");
		teamsArray[Match.BLUE_2] = new Team(5035, "Some random team");

		ShowTeamsListFrame teamsFrame = new ShowTeamsListFrame();
		
		for(int i = 0; i < teamsArray.length; i++){
			teamsFrame.list.addTeam(teamsArray[i]);
		}
		
		JFrame frame = new JFrame();
		frame.add(teamsFrame);
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		//teamsFrame.list.cons.setVisible(true);
		
		//teamsFrame.showAddTeamDialog();
		
		
	}

}