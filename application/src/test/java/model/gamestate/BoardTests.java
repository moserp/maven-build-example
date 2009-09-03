package model.gamestate;

import junit.framework.TestCase;
import model.strategy.ExampleStrategy;
import controller.gameplay.StubView;
import controller.gameplay.TicTacToeGame;

public class BoardTests extends TestCase {

	private Board board;

	@Override
	protected void setUp() {
		board = new Board();
	}

	public void testAllPositionsInBoardAreCreatedEmpty() {
	}

	public void testKnowsIfItIsEmpty() {
		assertTrue(board.isEmpty());
	}

	public void testKnowsIfIsNotEmpty() {
		board.setPosition(4, 5, Board.HUMAN_PLAYER_MARK);

		assertFalse(board.isEmpty());
	}

	public void testReSettingBoardAgainPreservesPositions() {
		board.setPosition(4, 5, Board.HUMAN_PLAYER_MARK);
		assertFalse(board.isEmpty());

		int[] newBoardArray = new int[Board.MAX_BOARD_SIZE];
		for (int i = 0; i < newBoardArray.length; i++) {
			newBoardArray[i] = board.getPosition(i);
		}

		board = new Board(newBoardArray);
		assertFalse(board.isEmpty());

	}

	public void testCanSetPositionsOnNew10by10Board() throws Exception {
		board.setPosition(0, 0, Board.HUMAN_PLAYER_MARK);
		board.setPosition(9, 9, Board.COMPUTER_PLAYER_MARK);

		assertEquals(Board.HUMAN_PLAYER_MARK, board.getPosition(0, 0));
		assertEquals(Board.COMPUTER_PLAYER_MARK, board.getPosition(9, 9));
	}

	public void testCanAndGetSetPositionsWithSingleIntegerRepresentation()
			throws Exception {
		board.setPosition(1, 0, Board.HUMAN_PLAYER_MARK);
		board.setPosition(0, 8, Board.COMPUTER_PLAYER_MARK);
		board.setPosition(9, 9, Board.COMPUTER_PLAYER_MARK);

		assertEquals(Board.HUMAN_PLAYER_MARK, board.getPosition(1, 0));
		assertEquals(Board.COMPUTER_PLAYER_MARK, board.getPosition(0, 8));
		assertEquals(Board.COMPUTER_PLAYER_MARK, board.getPosition(9, 9));

		assertEquals(Board.HUMAN_PLAYER_MARK, board.getPosition(1, 0));
		assertEquals(Board.COMPUTER_PLAYER_MARK, board.getPosition(0, 8));
		assertEquals(Board.COMPUTER_PLAYER_MARK, board.getPosition(9, 9));
	}

	public void testConvertOneDimensionalBoardtoTwoDimensionalBoard()
			throws Exception {
		int[] emptyBoardArray = new int[Board.MAX_BOARD_SIZE];
		for (int i = 0; i < Board.MAX_BOARD_SIZE; i++) {
			emptyBoardArray[i] = 0;
		}
		int[] oneDimensionalBoardArray = emptyBoardArray;

		oneDimensionalBoardArray[10] = Board.COMPUTER_PLAYER_MARK;
		oneDimensionalBoardArray[15] = Board.HUMAN_PLAYER_MARK;

		Board board = new Board(oneDimensionalBoardArray);

		assertEquals(Board.COMPUTER_PLAYER_MARK, board.getPosition(1, 0));
		assertEquals(Board.HUMAN_PLAYER_MARK, board.getPosition(1, 5));
	}

	public void testTooBigABoardBlowsUp() throws Exception {
		int[] tooBigABoard = new int[Board.MAX_BOARD_SIZE + 1];

		try {
			@SuppressWarnings("unused")
			Board doomedBoard = new Board(tooBigABoard);
		} catch (Exception e) {
			assertEquals(
					"Incoming one-dimensional board array must be of length "
							+ Board.MAX_BOARD_SIZE, e.getMessage());
		}
	}

	public void testCanGetOppositePlayerMarkForEitherPlayerMark()
			throws Exception {
		int playerMark = Board.COMPUTER_PLAYER_MARK;
		int otherPlayerMark = board.getOppositePlayerMarkFor(playerMark);

		assertEquals(Board.HUMAN_PLAYER_MARK, otherPlayerMark);
		assertEquals(playerMark, board
				.getOppositePlayerMarkFor(otherPlayerMark));
	}

	public void testPositionIsEmpty() throws Exception {
		assertTrue(board.positionIsEmpty(45));
		assertTrue(board.positionIsEmpty(4, 5));
	}

