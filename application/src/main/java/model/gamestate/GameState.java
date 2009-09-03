package model.gamestate;


//TODO Is this dumb little class really necessary? Should game state be returned to TicTacToe game?
public class GameState {

	public static final int HUMAN_PLAYER_WON = 2;
	public static final int COMPUTER_WON = 3;
	public static final int IN_PLAY = 0;
	public static final int DRAW = 4;

	private int moveNumber;
	private int state;
	private int lastMove;

	public GameState() {
		setInPlay();
		moveNumber = 0;
	}

	public void incrementMovenumber() {
		moveNumber++;
	}

	public void setInPlay() {
		state = IN_PLAY;
	}

	public void setComputerWon() {
		state = COMPUTER_WON;
	}

	public void setHumanPlayerWon() {
		state = HUMAN_PLAYER_WON;
	}

	public void setLastMove(int position) {
		lastMove = position;
	}

	public int moveNumber() {
		return moveNumber;
	}

	public int state() {
		return state;
	}

	public int lastMove() {
		return lastMove;
	}

	public boolean inPlay() {
		return state() == IN_PLAY;
	}

	public boolean humanPlayerWon() {
		return state == HUMAN_PLAYER_WON;
	}

	public boolean computerWon() {
		return state == COMPUTER_WON;
	}

	public boolean justStarted() {
		return moveNumber() == 0;
	}

	public void setDraw() {
		state = DRAW;
	}

	public boolean draw() {
		return (state == DRAW);
	}
}
