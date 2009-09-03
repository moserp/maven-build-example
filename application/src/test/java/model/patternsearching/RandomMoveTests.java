package model.patternsearching;

import junit.framework.TestCase;
import model.gamestate.Board;

public class RandomMoveTests extends TestCase {
	private static final int SAFE_NUMBER_OF_RANDOM_TEST_EXECUTIONS = 300;
	private Board board;
	private PatternFinder patternFinder;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		board = new Board();
		patternFinder = new PatternFinder(board);
	}

	public void testWillChooseEmptySpot() throws Exception {
		for (int i = 0; i < Board.MAX_ROWS; i++) {
			for (int j = 0; j < Board.MAX_COLUMNS; j++) {
				board.setPosition(i, j, Board.HUMAN_PLAYER_MARK);
			}
		}

		board.setPosition(0, 1, Board.EMPTY);
		board.setPosition(0, 2, Board.EMPTY);
		board.setPosition(0, 3, Board.EMPTY);
		board.setPosition(0, 4, Board.EMPTY);
		board.setPosition(0, 5, Board.EMPTY);

		for (int i = 0; i < SAFE_NUMBER_OF_RANDOM_TEST_EXECUTIONS; i++) {
			int position = patternFinder.findRandomEmptyPosition();
			assertTrue(position < 6);
			assertTrue(position >= 1);
		}

	}

	public void testWillChooseEmptyMidBoardSpot() throws Exception {
		for (int i = 0; i < Board.MAX_ROWS; i++) {
			for (int j = 0; j < Board.MAX_COLUMNS; j++) {
				board.setPosition(i, j, Board.HUMAN_PLAYER_MARK);
			}
		}

		board.setPosition(4, 3, Board.EMPTY);
		board.setPosition(4, 4, Board.EMPTY);
		board.setPosition(4, 5, Board.EMPTY);
		board.setPosition(4, 6, Board.EMPTY);
		board.setPosition(4, 7, Board.EMPTY);

		for (int i = 0; i < SAFE_NUMBER_OF_RANDOM_TEST_EXECUTIONS; i++) {
			int position = patternFinder.findRandomEmptyMidBoardPosition();
			assertTrue(position < Board.getSingleCoordValueFor(4, 8));
			assertTrue(position >= Board.getSingleCoordValueFor(4, 3));
		}
	}

	public void testCanFindRandomSpotInCenterStripeOfBoard() throws Exception {
		for (int i = 0; i < SAFE_NUMBER_OF_RANDOM_TEST_EXECUTIONS; i++) {
			int position = patternFinder.findRandomEmptyMidBoardPosition();
			assertTrue(position < Board.MID_BOARD_UPPER_BOUND);
			assertTrue(position >= Board.MID_BOARD_LOWER_BOUND);
		}
	}

	public void testCanFindRandomSpotInCenterBullsEyeSquareOfBoard()
			throws Exception {
		for (int i = 0; i < SAFE_NUMBER_OF_RANDOM_TEST_EXECUTIONS; i++) {
			int position = patternFinder.findRandomEmptyMidBoardPosition();
			assertTrue(position < Board.MID_BOARD_UPPER_BOUND);
			assertTrue(position >= Board.MID_BOARD_LOWER_BOUND);

			assertTrue(position % 10 >= 2);
			assertTrue(position % 10 <= 7);
		}
	}

}
