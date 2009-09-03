package model.patternsearching;

import junit.framework.TestCase;
import model.gamestate.Board;
import model.gamestate.Board.SeriesSize;
import model.patterns.ISeries;
import model.patterns.SeriesGroup;

public class SeriesFinderTests extends TestCase {
	private BaseSeriesFinder seriesFinder;
	private Board board;
	private SeriesGroup allSeriesFound;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		board = new Board();
		seriesFinder = new SeriesFinder(board, Board.HUMAN_PLAYER_MARK);
		board.setPosition(5, 1, Board.HUMAN_PLAYER_MARK);
		board.setPosition(5, 2, Board.HUMAN_PLAYER_MARK);
		board.setPosition(5, 3, Board.HUMAN_PLAYER_MARK);
	}

	public void testFindsOneHorizontalSeriesOfThree() throws Exception {
		allSeriesFound = seriesFinder.getAllSeriesOfSize(SeriesSize.THREE,
				Board.HUMAN_PLAYER_MARK);
		assertEquals(1, allSeriesFound.size());

		ISeries seriesFound = allSeriesFound.get(0);
		assertEquals(Board.getSingleCoordValueFor(5, 0), seriesFound
				.getStartingBlockingPosition());
		assertEquals(Board.getSingleCoordValueFor(5, 4), seriesFound
				.getEndingBlockingPosition());
	}

	public void testOnlyStartingBlockingPositionIsOpen() throws Exception {
		board.setPosition(4, 5, Board.HUMAN_PLAYER_MARK);
		board.setPosition(4, 6, Board.HUMAN_PLAYER_MARK);
		board.setPosition(4, 7, Board.HUMAN_PLAYER_MARK);
		board.setPosition(4, 8, Board.COMPUTER_PLAYER_MARK);
		allSeriesFound = seriesFinder.getAllSeriesOfSize(SeriesSize.THREE,
				Board.HUMAN_PLAYER_MARK);

		assertEquals(2, allSeriesFound.size());

		ISeries seriesFound = allSeriesFound.get(0);
		assertEquals(Board.getSingleCoordValueFor(4, 4), seriesFound
				.getStartingBlockingPosition());
		assertEquals(-1, seriesFound.getEndingBlockingPosition());
	}

	public void testOnlyEndingBlockingPositionIsOpen() throws Exception {
		board.setPosition(5, 0, Board.COMPUTER_PLAYER_MARK);
		allSeriesFound = seriesFinder.getAllSeriesOfSize(SeriesSize.THREE,
				Board.HUMAN_PLAYER_MARK);

		assertEquals(1, allSeriesFound.size());

		ISeries seriesFound = allSeriesFound.get(0);
		assertEquals(-1, seriesFound.getStartingBlockingPosition());
		assertEquals(Board.getSingleCoordValueFor(5, 4), seriesFound
				.getEndingBlockingPosition());
	}

	public void testFindsOneHorizontalSeriesOfFour() throws Exception {
		board.setPosition(5, 4, Board.HUMAN_PLAYER_MARK);
		allSeriesFound = seriesFinder.getAllSeriesOfSize(SeriesSize.FOUR,
				Board.HUMAN_PLAYER_MARK);
		assertEquals(1, allSeriesFound.size());

		ISeries seriesFound = allSeriesFound.get(0);
		assertEquals(Board.getSingleCoordValueFor(5, 0), seriesFound
				.getStartingBlockingPosition());
		assertEquals(Board.getSingleCoordValueFor(5, 5), seriesFound
				.getEndingBlockingPosition());
	}

	public void testFindsOneHorizontalSeriesOfTwo() throws Exception {
		board.setPosition(5, 3, Board.EMPTY);
		allSeriesFound = seriesFinder.getAllSeriesOfSize(SeriesSize.TWO,
				Board.HUMAN_PLAYER_MARK);
		assertEquals(1, allSeriesFound.size());

		ISeries seriesFound = allSeriesFound.get(0);
		assertEquals(Board.getSingleCoordValueFor(5, 0), seriesFound
				.getStartingBlockingPosition());
		assertEquals(Board.getSingleCoordValueFor(5, 3), seriesFound
				.getEndingBlockingPosition());
	}

	public void testFindOneHorizontalAndOneVerticalSeriesOfThree()
			throws Exception {
		board.setPosition(4, 1, Board.HUMAN_PLAYER_MARK);
		board.setPosition(6, 1, Board.HUMAN_PLAYER_MARK);

		allSeriesFound = seriesFinder.getAllSeriesOfSize(SeriesSize.THREE,
				Board.HUMAN_PLAYER_MARK);
		assertEquals(2, allSeriesFound.size());

		ISeries seriesFound = allSeriesFound.get(1);
		assertEquals(Board.getSingleCoordValueFor(3, 1), seriesFound
				.getStartingBlockingPosition());
		assertEquals(Board.getSingleCoordValueFor(7, 1), seriesFound
				.getEndingBlockingPosition());
	}

	public void testFindOneHorizontalAndOneVerticalSeriesWithNoStartingBlockingPosition()
			throws Exception {
		board.setPosition(0, 3, Board.HUMAN_PLAYER_MARK);
		board.setPosition(1, 3, Board.HUMAN_PLAYER_MARK);
		board.setPosition(2, 3, Board.HUMAN_PLAYER_MARK);

		allSeriesFound = seriesFinder.getAllSeriesOfSize(SeriesSize.THREE,
				Board.HUMAN_PLAYER_MARK);
		assertEquals(2, allSeriesFound.size());

		ISeries seriesFound = allSeriesFound.get(1);
		assertEquals(-1, seriesFound.getStartingBlockingPosition());
		assertEquals(Board.getSingleCoordValueFor(3, 3), seriesFound
				.getEndingBlockingPosition());
	}

	public void testFindOneHorizontalAndOneDiagonalDownSeriesWithNoEndingBlockingPosition()
			throws Exception {
		board.setPosition(7, 7, Board.HUMAN_PLAYER_MARK);
		board.setPosition(8, 8, Board.HUMAN_PLAYER_MARK);
		board.setPosition(9, 9, Board.HUMAN_PLAYER_MARK);

		allSeriesFound = seriesFinder.getAllSeriesOfSize(SeriesSize.THREE,
				Board.HUMAN_PLAYER_MARK);
		assertEquals(2, allSeriesFound.size());

		ISeries seriesFound = allSeriesFound.get(1);
		assertEquals(Board.getSingleCoordValueFor(6, 6), seriesFound
				.getStartingBlockingPosition());
		assertEquals(-1, seriesFound.getEndingBlockingPosition());
	}

	public void testFindOneHorizontalAndOneDiagonalUpSeriesOfThree()
			throws Exception {
		board.setPosition(0, 9, Board.HUMAN_PLAYER_MARK);
		board.setPosition(2, 7, Board.HUMAN_PLAYER_MARK);
		board.setPosition(1, 8, Board.HUMAN_PLAYER_MARK);

		allSeriesFound = seriesFinder.getAllSeriesOfSize(SeriesSize.THREE,
				Board.HUMAN_PLAYER_MARK);
		assertEquals(2, allSeriesFound.size());

		ISeries seriesFound = allSeriesFound.get(1);
		assertEquals(Board.getSingleCoordValueFor(3, 6), seriesFound
				.getStartingBlockingPosition());
		assertEquals(-1, seriesFound.getEndingBlockingPosition());
	}

	public void testFindOneHorizontalSeriesOfThreeInMixedList()
			throws Exception {
		board.setPosition(4, 2, Board.COMPUTER_PLAYER_MARK);
		board.setPosition(4, 3, Board.HUMAN_PLAYER_MARK);
		board.setPosition(4, 4, Board.COMPUTER_PLAYER_MARK);
		board.setPosition(4, 5, Board.HUMAN_PLAYER_MARK);
		board.setPosition(4, 6, Board.HUMAN_PLAYER_MARK);
		board.setPosition(4, 7, Board.HUMAN_PLAYER_MARK);

		allSeriesFound = seriesFinder.getAllSeriesOfSize(SeriesSize.THREE,
				Board.HUMAN_PLAYER_MARK);
		assertEquals(2, allSeriesFound.size());

		ISeries seriesFound = allSeriesFound.get(0);
		assertEquals(-1, seriesFound.getStartingBlockingPosition());
		assertEquals(Board.getSingleCoordValueFor(4, 8), seriesFound
				.getEndingBlockingPosition());
	}

	public void testIgnoresHorizontalSeriesOfThreeWithNoPentaRoom()
			throws Exception {
		board.setPosition(5, 4, Board.COMPUTER_PLAYER_MARK);

		allSeriesFound = seriesFinder.getAllSeriesOfSize(SeriesSize.THREE,
				Board.HUMAN_PLAYER_MARK);
		assertEquals(0, allSeriesFound.size());
	}

}
