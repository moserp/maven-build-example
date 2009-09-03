package model.patternsearching;

import model.gamestate.Board;
import model.gamestate.Board.SeriesSize;
import model.patterns.ISeries;
import model.patterns.DirectionalBoardPositionsList;
import model.patterns.DirectionalBoardPositionsListFactory;
import model.patterns.Series;
import model.patterns.SeriesGroup;

public class SeriesFinder extends BaseSeriesFinder implements ISeriesFinder {
	private int playerMark;
	private ISeries seriesFound;

	public SeriesFinder(Board board, int playerMark) {
		this.board = board;

		DirectionalBoardPositionsListFactory factory = new DirectionalBoardPositionsListFactory(playerMark);
		penteRoomVerifier = new PenteRoomVerifier(board);
		listGroup = factory.getAllIndexLists();
	}

	protected ISeries searchIndexListForSeriesOfSize(
			DirectionalBoardPositionsList currentIndexList, SeriesSize expectedSize, int playerMark) {
		resetSeriesFound();
		this.playerMark = playerMark;

		for (int i = 0; i < currentIndexList.size(); i++) {
			int position = currentIndexList.get(i);
			int positionContents = board.getPosition(position);

			if (thisPositionIsPartOfSeries(positionContents)) {
				seriesFound.add(position);
			}

			if (weFoundLastPositionInExpectedSeries(expectedSize,
					positionContents)) {
				break;
			}
		}

		if (ourSeriesSoFarIsNotBigEnough(expectedSize, seriesFound)) {
			resetSeriesFound();
		}

		return seriesFound;
	}

	private boolean weFoundLastPositionInExpectedSeries(
			SeriesSize expectedSize, int positionContents) {
		boolean foundSeries = false;

		if (!thisPositionIsPartOfSeries(positionContents)) {
			if (ourSeriesSoFarIsBigEnough(expectedSize, seriesFound)) {
				foundSeries = true;
			} else {
				resetSeriesFound();
			}
		}

		return foundSeries;
	}

	private void resetSeriesFound() {
		seriesFound = new Series();
	}

	private boolean ourSeriesSoFarIsNotBigEnough(SeriesSize expectedSize,
			ISeries seriesFound) {
		return seriesFound.size() < expectedSize.getSize();
	}

	private boolean ourSeriesSoFarIsBigEnough(SeriesSize expectedSize,
			ISeries seriesFound) {
		return seriesFound.size() >= expectedSize.getSize();
	}

	private boolean thisPositionIsPartOfSeries(int positionContents) {
		return positionContents == playerMark;
	}

	protected ISeries addBlockingPositionsTo(ISeries currentSeries,
			DirectionalBoardPositionsList currentList) {
		int startingPositionListIndex = currentList.getIndexFor(currentSeries
				.get(0));
		int lastSeriesPositionListIndex = currentList.getIndexFor(currentSeries
				.get(currentSeries.size() - 1));

		int startingBlockingPosition = getBlockingPositionBefore(
				startingPositionListIndex, currentList);
		int endingBlockingPosition = getBlockingPositionAfter(
				lastSeriesPositionListIndex, currentList);

		currentSeries.setStartingBlockingPosition(startingBlockingPosition);
		currentSeries.setEndingBlockingPosition(endingBlockingPosition);

		return currentSeries;
	}

	protected int getBlockingPositionAfter(int index, DirectionalBoardPositionsList currentList) {
		if (index < currentList.size() - 1) {
			int position = currentList.get(index + 1);

			if (board.getPosition(position) == Board.EMPTY)
				return position;
		}
		return -1;
	}

	protected int getBlockingPositionBefore(int index, DirectionalBoardPositionsList currentList) {
		if (index > 0) {
			int position = currentList.get(index - 1);

			if (board.getPosition(position) == Board.EMPTY)
				return position;
		}
		return -1;
	}

	public int getBestBlockingPositionForSeriesOfSize(SeriesSize size,
			int playerMark) {
		SeriesGroup allSeriesOfSize = getAllSeriesOfSize(size, playerMark);
		bestPosition = -1;
		alternatePosition = -1;

		for (int i = 0; i < allSeriesOfSize.size(); i++) {
			ISeries series = allSeriesOfSize.get(i);

			findBestBlockingPositions(series);
			if (foundGoodBlockingPosition())
				return bestPosition;
		}

		return bestPosition;
	}

	private boolean foundGoodBlockingPosition() {
		return bestPosition != -1;
	}

	private void findBestBlockingPositions(ISeries series) {
		if (seriesHasOpenStartingBlockingPosition(series)) {
			bestPosition = series.getStartingBlockingPosition();
			findAlternateBlockingPositionIfPossible(series);

		} else if (seriesHasOpenEndingBlockingPosition(series)) {
			bestPosition = series.getEndingBlockingPosition();
		}
	}

	private void findAlternateBlockingPositionIfPossible(ISeries series) {
		if (seriesHasOpenEndingBlockingPosition(series)) {
			alternatePosition = series.getEndingBlockingPosition();
		}
	}

	private boolean seriesHasOpenEndingBlockingPosition(ISeries series) {
		return series.getEndingBlockingPosition() != -1;
	}

	private boolean seriesHasOpenStartingBlockingPosition(ISeries series) {
		return series.getStartingBlockingPosition() != -1;
	}

	public int getAlternatePosition() {
		return alternatePosition;
	}

	public boolean containsSeriesOfSize(SeriesSize size, int playerMark) {
		SeriesGroup group = getAllSeriesOfSize(size, playerMark);
		return (group.size() != 0);
	}
}