	public void testPositionIsNotEmpty() throws Exception {
		board.setPosition(4, 5, Board.HUMAN_PLAYER_MARK);

		assertFalse(board.positionIsEmpty(45));
		assertFalse(board.positionIsEmpty(4, 5));
	}

	public void testPositionIsOutsideBoardBoundaries() throws Exception {
		assertFalse(board
				.within1DBoardPositionIndexBounds(Board.MAX_BOARD_SIZE + 10));
	}

	public void testRowIsOutsideBoardBoundaries() throws Exception {
		assertFalse(board.within2DBoardPositionIndexBounds(Board.MAX_ROWS + 5,
				Board.MAX_COLUMNS - 1));
		assertFalse(board.within2DBoardPositionIndexBounds(-5,
				Board.MAX_COLUMNS - 1));
	}

	public void testColumnIsOutsideBoardBoundaries() throws Exception {
		assertFalse(board.within2DBoardPositionIndexBounds(Board.MAX_ROWS - 1,
				Board.MAX_COLUMNS + 5));
		assertFalse(board.within2DBoardPositionIndexBounds(Board.MAX_ROWS - 1,
				-5));
	}

	public void testPositionIsWithinBoardBoundaries() throws Exception {
		assertTrue(board
				.within1DBoardPositionIndexBounds(Board.MAX_BOARD_SIZE - 10));
		assertTrue(board.within2DBoardPositionIndexBounds(Board.MAX_ROWS - 1,
				Board.MAX_COLUMNS - 1));
		assertTrue(board.within2DBoardPositionIndexBounds(Board.MAX_ROWS - 1,
				Board.MAX_COLUMNS - 1));
	}

	public void test1DEmptyPositionCheckOutsideBoardBoundaries()
			throws Exception {
		try {
			board.positionIsEmpty(Board.MAX_BOARD_SIZE + 10);
		} catch (Exception e) {
			assertEquals("Board index 110 is outside board bounds.", e
					.getMessage());
		}
	}

	public void test2DEmptyPositionCheckOutsideBoardBoundaries()
			throws Exception {
		try {
			board.positionIsEmpty(Board.MAX_ROWS + 5, Board.MAX_COLUMNS - 1);
		} catch (Exception e) {
			assertEquals("Board position 15,9 is outside board bounds.", e
					.getMessage());
		}
	}

	public void test1DGetPositionOutsideBoardBoundaries() throws Exception {
		try {
			board.getPosition(Board.MAX_BOARD_SIZE + 10);
		} catch (Exception e) {
			assertEquals("Board index 110 is outside board bounds.", e
					.getMessage());
		}
	}

	public void test2DSetPositionOutsideBoardBoundaries() throws Exception {
		try {
			board.setPosition(-5, Board.MAX_COLUMNS - 1,
					Board.HUMAN_PLAYER_MARK);
		} catch (Exception e) {
			assertEquals("Board index -5,9 is outside board bounds.", e
					.getMessage());
		}
	}

	public void testSetIncomingBoardArray() throws Exception {
		int[] oneDimensionalBoardArray = new int[Board.MAX_BOARD_SIZE];

		oneDimensionalBoardArray[10] = Board.COMPUTER_PLAYER_MARK;
		oneDimensionalBoardArray[15] = Board.HUMAN_PLAYER_MARK;

		TicTacToeGame game = new TicTacToeGame(new ExampleStrategy(),
				new StubView());
		game.setBoard(oneDimensionalBoardArray);

		Board board = game.getBoard();

		assertEquals(Board.COMPUTER_PLAYER_MARK, board.getPosition(1, 0));
		assertEquals(Board.HUMAN_PLAYER_MARK, board.getPosition(1, 5));
	}

	public void testPositionGetterOnGame() throws Exception {
		TicTacToeGame game = new TicTacToeGame(new ExampleStrategy(),
				new StubView());
		Board board = game.getBoard();

		board.setPosition(4, 4, Board.HUMAN_PLAYER_MARK);

		assertEquals(Board.HUMAN_PLAYER_MARK, game.getPosition(44));
	}

	public void testPositionIsAvailable() throws Exception {
		TicTacToeGame game = new TicTacToeGame(new ExampleStrategy(),
				new StubView());
		Board board = game.getBoard();

		board.setPosition(4, 4, Board.HUMAN_PLAYER_MARK);

		assertTrue(game.positionIsAvailable(42));
		assertTrue(game.positionIsAvailable(4, 2));
		assertFalse(game.positionIsAvailable(44));
		assertFalse(game.positionIsAvailable(4, 4));
	}

}
