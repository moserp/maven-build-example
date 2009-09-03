package controller.gameplay;

import model.gamestate.Board;
import model.gamestate.GameState;
import model.gamestate.Board.SeriesSize;
import model.strategy.IStrategy;
import view.applet.IGameView;

public class TicTacToeGame {
	private SeriesSize winningSize = SeriesSize.FIVE;

	private Board board;
	private GameState gameState;
	private IStrategy strategy;

	private IGameView view;

	public static final int MAX_NUMBER_OF_MOVES = 48;

	public enum MoveScore {
		TEN(10), NINE(9), EIGHT(8), SEVEN(7), SIX(6), FIVE(5), FOUR(4), THREE(3), TWO(
				2), ONE(1), ZERO(0), NONE(-1);

		private final int score;

		private MoveScore(int score) {
			this.score = score;
		}

		public int getScore() {
			return score;
		}
	}

	public TicTacToeGame(IStrategy strategy, IGameView view) {
		this.strategy = strategy;
		this.view = view;
		startNewGame();
	}

	public void startNewGame() {
		this.strategy = strategy.newInstance();
		gameState = new GameState();
		board = strategy.getBoard();
		view.restartGame();
	}

	public void makeCompleteMoveCycle(int playerPosition) {
		incrementMoveNumber();
		markTheirMove(playerPosition);
		makeOurMove();
		updateGameState();
	}

	public void markTheirMove(int playerMove) {
		markMove(playerMove, Board.HUMAN_PLAYER_MARK);
	}

	public void makeOurMove() {
		if (noWinnerYet()) {
			int position = makeMove();
			markMove(position, Board.COMPUTER_PLAYER_MARK);
		}
	}

	public int makeMove() {
		int movePosition = strategy.makeMove();
		gameState.setLastMove(movePosition);
		return movePosition;
	}

	public void updateGameState() {
		if (weWonTheGame()) {
			gameState.setComputerWon();
			view.computerWonGame();
		} 
		
		if (theyWonTheGame()) {
			gameState.setHumanPlayerWon();
			view.humanComputerWonGame();
		} 
		
		if (isDraw()) {
			gameState.setDraw();
			view.gameIsADraw();
		}
	}

	public void incrementMoveNumber() {
		gameState.incrementMovenumber();
	}

	private boolean isDraw() {
		return (moveNumber() > MAX_NUMBER_OF_MOVES);
	}

	private boolean theyWonTheGame() {
		return wonTheGame(Board.HUMAN_PLAYER_MARK, getWinningSize());
	}

	private boolean weWonTheGame() {
		return wonTheGame(Board.COMPUTER_PLAYER_MARK, getWinningSize());
	}

	public boolean noWinnerYet() {
		return (!theyWonTheGame() && (!weWonTheGame()));
	}

	public boolean wonTheGame(int playerMark, SeriesSize winningSeriesSize) {
		return strategy.wonTheGame(playerMark, winningSeriesSize);
	}

	public void markMove(int position, int playerMark) {
		int row = Board.getRowCoordFor(position);
		int column = Board.getColumnCoordFor(position);
		board.setPosition(row, column, playerMark);
		view.drawMark(row, column, playerMark);
	}

	public void setBoard(int[] incomingBoardArray) {
		board.setBoardArray(incomingBoardArray);
	}

	public Board getBoard() {
		return board;
	}

	public int getPosition(int position) {
		return board.getPosition(position);
	}

	public int moveNumber() {
		return gameState.moveNumber();
	}

	public boolean positionIsAvailable(int playerMove) {
		return board.positionIsEmpty(playerMove);
	}

	public boolean positionIsAvailable(int row, int column) {
		return board.positionIsEmpty(row, column);
	}

	public boolean weHaveAWinner() {
		return (!noWinnerYet());
	}

	public int lastMove() {
		return gameState.lastMove();
	}

	public boolean inPlay() {
		return gameState.inPlay();
	}

	public boolean humanPlayerWon() {
		return gameState.humanPlayerWon();
	}

	public boolean computerWon() {
		return gameState.computerWon();
	}

	public boolean justStarted() {
		return gameState.justStarted();
	}

	public GameState getGameState() {
		return gameState;
	}

	public SeriesSize getWinningSize() {
		return winningSize;
	}
}
