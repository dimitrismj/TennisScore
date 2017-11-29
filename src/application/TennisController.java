package application;

import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TennisController {
	@FXML
	Label games1;
	@FXML
	Label games2;
	@FXML
	Label sets1;
	@FXML
	Label sets2;
	@FXML
	Label score1;
	@FXML
	Label score2;
	@FXML
	Label scoreType;

	static HashMap<String, String> scoreCall= setScoreCalls();
	Player player1 = new Player();
	Player player2 = new Player();
	
	static boolean serverAdv = false;
	static boolean opponentAdv = false;
	static boolean isDeuce = false;
	static boolean resetScore = false;
	static int gamesServer = 0;
	static int setsServer = 0;	
	static int gamesOpponent = 0;
	static int setsOpponent = 0;

	public static HashMap<String, String> setScoreCalls() {
		scoreCall = new HashMap<String, String>();
		scoreCall.put("0-0", "Love-All");
		scoreCall.put("0-15", "Love-Fifteen");
		scoreCall.put("0-30", "Love-Thirty");
		scoreCall.put("0-40", "Love-Forty");
		scoreCall.put("15-0", "Fifteen-Love");
		scoreCall.put("15-15", "Fifteen - All");
		scoreCall.put("15-30", "Fifteen - Thirty");
		scoreCall.put("15-40", "Fifteen - Forty");
		scoreCall.put("30-0", "Thirty - Love");
		scoreCall.put("30-15", "Thirty - Fifteen");
		scoreCall.put("30-30", "Thirty - All");
		scoreCall.put("30-40", "Thirty - Forty");
		scoreCall.put("40-0", "Forty - Love");
		scoreCall.put("40-15", "Forty - Fifteen");
		scoreCall.put("40-30", "Forty - Thirty");
		scoreCall.put("40-40", "Deuce");
		scoreCall.put("3-3", "Deuce");
		scoreCall.put("4-3", "Adv server");
		scoreCall.put("5-3", "Server wins");
		scoreCall.put("3-4", "Adv opponent");
		scoreCall.put("3-5", "Opponent wins");
		return scoreCall;		
	}
	
	public void initializeScore() {
		player1.points = 0;
		player2.points = 0;
		resetScore = true;
	}
	
	public String showScore(int points) {
		String gamesText = "";
		switch (points)	{
			case 0: 
				gamesText = String.valueOf(0);
				break;
			case 1:
				gamesText = String.valueOf(15);
				break;
			case 2:
				gamesText = String.valueOf(30);
				break;
			case 3:
				gamesText = String.valueOf(40);
				break;
			case 4:
				gamesText = String.valueOf(40);
				break;	
			case 5:
				gamesText = String.valueOf(40);
				break;	
		}
		return gamesText;
	}
		
	
	public void checkDeuce()
	{
		if (scoreCall.get(showScore(player1.points) + "-" + showScore(player2.points)) == "Deuce") {
			isDeuce = true;
		}
		
		else {
			isDeuce = false;
		}
	}
		public void playSimulation(ActionEvent event) {
			if (resetScore == true) {
				score1.setText(String.valueOf(0));
				score2.setText(String.valueOf(0));
				resetScore = false;
			}
			double random = Math.random();
			if (random >= 0.5 ) {
				player1.points += 1;
				
				if (player1.points <= 2) {
					score1.setText(showScore(player1.points));
					scoreType.setText(scoreCall.get(score1.getText() + "-" + score2.getText()));
				}
				
				if (player1.points == 3) {
					checkDeuce();
					score1.setText(showScore(player1.points));
					scoreType.setText(scoreCall.get(score1.getText() + "-" + score2.getText()));
				}
				
				if (player1.points == 4 && isDeuce == false) {
					if(player2.points <= 2) {
						gamesServer += 1;
						games1.setText(String.valueOf(gamesServer));
						if (gamesServer >= 6 && (gamesServer - gamesOpponent) > 2) {
							setsServer += 1;
							sets1.setText(String.valueOf(setsServer));
							gamesServer = 0;
							games1.setText(String.valueOf(gamesServer));
							gamesOpponent = 0;
							games2.setText(String.valueOf(gamesOpponent));
						}
						score1.setText(showScore(player1.points));
						scoreType.setText("Server wins");
						initializeScore();
					}
				}
				
				else if (player1.points == 4 && isDeuce == true) {
					if (opponentAdv == false) {
						serverAdv = true;							
					}
					else if (opponentAdv == true) {
						opponentAdv = false;
						serverAdv = false;
						player1.points = 3;
						player2.points = 3;
					}
					scoreType.setText(scoreCall.get(player1.points + "-" + player2.points));
				}
				
				else if (player1.points == 5) {
					gamesServer += 1;
					games1.setText(String.valueOf(gamesServer));
					if (gamesServer >= 6 && (gamesServer - gamesOpponent) > 2) {
						setsServer += 1;
						sets1.setText(String.valueOf(setsServer));
						gamesServer = 0;
						games1.setText(String.valueOf(gamesServer));
						gamesOpponent = 0;
						games2.setText(String.valueOf(gamesOpponent));
					}
					scoreType.setText(scoreCall.get(player1.points + "-" + player2.points));
					initializeScore();
					serverAdv = false;
					opponentAdv = false;
				}
	
			}
			else if (random < 0.5 ) {
				player2.points += 1;
				
				if (player2.points <= 2) {
					score2.setText(showScore(player2.points));
					scoreType.setText(scoreCall.get(score1.getText() + "-" + score2.getText()));
				}
				
				if (player2.points == 3) {
					checkDeuce();
					score2.setText(showScore(player2.points));
					scoreType.setText(scoreCall.get(score1.getText() + "-" + score2.getText()));
				}
				
				if (player2.points == 4 && isDeuce == false) {
					if(player1.points <= 2) {
						gamesOpponent += 1;
						games2.setText(String.valueOf(gamesOpponent));
						if (gamesOpponent >= 6 && (gamesOpponent - gamesServer) > 2) {
							setsOpponent += 1;
							sets2.setText(String.valueOf(setsOpponent));
							gamesOpponent = 0;
							games2.setText(String.valueOf(gamesOpponent));
							gamesServer = 0;
							games1.setText(String.valueOf(gamesServer));
						}
						
						scoreType.setText("Opponent wins");
						initializeScore();
					}
				}
				
				else if (player2.points == 4 && isDeuce == true) {
					if (serverAdv == false) {
						opponentAdv = true;
					}
					else if (serverAdv == true) {
						serverAdv = false;
						opponentAdv = false;
						player1.points = 3;
						player2.points = 3;
					}	
					scoreType.setText(scoreCall.get(player1.points + "-" + player2.points));
				}
				
				else if (player2.points == 5) {
					gamesOpponent += 1;
					games2.setText(String.valueOf(gamesOpponent));
					if (gamesOpponent >= 6 && (gamesOpponent - gamesServer) > 2) {
						setsOpponent += 1;
						sets2.setText(String.valueOf(setsOpponent));
						gamesOpponent = 0;
						games2.setText(String.valueOf(gamesOpponent));
						gamesServer = 0;
						games1.setText(String.valueOf(gamesServer));
					}
					scoreType.setText(scoreCall.get(player1.points + "-" + player2.points));
					initializeScore();
					serverAdv = false;
					opponentAdv = false;
				}		
		}
	}
}
