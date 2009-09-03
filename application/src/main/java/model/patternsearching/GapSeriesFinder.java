package model.patternsearching;

import model.gamestate.Board;
import model.gamestate.Board.SeriesSize;
import model.patterns.GapSeries;
import model.patterns.ISeries;
import model.patterns.DirectionalBoardPositionsList;
import model.patterns.Series;

public class GapSeriesFinder extends SeriesFinder implements ISeriesFinder {
	private int playerMark;
	private ISeries seriesFound;
	private SeriesSize expectedSize;

	public GapSeriesFinder(Board board) {
		super(board, Board.HUMAN_PLAYER_MARK);
	}

	protected ISeries searchIndexListForSeriesOfSize(
			DirectionalBoardPositionsList currentIndexList, SeriesSize expectedSize, int playerMark) {
		this.playerMark = playerMark;
		this.seriesFound = new Series();
		this.expectedSize = expectedSize;

		for (int i = 0; i < currentIndexList.size() - expectedSize.getSize()
				+ 1; i++) {
			seriesFound = addSeriesIfItQualifies(getNextCandidateListOfExpectedSize(
					currentIndexList, i));
		}

		return seriesFound;
	}

	private ISeries addSeriesIfItQualifies(DirectionalBoardPositionsList candidateIndexList) {
		int firstPosition = candidateIndexList.get(0);
		int lastPosition = candidateIndexList
				.get(candidateIndexList.size() - 1);

		if (weHaveASeries(firstPosition, lastPosition)) {
			seriesFound = qualifyingGapSeries(expectedSize, candidateIndexList);
		}
		return seriesFound;
	}

	private ISeries qualifyingGapSeries(SeriesSize expectedSize,
			DirectionalBoardPositionsList candidateIndexList) {
		if ((candidateListContainsBoardMark(candidateIndexList, Board.EMPTY) && (!candidateListContainsBoardMark(
				candidateIndexList, board.getOppositePlayerMarkFor(playerMark))))) {

			seriesFound = createGapSeriesIfIndexListIsOfExpectedSize(candidateIndexList);
		}

		return seriesFound;
	}

	private ISeries createGapSeriesIfIndexListIsOfExpectedSize(
			DirectionalBoardPositionsList candidateIndexList) {
		if (candidateIndexList.size() == expectedSize.getSize()) {
			seriesFound = createGapSeries(candidateIndexList);
		}
		return seriesFound;
	}

	protected DirectionalBoardPositionsList getNextCandidateListOfExpectedSize(
			DirectionalBoardPositionsList currentIndexList, int currentStartingPositionIndex) {
		DirectionalBoardPositionsList candidateList = new DirectionalBoardPositionsList();
		for (int j = currentStartingPositionIndex; j < currentStartingPositionIndex
				+ expectedSize.getSize(); j++) {
			candidateList.add(currentIndexList.get(j));
		}

		return candidateList;
	}

	private boolean weHaveASeries(int firstPosition, int lastPosition) {
		return (positionEquals(firstPosition, playerMark))
				&& positionEquals(lastPosition, playerMark);
	}

	protected ISeries createGapSeries(DirectionalBoardPositionsList candidateList) {
		GapSeries series = new GapSeries();

		for (int i = 0; i < candidateList.size(); i++) {
			int position = candidateList.get(i);
			series.add(position);
			markPositionAsEmptyIfEmpty(series, position);
		}
		return series;
	}

	private void markPositionAsEmptyIfEmpty(GapSeries series, int position) {
		if (board.getPosition(position) == Board.EMPTY) {
			series.addEmptyPosition(position);
		}
	}

	protected boolean candidateListContainsBoardMark(DirectionalBoardPositionsList candidateList,
			int mark) {
		for (int positionIndex = 0; positionIndex < candidateList.size(); positionIndex++) {
			if (positionContainsBoardMark(positionIndex, mark, candidateList)) {
				return true;
			}
		}
		return false;
	}

	private boolean positionContainsBoardMark(int positionIndex, int mark,
			DirectionalBoardPositionsList candidateList) {
		return board.getPosition(candidateList.get(positionIndex)) == mark;
	}

	protected boolean positionEquals(int position, int playerMark) {
		boolean positionContainsMark = board.getPosition(position) == playerMark;
		return positionContainsMark;
	}

	protected ISeries addBlockingPositionsTo(ISeries currentSeries,
			DirectionalBoardPositionsList currentList) {
		currentSeries.setBlockingPositionsDependingOnNumberOfEmptySpaces();

		return currentSeries;
	}

}
