package model.patternsearching;

import junit.framework.TestCase;
import model.gamestate.Board;
import model.patterns.ShadowPosition;

public class ShadowPositionFinderTests extends TestCase {
	private Board board;
	private ShadowPositionFinder shadowFinder;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		board = new Board();
		shadowFinder = new ShadowPositionFinder(board);
	}

	public void testCanGetPositionNearPlayerPosition() throws Exception {
		board.setPosition(1, 0, Board.HUMAN_PLAYER_MARK);
		assertEquals(
				Board.getSingleCoordValueFor(2, 1),
				shadowFinder
						.getPositionInMostOpenSeriesNearOppositePlayer(Board.COMPUTER_PLAYER_MARK));
	}

	public void testCanGetPositionNearPlayerPositionWhenFirstChoiceIsNotEmpty()
			throws Exception {
		board.setPosition(1, 0, Board.HUMAN_PLAYER_MARK);
		board.setPosition(2, 1, Board.COMPUTER_PLAYER_MARK);

		assertEquals(
				Board.getSingleCoordValueFor(0, 1),
				shadowFinder
						.getPositionInMostOpenSeriesNearOppositePlayer(Board.COMPUTER_PLAYER_MARK));
	}

	public void testCanFindSinglePentaRoomLineOpenForShadowPosition()
			throws Exception {
		board.setPosition(4, 5, Board.HUMAN_PLAYER_MARK);
		board.setPosition(5, 5, Board.HUMAN_PLAYER_MARK);
		board.setPosition(6, 5, Board.HUMAN_PLAYER_MARK);
		board.setPosition(6, 6, Board.HUMAN_PLAYER_MARK);
		board.setPosition(7, 6, Board.HUMAN_PLAYER_MARK);

		assertEquals(Board.getSingleCoordValueFor(0, 1), shadowFinder
				.openLinesWithPentaRoomForPosition(new ShadowPosition(56),
						Board.COMPUTER_PLAYER_MARK));
	}

	public void testCanFindThreePentaRoomLineOpenForShadowPosition()
			throws Exception {
		board.setPosition(4, 5, Board.HUMAN_PLAYER_MARK);

		assertEquals(Board.getSingleCoordValueFor(0, 3), shadowFinder
				.openLinesWithPentaRoomForPosition(new ShadowPosition(56),
						Board.COMPUTER_PLAYER_MARK));
	}

	public void testCanFindFourPentaRoomLineOpenForShadowPosition()
			throws Exception {
		board.setPosition(4, 4, Board.HUMAN_PLAYER_MARK);

		assertEquals(Board.getSingleCoordValueFor(0, 4), shadowFinder
				.openLinesWithPentaRoomForPosition(new ShadowPosition(55),
						Board.COMPUTER_PLAYER_MARK));
	}

	public void testWeFindTheShadowPositionWithTheMostOpenLinesWithPentaRoom()
			throws Exception {
		board.setPosition(4, 5, Board.HUMAN_PLAYER_MARK);
		board.setPosition(4, 7, Board.HUMAN_PLAYER_MARK);

		board.setPosition(5, 5, Board.HUMAN_PLAYER_MARK);
		board.setPosition(6, 5, Board.HUMAN_PLAYER_MARK);
		board.setPosition(6, 6, Board.HUMAN_PLAYER_MARK);
		board.setPosition(7, 6, Board.HUMAN_PLAYER_MARK);

		assertEquals(
				Board.getSingleCoordValueFor(5, 4),
				shadowFinder
						.getPositionInMostOpenSeriesNearOppositePlayer(Board.COMPUTER_PLAYER_MARK));
	}

	public void testWeFindFirstOppositePlayerPositionWithAShadowPositionWithFourOpenLinesWithPentaRoom()
			throws Exception {
		board.setPosition(1, 1, Board.HUMAN_PLAYER_MARK);
		board.setPosition(2, 2, Board.COMPUTER_PLAYER_MARK);

		board.setPosition(1, 8, Board.HUMAN_PLAYER_MARK);
		board.setPosition(2, 7, Board.COMPUTER_PLAYER_MARK);

		board.setPosition(4, 5, Board.HUMAN_PLAYER_MARK);
		board.setPosition(4, 7, Board.HUMAN_PLAYER_MARK);

		board.setPosition(5, 5, Board.HUMAN_PLAYER_MARK);
		board.setPosition(6, 5, Board.HUMAN_PLAYER_MARK);
		board.setPosition(6, 6, Board.HUMAN_PLAYER_MARK);
		board.setPosition(7, 6, Board.HUMAN_PLAYER_MARK);

		assertEquals(
				Board.getSingleCoordValueFor(5, 4),
				shadowFinder
						.getPositionInMostOpenSeriesNearOppositePlayer(Board.COMPUTER_PLAYER_MARK));
	}

}
