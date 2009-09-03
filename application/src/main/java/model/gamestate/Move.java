package model.gamestate;

import controller.gameplay.TicTacToeGame.MoveScore;

public class Move {
	private int position;
	private MoveScore score = MoveScore.NONE;
	private String message;

	public Move(int position, MoveScore score, String message) {
		this.position = position;
		this.score = score;
		this.message = message;
	}

	public int getPosition() {
		return position;
	}

	public int getScore() {
		return score.getScore();
	}

	public String getMessage() {
		return message;
	}

	public boolean isValidMove() {
		return getPosition() != -1;
	}
}
