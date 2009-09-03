package model.gamestate;

import controller.gameplay.StubView;
import controller.gameplay.TicTacToeGame;
import junit.framework.TestCase;
import model.gamestate.Board;
import model.gamestate.GameState;
import model.strategy.ExampleStrategy;

public class GameStateTests extends TestCase {
	private static final int NOBODY_PLAYER_MARK_FORCES_DRAW = 7;

	private TicTacToeGame game;
	private Board board;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		game = new TicTacToeGame(new ExampleStrategy(), new StubView());
		board = game.getBoard();
	}

	public void testBasicGameStart() throws Exception {
		assertTrue(game.justStarted());
		assertTrue(game.inPlay());
		assertFalse(game.weHaveAWinner());
	}

	public void testGameStateGetterOnGame() throws Exception {
		GameState gameState = game.getGameState();

		assertEquals(GameState.IN_PLAY, gameState.state());
	}

	public void testLastMove() throws Exception {
		GameState gameState = game.getGameState();
		gameState.setLastMove(48);

		assertEquals(48, game.lastMove());
	}

	public void testWeWinTheGame() throws Exception {
		board.setPosition(4, 4, Board.COMPUTER_PLAYER_MARK);
		board.setPosition(4, 5, Board.COMPUTER_PLAYER_MARK);
		board.setPosition(4, 6, Board.COMPUTER_PLAYER_MARK);
		board.setPosition(4, 7, Board.COMPUTER_PLAYER_MARK);
		board.setPosition(4, 8, Board.COMPUTER_PLAYER_MARK);

		game.updateGameState();

		assertTrue(game.getGameState().computerWon());
		assertTrue(game.computerWon());
		assertTrue(game.weHaveAWinner());
		assertFalse(game.getGameState().inPlay());
		assertFalse(game.inPlay());
		assertFalse(game.getGameState().humanPlayerWon());
	}

	public void testTheyWinTheGame() throws Exception {
		board.setPosition(4, 4, Board.HUMAN_PLAYER_MARK);
		board.setPosition(4, 5, Board.HUMAN_PLAYER_MARK);
		board.setPosition(4, 6, Board.HUMAN_PLAYER_MARK);
		board.setPosition(4, 7, Board.HUMAN_PLAYER_MARK);
		board.setPosition(4, 8, Board.HUMAN_PLAYER_MARK);

		game.updateGameState();

		assertTrue(game.getGameState().humanPlayerWon());
		assertTrue(game.weHaveAWinner());
		assertTrue(game.humanPlayerWon());
		assertFalse(game.getGameState().computerWon());
		assertFalse(game.getGameState().inPlay());
		assertFalse(game.inPlay());
	}

	public void testDraw() throws Exception {
		int mark = Board.HUMAN_PLAYER_MARK;

		for (int position = 0; position < Board.MAX_BOARD_SIZE; position++) {
			mark = NOBODY_PLAYER_MARK_FORCES_DRAW;
			board.setPosition(Board.getRowCoordFor(position), Board
					.getColumnCoordFor(position), mark);
		}

		for (int j = 0; j < 49; j++) {
			game.incrementMoveNumber();
		}

		game.updateGameState();

		assertFalse(game.getGameState().humanPlayerWon());
		assertTrue(game.getGameState().draw());
		assertFalse(game.getGameState().computerWon());
		assertFalse(game.getGameState().inPlay());
	}

}
