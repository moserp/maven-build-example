package model.patternsearching;

import junit.framework.TestCase;
import model.gamestate.Board;
import model.gamestate.Board.SeriesSize;
import model.patterns.ISeries;
import model.patterns.SeriesGroup;

public class GapSeriesFinderTests extends TestCase {
	private GapSeriesFinder seriesFinder;
	private Board board;
	private SeriesGroup allSeriesFound;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		board = new Board();
		seriesFinder = new GapSeriesFinder(board);
	}

	public void testPlugsSimpleHorizontalGapSeries() throws Exception {
		board.setPosition(5, 1, Board.HUMAN_PLAYER_MARK);
		board.setPosition(5, 3, Board.HUMAN_PLAYER_MARK);
		allSeriesFound = seriesFinder.getAllSeriesOfSize(SeriesSize.THREE,
				Board.HUMAN_PLAYER_MARK);
		assertEquals(1, allSeriesFound.size());

		ISeries seriesFound = allSeriesFound.get(0);
		assertEquals(Board.getSingleCoordValueFor(5, 2), seriesFound.getGap(0));
	}

	public void testPlugsSimpleHorizontalGapSeriesAtLineEnd() throws Exception {
		board.setPosition(5, 7, Board.HUMAN_PLAYER_MARK);
		board.setPosition(5, 9, Board.HUMAN_PLAYER_MARK);
		allSeriesFound = seriesFinder.getAllSeriesOfSize(SeriesSize.THREE,
				Board.HUMAN_PLAYER_MARK);
		assertEquals(1, allSeriesFound.size());

		ISeries seriesFound = allSeriesFound.get(0);
		assertEquals(Board.getSingleCoordValueFor(5, 8), seriesFound.getGap(0));
	}

	public void testPlugsSimpleVerticalGapSeries() throws Exception {
		board.setPosition(7, 9, Board.HUMAN_PLAYER_MARK);
		board.setPosition(9, 9, Board.HUMAN_PLAYER_MARK);
		allSeriesFound = seriesFinder.getAllSeriesOfSize(SeriesSize.THREE,
				Board.HUMAN_PLAYER_MARK);
		assertEquals(1, allSeriesFound.size());

		ISeries seriesFound = allSeriesFound.get(0);
		assertEquals(Board.getSingleCoordValueFor(8, 9), seriesFound.getGap(0));
	}

	public void testPlugsSimpleDiagDownGapSeries() throws Exception {
		board.setPosition(7, 7, Board.HUMAN_PLAYER_MARK);
		board.setPosition(9, 9, Board.HUMAN_PLAYER_MARK);
		allSeriesFound = seriesFinder.getAllSeriesOfSize(SeriesSize.THREE,
				Board.HUMAN_PLAYER_MARK);
		assertEquals(1, allSeriesFound.size());

		ISeries seriesFound = allSeriesFound.get(0);
		assertEquals(Board.getSingleCoordValueFor(8, 8), seriesFound.getGap(0));
	}

	public void testPlugsSimpleDiagUpGapSeries() throws Exception {
		board.setPosition(2, 7, Board.HUMAN_PLAYER_MARK);
		board.setPosition(0, 9, Board.HUMAN_PLAYER_MARK);
		allSeriesFound = seriesFinder.getAllSeriesOfSize(SeriesSize.THREE,
				Board.HUMAN_PLAYER_MARK);
		assertEquals(1, allSeriesFound.size());

		ISeries seriesFound = allSeriesFound.get(0);
		assertEquals(Board.getSingleCoordValueFor(1, 8), seriesFound.getGap(0));
	}

	public void testPlugsComplexDiagUpGapSeriesOfFour() throws Exception {
		board.setPosition(3, 6, Board.HUMAN_PLAYER_MARK);
		board.setPosition(2, 7, Board.HUMAN_PLAYER_MARK);
		board.setPosition(0, 9, Board.HUMAN_PLAYER_MARK);
		allSeriesFound = seriesFinder.getAllSeriesOfSize(SeriesSize.FOUR,
				Board.HUMAN_PLAYER_MARK);
		assertEquals(1, allSeriesFound.size());

		ISeries seriesFound = allSeriesFound.get(0);
		assertEquals(Board.getSingleCoordValueFor(1, 8), seriesFound.getGap(0));
	}

	public void testPlugsComplexDiagUpGapSeriesOfFive() throws Exception {
		board.setPosition(3, 6, Board.HUMAN_PLAYER_MARK);
		board.setPosition(4, 5, Board.HUMAN_PLAYER_MARK);
		board.setPosition(1, 8, Board.HUMAN_PLAYER_MARK);
		board.setPosition(0, 9, Board.HUMAN_PLAYER_MARK);
		allSeriesFound = seriesFinder.getAllSeriesOfSize(SeriesSize.FIVE,
				Board.HUMAN_PLAYER_MARK);
		assertEquals(1, allSeriesFound.size());

		ISeries seriesFound = allSeriesFound.get(0);
		assertEquals(Board.getSingleCoordValueFor(2, 7), seriesFound.getGap(0));
	}

}
